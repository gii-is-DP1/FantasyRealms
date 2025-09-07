package es.us.dp1.l2_05_24_25.fantasy_realms.mod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeAllExceptBonus;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeBaseValue;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeNameTypeShapeMirage;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeNameTypeShapeShifter;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.EqualBaseStrenghts;

/*
 * Se pretende probar el funcionamiento del applyMod con instancias de tipo State (carpeta changeState)
 */
@ExtendWith(MockitoExtension.class)
public class ModStateTests {

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
     * Modificador de tipo ChangeBaseValue -> Añade la fuerza base de cualquier Arma, Inundación, Llama, Tierra o Clima en tu mano. 
    */

    // Caso 1: se trata de hacer el cambio con una carta disponible en la mano y que esté en los tipos aceptados

    @Test
    void shouldChangeBaseValue() {

        // Definimos el modificador ClearArmyPenaltyHand para aplicar a toda la mano
        Mod modTest = new ChangeBaseValue(
                "Añade la fuerza base de cualquier Arma, Inundación, Llama, Tierra o Clima en tu mano.",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.STATE);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Comprobaciones previas

        assertEquals(5, playerHand.get(3).getBaseValue());
        assertEquals(5, playerHand.get(3).getFinalValue());

        // Aplicamos el modificador usando carta válida y que esté en la mano
        ((ChangeBaseValue) modTest).applyMod(playerHand, playerHand.get(0));

        // Verificamos el resultado

        assertEquals(5, playerHand.get(3).getBaseValue());
        assertEquals(15, playerHand.get(3).getFinalValue()); // 5 (original) + 10 (pasada como parámetro)

    }

    // Caso 2: se trata de hacer el cambio con una carta disponible en la mano y que NO esté en los tipos aceptados

    @Test
    void shouldNotChangeBaseValue1() {

        Card card5 = new Card("Inundación 1", CardType.SALVAJE, 20);
        playerHand.add(card5);

        // Definimos el modificador ClearArmyPenaltyHand para aplicar a toda la mano
        Mod modTest = new ChangeBaseValue(
                "Añade la fuerza base de cualquier Arma, Inundación, Llama, Tierra o Clima en tu mano.",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.STATE);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Comprobaciones previas

        assertEquals(5, playerHand.get(3).getBaseValue());
        assertEquals(5, playerHand.get(3).getFinalValue());

        // Verificamos que se lanza una UnsupportedOperationException
        assertThrows(ModStatesException.class, () -> {
            ((ChangeBaseValue) modTest).applyMod(playerHand, playerHand.get(4));
        });

    }

    // Caso 3: se trata de hacer el cambio con una carta NO disponible en la mano y que esté en los tipos aceptados

    @Test
    void shouldNotChangeBaseValue2() {

        Card card5 = new Card("Inundación 1", CardType.LLAMA, 20);

        // Definimos el modificador ClearArmyPenaltyHand para aplicar a toda la mano
        Mod modTest = new ChangeBaseValue(
                "Añade la fuerza base de cualquier Arma, Inundación, Llama, Tierra o Clima en tu mano.",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.STATE);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Comprobaciones previas

        assertEquals(5, playerHand.get(3).getBaseValue());
        assertEquals(5, playerHand.get(3).getFinalValue());

        // Verificamos que se lanza una UnsupportedOperationException
        assertThrows(ModStatesException.class, () -> {
            ((ChangeBaseValue) modTest).applyMod(playerHand, card5);
        });

    }

    /*
     * Modificador de tipo ChangeAllExceptBonus -> Puede duplicar el nombre, tipo, fuerza base y penalización, pero no la bonificación, de cualquier otra carta en tu mano.
    */

    // Caso 1: se trata de hacer el cambio con una carta disponible en la mano, que no sea la del mod.

