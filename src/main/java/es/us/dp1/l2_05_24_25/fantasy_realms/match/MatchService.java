package es.us.dp1.l2_05_24_25.fantasy_realms.match;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.deck.Deck;
import es.us.dp1.l2_05_24_25.fantasy_realms.deck.DeckService;
import es.us.dp1.l2_05_24_25.fantasy_realms.discard.Discard;
import es.us.dp1.l2_05_24_25.fantasy_realms.dto.MatchDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.InvalidStatusException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.MatchStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.NotAllSpectatorsAreFriendsException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.PlayerAlreadyInMatchException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.PlayerHasNotPrivileges;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.RemovePlayerException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.friendship.FriendshipService;
import es.us.dp1.l2_05_24_25.fantasy_realms.patterns.builders.MatchBuilder;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerService;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerType;
import es.us.dp1.l2_05_24_25.fantasy_realms.turn.TurnService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;

/**
 * Servicio para gestionar las operaciones relacionadas con partidas.
 * Incluye creación, unión, inicio, finalización y eliminación de partidas.
 */
@Service
public class MatchService {

    private MatchRepository matchRepository;
    private DeckService deckService;
    private PlayerService playerService;
    private TurnService turnService;
    private FriendshipService friendshipService;
    private ApplicationEventPublisher eventPublisher; // evitar dependencia circular con AchievementService

    @Autowired
    public MatchService(MatchRepository matchRepository, DeckService deckService, PlayerService playerService,
            TurnService turnService, ApplicationEventPublisher eventPublisher, FriendshipService friendshipService) {
        this.matchRepository = matchRepository;
        this.deckService = deckService;
        this.playerService = playerService;
        this.turnService = turnService;
        this.eventPublisher = eventPublisher;
        this.friendshipService = friendshipService;
    }

    /**
     * Crea una nueva partida con el usuario como creador (estableciendo su jugador
     * oportuno).
     * 
     * @param currentUser Usuario que crea la partida.
     * @param matchName   Nombre de la partida.
     * @return La partida creada.
     */
    @Transactional
    public Match createMatch(User currentUser, String matchName) {

        // 1. Validar restricciones
        validateCreateMatch(currentUser);

        // 2. Crear el perfil de creador
        Player creator = playerService.createPlayerForUser(currentUser.getId(), PlayerType.CREADOR);

        // 3. Crear el mazo para la partida
        Deck deckForMatch = deckService.getShuffledDeckForMatch();

        // 4. Crear la partida
        Match match = new MatchBuilder()
                .setName(matchName)
                .setDeck(deckForMatch)
                .setDiscard(new Discard())
                .setPlayers(List.of(creator))
                .build();

        // 5. Guardar la partida
        return matchRepository.save(match);
    }

    /**
     * Permite a un jugador unirse a una partida en el lobby.
     * 
     * @param matchId     ID de la partida.
     * @param currentUser Usuario que desea unirse.
     * @return La partida actualizada.
     */
    @Transactional
    public Match joinMatch(Integer matchId, User currentUser) {
        // 1. Buscar la partida a la que se quiere unir el jugador
        Match match = findMatchById(matchId);

        // 2. Validar las restricciones generales
        validateJoinMatch(currentUser, match);

        // 3. Determinar el rol basado en si se ha alcanzado el máximo de jugadores
        PlayerType role = match.reachedMaxPlayers() ? PlayerType.ESPECTADOR : PlayerType.PARTICIPANTE;
        validateJoinMatch(currentUser, match);

        // 4. Crear el jugador con el rol determinado
        Player player = playerService.createPlayerForUser(currentUser.getId(), role);

        // 5. Añadir el jugador a la partida y guardar los cambios
        match.addPlayer(player);

        return matchRepository.save(match);
    }

    /**
     * Inicia una partida con las condiciones necesarias.
     * 
     * @param matchId ID de la partida.
     * @param userId  ID del usuario creador que quiere comenzar la partida.
     * @return La partida en progreso.
     */
    @Transactional
    public Match startMatch(Integer matchId, Integer userId) {

        // 1. Buscar la partida que se quiere comenzar

        Match match = findMatchById(matchId);

        // 2. Validar restricciones

        validateStartMatch(match, userId);

        // 3. Inicializar la partida

        initializeMatch(match);

        return match;
    }

