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
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.basic.BonusPenaltyForEachTypeMod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.basic.BonusPenaltyNoType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.blank.BlankTypes;

/*
 * Se pretende probar el funcionamiento del applyMod para instancias de modificadores básicas (en carpeta basic)
 */
@ExtendWith(MockitoExtension.class)
public class ModBasicTests {

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

    // Modificador de tipo "BonusPenaltyForEachTypeMod" -> +x por cada tipo "y" en la mano || Hay cartas de este tipo en la mano

    /*
     * Modificador de tipo "BonusPenaltyForEachTypeMod"
     *
    */

    // Caso 1: Bonus -> Hay cartas del tipo en la mano

    @Test
    void shouldBonusForEachTypeSingle() {

        // Carta que se va a añadir al target para verificar que solo se escogen del target las cartas de la mano

        Card card5 = new Card("Tierra 3", CardType.TIERRA, 20);

        Mod modTest = new BonusPenaltyForEachTypeMod("'Bonus: +1 por cada Tierra",
            1,
            null,
            playerHand.get(3),
            List.of(playerHand.get(0),playerHand.get(1),card5),
            ModType.BONUS);

        // Llamamos al método a probar

        modTest.applyMod(playerHand);

        // Validamos el comportamiento

        assertEquals(7, playerHand.get(3).getFinalValue()); // Valor original de la carta origen (5) + 1*2 al haber 2 cartas tipo Tierra en el mazo.
        // Para el resto de cartas su valor debe permanecer
        assertEquals(10, playerHand.get(0).getFinalValue()); 
        assertEquals(15, playerHand.get(1).getFinalValue());
        assertEquals(20, playerHand.get(2).getFinalValue());
    }

    // Caso 2: Bonus -> No hay cartas de este tipo en la mano

    @Test
    void shouldNotBonusForEachTypeSingle() {

        // Carta que se va a añadir al target para verificar que solo se escogen del target las cartas de la mano

        Card card5 = new Card("Llama 1", CardType.LLAMA, 20);

        Mod modTest = new BonusPenaltyForEachTypeMod("'Bonus: +1 por cada Llama",
            1,
            null,
            playerHand.get(3),
            List.of(card5), // Suponemos que solo hay una carta de tipo llama en todo el juego
            ModType.BONUS);

        // Llamamos al método a probar

        modTest.applyMod(playerHand);

        // Validamos el comportamiento

        assertEquals(5, playerHand.get(3).getFinalValue()); // Valor original de la carta origen (5) y no aplica bonus
        // Para el resto de cartas su valor debe permanecer
        assertEquals(10, playerHand.get(0).getFinalValue()); 
        assertEquals(15, playerHand.get(1).getFinalValue());
        assertEquals(20, playerHand.get(2).getFinalValue());
    }


    // Caso 3: Penalty -> Hay cartas de ese tipo en la mano

    @Test
    void shouldPenaltyForEachTypeSingle() {

        // Carta que se va a añadir al target para verificar que solo se escogen del target las cartas de la mano

        Card card5 = new Card("Tierra 3", CardType.TIERRA, 20);

        Mod modTest = new BonusPenaltyForEachTypeMod("'Bonus: -1 por cada Tierra",
        -1,
        null,
        playerHand.get(3),
        List.of(playerHand.get(0),playerHand.get(1),card5),
        ModType.PENALTY);

        // Llamamos al método a probar

        modTest.applyMod(playerHand);

        // Validamos el comportamiento

        assertEquals(3, playerHand.get(3).getFinalValue()); // Valor original de la carta origen (5) -1*2 al haber 2 cartas tipo Tierra en el mazo.
        // Para el resto de cartas su valor debe permanecer
        assertEquals(10, playerHand.get(0).getFinalValue()); 
        assertEquals(15, playerHand.get(1).getFinalValue());
        assertEquals(20, playerHand.get(2).getFinalValue());
    }

    // Caso 3: Penalty -> No hay cartas de ese tipo en la mano

