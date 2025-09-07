package es.us.dp1.l2_05_24_25.fantasy_realms.deck;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.TestMod;

@ExtendWith(SpringExtension.class)
public class DeckServiceTest {

    @MockBean
    private DeckRepository deckRepository;

    protected DeckService deckService;

    private Deck deck;
    private Card card;
    private List<Card> cards;

    @BeforeEach
    public void setUp() {

        deckService = new DeckService(deckRepository);
        
        // Crear una carta de prueba
        card = new Card();
        card.setId(1);
        card.setName("Card1");
        
        // Crear una lista de cartas
        cards = new ArrayList<>();
        cards.add(card);
        
        // Crear un mazo con una carta
        deck = new Deck(cards);
    }

    @Test
    void testGetShuffledDeckForMatch() {

        Deck mockDeck = new Deck();
        List<Card> cards = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Card card = new Card("Card" + i, CardType.BESTIA, i);
            card.setMods(new ArrayList<>()); // Inicializar mods como lista vacía
            cards.add(card);
        }
        mockDeck.setCards(cards);
    
        when(deckRepository.findById(1)).thenReturn(Optional.of(mockDeck));
    

        Deck shuffledDeck = deckService.getShuffledDeckForMatch();
    

        assertNotNull(shuffledDeck);
        assertEquals(10, shuffledDeck.getCards().size());
        verify(deckRepository, times(1)).findById(1);
    }

    @Test
    void testGetShuffledDeckForMatch_ThrowsResourceNotFoundException() {

        when(deckRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> deckService.getShuffledDeckForMatch());
        verify(deckRepository, times(1)).findById(1);
    }

    @Test
    public void testFindDeckByIdValid() {
        // Simula que el mazo con ID 1 existe
        when(deckRepository.findById(1)).thenReturn(Optional.of(deck));
        
        // Verificar que el mazo es devuelto correctamente
        Deck foundDeck = deckService.findDeckById(1);
        
        assertNotNull(foundDeck);
        assertEquals(deck, foundDeck);
        verify(deckRepository, times(1)).findById(1);
    }

    @Test
    public void testFindDeckByIdNotFound() {
        // Simula que el mazo con ID 99 no existe
        when(deckRepository.findById(99)).thenReturn(Optional.empty());
        
        // Verificar que se lanza una excepción
        assertThrows(ResourceNotFoundException.class, () -> deckService.findDeckById(99));
        verify(deckRepository, times(1)).findById(99);
    }

    @Test
    public void testDrawCard() {
        // Simula que el mazo con ID 1 existe y tiene cartas
        when(deckRepository.findById(1)).thenReturn(Optional.of(deck));
        
        // Extraer una carta
        Card drawnCard = deckService.drawCard(1);
        
        assertNotNull(drawnCard);
        assertEquals(card, drawnCard);
        assertEquals(0, deck.getCards().size());  // El mazo debe estar vacío después de extraer la carta
        verify(deckRepository, times(1)).findById(1);
    }

    @Test
    public void testIsDeckEmptyTrue() {
        // Simula que el mazo está vacío
        when(deckRepository.findById(1)).thenReturn(Optional.of(new Deck(new ArrayList<>())));
        
        assertTrue(deckService.isDeckEmpty(1));
        verify(deckRepository, times(1)).findById(1);
    }

    @Test
    public void testIsDeckEmptyFalse() {
        // Simula que el mazo tiene cartas
        when(deckRepository.findById(1)).thenReturn(Optional.of(deck));
        
        assertFalse(deckService.isDeckEmpty(1));
        verify(deckRepository, times(1)).findById(1);
    }

    @Test
    void testDeepCloneAndShuffle() {

        Deck mockDeck = new Deck();
        List<Card> cards = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Card card = new Card("Card" + i, CardType.BESTIA, i);
            card.setMods(new ArrayList<>()); // Inicializar la lista de mods como vacía
            cards.add(card);
        }
        mockDeck.setCards(cards);

        Deck clonedDeck = deckService.deepCloneAndShuffle(mockDeck);

        assertNotNull(clonedDeck);
        assertEquals(10, clonedDeck.getCards().size());
        assertNotEquals(mockDeck.getCards(), clonedDeck.getCards());
    }