    /**
     * Finaliza una partida y calcula los puntos de los jugadores.
     * 
     * @param match Partida a finalizar.
     * @return La partida finalizada.
     */
    @Transactional
    public Match endMatch(Match match) {

        // 1. Establecemos las condiciones para finalizar la partida
        establishConditionsToFinish(match);

        // 2. Sumamos los puntos de cada jugador
        sumUpPoints(match.getPlayers());

        // 3. Determinamos al jugador con más puntos y lo marcamos como ganador
        determineWinner(match);

        // 4. Publicar el evento (partida finalizada), para actualizar los logros

        eventPublisher.publishEvent(new MatchFinishedEvent(this, match));

        // 5. Guardamos la partida con su estado actualizado
        return matchRepository.save(match);

    }

    /**
     * Determina al jugador con más puntos y lo establece como ganador.
     * 
     * @param match Partida a finalizar.
     */
    public void determineWinner(Match match) {
        Optional<Player> winner = match.getPlayers().stream()
            .filter(player -> !player.getRole().equals(PlayerType.ESPECTADOR)) // Excluir espectadores
            .max(Comparator.comparingInt(Player::getScore)); // Buscar al jugador con más puntos

        if (winner.isPresent()) {
            Player winningPlayer = winner.get();
            winningPlayer.setRole(PlayerType.GANADOR); // Establecer como ganador
        }
    }

    /**
     * Elimina una partida que no ha comenzado.
     * 
     * @param matchId ID de la partida.
     * @param userId  ID del usuario creador.
     */
    @Transactional
    public void deleteMatch(Integer matchId, Integer userId) {

        // 1. Buscar la partida que se quiere borrar

        Match match = findMatchById(matchId);

        // 2. Validar las restricciones

        validateDeleteMatch(match, userId);

        // 3. Borrar la partida por su id

        matchRepository.deleteById(matchId);

    }

    /**
     * Busca una partida por su ID.
     * 
     * @param id ID de la partida.
     * @return La partida encontrada.
     */
    @Transactional(readOnly = true)
    public List<Match> findAllMatches() {

        Iterable<Match> matchesFound = matchRepository.findAll();

        List<Match> matchList = new ArrayList<>();
        matchesFound.forEach(match -> matchList.add(match));

        return matchList;

    }

    /**
     * Encuentra partidas filtradas por estado y devuelve una lista paginada.
     * 
     * @param page   Número de la página solicitada.
     * @param size   Cantidad de partidas a incluir en cada página.
     * @param status Estado de las partidas a filtrar. Los valores válidos son:
     *               `lobby`: Partidas disponibles para unirse.
     *               `inProgress`: Partidas actualmente en progreso.
     *               `finished`: Partidas finalizadas.
     * @return Página de partidas en formato {@link MatchDTO}.
     * @throws InvalidStatusException Si el valor de `status` no es uno de los
     *                                permitidos (`lobby`, `inProgress`,
     *                                `finished`).
     */
    @Transactional(readOnly = true)
    public Page<MatchDTO> findMatchesByStatus(int page, int size, String status) {

        // Crear un objeto Pageable con el número de página y el tamaño de la página
        Pageable pageable = PageRequest.of(page, size);

        Page<Match> matchPage;

        switch (status.toLowerCase()) {
            case "inprogress":
                matchPage = matchRepository.findMatchesInProgress(pageable);
                break;
            case "finished":
                matchPage = matchRepository.findMatchesFinished(pageable);
                break;
            case "lobby":
                matchPage = matchRepository.findMatchesPlayable(pageable);
                break;
            default:
                throw new InvalidStatusException(
                        "The value for ‘status’ is invalid. It should be ‘lobby’, ‘inProgress’ or ‘finished’");
        }

        return matchPage.map(MatchDTO::new);
    }

    /**
    * Devuelve una lista paginada de partidas en las que un usuario ha participado, 
    * con la opción de filtrar solo las creadas por él.
    *
    * @param userId      ID del usuario.
    * @param page        Número de la página para la paginación.
    * @param size        Cantidad de elementos por página.
    * @param onlyCreator Si es `true`, filtra únicamente partidas creadas por el usuario.
    *
    * @return Página de partidas en formato {@link MatchDTO}.
    *
    */
    @Transactional(readOnly = true)
    public Page<MatchDTO> findMatchesForUser(int userId, int page, int size, boolean onlyCreator) {
    
        Pageable pageable = PageRequest.of(page, size);
    
        // Recuperar las partidas donde el usuario participó
        List<Match> matches = matchRepository.findMatchesWhereUserParticipated(userId, pageable);
    
        List<Match> filteredMatches = new ArrayList<>();
    
        if (onlyCreator) {
            for (Match match : matches) {
                
                Player actualCreator = match.getCreador();  
                
                if (actualCreator.getUser().getId().equals(userId)) {
                    filteredMatches.add(match);
                }
            }
        } else {
            filteredMatches.addAll(matches);
        }
    
        // Convertir a Page
        Page<Match> matchPage = new PageImpl<>(filteredMatches, pageable, filteredMatches.size());
    
        return matchPage.map(MatchDTO::new);
    }    

