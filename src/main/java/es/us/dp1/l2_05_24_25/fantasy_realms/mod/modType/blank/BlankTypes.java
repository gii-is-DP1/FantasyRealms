package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.blank;

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
@DiscriminatorValue("BLANK_TYPES")
@TemplateMethodPattern.ConcreteClass
public class BlankTypes extends Mod{

    // Sirve para mods tipo: Anula todos los ejércitos, líderes y OTRAS bestias (tanto modificadores como valores de las cartas) (el basilisco a sí mismo no)
    // Sirve para mods tipo: Anula todas las cartas de Inundación

    @Override
    public void applyMod(List<Card> playerHand) {
        
        // Hace falta el target porque los modificadores clear pueden borrar algunos de esos target

        Set<CardType> cTypes = this.getTarget().stream().map(c -> c.getCardType()).collect(Collectors.toSet());

        // Si la carta es de alguno de esos tipos

        for(Card c: playerHand) {
            if(cTypes.contains(c.getCardType()) && !c.getName().equals(this.getOriginCard().getName())) { // la segunda cond es para que el basilisco no se anule a sí mismo
                c.setBaseValue(0); // Anulamos el valor de la carta
                c.setFinalValue(0); // Anulamos el valor de la carta
                c.getMods().clear(); //Anulamos sus efectos
            }
        }

    }

    public BlankTypes(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BlankTypes() {
        super();
    }
    
    public BlankTypes(BlankTypes other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BlankTypes(this);
    }

}
