package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.clear;

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
@DiscriminatorValue("CLEAR_PENALTY_FOR_TYPE")
@TemplateMethodPattern.ConcreteClass
public class ClearPenaltyForType extends Mod {

    // Sirve para mods tipo: Elimina la penalización de una "Flood" o "Flame".
    // Sirve para mods tipo: Elimina la penalización en todos los "Weather".
    // Sirve para mods tipo: Elimina la penalización en todos los "Floods".
    // Sirve para mods tipo: Borra la penalización de todas las "Beasts".

    @Override
    public void applyMod(List<Card> playerHand) {

        Set<CardType> cTypes = this.getTarget().stream().map(c -> c.getCardType()).collect(Collectors.toSet());

        List<Card> cards = playerHand.stream().filter(c -> cTypes.contains(c.getCardType())).collect(Collectors.toList());

        for(Card c: cards) {
            c.getMods().removeIf(mod -> mod.getModType().equals(ModType.PENALTY));
        }

    }

    public ClearPenaltyForType(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public ClearPenaltyForType() {
        super();
    }

    public ClearPenaltyForType(ClearPenaltyForType other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new ClearPenaltyForType(this);
    }
    
}