    /**
     * Busca una partida por su ID.
     * 
     * @param id ID de la partida.
     * @return Partida encontrada.
     * @throws ResourceNotFoundException si no se encuentra una partida con el ID
     *                                   proporcionado.
     */
    @Transactional(readOnly = true)
    public Match findMatchById(Integer id) {
        return matchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Match", "id", id));
    }

    /**
     * Guarda o actualiza una partida en la base de datos.
     * 
     * @param match Partida a guardar o actualizar.
     * @return Partida guardada.
     */
    @Transactional
    public Match saveMatch(Match match) {
        return matchRepository.save(match);
    }

    /**
     * Valida las condiciones necesarias para crear una partida.
     * 
     * @param currentUser Usuario que intenta crear la partida.
     */
    public void validateCreateMatch(User currentUser) {
        ensureUserNotInMatch(currentUser);
    }

    /**
     * Valida las condiciones necesarias para unirse a una partida.
     * 
     * @param currentUser Usuario que intenta unirse.
     * @param match       Partida a la que se intenta unir.
     */
    public void validateJoinMatch(User currentUser, Match match) {
        ensureUserNotInMatch(currentUser);
        ensureMatchIsNotInProgressOrFinished(match);
        ensureMatchHasSpace(match);
    }

    /**
     * Valida las condiciones necesarias para comenzar una partida.
     * 
     * @param match  Partida que se intenta comenzar.
     * @param userId ID del usuario que intenta iniciar la partida.
     */
    public void validateStartMatch(Match match, Integer userId) {
        ensureUserIsCreator(match, userId);
        ensureMatchIsNotInProgressOrFinished(match);
        ensureMatchHasRightDimensions(match);
        ensureAllSpectatorsAreFriends(match);
        
            }
        
    private void ensureAllSpectatorsAreFriends(Match match) {

            Set<Integer> nonSpectatorUserIds = match.getPlayers().stream()
                    .filter(p -> p.getRole() != PlayerType.ESPECTADOR)
                    .map(p -> p.getUser().getId())
                    .collect(Collectors.toSet());
            

            List<User> failingSpectators = match.getPlayers().stream()
                    .filter(p -> p.getRole() == PlayerType.ESPECTADOR)
                    .map(Player::getUser)
                    .filter(spectatorUser -> {
                        
                        return nonSpectatorUserIds.stream()
                            .anyMatch(nonSpectId -> 
                                !friendshipService.areFriends(spectatorUser.getId(), nonSpectId)
                            );
                    })
                    .collect(Collectors.toList());
            

            if (!failingSpectators.isEmpty()) {
                    String failingNames = failingSpectators.stream()
                        .map(User::getUsername)
                        .collect(Collectors.joining(", "));
            
                String msg = "Los siguientes espectadores no son amigos de todos los participantes/creador: "
                                 + failingNames;
            throw new NotAllSpectatorsAreFriendsException(msg);
            }
    }

        
        /**
     * Valida las condiciones necesarias para eliminar una partida.
     * 
     * @param match  Partida que se intenta eliminar.
     * @param userId ID del usuario que intenta eliminar la partida.
     */
    public void validateDeleteMatch(Match match, Integer userId) {
        ensureMatchIsNotInProgressOrFinished(match);
        ensureUserIsCreator(match, userId);
    }

    /**
     * Verifica que el usuario no esté ya participando en otra partida.
     * 
     * @param currentUser Usuario a validar.
     * @throws PlayerAlreadyInMatchException si el usuario ya está en una partida.
     */
    public void ensureUserNotInMatch(User currentUser) {
        if (currentUser.isPlaying()) {
            throw new PlayerAlreadyInMatchException("The current user has an associated player who is in game.");
        }
    }

    /**
     * Verifica que la partida no esté en progreso ni finalizada.
     * 
     * @param match Partida a validar.
     * @throws MatchStatesException si la partida está en progreso o ya ha
     *                              finalizado.
     */
    public void ensureMatchIsNotInProgressOrFinished(Match match) {
        if (match.isInProgress() || match.isFinished()) {
            throw new MatchStatesException("Match is already in progress or finished.");
        }
    }

