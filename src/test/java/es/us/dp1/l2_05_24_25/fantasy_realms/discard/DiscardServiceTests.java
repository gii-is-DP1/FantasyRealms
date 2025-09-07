package es.us.dp1.l2_05_24_25.fantasy_realms.discard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardService;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;

@ExtendWith(MockitoExtension.class)
public class DiscardServiceTests {

    @Mock
    private DiscardRepository discardRepository;

    @Mock
    private CardService cardService;

    protected DiscardService discardService;

    @BeforeEach
    void setUp() {
        discardService = new DiscardService(discardRepository, cardService);
    }

    @Test
    void testFindDiscardById_ShouldReturnDiscard_WhenFound() {
        // Arrange
        int discardId = 1;
        Discard discard = new Discard();
        when(discardRepository.findById(discardId)).thenReturn(Optional.of(discard));

        // Act
        Discard result = discardService.findDiscardById(discardId);

        // Assert
        assertNotNull(result);
        assertEquals(discard, result);
        verify(discardRepository, times(1)).findById(discardId);
    }

    @Test
    void testFindDiscardById_ShouldThrowException_WhenNotFound() {
        // Arrange
        int discardId = 1;
        when(discardRepository.findById(discardId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> discardService.findDiscardById(discardId));
        verify(discardRepository, times(1)).findById(discardId);
    }

   @Test
    public void testDiscardCardFromHand_ShouldModifyPlayerAndDiscard() {
        // Configuración del jugador
        Player player = new Player();
        player.setPlayerHand(new ArrayList<>()); // Inicializamos la lista para evitar el NullPointerException
        Card card = new Card();
        player.getPlayerHand().add(card);

        // Configuración de la partida y zona de descarte
        Match match = new Match();
        Discard discard = new Discard();
        discard.setCards(new ArrayList<>()); // Inicializamos la lista de cartas del descarte
        match.setDiscard(discard);

        // Ejecución del método
        discardService.discardCardFromHand(match, player, card);

        // Verificaciones
        assertFalse(player.getPlayerHand().contains(card)); // Verificamos que la carta fue eliminada de la mano del jugador
        assertTrue(discard.getCards().contains(card)); // Verificamos que la carta fue añadida al descarte
    }

    @Test
    void testDrawCard_ShouldReturnCardAndRemoveFromDiscard() {
        // Arrange
        int discardId = 1;
        int cardId = 42;

        Discard discard = new Discard();
        Card card = new Card();
        card.setId(cardId);
        discard.getCards().add(card);

        when(discardRepository.findById(discardId)).thenReturn(Optional.of(discard));
        when(cardService.findCardById(cardId)).thenReturn(card);

        // Act
        Card result = discardService.drawCard(discardId, cardId);

        // Assert
        assertNotNull(result);
        assertEquals(card, result);
        assertFalse(discard.getCards().contains(card));
        verify(discardRepository, times(1)).findById(discardId);
        verify(cardService, times(1)).findCardById(cardId);
    }

    @Test
    void testDrawCard_ShouldThrowException_WhenDiscardNotFound() {
        // Arrange
        int discardId = 1;
        int cardId = 42;
        when(discardRepository.findById(discardId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> discardService.drawCard(discardId, cardId));
        verify(discardRepository, times(1)).findById(discardId);
        verify(cardService, never()).findCardById(anyInt());
    }

    @Test
    void testDrawCard_ShouldThrowException_WhenCardNotFound() {
        // Arrange
        int discardId = 1;
        int cardId = 42;
        Discard discard = new Discard();

        when(discardRepository.findById(discardId)).thenReturn(Optional.of(discard));
        when(cardService.findCardById(cardId)).thenThrow(new ResourceNotFoundException("Card", "id", cardId));

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> discardService.drawCard(discardId, cardId));
        verify(discardRepository, times(1)).findById(discardId);
        verify(cardService, times(1)).findCardById(cardId);
    }
}
