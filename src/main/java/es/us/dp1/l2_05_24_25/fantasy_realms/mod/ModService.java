package es.us.dp1.l2_05_24_25.fantasy_realms.mod;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardService;
import es.us.dp1.l2_05_24_25.fantasy_realms.dto.DecisionDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.NecromancerMod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeAllExceptBonus;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeBaseValue;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeNameTypeShapeMirage;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeNameTypeShapeShifter;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.clear.ClearPenaltyIsland;

/**
 * Servicio que gestiona la persistencia y la aplicación de modificadores (Mods).
 */

@Service
public class ModService {

    private static final Logger log = LoggerFactory.getLogger(ModService.class);

    private final ModRepository modRepository;
    private final CardService cardService;

    @Autowired
    public ModService(ModRepository modRepository, CardService cardService) {
        this.modRepository = modRepository;
        this.cardService = cardService;
    }

    /**
     * Convierte una lista de {@link DecisionDTO} proveniente del frontend en 
     * un Map<Mod, Decision>, relacionando cada Mod con la decisión correspondiente.
     *
     * @param playerHand   Mano de cartas del jugador (asumimos que no es nula/ vacía) en el juego real
     * @param decisionList Lista de DTOs que describen la decisión para cada Mod
     * @return Mapa de Mod -> Decision, listo para usarse en la lógica de aplicación de Mods
     */
    public Map<Mod, Decision> convertToDecisionMap(List<Card> playerHand, List<DecisionDTO> decisionList) {

        // Si la lista de decisiones está vacía o nula, no hay nada que hacer
        if (decisionList == null || decisionList.isEmpty()) {
            return Collections.emptyMap();
        }

        // Indexamos los Mods por su ID para buscarlos rápidamente
        Map<Integer, Mod> modIndex = createModIndex(playerHand);

        Map<Mod, Decision> result = new HashMap<>();
        for (DecisionDTO dto : decisionList) {
            if (dto == null || dto.getModId() == null) {
                // Si el DTO está incompleto o no tiene modId, saltamos
                continue;
            }
            Mod mod = modIndex.get(dto.getModId());
            if (mod == null) {
                // Si no existe un Mod con el modId recibido, lo ignoramos
                continue;
            }
            // Construimos la Decision a partir del DTO
            Decision decision = buildDecision(dto);
            // Asociamos el Mod con la Decision
            result.put(mod, decision);
        }
        return result;
    }

    /**
     * Crea un índice que asocia cada ID de Mod con el objeto Mod correspondiente,
     * recorriendo los Mods de todas las cartas en la mano del jugador.
     */
    private Map<Integer, Mod> createModIndex(List<Card> playerHand) {
        return playerHand.stream()
            .flatMap(card -> card.getMods().stream())
            .collect(Collectors.toMap(Mod::getId, mod -> mod));
    }

    /**
     * Construye un objeto {@link Decision} a partir del DTO que contiene
     * la información de la carta objetivo y/o el tipo de carta.
     */
    private Decision buildDecision(DecisionDTO dto) {
        Decision decision = new Decision();
        // Si el ID de carta no es nulo, buscamos la carta en DB. Si es nulo, 
        // la CardService lanzará la excepción ResourceNotFound si no existe.
        if (dto.getCardId() != null) {
            Card targetCard = cardService.findCardById(dto.getCardId());
            decision.setTargetCard(targetCard);
        }
        // Si el DTO incluye un tipo de carta, lo asignamos
        if (dto.getCardType() != null) {
            decision.setTargetCardType(dto.getCardType());
        }
        return decision;
    }

