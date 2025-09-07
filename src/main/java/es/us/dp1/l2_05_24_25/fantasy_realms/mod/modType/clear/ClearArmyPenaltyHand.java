package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.clear;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jpatterns.gof.TemplateMethodPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CLEAR_ARMY_PENALTY_HAND")
@TemplateMethodPattern.ConcreteClass
public class ClearArmyPenaltyHand extends Mod {

    // Elimina la palabra Ejército de la sección de penalización de todas las cartas en la mano.

    @Override
    public void applyMod(List<Card> playerHand) {
        for (Mod m : getPenaltyMods(playerHand)) {
            // Verificamos si el target del modificador no es nulo
            if (m.getTarget() != null) {
                List<Card> mutableTarget = new ArrayList<>(m.getTarget());
                mutableTarget.removeIf(c -> c.getCardType().equals(CardType.EJERCITO));
                m.setTarget(mutableTarget); // Actualiza la lista de target después de modificarla
            }
        }
    }

    // Filtramos todos los modificadores que sean de tipo penalizador, que pertenezcan a cartas de tipo inundación y que no sean este mismo

    private List<Mod> getPenaltyMods(List<Card> playerHand) {
        return playerHand.stream()
            .flatMap(card -> card.getMods().stream())
            .filter(m -> m != this && (m.getModType().equals(ModType.PENALTY) || m.getModType().equals(ModType.PENALTY_AND_BLANK)))
            .collect(Collectors.toList());
    }

    public ClearArmyPenaltyHand(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public ClearArmyPenaltyHand() {
        super();
    }

    public ClearArmyPenaltyHand(ClearArmyPenaltyHand other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new ClearArmyPenaltyHand(this);
    }
    
}
