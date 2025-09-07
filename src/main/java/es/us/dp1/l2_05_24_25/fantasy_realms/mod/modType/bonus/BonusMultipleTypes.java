package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus;

import java.util.List;
import java.util.Optional;

import org.jpatterns.gof.TemplateMethodPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BONUS_PRINCESS_QUEEN_EMPRESS")
@TemplateMethodPattern.ConcreteClass
public class BonusMultipleTypes extends Mod{

    // suma 30 si hay princesa y 15 si hay reina, empress, o elemental enchantress

    @Override
    public void applyMod(List<Card> playerHand) {
        
        Card originCard = this.getOriginCard();

        Optional<Card> princess = playerHand.stream().filter(c -> c.getName().equals("Princesa")).findFirst();

        Optional<Card> others = playerHand.stream().filter(c -> c.getName().equals("Reina") || c.getName().equals("Emperatriz") || c.getName().equals("Hechicera elemental")).findFirst();

        if(princess.isPresent()) {
            originCard.setFinalValue(originCard.getFinalValue() + this.getSecondaryValue());
        } 
        
        if(others.isPresent()) {
            originCard.setFinalValue(originCard.getFinalValue() + this.getPrimaryValue());
        }

    }

    public BonusMultipleTypes(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BonusMultipleTypes() {
        super();
    }

    public BonusMultipleTypes(BonusMultipleTypes other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BonusMultipleTypes(this);
    }
    
}
