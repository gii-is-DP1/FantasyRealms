package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus;

import java.util.List;
import java.util.Optional;

import org.jpatterns.gof.TemplateMethodPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BONUS_SMOKE_WILDFIRE")
@TemplateMethodPattern.ConcreteClass
public class BonusSmokeAndWildfire extends Mod {

    // Suma 50 si hay Smoke y Wildfire

    @Override
    public void applyMod(List<Card> playerHand) {
        
        Card originCard = this.getOriginCard();

        // Buscar las cartas por nombre
        Optional<Card> smoke = playerHand.stream().filter(c -> c.getName().equals("Humo")).findFirst();
        Optional<Card> wildfire = playerHand.stream().filter(c -> c.getName().equals("Fuego Salvaje")).findFirst();

        // Verificar si ambas cartas están presentes
        if (smoke.isPresent() && wildfire.isPresent()) {
            originCard.setFinalValue(originCard.getFinalValue() + this.getPrimaryValue());
        }
    }

    public BonusSmokeAndWildfire(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BonusSmokeAndWildfire() {
        super();
    }

    public BonusSmokeAndWildfire(BonusSmokeAndWildfire other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BonusSmokeAndWildfire(this);
    }
    
}