    /**
     * Verifica que la partida tenga espacio para más jugadores.
     * 
     * @param match Partida a validar.
     * @throws MatchStatesException si la partida ya alcanzó el límite de jugadores.
     */
    public void ensureMatchHasSpace(Match match) {
        if (match.reachedMaxPlayers()) {
            throw new MatchStatesException("You cannot join a game that has reached the player limit.");
        }
    }

    /**
     * Verifica que el usuario sea el creador de la partida.
     * 
     * @param match  Partida a validar.
     * @param userId ID del usuario a validar.
     * @throws PlayerHasNotPrivileges si el usuario no es el creador de la partida.
     */
    public void ensureUserIsCreator(Match match, Integer userId) {
        boolean userIsCreator = match.getPlayers().stream()
                .anyMatch(player -> player.getUser().getId().equals(userId) && player.getRole() == PlayerType.CREADOR);

        if (!userIsCreator) {
            throw new PlayerHasNotPrivileges("Only the creator can start the match.");
        }
    }

    /**
     * Verifica que el número de jugadores de la partida sea válido.
     * 
     * @param match Partida a validar.
     * @throws MatchStatesException si la partida no tiene un número válido de
     *                              jugadores.
    */
    public void ensureMatchHasRightDimensions(Match match) {
        if (match.notReachedMinPlayers() || match.reachedMaxPlayers()) {
            throw new MatchStatesException("Match has not reached the correct number of players.");
        }
    }

    /**
     * Establece las condiciones para finalizar una partida.
     * 
     * @param match Partida a finalizar.
    */
    
    public void establishConditionsToFinish(Match match) {

        // Establecemos la fecha de finalización
        match.setEndDate(LocalDateTime.now());

    }

    /**
     * Distribuye las cartas a los jugadores al comenzar la partida.
     * 
     * @param match Partida en la que se distribuyen las cartas.
     */
    @Transactional
    public void distributeCards(Match match) {
        
        match.getPlayers().stream()
            .filter(player -> !player.getRole().equals(PlayerType.ESPECTADOR))
            .forEach(player -> {
                List<Card> drawn = deckService.drawMultiple(match.getDeck());
                player.setPlayerHand(drawn);
            });
    }

    /**
     * Calcula y actualiza la puntuación total de cada jugador.
     * 
     * @param players Lista de jugadores de la partida.
     */
    public void sumUpPoints(List<Player> players) {
        players.stream()
            .filter(player -> !player.getRole().equals(PlayerType.ESPECTADOR))
            .forEach(player -> playerService.sumPlayerPoints(player));
    }    

    /**
     * Inicializa una partida estableciendo su estado inicial.
     * 
     * @param match Partida a inicializar.
     */
    public void initializeMatch(Match match) {

        // Distribuir las cartas

        distributeCards(match);

        // Establecer la fecha de inicio

        match.setStartDate(LocalDateTime.now());

        // Dar comienzo al primer turno

        turnService.firstTurn(match);

    }

    @Transactional
    public void togglePlayerRole(Integer matchId, User currentUser) {
        Match match = findMatchById(matchId);

        Player player = playerService.findPlayerAssociatedToMatch(currentUser, match);

        switch (player.getRole()) {
            case ESPECTADOR -> player.setRole(PlayerType.PARTICIPANTE);
            case PARTICIPANTE -> player.setRole(PlayerType.ESPECTADOR);
            default -> throw new IllegalStateException("El rol actual no permite cambiar.");
        }

        playerService.savePlayer(player);
    }
        
    // Método usado por el modulo de estadisticas

    public List<Match> findMatchesByUser(User user) {
        return matchRepository.findByPlayers_User(user);
    }

    @Transactional
    public Match removePlayerFromMatch(Integer matchId, Integer currentUserId, Integer userIdToRemove) {

        Match match = findMatchById(matchId);

        ensureMatchIsNotInProgressOrFinished(match);

        ensureUserIsCreator(match, currentUserId);

        Player playerToRemove = match.getPlayers().stream()
            .filter(p -> p.getUser().getId().equals(userIdToRemove))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException(
                "Player", "userId", userIdToRemove
            ));

        if (playerToRemove.getRole() == PlayerType.CREADOR) {
            throw new RemovePlayerException("No puedes eliminar al creador de la partida.");
        }

        match.getPlayers().remove(playerToRemove);

        playerService.deletePlayer(playerToRemove);

        return matchRepository.save(match);
    }

    @Transactional(readOnly = true)
    public List<Match> findMatchesInProgress() {
        return matchRepository.findAllMatchesInProgress();
    }


}