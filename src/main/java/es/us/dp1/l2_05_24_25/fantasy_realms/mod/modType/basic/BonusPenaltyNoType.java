package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.basic;

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
@DiscriminatorValue("BONUS_PENALTY_NO_TYPE")
@TemplateMethodPattern.ConcreteClass
public class BonusPenaltyNoType extends Mod{

    // handleNoWeather -> bonus -> suma 5 si no hay weather.
    // handleNoLeader -> penalty -> resta 8 a no ser que haya al menos un lider.
    // handleNoWizard -> penalty -> resta 40 a no ser que haya al menos un mago.

    @Override
    public void applyMod(List<Card> playerHand) {
        // Obtenemos el tipo al que afecta el modificador
        Optional<CardType> cType = this.getTarget().stream().map(c -> c.getCardType()).findFirst();
        // Encontrar carta de ese tipo en la mano del jugador
        Optional<Card> cardOpt = playerHand.stream().filter(c -> c.getCardType().equals(cType.get())).findFirst();
        // Obtenemos la carta a la que pertenece el mod
        Card originCard = this.getOriginCard();

        // Verificamos si la carta existe o no y aplicamos la bonificación/penalización
        if (cardOpt.isEmpty()) {
            // Si no existe la carta requerida
            originCard.setFinalValue(originCard.getFinalValue() + this.getPrimaryValue());
        }
    }

    public BonusPenaltyNoType(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BonusPenaltyNoType() {
        super();
    }

    public BonusPenaltyNoType(BonusPenaltyNoType other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BonusPenaltyNoType(this);
    }
    
}
