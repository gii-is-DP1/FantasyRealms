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
@DiscriminatorValue("BONUS_RAINSTORM")
@TemplateMethodPattern.ConcreteClass
public class BonusRainstorm extends Mod{

    //handleRainstorm -> suma 30 si hay rainstorm

    @Override
    public void applyMod(List<Card> playerHand) {
        // Encontrar la carta en la mano del jugador
        Optional<Card> cardOpt = playerHand.stream().filter(c -> c.getName().equals("Tormenta")).findFirst();
        // Obtenemos la carta a la que pertenece el mod
        Card originCard = this.getOriginCard();

        // Verificamos si la carta existe o no y aplicamos la bonificación
        if (cardOpt.isPresent()) {
            // Si no existe la carta requerida
            originCard.setFinalValue(originCard.getFinalValue() +this.getPrimaryValue());
        }
    }

    public BonusRainstorm(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BonusRainstorm() {
        super();
    }

    public BonusRainstorm(BonusRainstorm other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BonusRainstorm(this);
    }
    
}