    @Test
    void shouldChangeAllExceptBonus() {

        Card card5 = new Card("Bestia 1", CardType.SALVAJE, 20);
        Mod penaltyMod1 = new BonusPenaltyForEachTypeMod("PENALIZACIÓN: Resta 10 por cada Bestia", -10, null, card5, null, ModType.PENALTY);
        Mod bonusMod1 = new BonusPenaltyForEachTypeMod("BONUS: Suma 10 por cada Bestia", 10, null, card5, null, ModType.BONUS);

        card5.setMods(new ArrayList<>(List.of(penaltyMod1,bonusMod1)));

        playerHand.add(card5);

        // Suponemos que la carta del mod tiene valor base 0 como ocurre en el juego

        playerHand.get(3).setBaseValue(0);
        playerHand.get(3).setFinalValue(0);

        // Definimos el modificador ClearArmyPenaltyHand para aplicar a toda la mano
        Mod modTest = new ChangeAllExceptBonus(
                "Puede duplicar el nombre, tipo, fuerza base y penalización, pero no la bonificación, de cualquier otra carta en tu mano.",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.STATE);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Aplicamos el modificador usando carta válida y que esté en la mano
        ((ChangeAllExceptBonus) modTest).applyMod(playerHand, playerHand.get(4));

        // Verificamos el resultado

        assertEquals(playerHand.get(4).getName(), playerHand.get(3).getName());
        assertEquals(playerHand.get(4).getName(), playerHand.get(3).getName()); 
        assertEquals(playerHand.get(4).getCardType(), playerHand.get(3).getCardType()); 
        assertEquals(penaltyMod1.getDescription(), playerHand.get(3).getMods().get(0).getDescription()); // se hace así porque al copiar el mod se crea una nueva instancia del mismo

    }

    // Caso 2: se trata de hacer el cambio con una carta NO disponible en la mano, que no sea la del mod.

    @Test
    void shouldNotChangeAllExceptBonus1() {

        Card card5 = new Card("Inundación 1", CardType.SALVAJE, 20);

        // Suponemos que la carta del mod tiene valor base 0 como ocurre en el juego

        playerHand.get(3).setBaseValue(0);
        playerHand.get(3).setFinalValue(0);

        // Definimos el modificador ClearArmyPenaltyHand para aplicar a toda la mano
        Mod modTest = new ChangeAllExceptBonus(
                "Puede duplicar el nombre, tipo, fuerza base y penalización, pero no la bonificación, de cualquier otra carta en tu mano.",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.STATE);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Verificamos que se lanza una UnsupportedOperationException
        assertThrows(ModStatesException.class, () -> {
            ((ChangeAllExceptBonus) modTest).applyMod(playerHand, card5);
        });

    }

    // Caso 3: se trata de hacer el cambio con la misma carta.

    @Test
    void shouldNotChangeAllExceptBonus2() {

        // Suponemos que la carta del mod tiene valor base 0 como ocurre en el juego

        playerHand.get(3).setBaseValue(0);
        playerHand.get(3).setFinalValue(0);

        // Definimos el modificador ClearArmyPenaltyHand para aplicar a toda la mano
        Mod modTest = new ChangeAllExceptBonus(
                "Puede duplicar el nombre, tipo, fuerza base y penalización, pero no la bonificación, de cualquier otra carta en tu mano.",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.STATE);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Verificamos que se lanza una UnsupportedOperationException
        assertThrows(ModStatesException.class, () -> {
            ((ChangeAllExceptBonus) modTest).applyMod(playerHand, playerHand.get(3));
        });

    }

    /*
     * Modificador de tipo ChangeNameTypeShapeMirage: Puede adoptar el nombre y el tipo de cualquier Artefacto, Líder, Mago, Arma o Bestia. No obtiene bonificación ni penalización. -> No tiene que estar en la mano
    */

    // Caso 1: se trata de hacer el cambio con una carta de los tipos disponibles que está en la mano

    @Test
    void shouldChangeNameTypeShapeMirageInHand() {

        Card card5 = new Card("Arma 1 1", CardType.TIERRA, 20);
        Mod penaltyMod1 = new BonusPenaltyForEachTypeMod("PENALIZACIÓN: Resta 10 por cada Bestia", -10, null, card5, null, ModType.PENALTY);
        Mod bonusMod1 = new BonusPenaltyForEachTypeMod("BONUS: Suma 10 por cada Bestia", 10, null, card5, null, ModType.BONUS);

        card5.setMods(new ArrayList<>(List.of(penaltyMod1,bonusMod1)));

        playerHand.add(card5);

        // Suponemos que la carta del mod tiene valor base 0 como ocurre en el juego

        playerHand.get(3).setBaseValue(0);
        playerHand.get(3).setFinalValue(0);

        Mod modTest = new ChangeNameTypeShapeMirage(
                "Puede adoptar el nombre y el tipo de cualquier Artefacto, Líder, Mago, Arma o Bestia. No obtiene bonificación ni penalización.",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.STATE);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Aplicamos el modificador usando carta válida y que esté en la mano
        ((ChangeNameTypeShapeMirage) modTest).applyMod(playerHand, card5);

        // Verificamos el resultado

        assertEquals(playerHand.get(4).getName(), playerHand.get(3).getName());
        assertEquals(playerHand.get(4).getCardType(), playerHand.get(3).getCardType()); 

        // El resto de atributos deben permanecer igual

        assertEquals(0, playerHand.get(3).getBaseValue()); 
        assertEquals(0, playerHand.get(3).getFinalValue()); 
        assertEquals(1, playerHand.get(3).getMods().size()); 
        assertEquals(modTest, playerHand.get(3).getMods().get(0));

    }

