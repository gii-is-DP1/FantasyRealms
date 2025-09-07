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
@DiscriminatorValue("BONUS_LEADER_SWORD_KETH")
@TemplateMethodPattern.ConcreteClass
public class BonusLeaderSwordKeth extends Mod {

    // Bonus: +10 con cualquier Lider o +40 con Lider y Escudo de Keth
    
    @Override
    public void applyMod(List<Card> playerHand) {
        
        Card originCard = this.getOriginCard();

        // Buscamos un líder en la mano del jugador
        Optional<Card> leaderCard = playerHand.stream()
            .filter(c -> c.getCardType().equals(CardType.LIDER))
            .findFirst();

        // Buscamos la espada de Keth en la mano del jugador
        Optional<Card> shieldOfKeth = playerHand.stream()
            .filter(c -> c.getName().equals("Escudo de Keth"))
            .findFirst();

        // Si están ambos, aplicamos el valor secundario
        if (leaderCard.isPresent() && shieldOfKeth.isPresent()) {
            originCard.setFinalValue(originCard.getFinalValue() + this.getSecondaryValue());
        }
        // Si solo está el líder, aplicamos el valor primario
        else if (leaderCard.isPresent()) {
            originCard.setFinalValue(originCard.getFinalValue() + this.getPrimaryValue());
        }
    }

    public BonusLeaderSwordKeth(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BonusLeaderSwordKeth() {
        super();
    }

    public BonusLeaderSwordKeth(BonusLeaderSwordKeth other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BonusLeaderSwordKeth(this);
    }

}

