package es.us.dp1.l2_05_24_25.fantasy_realms.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.dto.DecisionDTO;

public class DecisionDTOTests {
    @Test
    public void testNoArgsConstructor() {
        DecisionDTO decisionDTO = new DecisionDTO();
        assertNull(decisionDTO.getModId());
        assertNull(decisionDTO.getCardId());
        assertNull(decisionDTO.getCardType());
    }

    @Test
    public void testAllArgsConstructor() {
        DecisionDTO decisionDTO = new DecisionDTO(1, 2, CardType.MAGO);
        assertEquals(1, decisionDTO.getModId());
        assertEquals(2, decisionDTO.getCardId());
        assertEquals(CardType.MAGO, decisionDTO.getCardType());
    }

    @Test
    public void testSettersAndGetters() {
        DecisionDTO decisionDTO = new DecisionDTO();
        decisionDTO.setModId(1);
        decisionDTO.setCardId(2);
        decisionDTO.setCardType(CardType.BESTIA);

        assertEquals(1, decisionDTO.getModId());
        assertEquals(2, decisionDTO.getCardId());
        assertEquals(CardType.BESTIA, decisionDTO.getCardType());
    }

    @Test
    public void testToString() {
        DecisionDTO decisionDTO = new DecisionDTO(1, 2, CardType.MAGO);
        String expectedString = "DecisionDTO{modId=1, cardId=2, cardType=MAGO}";
        assertEquals(expectedString, decisionDTO.toString());
    }

    @Test
    public void testEquality() {
        DecisionDTO decisionDTO1 = new DecisionDTO(1, 2, CardType.MAGO);
        DecisionDTO decisionDTO2 = new DecisionDTO(1, 2, CardType.MAGO);

        assertEquals(decisionDTO1, decisionDTO2);
        assertEquals(decisionDTO1.hashCode(), decisionDTO2.hashCode());

        decisionDTO2.setCardType(CardType.BESTIA);
        assertNotEquals(decisionDTO1, decisionDTO2);
    }
}
