package es.us.dp1.l2_05_24_25.fantasy_realms.mod;

import java.util.List;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;


/**
 * Clase concreta de {@link Mod} utilizada exclusivamente para pruebas.
 * Proporciona implementaciones mínimas de los métodos abstractos necesarios para 
 * permitir la creación y clonación de instancias de {@link Mod} durante los tests.
 */
public class TestMod extends Mod{

    public TestMod(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description, primaryValue, secondaryValue, originCard, target, modType);
    }

    public TestMod(Mod other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new TestMod(this);
    }

    @Override
    public void applyMod(List<Card> playerHand) {

    }
    
}
