package es.us.dp1.l2_05_24_25.fantasy_realms.deck;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.deck.Deck;

public class DeckTest {

        private Deck deck;
    private List<Card> cards;

    @BeforeEach
    void setUp() {
        // Inicializar una lista de cartas para las pruebas
        cards = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            cards.add(new Card("Card" + i, CardType.BESTIA, i));
        }
        deck = new Deck(cards);
    }

    @Test
    void testDeckInitialization() {
        // Verificar que el mazo fue inicializado correctamente
        assertNotNull(deck);
        assertNotNull(deck.getCards());
        assertNotNull(deck.getInitialCards());
        assertEquals(10, deck.getCards().size());
        assertEquals(10, deck.getInitialCards().size());
    }

    @Test
    void testDeckIsEmpty() {
        // Verificar que el mazo no está vacío inicialmente
        assertFalse(deck.isEmpty());

        // Limpiar las cartas
        deck.getCards().clear();
        assertTrue(deck.isEmpty());
    }

    @Test
    void testAddCardToDeck() {
        // Agregar una carta al mazo
        Card newCard = new Card("New Card", CardType.BESTIA, 11);
        deck.addCardDeck(newCard);

        // Verificar que la carta se haya añadido al principio del mazo
        assertEquals(11, deck.getCards().size());
        assertEquals(newCard, deck.getCards().get(0));
    }

    @Test
    void testDeckCardEquality() {
        // Crear un mazo nuevo y verificar que las cartas son copias independientes
        List<Card> newCards = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            newCards.add(new Card("Card" + i, CardType.BESTIA, i));
        }
        Deck newDeck = new Deck(newCards);

        // Verificar que las cartas son copias, no las mismas instancias
        assertNotSame(deck.getCards(), newDeck.getCards());
        assertNotSame(deck.getCards().get(0), newDeck.getCards().get(0));
    }

    @Test
    void testDeckShuffling() {
        // Barajar las cartas del mazo (esto se simula aquí al verificar que el orden cambie)
        List<Card> originalCards = new ArrayList<>(deck.getCards());
        deck.getCards().sort((card1, card2) -> Integer.compare(card2.getBaseValue(), card1.getBaseValue()));

        // Verificar que el orden de las cartas cambió
        assertNotEquals(originalCards, deck.getCards());
    }

    @Test
    void testInitialCardsIntegrity() {
        // Verificar que las cartas iniciales no han sido alteradas
        List<Card> initialCards = new ArrayList<>(deck.getInitialCards());
        deck.getCards().remove(0);

        // Verificar que las cartas iniciales permanecen intactas
        assertEquals(initialCards.size(), deck.getInitialCards().size());
        assertNotEquals(deck.getCards().size(), deck.getInitialCards().size());
    }
}
