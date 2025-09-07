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
@DiscriminatorValue("BASIC_BONUS_SINGLE_WIZARD")
@TemplateMethodPattern.ConcreteClass
public class BonusBasicWizzardInHand extends Mod{

    // handleBonusWithWizard -> suma 15 si hay mago
    // handleBonusWithWizard -> suma 25 si hay un mago

    @Override
    public void applyMod(List<Card> playerHand) {

        // Obtenemos la carta a la que pertenece el mod
        Card originCard = this.getOriginCard();

        // Verificamos si hay al menos una carta del tipo en la mano del jugador
        boolean typeFound = playerHand.stream()
            .anyMatch(c -> c.getCardType().equals(CardType.MAGO));

        // Si se encuentra el tipo, aplicamos el bonus
        if (typeFound) {
            originCard.setFinalValue(originCard.getFinalValue() + this.getPrimaryValue());
        }
    }

    public BonusBasicWizzardInHand(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BonusBasicWizzardInHand() {
        super();
    }

    public BonusBasicWizzardInHand(BonusBasicWizzardInHand other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BonusBasicWizzardInHand(this);
    }
    
}
