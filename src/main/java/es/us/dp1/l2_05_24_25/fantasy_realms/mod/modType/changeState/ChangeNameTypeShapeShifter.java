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
@DiscriminatorValue("CHANGE_NAME_TYPE_SHAPESHIFTER")
@TemplateMethodPattern.ConcreteClass
public class ChangeNameTypeShapeShifter extends Mod{

    // Para mods tipo: Puede adoptar el nombre y el tipo de cualquier Artefacto, Lider, Mago, Arma o Bestia. No obtiene bonificación ni penalización.

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

    // Lo que hacemos es que antes de hacer set a target con la carta, llamamos a isValidTarget para comprobar que es válido
    
    private boolean isValidTarget(Card c) {
        return c.getCardType().equals(CardType.ARTEFACTO) || 
               c.getCardType().equals(CardType.LIDER) ||
               c.getCardType().equals(CardType.MAGO) || 
               c.getCardType().equals(CardType.ARMA) || 
               c.getCardType().equals(CardType.BESTIA);
    }    

    public ChangeNameTypeShapeShifter(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public ChangeNameTypeShapeShifter() {
        super();
    }

    public ChangeNameTypeShapeShifter(ChangeNameTypeShapeShifter other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new ChangeNameTypeShapeShifter(this);
    }
    
}
