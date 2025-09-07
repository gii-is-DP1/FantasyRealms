package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState;

import java.util.List;

import org.jpatterns.gof.TemplateMethodPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ModStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("CHANGE_NAME_AND_TYPE_MIRAGE")
@TemplateMethodPattern.ConcreteClass
public class ChangeNameTypeShapeMirage extends Mod{

    // Para mods tipo: Puede adoptar el nombre y el tipo de cualquier Ejército, Tierra, Tiempo, Inundación o Llama. No obtiene bonificación ni penalización. -> No tiene que estar en la mano

    @Override
    public void applyMod(List<Card> playerHand) {

        // No debe hacer nada

        throw new UnsupportedOperationException("This method requires the card from which it will adopt name and type.");
    }

    public void applyMod(List<Card> playerHand, Card targetCard) {

        if (isValidTarget(targetCard)) { 
            this.getOriginCard().setName(targetCard.getName());
            this.getOriginCard().setCardType(targetCard.getCardType());
        } else {
            throw new ModStatesException("You cannot adopt name and type of a card that is not within the available types.");
        }
    }
    
    private boolean isValidTarget(Card c) {
        return c.getCardType().equals(CardType.EJERCITO) || 
               c.getCardType().equals(CardType.TIERRA) ||
               c.getCardType().equals(CardType.TIEMPO) || 
               c.getCardType().equals(CardType.INUNDACION) || 
               c.getCardType().equals(CardType.LLAMA);
    }
    
    public ChangeNameTypeShapeMirage(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public ChangeNameTypeShapeMirage() {
        super();
    }

    public ChangeNameTypeShapeMirage(ChangeNameTypeShapeMirage other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new ChangeNameTypeShapeMirage(this);
    }
    
}
