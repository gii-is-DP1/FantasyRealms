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
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus.BonusBasicWizzardInHand;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus.BonusBookBellWizard;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus.BonusDwarvishOrDragon;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus.BonusElvenWarlordBeastmaster;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus.BonusForest;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus.BonusKing;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus.BonusLeaderShieldKeth;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus.BonusLeaderSwordKeth;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus.BonusLeaderWizard;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus.BonusMultipleTypes;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus.BonusQueen;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus.BonusRainstorm;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus.BonusSmokeAndWildfire;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus.BonusSwamp;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus.BonusWhirlwind;

/*
 * Se pretende probar el funcionamiento del applyMod para instancias de tipo Bonus (en la carpeta bonus)
 */
@ExtendWith(MockitoExtension.class)
public class ModBonusTests {

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
     * Modificador tipo BonusBasicWizzardInHand -> suma x si hay tipo Mago en la mano
    */

    // Caso 1: Hay dicho tipo en la mano

    @Test
    public void shouldBonusWizzardInHand() {

        // Creamos 2 cartas de tipo weather para el target, 1 la metemos en la mano

        Card card5 = new Card("Mago 1", CardType.MAGO, 10);
        playerHand.add(card5);

        Mod modTest = new BonusBasicWizzardInHand("BONUS: suma 15 si hay lider",
                15,
                null,
                playerHand.get(3),
                null, // No es necesario definir target para este modificador
                ModType.BONUS);

        // Llamamos al método a probar

        modTest.applyMod(playerHand);

        // Validamos el comportamiento

        assertEquals(20, playerHand.get(3).getFinalValue()); // 5 de la carta original + 15 por haber el tipo Mago en la mano

        // Verificamos que al haber 2 cartas de dicho tipo en la mano, solo sume una vez

        Card card6 = new Card("Mago 2", CardType.MAGO, 10);
        playerHand.add(card6);

        // Hacemos reset del valor de la carta origen del mod
        playerHand.get(3).setFinalValue(5);

        modTest.applyMod(playerHand);

        assertEquals(20, playerHand.get(3).getFinalValue()); // 5 de la carta original + 15 por haber el tipo Mago en la mano

    }

    // Caso 2: No hay dicho tipo en la mano

    @Test
    public void shouldNotBonusNoWizzardInHand() {

        Mod modTest = new BonusBasicWizzardInHand("BONUS: suma 15 si hay lider",
                15,
                null,
                playerHand.get(3),
                null, // No es necesario definir target para este modificador
                ModType.BONUS);

        // Llamamos al método a probar

        modTest.applyMod(playerHand);

        // Validamos el comportamiento

        assertEquals(5, playerHand.get(3).getFinalValue()); // 5 de la carta original y no hay bonus al no haber mago en la mano.

    }

    /*
     * Modificador tipo BonusBookBellWizard -> Suma 100 si hay Libro de cambios, Campanario y un MAGO
    */

    // Caso 1: Están las 3 cartas en la mano

    @Test
    public void shouldBonusBookBellWizard() {

        Card card5 = new Card("Libro de los Cambios", CardType.ARTEFACTO, 10);
        Card card6 = new Card("Campanario", CardType.TIERRA, 10);
        Card card7 = new Card("Mago 1", CardType.MAGO, 10);
        playerHand.add(card5);
        playerHand.add(card6);
        playerHand.add(card7);

        Mod modTest = new BonusBookBellWizard("Suma 100 si hay Libro de cambios, Campanario y un MAGO",
                100,
                null,
                playerHand.get(3),
                null, // No es necesario definir target para este modificador
                ModType.BONUS);

        // Llamamos al método a probar

        modTest.applyMod(playerHand);

        // Validamos el comportamiento

        assertEquals(105, playerHand.get(3).getFinalValue()); // 5 de la carta original + 100 por estar las 3 cartas

    }

    // Caso 2: No están las 3 cartas en la mano

    @Test
    public void shouldNotBonusBookBellWizard() {

        // Suponemos que no hay campanario
        Card card5 = new Card("Libro de cambios", CardType.ARTEFACTO, 10);
        Card card7 = new Card("Mago 1", CardType.MAGO, 10);
        playerHand.add(card5);
        playerHand.add(card7);

        Mod modTest = new BonusBookBellWizard("Suma 100 si hay Libro de cambios, Campanario y un MAGO",
                100,
                null,
                playerHand.get(3),
                null, // No es necesario definir target para este modificador
                ModType.BONUS);

        // Llamamos al método a probar

        modTest.applyMod(playerHand);

        // Validamos el comportamiento

        assertEquals(5, playerHand.get(3).getFinalValue()); // 5 de la carta original y no hay bonus al no haber campanario

    }

    /*
     * Modificador tipo BonusDwarvishOrDragon -> Suma 25 si hay "Infantería enana" o "Dragón"
    */

