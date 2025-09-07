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
@DiscriminatorValue("BONUS_QUEEN")
@TemplateMethodPattern.ConcreteClass
public class BonusQueen extends Mod {

    // Para manejar bonus específico de Queen -> Bonus: +5 por cada Ejercito o +20 por cada Ejercito si también tienes al Rey

    @Override
    public void applyMod(List<Card> playerHand) {

        Card originCard = this.getOriginCard();

        // Contamos los ejércitos en la mano
        long armyCount = playerHand.stream()
            .filter(c -> c.getCardType().equals(CardType.EJERCITO))
            .count();

        // Buscamos si el Rey está en la mano
        boolean hasKing = playerHand.stream()
            .anyMatch(c -> c.getName().equals("Rey"));

        // Aplicamos el bonus correspondiente dependiendo si el Rey está o no
        if (hasKing) {
            originCard.setFinalValue(originCard.getFinalValue() + (int) (armyCount * this.getSecondaryValue())); // es decir +20 por ejército
        } else {
            originCard.setFinalValue(originCard.getFinalValue() + (int) (armyCount * this.getPrimaryValue())); // es decir + 5 por ejército
        }
    }

    public BonusQueen(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BonusQueen() {
        super();
    }

    public BonusQueen(BonusQueen other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BonusQueen(this);
    }
}
