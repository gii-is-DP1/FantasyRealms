package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus;

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
@DiscriminatorValue("BONUS_LEADER_SHIELD_KETH")
@TemplateMethodPattern.ConcreteClass
public class BonusLeaderShieldKeth extends Mod {

   // Suma 15 si hay un Leader, suma 40 si hay Leader y Shield of Keth

    @Override
    public void applyMod(List<Card> playerHand) {
        
        Card originCard = this.getOriginCard();

        // Buscar la carta por tipo (Leader) y por nombre (Shield of Keth)
        Optional<Card> leader = playerHand.stream().filter(c -> c.getCardType().equals(CardType.LIDER)).findFirst();
        Optional<Card> shieldOfKeth = playerHand.stream().filter(c -> c.getName().equals("Espada de Keth")).findFirst();

        // Si ambas cartas están presentes, aplica el bonus de +40
        if (leader.isPresent() && shieldOfKeth.isPresent()) {
            originCard.setFinalValue(originCard.getFinalValue() + this.getSecondaryValue());
        }
        // Si solo está el Leader, aplica el bonus de +15
        else if (leader.isPresent()) {
            originCard.setFinalValue(originCard.getFinalValue() + this.getPrimaryValue());
        }
    }

    public BonusLeaderShieldKeth(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }
    
    public BonusLeaderShieldKeth() {
        super();
    }

    public BonusLeaderShieldKeth(BonusLeaderShieldKeth other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BonusLeaderShieldKeth(this);
    }
}


