package es.us.dp1.l2_05_24_25.fantasy_realms.match;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.deck.Deck;
import es.us.dp1.l2_05_24_25.fantasy_realms.deck.DeckService;
import es.us.dp1.l2_05_24_25.fantasy_realms.discard.Discard;
import es.us.dp1.l2_05_24_25.fantasy_realms.dto.MatchDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.InvalidStatusException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.MatchStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.NotAllSpectatorsAreFriendsException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.PlayerHasNotPrivileges;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModService;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.friendship.FriendshipService;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.AchievementService;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerService;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerType;
import es.us.dp1.l2_05_24_25.fantasy_realms.turn.TurnService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;

@ExtendWith(SpringExtension.class)
public class MatchServiceTests {

    protected MatchService matchService; 

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private DeckService deckService;

    @Mock
    private PlayerService playerService;

    @Mock
    private TurnService turnServiceMock;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private ModService modService;

    @Mock
    private Deck deck;

    @Mock
    private Discard discard;

    @Mock
    private FriendshipService friendshipService;

    @Mock
    private AchievementService achievementService;
    
    private Match match;

    private User u1;

    private User u2;

    private User u3;

    @BeforeEach
    public void setup() {

        matchService = new MatchService(matchRepository, deckService, playerService, turnServiceMock, eventPublisher, friendshipService);

        // Crear y configurar el primer usuario (creador)
        u1 = new User();
        u1.setId(1);
        u1.setUsername("Test User");
        u1.setPlayers(new HashSet<>());

        // Crear y configurar el segundo usuario (participante)
        u2 = new User();
        u2.setId(2);
        u2.setUsername("Test User 2");
        u2.setPlayers(new HashSet<>());

        // Crear y configurar el segundo usuario (participante)
        u3 = new User();
        u3.setId(3);
        u3.setUsername("Test User 3");
        u3.setPlayers(new HashSet<>());

        Player player = new Player(u1, PlayerType.CREADOR);

        // Crear una nueva instancia de match
        match = new Match();
        match.setId(1);
        match.setDeck(deck);
        match.setDiscard(discard);
        match.setPlayers(new ArrayList<>());
        match.getPlayers().add(player);
        match.setStartDate(null);
        match.setEndDate(null);
    }

    
    @Test
    public void testCreateMatch() throws Exception {
        Deck deck = new Deck(); 
        when(deckService.getShuffledDeckForMatch()).thenReturn(deck); 
    
        when(matchRepository.save(any(Match.class))).thenAnswer(invocation -> invocation.getArgument(0));
    
        User u1 = new User();
        u1.setId(1);
        u1.setUsername("creator");
        u1.setPlayers(new HashSet<>());
        
        Player player = new Player();
        player.setUser(u1);
        player.setRole(PlayerType.CREADOR);
        when(playerService.createPlayerForUser(eq(u1.getId()), eq(PlayerType.CREADOR))).thenReturn(player);
    
        Match createdMatch = matchService.createMatch(u1, "Partida1");
    
        assertNotNull(createdMatch, "La partida creada no debería ser nula");

        assertEquals(1, createdMatch.getPlayers().size(), "Debería tener solo al creador");

        Player createdPlayer = createdMatch.getPlayers().get(0);
        assertNotNull(createdPlayer, "El jugador no debería ser nulo");
        assertEquals(u1, createdPlayer.getUser(), "El creador debería ser el primer jugador");
        assertEquals(PlayerType.CREADOR, createdPlayer.getRole(), "El creador debería tener el rol de CREADOR");

        assertNotNull(createdMatch.getDeck(), "El mazo no debería ser nulo");
        assertNotNull(createdMatch.getDiscard(), "La zona de descarte no debería ser nula");

        assertSame(deck, createdMatch.getDeck(), "El mazo asignado debería ser el correcto");
    }

    
    @Test
    public void testJoinMatch() throws Exception {

        Player player1 = new Player(u1, PlayerType.CREADOR);
        List<Player> initialPlayers = new ArrayList<>();
        initialPlayers.add(player1);

        Match match = new Match();
        match.setPlayers(initialPlayers);
        

        when(matchRepository.findById(1)).thenReturn(Optional.of(match));

        Player player2 = new Player(u2, PlayerType.PARTICIPANTE);
    

        when(playerService.createPlayerForUser(eq(u2.getId()), eq(PlayerType.PARTICIPANTE))).thenReturn(player2);
    

        when(matchRepository.save(any(Match.class))).thenReturn(match);

        Match joinedMatch = matchService.joinMatch(1, u2);
    
        assertNotNull(joinedMatch);
        assertEquals(2, joinedMatch.getPlayers().size());
    
        assertTrue(joinedMatch.getPlayers().stream()
                .anyMatch(p -> p.getUser().getId().equals(u2.getId())));
    }  
    
