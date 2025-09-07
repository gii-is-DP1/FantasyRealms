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
@DiscriminatorValue("CHANGE_BASE_VALE")
@TemplateMethodPattern.ConcreteClass
public class ChangeBaseValue extends Mod{

    // (Cambia valor base de esa carta) Añade la fuerza base de cualquier Arma, Inundación, Llama, Tierra o Clima en tu mano. 

    @Override
    public void applyMod(List<Card> playerHand) {
        
        // No debe hacer nada

        throw new UnsupportedOperationException("This method requires the card from which it will adopt name and type.");

    }

    public void applyMod(List<Card> playerHand, Card targetCard) {

        // Si la carta es nula, no hay cartas de esos tipos disponibles en la mano, por lo que no se hace nada

        if(targetCard == null) {
            return;
        }

        // Verifica que la carta está en la mano y tiene un tipo permitido
        if (playerHand.contains(targetCard) && isAllowedType(targetCard.getCardType()) && !targetCard.equals(this.getOriginCard())) {
            Integer baseValue = targetCard.getBaseValue();
            // Por ahora entendemos que se le suma al valor base de la carta origen, que tiene valor 1
            this.getOriginCard().setFinalValue(this.getOriginCard().getBaseValue() + baseValue);
        } else {
            if(!playerHand.contains(targetCard)) {
                throw new ModStatesException("The targetCard must be part of the player's hand.");
            }

            if(!isAllowedType(targetCard.getCardType())) {
                throw new ModStatesException("The card is not of the allowed types.");
            }

            if(targetCard.equals(this.getOriginCard())) {
                throw new ModStatesException("The target card cannot be the source card of the mod.");
            }

        }
        
    }

    // Método auxiliar para validar que la carta es de un tipo permitido
    
    private boolean isAllowedType(CardType cardType) {
        return cardType == CardType.ARMA || cardType == CardType.INUNDACION || 
               cardType == CardType.LLAMA || cardType == CardType.TIERRA || 
               cardType == CardType.TIEMPO;
    }

    public ChangeBaseValue(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public ChangeBaseValue() {
        super();
    }

    public ChangeBaseValue(ChangeBaseValue other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new ChangeBaseValue(this);
    }

    
}
