package es.us.dp1.l2_05_24_25.fantasy_realms.turn;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.deck.Deck;
import es.us.dp1.l2_05_24_25.fantasy_realms.discard.Discard;
import es.us.dp1.l2_05_24_25.fantasy_realms.dto.DecisionDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.MatchStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.TurnStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModService;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerType;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;

@ExtendWith(SpringExtension.class)
class TurnServiceTests {

    protected TurnService turnService;

    @Mock
    protected ModService modService;

    private Match match;
    private Player player1;
    private Player player2;
    private Player player3;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {
        
        turnService = new TurnService(modService);

        Deck deck = new Deck(List.of());
       
        Discard discard = new Discard();

        // 2. Creamos usuarios de prueba
        user1 = new User();
        user1.setId(1);
        user1.setUsername("testUser");

        user2 = new User();
        user2.setId(2);
        user2.setUsername("testUser2");

        user3 = new User();
        user3.setId(3);
        user3.setUsername("testUser3");

        // 3. Creamos jugadores de prueba
        player1 = new Player(user1, PlayerType.CREADOR);
        player2 = new Player(user2, PlayerType.PARTICIPANTE);
        player3 = new Player(user3, PlayerType.PARTICIPANTE);
        
        // 4. Crear un Match sencillo

        List<Player> players = List.of(player1,player2,player3);

        match = spy(new Match("Test Match", deck, discard, players));

        // 5. Asociar un turno

        Turn turn = new Turn(player1,1);
        match.setCurrentTurn(turn);

    }

    // 1) Test de firstTurn()

    @Test
    void testFirstTurn() {

        match.setCurrentTurn(null);

        match.setPlayers(List.of(player1)); // Solo un jugador no espectador en la partida

        turnService.firstTurn(match);
        
        Turn currentTurn = match.getCurrentTurn();
        assertNotNull(currentTurn, "El turno inicial no debería ser nulo");
        assertEquals(1, currentTurn.getTurnCount(), "El turno inicial debe ser 1");
        assertEquals(player1, currentTurn.getPlayer(), "El jugador inicial debe ser 'player1'");
    }
    // 2) Test cancelTurn() => notInProgress

    @Test
    void testCancelTurnNotInProgress() {
        // Sin startDate => isInProgress() == false
        // Llamar a turnService.cancelTurn(...) => debe lanzar MatchStatesException
        assertThrows(MatchStatesException.class, () -> {
            turnService.cancelTurn(user1, match);
        });
    }

    // 3) Test cancelTurn() => el usuario actual no tiene el turno

    @Test
    void testCancelTurnPlayerHasNotTheTurn() {

        // El turno actual lo tiene player1 => user1

        // Si llamamos con user2, debe dar TurnStatesException

        assertThrows(TurnStatesException.class, () -> {
            turnService.cancelTurn(user2, match);
        }); 

    }

    // 3) Test cancelTurn() => en progreso y sin acciones que revertir

    @Test
    void testCancelTurnNoActionsToRevert() {

        // Ponemos la partida en progreso
        match.setStartDate(LocalDateTime.now());

        // EL turno actual no tiene acciones que revertir => DrawSource = NONE

        // Llamamos
        turnService.cancelTurn(user1, match);

        // Verificamos que se ha realizado nextTurn

        Turn newTurn = match.getCurrentTurn();
        assertNotNull(newTurn);
        
        // Ahora el turno debe pertenecer a player2 de forma circular

        assertEquals(player2, newTurn.getPlayer());
        assertEquals(2, newTurn.getTurnCount()); 
    }

    // 4) Test cancelTurn() => en progreso y con acciones que revertir desde el mazo

