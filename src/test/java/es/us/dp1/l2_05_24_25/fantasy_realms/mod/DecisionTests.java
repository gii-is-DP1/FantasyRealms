package es.us.dp1.l2_05_24_25.fantasy_realms.mod;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;

public class DecisionTests {

    @Test
    void testDefaultConstructor() {
        // Crear una instancia utilizando el constructor por defecto
        Decision decision = new Decision();

        // Verificar que los valores iniciales son nulos
        assertNull(decision.getTargetCard());
        assertNull(decision.getTargetCardType());
    }

    @Test
    void testParameterizedConstructor() {
        // Crear objetos de prueba
        Card card = new Card();
        CardType cardType = CardType.LLAMA;

        // Crear una instancia utilizando el constructor parametrizado
        Decision decision = new Decision(card, cardType);

        // Verificar que los valores se asignan correctamente
        assertEquals(card, decision.getTargetCard());
        assertEquals(cardType, decision.getTargetCardType());
    }
    
}