    // Caso 1: Están las 2 cartas en la mano y Caso 2: Solo hay una carta en la mano

    @Test
    public void shouldBonusDwarvishOrDragon() {

        // Caso 1

        Card card5 = new Card("Infanteria Enana", CardType.EJERCITO, 10);
        Card card7 = new Card("Dragon", CardType.BESTIA, 10);
        playerHand.add(card5);
        playerHand.add(card7);

        Mod modTest = new BonusDwarvishOrDragon("Suma 25 si hay Infantería enana o Dragón",
                25,
                null,
                playerHand.get(3),
                null, // No es necesario definir target para este modificador
                ModType.BONUS);

        // Llamamos al método a probar

        modTest.applyMod(playerHand);

        // Validamos el comportamiento

        assertEquals(30, playerHand.get(3).getFinalValue()); // 5 de la carta original y 25 del bonus

        // Caso 2
        
        playerHand.get(3).setFinalValue(5); // resel del final value de la carta origen del mod
        playerHand.remove(card7); // eliminamos por ejemplo el dragón

         // Llamamos al método a probar

         modTest.applyMod(playerHand);

         // Validamos el comportamiento
 
         assertEquals(30, playerHand.get(3).getFinalValue()); // 5 de la carta original y 25 del bonus

    }

    // Caso 3: No están ninguna de las 2

    @Test
    public void shouldNotBonusDwarvishOrDragon() {

        Mod modTest = new BonusDwarvishOrDragon("Suma 25 si hay Infantería enana o Dragón",
                25,
                null,
                playerHand.get(3),
                null, // No es necesario definir target para este modificador
                ModType.BONUS);

        // Llamamos al método a probar

        modTest.applyMod(playerHand);

        // Validamos el comportamiento

        assertEquals(5, playerHand.get(3).getFinalValue()); // 5 de la carta original y no hay bonus

    }

    /*
     * Modificador tipo BonusElvenWarlordBeastmaster -> Suma 30 si hay Arqueros élficos, Jefe militar o Maestro de bestias
    */

    // Caso 1: Están las 3 cartas en la mano, Caso 2: Hay 2 cartas en la mano y Caso 3: Hay 1 carta en la mano

    @Test
    public void shouldBonusElvenWarlordBeastmaste() {

        // Caso 1

        Card card5 = new Card("Arqueros élficos", CardType.EJERCITO, 10);
        Card card6 = new Card("Jefe militar", CardType.BESTIA, 10);
        Card card7 = new Card("Maestro de bestias", CardType.BESTIA, 10);
        playerHand.add(card5);
        playerHand.add(card6);
        playerHand.add(card7);

        Mod modTest = new BonusElvenWarlordBeastmaster("Suma 30 si hay Arqueros élficos, Jefe militar o Maestro de bestias",
                30,
                null,
                playerHand.get(3),
                null, // No es necesario definir target para este modificador
                ModType.BONUS);

        // Llamamos al método a probar

        modTest.applyMod(playerHand);

        // Validamos el comportamiento

        assertEquals(35, playerHand.get(3).getFinalValue()); // 5 de la carta original y 30 del bonus

        // Caso 2
        
        playerHand.get(3).setFinalValue(5); // resel del final value de la carta origen del mod
        playerHand.remove(card5); // eliminamos por ejemplo el dragón

        // Llamamos al método a probar

        modTest.applyMod(playerHand);

         // Validamos el comportamiento
 
        assertEquals(35, playerHand.get(3).getFinalValue()); // 5 de la carta original y 30 del bonus

        // Caso 3
        
        playerHand.get(3).setFinalValue(5); // resel del final value de la carta origen del mod
        playerHand.remove(card6); // eliminamos por ejemplo el dragón

        // Llamamos al método a probar

        modTest.applyMod(playerHand);

         // Validamos el comportamiento
 
        assertEquals(35, playerHand.get(3).getFinalValue()); // 5 de la carta original y 30 del bonus

    }

    // Caso 4: No hay ninguna carta de las pedidas en la mano

    @Test
    public void shouldNotBonusElvenWarlordBeastmaste() {

        Mod modTest = new BonusElvenWarlordBeastmaster("Suma 30 si hay Arqueros élficos, Jefe militar o Maestro de bestias",
                30,
                null,
                playerHand.get(3),
                null, // No es necesario definir target para este modificador
                ModType.BONUS);

        // Llamamos al método a probar

        modTest.applyMod(playerHand);

        // Validamos el comportamiento

        assertEquals(5, playerHand.get(3).getFinalValue()); // No suma

    }

    /*
     * Modificador tipo BonusLeaderShieldKeth -> Suma 15 si hay un Leader, suma 40 si hay un Líder y Espada de Keth
    */

    // Caso 1: Hay carta de tipo Líder y Escudo de Keth

