package es.us.dp1.l2_05_24_25.fantasy_realms.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.deck.Deck;
import es.us.dp1.l2_05_24_25.fantasy_realms.dto.DeckDTO;

public class DeckDTOTests {
    @Test
    public void testNoArgsConstructor() {
        DeckDTO deckDTO = new DeckDTO();
        assertNull(deckDTO.getCards());
        assertNull(deckDTO.getInitialCards());
    }

    @Test
    public void testConstructorWithDeck() {
        // Creación de un Deck de ejemplo
        Card card1 = new Card("Card1", CardType.MAGO, 10);
        Card card2 = new Card("Card2", CardType.BESTIA, 15);
        List<Card> cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);
        
        Deck deck = new Deck(cards);

        // Creación del DeckDTO a partir del Deck
        DeckDTO deckDTO = new DeckDTO(deck);

        // Comprobaciones
        assertNotNull(deckDTO.getCards());
        assertEquals(2, deckDTO.getCards().size());
        assertEquals("Card1", deckDTO.getCards().get(0).getName());
        assertEquals("Card2", deckDTO.getCards().get(1).getName());

        assertNotNull(deckDTO.getInitialCards());
        assertEquals(2, deckDTO.getInitialCards().size());
        assertEquals("Card1", deckDTO.getInitialCards().get(0).getName());
        assertEquals("Card2", deckDTO.getInitialCards().get(1).getName());
    }

    @Test
    public void testConstructorWithEmptyDeck() {
        // Creación de un Deck vacío
        Deck deck = new Deck(new ArrayList<>());

        // Creación del DeckDTO a partir del Deck vacío
        DeckDTO deckDTO = new DeckDTO(deck);

        // Comprobaciones
        assertNotNull(deckDTO.getCards());
        assertTrue(deckDTO.getCards().isEmpty());
        
        assertNotNull(deckDTO.getInitialCards());
        assertTrue(deckDTO.getInitialCards().isEmpty());
    }

    @Test
    public void testDeckDTOCardConsistency() {
        // Creación de un Deck con una carta
        Card card1 = new Card("Card1", CardType.MAGO, 10);
        List<Card> cards = new ArrayList<>();
        cards.add(card1);

        Deck deck = new Deck(cards);

        // Creación del DeckDTO a partir del Deck
        DeckDTO deckDTO = new DeckDTO(deck);

        // Verificar que ambas listas tengan la misma carta y el mismo tamaño
        assertEquals(deckDTO.getCards().size(), deckDTO.getInitialCards().size());
        assertEquals(deckDTO.getCards().get(0).getName(), deckDTO.getInitialCards().get(0).getName());
    }

    @Test
    public void testDeckDTOEmptyConstructor() {
        // Usamos el constructor vacío
        DeckDTO deckDTO = new DeckDTO();
        
        // Las listas deben ser nulas al principio
        assertNull(deckDTO.getCards());
        assertNull(deckDTO.getInitialCards());
    }
}
