package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState;


import java.util.List;
import java.util.stream.Collectors;

import org.jpatterns.gof.TemplateMethodPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ModStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("CHANGE_ALL_EXCEPT_BONUS")
@TemplateMethodPattern.ConcreteClass
public class ChangeAllExceptBonus extends Mod {

    // Puede duplicar el nombre, tipo, fuerza base y penalización, pero no la bonificación, de cualquier otra carta en tu mano.

    @Override
    public void applyMod(List<Card> playerHand) {

        // No debe hacer nada

        throw new UnsupportedOperationException("This method requires the card from which it will adopt name and type.");

    }

    public void applyMod(List<Card> playerHand, Card targetCard) {

        if (playerHand.contains(targetCard) && !targetCard.getName().equals(this.getOriginCard().getName())) {

            // Duplica los atributos

            this.getOriginCard().setName(targetCard.getName());
            this.getOriginCard().setCardType(targetCard.getCardType());
            this.getOriginCard().setBaseValue(targetCard.getBaseValue());

            // Obtenemos los mods de penalización de la carta que queremos adoptar y clonamos

            List<Mod> penaltyMods = targetCard.getMods().stream()
                .filter(mod -> mod.getModType().equals(ModType.PENALTY) || mod.getModType().equals(ModType.PENALTY_AND_BLANK))
                .map(originalMod -> {
                    // Clonamos el Mod
                    Mod clonedMod = originalMod.clone();
                    // Reasignamos la originCard del mod al originCard actual
                    clonedMod.setOriginCard(this.getOriginCard());
                    return clonedMod;
                })
                .collect(Collectors.toList());

            this.getOriginCard().setMods(penaltyMods);

        } else {
            throw new ModStatesException("You cannot duplicate a card that is not in your hand or itself.");
        }
    }

    public ChangeAllExceptBonus(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public ChangeAllExceptBonus() {
        super();
    }

    public ChangeAllExceptBonus(ChangeAllExceptBonus other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new ChangeAllExceptBonus(this);
    }

}
