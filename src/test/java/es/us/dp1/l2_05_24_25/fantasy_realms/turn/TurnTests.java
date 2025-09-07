package es.us.dp1.l2_05_24_25.fantasy_realms.turn;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.turn.DrawEnum;
import es.us.dp1.l2_05_24_25.fantasy_realms.turn.Turn;

public class TurnTests {

    private Turn turn;
    private Player mockPlayer;
    private Card mockCard;

    @BeforeEach
    void setUp() {
        // Mocks de Player y Card
        mockPlayer = mock(Player.class);
        mockCard = mock(Card.class);

        // Iniciamos un Turn con turnCount = 1
        turn = new Turn(mockPlayer, 1);
    }

    @Test
    void testConstructor() {
        // Verificamos que el constructor establece correctamente player y turnCount
        assertEquals(mockPlayer, turn.getPlayer());
        assertEquals(1, turn.getTurnCount());
        assertFalse(turn.hasDiscarded()); // valor por defecto false
        assertEquals(DrawEnum.NONE, turn.getDrawSource()); // valor por defecto
        assertNull(turn.getCardDrawn());
    }

    @Test
    void testCanDrawCard() {
        // Por defecto drawSource = NONE, canDrawCard() debe ser true
        assertTrue(turn.canDrawCard());

        // Simulamos que el jugador ya robó del mazo
        turn.drawFromDeck(mockCard);
        assertFalse(turn.canDrawCard());
    }

    @Test
    void testHasDrawnCard() {
        // Por defecto, drawSource = NONE -> no ha robado carta
        assertFalse(turn.hasDrawnCard());

        // Cuando el jugador roba de la baraja
        turn.drawFromDeck(mockCard);
        assertTrue(turn.hasDrawnCard());
        
        // Cuando el jugador roba de la pila de descartes
        turn.drawFromDiscard(mockCard);
        assertTrue(turn.hasDrawnCard());
    }

    @Test
    void testHasDiscarded() {

        // Test poco necesario pero nos sirve para ganar algo de cobertura
        
        // Por defecto es false
        assertFalse(turn.hasDiscarded());

        // Marcamos el turno como descartado
        turn.setDiscarded(true);
        assertTrue(turn.hasDiscarded());

        // Volver a false
        turn.setDiscarded(false);
        assertFalse(turn.hasDiscarded());
    }

    @Test
    void testHasActionsToRevert() {
        // Por defecto drawSource=NONE y discarded=false -> no hay acciones a revertir
        assertFalse(turn.hasActionsToRevert());

        // Si roba carta y no descarta, drawSource != NONE y discarded=false
        turn.drawFromDeck(mockCard);
        assertTrue(turn.hasActionsToRevert());

        // Si marca discarded = true, ya no hay acciones por revertir
        turn.setDiscarded(true);
        assertFalse(turn.hasActionsToRevert());
    }

    @Test
    void testDrawFromDeck() {
        // Llamamos a drawFromDeck
        turn.drawFromDeck(mockCard);

        // Verificamos que la carta se establece como cardDrawn
        assertEquals(mockCard, turn.getCardDrawn());
        // Y drawSource pasa a DECK
        assertEquals(DrawEnum.DECK, turn.getDrawSource());
    }

    @Test
    void testDrawFromDiscard() {
        turn.drawFromDiscard(mockCard);

        assertEquals(mockCard, turn.getCardDrawn());
        assertEquals(DrawEnum.DISCARD, turn.getDrawSource());
    }
}

