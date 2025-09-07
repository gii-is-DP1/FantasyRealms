package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.basic;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jpatterns.gof.TemplateMethodPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BONUS_PENALTY_EACH_TYPE")
@TemplateMethodPattern.ConcreteClass
public class BonusPenaltyForEachTypeMod extends Mod {

    // Este método aplica un bonus o penalización para cada carta de un tipo específico en la mano del jugador
    // Aquí sí necesitamos el target
    // Para penalizar en vez de bonus, metemos en primary value del mod de penalty un -value, y así restará.

    // funciona para tipos: +/-x por cada tipo "y" en la mano
    // funciona para tipos: +/-x por cada tipo "y","z",... en la mano

    @Override
    public void applyMod(List<Card> playerHand) {
        
        Card originCard = this.getOriginCard();  // La carta que recibe el bonus

        Set<CardType> targetTypes = this.getTarget().stream().map(c -> c.getCardType()).collect(Collectors.toSet());

        // Contar cuántas cartas del tipo "targetType" hay en la mano del jugador  excluyendo la carta origen
        long count = playerHand.stream()
            .filter(c -> !c.equals(originCard))
            .filter(c -> targetTypes.contains(c.getCardType()))
            .count();

        // Aplicar el bonus/penalización: valor final = valor base + (número de cartas encontradas * bonus/penalización por carta)
        originCard.setFinalValue(originCard.getFinalValue() + (int) (count * this.getPrimaryValue()));
    }

    public BonusPenaltyForEachTypeMod(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BonusPenaltyForEachTypeMod() {
        super();
    }

    public BonusPenaltyForEachTypeMod(BonusPenaltyForEachTypeMod other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BonusPenaltyForEachTypeMod(this);
    }
}
