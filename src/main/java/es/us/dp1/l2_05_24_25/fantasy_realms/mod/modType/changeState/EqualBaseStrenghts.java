package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState;

import java.util.List;

import org.jpatterns.gof.TemplateMethodPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("EQUAL_BASE_STRENGTHS")
@TemplateMethodPattern.ConcreteClass
public class EqualBaseStrenghts extends Mod{

    // Igual a las fuerzas base de todos los Ejércitos en tu mano. -> Entiendo que se refiere a sumarlo a la baseValue no a sustituirlo

    @Override
    public void applyMod(List<Card> playerHand) {

        Integer cont = playerHand.stream().filter(c -> c.getCardType().equals(CardType.EJERCITO)).mapToInt(c -> c.getBaseValue()).sum();

        this.getOriginCard().setFinalValue(this.getOriginCard().getBaseValue() + cont); // Como este tipo se ejecuta despues de clear y blank no hay problema con coger baseValue
    }

    public EqualBaseStrenghts(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public EqualBaseStrenghts() {
        super();
    }

    public EqualBaseStrenghts(EqualBaseStrenghts other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new EqualBaseStrenghts(this);
    }
    
}
