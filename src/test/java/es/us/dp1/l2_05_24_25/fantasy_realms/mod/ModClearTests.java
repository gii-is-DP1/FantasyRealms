package es.us.dp1.l2_05_24_25.fantasy_realms.mod;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ModStatesException;

import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.basic.BonusPenaltyForEachTypeMod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.basic.BonusPenaltyNoType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.blank.BlankTypes;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.clear.ClearAllPenalties;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.clear.ClearArmyPenaltyFlood;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.clear.ClearArmyPenaltyHand;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.clear.ClearPenaltyForType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.clear.ClearPenaltyIsland;

/*
 * Se pretende probar el funcionamiento del applyMod con las instancias de tipo Clear (carpeta clear)
 */
@ExtendWith(MockitoExtension.class)
public class ModClearTests {

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
     * Modificador de tipo ClearAllPenalties -> Borra la sección de penalización de todas las cartas de la mano
    */

    @Test
    void shouldClearAllPenalties() {

        // En el setup tenemos definidos varios modificadores. Hay que tener en cuenta que hay modificadores Blank que son penalizaciones (ModType.PENALTY) y otros que no (ModType.BLANK)

        // Definimos el modificador ClearAllPenalties para aplicar a toda la mano
        Mod modTest = new ClearAllPenalties(
                "Borra la sección de penalización de todas las cartas de la mano",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.CLEAR);

        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Validamos el comportamiento previo
        assertFalse(playerHand.get(0).getMods().isEmpty()); // Carta 1 tiene un penalizador inicialmente
        assertFalse(playerHand.get(1).getMods().isEmpty()); // Carta 2 tiene un penalizador inicialmente
        assertFalse(playerHand.get(2).getMods().isEmpty()); // Carta 3 tiene un modificador de tipo BONUS inicialmente
        assertFalse(playerHand.get(3).getMods().isEmpty()); // Carta 4 no tiene modificadores

        // Aplicamos el modificador para eliminar todos los PENALTY
        modTest.applyMod(playerHand);

        // Validamos el comportamiento esperado
        assertFalse(playerHand.get(0).getMods().isEmpty()); // Carta 1 debe permanecer con el modificador de tipo blank no penalty
        assertEquals(ModType.BLANK,playerHand.get(0).getMods().get(0).getModType());
        assertTrue(playerHand.get(1).getMods().isEmpty()); // Carta 2 tiene un blank tipo penalty -> la carta debe quedarse sin mods
        assertFalse(playerHand.get(2).getMods().isEmpty()); // Carta 3 tiene un modificador de tipo BONUS inicialmente
        assertFalse(playerHand.get(3).getMods().isEmpty()); // Carta 4 tiene el modificador modTest que no es de tipo Penalty
    }

    /*
     * Modificador tipo ClearArmyPenaltyFlood -> Elimina la palabra Ejército de la sección de penalización de todos los "Floods"
    */

