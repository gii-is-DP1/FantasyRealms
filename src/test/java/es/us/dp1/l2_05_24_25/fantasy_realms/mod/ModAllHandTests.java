package es.us.dp1.l2_05_24_25.fantasy_realms.mod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.allHand.BonusCardRun;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.allHand.BonusDifferentSuit;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.allHand.BonusSameSuit;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.basic.BonusPenaltyForEachTypeMod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.basic.BonusPenaltyNoType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.blank.BlankTypes;

/*
 * Se pretende probar el funcionamiento del applyMod con instancias de tipo ALLHAND (en la carpeta allHand)
 */
@ExtendWith(MockitoExtension.class)
public class ModAllHandTests {

        private List<Card> playerHand;

    @BeforeEach
    void setUp() {

        this.playerHand = new ArrayList<>();

        Card card1 = new Card("Tierra 1", CardType.TIERRA, 10);
        Card card2 = new Card("Tierra 2", CardType.TIERRA, 15);
        Card card3 = new Card("Bestia 1", CardType.BESTIA, 20);
        Card card4 = new Card("Bestia 2", CardType.BESTIA, 5);

        // Añadimos modificadores a las cartas para probar la eliminación
        Mod penaltyMod1 = new BonusPenaltyForEachTypeMod("Penalty 1", -5, null, card1, null, ModType.PENALTY);
        Mod blankNoPenalty1 = new BlankTypes("Blank 1", null, null, card1, null, ModType.BLANK);
        Mod blankPenalty2 = new BlankTypes("Blank 2", -10, null, card2, null, ModType.PENALTY);
        Mod bonusMod = new BonusPenaltyNoType("Bonus", 5, null, card3, null, ModType.BONUS);

        card1.setMods(new ArrayList<>(List.of(penaltyMod1,blankNoPenalty1)));
        card2.setMods(new ArrayList<>(List.of(blankPenalty2)));
        card3.setMods(new ArrayList<>(List.of(bonusMod)));

        playerHand.add(card1);
        playerHand.add(card2);
        playerHand.add(card3);
        playerHand.add(card4);

    }

    /*
     * Modificador de tipo BonusCardRun: 10 por una secuencia de 3 cartas, +30 por una secuencia de 4 cartas, +60 por  una secuencia de 5 cartas, +100 por una secuencia de 6 cartas, +150 por una secuencia de 7 cartas.
    */

    // Caso 1: Secuencia de dos cartas no suma. Vamos a usar por ejemplo la carta 1 y 3 de la mano

    @Test
    void shouldNotBonusCardRun() {

        playerHand.get(0).setBaseValue(100);
        playerHand.get(2).setBaseValue(101);

        Mod modTest = new BonusCardRun(
            "10 por una secuencia de 3 cartas, +30 por una secuencia de 4 cartas, +60 por  una secuencia de 5 cartas, +100 por una secuencia de 6 cartas, +150 por una secuencia de 7 cartas.",
            null,
            null,
            playerHand.get(3),
            null,
            ModType.STATE);

        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        modTest.applyMod(playerHand);
        
        // Verificamos que no se ha sumando ningún valor a la carta origen del mod

        assertEquals(5, playerHand.get(3).getFinalValue());

    }

    // Caso 2: Secuencia de tres cartas suma 10. Vamos a usar por ejemplo la carta 1,2 y 3 de la mano

    @Test
    void shouldBonusTenCardRun() {

        playerHand.get(0).setBaseValue(100);
        playerHand.get(2).setBaseValue(101);
        playerHand.get(1).setBaseValue(102);

        Mod modTest = new BonusCardRun(
            "10 por una secuencia de 3 cartas, +30 por una secuencia de 4 cartas, +60 por  una secuencia de 5 cartas, +100 por una secuencia de 6 cartas, +150 por una secuencia de 7 cartas.",
            null,
            null,
            playerHand.get(3),
            null,
            ModType.STATE);

        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        modTest.applyMod(playerHand);
        
        // Verificamos que no se ha sumando ningún valor a la carta origen del mod

        assertEquals(15, playerHand.get(3).getFinalValue()); // Debería sumar 5 (valor original) + 10 por bonus

    }

    // Caso 3: Secuencia de cuatro cartas suma 30. Vamos a usar por ejemplo la carta 1,2,3 y 4 de la mano

    @Test
    void shouldBonusThirtyCardRun() {

        playerHand.get(0).setBaseValue(102);
        playerHand.get(2).setBaseValue(101);
        playerHand.get(1).setBaseValue(103);
        playerHand.get(3).setBaseValue(100);

        Mod modTest = new BonusCardRun(
            "10 por una secuencia de 3 cartas, +30 por una secuencia de 4 cartas, +60 por  una secuencia de 5 cartas, +100 por una secuencia de 6 cartas, +150 por una secuencia de 7 cartas.",
            null,
            null,
            playerHand.get(3),
            null,
            ModType.STATE);

        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        modTest.applyMod(playerHand);
        
        // Verificamos que no se ha sumando ningún valor a la carta origen del mod

        assertEquals(35, playerHand.get(3).getFinalValue()); // Debería sumar 5 (valor original) + 30 por bonus

    }

