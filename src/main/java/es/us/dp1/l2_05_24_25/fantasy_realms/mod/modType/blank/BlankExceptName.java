package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.blank;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jpatterns.gof.TemplateMethodPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BLANK_EXCEPT_NAME")
@TemplateMethodPattern.ConcreteClass
public class BlankExceptName extends Mod {

    // Anula todas las cartas de Llama excepto Rayo.

    // Anula todas las cartas excepto Llamas, Clima, Magos, Armas, Artefactos, Gran Inundación, Isla, Montaña, Unicornio y Dragón.

    // Anula todos los Ejércitos, todas las Tierras excepto Montaña, y todas las Llamas excepto Rayo.

    @Override
    public void applyMod(List<Card> playerHand) {

        // Nos quedamos con los nombres del target

        Set<String> targetNames = this.getTarget().stream()
                .map(c -> c.getName())
                .collect(Collectors.toSet());

        for (Card c : playerHand) {
            
            // Si la carta pertenece al target, se anula

            if (targetNames.contains(c.getName())) {
                c.setBaseValue(0); // Anula el valor base
                c.setFinalValue(0); // Anula el valor final
                c.getMods().clear(); // Anula todos sus modificadores
            }
        }
    }

    public BlankExceptName() {
        super();
    }

    public BlankExceptName(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BlankExceptName(BlankExceptName other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BlankExceptName(this);
    }

}