    @Test
    void shouldNotPenaltyForEachTypeSingle() {

        // Carta que se va a añadir al target para verificar que solo se escogen del target las cartas de la mano

        Card card5 = new Card("Llama 1", CardType.LLAMA, 20);

        Mod modTest = new BonusPenaltyForEachTypeMod("'Bonus: -1 por cada Llama",
        -1,
        null,
        playerHand.get(3),
        List.of(card5), // suponemos que solo hay una carta de tipo llama en todo el juego
        ModType.PENALTY);

        // Llamamos al método a probar

        modTest.applyMod(playerHand);

        // Validamos el comportamiento

        assertEquals(5, playerHand.get(3).getFinalValue()); // Valor original de la carta origen y no hay penalización al no haber ese tipo en la mano
        // Para el resto de cartas su valor debe permanecer
        assertEquals(10, playerHand.get(0).getFinalValue()); 
        assertEquals(15, playerHand.get(1).getFinalValue());
        assertEquals(20, playerHand.get(2).getFinalValue());
    }

    // Caso 4: Bonus por varios tipos -> Hay cartas de esos tipos en la mano

    @Test
    void shouldBonusForEachTypeMultiple() {

        // Nueva carta para el modificador a testear

        Card card5 = new Card("Llama 1", CardType.LLAMA, 10);
        playerHand.add(card5);

        // Carta a añadir al target para comprobar funcionamiento correcto

        Card card6 = new Card("Tierra 3", CardType.TIERRA, 20);
        


        Mod modTest = new BonusPenaltyForEachTypeMod("'Bonus: +1 por cada Tierra y Bestia en la mano",
        1,
        null,
        playerHand.get(4),
        List.of(playerHand.get(0),playerHand.get(1),playerHand.get(2),playerHand.get(3),card6),
        ModType.BONUS);

        // Llamamos al método a probar

        modTest.applyMod(playerHand);

        // Validamos el comportamiento

        assertEquals(14, playerHand.get(4).getFinalValue()); // Valor original de la carta origen (10) + 1*2 Tierra + 1*2 Bestia en el mazo
        // Para el resto de cartas su valor debe permanecer
        assertEquals(10, playerHand.get(0).getFinalValue()); 
        assertEquals(15, playerHand.get(1).getFinalValue());
        assertEquals(20, playerHand.get(2).getFinalValue());
        assertEquals(5, playerHand.get(3).getFinalValue());
    }

    // Caso 4: Penalty por varios tipos -> Hay cartas de esos tipos en la mano

    @Test
    void shouldPenaltyForEachTypeMultiple() {

        // Nueva carta para el modificador a testear

        Card card5 = new Card("Llama 1", CardType.LLAMA, 10);
        playerHand.add(card5);

        // Carta a añadir al target para comprobar funcionamiento correcto

        Card card6 = new Card("Tierra 3", CardType.TIERRA, 20);


        Mod modTest = new BonusPenaltyForEachTypeMod("'Bonus: -1 por cada Tierra y Bestia en la mano",
            -1,
            null,
            playerHand.get(4),
            List.of(playerHand.get(0),playerHand.get(1),playerHand.get(2),playerHand.get(3),card6),
            ModType.PENALTY);

        // Llamamos al método a probar

        modTest.applyMod(playerHand);

        // Validamos el comportamiento

        assertEquals(6, playerHand.get(4).getFinalValue()); // Valor original de la carta origen (10) - 1*2 Tierra - 1*2 Bestia en el mazo
        // Para el resto de cartas su valor debe permanecer
        assertEquals(10, playerHand.get(0).getFinalValue()); 
        assertEquals(15, playerHand.get(1).getFinalValue());
        assertEquals(20, playerHand.get(2).getFinalValue());
        assertEquals(5, playerHand.get(3).getFinalValue());
    }

    /*
     * Modificador de tipo BonusPenaltyNoType
     */

    // Caso 1: Bonus por no haber x tipo en la mano -> No hay dicho tipo en la mano