    // Caso 4: Secuencia de cinco cartas suma 60. Añadimos una carta

    @Test
    void shouldBonusSixtyCardRun() {

        Card card5 = new Card("Tiempo 1", CardType.TIEMPO, 104);
        playerHand.add(card5);

        playerHand.get(0).setBaseValue(102);
        playerHand.get(2).setBaseValue(101);
        playerHand.get(1).setBaseValue(103);
        playerHand.get(3).setBaseValue(100);

        Mod modTest = new BonusCardRun(
            "10 por una secuencia de 3 cartas, +30 por una secuencia de 4 cartas, +60 por  una secuencia de 5 cartas, +100 por una secuencia de 6 cartas, +150 por una secuencia de 7 cartas.",
            null,
            null,
            playerHand.get(3),
            null,
            ModType.STATE);

        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        modTest.applyMod(playerHand);
        
        // Verificamos que no se ha sumando ningún valor a la carta origen del mod

        assertEquals(65, playerHand.get(3).getFinalValue()); // Debería sumar 5 (valor original) + 60 por bonus

    }

    // Caso 5: Secuencia de seis cartas suma 100. Añadimos dos carta

    @Test
    void shouldBonusHundredCardRun() {

        Card card5 = new Card("Tiempo 1", CardType.TIEMPO, 104);
        Card card6 = new Card("Tiempo 1", CardType.TIEMPO, 105);
        playerHand.add(card5);
        playerHand.add(card6);

        playerHand.get(0).setBaseValue(102);
        playerHand.get(2).setBaseValue(101);
        playerHand.get(1).setBaseValue(103);
        playerHand.get(3).setBaseValue(100);

        Mod modTest = new BonusCardRun(
            "10 por una secuencia de 3 cartas, +30 por una secuencia de 4 cartas, +60 por  una secuencia de 5 cartas, +100 por una secuencia de 6 cartas, +150 por una secuencia de 7 cartas.",
            null,
            null,
            playerHand.get(3),
            null,
            ModType.STATE);

        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        modTest.applyMod(playerHand);
        
        // Verificamos que no se ha sumando ningún valor a la carta origen del mod

        assertEquals(105, playerHand.get(3).getFinalValue()); // Debería sumar 5 (valor original) + 100 por bonus

    }

    // Caso 6: Secuencia de siete cartas suma 150. Añadimos tres carta

    @Test
    void shouldBonusHundredAndFiftyCardRun() {

        Card card5 = new Card("Tiempo 1", CardType.TIEMPO, 105);
        Card card6 = new Card("Tiempo 2", CardType.TIEMPO, 106);
        Card card7 = new Card("Tiempo 3", CardType.TIEMPO, 104);
        playerHand.add(card5);
        playerHand.add(card6);
        playerHand.add(card7);

        playerHand.get(0).setBaseValue(102);
        playerHand.get(2).setBaseValue(101);
        playerHand.get(1).setBaseValue(103);
        playerHand.get(3).setBaseValue(100);

        Mod modTest = new BonusCardRun(
            "10 por una secuencia de 3 cartas, +30 por una secuencia de 4 cartas, +60 por  una secuencia de 5 cartas, +100 por una secuencia de 6 cartas, +150 por una secuencia de 7 cartas.",
            null,
            null,
            playerHand.get(3),
            null,
            ModType.ALLHAND);

        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        modTest.applyMod(playerHand);
        
        // Verificamos que no se ha sumando ningún valor a la carta origen del mod

        assertEquals(155, playerHand.get(3).getFinalValue()); // Debería sumar 5 (valor original) + 150 por bonus

    }

    /*
     * Modificador de tipo BonusDifferentSuit -> Suma 50 si todas las cartas de la mano son de distinto tipo.
    */

    // Caso 1 : todas las cartas de la mano son de distinto tipo

