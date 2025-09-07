package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.clear;

import java.util.List;

import org.jpatterns.gof.TemplateMethodPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CLEAR_ALL_PENALTIES")
@TemplateMethodPattern.ConcreteClass
public class ClearAllPenalties extends Mod {

    // Borra la sección de penalización de todas las cartas de la mano

    @Override
    public void applyMod(List<Card> playerHand) {
        
        for (Card card : playerHand) {
            // Filtra y elimina todos los Mods de tipo PENALTY
            card.getMods().removeIf(mod -> mod.getModType() == ModType.PENALTY || mod.getModType() == ModType.PENALTY_AND_BLANK);
        }

    }

    public ClearAllPenalties(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public ClearAllPenalties() {
        super();
    }

    public ClearAllPenalties(ClearAllPenalties other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new ClearAllPenalties(this);
    }
    
}
