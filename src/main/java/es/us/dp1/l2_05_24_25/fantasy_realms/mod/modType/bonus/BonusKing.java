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
@DiscriminatorValue("BONUS_KING")
@TemplateMethodPattern.ConcreteClass
public class BonusKing extends Mod {

    // Para manejar bonus específico de King -> Bonus: +5 por cada Ejercito o +20 por cada Ejercito si también tienes a la Reina

    @Override
    public void applyMod(List<Card> playerHand) {

        Card originCard = this.getOriginCard();

        // Contamos los ejércitos en la mano
        long armyCount = playerHand.stream()
            .filter(c -> c.getCardType().equals(CardType.EJERCITO))
            .count();

        // Buscamos si la Reina está en la mano
        boolean hasQueen = playerHand.stream()
            .anyMatch(c -> c.getName().equals("Reina"));

        // Aplicamos el bonus correspondiente dependiendo si el Rey está o no
        if (hasQueen) {
            originCard.setFinalValue(originCard.getFinalValue() + (int) (armyCount * this.getSecondaryValue())); // es decir +20 por ejército
        } else {
            originCard.setFinalValue(originCard.getFinalValue() + (int) (armyCount * this.getPrimaryValue())); // es decir + 5 por ejército
        }
    }

    public BonusKing(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BonusKing() {
        super();
    }

    public BonusKing(BonusKing other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BonusKing(this);
    }

}
