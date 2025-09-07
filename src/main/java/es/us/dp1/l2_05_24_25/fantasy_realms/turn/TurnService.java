package es.us.dp1.l2_05_24_25.fantasy_realms.turn;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.dto.DecisionDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.MatchStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.TurnStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Decision;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModService;
import es.us.dp1.l2_05_24_25.fantasy_realms.patterns.builders.TurnBuilder;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerType;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;

/**
 * Servicio que gestiona la lógica de turnos en una partida.
 */
@Service
public class TurnService {

    private final ModService modService;

    @Autowired
    public TurnService(ModService modService) {
        this.modService = modService;
    }

    /**
     * Inicializa el primer turno de una partida asignando al primer jugador válido.
     * Añade la marca de tiempo de inicio del turno (turnStartTime).
     *
     * @param match Partida en la que se establece el turno inicial.
     */
    @Transactional
    public void firstTurn(Match match) {
        // Filtra la lista de jugadores que no son espectadores
        List<Player> nonSpectatorPlayers = match.getPlayers().stream()
            .filter(player -> !player.getRole().equals(PlayerType.ESPECTADOR))
            .collect(Collectors.toList());

        // Verifica que exista al menos un jugador válido, de lo contrario lanza una excepción
        if (nonSpectatorPlayers.isEmpty()) {
            throw new IllegalStateException("No hay jugadores válidos para iniciar la partida.");
        }

        // Selecciona un jugador aleatorio de la lista filtrada
        Random random = new Random();
        Player firstPlayer = nonSpectatorPlayers.get(random.nextInt(nonSpectatorPlayers.size()));

        // Construye el primer turno con el jugador seleccionado
        Turn firstTurn = new TurnBuilder()
            .setPlayer(firstPlayer)
            .setTurnCount(1)
            .build();

        // Marca la hora de inicio del primer turno
        firstTurn.setTurnStartTime(Instant.now());

        // Asigna el primer turno a la partida
        match.setCurrentTurn(firstTurn);
    }


    /**
     * Cancela el turno actual de la partida, revirtiendo acciones si es necesario 
     * y pasando al siguiente turno.
     *
     * @param user  Usuario que solicita la cancelación del turno.
     * @param match Partida en curso.
     */    
    @Transactional
    public void cancelTurn(User user, Match match) {

        // 1. Obtener el turno actual de la partida
        Turn currentTurn = match.getCurrentTurn();

        // 2. Validar condiciones: el usuario debe tener el turno y la partida debe estar en curso
        validateConditionsForCancel(currentTurn, match, user);

        // 3. Si el turno tiene acciones que revertir (se robó carta y no se descartó),
        //    las revertimos
        if (currentTurn.hasActionsToRevert()) {
            cancelTurnActions(match, currentTurn);
        }

        // 4. Pasamos al siguiente turno
        nextTurn(match);
    }

    /**
     * Gestiona un turno especial aplicando modificadores dinámicos y decide si se 
     * debe finalizar la partida.
     *
     * @param user             Usuario que realiza el turno.
     * @param match            Partida en curso.
     * @param dynamicDecisions Decisiones dinámicas para los modificadores.
     * @return true si se finaliza la partida, false si continúa al siguiente turno.
     */
    @Transactional
    public boolean specialTurn(User user, Match match, List<DecisionDTO> dynamicDecisions) {

        // 1. Obtener el turno actual
        Turn currentTurn = match.getCurrentTurn();

        // 2. Calcular cuántos turnos máximos habrá en la fase especial
        int maxSpecialTurn = match.getMaxSpecialTurn();

        // 3. Validar el turno especial (fase de scoring activa, usuario correcto)
        validateSpecialTurn(match, currentTurn, user);

        // 4. Convertir decisiones a mapa y aplicar modificadores
        convertDecisionsAndApply(match, currentTurn, dynamicDecisions);

        // 5. Comprobar si es el último turno especial o no
        return passOrEndMatch(match, currentTurn, maxSpecialTurn);
    }

    /**
     * Avanza al siguiente turno de forma circular, asignando al siguiente jugador
     * y marcando la hora de inicio de ese nuevo turno.
     *
     * @param match Partida en curso.
     */
    public void nextTurn(Match match) {

        // 1. Obtener el turno actual
        Turn currentTurn = match.getCurrentTurn();

        // 2. Determinar el siguiente jugador de forma circular
        Player nextPlayer = determineNextPlayer(match, currentTurn.getPlayer());

        // 3. Construir el nuevo turno
        Turn nextTurn = new TurnBuilder()
            .setPlayer(nextPlayer)
            .setTurnCount(currentTurn.getTurnCount() + 1)
            .build();

        // Marcar la hora de inicio del siguiente turno
        nextTurn.setTurnStartTime(Instant.now());

        // 4. Asignarlo a la partida
        match.setCurrentTurn(nextTurn);
    }

    public void validateConditionsForCancel(Turn currentTurn, Match match, User user) {
        ensurePlayerHasTheTurn(currentTurn, user);
        ensureMatchIsInProgress(match);
        ensurePlayerIsNotSpectator(currentTurn.getPlayer());
    }
    
    /**
     * Verifica que el jugador no sea un espectador.
     *
     * @param player Jugador a validar.
     */
    private void ensurePlayerIsNotSpectator(Player player) {
        if (player.getRole().equals(PlayerType.ESPECTADOR)) {
            throw new TurnStatesException("Spectators cannot participate in turns.");
        }
    }    