    @Test
    public void testStartMatch() throws Exception {

        Player p2 = new Player(u2, PlayerType.PARTICIPANTE);
        match.getPlayers().add(p2);

        Player p3 = new Player(u3, PlayerType.PARTICIPANTE);
        match.getPlayers().add(p3);

        when(matchRepository.findById(1)).thenReturn(Optional.of(match));

        Match startedMatch = matchService.startMatch(1, u1.getId());

        assertNotNull(startedMatch.getStartDate());
        assertTrue(startedMatch.isInProgress());

        verify(turnServiceMock).firstTurn(any(Match.class));
    }

    @Test
    public void testStartMatchWhenAlreadyInProgress() throws Exception{
        
        match.setStartDate(LocalDateTime.now());
        when(matchRepository.findById(1)).thenReturn(Optional.of(match));

        PlayerHasNotPrivileges exception = assertThrows(PlayerHasNotPrivileges.class, () -> {
            matchService.startMatch(1, u2.getId());
        });

        assertEquals("Only the creator can start the match.", exception.getMessage());
    }

    @Test
    public void testStartMatchNotEnoughPlayers() throws Exception{
        // Arrange
        Integer matchId = 1;
        Integer userId = 1;

        User creatorUser = new User();
        creatorUser.setId(userId);

        Player creatorPlayer = new Player(creatorUser, PlayerType.CREADOR);

        Match match = new Match();
        match.setPlayers(List.of(creatorPlayer));

        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));

        MatchStatesException exception = assertThrows(MatchStatesException.class, () -> {
            matchService.startMatch(matchId, userId);
        });

        assertEquals("Match has not reached the correct number of players.", exception.getMessage());
    }

    @Test
    public void testDeleteMatch() throws Exception{

        Player playerCreator = new Player(u1, PlayerType.CREADOR);
        match.getPlayers().add(playerCreator);

        when(matchRepository.findById(1)).thenReturn(Optional.of(match));
        doNothing().when(matchRepository).deleteById(1);

        
        matchService.deleteMatch(1, u1.getId());

        
        verify(matchRepository).deleteById(1); 
    }


    @Test
    public void testDeleteMatchWhenInProgress() throws Exception{
        
        match.setStartDate(LocalDateTime.now());
        when(matchRepository.findById(1)).thenReturn(Optional.of(match));

        MatchStatesException exception = assertThrows(MatchStatesException.class, () -> {
            matchService.deleteMatch(1, u1.getId());
        });

        assertEquals("Match is already in progress or finished.", exception.getMessage());
    }
     
    
    @Test
    public void testEndMatch() throws Exception {
        // Configuración de la partida
        Match match = new Match();
        match.setId(1);
        match.setName("Partida1");
        match.setStartDate(LocalDateTime.now());
        match.setPlayers(new ArrayList<>());
    
        User u1 = new User();
        u1.setId(1);
        u1.setUsername("creator");
        u1.setAchievements(new ArrayList<>());
    
        Player playerCreator = new Player(u1, PlayerType.CREADOR);
        playerCreator.setScore(0);
    
        Card card1 = new Card();
        card1.setFinalValue(5);
        Card card2 = new Card();
        card2.setFinalValue(10);
        playerCreator.setPlayerHand(Arrays.asList(card1, card2));
    
        match.getPlayers().add(playerCreator);
    
        doAnswer(invocation -> {
            Player player = invocation.getArgument(0);
            int totalScore = player.getPlayerHand().stream()
                                .mapToInt(Card::getFinalValue)
                                .sum();
            player.setScore(totalScore); 
            return null;
        }).when(playerService).sumPlayerPoints(any(Player.class));
    
        when(matchRepository.save(any(Match.class))).thenAnswer(invocation -> invocation.getArgument(0));
    
        doNothing().when(eventPublisher).publishEvent(any(MatchFinishedEvent.class));
    
        Match endedMatch = matchService.endMatch(match);
    
        assertNotNull(endedMatch.getEndDate(), "La fecha de finalización no debe ser null");
        assertNull(endedMatch.getCurrentTurn(), "El turno actual debe ser null");
    
        assertTrue(endedMatch.getPlayers().stream()
                .allMatch(player -> player.getScore() == 15),
                "La puntuación de los jugadores debe ser 15");
    
        verify(matchRepository, times(1)).save(endedMatch);
    }    

    @Test
    public void testDetermineWinner_Success() {
        // Crear jugadores con diferentes puntuaciones
        Player player1 = new Player(u1, PlayerType.PARTICIPANTE);
        player1.setScore(50);

        Player player2 = new Player(u2, PlayerType.PARTICIPANTE);
        player2.setScore(100); // Mayor puntuación

        Player player3 = new Player(u3, PlayerType.ESPECTADOR); // Espectador, debe ser ignorado
        player3.setScore(null);

        // Configurar la partida con los jugadores
        match.setPlayers(List.of(player1, player2, player3));

        // Llamar al método a probar
        matchService.determineWinner(match);

        // Verificar que el jugador con la mayor puntuación es el ganador
        assertEquals(PlayerType.GANADOR, player2.getRole(), "El jugador con la mayor puntuación debe ser el ganador");
        assertEquals(PlayerType.PARTICIPANTE, player1.getRole(), "El rol del jugador no ganador debe permanecer como participante");
        assertEquals(PlayerType.ESPECTADOR, player3.getRole(), "El rol del espectador debe permanecer sin cambios");
    }

    @Test
    public void testFindAllMatches() {
        when(matchRepository.findAll()).thenReturn(Arrays.asList(match));

        List<Match> matches = matchService.findAllMatches();

        assert matches.size() == 1;
        verify(matchRepository, times(1)).findAll();
    }

    
    @Test
    public void testFindMatchesByStatusInProgress() {
        // Configurar mock del repositorio para el estado "inProgress"
        Page<Match> pageMatches = new PageImpl<>(List.of(match));
        when(matchRepository.findMatchesInProgress(any(Pageable.class))).thenReturn(pageMatches);
    
        // Probar el servicio con estado "inProgress"
        Page<MatchDTO> result = matchService.findMatchesByStatus(0, 10, "inProgress");
    
        // Verificar el resultado no nulo y tamaño correcto
        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals(1, result.getContent().size(), "Debe haber 1 partida en el resultado");
    
        // Validar el contenido del MatchDTO resultante
        MatchDTO matchDTO = result.getContent().get(0);
        assertEquals(match.getId(), matchDTO.getId(), "El ID de la partida no coincide");
        assertEquals(u1.getUsername(), matchDTO.getCreator(), "El creador de la partida no coincide");
    
        // Verificar que el repositorio fue llamado una vez
        verify(matchRepository, times(1)).findMatchesInProgress(any(Pageable.class));
    }

    @Test
    public void testFindMatchesByStatusLobby() {
        // Configurar mock del repositorio para el estado "lobby"
        Page<Match> pageMatches = new PageImpl<>(List.of(match));
        when(matchRepository.findMatchesPlayable(any(Pageable.class))).thenReturn(pageMatches);

        // Probar el servicio con estado "lobby"
        Page<MatchDTO> result = matchService.findMatchesByStatus(0, 10, "lobby");

        // Verificar el resultado no nulo y tamaño correcto
        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals(1, result.getContent().size(), "Debe haber 1 partida en el resultado");

        // Validar el contenido del MatchDTO resultante
        MatchDTO matchDTO = result.getContent().get(0);
        assertEquals(match.getId(), matchDTO.getId(), "El ID de la partida no coincide");
        assertEquals(u1.getUsername(), matchDTO.getCreator(), "El creador de la partida no coincide");

        // Verificar que el repositorio fue llamado una vez
        verify(matchRepository, times(1)).findMatchesPlayable(any(Pageable.class));
    }

    @Test
    public void testFindMatchesByStatusFinished() {
        // Configurar mock del repositorio para el estado "finished"
        Page<Match> pageMatches = new PageImpl<>(List.of(match));
        when(matchRepository.findMatchesFinished(any(Pageable.class))).thenReturn(pageMatches);

        // Probar el servicio con estado "finished"
        Page<MatchDTO> result = matchService.findMatchesByStatus(0, 10, "finished");

        // Verificar el resultado no nulo y tamaño correcto
        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals(1, result.getContent().size(), "Debe haber 1 partida en el resultado");

        // Validar el contenido del MatchDTO resultante
        MatchDTO matchDTO = result.getContent().get(0);
        assertEquals(match.getId(), matchDTO.getId(), "El ID de la partida no coincide");
        assertEquals(u1.getUsername(), matchDTO.getCreator(), "El creador de la partida no coincide");

        // Verificar que el repositorio fue llamado una vez
        verify(matchRepository, times(1)).findMatchesFinished(any(Pageable.class));
    }
        

    @Test
    public void testFindMatchesByStatusInvalid() {
        // Probar con un valor de estado no válido
        InvalidStatusException exception = assertThrows(InvalidStatusException.class, () -> {
            matchService.findMatchesByStatus(0, 10, "invalidStatus");
        });

        // Verificar el mensaje de la excepción
        assertEquals("The value for ‘status’ is invalid. It should be ‘lobby’, ‘inProgress’ or ‘finished’", exception.getMessage());

        // Verificar que no se haya llamado a los métodos del repositorio
        verify(matchRepository, times(0)).findMatchesInProgress(any(Pageable.class));
        verify(matchRepository, times(0)).findMatchesFinished(any(Pageable.class));
        verify(matchRepository, times(0)).findMatchesPlayable(any(Pageable.class));
    }

     
    @Test
    public void testFindMatchByIdSuccess() {
        // Crear un objeto Match
        Match match = new Match();
        match.setId(1);
    
        // Configurar el mock para devolver la partida
        when(matchRepository.findById(1)).thenReturn(Optional.of(match));
    
        // Llamar al método findMatchById
        Match foundMatch = matchService.findMatchById(1);
    
        // Verificar que la partida devuelta es la esperada
        assertNotNull(foundMatch);
        assertEquals(1, foundMatch.getId());
    
        // Verificar que se haya llamado al método del repositorio
        verify(matchRepository, times(1)).findById(1);
    }

    @Test
    public void testFindMatchByIdNotFound() {
        // Configurar el mock para devolver un Optional vacío
        when(matchRepository.findById(1)).thenReturn(Optional.empty());

        // Verificar que se lanza la excepción correcta
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            matchService.findMatchById(1);
        });

        // Verificar el mensaje de la excepción
        assertEquals("Match not found with id: '1'", exception.getMessage());

        // Verificar que se haya llamado al método del repositorio
        verify(matchRepository, times(1)).findById(1);
    }

    @Test
    public void testFindMatchesForUser() {
        int userId = 1;
        int page = 0;
        int size = 5;
        boolean onlyCreator = true;
    
        // Inicialización de jugadores
        User creatorUser = new User();
        creatorUser.setId(userId);
        creatorUser.setUsername("creator");
    
        Player creatorPlayer = new Player(creatorUser, PlayerType.CREADOR);
    
        User winnerUser = new User();
        winnerUser.setId(userId);
        winnerUser.setUsername("winner");
    
        Player winnerPlayer = new Player(winnerUser, PlayerType.GANADOR);
    

        Match match1 = new Match();
        match1.setId(1);
        match1.setName("Partida 1");
        match1.setStartDate(LocalDateTime.now());
        match1.setDeck(new Deck());
        match1.setDiscard(new Discard());
        match1.setPlayers(Arrays.asList(creatorPlayer)); 
    

        Match match2 = new Match();
        match2.setId(2);
        match2.setName("Partida 2");
        match2.setStartDate(LocalDateTime.now());
        match2.setDeck(new Deck());
        match2.setDiscard(new Discard());
        match2.setPlayers(Arrays.asList(winnerPlayer, creatorPlayer));
    
        List<Match> matches = Arrays.asList(match1, match2);
    
        when(matchRepository.findMatchesWhereUserParticipated(eq(userId), any(Pageable.class)))
            .thenReturn(matches);
    
        Page<MatchDTO> result = matchService.findMatchesForUser(userId, page, size, onlyCreator);
    
        assertNotNull(result);
        assertEquals(2, result.getTotalElements()); 
        assertEquals(2, result.getContent().size()); 
    
        List<MatchDTO> matchDTOs = result.getContent();
        assertTrue(matchDTOs.stream().anyMatch(dto -> dto.getName().equals("Partida 1")));
        assertTrue(matchDTOs.stream().anyMatch(dto -> dto.getName().equals("Partida 2")));
    
        if (onlyCreator) {
            assertTrue(matchDTOs.stream().anyMatch(dto -> dto.getName().equals("Partida 1")));
            assertTrue(matchDTOs.stream().anyMatch(dto -> dto.getName().equals("Partida 2")));
        }
    
        verify(matchRepository, times(1)).findMatchesWhereUserParticipated(eq(userId), any(Pageable.class));
    }

    @Test
    public void testFindMatchesForUser_IsWinnerAndCreator() {

        Player winnerPlayer = new Player(u1, PlayerType.GANADOR);
        match.getPlayers().clear();
        match.getPlayers().add(winnerPlayer);
    
        when(matchRepository.findMatchesWhereUserParticipated(eq(u1.getId()), any(Pageable.class)))
                .thenReturn(List.of(match));

        Page<MatchDTO> result = matchService.findMatchesForUser(u1.getId(), 0, 10, true);
    
        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals(1, result.getContent().size(), "Debe haber 1 partida en el resultado");
    
        MatchDTO filteredMatch = result.getContent().get(0);
        assertEquals(match.getId(), filteredMatch.getId(), "El ID de la partida no coincide");
        assertEquals(u1.getUsername(), filteredMatch.getCreator(), "El creador de la partida no coincide con el ganador");
    
        verify(matchRepository, times(1)).findMatchesWhereUserParticipated(eq(u1.getId()), any(Pageable.class));
    }

    @Test
    public void testSaveMatch() {

        User creatorUser = new User();
        creatorUser.setId(1);
        creatorUser.setUsername("creator");

        Player creatorPlayer = new Player(creatorUser, PlayerType.CREADOR);

        Match match = new Match();
        match.setId(1);
        match.setName("Partida 1");
        match.setStartDate(LocalDateTime.now());
        match.setDeck(new Deck());
        match.setDiscard(new Discard());
        match.setPlayers(Arrays.asList(creatorPlayer));

        when(matchRepository.save(any(Match.class))).thenReturn(match);

        Match savedMatch = matchService.saveMatch(match);

        assertNotNull(savedMatch);

        assertEquals("Partida 1", savedMatch.getName());

        assertEquals(1, savedMatch.getId());

        verify(matchRepository, times(1)).save(match);
    }

    @Test
    public void testFindMatchesByUser() {

        User user = new User();
        user.setId(1);
        user.setUsername("creator");

        Player player1 = new Player(user, PlayerType.CREADOR);
        Player player2 = new Player(new User(), PlayerType.PARTICIPANTE);

        Match match1 = new Match();
        match1.setId(1);
        match1.setName("Partida 1");
        match1.setStartDate(LocalDateTime.now());
        match1.setDeck(new Deck());
        match1.setDiscard(new Discard());
        match1.setPlayers(Arrays.asList(player1, player2));

        Match match2 = new Match();
        match2.setId(2);
        match2.setName("Partida 2");
        match2.setStartDate(LocalDateTime.now());
        match2.setDeck(new Deck());
        match2.setDiscard(new Discard());
        match2.setPlayers(Arrays.asList(player1)); 

        List<Match> matches = Arrays.asList(match1, match2);

        when(matchRepository.findByPlayers_User(eq(user))).thenReturn(matches);

        List<Match> result = matchService.findMatchesByUser(user);

        assertNotNull(result);
        assertEquals(2, result.size());

        assertTrue(result.stream().anyMatch(m -> m.getId() == 1));
        assertTrue(result.stream().anyMatch(m -> m.getId() == 2));

        verify(matchRepository, times(1)).findByPlayers_User(eq(user));
    }

    //Test end2end para comprobar que un jugador crea una partida y otro se une a ella
    @Test
    public void testCreateMatchAndJoin() {

        User creatorUser = new User();
        creatorUser.setId(1);
        creatorUser.setUsername("player1");
        creatorUser.setPassword("password");
        
        Deck deck = new Deck();
        Discard discard = new Discard();
        
        List<Player> initialPlayers = new ArrayList<>();
        Player player1 = new Player(creatorUser, PlayerType.CREADOR);
        initialPlayers.add(player1);
    
        match = new Match("Test Match", deck, discard, initialPlayers);
    
        assertNotNull(match);
        assertEquals("Test Match", match.getName());
        assertNotNull(match.getPlayers());
        assertEquals(1, match.getPlayers().size());
        
        User anotherUser = new User();
        anotherUser.setId(2);
        anotherUser.setUsername("player2");
        anotherUser.setPassword("password");
        
        Player player2 = new Player(anotherUser, PlayerType.PARTICIPANTE);
        
        match.addPlayer(player2);
        
        assertNotNull(match);
        assertNotNull(match.getPlayers());
        assertEquals(2, match.getPlayers().size());
    }

    @Test
    void testTogglePlayerRole_Success() {
        // Configuración inicial
        Player player = new Player();
        player.setUser(u1);
        player.setRole(PlayerType.PARTICIPANTE);

        match.setPlayers(List.of(player));

        // Mock del comportamiento de los servicios
        when(matchRepository.findById(match.getId())).thenReturn(Optional.of(match));
        when(playerService.findPlayerAssociatedToMatch(u1, match)).thenReturn(player);

        // Llamada al método a probar
        matchService.togglePlayerRole(match.getId(), u1);

        // Verificaciones
        assertEquals(PlayerType.ESPECTADOR, player.getRole(), "El rol del jugador debe cambiar a ESPECTADOR");
        verify(playerService, times(1)).savePlayer(player);
    }

    @Test
    void testTogglePlayerRole_ToggleBackToParticipant() {
        // Configuración inicial
        Player player = new Player();
        player.setUser(u1);
        player.setRole(PlayerType.ESPECTADOR);

        match.setPlayers(List.of(player));

        // Mock del comportamiento de los servicios
        when(matchRepository.findById(match.getId())).thenReturn(Optional.of(match));
        when(playerService.findPlayerAssociatedToMatch(u1, match)).thenReturn(player);

        // Llamada al método a probar
        matchService.togglePlayerRole(match.getId(), u1);

        // Verificaciones
        assertEquals(PlayerType.PARTICIPANTE, player.getRole(), "El rol del jugador debe cambiar a PARTICIPANTE");
        verify(playerService, times(1)).savePlayer(player);
    }

    @Test
    void testTogglePlayerRole_InvalidRole() {
        // Configuración inicial
        Player player = new Player();
        player.setUser(u1);
        player.setRole(PlayerType.CREADOR); // Rol no permitido para cambiar

        match.setPlayers(List.of(player));

        // Mock del comportamiento de los servicios
        when(matchRepository.findById(match.getId())).thenReturn(Optional.of(match));
        when(playerService.findPlayerAssociatedToMatch(u1, match)).thenReturn(player);

        // Llamada al método a probar y verificación de excepción
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                matchService.togglePlayerRole(match.getId(), u1)
        );

        assertEquals("El rol actual no permite cambiar.", exception.getMessage());
        verify(playerService, never()).savePlayer(any());
    }

    @Test
    void testTogglePlayerRole_MatchNotFound() {
        // Mock del comportamiento cuando no se encuentra la partida
        when(matchRepository.findById(match.getId())).thenReturn(Optional.empty());

        // Llamada al método a probar y verificación de excepción
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                matchService.togglePlayerRole(match.getId(), u1)
        );

        assertEquals("Match not found with id: '" + match.getId() + "'", exception.getMessage());
        verify(playerService, never()).savePlayer(any());
    }

    @Test
    public void testStartMatchThrowsWhenSpectatorNotFriends() {
        // Configuración de usuarios
        User creator = new User();
        creator.setId(1);
        creator.setUsername("creator");

        User spectator = new User();
        spectator.setId(2);
        spectator.setUsername("spectator1");

        User participant = new User();
        participant.setId(3);
        participant.setUsername("participant1");

        User participant2 = new User();
        participant2.setId(4);
        participant2.setUsername("participant2");

        // Configuración de jugadores
        Player creatorPlayer = new Player(creator, PlayerType.CREADOR);
        Player spectatorPlayer = new Player(spectator, PlayerType.ESPECTADOR);
        Player participantPlayer = new Player(participant, PlayerType.PARTICIPANTE);
        Player participantPlayer2 = new Player(participant2, PlayerType.PARTICIPANTE);

        // Crear un Match sobrescribiendo métodos para pasar validaciones de dimensiones
        Match customMatch = new Match();
        customMatch.setId(1);
        customMatch.setDeck(deck);
        customMatch.setDiscard(discard);
        customMatch.setPlayers(List.of(creatorPlayer, spectatorPlayer, participantPlayer, participantPlayer2));
        customMatch.setStartDate(null);
        customMatch.setEndDate(null);

        // Simular la recuperación del match desde el repositorio
        when(matchRepository.findById(1)).thenReturn(Optional.of(customMatch));

        // Configurar el servicio de amistad
        when(friendshipService.areFriends(spectator.getId(), creator.getId())).thenReturn(false);
        when(friendshipService.areFriends(spectator.getId(), participant.getId())).thenReturn(true);
        when(friendshipService.areFriends(spectator.getId(), participant2.getId())).thenReturn(true);

        // Invocar el método y verificar que se lanza la excepción esperada
        NotAllSpectatorsAreFriendsException exception = assertThrows(
            NotAllSpectatorsAreFriendsException.class,
            () -> matchService.startMatch(1, creator.getId())
        );

        // Verificar que el mensaje de la excepción contiene el nombre del espectador problemático
        assertTrue(exception.getMessage().contains("spectator1"));
    }

    @Test
    public void testStartMatchThrowsWhenSpectatorAreFriends() {
        // Configuración de usuarios
        User creator = new User();
        creator.setId(1);
        creator.setUsername("creator");

        User spectator = new User();
        spectator.setId(2);
        spectator.setUsername("spectator1");

        User participant = new User();
        participant.setId(3);
        participant.setUsername("participant1");

        User participant2 = new User();
        participant2.setId(4);
        participant2.setUsername("participant2");

        // Configuración de jugadores
        Player creatorPlayer = new Player(creator, PlayerType.CREADOR);
        Player spectatorPlayer = new Player(spectator, PlayerType.ESPECTADOR);
        Player participantPlayer = new Player(participant, PlayerType.PARTICIPANTE);
        Player participantPlayer2 = new Player(participant2, PlayerType.PARTICIPANTE);

        // Crear un Match sobrescribiendo métodos para pasar validaciones de dimensiones
        Match customMatch = new Match();
        customMatch.setId(1);
        customMatch.setDeck(deck);
        customMatch.setDiscard(discard);
        customMatch.setPlayers(List.of(creatorPlayer, spectatorPlayer, participantPlayer, participantPlayer2));
        customMatch.setStartDate(null);
        customMatch.setEndDate(null);

        // Simular la recuperación del match desde el repositorio
        when(matchRepository.findById(1)).thenReturn(Optional.of(customMatch));

        // Configurar el servicio de amistad
        when(friendshipService.areFriends(spectator.getId(), creator.getId())).thenReturn(true);
        when(friendshipService.areFriends(spectator.getId(), participant.getId())).thenReturn(true);
        when(friendshipService.areFriends(spectator.getId(), participant2.getId())).thenReturn(true);

        doNothing().when(turnServiceMock).firstTurn(any(Match.class));

        Match startedMatch = matchService.startMatch(1, creator.getId());
    
        // Verificar que la partida se inició correctamente
        assertNotNull(startedMatch.getStartDate(), "La fecha de inicio debe establecerse");
        assertTrue(startedMatch.isInProgress(), "La partida debe estar en progreso");
    
        // Verificar que el primer turno se inició
        verify(turnServiceMock, times(1)).firstTurn(any(Match.class));
    }

}