    @Test
    void shouldBonusLeaderAndShieldOfKeth() {

        // Suponemos que ambas cartas están en la mano
        Card leaderCard = new Card("Líder", CardType.LIDER, 10);
        Card shieldOfKeth = new Card("Espada de Keth", CardType.ARTEFACTO, 10);
        playerHand.add(leaderCard);
        playerHand.add(shieldOfKeth);

        Mod modTest = new BonusLeaderShieldKeth(
                "Suma 15 si hay un Leader, suma 40 si hay un Líder y Espada de Keth",
                15, // Valor del bonus si solo está el Leader
                40, // Valor del bonus si están Leader y Shield of Keth
                playerHand.get(3),
                null,
                ModType.BONUS);

        // Llamamos al método a probar
        modTest.applyMod(playerHand);

        // Validamos el comportamiento
        assertEquals(45, playerHand.get(3).getFinalValue()); // 5 de la carta original + 40 del bonus al tener Leader y Shield of Keth
    }

    // Caso 2: Solo hay una carta de tipo Líder

    @Test
    void shouldBonusLeaderWithoutShieldOfKeth() {

        // Suponemos que solo el Leader está en la mano
        Card leaderCard = new Card("Leader", CardType.LIDER, 10);
        playerHand.add(leaderCard);

        Mod modTest = new BonusLeaderShieldKeth(
                "Suma 15 si hay un Leader, suma 40 si hay Leader y Shield of Keth",
                15, // Valor del bonus si solo está el Leader
                40, // Valor del bonus si están Leader y Shield of Keth
                playerHand.get(3),
                null,
                ModType.BONUS);

        // Llamamos al método a probar
        modTest.applyMod(playerHand);

        // Validamos el comportamiento
        assertEquals(20, playerHand.get(3).getFinalValue()); // 5 de la carta original + 15 del bonus al tener solo el Leader
    }

    // Caso 3: No hay ninguna de las cartas pedidas

    @Test
    void shouldNotBonusWithoutLeaderOrShieldOfKeth() {

        Mod modTest = new BonusLeaderShieldKeth(
                "Suma 15 si hay un Leader, suma 40 si hay Leader y Shield of Keth",
                15,
                40,
                playerHand.get(3),
                null,
                ModType.BONUS);

        // Llamamos al método a probar
        modTest.applyMod(playerHand);

        // Validamos el comportamiento
        assertEquals(5, playerHand.get(3).getFinalValue()); // No se aplica bonus, el valor se mantiene en 5
    }

    /*
     * Modificador tipo BonusKing -> +5 por cada Ejército, +20 por cada Ejército si está con Reina
     */

   // Caso 1: Hay 2 ejércitos en la mano y la Reina

    @Test
    void shouldBonusKingWithQueen() {
    
        // Suponemos que hay dos Ejércitos en la mano y la Reina
        Card cardArmy1 = new Card("Ejército 1", CardType.EJERCITO, 10);
        Card cardArmy2 = new Card("Ejército 2", CardType.EJERCITO, 15);
        Card cardQueen = new Card("Reina", CardType.LIDER, 20);
        playerHand.add(cardArmy1);
        playerHand.add(cardArmy2);
        playerHand.add(cardQueen);
    
        Mod modTest = new BonusKing(
                "Bonus: +5 por cada Ejército, +20 por cada Ejército si está en la misma mano que Reina",
                5,  // Valor del bonus por cada Ejército si no está con Reina
                20, // Valor del bonus por cada Ejército si está con Reina
                playerHand.get(3),
                null,
                ModType.BONUS);
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(45, playerHand.get(3).getFinalValue()); // 5 de la carta original + 20*2 (por cada Ejército con Reina)
    }
    
    // Caso 2: Hay ejércitos pero no reina en la mano

    @Test
    void shouldBonusKingWithoutQueen() {
    
        // Suponemos que hay dos Ejércitos en la mano sin Reina
        Card cardArmy1 = new Card("Ejército 1", CardType.EJERCITO, 10);
        Card cardArmy2 = new Card("Ejército 2", CardType.EJERCITO, 15);
        playerHand.add(cardArmy1);
        playerHand.add(cardArmy2);
    
        Mod modTest = new BonusKing(
                "Bonus: +5 por cada Ejército, +20 por cada Ejército si está en la misma mano que Reina",
                5,  // Valor del bonus por cada Ejército si no está con Reina
                20, // Valor del bonus por cada Ejército si está con Reina
                playerHand.get(3),
                null,
                ModType.BONUS);
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(15, playerHand.get(3).getFinalValue()); // 5 de la carta original + 5*2 (por cada Ejército sin Reina)
    }
    
    // Caso 3 -> No hay cartas de tipo ejército, no aplica el bonus