    @Test
    void shouldClearArmyPenaltyFromFloods() {

        // Añadimos cartas de tipo ejército de prueba para el target

        Card card5 = new Card("Ejercito 1", CardType.EJERCITO, 20);
        Card card6 = new Card("Ejercito 2", CardType.EJERCITO, 20);

        // Añadimos carta de tipo inundación

        Card card7 = new Card("Inundación 1", CardType.INUNDACION, 20);

        // Modificadores a probar

        Mod penaltyMod1 = new BonusPenaltyForEachTypeMod("PENALIZACIÓN: Resta 10 por cada ejército", -5, null, playerHand.get(0), List.of(card5,card6), ModType.PENALTY); // el target debería quedar vacío y no aplicar penalización
        Mod penaltyMod2 = new BlankTypes("PENALIZACIÓN: Anula todos los ejércitos y bestias", -5, null, playerHand.get(0), List.of(card5,card6,playerHand.get(2),playerHand.get(3)), ModType.PENALTY); // elimina del target el ejército
        Mod noPenaltyMod3 = new BlankTypes("Anula todos los ejércitos y bestias", -5, null, playerHand.get(0), List.of(card5,card6,playerHand.get(2),playerHand.get(3)), ModType.BLANK); // no debe hacer nada porque no es penalización

        card7.setMods(new ArrayList<>(List.of(penaltyMod1,penaltyMod2,noPenaltyMod3)));

        playerHand.add(card7);

        // Añadimos modificador de pruebas para carta tipo tierra

        Mod penaltyMod3 = new BonusPenaltyForEachTypeMod("PENALIZACIÓN: Resta 10 por cada ejército", -5, null, playerHand.get(0), List.of(card5,card6), ModType.PENALTY); // no debe hacer nada porque es un tipo tierra
        playerHand.get(0).setMods(new ArrayList<>(List.of(penaltyMod3)));

        // Definimos el modificador ClearArmyPenaltyFlood para aplicar a toda la mano
        Mod modTest = new ClearArmyPenaltyFlood(
                "Elimina la palabra Ejército de la sección de penalización de todos los Floods",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.CLEAR);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Validamos el comportamiento previo
        assertFalse(card7.getMods().isEmpty()); // La carta de tipo Inundación (card7) tiene modificadores inicialmente
        assertFalse(playerHand.get(0).getMods().isEmpty()); // Carta de tipo Tierra tiene un penalizador inicialmente
        assertEquals(3, card7.getMods().size()); // Validamos que la carta de tipo Inundación tiene tres modificadores

        // Aplicamos el modificador para eliminar el objetivo "Ejército" de la sección de penalización de las cartas de tipo "Inundación"
        modTest.applyMod(playerHand);

        // Validamos el comportamiento esperado

        // Carta de tipo "Inundación" (card7)
        // Deben eliminarse el tipo "Ejército" en los modificadores de tipo PENALTY de la carta de tipo Inundación
        assertEquals(3, card7.getMods().size()); // La cantidad de modificadores debe permanecer igual
        assertTrue(card7.getMods().get(0).getTarget().isEmpty()); // El primer modificador de penalización debe tener el target vacío
        assertTrue(card7.getMods().get(1).getTarget().stream().noneMatch(c -> c.getCardType().equals(CardType.EJERCITO))); // El segundo modificador debe eliminar los ejércitos de su target

        // Carta de tipo "Tierra" (playerHand.get(0))
        // La carta de tipo "Tierra" no debe ser afectada por el modificador `ClearArmyPenaltyFlood`
        assertFalse(playerHand.get(0).getMods().isEmpty());
        assertEquals(1, playerHand.get(0).getMods().size()); // La carta de tipo "Tierra" debe tener su penalizador original intacto
        assertTrue(playerHand.get(0).getMods().get(0).getTarget().contains(card5)); // La carta de tipo "Tierra" debe seguir apuntando al objetivo "Ejército"
        assertFalse(playerHand.get(0).getMods().get(0).getTarget().stream().noneMatch(c -> c.getCardType().equals(CardType.EJERCITO)));

    }

    /*
     * Modificador de tipo ClearArmyPenaltyHand -> Elimina la palabra Ejército de la sección de penalización de todas las cartas en la mano.
    */

    // La única diferencia del anterior es que ahora también lo elimina de Tierra

