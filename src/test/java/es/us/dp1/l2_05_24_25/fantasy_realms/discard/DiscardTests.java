package es.us.dp1.l2_05_24_25.fantasy_realms.discard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.discard.Discard;

public class DiscardTests {
    
    private Discard discard;

    @BeforeEach
    void setUp() {
        discard = new Discard();
    }

    @Test
    void testAddCardDiscard_ShouldAddCardToDiscard() {
        // Arrange
        Card card = new Card();

        // Act
        discard.addCardDiscard(card);

        // Assert
        assertTrue(discard.getCards().contains(card), "La carta debería estar en la zona de descarte.");
        assertEquals(1, discard.getCards().size(), "El tamaño de la lista de cartas debería ser 1.");
    }

    @Test
    void testRemoveCard_ShouldRemoveCardFromDiscard() {
        // Arrange
        Card card1 = new Card();
        Card card2 = new Card();
      
        discard.addCardDiscard(card1);
        discard.addCardDiscard(card2);

        // Act
        boolean result = discard.removeCard(card1);

        // Assert
        assertTrue(result, "El método debería devolver true cuando se elimina la carta.");
        assertFalse(discard.getCards().contains(card1), "La carta debería haber sido eliminada del descarte.");
        assertEquals(1, discard.getCards().size(), "El tamaño de la lista de cartas debería ser 1.");
    }

    @Test
    void testRemoveCard_ShouldReturnFalseWhenCardNotInDiscard() {
        // Arrange
        Card card = new Card();

        // Act
        boolean result = discard.removeCard(card);

        // Assert
        assertFalse(result, "El método debería devolver false cuando la carta no está en el descarte.");
        assertEquals(0, discard.getCards().size(), "El tamaño de la lista de cartas debería ser 0.");
    }

    @Test
    void testConstructor_ShouldInitializeEmptyDiscard() {
        // Assert
        assertNotNull(discard.getCards(), "La lista de cartas no debería ser null.");
        assertTrue(discard.getCards().isEmpty(), "La lista de cartas debería estar vacía inicialmente.");
    }

    @Test
    void testAddCardDiscard_ShouldAllowMultipleCards() {
        // Arrange
        Card card1 = new Card();
        Card card2 = new Card();

        // Act
        discard.addCardDiscard(card1);
        discard.addCardDiscard(card2);

        // Assert
        List<Card> cards = discard.getCards();
        assertEquals(2, cards.size(), "El tamaño de la lista de cartas debería ser 2.");
        assertTrue(cards.contains(card1), "La lista debería contener la primera carta.");
        assertTrue(cards.contains(card2), "La lista debería contener la segunda carta.");
    }
}