    @Test
    public void shouldBonusNoTypeInHand() {

        // Creamos 2 cartas de tipo weather para el target

        Card card5 = new Card("Tiempo 1", CardType.TIEMPO, 10);
        Card card6 = new Card("Tiempo 2", CardType.TIEMPO, 10);

        Mod modTest = new BonusPenaltyNoType("'Bonus: suma 5 si no hay weather en la mano",
            5,
            null,
            playerHand.get(3),
            List.of(card5,card6), // Va a afectar a todas las cartas de tipo Weather -> suponemos que solo hay 2
            ModType.BONUS);
        
        // Llamamos al método a probar

        modTest.applyMod(playerHand);

        // Validamos el comportamiento

        assertEquals(10, playerHand.get(3).getFinalValue()); // 5 de la carta original + 5 por no haber tipo weather en la mano

    }

    // Caso 2: Bonus por no haber x tipo en la mano -> Hay dicho tipo en la mano

    @Test
    public void shouldNotBonusTypeInHand() {

        // Creamos 2 cartas de tipo weather para el target

        Card card5 = new Card("Tiempo 1", CardType.TIEMPO, 10);
        Card card6 = new Card("Tiempo 2", CardType.TIEMPO, 10);
        playerHand.add(card5);

        Mod modTest = new BonusPenaltyNoType("'Bonus: suma 5 si no hay weather en la mano",
                5,
                null,
                playerHand.get(3),
                List.of(card5, card6), // Va a afectar a todas las cartas de tipo Weather -> suponemos que solo hay 2
                ModType.BONUS);

        // Llamamos al método a probar

        modTest.applyMod(playerHand);

        // Validamos el comportamiento

        System.out.println(playerHand);

        assertEquals(5, playerHand.get(3).getFinalValue()); // 5 de la carta original -> No se aplica bonus porque hay
                                                            // carta de tipo weather en la mano

    }

    // Caso 3: Penalización salvo que haya x tipo en la mano -> Hay tipo en la mano

    @Test
    public void shouldNotPenaltyTypeInHand() {

        // Creamos 2 cartas de tipo weather para el target

        Card card5 = new Card("Lider 1", CardType.LIDER, 10);
        Card card6 = new Card("Tiempo 2", CardType.LIDER, 10);

        // Suponemos que card5 está en la mano

        playerHand.add(card5);

        Mod modTest = new BonusPenaltyNoType("'PENALIZACIÓN: resta 5 salvo que haya al menos un lider en la mano.",
            -5,
            null,
            playerHand.get(3),
            List.of(card5,card6), // Va a afectar a todas las cartas de tipo Lider -> suponemos que solo hay 2
            ModType.PENALTY);
        
        // Llamamos al método a probar

        modTest.applyMod(playerHand);

        // Validamos el comportamiento

        assertEquals(5, playerHand.get(3).getFinalValue()); // 5 de la carta original -> no penaliza porque hay carta de tipo lider en la mano

    }

    // Caso 4: Penalización salvo que haya x tipo en la mano -> No hay tipo en la mano

    @Test
    public void shouldPenaltyNoTypeInHand() {

        // Creamos 2 cartas de tipo weather para el target

        Card card5 = new Card("Lider 1", CardType.LIDER, 10);
        Card card6 = new Card("Tiempo 2", CardType.LIDER, 10);

        Mod modTest = new BonusPenaltyNoType("'PENALIZACIÓN: resta 5 salvo que haya al menos un lider en la mano.",
                -5,
                null,
                playerHand.get(3),
                List.of(card5, card6), // Va a afectar a todas las cartas de tipo Lider -> suponemos que solo hay 2
                ModType.PENALTY);

        // Llamamos al método a probar

        modTest.applyMod(playerHand);

        // Validamos el comportamiento

        assertEquals(0, playerHand.get(3).getFinalValue()); // 5 de la carta original -> no penaliza porque hay
                                                            // carta de tipo lider en la mano

    }
    
}
