package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.blank;

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
@DiscriminatorValue("BLANK_UNLESS_TYPE")
@TemplateMethodPattern.ConcreteClass
public class BlankUnlessType extends Mod {

    // Sirve para mods tipo: Anulada a menos que esté con al menos una carta de Inundación.
    // Sirve para mods tipo: Anulada a menos que esté acompañada por al menos una carta de Fuego.

    @Override
    public void applyMod(List<Card> playerHand) {

        // ESTE MOD TIENE EN TARGET LAS CARTAS DEL TIPO QUE NECESITA -> SI NO, NO VAMOS A PODER PARAR SU EJECUCIÓN SI HAY CLEAR

        Optional<CardType> cType = this.getTarget().stream().map(c -> c.getCardType()).findFirst();

        if (cType.isPresent()) {

            boolean cond = playerHand.stream().anyMatch(c -> c.getCardType().equals(cType.get()));

            // Si `cond` es falso, anula la carta y sus modificadores

            if (!cond) {
                Card originCard = this.getOriginCard();
                originCard.setBaseValue(0);
                originCard.setFinalValue(0);
                originCard.getMods().clear();
                // por ahora eliminamos todos los modificadores, éste no lo mantenemos: originCard.getMods().removeIf(m -> m != this);
            }
        }
        
    }

    public BlankUnlessType(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BlankUnlessType() {
        super();
    }

    public BlankUnlessType(BlankUnlessType other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BlankUnlessType(this);
    }

}