    @Test
    void shouldBonusDifferentSuit() {

        playerHand.get(0).setCardType(CardType.ARMA);
        playerHand.get(1).setCardType(CardType.ARTEFACTO);
        playerHand.get(2).setCardType(CardType.BESTIA);
        playerHand.get(3).setCardType(CardType.EJERCITO);

        // Añadimos las cartas que faltan

        Card card5 = new Card("Inundación 1", CardType.INUNDACION, 105);
        Card card6 = new Card("Lider 1", CardType.LIDER, 106);
        Card card7 = new Card("Llama 1", CardType.LLAMA, 104);

        playerHand.add(card5);
        playerHand.add(card6);
        playerHand.add(card7);

        Mod modTest = new BonusDifferentSuit(
            "Suma 50 si todas las cartas de la mano son de distinto tipo.",
            50,
            null,
            playerHand.get(3),
            null,
            ModType.ALLHAND);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Probamos

        modTest.applyMod(playerHand);

        // Verificamos el resultado

        assertEquals(55, playerHand.get(3).getFinalValue()); // suma 5 del valor original + 50 por bonus

    }

    // Caso 2 : no todas las cartas de la mano son de distinto tipo

    @Test
    void shouldNotBonusDifferentSuit() {

        // Con dejar los tipos de la parte de la mano original ya no se cumple, porque hay dos tierras y dos bestias

        // Añadimos las cartas que faltan

        Card card5 = new Card("Inundación 1", CardType.INUNDACION, 105);
        Card card6 = new Card("Lider 1", CardType.LIDER, 106);
        Card card7 = new Card("Llama 1", CardType.LLAMA, 104);

        playerHand.add(card5);
        playerHand.add(card6);
        playerHand.add(card7);

        Mod modTest = new BonusDifferentSuit(
            "Suma 50 si todas las cartas de la mano son de distinto tipo.",
            50,
            null,
            playerHand.get(3),
            null,
            ModType.ALLHAND);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Probamos

        modTest.applyMod(playerHand);

        // Verificamos el resultado

        assertEquals(5, playerHand.get(3).getFinalValue()); // suma 5 del valor original y no se aplica bonus

    }

    /*
     * Modificador de tipo BonusSameSuit: Bonificación: +10 si hay tres cartas diferentes de un mismo tipo, +40 si hay cuatro, +100 si hay cinco.
    */

    // Caso 1: No hay cartas diferentes de un mismo tipo. -> reutilización del test shouldBonusDifferentSuit

    @Test
    void shouldNotBonusAllCardsDifferentSuit() {

        playerHand.get(0).setCardType(CardType.ARMA);
        playerHand.get(1).setCardType(CardType.ARTEFACTO);
        playerHand.get(2).setCardType(CardType.BESTIA);
        playerHand.get(3).setCardType(CardType.EJERCITO);

        // Añadimos las cartas que faltan

        Card card5 = new Card("Inundación 1", CardType.INUNDACION, 105);
        Card card6 = new Card("Lider 1", CardType.LIDER, 106);
        Card card7 = new Card("Llama 1", CardType.LLAMA, 104);

        playerHand.add(card5);
        playerHand.add(card6);
        playerHand.add(card7);

        Mod modTest = new BonusSameSuit(
            "+10 si hay tres cartas diferentes de un mismo tipo, +40 si hay cuatro, +100 si hay cinco.",
            null,
            null,
            playerHand.get(3),
            null,
            ModType.ALLHAND);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Probamos

        modTest.applyMod(playerHand);

        // Verificamos el resultado

        assertEquals(5, playerHand.get(3).getFinalValue()); // suma 5 del valor original y no hay bonus

    }

    // Caso 2: Hay 3 cartas diferentes de un mismo tipo. -> reutilización del test shouldBonusDifferentSuit

    @Test
    void shouldBonusThreeCardsSametSuit() {

        playerHand.get(0).setCardType(CardType.ARMA);
        playerHand.get(1).setCardType(CardType.ARMA);
        playerHand.get(2).setCardType(CardType.ARMA);
        playerHand.get(3).setCardType(CardType.EJERCITO);

        // Añadimos las cartas que faltan

        Card card5 = new Card("Inundación 1", CardType.INUNDACION, 105);
        Card card6 = new Card("Lider 1", CardType.LIDER, 106);
        Card card7 = new Card("Llama 1", CardType.LLAMA, 104);

        playerHand.add(card5);
        playerHand.add(card6);
        playerHand.add(card7);

        Mod modTest = new BonusSameSuit(
            "+10 si hay tres cartas diferentes de un mismo tipo, +40 si hay cuatro, +100 si hay cinco.",
            10,
            40,
            playerHand.get(3),
            null,
            ModType.ALLHAND);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Probamos

        modTest.applyMod(playerHand);

        // Verificamos el resultado

        assertEquals(15, playerHand.get(3).getFinalValue()); // suma 5 del valor original y no hay bonus

    }

    // Caso 3: Hay 2 grupos de 3 cartas diferentes de un mismo tipo -> debería sumar 20 (10 + 10)