    @Test
    void shouldClearArmyPenaltyFromHand() {
    
        // Añadimos cartas de tipo ejército de prueba para el target
        Card card5 = new Card("Ejercito 1", CardType.EJERCITO, 20);
        Card card6 = new Card("Ejercito 2", CardType.EJERCITO, 20);
    
        // Añadimos carta de tipo inundación
        Card card7 = new Card("Inundación 1", CardType.INUNDACION, 20);
    
        // Modificadores a probar
        Mod penaltyMod1 = new BonusPenaltyForEachTypeMod("PENALIZACIÓN: Resta 10 por cada ejército", -5, null, card7, List.of(card5, card6), ModType.PENALTY); // El target debería quedar vacío
        Mod penaltyMod2 = new BlankTypes("PENALIZACIÓN: Anula todos los ejércitos y bestias", -5, null, card7, List.of(card5, card6, playerHand.get(2), playerHand.get(3)), ModType.PENALTY); // Elimina del target los ejércitos
        Mod noPenaltyMod3 = new BlankTypes("Anula todos los ejércitos y bestias", -5, null, card7, List.of(card5, card6, playerHand.get(2), playerHand.get(3)), ModType.BLANK); // No debe hacer nada porque no es penalización
    
        card7.setMods(new ArrayList<>(List.of(penaltyMod1, penaltyMod2, noPenaltyMod3)));
    
        playerHand.add(card7);
    
        // Añadimos modificador de pruebas para carta tipo tierra
        Mod penaltyMod3 = new BonusPenaltyForEachTypeMod("PENALIZACIÓN: Resta 10 por cada ejército", -5, null, playerHand.get(0), List.of(card5, card6), ModType.PENALTY); // No debe hacer nada porque es un tipo tierra
        playerHand.get(0).setMods(new ArrayList<>(List.of(penaltyMod3)));
    
        // Definimos el modificador ClearArmyPenaltyHand para aplicar a toda la mano
        Mod modTest = new ClearArmyPenaltyHand(
                "Elimina la palabra Ejército de la sección de penalización de todas las cartas de la mano",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.CLEAR);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));
    
        // Validamos el comportamiento previo
        assertFalse(card7.getMods().isEmpty()); // La carta de tipo Inundación (card7) tiene modificadores inicialmente
        assertFalse(playerHand.get(0).getMods().isEmpty()); // Carta de tipo Tierra tiene un penalizador inicialmente
        assertEquals(3, card7.getMods().size()); // Validamos que la carta de tipo Inundación tiene tres modificadores
    
        // Aplicamos el modificador para eliminar el objetivo "Ejército" de la sección de penalización de las cartas de la mano
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento esperado
    
        // Carta de tipo "Inundación" (card7)
        // Deben eliminarse el tipo "Ejército" en los modificadores de tipo PENALTY de la carta de tipo Inundación
        assertEquals(3, card7.getMods().size()); // La cantidad de modificadores debe permanecer igual
        assertTrue(penaltyMod1.getTarget().isEmpty()); // El primer modificador de penalización debe tener el target vacío
        assertTrue(penaltyMod2.getTarget().stream().noneMatch(c -> c.getCardType().equals(CardType.EJERCITO))); // El segundo modificador debe eliminar los ejércitos de su target
    
        // Carta de tipo "Tierra" (playerHand.get(0))
        // La carta de tipo "Tierra" no debe tener ejércitos en su target después de aplicar el modificador
        assertFalse(playerHand.get(0).getMods().isEmpty());
        assertEquals(1, playerHand.get(0).getMods().size());
        assertTrue(playerHand.get(0).getMods().get(0).getTarget().stream().noneMatch(c -> c.getCardType().equals(CardType.EJERCITO)));
    }

    /*
     * Modificador de tipo ClearPenaltyForType -> Elimina la penalización de x tipo, ó de x e y tipos
    */

    @Test
    void shouldClearPenaltyForSpecifiedTypes() {
    
        // Añadimos cartas de distintos tipos para el target
        Card card5 = new Card("Inundación 1", CardType.INUNDACION, 20);
        Card card6 = new Card("Llama 1", CardType.LLAMA, 15);
        Card card7 = new Card("Bestia 1", CardType.BESTIA, 25);
    
        // Modificadores a probar
        Mod penaltyMod1 = new BonusPenaltyForEachTypeMod("PENALIZACIÓN: Resta 5 por cada Bestia", -5, null, card5, List.of(card6, card7), ModType.PENALTY);
        Mod penaltyMod2 = new BlankTypes("PENALIZACIÓN: Anula todos los Inundación y Bestia", -5, null, card6, List.of(card5, card7), ModType.PENALTY);
        Mod noPenaltyMod3 = new BlankTypes("Anula todas las Llamas", -5, null, card7, List.of(card6), ModType.BLANK);
        Mod penaltyMod3 = new BlankTypes("PENALIZACIÓN: Anula todas las Llamas", -5, null, card7, List.of(card6), ModType.PENALTY);
    
        card5.setMods(new ArrayList<>(List.of(penaltyMod1)));
        card6.setMods(new ArrayList<>(List.of(penaltyMod2)));
        card7.setMods(new ArrayList<>(List.of(noPenaltyMod3,penaltyMod3)));
    
        playerHand.add(card5);
        playerHand.add(card6);
        playerHand.add(card7);
    
        // Definimos el modificador ClearPenaltyForType para aplicar a las cartas de tipo INUNDACION y LLAMA
        Mod modTest = new ClearPenaltyForType(
                "Elimina la penalización de Inundaciones y Llamas",
                null,
                null,
                playerHand.get(3),
                List.of(card5, card6), // Target: cartas tipo Inundación y Llama
                ModType.CLEAR);
    
        // Validamos el comportamiento previo
        assertFalse(card5.getMods().isEmpty()); // Carta de tipo Inundación tiene un modificador de penalización inicialmente
        assertFalse(card6.getMods().isEmpty()); // Carta de tipo Llama tiene un modificador de penalización inicialmente
        assertFalse(card7.getMods().isEmpty()); // Carta de tipo Bestia tiene un modificador que no es de penalización
    
        // Aplicamos el modificador para eliminar los penalizadores de las cartas especificadas
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento esperado
    
        // Carta de tipo "Inundación" (card5)

        assertTrue(card5.getMods().isEmpty()); // Deben eliminarse los penalizadores de la carta de tipo Inundación
    
        // Carta de tipo "Llama" (card6)

        assertTrue(card6.getMods().isEmpty()); // Deben eliminarse los penalizadores de la carta de tipo Llama
    
        // Carta de tipo "Bestia" (card7)

        assertFalse(card7.getMods().isEmpty()); // La carta de tipo Bestia debe mantener sus modificadores
        assertEquals(ModType.BLANK, card7.getMods().get(0).getModType()); // El modificador de tipo BLANK debe permanecer
        assertEquals(ModType.PENALTY, card7.getMods().get(1).getModType()); // El modificador de tipo PENALTY debe permanecer
    
    }

    /*
    * Modificador tipo ClearPenaltyIsland
    */

    // Caso 1: Elimina la penalización de una carta de tipo Llama o Inundación

    @Test
    void shouldClearPenaltyIsland() {

        // Creamos una carta de tipo Llama con un modificador de penalización
        Card flameCard = new Card("Llama Fuego", CardType.LLAMA, 10);
        Mod penaltyMod = new BlankTypes("Penalización -5", -5, null, flameCard, null, ModType.PENALTY);
        flameCard.setMods(new ArrayList<>(List.of(penaltyMod)));

        // Creamos el modificador ClearPenaltyIsland que eliminará la penalización y lo añadimos a su carta
        Card island = new Card("Isla", CardType.INUNDACION, 14);
        Mod modTest = new ClearPenaltyIsland("Elimina penalización de Llama/Inundación", null, null, island, null, ModType.CLEAR);
        island.setMods(List.of(modTest));

        // Añadimos las cartas a la mano

        playerHand.add(flameCard);
        playerHand.add(island);


        // Comprobamos funcionamiento previo
        assertFalse(flameCard.getMods().isEmpty(), "La carta debería tener un modificador de penalización antes de aplicar el mod");
        assertEquals(1, flameCard.getMods().size(), "La carta debería tener un solo modificador antes de aplicar el mod");

        // Llamamos al método a probar
        ((ClearPenaltyIsland) modTest).applyMod(playerHand, flameCard);

        // Validamos el comportamiento: la penalización debe ser eliminada
        assertTrue(flameCard.getMods().isEmpty(), "La carta no debería tener penalizadores después de aplicar el mod");
    }

    // Caso 2: No elimina penalización si la carta no es de tipo Llama o Inundación

    @Test
    void shouldNotClearPenaltyIsland() {
        // Creamos una carta de tipo Tierra con un modificador de penalización
        Card badCard = new Card("Tierra Firme", CardType.TIERRA, 8);
        Mod penaltyMod = new BlankTypes("Penalización -3", -3, null, badCard, null, ModType.PENALTY);
        badCard.setMods(new ArrayList<>(List.of(penaltyMod)));

        // Creamos el modificador ClearPenaltyIsland y su carta
        Card island = new Card("Isla", CardType.INUNDACION, 14);
        Mod modTest = new ClearPenaltyIsland("Elimina penalización de Llama/Inundación", null, null, island, null, ModType.CLEAR);
        island.setMods(List.of(modTest));

        // Añadimos las cartas a la mano del jugador
        playerHand.add(badCard);
        playerHand.add(island);

        // Comprobamos funcionamiento previo
        assertFalse(badCard.getMods().isEmpty(), "La carta debería tener un modificador de penalización antes de aplicar el mod");
        assertEquals(1, badCard.getMods().size(), "La carta debería tener un solo modificador antes de aplicar el mod");

        // Comprobamos que se lanza la excepción
        assertThrows(ModStatesException.class, () -> { ((ClearPenaltyIsland) modTest).applyMod(playerHand, badCard); });
    }

}
