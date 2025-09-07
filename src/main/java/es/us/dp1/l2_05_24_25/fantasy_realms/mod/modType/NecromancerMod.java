package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType;

import java.util.List;

import org.jpatterns.gof.TemplateMethodPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("NECROMANCER_MOD")
@TemplateMethodPattern.ConcreteClass
public class NecromancerMod extends Mod{

    // Al final del juego, puedes tomar un Ejército, Líder, Mago o Bestia del montón de descarte y añadirlo a tu mano como una octava carta.

    @Override
    public void applyMod(List<Card> playerHand) {
        throw new UnsupportedOperationException("This method requires the discard pile.");
    }

    // Método sobrecargado con el mazo de descarte
    public void applyMod(List<Card> playerHand, List<Card> discardPile, Card targetCard) {

        // Si la carta es nula, significa que no hay carta que cumpla la condición por lo que no se hace nada

        if (targetCard == null) { 
            return; 
        }

        if (discardPile.contains(targetCard) && passCondition(targetCard)) {
            playerHand.add(targetCard);
        }
    }

    private Boolean passCondition(Card targetCard) {
        return targetCard.getCardType().equals(CardType.EJERCITO) || 
               targetCard.getCardType().equals(CardType.LIDER) || 
               targetCard.getCardType().equals(CardType.MAGO) || 
               targetCard.getCardType().equals(CardType.BESTIA);
    }

    public NecromancerMod(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public NecromancerMod() {
        super();
    }

    public NecromancerMod(NecromancerMod other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new NecromancerMod(this);
    }
}

