package es.us.dp1.l2_05_24_25.fantasy_realms.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.dto.CardDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.dto.ModDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;

public class CardDTOTests {
    private Card card;
    private CardDTO cardDTO;
    private Mod mod;
    private ModDTO modDTO;

    @BeforeEach
    public void setUp() {
        mod = Mockito.mock(Mod.class);
        when(mod.getId()).thenReturn(1);
        when(mod.getDescription()).thenReturn("Test Mod Description");
        when(mod.getPrimaryValue()).thenReturn(10);
        when(mod.getSecondaryValue()).thenReturn(20);
        when(mod.getModType()).thenReturn(ModType.PENALTY);

        modDTO = new ModDTO(mod);

        card = Mockito.mock(Card.class);
        when(card.getId()).thenReturn(1);
        when(card.getName()).thenReturn("Test Card");
        when(card.getImage()).thenReturn("test_image.png");
        when(card.getCardType()).thenReturn(CardType.MAGO);
        when(card.getBaseValue()).thenReturn(100);
        when(card.getFinalValue()).thenReturn(150);
        when(card.getMods()).thenReturn(List.of(mod));

        cardDTO = new CardDTO(card);
    }

    @Test
    public void testCardDTOCreation() {
        assertEquals(card.getId(), cardDTO.getId());
        assertEquals(card.getName(), cardDTO.getName());
        assertEquals(card.getImage(), cardDTO.getImage());
        assertEquals(card.getCardType(), cardDTO.getCardType());
        assertEquals(card.getBaseValue(), cardDTO.getBaseValue());
        assertEquals(card.getFinalValue(), cardDTO.getFinalValue());
        assertNotNull(cardDTO.getModsDTO());
        assertEquals(1, cardDTO.getModsDTO().size());
        assertEquals(mod.getId(), cardDTO.getModsDTO().get(0).getId());
    }

    @Test
    public void testCardDTOSettersAndGetters() {
        cardDTO.setName("New Card Name");
        assertEquals("New Card Name", cardDTO.getName());

        cardDTO.setImage("new_image.png");
        assertEquals("new_image.png", cardDTO.getImage());

        cardDTO.setCardType(CardType.EJERCITO);
        assertEquals(CardType.EJERCITO, cardDTO.getCardType());

        cardDTO.setBaseValue(200);
        assertEquals(200, cardDTO.getBaseValue());

        cardDTO.setFinalValue(250);
        assertEquals(250, cardDTO.getFinalValue());

        ModDTO newModDTO = new ModDTO(mod);
        cardDTO.setModsDTO(List.of(newModDTO));
        assertEquals(1, cardDTO.getModsDTO().size());
        assertEquals(newModDTO, cardDTO.getModsDTO().get(0));
    }

    @Test
    public void testCardDTOConstructorWithNullValues() {
        Card nullCard = Mockito.mock(Card.class);
        when(nullCard.getId()).thenReturn(2);
        when(nullCard.getName()).thenReturn(null);
        when(nullCard.getImage()).thenReturn(null);
        when(nullCard.getCardType()).thenReturn(null);
        when(nullCard.getBaseValue()).thenReturn(null);
        when(nullCard.getFinalValue()).thenReturn(null);
        when(nullCard.getMods()).thenReturn(null);

        CardDTO nullCardDTO = new CardDTO(nullCard);

        assertEquals(nullCard.getId(), nullCardDTO.getId());
        assertNull(nullCardDTO.getName());
        assertNull(nullCardDTO.getImage());
        assertNull(nullCardDTO.getCardType());
        assertNull(nullCardDTO.getBaseValue());
        assertNull(nullCardDTO.getFinalValue());
        assertNotNull(nullCardDTO.getModsDTO());
        assertTrue(nullCardDTO.getModsDTO().isEmpty());
    }
}
