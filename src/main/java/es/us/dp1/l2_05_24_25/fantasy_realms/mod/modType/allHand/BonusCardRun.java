package es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.allHand;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.jpatterns.gof.TemplateMethodPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BONUS_CARD_RUN_HAND")
@TemplateMethodPattern.ConcreteClass
public class BonusCardRun extends Mod {

    /*
     * +10 por una secuencia de 3 cartas, +30 por una secuencia de 4 cartas, +60 por
     * una secuencia de 5 cartas,
     * +100 por una secuencia de 6 cartas, +150 por una secuencia de 7 cartas.
     */

    // Con secuencia entiendo que las cartas tienen números o algún valor ordenado de forma consecutiva -> Si tienes una secuencia de 3 cartas consecutivas (ejemplo: 5, 6, 7), ganas un +10,etc.

    // Mi idea es ordenar la mano por baseValue -> mientras que la diferencia entre el valor de una carta y la siguiente sea 1 -> +1 para cardRun

    @Override
    public void applyMod(List<Card> playerHand) {

        Card originCard = this.getOriginCard();

        // Ordenamos la mano por valores base de las cartas

        List<Card> sortedHand = playerHand.stream().sorted(Comparator.comparingInt(c -> c.getBaseValue())).collect(Collectors.toList());

        // Aplicamos el método para calcular la secuencia

        int maxSequenceLength = findLongestSequence(sortedHand);

        // Calculamos el bonus en base a la longitud de la secuencia

        int bonus = switch(maxSequenceLength) {
            case 3 -> 10;
            case 4 -> 30;
            case 5 -> 60;
            case 6 -> 100;
            case 7 -> 150;
            default -> 0; // si no hay ninguna secuencia en la mano, no hay bonus
        };

        originCard.setFinalValue(originCard.getFinalValue() + bonus);

    }

    private int findLongestSequence(List<Card> sortedHand) {
        int maxSequence = 1;  // La longitud máxima de secuencia encontrada
        int currentSequence = 1;  // La longitud de la secuencia actual
    
        for (int i = 1; i < sortedHand.size(); i++) {
            int currentValue = sortedHand.get(i).getBaseValue();
            int previousValue = sortedHand.get(i - 1).getBaseValue();
    
            if (currentValue == previousValue + 1) {
                currentSequence++;  // Incrementamos la secuencia actual
                maxSequence = Math.max(maxSequence, currentSequence);  // Actualizamos la secuencia máxima si es necesario con el currentSequence
            } else {
                currentSequence = 1;  // Reiniciamos la secuencia actual si se corta la secuencia
            }
        }
    
        return maxSequence;  // Devolvemos la secuencia máxima encontrada
    }

    public BonusCardRun(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        super(description,primaryValue,secondaryValue,originCard,target,modType);
    }

    public BonusCardRun() {
        super();
    }

    public BonusCardRun(BonusCardRun other) {
        super(other);
    }

    @Override
    public Mod clone() {
        return new BonusCardRun(this);
    }
    

}