    @Test
    void shouldNotBonusKingWithoutArmies() {
    
        // Suponemos que no hay Ejércitos en la mano pero sí está la Reina
        Card cardQueen = new Card("Reina", CardType.LIDER, 20);
        playerHand.add(cardQueen);
    
        Mod modTest = new BonusKing(
                "Bonus: +5 por cada Ejército, +20 por cada Ejército si está en la misma mano que Reina",
                5,  // Valor del bonus por cada Ejército si no está con Reina
                20, // Valor del bonus por cada Ejército si está con Reina
                playerHand.get(3),
                null,
                ModType.BONUS);
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(5, playerHand.get(3).getFinalValue()); // 5 de la carta original ya que no hay Ejércitos
    }

    /*
     * Modificador tipo BonusLeaderSwordKeth -> +10 si hay un líder, +40 si hay un líder y la Espada de Keth
     */

    // Caso 1: Están ambas cartas en la mano

    @Test
    void shouldBonusLeaderAndSwordOfKeth() {
    
        // Suponemos que hay un líder y la Espada de Keth en la mano
        Card leaderCard = new Card("Líder 1", CardType.LIDER, 15);
        Card swordOfKeth = new Card("Escudo de Keth", CardType.ARTEFACTO, 5);
        playerHand.add(leaderCard);
        playerHand.add(swordOfKeth);
    
        Mod modTest = new BonusLeaderSwordKeth(
                "Bonus: +10 si hay líder, +40 si hay líder y Escudo de Keth",
                10,  // Valor del bonus si hay un líder
                40,  // Valor del bonus si hay un líder y la Espada de Keth
                playerHand.get(3),
                null,
                ModType.BONUS);
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(45, playerHand.get(3).getFinalValue()); // 5 de la carta original + 40 del bonus por el líder y la espada
    }
    
    // Caso 2: Hay líder pero no espada

    @Test
    void shouldBonusLeaderWithoutSwordOfKeth() {
    
        // Suponemos que hay solo un líder en la mano
        Card leaderCard = new Card("Líder 1", CardType.LIDER, 15);
        playerHand.add(leaderCard);
    
        Mod modTest = new BonusLeaderSwordKeth(
                "Bonus: +10 si hay líder, +40 si hay líder y Espada de Keth",
                10,  // Valor del bonus si hay un líder
                40,  // Valor del bonus si hay un líder y la Espada de Keth
                playerHand.get(3),
                null,
                ModType.BONUS);
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(15, playerHand.get(3).getFinalValue()); // 5 de la carta original + 10 del bonus por el líder
    }
    
    // Caso 3: No hay líder, no aplica la bonificación

    @Test
    void shouldNotBonusWithoutLeader() {
    
        // Suponemos que no hay líder en la mano, pero sí está la Espada de Keth
        Card swordOfKeth = new Card("Espada de Keth", CardType.ARTEFACTO, 5);
        playerHand.add(swordOfKeth);
    
        Mod modTest = new BonusLeaderSwordKeth(
                "Bonus: +10 si hay líder, +40 si hay líder y Espada de Keth",
                10,  // Valor del bonus si hay un líder
                40,  // Valor del bonus si hay un líder y la Espada de Keth
                playerHand.get(3),
                null,
                ModType.BONUS);
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(5, playerHand.get(3).getFinalValue()); // 5 de la carta original, sin bonus aplicado
    }

    /*
     * Modificador tipo BonusLeaderWizard -> Suma 14 si hay un Leader o un Wizard en la mano
    */

    // Caso 1: Están tanto el Leader como el Wizard en la mano, Caso 2: Solo está el Leader en la mano y Caso 3: Solo está el Wizard en la mano

    @Test
    void shouldBonusIfLeaderOrWizardInHand() {

        // Caso 1
        Card leaderCard = new Card("Leader", CardType.LIDER, 10);
        Card wizardCard = new Card("Wizard", CardType.MAGO, 10);
        playerHand.add(leaderCard);
        playerHand.add(wizardCard);

        Mod modTest = new BonusLeaderWizard(
                "Suma 14 si hay un Leader o un Wizard",
                14, // Valor del bonus
                null,
                playerHand.get(3),
                null,
                ModType.BONUS);

        // Llamamos al método a probar
        modTest.applyMod(playerHand);

        // Validamos el comportamiento
        assertEquals(19, playerHand.get(3).getFinalValue()); // 5 de la carta original + 14 del bonus al tener un Leader o un Wizard

        // Caso 2
        playerHand.get(3).setFinalValue(5); // Reset del valor final de la carta origen del mod
        playerHand.remove(wizardCard); // Eliminamos el Wizard

        // Llamamos al método a probar
        modTest.applyMod(playerHand);

        // Validamos el comportamiento
        assertEquals(19, playerHand.get(3).getFinalValue()); // 5 de la carta original + 14 del bonus al tener solo el Leader

        // Caso 3
        playerHand.get(3).setFinalValue(5); // Reset del valor final de la carta origen del mod
        playerHand.remove(leaderCard); // Eliminamos el Leader
        playerHand.add(wizardCard); // Añadimos el Wizard nuevamente

        // Llamamos al método a probar
        modTest.applyMod(playerHand);

        // Validamos el comportamiento
        assertEquals(19, playerHand.get(3).getFinalValue()); // 5 de la carta original + 14 del bonus al tener solo el Wizard
    }
    
