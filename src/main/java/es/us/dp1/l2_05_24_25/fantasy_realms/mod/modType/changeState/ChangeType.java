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
@DiscriminatorValue("CHANGE_TYPE_CARD")
@TemplateMethodPattern.ConcreteClass
public class ChangeType extends Mod{

    // Método que permite cambiar el tipo de una carta de la mano

    // Aquí la lista target está vacía, directamente el la carta target se pasa como parámetro, al igual que el tipo

    @Override
    public void applyMod(List<Card> playerHand) {

        // No debe hacer nada

        throw new UnsupportedOperationException("This method requires the card from which it will adopt name and type.");
    }

    public void applyMod(List<Card> playerHand, Card targetCard, CardType cType) {

        // Comprobamos que el tipo sea distinto al que ya tiene y lo cambiamos

        if(playerHand.contains(targetCard) && !targetCard.getCardType().equals(cType)) {
            targetCard.setCardType(cType);
        } else {
            throw new ModStatesException("You can't use a card that is not in your hand or you can't use a type that already has the card you want to change it to.");
        }
       
    }

    public ChangeType(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public ChangeType() {
        super();
    }
    
    public ChangeType(ChangeType other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new ChangeType(this);
    }
}
