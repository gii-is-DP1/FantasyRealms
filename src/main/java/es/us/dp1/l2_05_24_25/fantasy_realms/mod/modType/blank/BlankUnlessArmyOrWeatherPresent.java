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
@DiscriminatorValue("BLANK_UNLESS_ARMY_OR_WEATHER")
@TemplateMethodPattern.ConcreteClass
public class BlankUnlessArmyOrWeatherPresent extends Mod {

    // Anulada a menos que esté con al menos un Ejército, Anulada si la mano contiene cualquier carta de Tiempo.

    @Override
    public void applyMod(List<Card> playerHand) {

        // target especial -> no está la propia origin card porque hay que manejar correctamente los modificadores de tipo clear
        
        Set<CardType> targetTypes = this.getTarget().stream().map(c -> c.getCardType()).collect(Collectors.toSet());

        // Condición de anulación: no tiene Ejército o tiene Tiempo
        boolean hasRequiredType = playerHand.stream().anyMatch(c -> c.getCardType().equals(CardType.EJERCITO) && targetTypes.contains(CardType.EJERCITO)); // hay que comprobarlo así porque un mod clear puede haberlo borrarlo
        boolean hasProhibitiveType = playerHand.stream().anyMatch(c -> c.getCardType().equals(CardType.TIEMPO) && targetTypes.contains(CardType.TIEMPO));
        
        // Anula la carta y sus modificadores si la condición es verdadera
        if (!hasRequiredType || hasProhibitiveType) {
            Card originCard = this.getOriginCard();
            originCard.setBaseValue(0);
            originCard.setFinalValue(0);
            originCard.getMods().clear(); // Borra todos los demás modificadores, excepto el actual
        }
    }

    public BlankUnlessArmyOrWeatherPresent(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BlankUnlessArmyOrWeatherPresent() {
        super();
    }

    public BlankUnlessArmyOrWeatherPresent(BlankUnlessArmyOrWeatherPresent other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BlankUnlessArmyOrWeatherPresent(this);
    }

}