    // Caso 4: No hay ni Leader ni Wizard en la mano y no se aplica ningún bonus
    @Test
    void shouldNotBonusIfNoLeaderOrWizardInHand() {
    
        Mod modTest = new BonusLeaderWizard(
                "Suma 14 si hay un Leader o un Wizard",
                14, // Valor del bonus
                null,
                playerHand.get(3),
                null,
                ModType.BONUS);
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(5, playerHand.get(3).getFinalValue()); // No se aplica bonus, el valor se mantiene en 5
    }

    /*
     * Modificador tipo BonusMultipleTypes -> Suma 30 si hay Princesa, suma 15 si hay Reina, Emperatriz o Hechicera Elemental
    */

    // Caso 1: Están tanto la Princesa como la Reina en la mano, Caso 2: Solo está la Princesa en la mano, Caso 3: Solo está la Reina en la mano, 
    // Caso 4: Está la Emperatriz en la mano y Caso 5: Está la Hechicera Elemental en la mano

    @Test
    void shouldApplyBonusForPrincessOrOthersInHand() {
    
        // Caso 1

        Card princessCard = new Card("Princesa", CardType.LIDER, 10);
        Card queenCard = new Card("Reina", CardType.LIDER, 10);
        playerHand.add(princessCard);
        playerHand.add(queenCard);
    
        Mod modTest = new BonusMultipleTypes(
                "Suma 30 si hay Princesa y 15 si hay Reina, Emperatriz o Hechicera Elemental",
                15,  // Valor del bonus por Reina, Emperatriz o Hechicera Elemental
                30,  // Valor del bonus por Princesa
                playerHand.get(3),
                null,
                ModType.BONUS);
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(50, playerHand.get(3).getFinalValue()); // 10 de la carta original + 30 por Princesa + 15 por Reina
    
        // Caso 2

        playerHand.get(3).setFinalValue(10); // Reset del valor final de la carta origen del mod
        playerHand.remove(queenCard); // Eliminamos la Reina de la mano
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(40, playerHand.get(3).getFinalValue()); // 10 de la carta original + 30 del bonus por tener solo la Princesa
    
        // Caso 3

        playerHand.get(3).setFinalValue(10); // Reset del valor final de la carta origen del mod
        playerHand.remove(princessCard); // Eliminamos la Princesa de la mano
        playerHand.add(queenCard); // Añadimos la Reina nuevamente
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(25, playerHand.get(3).getFinalValue()); // 10 de la carta original + 15 del bonus por tener solo la Reina
    
        // Caso 4

        playerHand.get(3).setFinalValue(10); // Reset del valor final de la carta origen del mod
        Card empressCard = new Card("Emperatriz", CardType.LIDER, 10);
        playerHand.add(empressCard);
        playerHand.remove(queenCard); // Eliminamos la Reina para este caso
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(25, playerHand.get(3).getFinalValue()); // 10 de la carta original + 15 del bonus por tener solo la Emperatriz
    
        // Caso 5

        playerHand.get(3).setFinalValue(10); // Reset del valor final de la carta origen del mod
        Card elementalEnchantress = new Card("Hechicera elemental", CardType.LIDER, 10);
        playerHand.add(elementalEnchantress);
        playerHand.remove(empressCard); // Eliminamos la Emperatriz para este caso
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(25, playerHand.get(3).getFinalValue()); // 10 de la carta original + 15 del bonus por tener solo la Hechicera Elemental
    }

    // Caso 6: No hay ninguna

    @Test
    void shouldNotBonusForPrincessOrOthersInHand() {
    
        Mod modTest = new BonusMultipleTypes(
                "Suma 30 si hay Princesa y 15 si hay Reina, Emperatriz o Hechicera Elemental",
                15,  // Valor del bonus por Reina, Emperatriz o Hechicera Elemental
                30,  // Valor del bonus por Princesa
                playerHand.get(3),
                null,
                ModType.BONUS);
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(5, playerHand.get(3).getFinalValue()); // 10 de la carta original + 30 por Princesa + 15 por Reina

    }

    /*
     * Modificador tipo BonusQueen -> Bonus: +5 por cada Army, +20 por cada Army si está en la misma mano que el King
    */

    // Caso 1: Están tanto el Rey como ejércitos en la mano y Caso 2: Solo están los ejércitos en la mano, sin Rey

