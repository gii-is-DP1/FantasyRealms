package es.us.dp1.l2_05_24_25.fantasy_realms.card;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;

public class CardTests {
     private Card defaultCard;
    private Card parameterizedCard;
    private Card copiedCard;

    @BeforeEach
    public void setUp() {

        defaultCard = new Card();


        parameterizedCard = new Card("Test Card", CardType.INUNDACION, 10);

        copiedCard = new Card(parameterizedCard);
    }

    @Test
    public void testDefaultConstructor() {
        assertNull(defaultCard.getName());
        assertNull(defaultCard.getCardType());
        assertNull(defaultCard.getBaseValue());
        assertNull(defaultCard.getFinalValue());
        assertNull(defaultCard.getImage());
        assertNull(defaultCard.getMods());
    }

    @Test
    public void testParameterizedConstructor() {
        assertEquals("Test Card", parameterizedCard.getName());
        assertEquals(CardType.INUNDACION, parameterizedCard.getCardType());
        assertEquals(10, parameterizedCard.getBaseValue());
        assertEquals(10, parameterizedCard.getFinalValue());
        assertNull(parameterizedCard.getImage());
        assertNull(parameterizedCard.getMods());
    }

    @Test
    public void testCopyConstructor() {
        assertEquals("Test Card", copiedCard.getName());
        assertEquals(CardType.INUNDACION, copiedCard.getCardType());
        assertEquals(10, copiedCard.getBaseValue());
        assertEquals(10, copiedCard.getFinalValue());
        assertNull(copiedCard.getImage());
        assertNotNull(copiedCard.getMods());
        assertTrue(copiedCard.getMods().isEmpty());
    }

    @Test
    public void testSetName() {
        defaultCard.setName("New Card");
        assertEquals("New Card", defaultCard.getName());
    }

    @Test
    public void testSetCardType() {
        defaultCard.setCardType(CardType.ARMA);
        assertEquals(CardType.ARMA, defaultCard.getCardType());
    }

    @Test
    public void testSetBaseValue() {
        defaultCard.setBaseValue(20);
        assertEquals(20, defaultCard.getBaseValue());
        assertEquals(null, defaultCard.getFinalValue());
    }

    @Test
    public void testSetFinalValue() {
        defaultCard.setFinalValue(30);
        assertEquals(30, defaultCard.getFinalValue());
    }

    @Test
    public void testSetImage() {
        defaultCard.setImage("image.png");
        assertEquals("image.png", defaultCard.getImage());
    }

}