    @Test
    void testCancelTurnHasActionsToRevertDeck() {
        match.setStartDate(LocalDateTime.now()); // en progreso

        // Turn con drawSource != NONE
        
        Card cardDrawn = new Card();
        cardDrawn.setId(5);
        match.getCurrentTurn().drawFromDeck(cardDrawn); 

        // Ahora hasActionsToRevert() => true porque drawSource != NONE && !discarded

        // Añadimos la carta a la mano del player
        player1.setPlayerHand(new ArrayList<>());
        player1.getPlayerHand().add(cardDrawn);

        // Llamamos
        turnService.cancelTurn(user1, match);

        // Verificar se revirtieron acciones => la carta se eliminó de la mano del player
        assertFalse(player1.getPlayerHand().contains(cardDrawn), "La carta debe haberse removido de la mano");
        assertTrue(match.getDeck().getCards().size() == 1);
        // Se calcula el siguiente turno
        Turn newTurn = match.getCurrentTurn();
        assertEquals(2, newTurn.getTurnCount());
        assertEquals(player2, newTurn.getPlayer());
    }

    // 5) Test cancelTurn() => en progreso y con acciones que revertir desde descartes

    @Test
    void testCancelTurnHasActionsToRevertDiscard() {
        match.setStartDate(LocalDateTime.now()); // en progreso

        // Turn con drawSource != NONE
        
        Card cardDrawn = new Card();
        cardDrawn.setId(5);
        match.getCurrentTurn().drawFromDiscard(cardDrawn); 

        // Ahora hasActionsToRevert() => true porque drawSource != NONE && !discarded

        // Añadimos la carta a la mano del player
        player1.setPlayerHand(new ArrayList<>());
        player1.getPlayerHand().add(cardDrawn);

        // Llamamos
        turnService.cancelTurn(user1, match);

        // Verificar se revirtieron acciones => la carta se eliminço de la mano del player
        assertFalse(player1.getPlayerHand().contains(cardDrawn), "La carta debe haberse removido de la mano");
        assertTrue(match.getDiscard().getCards().size() == 1);
        // Se calcula el siguiente turno
        Turn newTurn = match.getCurrentTurn();
        assertEquals(2, newTurn.getTurnCount());
        assertEquals(player2, newTurn.getPlayer());
    }

    // 6) Test specialTurn => normal flow

    @Test
    void testSpecialTurnNormalFlow() {
        // In scoring phase 
        match.setInScoringPhase(true);

        // MaxSpecialTurn = 5 => no es el último => => nextTurn
        doReturn(4).when(match).getMaxSpecialTurn();

        // Pasar al siguiente turno

        Turn turn = new Turn(player2,2);
        match.setCurrentTurn(turn);

        List<DecisionDTO> dynamicDecisions = new ArrayList<>();

        boolean result = turnService.specialTurn(user2, match, dynamicDecisions);
        assertFalse(result, "No debe finalizar la partida si el turnoCount (2) != maxSpecialTurn(4)");

        // Verificar applyMods
        verify(modService).convertToDecisionMap(anyList(), anyList());
        verify(modService).applyMods(anyList(), anyList(), anyMap());
    }

    // 7) Test specialTurn => último turno => finaliza

    @Test
    void testSpecialTurnLastTurn() {
        match.setInScoringPhase(true);

        doReturn(4).when(match).getMaxSpecialTurn();

        match.setCurrentTurn(new Turn(player2, 4));

        boolean result = turnService.specialTurn(user2, match, new ArrayList<>());
        assertTrue(result, "Si turnCount = maxSpecialTurn => finaliza la partida");
    }

    // 8) testSpecialTurn => no en scoring => error

    @Test
    void testSpecialTurnNotInScoringPhase() {
        match.setInScoringPhase(false);
        
        doReturn(4).when(match).getMaxSpecialTurn();

        assertThrows(MatchStatesException.class, () -> {
            turnService.specialTurn(user1, match, new ArrayList<>());
        });
    }

    // 9) testSpecialTurn => user no es el jugador actual => TurnStatesException

    @Test
    void testSpecialTurnUserHasNotTurn() {
        match.setInScoringPhase(true);

        doReturn(4).when(match).getMaxSpecialTurn();

        assertThrows(TurnStatesException.class, () -> {
            turnService.specialTurn(user2, match, new ArrayList<>());
        });
    }

}