    @Test
    void shouldBonusForQueenBasedOnArmyAndKingInHand() {

        // Caso 1

        Card kingCard = new Card("Rey", CardType.LIDER, 10);
        Card armyCard1 = new Card("Ejército 1", CardType.EJERCITO, 10);
        Card armyCard2 = new Card("Ejército 2", CardType.EJERCITO, 15);
        playerHand.add(kingCard);
        playerHand.add(armyCard1);
        playerHand.add(armyCard2);

        Mod modTest = new BonusQueen(
                "Bonus: +5 por cada Army, +20 por cada Army en la misma mano que el King",
                5, // Valor del bonus por cada Ejército si NO está el Rey
                20, // Valor del bonus por cada Ejército si SÍ está el Rey
                playerHand.get(3),
                null,
                ModType.BONUS);

        // Llamamos al método a probar
        modTest.applyMod(playerHand);

        // Validamos el comportamiento
        assertEquals(45, playerHand.get(3).getFinalValue()); // 5 de la carta original + (20 * 2 Ejércitos)

        // Caso 2

        playerHand.get(3).setFinalValue(5); // Reset del valor final de la carta origen del mod
        playerHand.remove(kingCard); // Eliminamos el Rey de la mano

        // Llamamos al método a probar
        modTest.applyMod(playerHand);

        // Validamos el comportamiento
        assertEquals(15, playerHand.get(3).getFinalValue()); // 5 de la carta original + (5 * 2 Ejércitos)
    }

    // Caso 3: No hay ejércitos en la mano, pero está el Rey

    @Test
    void shouldNotBonusForQueenBasedOnArmyAndKingInHand() {

        // Caso 3: No hay ejércitos en la mano, pero está el Rey
        Card kingCard = new Card("Rey", CardType.LIDER, 10);
        playerHand.add(kingCard);

        Mod modTest = new BonusQueen(
                "Bonus: +5 por cada Army, +20 por cada Army en la misma mano que el King",
                5, // Valor del bonus por cada Ejército si NO está el Rey
                20, // Valor del bonus por cada Ejército si SÍ está el Rey
                playerHand.get(3),
                null,
                ModType.BONUS);

        // Llamamos al método a probar
        modTest.applyMod(playerHand);

        // Validamos el comportamiento
        assertEquals(5, playerHand.get(3).getFinalValue()); // No hay ejércitos, por lo tanto no hay bonus

    }
    
    /*
     * Modificador tipo BonusRainstorm -> Suma 30 si hay una carta de "Tormenta" en la mano
     */

    // Caso 1: La carta "Tormenta" está en la mano

    @Test
    void shouldBonusIfRainstormInHand() {
    
        Card rainstormCard = new Card("Tormenta", CardType.TIEMPO, 10);
        playerHand.add(rainstormCard);
    
        Mod modTest = new BonusRainstorm(
                "Suma 30 si hay una carta de 'Tormenta' en la mano",
                30, // Valor del bonus si la carta "Tormenta" está en la mano
                null,
                playerHand.get(3),
                null,
                ModType.BONUS);
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(35, playerHand.get(3).getFinalValue()); // 5 de la carta original + 30 del bonus
    }
    
    // Caso 2: La carta "Tormenta" no está en la mano

    @Test
    void shouldNotBonusIfNoRainstormInHand() {
    
        // Creamos el modificador sin tener la carta "Tormenta" en la mano
        Mod modTest = new BonusRainstorm(
                "Suma 30 si hay una carta de 'Tormenta' en la mano",
                30, // Valor del bonus si la carta "Tormenta" está en la mano
                null,
                playerHand.get(3),
                null,
                ModType.BONUS);
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(5, playerHand.get(3).getFinalValue()); // No hay "Tormenta", el valor se mantiene igual
    }

    /*
     * Modificador tipo BonusSmokeAndWildfire -> Suma 50 si hay "Smoke" y "Wildfire" en la mano
    */
    
    // Caso 1: Ambas cartas están en la mano

    @Test
    void shouldApplyBonusIfSmokeAndWildfireInHand() {
    
        // Suponemos que ambas cartas están en la mano
        Card smokeCard = new Card("Humo", CardType.TIEMPO, 10);
        Card wildfireCard = new Card("Fuego Salvaje", CardType.LLAMA, 15);
        playerHand.add(smokeCard);
        playerHand.add(wildfireCard);
    
        Mod modTest = new BonusSmokeAndWildfire(
                "Suma 50 si hay 'Smoke' y 'Wildfire' en la mano",
                50, // Valor del bonus si ambas cartas están en la mano
                null,
                playerHand.get(3),
                null,
                ModType.BONUS);
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(55, playerHand.get(3).getFinalValue()); // 5 de la carta original + 50 del bonus al tener "Smoke" y "Wildfire"
    }
    
    // Caso 2: Falta una o ambas cartas

