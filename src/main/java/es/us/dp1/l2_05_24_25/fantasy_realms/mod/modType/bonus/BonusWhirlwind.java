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
@DiscriminatorValue("BONUS_WHIRLWIND")
@TemplateMethodPattern.ConcreteClass
public class BonusWhirlwind extends Mod{

    // Bonus: +40 si tienes Tormenta y Ventisca o +40 si tienes Tormenta y Gran Inundacion

    @Override
    public void applyMod(List<Card> playerHand) {
        
        Card originCard = this.getOriginCard();

        // Buscamos Tormenta

        Optional<Card> storm = playerHand.stream().filter(card -> card.getName().equals("Tormenta")).findFirst();

        // Buscamos a Ventisca y/o Gran Inundación

        Optional<Card> otherCard = playerHand.stream().filter(card -> card.getName().equals("Ventisca") ||
            card.getName().equals("Gran Inundacion")).findFirst();

        if(storm.isPresent() && otherCard.isPresent()) {
            originCard.setFinalValue(originCard.getFinalValue() + this.getPrimaryValue());
        }

    }

    public BonusWhirlwind() {
        super();
    }

    public BonusWhirlwind(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BonusWhirlwind(BonusWhirlwind other) {
        super(other);
    }
    
    @Override
    public Mod clone() {
        return new BonusWhirlwind(this);
    }
}
