package es.us.dp1.l2_05_24_25.fantasy_realms.mod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.NecromancerMod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.allHand.BonusCardRun;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.allHand.BonusDifferentSuit;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.allHand.BonusSameSuit;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.basic.BonusPenaltyForEachTypeMod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.basic.BonusPenaltyNoType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.blank.BlankExceptName;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.blank.BlankTypes;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.blank.BlankUnlessArmyOrWeatherPresent;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.blank.BlankUnlessType;
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
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeAllExceptBonus;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeBaseValue;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeNameTypeShapeMirage;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeNameTypeShapeShifter;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.EqualBaseStrenghts;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.clear.ClearAllPenalties;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.clear.ClearArmyPenaltyFlood;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.clear.ClearArmyPenaltyHand;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.clear.ClearPenaltyForType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.clear.ClearPenaltyIsland;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ModConstructorTests {

    private Card card;

    private List<Card> targetCards;

    @BeforeEach
    void setUp() {
        
        card = new Card("prueba", CardType.ARTEFACTO, 10);
    
        
        targetCards = List.of(card);
    }
    
    @Test
    public void testAllModConstructors() {
        // Prueba de constructor de copia para cada subclase de Mod
        testCopyConstructor(new BonusCardRun("Description", 10, 5, card, targetCards, ModType.BONUS));
        testCopyConstructor(new BonusDifferentSuit("Description", 10, 5, card, targetCards, ModType.BONUS));
        testCopyConstructor(new BonusSameSuit("Description", 10, 5, card, targetCards, ModType.BONUS));
        testCopyConstructor(new BonusPenaltyForEachTypeMod("Description", 10, 5, card, targetCards, ModType.PENALTY));
        testCopyConstructor(new BonusPenaltyNoType("Description", 10, 5, card, targetCards, ModType.PENALTY));
        testCopyConstructor(new BlankExceptName("Description", 10, 5, card, targetCards, ModType.BLANK));
        testCopyConstructor(new BlankTypes("Description", 10, 5, card, targetCards, ModType.BLANK));
        testCopyConstructor(new BlankUnlessArmyOrWeatherPresent("Description", 10, 5, card, targetCards, ModType.BLANK));
        testCopyConstructor(new BlankUnlessType("Description", 10, 5, card, targetCards, ModType.BLANK));
        testCopyConstructor(new BonusBasicWizzardInHand("Description", 10, 5, card, targetCards, ModType.BONUS));
        testCopyConstructor(new BonusBookBellWizard("Description", 10, 5, card, targetCards, ModType.BONUS));
        testCopyConstructor(new BonusDwarvishOrDragon("Description", 10, 5, card, targetCards, ModType.BONUS));
        testCopyConstructor(new BonusElvenWarlordBeastmaster("Description", 10, 5, card, targetCards, ModType.BONUS));
        testCopyConstructor(new BonusWhirlwind("Description", 10, 5, card, targetCards, ModType.BONUS));
        testCopyConstructor(new BonusForest("Description", 10, 5, card, targetCards, ModType.BONUS));
        testCopyConstructor(new BonusKing("Description", 10, 5, card, targetCards, ModType.BONUS));
        testCopyConstructor(new BonusLeaderShieldKeth("Description", 10, 5, card, targetCards, ModType.BONUS));
        testCopyConstructor(new BonusLeaderSwordKeth("Description", 10, 5, card, targetCards, ModType.BONUS));
        testCopyConstructor(new BonusLeaderWizard("Description", 10, 5, card, targetCards, ModType.BONUS));
        testCopyConstructor(new BonusMultipleTypes("Description", 10, 5, card, targetCards, ModType.BONUS));
        testCopyConstructor(new BonusQueen("Description", 10, 5, card, targetCards, ModType.BONUS));
        testCopyConstructor(new BonusRainstorm("Description", 10, 5, card, targetCards, ModType.BONUS));
        testCopyConstructor(new BonusSmokeAndWildfire("Description", 10, 5, card, targetCards, ModType.BONUS));
        testCopyConstructor(new BonusSwamp("Description", 10, 5, card, targetCards, ModType.BONUS));
        testCopyConstructor(new ChangeAllExceptBonus("Description", 10, 5, card, targetCards, ModType.STATE));
        testCopyConstructor(new ChangeBaseValue("Description", 10, 5, card, targetCards, ModType.STATE));
        testCopyConstructor(new ChangeNameTypeShapeMirage("Description", 10, 5, card, targetCards, ModType.STATE));
        testCopyConstructor(new ChangeNameTypeShapeShifter("Description", 10, 5, card, targetCards, ModType.STATE));
        testCopyConstructor(new ChangeType("Description", 10, 5, card, targetCards, ModType.STATE));
        testCopyConstructor(new EqualBaseStrenghts("Description", 10, 5, card, targetCards, ModType.STATE));
        testCopyConstructor(new ClearAllPenalties("Description", 10, 5, card, targetCards, ModType.PENALTY));
        testCopyConstructor(new ClearArmyPenaltyFlood("Description", 10, 5, card, targetCards, ModType.PENALTY));
        testCopyConstructor(new ClearArmyPenaltyHand("Description", 10, 5, card, targetCards, ModType.PENALTY));
        testCopyConstructor(new ClearPenaltyForType("Description", 10, 5, card, targetCards, ModType.PENALTY));
        testCopyConstructor(new ClearPenaltyIsland("Description", 10, 5, card, targetCards, ModType.PENALTY));
        testCopyConstructor(new NecromancerMod("Description", 10, 5, card, targetCards, ModType.STATE));
    }

    @Test
    public void testAllEmptyConstructors() {
        // Prueba de constructor vacío para cada subclase de Mod
        testEmptyConstructor(new BonusCardRun());
        testEmptyConstructor(new BonusDifferentSuit());
        testEmptyConstructor(new BonusSameSuit());
        testEmptyConstructor(new BonusPenaltyForEachTypeMod());
        testEmptyConstructor(new BonusPenaltyNoType());
        testEmptyConstructor(new BlankExceptName());
        testEmptyConstructor(new BlankTypes());
        testEmptyConstructor(new BlankUnlessArmyOrWeatherPresent());
        testEmptyConstructor(new BlankUnlessType());
        testEmptyConstructor(new BonusBasicWizzardInHand());
        testEmptyConstructor(new BonusBookBellWizard());
        testEmptyConstructor(new BonusDwarvishOrDragon());
        testEmptyConstructor(new BonusElvenWarlordBeastmaster());
        testEmptyConstructor(new BonusKing());
        testEmptyConstructor(new BonusLeaderShieldKeth());
        testEmptyConstructor(new BonusLeaderSwordKeth());
        testEmptyConstructor(new BonusLeaderWizard());
        testEmptyConstructor(new BonusMultipleTypes());
        testEmptyConstructor(new BonusQueen());
        testEmptyConstructor(new BonusRainstorm());
        testEmptyConstructor(new BonusSmokeAndWildfire());
        testEmptyConstructor(new BonusSwamp());
        testEmptyConstructor(new ChangeAllExceptBonus());
        testEmptyConstructor(new ChangeBaseValue());
        testEmptyConstructor(new ChangeNameTypeShapeMirage());
        testEmptyConstructor(new ChangeNameTypeShapeShifter());
        testEmptyConstructor(new ChangeType());
        testEmptyConstructor(new EqualBaseStrenghts());
        testEmptyConstructor(new ClearAllPenalties());
        testEmptyConstructor(new ClearArmyPenaltyFlood());
        testEmptyConstructor(new ClearArmyPenaltyHand());
        testEmptyConstructor(new ClearPenaltyForType());
        testEmptyConstructor(new ClearPenaltyIsland());
        testEmptyConstructor(new NecromancerMod());
        testEmptyConstructor(new BonusWhirlwind());
        testEmptyConstructor(new BonusForest());
    }

    private <T extends Mod> void testCopyConstructor(T originalMod) {
        // Clonar el modificador usando el constructor de copia
        T clonedMod = (T) originalMod.clone();

        // Verificar que los atributos son iguales
        assertEquals(originalMod.getDescription(), clonedMod.getDescription(), "Las descripciones deberían ser iguales");
        assertEquals(originalMod.getPrimaryValue(), clonedMod.getPrimaryValue(), "Los valores primarios deberían ser iguales");
        assertEquals(originalMod.getSecondaryValue(), clonedMod.getSecondaryValue(), "Los valores secundarios deberían ser iguales");
        assertEquals(originalMod.getModType(), clonedMod.getModType(), "El tipo de modificador debería ser el mismo");

        // La origin card y target debe ser null porque se establecen manualmente para evitar referencias a cartas iniciales

        assertNull(clonedMod.getOriginCard());
        assertNull(clonedMod.getTarget());

        // Verificar que las referencias son diferentes
        assertNotSame(originalMod, clonedMod, "El objeto clonado debe ser diferente del original");

        // Modificar el clon y verificar que el original no se vea afectado
        clonedMod.setPrimaryValue(15);
        assertNotEquals(originalMod.getPrimaryValue(), clonedMod.getPrimaryValue(), "Los valores primarios no deberían ser iguales después de modificar el clon");
    }

    private <T extends Mod> void testEmptyConstructor(T modInstance) {
        // Verificar que el objeto se crea y que los valores están en su estado predeterminado
        assertNotNull(modInstance, "La instancia creada no debería ser nula");
        assertNull(modInstance.getDescription(), "La descripción debería ser null por defecto");
        assertNull(modInstance.getOriginCard(), "La carta de origen debería ser null por defecto");
        assertNull(modInstance.getTarget(), "El target debería ser null por defecto");
        assertNull(modInstance.getModType(), "El tipo de modificador debería ser null por defecto");
        assertNull(modInstance.getPrimaryValue(), "El valor primario debería ser null por defecto");
        assertNull(modInstance.getSecondaryValue(), "El valor secundario debería ser null por defecto");
    }
}