    @Test
    void shouldNotApplyBonusIfNoSmokeOrWildfireInHand() {
    
        // Caso donde no hay ninguna de las dos cartas
        Mod modTest = new BonusSmokeAndWildfire(
                "Suma 50 si hay 'Smoke' y 'Wildfire' en la mano",
                50,
                null,
                playerHand.get(3),
                null,
                ModType.BONUS);
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(5, playerHand.get(3).getFinalValue()); // No hay "Smoke" ni "Wildfire", el valor se mantiene igual
    
        // Caso donde solo está "Smoke" en la mano
        Card smokeCard = new Card("Smoke", CardType.TIEMPO, 10);
        playerHand.add(smokeCard);
    
        playerHand.get(3).setFinalValue(5); // Reset del valor final
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(5, playerHand.get(3).getFinalValue()); // Solo "Smoke", no se aplica el bonus
    
        // Caso donde solo está "Wildfire" en la mano
        playerHand.remove(smokeCard); // Quitamos "Smoke"
        Card wildfireCard = new Card("Wildfire", CardType.LLAMA, 15);
        playerHand.add(wildfireCard);
    
        playerHand.get(3).setFinalValue(5); // Reset del valor final
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(5, playerHand.get(3).getFinalValue()); // Solo "Wildfire", no se aplica el bonus
    }

    /*
     * Modificador tipo BonusSwamp -> Suma 28 si hay "Pantano" en la mano
    */
    
    // Caso 1: La carta "Pantano" está en la mano
    @Test
    void shouldApplyBonusIfSwampInHand() {
    
        Card swampCard = new Card("Pantano", CardType.TIERRA, 10);
        playerHand.add(swampCard);
    
        Mod modTest = new BonusSwamp(
                "Suma 28 si hay 'Pantano' en la mano",
                28, // Valor del bonus si "Pantano" está en la mano
                null,
                playerHand.get(3),
                null,
                ModType.BONUS);
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(33, playerHand.get(3).getFinalValue()); // 5 de la carta original + 28 del bonus al tener "Pantano"
    }
    
    // Caso 2: La carta "Pantano" no está en la mano

    @Test
    void shouldNotApplyBonusIfNoSwampInHand() {
    
        Mod modTest = new BonusSwamp(
                "Suma 28 si hay 'Pantano' en la mano",
                28,
                null,
                playerHand.get(3),
                null,
                ModType.BONUS);
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertEquals(5, playerHand.get(3).getFinalValue()); // No hay "Pantano", el valor se mantiene igual
    }

    /*
     * Modificador de la carta Torbellino: BonusWhirlwind
    */

    // Caso 1: Ambas cartas en la mano -> Debe sumar 40

    @Test
    void shouldApplyBonusWhirlwindBoth() {

        // Suponemos que la carta "Pantano" está en la mano
        Card whirlwind = new Card("Torbellino", CardType.TIEMPO, 10);
        playerHand.add(whirlwind);

        // Creamos y añadimos a la primera carta

        Mod modTest = new BonusWhirlwind(
            "",
            40,
            null,
            whirlwind,
            null,
            ModType.BONUS
        );

        whirlwind.setMods(List.of(modTest));

        // Cambiamos nombre de la segunda y tercera a los objetivos

        playerHand.get(1).setName("Tormenta");
        playerHand.get(2).setName("Ventisca");

        // Comprobamos
        modTest.applyMod(playerHand);

        assertEquals(whirlwind.getBaseValue()+40, whirlwind.getFinalValue());

        // Ahora probamos con Gran Inundacion

        whirlwind.setFinalValue(whirlwind.getBaseValue());

        playerHand.get(2).setName("Gran Inundacion");

        // Comprobamos
        modTest.applyMod(playerHand);

        assertEquals(whirlwind.getBaseValue()+40, whirlwind.getFinalValue());
    }

    // Caso 2: Solo una carta en la mano -> No debe sumar

    @Test
    void shouldApplyBonusOnlyOneCard() {

        // Suponemos que la carta "Pantano" está en la mano
        Card whirlwind = new Card("Torbellino", CardType.TIEMPO, 10);
        playerHand.add(whirlwind);

        // Creamos y añadimos a la primera carta

        Mod modTest = new BonusWhirlwind(
            "",
            40,
            null,
            whirlwind,
            null,
            ModType.BONUS
        );

        whirlwind.setMods(List.of(modTest));

        // Suponemos que solo hay Tormenta

        playerHand.get(1).setName("Tormenta");

        // Comprobamos
        modTest.applyMod(playerHand);

        assertEquals(whirlwind.getBaseValue(), whirlwind.getFinalValue());

        // Ahora probamos Ventisca sola en la mano

        playerHand.get(1).setName("Ventisca");

        // Comprobamos
        modTest.applyMod(playerHand);

        assertEquals(whirlwind.getBaseValue(), whirlwind.getFinalValue());
    }

    // Caso 3: Están las tres cartas en la mano -> Debe sumar 40

