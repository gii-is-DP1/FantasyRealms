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
@DiscriminatorValue("BONUS_BOOK_BELL_WIZARD")
@TemplateMethodPattern.ConcreteClass
public class BonusBookBellWizard extends Mod {

    // Suma 100 si hay Libro de cambios, Campanario y un MAGO

    @Override
    public void applyMod(List<Card> playerHand) {
        Card originCard = this.getOriginCard();

        // Buscar las cartas por nombres
        Optional<Card> bookOfChanges = playerHand.stream().filter(c -> c.getName().equals("Libro de los Cambios")).findFirst();
        Optional<Card> bellTower = playerHand.stream().filter(c -> c.getName().equals("Campanario")).findFirst();
        Optional<Card> wizard = playerHand.stream().filter(c -> c.getCardType().equals(CardType.MAGO)).findFirst();

        // Verificar si todas las cartas están presentes
        if (bookOfChanges.isPresent() && bellTower.isPresent() && wizard.isPresent()) {
            originCard.setFinalValue(originCard.getFinalValue() + this.getPrimaryValue());
        }
    }

    public BonusBookBellWizard(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BonusBookBellWizard() {
        super();
    }

    public BonusBookBellWizard(BonusBookBellWizard other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BonusBookBellWizard(this);
    }
}

