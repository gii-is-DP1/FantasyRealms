package es.us.dp1.l2_05_24_25.fantasy_realms.mod;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardService;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.dto.DecisionDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.NecromancerMod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.allHand.BonusSameSuit;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.basic.BonusPenaltyForEachTypeMod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.blank.BlankTypes;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus.BonusBasicWizzardInHand;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeAllExceptBonus;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeBaseValue;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeNameTypeShapeMirage;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeNameTypeShapeShifter;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.clear.ClearArmyPenaltyHand;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.clear.ClearPenaltyIsland;

@ExtendWith(MockitoExtension.class)
public class ModServiceTests {

    protected ModService modService;

    @Mock
    protected CardService cardService;

    @Mock
    protected ModRepository modRepository;

    private List<Card> playerHand;

    @BeforeEach
    void setUp() { 

        modService = new ModService(modRepository, cardService);

        this.playerHand = new ArrayList<>();

        Card card1 = new Card("Tierra 1", CardType.TIERRA, 10);
        Card card2 = new Card("Tierra 2", CardType.MAGO, 15);
        Card card3 = new Card("Bestia 1", CardType.BESTIA, 20);
        Card card4 = new Card("Bestia 2", CardType.BESTIA, 5);
        Card card5 = new Card("Inundación 1", CardType.INUNDACION, 10);
        Card card6 = new Card("Lider 1", CardType.LIDER, 15);
        Card card7 = new Card("Ejército 1", CardType.EJERCITO, 2);

        // Les damos id a las cartas de la mano

        card1.setId(1);
        card2.setId(2);
        card3.setId(3);
        card4.setId(4);
        card5.setId(5);
        card6.setId(6);
        card7.setId(7);

        // Añadimos modificadores de distintos tipos

        // Añadimos un basic bonus
        Mod basicBonusMod1 = new BonusPenaltyForEachTypeMod("Suma 10 por cada carta de tipo Tierra", 10, null, card1, List.of(card1, card2), ModType.BONUS);
        basicBonusMod1.setId(1);

        // Añadimos una penalización básica
        Mod basicPenaltyMod1 = new BonusPenaltyForEachTypeMod("PENALIZACIÓN: Resta 5 por cada carta de tipo Ejército", -5, null, card1, List.of(card7), ModType.PENALTY);
        basicPenaltyMod1.setId(2);

        // Añadimos un bonus normal
        Mod bonusMod1 = new BonusBasicWizzardInHand("Suma 15 si hay mago", 15, null, card2, null, ModType.BONUS);
        bonusMod1.setId(3);

        // Añadimos un blank penalty
        Mod blankPenalty2 = new BlankTypes("PENALIZACIÓN: Anula todos los Líderes", null, null, card2, List.of(card6), ModType.PENALTY_AND_BLANK);
        blankPenalty2.setId(4);

        Mod blankNoPenalty1 = new BlankTypes("Anula todos los ejercitos", null, null, card1, List.of(card7), ModType.BLANK);
        blankNoPenalty1.setId(5);

        // Añadimos un clear
        Mod clearMod = new ClearArmyPenaltyHand("Elimina la palabra Ejército de la sección de penalización de todas las cartas en la mano.", null, null, card3, null, ModType.CLEAR);
        clearMod.setId(6);

        // Añadimos un allHand
        Mod allHandMod = new BonusSameSuit("Bonificación: +10 si hay tres cartas diferentes de un mismo tipo, +40 si hay cuatro, +100 si hay cinco", 10, 40, card3, null, ModType.ALLHAND);
        allHandMod.setId(7);

        // Añadimos un changeState
        Mod changeStateMod = new ChangeType("Permite cambiar el tipo de una carta de la mano", null, null, card3, null, ModType.STATE);
        changeStateMod.setId(8);

        // Asignamos los modificadores a las cartas
        card1.setMods(new ArrayList<>(List.of(basicBonusMod1, basicPenaltyMod1)));
        card2.setMods(new ArrayList<>(List.of(bonusMod1, blankPenalty2,blankNoPenalty1)));
        card3.setMods(new ArrayList<>(List.of(clearMod, allHandMod, changeStateMod)));
        card4.setMods(new ArrayList<>());
        card5.setMods(new ArrayList<>());
        card6.setMods(new ArrayList<>());
        card7.setMods(new ArrayList<>());

        playerHand.add(card1);
        playerHand.add(card2);
        playerHand.add(card3);
        playerHand.add(card4);
        playerHand.add(card5);
        playerHand.add(card6);
        playerHand.add(card7);
    }