    @Test
    void shouldApplyBonusWhrlwindAllCards() {

        // Suponemos que la carta "Pantano" está en la mano
        Card whirlwind = new Card("Torbellino", CardType.TIEMPO, 10);
        playerHand.add(whirlwind);

        // Creamos y añadimos a la primera carta

        Mod modTest = new BonusWhirlwind(
            "",
            40,
            null,
            whirlwind,
            null,
            ModType.BONUS
        );

        whirlwind.setMods(List.of(modTest));

        // Cambiamos nombre de la segunda y tercera a los objetivos

        playerHand.get(1).setName("Tormenta");
        playerHand.get(2).setName("Ventisca");
        playerHand.get(3).setName("Gran Inundacion");

        // Comprobamos
        modTest.applyMod(playerHand);

        assertEquals(whirlwind.getBaseValue()+40, whirlwind.getFinalValue());
    }

    // Caso 4: Hay Tormenta y Gran Inundacion -> Debe sumar 40

    @Test
    void shouldApplyBonusWhirlwindStormAndFlood() {

        // Suponemos que la carta "Pantano" está en la mano
        Card whirlwind = new Card("Torbellino", CardType.TIEMPO, 10);
        playerHand.add(whirlwind);

        // Creamos y añadimos a la primera carta

        Mod modTest = new BonusWhirlwind(
            "",
            40,
            null,
            whirlwind,
            null,
            ModType.BONUS
        );

        whirlwind.setMods(List.of(modTest));

        // Cambiamos nombre de la segunda y tercera a los objetivos

        playerHand.get(1).setName("Tormenta");
        playerHand.get(2).setName("Gran Inundacion");

        // Comprobamos
        modTest.applyMod(playerHand);

        assertEquals(whirlwind.getBaseValue()+40, whirlwind.getFinalValue());
    }
    
    /*
     * Modificador de tipo BonusForest -> +12 por cada Bestia y Arqueros Élficos
    */

    // Caso 1: Hay 2 Bestias y 1 Arqueros Élficos

    @Test
    public void shouldApplyBonusForestMultiple() {

        // En la mano tenemos 2 bestias.

        // Creamos la carta de los arqueros

        Card archers = new Card("Arqueros Elficos", CardType.EJERCITO, 10);

        // Creamos el mod a probar y su carta

        Card originCard = new Card("Bosque", CardType.TIERRA, 7);

        Mod modTest = new BonusForest(
            "",
            12,
            null,
            originCard,
            null,
            ModType.BONUS
        );

        originCard.setMods(List.of(modTest));

        // Añadimos a la mano

        playerHand.add(archers);
        playerHand.add(originCard);

        // Aplicamos

        modTest.applyMod(playerHand);

        // Comprobamos que se ha aplicado el bonus -> 2 + 1 = 3 -> 3*12 = 36 puntos

        assertEquals(7+36, originCard.getFinalValue());
    }

    // Caso 2: Solo hay 1 Bestia o un Arquero
    @Test
    public void shouldApplyBonusForestOneCard() {

        // Dejamos solo una bestia en la mano

        playerHand.get(3).setCardType(CardType.TIERRA);

        // Creamos el mod a probar y su carta

        Card originCard = new Card("Bosque", CardType.TIERRA, 7);

        Mod modTest = new BonusForest(
            "",
            12,
            null,
            originCard,
            null,
            ModType.BONUS
        );

        originCard.setMods(List.of(modTest));

        // Añadimos a la mano

        playerHand.add(originCard);

        // Aplicamos

        modTest.applyMod(playerHand);

        // Comprobamos que se ha aplicado el bonus -> 1 bestia * 12 = 12 puntos

        assertEquals(7+12, originCard.getFinalValue());

        // Si solo hubiese un arquero

        // Ponemos todas las cartas de la mano a tipo tierra

        playerHand.get(2).setCardType(CardType.TIERRA);

        // Creamos la carta de los arqueros

        Card archers = new Card("Arqueros Elficos", CardType.EJERCITO, 10);
        playerHand.add(archers);

        // Reset del valor final de Bosque

        originCard.setFinalValue(originCard.getBaseValue());

        // Probamos que se ha vuelto a aplicar el bonus

        modTest.applyMod(playerHand);

        assertEquals(7+12, originCard.getFinalValue());
    }

    // Caso 3: No hay ninguna de las cartas
    @Test
    public void shouldNotApplyBonusForest() {

        // Cambiamos las cartas de tipo bestia por otro tipo

        playerHand.get(2).setCardType(CardType.TIERRA);
        playerHand.get(3).setCardType(CardType.TIERRA);

        // Creamos el mod a probar y su carta

        Card originCard = new Card("Bosque", CardType.TIERRA, 7);

        Mod modTest = new BonusForest(
            "",
            12,
            null,
            originCard,
            null,
            ModType.BONUS
        );

        originCard.setMods(List.of(modTest));

        // Aplicamos y vemos que no se ha aplicado el modificador

        modTest.applyMod(playerHand);

        assertEquals(originCard.getBaseValue(), originCard.getFinalValue());
    }
}