    /**
     * Verifica que el usuario actual sea el que tiene el turno.
     *
     * @param currentTurn Turno actual de la partida.
     * @param user        Usuario a validar.
     */
    public void ensurePlayerHasTheTurn(Turn currentTurn, User user) {
        String currentUsername = currentTurn.getPlayer().getUser().getUsername();
        if (!currentUsername.equals(user.getUsername())) {
            throw new TurnStatesException("The user must be the one with the current turn!");
        }
    }

    /**
     * Verifica que la partida esté en curso.
     *
     * @param match Partida a validar.
     */
    public void ensureMatchIsInProgress(Match match) {
        if (!match.isInProgress()) {
            throw new MatchStatesException("The match is not in progress!");
        }
    }

    /**
     * Revierten las acciones realizadas en un turno cuando es cancelado.
     *
     * @param match       Partida en curso.
     * @param currentTurn Turno a cancelar.
     */
    public void cancelTurnActions(Match match, Turn currentTurn) {

        // 1. Jugador y carta robada
        Player player = currentTurn.getPlayer();
        Card cardDrawn = currentTurn.getCardDrawn();

        // 2. Eliminar la carta de la mano del jugador
        player.getPlayerHand().remove(cardDrawn);

        // 3. Revertir: si vino del mazo, la devolvemos a la parte superior;
        //    si vino del descarte, la regresamos allí.
        revertActions(match, cardDrawn, currentTurn);
    }

    /**
     * Devuelve una carta a su origen (mazo o pila de descartes) tras cancelar acciones.
     *
     * @param match      Partida en curso.
     * @param cardDrawn  Carta robada a devolver.
     * @param currentTurn Turno actual con las acciones revertidas.
     */
    public void revertActions(Match match, Card cardDrawn, Turn currentTurn) {
        // Si la carta se robó del mazo:
        if (currentTurn.getDrawSource() == DrawEnum.DECK) {
            match.getDeck().addCardDeck(cardDrawn);
        } else {
            // Si se robó de la pila de descartes:
            match.getDiscard().addCardDiscard(cardDrawn);
        }
    }

    /**
     * Valida que la partida esté en fase de turnos especiales y el usuario tenga el turno actual.
     *
     * @param match       Partida en curso.
     * @param currentTurn Turno actual.
     * @param user        Usuario a validar.
     */
    public void validateSpecialTurn(Match match, Turn currentTurn, User user) {
        ensureMatchIsInScoringPhase(match);
        ensurePlayerHasTheTurn(currentTurn, user);
    }

    /**
     * Verifica que la partida esté en la fase de turnos especiales.
     *
     * @param match Partida en curso.
     */
    public void ensureMatchIsInScoringPhase(Match match) {
        if (!match.isInScoringPhase()) {
            throw new MatchStatesException("The match is not in the scoring phase yet!");
        }
    }

    /**
     * Convierte las decisiones dinámicas en un mapa y aplica los modificadores sobre las cartas.
     *
     * @param match            Partida en curso.
     * @param currentTurn      Turno actual.
     * @param dynamicDecisions Decisiones dinámicas proporcionadas.
     */
    public void convertDecisionsAndApply(Match match, Turn currentTurn, List<DecisionDTO> dynamicDecisions) {
        Map<Mod, Decision> modDecisionMap = 
            modService.convertToDecisionMap(currentTurn.getPlayer().getPlayerHand(), dynamicDecisions);

        modService.applyMods(
            currentTurn.getPlayer().getPlayerHand(),
            match.getDiscard().getCards(),
            modDecisionMap
        );
    }

    /**
     * Determina si la partida finaliza o pasa al siguiente turno en fase especial.
     *
     * @param match         Partida en curso.
     * @param currentTurn   Turno actual.
     * @param maxSpecialTurn Número máximo de turnos en fase especial.
     * @return true si finaliza la partida, false si continúa.
     */
    public boolean passOrEndMatch(Match match, Turn currentTurn, int maxSpecialTurn) {
        if (currentTurn.getTurnCount() == maxSpecialTurn) {
            return true; // Finaliza la partida
        } else {
            // Salta a jugadores válidos
            Player nextPlayer = determineNextPlayer(match, currentTurn.getPlayer());
            Turn nextTurn = new TurnBuilder()
                .setPlayer(nextPlayer)
                .setTurnCount(currentTurn.getTurnCount() + 1)
                .build();

            // Marcar la hora de inicio del nuevo turno en fase especial
            nextTurn.setTurnStartTime(Instant.now());

            match.setCurrentTurn(nextTurn);
            return false; // Continúa la partida
        }
    }

    /**
     * Determina el siguiente jugador válido (excluyendo roles como ESPECTADOR) 
     * de forma circular en la lista de jugadores.
     *
     * @param match         Partida en curso.
     * @param currentPlayer Jugador actual.
     * @return El siguiente jugador en el turno.
     */
    public Player determineNextPlayer(Match match, Player currentPlayer) {
        List<Player> eligiblePlayers = match.getPlayers().stream()
            .filter(player -> !player.getRole().equals(PlayerType.ESPECTADOR))
            .toList();

        int currentIndex = eligiblePlayers.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % eligiblePlayers.size();

        return eligiblePlayers.get(nextIndex);
    }

}