    // Caso 2: se trata de hacer el cambio con una carta de los tipos disponibles que no está en la mano

    @Test
    void shouldChangeNameTypeShapeMirageNotInHand() {

        Card card5 = new Card("Arma 1", CardType.TIERRA, 20);
        Mod penaltyMod1 = new BonusPenaltyForEachTypeMod("PENALIZACIÓN: Resta 10 por cada Bestia", -10, null, card5, null, ModType.PENALTY);
        Mod bonusMod1 = new BonusPenaltyForEachTypeMod("BONUS: Suma 10 por cada Bestia", 10, null, card5, null, ModType.BONUS);

        card5.setMods(new ArrayList<>(List.of(penaltyMod1,bonusMod1)));

        // Suponemos que la carta del mod tiene valor base 0 como ocurre en el juego

        playerHand.get(3).setBaseValue(0);
        playerHand.get(3).setFinalValue(0);

        Mod modTest = new ChangeNameTypeShapeMirage(
                "Puede adoptar el nombre y el tipo de cualquier Artefacto, Líder, Mago, Arma o Bestia. No obtiene bonificación ni penalización.",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.STATE);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Aplicamos el modificador usando carta válida y que esté en la mano
        ((ChangeNameTypeShapeMirage) modTest).applyMod(playerHand, card5);

        // Verificamos el resultado

        assertEquals(card5.getName(), playerHand.get(3).getName());
        assertEquals(card5.getCardType(), playerHand.get(3).getCardType()); 

        // El resto de atributos deben permanecer igual

        assertEquals(0, playerHand.get(3).getBaseValue()); 
        assertEquals(0, playerHand.get(3).getFinalValue()); 
        assertEquals(1, playerHand.get(3).getMods().size()); 
        assertEquals(modTest, playerHand.get(3).getMods().get(0));

    }

    // Caso 3: se trata de hacer el cambio con una de un tipo no disponible

    @Test
    void shouldNotChangeNameTypeShapeMirageBadType() {


        // Usamos la carta 3 que es de tipo bestia y no sirve

        Mod modTest = new ChangeNameTypeShapeMirage(
                "Puede adoptar el nombre y el tipo de cualquier Artefacto, Líder, Mago, Arma o Bestia. No obtiene bonificación ni penalización.",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.STATE);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Verificamos que se lanza una UnsupportedOperationException
        assertThrows(ModStatesException.class, () -> {
            ((ChangeNameTypeShapeMirage) modTest).applyMod(playerHand, playerHand.get(2));
        });

    }
    
    // Caso 4: se trata de hacer el cambio con la misma carta

    @Test
    void shouldNotChangeNameTypeShapeMirageSameCard() {

        Mod modTest = new ChangeNameTypeShapeMirage(
                "Puede adoptar el nombre y el tipo de cualquier Artefacto, Líder, Mago, Arma o Bestia. No obtiene bonificación ni penalización.",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.STATE);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Verificamos que se lanza una UnsupportedOperationException
        assertThrows(ModStatesException.class, () -> {
            ((ChangeNameTypeShapeMirage) modTest).applyMod(playerHand, playerHand.get(3));
        });

    }

    /*
     * Modificador de tipo ChangeNameTypeShapeShifter -> Puede adoptar el nombre y el tipo de cualquier Ejército, Tierra, Tiempo, Inundación o Llama. No obtiene bonificación ni penalización. -> No tiene que estar en la mano
    */

    // Caso 1: se trata de hacer el cambio con una carta de los tipos disponibles que está en la mano

