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
@DiscriminatorValue("BONUS_DWARVISH_DRAGON")
@TemplateMethodPattern.ConcreteClass
public class BonusDwarvishOrDragon extends Mod {

    // Suma 25 si hay Dwarvish Infantry o Dragon

    @Override
    public void applyMod(List<Card> playerHand) {
        
        Card originCard = this.getOriginCard();

        // Buscar las cartas por nombre
        Optional<Card> dwarvishInfantry = playerHand.stream().filter(c -> c.getName().equals("Infanteria Enana")).findFirst();
        Optional<Card> dragon = playerHand.stream().filter(c -> c.getName().equals("Dragon")).findFirst();

        // Verificar si alguna de las dos cartas está presente
        if (dwarvishInfantry.isPresent() || dragon.isPresent()) {
            originCard.setFinalValue(originCard.getFinalValue() + this.getPrimaryValue());
        }
    }

    public BonusDwarvishOrDragon(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BonusDwarvishOrDragon() {
        super();
    }

    public BonusDwarvishOrDragon(BonusDwarvishOrDragon other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BonusDwarvishOrDragon(this);
    }
}