    @Test
    void shouldBonusTwiceThreeCardsSametSuit() {

        playerHand.get(0).setCardType(CardType.ARMA);
        playerHand.get(1).setCardType(CardType.ARMA);
        playerHand.get(2).setCardType(CardType.ARMA);
        playerHand.get(3).setCardType(CardType.EJERCITO);

        // Añadimos las cartas que faltan

        Card card5 = new Card("Inundación 1", CardType.EJERCITO, 105);
        Card card6 = new Card("Lider 1", CardType.EJERCITO, 106);
        Card card7 = new Card("Llama 1", CardType.INUNDACION, 104);

        playerHand.add(card5);
        playerHand.add(card6);
        playerHand.add(card7);

        Mod modTest = new BonusSameSuit(
            "+10 si hay tres cartas diferentes de un mismo tipo, +40 si hay cuatro, +100 si hay cinco.",
            10,
            40,
            playerHand.get(3),
            null,
            ModType.ALLHAND);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Probamos

        modTest.applyMod(playerHand);

        // Verificamos el resultado

        assertEquals(25, playerHand.get(3).getFinalValue()); // suma 5 del valor original y 20 del bonus

    }

    // Caso 4: Hay 1 grupo de 3 y un grupo de 4 -> debería sumar 50 (10 + 40)

    @Test
    void shouldBonusTwoGroupsCardsSametSuit() {

        playerHand.get(0).setCardType(CardType.ARMA);
        playerHand.get(1).setCardType(CardType.ARMA);
        playerHand.get(2).setCardType(CardType.ARMA);
        playerHand.get(3).setCardType(CardType.EJERCITO);

        // Añadimos las cartas que faltan

        Card card5 = new Card("Inundación 1", CardType.EJERCITO, 105);
        Card card6 = new Card("Lider 1", CardType.EJERCITO, 106);
        Card card7 = new Card("Llama 1", CardType.EJERCITO, 104);

        playerHand.add(card5);
        playerHand.add(card6);
        playerHand.add(card7);

        Mod modTest = new BonusSameSuit(
            "+10 si hay tres cartas diferentes de un mismo tipo, +40 si hay cuatro, +100 si hay cinco.",
            10,
            40,
            playerHand.get(3),
            null,
            ModType.ALLHAND);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Probamos

        modTest.applyMod(playerHand);

        // Verificamos el resultado

        assertEquals(55, playerHand.get(3).getFinalValue()); // suma 5 del valor original y 50 del bonus

    }

    // Caso 5: Hay 1 un grupo de 4 -> debería sumar 40

    @Test
    void shouldBonusFourCardsSametSuit() {

        playerHand.get(0).setCardType(CardType.ARMA);
        playerHand.get(1).setCardType(CardType.ARMA);
        playerHand.get(2).setCardType(CardType.ARMA);
        playerHand.get(3).setCardType(CardType.ARMA);

        // Añadimos las cartas que faltan

        Card card5 = new Card("Inundación 1", CardType.INUNDACION, 105);
        Card card6 = new Card("Lider 1", CardType.SALVAJE, 106);
        Card card7 = new Card("Llama 1", CardType.MAGO, 104);

        playerHand.add(card5);
        playerHand.add(card6);
        playerHand.add(card7);

        Mod modTest = new BonusSameSuit(
            "+10 si hay tres cartas diferentes de un mismo tipo, +40 si hay cuatro, +100 si hay cinco.",
            10,
            40,
            playerHand.get(3),
            null,
            ModType.ALLHAND);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Probamos

        modTest.applyMod(playerHand);

        // Verificamos el resultado

        assertEquals(45, playerHand.get(3).getFinalValue()); // suma 5 del valor original y 40 del bonus

    }

    // Caso 6: Hay 1 un grupo de 5 -> debería sumar 100

    @Test
    void shouldBonusFiveCardsSametSuit() {

        playerHand.get(0).setCardType(CardType.ARMA);
        playerHand.get(1).setCardType(CardType.ARMA);
        playerHand.get(2).setCardType(CardType.ARMA);
        playerHand.get(3).setCardType(CardType.ARMA);

        // Añadimos las cartas que faltan

        Card card5 = new Card("Inundación 1", CardType.ARMA, 105);
        Card card6 = new Card("Lider 1", CardType.SALVAJE, 106);
        Card card7 = new Card("Llama 1", CardType.MAGO, 104);

        playerHand.add(card5);
        playerHand.add(card6);
        playerHand.add(card7);

        Mod modTest = new BonusSameSuit(
            "+10 si hay tres cartas diferentes de un mismo tipo, +40 si hay cuatro, +100 si hay cinco.",
            10,
            40,
            playerHand.get(3),
            null,
            ModType.ALLHAND);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Probamos

        modTest.applyMod(playerHand);

        // Verificamos el resultado

        assertEquals(105, playerHand.get(3).getFinalValue()); // suma 5 del valor original y 100 del bonus

    }
    
}