    @Test
    void shouldChangeNameTypeShapeShifterInHand() {

        Card card5 = new Card("Artefacto 1", CardType.ARTEFACTO, 20);
        Mod penaltyMod1 = new BonusPenaltyForEachTypeMod("PENALIZACIÓN: Resta 10 por cada Bestia", -10, null, card5, null, ModType.PENALTY);
        Mod bonusMod1 = new BonusPenaltyForEachTypeMod("BONUS: Suma 10 por cada Bestia", 10, null, card5, null, ModType.BONUS);

        card5.setMods(new ArrayList<>(List.of(penaltyMod1,bonusMod1)));

        playerHand.add(card5);

        // Suponemos que la carta del mod tiene valor base 0 como ocurre en el juego

        playerHand.get(3).setBaseValue(0);
        playerHand.get(3).setFinalValue(0);

        Mod modTest = new ChangeNameTypeShapeShifter(
                "Puede adoptar el nombre y el tipo de cualquier Artefacto, Lider, Mago, Arma o Bestia. No obtiene bonificación ni penalización.",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.STATE);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Aplicamos el modificador usando carta válida y que esté en la mano
        ((ChangeNameTypeShapeShifter) modTest).applyMod(playerHand, card5);

        // Verificamos el resultado

        assertEquals(playerHand.get(4).getName(), playerHand.get(3).getName());
        assertEquals(playerHand.get(4).getCardType(), playerHand.get(3).getCardType()); 

        // El resto de atributos deben permanecer igual

        assertEquals(0, playerHand.get(3).getBaseValue()); 
        assertEquals(0, playerHand.get(3).getFinalValue()); 
        assertEquals(1, playerHand.get(3).getMods().size()); 
        assertEquals(modTest, playerHand.get(3).getMods().get(0));

    }

    // Caso 2: se trata de hacer el cambio con una carta de los tipos disponibles que no está en la mano

    @Test
    void shouldChangeNameTypeShapeShifterNotInHand() {

        Card card5 = new Card("Artefacto 1", CardType.ARTEFACTO, 20);
        Mod penaltyMod1 = new BonusPenaltyForEachTypeMod("PENALIZACIÓN: Resta 10 por cada Bestia", -10, null, card5, null, ModType.PENALTY);
        Mod bonusMod1 = new BonusPenaltyForEachTypeMod("BONUS: Suma 10 por cada Bestia", 10, null, card5, null, ModType.BONUS);

        card5.setMods(new ArrayList<>(List.of(penaltyMod1,bonusMod1)));

        // Suponemos que la carta del mod tiene valor base 0 como ocurre en el juego

        playerHand.get(3).setBaseValue(0);
        playerHand.get(3).setFinalValue(0);

        // Definimos el modificador ChangeNameTypeShapeShifter para aplicar a toda la mano
        Mod modTest = new ChangeNameTypeShapeShifter(
                "Puede adoptar el nombre y el tipo de cualquier Artefacto, Lider, Mago, Arma o Bestia. No obtiene bonificación ni penalización.",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.STATE);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Aplicamos el modificador usando carta válida y que esté en la mano
        ((ChangeNameTypeShapeShifter) modTest).applyMod(playerHand, card5);

        // Verificamos el resultado

        assertEquals(card5.getName(), playerHand.get(3).getName());
        assertEquals(card5.getCardType(), playerHand.get(3).getCardType()); 

        // El resto de atributos deben permanecer igual

        assertEquals(0, playerHand.get(3).getBaseValue()); 
        assertEquals(0, playerHand.get(3).getFinalValue()); 
        assertEquals(1, playerHand.get(3).getMods().size()); 
        assertEquals(modTest, playerHand.get(3).getMods().get(0));

    }

    // Caso 3: se trata de hacer el cambio con una de un tipo no disponible

    @Test
    void shouldNotChangeNameTypeShapeShifterBadType() {


        // Usamos la carta 1 que es de tipo tierra y no sirve

        // Definimos el modificador ClearArmyPenaltyHand para aplicar a toda la mano
        Mod modTest = new ChangeNameTypeShapeShifter(
                "Puede adoptar el nombre y el tipo de cualquier Artefacto, Lider, Mago, Arma o Bestia. No obtiene bonificación ni penalización.",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.STATE);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Verificamos que se lanza la excepción
        assertThrows(ModStatesException.class, () -> {
            ((ChangeNameTypeShapeShifter) modTest).applyMod(playerHand, playerHand.get(0));
        });

    }

    /*
     * Modificador de tipo ChangeType -> Puedes cambiar el tipo de una carta. Su nombre, bonificaciones y penalizaciones permanecen iguales.
    */

