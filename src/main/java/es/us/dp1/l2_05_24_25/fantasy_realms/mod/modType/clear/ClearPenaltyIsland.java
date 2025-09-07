package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.clear;

import java.util.List;

import org.jpatterns.gof.TemplateMethodPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ModStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CLEAR_PENALTY_ISLAND")
@TemplateMethodPattern.ConcreteClass
public class ClearPenaltyIsland extends Mod{

    @Override
    public void applyMod(List<Card> playerHand) {

        // No debe hacer nada

        throw new UnsupportedOperationException("This method requires the card from which it will adopt name and type.");
    }  

    // Elimina la penalización de una Inundación o Llama

    public void applyMod(List<Card> playerHand, Card targetCard) {

        if (targetCard == null) {
            return;
        }

        if (playerHand.contains(targetCard) && !targetCard.equals(this.getOriginCard()) && passCondition(targetCard)) {

            targetCard.getMods().removeIf(mod -> mod.getModType().equals(ModType.PENALTY));

        } else {

            if(!playerHand.contains(targetCard)) {
                throw new ModStatesException("The player's hand must contain the target card.");
            }

            if(targetCard.equals(this.getOriginCard())) {
                throw new ModStatesException("The target card cannot be the source card of the mod.");
            }

            if(!passCondition(targetCard)) {
                throw new ModStatesException("The target card is not 'INUNDACION' or 'LLAMA' type.");
            }
        }

    }

    // Podemos comprobarlo aquí con target del mod también
    private Boolean passCondition(Card targetCard) {
        return targetCard.getCardType().equals(CardType.LLAMA) || 
               targetCard.getCardType().equals(CardType.INUNDACION);
    }

    public ClearPenaltyIsland(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }


    public ClearPenaltyIsland() {
        super();
    }

    public ClearPenaltyIsland(ClearPenaltyIsland other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new ClearPenaltyIsland(this);
    }

    
}
