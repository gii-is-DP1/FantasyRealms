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
@DiscriminatorValue("BONUS_ELVEN_WARLORD_BEASTMASTER")
@TemplateMethodPattern.ConcreteClass
public class BonusElvenWarlordBeastmaster extends Mod {

    // Suma 30 si hay Arqueros élficos, Jefe militar o Maestro de bestias

    @Override
    public void applyMod(List<Card> playerHand) {
        
        Card originCard = this.getOriginCard();

        // Buscar las cartas por nombre
        Optional<Card> elvenArchers = playerHand.stream().filter(c -> c.getName().equals("Arqueros Elficos")).findFirst();
        Optional<Card> warlord = playerHand.stream().filter(c -> c.getName().equals("Jefe militar")).findFirst();
        Optional<Card> beastmaster = playerHand.stream().filter(c -> c.getName().equals("Maestro de bestias")).findFirst();

        // Verificar si alguna de las tres cartas está presente
        if (elvenArchers.isPresent() || warlord.isPresent() || beastmaster.isPresent()) {
            originCard.setFinalValue(originCard.getFinalValue() + this.getPrimaryValue());
        }
    }

    public BonusElvenWarlordBeastmaster(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BonusElvenWarlordBeastmaster() {
        super();
    }

    public BonusElvenWarlordBeastmaster(BonusElvenWarlordBeastmaster other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BonusElvenWarlordBeastmaster(this);
    }

}