    // Caso 1: se trata de hacer el cambio con una carta de la mano

    @Test
    void shouldChangeTypeCardInHand() {

        // Vamos a suponer que queremos cambiar el tipo de la carta 1 de la mano a bestia.
    
        // Definimos el modificador ChangeType para aplicar a una carta de la mano
        Mod modTest = new ChangeType(
                "Puedes cambiar el tipo de una carta. Su nombre, bonificaciones y penalizaciones permanecen iguales.",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.STATE);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));
    
        // Aplicamos el modificador usando una carta válida que esté en la mano para cambiar su tipo
        ((ChangeType) modTest).applyMod(playerHand, playerHand.get(0),CardType.BESTIA);
    
        // Verificamos el resultado

        assertEquals(playerHand.get(0).getCardType(), CardType.BESTIA); // El tipo de la carta debería haber cambiado
    
        // El nombre, bonificaciones y penalizaciones permanecen iguales

        assertEquals("Tierra 1", playerHand.get(0).getName()); // Nombre debería permanecer igual
        assertEquals(10, playerHand.get(0).getBaseValue()); // Valor base debería permanecer igual
        assertEquals(10, playerHand.get(0).getFinalValue()); // Valor final debería permanecer igual
        assertEquals(2, playerHand.get(0).getMods().size());
    }

    // Caso 2: se trata de hacer el cambio con una carta que no está en la mano

    @Test
    void shouldNotChangeTypeCardNotInHand() {

        Card card5 = new Card("Tiempo 1", CardType.TIEMPO, 20);

        // Vamos a suponer que queremos cambiar el tipo de la carta 1 de la mano a bestia.
    
        // Definimos el modificador ChangeType para aplicar a una carta de la mano
        Mod modTest = new ChangeType(
                "Puedes cambiar el tipo de una carta. Su nombre, bonificaciones y penalizaciones permanecen iguales.",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.STATE);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));

        // Verificamos que se lanza una UnsupportedOperationException
        assertThrows(ModStatesException.class, () -> {
            ((ChangeType) modTest).applyMod(playerHand, card5,CardType.BESTIA);
        });
    }

    // Caso 3: se trata de hacer el cambio con una carta que ya tiene el tipo que se quiere

    @Test
    void shouldChangeTypeSameCard() {

        // Definimos el modificador ChangeType para aplicar a una carta de la mano
        Mod modTest = new ChangeType(
                "Puedes cambiar el tipo de una carta. Su nombre, bonificaciones y penalizaciones permanecen iguales.",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.STATE);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));
    
        // Verificamos que se lanza una UnsupportedOperationException
        assertThrows(ModStatesException.class, () -> {
            ((ChangeType) modTest).applyMod(playerHand, playerHand.get(0),CardType.TIERRA);
        });
    }

    /*
     * Modificador de tipo EqualBaseStrenghts -> Igual a las fuerzas base de todos los Ejércitos en tu mano.
    */

    // Caso 1: hay ejércitos en la mano

    @Test
    void shouldEqualBaseStrenghts() {

        // Cambiamos los tipos de las cartas tipo tierra de la mano

        playerHand.get(0).setCardType(CardType.EJERCITO);
        playerHand.get(1).setCardType(CardType.EJERCITO);

        // Definimos el modificador ChangeType para aplicar a una carta de la mano
        Mod modTest = new EqualBaseStrenghts(
                "Igual a las fuerzas base de todos los Ejércitos en tu mano.",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.STATE);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));
    
        // Probamos

        modTest.applyMod(playerHand);

        // Verificamos resultados

        assertEquals(30, playerHand.get(3).getFinalValue()); // 5 de valor base + 10 + 15 de los ejércitos
    }

    // Caso 2: no hay ejércitos en la mano -> fuerza final igual a la anterior

    @Test
    void shouldEqualBaseStrenghtsWithNoArmy() {

        // Definimos el modificador ChangeType para aplicar a una carta de la mano
        Mod modTest = new EqualBaseStrenghts(
                "Igual a las fuerzas base de todos los Ejércitos en tu mano.",
                null,
                null,
                playerHand.get(3),
                null,
                ModType.STATE);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));
    
        // Probamos

        modTest.applyMod(playerHand);

        // Verificamos resultados

        assertEquals(5, playerHand.get(3).getFinalValue()); // 5 de valor base.
    }
    
}
