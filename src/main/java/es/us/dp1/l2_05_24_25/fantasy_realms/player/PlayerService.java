package es.us.dp1.l2_05_24_25.fantasy_realms.player;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardService;
import es.us.dp1.l2_05_24_25.fantasy_realms.deck.DeckService;
import es.us.dp1.l2_05_24_25.fantasy_realms.discard.DiscardService;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.CardStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.MatchStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.PlayerStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.TurnStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.patterns.builders.PlayerBuilder;
import es.us.dp1.l2_05_24_25.fantasy_realms.turn.Turn;
import es.us.dp1.l2_05_24_25.fantasy_realms.turn.TurnService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;

/**
 * Servicio que gestiona la persistencia, manejo, y lógica de los jugadores
 * asociados a una partida.
 */
@Service
public class PlayerService {

    private PlayerRepository playerRepository;
    private CardService cardService;
    private UserService userService;
    private TurnService turnService;
    private DeckService deckService;
    private DiscardService discardService;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, CardService cardService, UserService userService,
            TurnService turnService, DeckService deckService, DiscardService discardService) {
        this.playerRepository = playerRepository;
        this.cardService = cardService;
        this.userService = userService;
        this.turnService = turnService;
        this.deckService = deckService;
        this.discardService = discardService;
    }

    /**
     * Permite al jugador del turno actual robar una carta del mazo.
     *
     * @param user  Usuario asociado al jugador.
     * @param match Partida en curso.
     * @return El {@link Player} actualizado después de robar.
     */
    @Transactional
    public Player drawCardFromDeck(User user, Match match) {

        // 1. Buscamos el player del usuario asociado a la partida, si existe
        Player player = findPlayerAssociatedToMatch(user, match);

        // 2. Validar las condiciones para poder robar del mazo
        validateDrawFromDeck(match, player, user);

        // 3. Si se cumplen las condiciones, se puede robar del mazo
        drawCardFromDeckAndUpdatePlayerAndMatch(match, player);

        return player;

    }

    /**
     * Permite al jugador del turno actual robar una carta de la zona de descarte.
     *
     * @param user   Usuario asociado al jugador.
     * @param match  Partida en curso.
     * @param cardId ID de la carta a robar.
     * @return El {@link Player} actualizado después de robar.
     */
    @Transactional
    public Player drawCardFromDiscard(User user, Match match, Integer cardId) {

        // 1. Buscamos el player del usuario asociado a la partida, si existe

        Player player = findPlayerAssociatedToMatch(user, match);

        // 2. Validar las condiciones para robar una carta de la zona de descartes

        validateDrawFromDiscard(match, player, user);

        // 3. Si se cumplen las condiciones, se puede robar de la zona de descartes

        drawCardFromDiscardAndUpdatePlayerAndMatch(match, player, cardId);

        return player;

    }

    /**
     * Permite al jugador del turno actual descartar una carta.
     *
     * @param user   Usuario asociado al jugador.
     * @param match  Partida en curso.
     * @param cardId ID de la carta a descartar.
     * @return El {@link Player} actualizado después de descartar.
     */
    @Transactional
    public Player discardCard(User user, Match match, Integer cardId) {

        // 1. Obtener el jugador asociado al usuario en la partida
        Player player = findPlayerAssociatedToMatch(user, match);

        // 2. Obtener la carta a descartar
        Card cardToDiscard = cardService.findCardById(cardId);

        // 3. Validar condiciones para descartar
        validateDiscardConditions(match, player, cardToDiscard, user);

        // 4. Realizar la acción de descartar
        performDiscard(player, match, cardToDiscard);

        // 5. Pasar al siguiente turno
        turnService.nextTurn(match);

        return player;
    }

    /**
     * Busca el jugador asociado al usuario en la partida.
     *
     * @param user  Usuario del jugador.
     * @param match Partida en curso.
     * @return El {@link Player} asociado al usuario.
     * @throws PlayerStatesException Si no se encuentra un jugador asociado al
     *                               usuario.
     */
    public Player findPlayerAssociatedToMatch(User user, Match match) {

        // Buscamos al jugador en la partida que esté asociado con el usuario dado

        return match.getPlayers().stream()
                .filter(player -> player.getUser().equals(user))
                .findFirst().orElseThrow(
                        () -> new PlayerStatesException("There is no player in the game associated with the user."));
    }

    /**
     * Busca un jugador por su ID.
     *
     * @param id ID del jugador.
     * @return El {@link Player} encontrado.
     * @throws ResourceNotFoundException Si no se encuentra el jugador.
     */
    @Transactional(readOnly = true)
    public Player findPlayerById(Integer id) {
        return playerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Player", "id", id));
    }

    /**
     * Guarda un jugador en la base de datos.
     *
     * @param player Instancia del jugador a guardar.
     * @return El {@link Player} guardado.
     */
    @Transactional
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    /**
     * Crea un jugador a partir de un usuario y un rol específico.
     *
     * @param userId ID del usuario.
     * @param role   Rol del jugador (CREADOR, PARTICIPANTE).
     * @return El {@link Player} creado.
     */
    @Transactional
    public Player createPlayerForUser(Integer userId, PlayerType role) {

        // Verificamos que el usuario exista

        User user = userService.findUser(userId);

        // Creamos el jugador con el rol dado

        Player player = new PlayerBuilder()
                .setUser(user)
                .setRole(role)
                .build();

        return player;

    }

    /**
     * Calcula y asigna la suma de puntos del jugador basada en su mano de cartas.
     *
     * @param player Jugador al que se le calcularán los puntos.
     */
    @Transactional
    public void sumPlayerPoints(Player player) {

        int score = 0;

        for (Card card : player.getPlayerHand()) {

            score += card.getFinalValue();

        }

        player.setScore(score);

    }

    /**
     * Valida las condiciones necesarias para que un jugador pueda robar una carta
     * del mazo.
     *
     * @param match  Partida en curso.
     * @param player Jugador que intenta robar.
     * @param user   Usuario asociado al jugador.
     */
    public void validateDrawFromDeck(Match match, Player player, User user) {
        ensureMatchIsInProgress(match);
        ensureMatchIsNotInScoringPhase(match);
        ensureUserHasCurrentTurn(match.getCurrentTurn(), user);
        ensureNoActionsMade(match.getCurrentTurn());
    }

    /**
     * Valida las condiciones necesarias para que un jugador pueda robar una carta
     * de la zona de descarte.
     *
     * @param match  Partida en curso.
     * @param player Jugador que intenta robar.
     * @param user   Usuario asociado al jugador.
     */
    public void validateDrawFromDiscard(Match match, Player player, User user) {
        validateDrawFromDeck(match, player, user);
        // Validación adicional si se quiere robar de descarte
        ensureIsNotFirstTurn(match.getCurrentTurn());

    }

    /**
     * Valida las condiciones necesarias para que un jugador pueda descartar una
     * carta.
     *
     * @param match         Partida en curso.
     * @param player        Jugador que intenta descartar.
     * @param cardToDiscard Carta que se desea descartar.
     * @param user          Usuario asociado al jugador.
     */
    public void validateDiscardConditions(Match match, Player player, Card cardToDiscard, User user) {
        ensureMatchIsInProgress(match);
        ensureMatchIsNotInScoringPhase(match);
        ensureUserHasCurrentTurn(match.getCurrentTurn(), user);
        ensureActionsMade(match.getCurrentTurn());
        ensureNotDiscarded(match);
        ensureCardToDiscardInHand(player, cardToDiscard);

    }

    /**
     * Verifica si una partida está en progreso.
     *
     * @param match {@link Match} a validar.
     * @throws MatchStatesException si la partida no está en progreso.
     */
    public void ensureMatchIsInProgress(Match match) {

        if (!match.isInProgress()) {
            throw new MatchStatesException("The match is not in progress!");
        }

    }

    /**
     * Verifica si una partida no está en fase de turnos especiales.
     *
     * @param match {@link Match} a validar.
     * @throws MatchStatesException si la partida ya está en la fase de turnos
     *                              especiales.
     */
    public void ensureMatchIsNotInScoringPhase(Match match) {

        if (match.isInScoringPhase()) {

            throw new MatchStatesException("The match is already in the scoring phase!");

        }

    }

    /**
     * Verifica si el usuario es el jugador asociado al turno actual.
     *
     * @param currentTurn {@link Turn} actual de la partida.
     * @param user        {@link User} que se desea validar.
     * @throws TurnStatesException si el usuario no es el jugador con el turno
     *                             actual.
     */
    public void ensureUserHasCurrentTurn(Turn currentTurn, User user) {

        if (!currentTurn.getPlayer().getUser().getUsername().equals(user.getUsername())) {
            throw new TurnStatesException("The user must be the one with the current turn!");
        }
    }

    /**
     * Verifica si un usuario no ha realizado acciones previas en su turno.
     *
     * @param currentTurn {@link Turn} actual de la partida.
     * @throws TurnStatesException si el usuario ya realizó acciones en su turno.
     */
    public void ensureNoActionsMade(Turn currentTurn) {

        if (!currentTurn.canDrawCard()) {
            throw new TurnStatesException("The player has already performed previous actions in his turn.");
        }

    }

    /**
     * Verifica si un usuario ha realizado acciones en su turno.
     *
     * @param currentTurn {@link Turn} actual de la partida.
     * @throws TurnStatesException si el usuario no ha realizado las acciones
     *                             necesarias en su turno.
     */
    public void ensureActionsMade(Turn currentTurn) {

        if (currentTurn.canDrawCard()) {
            throw new TurnStatesException("The player has to perform previous actions in his turn.");
        }

    }

    /**
     * Verifica si la carta que se desea descartar pertenece a la mano del jugador.
     *
     * @param player        {@link Player} que desea descartar una carta.
     * @param cardToDiscard {@link Card} que se desea validar.
     * @throws CardStatesException si la carta no pertenece a la mano del jugador.
     */
    public void ensureCardToDiscardInHand(Player player, Card cardToDiscard) {

        if (!player.isCardInHand(cardToDiscard)) {
            throw new CardStatesException("The card to be discarded does not belong to the player's hand!");
        }

    }

    /**
     * Verifica si el jugador ya ha descartado una carta en el turno actual.
     *
     * @param match {@link Match} que contiene el estado del turno actual.
     * @throws TurnStatesException si el jugador ya ha descartado una carta en este
     *                             turno.
     */
    public void ensureNotDiscarded(Match match) {

        if (match.getCurrentTurn().hasDiscarded()) {
            throw new TurnStatesException("The player has already discarded a card this turn!");
        }

    }

    /**
     * Realiza la acción de robar una carta del mazo, actualizando el estado de la
     * partida y del jugador.
     *
     * @param match  Partida en curso.
     * @param player Jugador que realiza la acción.
     */
    public void drawCardFromDeckAndUpdatePlayerAndMatch(Match match, Player player) {

        // Robamos la carta del mazo

        Card drawnCard = deckService.drawCard(match.getDeck().getId());

        // Asignamos a la mano del jugador

        player.getPlayerHand().add(drawnCard);

        // Actualizamos el estado del turno

        match.getCurrentTurn().drawFromDeck(drawnCard);

    }

    /**
     * Realiza la acción de robar una carta de la zona de descarte, actualizando el
     * estado de la partida y del jugador.
     *
     * @param match  Partida en curso.
     * @param player Jugador que realiza la acción.
     * @param cardId ID de la carta a robar.
     */
    public void drawCardFromDiscardAndUpdatePlayerAndMatch(Match match, Player player, Integer cardId) {

        // Robar la carta de la zona de descartes

        Card cardDrawn = discardService.drawCard(match.getDiscard().getId(), cardId);

        // Asignamos a la mano del jugador

        player.getPlayerHand().add(cardDrawn);

        // Actualizamos el estado del turno

        match.getCurrentTurn().drawFromDiscard(cardDrawn);

    }

    /**
     * Verifica que no es el primer turno.
     *
     * @param currentTurn Turno actual de la partida.
     * @throws TurnStatesException Si es el primer turno.
     */
    public void ensureIsNotFirstTurn(Turn currentTurn) {

        if (currentTurn.getTurnCount() < 2) { // El primer turno es el 1
            throw new TurnStatesException("Drawing from the discard pile is not allowed on the first turn.");
        }

    }

    /**
     * Realiza la acción de descartar una carta de la mano del jugador.
     *
     * @param player        Jugador que realiza la acción.
     * @param match         Partida en curso.
     
     * @param cardToDiscard Carta que se desea descartar.
     */
    private void performDiscard(Player player, Match match, Card cardToDiscard) {

        // Remover la carta de la mano del jugador y agregarla a la zona de descarte
        discardService.discardCardFromHand(match, player, cardToDiscard);

        // Marcar que el jugador ha descartado en el turno actual
        match.getCurrentTurn().setDiscarded(true);

        // Verificar si se inicia la fase de turnos especiales
        if (match.getDiscard().getCards().size() == 10) {
            match.activateScoringPhase(); // sets inScoringPhase = true y scoringStartTurn = turno_actual
        }
    }

    /**
     * Obtiene la lista de partidas creadas por un usuario específico a partir de la
     * partida
     * que tiene asociada cada uno de sus jugadores.
     * 
     * @param userId ID del usuario creador.
     * @return Lista de partidas creadas por el usuario.
     */
    public List<Match> getMatchesCreatedByUser(Integer userId) {
        return playerRepository.findMatchesCreatedByUser(userId);
    }

    // Métodos que se usan en el modulo de estadisticas

    public List<Player> getPlayersByUser(User currentUser) {
        return playerRepository.findByUser(currentUser);
    }

    public Integer countPlayersByUser(User currentUser) {
        return playerRepository.countByUser(currentUser);
    }

    @Transactional
    public void deletePlayer(Player player) {
        playerRepository.delete(player);
    }

    /**
     * Busca el rol del jugador en una partida específica.
     *
     * @param matchId  ID de la partida.
     * @param username Nombre de usuario del jugador.
     * @return Rol del jugador en la partida o null si no se encuentra.
     */
    @Transactional(readOnly = true)
    public PlayerType findRoleByMatchIdAndUsername(Integer matchId, String username) {
        return playerRepository.findRoleByMatchIdAndUsername(matchId, username);
    }

}
