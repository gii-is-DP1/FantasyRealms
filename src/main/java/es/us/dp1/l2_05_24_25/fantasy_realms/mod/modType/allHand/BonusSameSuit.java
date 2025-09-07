package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.allHand;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jpatterns.gof.TemplateMethodPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BONUS_SAME_SUIT_HAND")
@TemplateMethodPattern.ConcreteClass
public class BonusSameSuit extends Mod{

    // Bonificación: +10 si hay tres cartas diferentes de un mismo tipo, +40 si hay cuatro, +100 si hay cinco

    @Override
    public void applyMod(List<Card> playerHand) {
        
        Card originCard = this.getOriginCard();

        // Creamos un mapa que agrupe las cartas por tipo

        Map<CardType, List<Card>> groupedByType = playerHand.stream().collect(Collectors.groupingBy(c -> c.getCardType()));

        // Recorremos cada grupo de cartas para aplicar la bonificación si cumplen con los umbrales

        for (Map.Entry<CardType, List<Card>> entry : groupedByType.entrySet()) { 

            int count = entry.getValue().size();  // Número de cartas de este tipo

            if (count >= 5) {
                originCard.setFinalValue(originCard.getFinalValue() + 100); // +100
                break;  // Si encontramos un grupo con cinco cartas, aplicamos el máximo bono y salimos, ya que si hay 7 cartas en mano no va a haber otro grupo que tenga el mínimo de 3 cartas del mismo tipo
            } else if (count == 4) {
                originCard.setFinalValue(originCard.getFinalValue() + this.getSecondaryValue()); // +40
            } else if (count == 3) {
                originCard.setFinalValue(originCard.getFinalValue() + this.getPrimaryValue()); // +10
            }
        }

    }

    public BonusSameSuit(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BonusSameSuit() {
        super();
    }

    public BonusSameSuit(BonusSameSuit other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BonusSameSuit(this);
    }
    
}