@Test
void testDeepCloneCardsWithMods() {
    // Crear cartas originales con modificadores
    Card originalCard1 = new Card("Card3", CardType.BESTIA, 10);
    Card originalCard2 = new Card("Card2", CardType.BESTIA, 20);

    Mod mod1 = new TestMod("Mod1", 1, 2, originalCard1, List.of(originalCard2), ModType.BONUS);
    Mod mod2 = new TestMod("Mod2", 3, 4, originalCard2, List.of(originalCard1), ModType.BONUS);

    originalCard1.setMods(new ArrayList<>(List.of(mod1)));
    originalCard2.setMods(new ArrayList<>(List.of(mod2)));

    List<Card> originalCards = List.of(originalCard1, originalCard2);

    // Llamar al método a probar
    List<Card> clonedCards = deckService.deepCloneCards(originalCards);

    // Verificaciones
    assertNotNull(clonedCards, "La lista de cartas clonadas no debe ser nula.");
    assertEquals(2, clonedCards.size(), "La lista de cartas clonadas debe tener el mismo tamaño que la original.");

    // Mapear los clones para evitar problemas de orden
    Map<String, Card> clonedCardsMap = clonedCards.stream()
        .collect(Collectors.toMap(Card::getName, card -> card));

    Card clonedCard1 = clonedCardsMap.get("Card3");
    Card clonedCard2 = clonedCardsMap.get("Card2");

    assertNotNull(clonedCard1, "Card3 clonada no encontrada.");
    assertNotNull(clonedCard2, "Card2 clonada no encontrada.");

    // Verificar que las cartas clonadas no sean las mismas instancias
    assertNotSame(originalCard1, clonedCard1);
    assertNotSame(originalCard2, clonedCard2);

    // Verificar que los nombres y valores se mantengan iguales
    assertEquals(originalCard1.getName(), clonedCard1.getName());
    assertEquals(originalCard2.getName(), clonedCard2.getName());


    // Verificar que los modificadores se hayan clonado
    assertNotNull(clonedCard1.getMods());
    assertNotNull(clonedCard2.getMods());
    assertEquals(1, clonedCard1.getMods().size());
    assertEquals(1, clonedCard2.getMods().size());

    Mod clonedMod1 = clonedCard1.getMods().get(0);
    Mod clonedMod2 = clonedCard2.getMods().get(0);

    // Verificar que los modificadores sean instancias diferentes a los originales
    assertNotSame(mod1, clonedMod1);
    assertNotSame(mod2, clonedMod2);

    // Verificar que los modificadores apunten a las cartas clonadas correspondientes
    assertSame(clonedCard2, clonedMod1.getTarget().get(0));
    assertSame(clonedCard1, clonedMod2.getTarget().get(0));

}


    @Test
    public void testDrawMultiple() {
        // Simula un mazo con 10 cartas
        Deck deck = new Deck();
        List<Card> cards = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            cards.add(new Card("Card " + i, CardType.ARMA, i)); // Crea cartas con nombres, tipo y valores únicos
        }
        deck.setCards(cards);
    
        // Extraer 7 cartas
        List<Card> drawnCards = deckService.drawMultiple(deck);
    
        // Validar que las 7 primeras cartas se han extraído correctamente
        assertNotNull(drawnCards);
        assertEquals(7, drawnCards.size());
        for (int i = 0; i < 7; i++) {
            assertEquals("Card " + (i + 1), drawnCards.get(i).getName());
        }
    
        // Validar que el mazo tiene las 3 cartas restantes
        assertEquals(3, deck.getCards().size());
        for (int i = 0; i < 3; i++) {
            assertEquals("Card " + (i + 8), deck.getCards().get(i).getName());
        }
    }

}
