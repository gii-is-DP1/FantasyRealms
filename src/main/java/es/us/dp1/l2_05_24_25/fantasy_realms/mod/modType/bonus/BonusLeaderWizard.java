package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus;

import java.util.List;
import java.util.Optional;

import org.jpatterns.gof.TemplateMethodPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BONUS_LEADER_OR_WIZARD")
@TemplateMethodPattern.ConcreteClass
public class BonusLeaderWizard extends Mod{

    // suma 14 si hay lider o mago

    @Override
    public void applyMod(List<Card> playerHand) {
        
        Card originCard = this.getOriginCard();

        Optional<Card> card = playerHand.stream().filter(c -> c.getCardType().equals(CardType.LIDER) || c.getCardType().equals(CardType.MAGO)).findFirst();

        if(card.isPresent()) {
            originCard.setFinalValue(originCard.getFinalValue() + this.getPrimaryValue());
        }

    }

    public BonusLeaderWizard(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BonusLeaderWizard() {
        super();
    }

    public BonusLeaderWizard(BonusLeaderWizard other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BonusLeaderWizard(this);
    }
    
}