    // Probamos métodos básicos del servicio

    @Test
    void shouldFindAllMods() {
        when(modRepository.findAll()).thenReturn(List.of(playerHand.get(0).getMods().get(0)));
        Iterable<Mod> mods = modService.findAll();
        assertNotNull(mods, "La lista de mods no debería ser nula");
        assertTrue(mods.iterator().hasNext(), "Debería haber al menos un mod en la lista");
    }

    @Test
    void shouldFindModById() {
        when(modRepository.findById(1)).thenReturn(Optional.of(playerHand.get(0).getMods().get(0)));
        Mod mod = modService.findModById(1); // Suponemos que hay un mod con id 1
        assertEquals(mod.getId(), 1, "El mod encontrado debe tener id 1");
        assertNotNull(mod, "El mod con ID 1 debería existir");
    }

    @Test
    void shouldThrowExceptionIfModNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            modService.findModById(9999); // ID que no existe
        });
    }

    @Test
    void shouldSaveMod() {
        Mod mod = new BonusPenaltyForEachTypeMod("Test Bonus", 10, null, null, null, ModType.BONUS);
        mod.setId(1);
        when(modRepository.save(mod)).thenReturn(mod);
        Mod savedMod = modService.saveMod(mod);
        assertNotNull(savedMod.getId(), "El mod debería tener un ID después de ser guardado");
        assertEquals("Test Bonus", savedMod.getDescription(), "La descripción del mod guardado debería coincidir");
        verify(modRepository, times(1)).save(mod);
    }

    @Test
    void shouldDeleteModById() {
        Mod mod = new BonusPenaltyForEachTypeMod("To Delete", 5, null, null, null, ModType.PENALTY);
        mod.setId(1);

        when(modRepository.findById(1)).thenReturn(Optional.of(mod));
        doNothing().when(modRepository).deleteById(1);

        modService.deleteModById(1);

        verify(modRepository, times(1)).deleteById(1);

        when(modRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> modService.findModById(1));
    }

    // Pruebas para convertir lista de decisiones a mapa

    @Test
    void shouldConvertToDecisionMap() {
        // Probamos por ejemplo con mod changeStateMod
        DecisionDTO decisionDTO = new DecisionDTO();
        decisionDTO.setModId(1);
        decisionDTO.setCardId(1);
        decisionDTO.setCardType(CardType.ARTEFACTO);

        List<DecisionDTO> decisionDTOList = List.of(decisionDTO);
        Map<Mod, Decision> decisionMap = modService.convertToDecisionMap(playerHand, decisionDTOList);

        assertFalse(decisionMap.isEmpty(), "El mapa de decisiones no debería estar vacío");
    }

    // Si desde el front se envía una lista de decisiones vacía, se debe crear un mapa de decisiones vacío

    @Test
    void shouldCreateEmptyMapListNullDecision() {

        Map<Mod, Decision> decisionMap = modService.convertToDecisionMap(playerHand, null);

        assertTrue(decisionMap.isEmpty(), "El mapa de decisiones debería estar vacío");
    }

    // Si la lista de decisiones está vacía, ocurre lo mismo

    @Test
    void shouldCreateEmptyMapListEmptyDecision() {

        Map<Mod, Decision> decisionMap = modService.convertToDecisionMap(playerHand, new ArrayList<>());

        assertTrue(decisionMap.isEmpty(), "El mapa de decisiones debería estar vacío");
    }

    // Si la mano del jugador es vacía, ocurre lo mismo

    @Test
    void shouldCreateEmptyMapEmptyHand() {

        // Probamos por ejemplo con mod changeStateMod
        DecisionDTO decisionDTO = new DecisionDTO();
        decisionDTO.setModId(1);
        decisionDTO.setCardId(1);
        decisionDTO.setCardType(CardType.ARTEFACTO);

        List<DecisionDTO> decisionDTOList = List.of(decisionDTO);

        Map<Mod, Decision> decisionMap = modService.convertToDecisionMap(new ArrayList<>(), decisionDTOList);

        assertTrue(decisionMap.isEmpty(), "El mapa de decisiones debería estar vacío");
    }

    @Test
    void shouldSkipNullDecisionDTO() {
        
        List<DecisionDTO> decisionDTOList = new ArrayList<>();
        decisionDTOList.add(null); // Agregamos un DecisionDTO nulo

        Map<Mod, Decision> decisionMap = modService.convertToDecisionMap(playerHand, decisionDTOList);

        // Dado que el DecisionDTO es nulo, no se debería agregar ninguna decisión al mapa
        assertTrue(decisionMap.isEmpty(), "El mapa de decisiones debería estar vacío cuando DecisionDTO es nulo");
    }

    @Test
    void shouldSkipDecisionWithNullModId() {
        DecisionDTO decisionDTO = new DecisionDTO();
        decisionDTO.setModId(null); // modId es nulo
        List<DecisionDTO> decisionDTOList = List.of(decisionDTO);

        Map<Mod, Decision> decisionMap = modService.convertToDecisionMap(playerHand, decisionDTOList);

        // No debería agregar nada al mapa ya que modId es nulo
        assertTrue(decisionMap.isEmpty(), "El mapa de decisiones debería estar vacío cuando el modId es nulo");
    }

    @Test
    void shouldSkipDecisionWithInvalidModId() {
        DecisionDTO decisionDTO = new DecisionDTO();
        decisionDTO.setModId(999); // modId que no existe
        List<DecisionDTO> decisionDTOList = List.of(decisionDTO);

        Map<Mod, Decision> decisionMap = modService.convertToDecisionMap(playerHand, decisionDTOList);

        // El mapa debe estar vacío porque el modId no existe en modMap
        assertTrue(decisionMap.isEmpty(), "El mapa de decisiones debería estar vacío si no se encuentra el modId");
    }

    @Test
    void shouldNotSetTargetCardIfCardIdNotFound() {
        DecisionDTO decisionDTO = new DecisionDTO();
        decisionDTO.setModId(1); // id válido de un mod
        decisionDTO.setCardId(999); // cardId que no existe en el sistema

        List<DecisionDTO> decisionDTOList = List.of(decisionDTO);

        // Simulamos el comportamiento de cardService para devolver null
        when(cardService.findCardById(999)).thenReturn(null);

        Map<Mod, Decision> decisionMap = modService.convertToDecisionMap(playerHand, decisionDTOList);

        // Verificamos que no se haya agregado la carta objetivo ya que no existe
        Decision decision = decisionMap.get(playerHand.get(0).getMods().get(0));
        assertNull(decision.getTargetCard(), "El targetCard debería ser nulo si no se encuentra la carta");
    }

    @Test
    void shouldSetTargetCardTypeInDecision() {
        DecisionDTO decisionDTO = new DecisionDTO();
        decisionDTO.setModId(1); // id válido de un mod
        decisionDTO.setCardType(CardType.ARTEFACTO); // Tipo de carta a setear

        List<DecisionDTO> decisionDTOList = List.of(decisionDTO);
        Map<Mod, Decision> decisionMap = modService.convertToDecisionMap(playerHand, decisionDTOList);

        Decision decision = decisionMap.get(playerHand.get(0).getMods().get(0));
        assertEquals(CardType.ARTEFACTO, decision.getTargetCardType(), "El tipo de carta objetivo debería ser ARTEFACTO");
    }

    @Test
    void shouldApplyBasicDynamicMods() {

        // Preparar la decisión para cambiar el tipo de card3 a EJERCITO
        DecisionDTO changeTypeDecision = new DecisionDTO(8, 3, CardType.EJERCITO);

        // Crear y mockear el modificador ChangeBaseValue
        ChangeBaseValue changeBaseValueMod = new ChangeBaseValue(
            "Añade fuerza base", 
            null, 
            null, 
            playerHand.get(1), 
            null, 
            ModType.STATE
        );
        changeBaseValueMod.setId(9);

        // Mockear el cardService para la carta ID 3, que es card3
        when(cardService.findCardById(3)).thenReturn(playerHand.get(2));

        // Crear DecisionDTO para ChangeBaseValue
        DecisionDTO changeBaseValueDecision = new DecisionDTO(9, 2, null);

        // Lista de decisiones dinámicas
        List<DecisionDTO> decisionDTOList = List.of(changeTypeDecision, changeBaseValueDecision);

        // Convertir las decisiones DTO a un mapa de decisiones dinámicas
        Map<Mod, Decision> dynamicDecisions = modService.convertToDecisionMap(playerHand, decisionDTOList);

        // Asegurar que las decisiones dinámicas contienen los modificadores esperados
        assertFalse(dynamicDecisions.isEmpty(), "El mapa de decisiones dinámicas no debería estar vacío");

        // Act: Aplicar modificadores con decisiones dinámicas
        modService.applyMods(playerHand, new ArrayList<>(), dynamicDecisions);

        // Assert: Verificar resultados
        // Verificar que el tipo de card3 ha cambiado a EJERCITO
        assertEquals(CardType.EJERCITO, playerHand.get(2).getCardType(), 
            "El tipo de card3 debería haber cambiado a EJERCITO");

        // Verificar que el valor final de card2 ha sido modificado correctamente
        assertEquals(30, playerHand.get(1).getFinalValue(), 
            "El valor final de card2 debería ser 25");

        
    }

    @Test
    void shouldApplyChangeNameTypeShapeMirage() {
        
        when(cardService.findCardById(1)).thenReturn(playerHand.get(0));

        // Eliminar todos los modificadores
        playerHand.forEach(card -> card.setMods(new ArrayList<>()));

        // Añadir el modificador ChangeNameTypeShapeMirage a la carta 3 (Bestia 1)
        ChangeNameTypeShapeMirage changeNameMod = new ChangeNameTypeShapeMirage("Cambia el nombre y tipo", null, null, playerHand.get(2), null, ModType.STATE);
        changeNameMod.setId(10);
        playerHand.get(2).getMods().add(changeNameMod);

        // Crear DecisionDTO para cambiar el nombre y tipo de Bestia 1 (card3) al de Tierra 1 (card1)
        DecisionDTO changeNameDecision = new DecisionDTO(10, 1, null);
        List<DecisionDTO> decisionDTOList = List.of(changeNameDecision);

        // Convertir a un mapa de decisiones dinámicas
        Map<Mod, Decision> dynamicDecisions = modService.convertToDecisionMap(playerHand, decisionDTOList);

        // Aplicar modificadores con decisiones dinámicas
        modService.applyMods(playerHand, new ArrayList<>(), dynamicDecisions);

        // Verificar si el nombre y tipo de Bestia 1 (card3) han cambiado al de Tierra 1 (card1)
        assertEquals(playerHand.get(0).getName(), playerHand.get(2).getName(), "El nombre de card3 debería haber cambiado al de card1");
        assertEquals(playerHand.get(0).getCardType(), playerHand.get(2).getCardType(), "El tipo de card3 debería haber cambiado al de card1");
    }


    @Test
    void shouldApplyChangeNameTypeShapeShifter() {
        
        when(cardService.findCardById(3)).thenReturn(playerHand.get(2));

        // Eliminar todos los modificadores
        playerHand.forEach(card -> card.setMods(new ArrayList<>()));

        // Añadir el modificador ChangeNameTypeShapeShifter a la carta 5 (Inundación 1)
        ChangeNameTypeShapeShifter changeNameMod = new ChangeNameTypeShapeShifter("Adopta nombre y tipo", null, null, playerHand.get(4), null, ModType.STATE);
        changeNameMod.setId(11);
        playerHand.get(4).getMods().add(changeNameMod);

        // Crear DecisionDTO para cambiar el nombre y tipo de Inundación 1 (card5) al de Bestia 1 (card3)
        DecisionDTO changeNameDecision = new DecisionDTO(11, 3, null);
        List<DecisionDTO> decisionDTOList = List.of(changeNameDecision);

        // Convertir a un mapa de decisiones dinámicas
        Map<Mod, Decision> dynamicDecisions = modService.convertToDecisionMap(playerHand, decisionDTOList);

        // Aplicar modificadores con decisiones dinámicas
        modService.applyMods(playerHand, new ArrayList<>(), dynamicDecisions);

        // Verificar si el nombre y tipo de Inundación 1 (card5) han cambiado al de Bestia 1 (card3)
        assertEquals(playerHand.get(2).getName(), playerHand.get(4).getName(), "El nombre de card5 debería haber cambiado al de card3");
        assertEquals(playerHand.get(2).getCardType(), playerHand.get(4).getCardType(), "El tipo de card5 debería haber cambiado al de card3");
    }


    @Test
    void shouldApplyNecromancerMod() {

        // Eliminar todos los modificadores
        playerHand.forEach(card -> card.setMods(new ArrayList<>()));

        // Añadir el modificador NecromancerMod a la carta 1 (Tierra 1)
        NecromancerMod necromancerMod = new NecromancerMod();
        necromancerMod.setId(12);
        necromancerMod.setModType(ModType.NECROMANCER);
        playerHand.get(0).getMods().add(necromancerMod);

        // Crear un montón de descarte con cartas válidas y no válidas para el necromancer
        List<Card> discardPile = new ArrayList<>();
        Card discardedCard1 = new Card("Ejército 2", CardType.EJERCITO, 5);
        discardedCard1.setId(8);
        Card discardedCard2 = new Card("Llama 1", CardType.LLAMA, 10); // Carta no válida
        discardedCard2.setId(9);
        discardPile.add(discardedCard1);
        discardPile.add(discardedCard2);

        // Mockear las cartas del descarte para el cardService
        when(cardService.findCardById(8)).thenReturn(discardedCard1);

        // Crear DecisionDTO para tomar la carta de tipo Ejército del descarte
        DecisionDTO necromancerDecision = new DecisionDTO(12, 8, null);
        List<DecisionDTO> decisionDTOList = List.of(necromancerDecision);

        // Convertir a un mapa de decisiones dinámicas
        Map<Mod, Decision> dynamicDecisions = modService.convertToDecisionMap(playerHand, decisionDTOList);

        // Aplicar modificadores con decisiones dinámicas
        modService.applyMods(playerHand, discardPile, dynamicDecisions);

        // Verificar si la carta de tipo Ejército fue añadida a la mano del jugador
        assertTrue(playerHand.contains(discardedCard1), "La carta Ejército 2 debería haberse añadido a la mano del jugador");

        // Verificar que la carta no válida no fue añadida a la mano del jugador
        assertFalse(playerHand.contains(discardedCard2), "La carta Llama 1 no debería haberse añadido a la mano del jugador");
    }

    @Test
    void shouldApplyChangeAllExceptBonus() {

        when(cardService.findCardById(2)).thenReturn(playerHand.get(1));
    
        // Eliminar todos los modificadores
        playerHand.forEach(card -> card.setMods(new ArrayList<>()));
    
        // Añadir un modificador de penalización a la carta 2 (Tierra 2)
        Mod penaltyMod = new BonusPenaltyForEachTypeMod("PENALIZACIÓN: Resta 5", -5, null, playerHand.get(1), new ArrayList<>(), ModType.PENALTY);
        penaltyMod.setId(11);
        playerHand.get(1).getMods().add(penaltyMod);
    
        // Añadir el modificador ChangeAllExceptBonus a la carta 1 (Tierra 1)
        ChangeAllExceptBonus changeAllExceptBonusMod = new ChangeAllExceptBonus();
        changeAllExceptBonusMod.setId(12);
        changeAllExceptBonusMod.setModType(ModType.STATE);
        changeAllExceptBonusMod.setOriginCard(playerHand.get(0));
        playerHand.get(0).getMods().add(changeAllExceptBonusMod);
    
        // Crear DecisionDTO para duplicar los atributos de Tierra 2 (card2) a Tierra 1 (card1)
        DecisionDTO changeAllExceptBonusDecision = new DecisionDTO(12, 2, null);
        List<DecisionDTO> decisionDTOList = List.of(changeAllExceptBonusDecision);
    
        // Convertir a un mapa de decisiones dinámicas
        Map<Mod, Decision> dynamicDecisions = modService.convertToDecisionMap(playerHand, decisionDTOList);
    
        // Aplicar modificadores con decisiones dinámicas
        modService.applyMods(playerHand, new ArrayList<>(), dynamicDecisions);
    
        // Verificar si el nombre, tipo y base value de Tierra 1 (card1) han cambiado a los de Tierra 2 (card2)
        assertEquals(playerHand.get(1).getName(), playerHand.get(0).getName(), "El nombre de card1 debería haber cambiado al de card2");
        assertEquals(playerHand.get(1).getCardType(), playerHand.get(0).getCardType(), "El tipo de card1 debería haber cambiado al de card2");
        assertEquals(playerHand.get(1).getBaseValue(), playerHand.get(0).getBaseValue(), "El valor base de card1 debería haber cambiado al de card2");
    
        // Verificar si los mods de card1 han sido actualizados con los mods de penalización de card2
        assertEquals(1, playerHand.get(0).getMods().size(), "Card1 debería tener 1 modificador de penalización");
        assertEquals(ModType.PENALTY, playerHand.get(0).getMods().get(0).getModType(), "El tipo de modificador de card1 debería ser PENALTY");
        assertEquals(penaltyMod.getDescription(), playerHand.get(0).getMods().get(0).getDescription(), "El modificador de card1 debería ser una copia del modificador de card2");
    }
    
    @Test
    void shouldApplyClearPenaltyIsland() {
        
        when(cardService.findCardById(5)).thenReturn(playerHand.get(4));
    
        // Eliminar todos los modificadores de las cartas de la mano
        playerHand.forEach(card -> card.setMods(new ArrayList<>()));
    
        // Añadir un modificador de penalización a la carta 5 (Inundación 1)
        Mod penaltyMod = new BonusPenaltyForEachTypeMod("PENALIZACIÓN: Resta 5", -5, null, playerHand.get(4), null, ModType.PENALTY);
        penaltyMod.setId(14);
        playerHand.get(4).getMods().add(penaltyMod);
    
        // Añadir el modificador ClearPenaltyIsland a la carta 1 (Tierra 1)
        ClearPenaltyIsland clearPenaltyIslandMod = new ClearPenaltyIsland();
        clearPenaltyIslandMod.setId(13);
        clearPenaltyIslandMod.setModType(ModType.CLEAR);
        clearPenaltyIslandMod.setOriginCard(playerHand.get(0));
        playerHand.get(0).getMods().add(clearPenaltyIslandMod);
    
        // Crear DecisionDTO para apuntar a Inundación 1 (card5), que cumple con el passCondition
        DecisionDTO clearPenaltyDecision = new DecisionDTO(13, 5, null);
        List<DecisionDTO> decisionDTOList = List.of(clearPenaltyDecision);
    
        // Convertir a un mapa de decisiones dinámicas
        Map<Mod, Decision> dynamicDecisions = modService.convertToDecisionMap(playerHand, decisionDTOList);
    
        // Aplicar modificadores con decisiones dinámicas
        modService.applyMods(playerHand, new ArrayList<>(), dynamicDecisions);
    
        // Verificar si el modificador de penalización fue eliminado de la carta 5 (Inundación 1)
        assertTrue(playerHand.get(4).getMods().isEmpty(), "Los modificadores de penalización deberían haber sido eliminados de la carta 5");
    }
    
    // Probamos que applyMods no se aplique si el mapa de decisiones es vacío pero hay una carta dinámica

    @Test
    void shouldApplyModsWithoutDynamicDecisions() {

        // Verificamos que se lanza una UnsupportedOperationException

        assertThrows(UnsupportedOperationException.class, () -> {
            modService.applyMods(playerHand, new ArrayList<>(), new HashMap<>());
        }); 

    }
    
}
