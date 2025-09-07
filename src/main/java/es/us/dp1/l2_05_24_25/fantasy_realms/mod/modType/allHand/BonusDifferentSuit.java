package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.allHand;

import java.util.List;

import org.jpatterns.gof.TemplateMethodPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BONUS_DIFFERENT_SUIT_HAND")
@TemplateMethodPattern.ConcreteClass
public class BonusDifferentSuit extends Mod{

    // suma 50 si todas las cartas de la mano son de distinto tipo.

    @Override
    public void applyMod(List<Card> playerHand) {
        
        Card originCard = this.getOriginCard();

        // Comprobamos si el tamaño de los tipos únicos es igual al tamaño de la mano

        boolean allDifferentTypes = playerHand.stream()
            .map(c -> c.getCardType())
            .distinct()
            .count() == playerHand.size();

        if (allDifferentTypes) {
            originCard.setFinalValue(originCard.getFinalValue() + this.getPrimaryValue());
        }

    }

    public BonusDifferentSuit(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }
    
    public BonusDifferentSuit() {
        super();
    }

    public BonusDifferentSuit(BonusDifferentSuit other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BonusDifferentSuit(this);
    }
}
