package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus;

import java.util.List;

import org.jpatterns.gof.TemplateMethodPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BONUS_FOREST")
@TemplateMethodPattern.ConcreteClass
public class BonusForest extends Mod{

    // +12 por cada Bestia y Arqueros Élficos

    @Override
    public void applyMod(List<Card> playerHand) {

        Card originCard = this.getOriginCard();

        // Contamos el número de bestias
        
        long countBeastsAndArchers = playerHand.stream()
            .filter(card -> card.getCardType().equals(CardType.BESTIA) || card.getName().equals("Arqueros Elficos"))
            .count();

        // Aplicar el bonus: valor final = valor base + (número de cartas encontradas * bonus por carta)
        originCard.setFinalValue(originCard.getFinalValue() + (int) (countBeastsAndArchers * this.getPrimaryValue()));

    }

    public BonusForest() {
        super();
    }

    public BonusForest(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BonusForest(BonusForest other) {
        super(other);
    }
    
    @Override
    public Mod clone() {
        return new BonusForest(this);
    }
}