    /**
     * Aplica todos los modificadores a la mano del jugador, respetando las prioridades.
     * Además, maneja los modificadores dinámicos (decisiones).
     * 
     * @param playerHand      Mano de cartas del jugador
     * @param discardPile     Pila de descartes
     * @param dynamicDecisions Mapa que relaciona un Mod con la Decision tomada en el frontend
     */
    @Transactional
    public void applyMods(List<Card> playerHand, List<Card> discardPile, Map<Mod, Decision> dynamicDecisions) {

        // Extraer todos los modificadores de las cartas de la mano
        List<Mod> modsToApply = playerHand.stream()
                .flatMap(card -> card.getMods().stream())
                .collect(Collectors.toList());

        // Ordenar los modificadores por prioridad (definida en ModType)
        modsToApply.sort(Comparator.comparingInt(mod -> mod.getModType().getPriority()));

        // Recorrer y aplicar cada Mod por orden de prioridad
        for (int i = 0; i < modsToApply.size(); i++) {
            Mod mod = modsToApply.get(i);

            // Verificar si el Mod sigue existiendo en la mano
            if (!isModStillInHand(mod, playerHand)) {
                modsToApply.remove(i--);
                continue;
            }

            // Si hay una Decision asociada a este Mod, la aplicamos
            Decision decision = dynamicDecisions.get(mod);
            if (decision != null) {
                applyDynamicMod(mod, decision, playerHand, discardPile);
            } else {
                // Lógica por defecto
                mod.applyMod(playerHand);
            }
        }
    }


    /**
     * Aplica un Mod que requiere decisiones dinámicas.
     * 
     * @param mod         Mod a aplicar
     * @param decision    Decisión tomada por el jugador
     * @param playerHand  Mano del jugador
     * @param discardPile Pila de descartes
     */
    private void applyDynamicMod(Mod mod, Decision decision, List<Card> playerHand, List<Card> discardPile) {

        // Casos especiales según la subclase de Mod
        
        if (mod instanceof NecromancerMod) {
            ((NecromancerMod) mod).applyMod(playerHand, discardPile, decision.getTargetCard());
        } else if (mod instanceof ChangeAllExceptBonus) {
            ((ChangeAllExceptBonus) mod).applyMod(playerHand, decision.getTargetCard());
        } else if (mod instanceof ChangeNameTypeShapeMirage) {
            ((ChangeNameTypeShapeMirage) mod).applyMod(playerHand, decision.getTargetCard());
        } else if (mod instanceof ChangeNameTypeShapeShifter) {
            ((ChangeNameTypeShapeShifter) mod).applyMod(playerHand, decision.getTargetCard());
        } else if (mod instanceof ChangeType) {
            ((ChangeType) mod).applyMod(playerHand, decision.getTargetCard(), decision.getTargetCardType());
        } else if (mod instanceof ChangeBaseValue) {
            ((ChangeBaseValue) mod).applyMod(playerHand, decision.getTargetCard());
        }  else if (mod instanceof ClearPenaltyIsland) {
            ((ClearPenaltyIsland) mod).applyMod(playerHand, decision.getTargetCard());
        } else {
        // Si el Mod no encaja en los anteriores, aplicamos su lógica por defecto
        mod.applyMod(playerHand);
        }
    }

    /**
     * Verifica si un Mod sigue presente en la mano del jugador
     * 
     * @param mod        Mod a comprobar
     * @param playerHand Mano del jugador
     * @return true si el Mod permanece en alguna de las cartas de la mano
     */
    private boolean isModStillInHand(Mod mod, List<Card> playerHand) {
        return playerHand.stream()
                .flatMap(card -> card.getMods().stream())
                .anyMatch(m -> m.equals(mod));
    }

    /**
     * Devuelve todos los modificadores guardados en la base de datos.
     * 
     * @return Iterable<Mod>
     */
    @Transactional
    public Iterable<Mod> findAll() {
        return modRepository.findAll();
    }

    /**
     * Encuentra un modificador por su ID.
     * 
     * @param id ID del modificador
     * @return Mod encontrado
     * @throws ResourceNotFoundException si no existe un Mod con ese ID
     */
    @Transactional
    public Mod findModById(Integer id) {
        return modRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Mod", "id", id));
    }

    /**
     * Guarda (crea o actualiza) un modificador en la base de datos.
     * 
     * @param mod Instancia de Mod a guardar
     * @return Mod guardado
     */
    @Transactional
    public Mod saveMod(Mod mod) {
        return modRepository.save(mod);
    }

    /**
     * Elimina un Mod de la base de datos por su ID.
     * 
     * @param id del modificador a eliminar
     */
    @Transactional
    public void deleteModById(Integer id) {
        modRepository.deleteById(id);
    }

}
