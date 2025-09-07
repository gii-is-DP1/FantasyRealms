package es.us.dp1.l2_05_24_25.fantasy_realms.card;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CardServiceTests {

    @Mock
    private CardRepository cardRepository;

    protected CardService cardService;

    private Card card1;
    private Card card2;

    @BeforeEach
    public void setUp() {
        
        cardService = new CardService(cardRepository);

        card1 = new Card("Card1", CardType.MAGO, 10);
        card2 = new Card("Card2", CardType.EJERCITO, 20);
    }

    @Test
    public void testFindAll() {
        when(cardRepository.findAll()).thenReturn(Arrays.asList(card1, card2));

        Iterable<Card> cards = cardService.findAll();

        assertNotNull(cards);
        assertEquals(2, ((List<Card>) cards).size());
        verify(cardRepository, times(1)).findAll();
    }

    @Test
    public void testFindCardTypes() {
        CardType[] cardTypes = cardService.findCardTypes();

        assertNotNull(cardTypes);
        assertEquals(CardType.values().length, cardTypes.length);
    }

    @Test
    public void testFindCardsByType() {
        when(cardRepository.findByCardType(CardType.MAGO)).thenReturn(Arrays.asList(card1));

        List<Card> cards = cardService.findCardsByType(CardType.MAGO);

        assertNotNull(cards);
        assertEquals(1, cards.size());
        verify(cardRepository, times(1)).findByCardType(CardType.MAGO);
    }

    @Test
    public void testSaveCard() {
        when(cardRepository.save(card1)).thenReturn(card1);

        Card savedCard = cardService.saveCard(card1);

        assertNotNull(savedCard);
        assertEquals(card1.getName(), savedCard.getName());
        verify(cardRepository, times(1)).save(card1);
    }

    @Test
    public void testDeleteCardById() {

        Card mockCard = new Card();
        mockCard.setId(1);
    

        when(cardRepository.findById(1)).thenReturn(Optional.of(mockCard));
    

        doNothing().when(cardRepository).delete(mockCard);
    

        cardService.deleteCardById(1);
    

        verify(cardRepository, times(1)).findById(1);
    

        verify(cardRepository, times(1)).delete(mockCard);
    }

    @Test
    public void testFindCardById() {
        when(cardRepository.findById(1)).thenReturn(Optional.of(card1));

        Card foundCard = cardService.findCardById(1);

        assertNotNull(foundCard);
        assertEquals(card1.getName(), foundCard.getName());
        verify(cardRepository, times(1)).findById(1);
    }

    @Test
    public void testFindCardById_NotFound() {
        when(cardRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            cardService.findCardById(1);
        });

        verify(cardRepository, times(1)).findById(1);
    }
}
