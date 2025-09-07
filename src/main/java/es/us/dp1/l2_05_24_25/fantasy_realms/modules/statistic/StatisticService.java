package es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.InvalidStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.MatchService;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.NecromancerMod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeAllExceptBonus;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeBaseValue;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeNameTypeShapeMirage;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeNameTypeShapeShifter;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.clear.ClearPenaltyIsland;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerService;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerType;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import lombok.NoArgsConstructor;
@Service
@NoArgsConstructor // para las pruebas unitarias
public class StatisticService {

    private PlayerService playerService;

    private MatchService matchService;

    @Autowired
    public StatisticService(PlayerService playerService, MatchService matchService) {
        this.playerService = playerService;
        this.matchService = matchService;
    }

    /**
     * Número total de partidas jugadas por el usuario autenticado.
     */
    public Integer getTotalMatches(User currentUser) {
        return playerService.countPlayersByUser(currentUser);
    }

    /**
     * Duración promedio de las partidas (en minutos) del usuario autenticado.
     */
    public Double getAverageMatchDuration(User currentUser) {
        List<Match> userMatches = matchService.findMatchesByUser(currentUser);

        return userMatches.stream()
                .filter(match -> match.getStartDate() != null && match.getEndDate() != null)
                .mapToInt(match -> {
                    Integer duration = match.getEndDate().getMinute() - match.getStartDate().getMinute();
                    return duration;
                })
                .average()
                .orElse(0.0);
    }

    /**
     * Tiempo total jugado por el usuario.
     */
    public Integer getTotalMatchDuration(User currentUser) {
        List<Match> userMatches = matchService.findMatchesByUser(currentUser);

        return userMatches.stream()
                .filter(match -> match.getStartDate() != null && match.getEndDate() != null)
                .mapToInt(match -> {
                    Integer duration = match.getEndDate().getMinute() - match.getStartDate().getMinute();
                    return duration;
                })
                .sum();
    }

    /**
     * Duración máxima partida usuario.
     */
    public Integer getMaxMatchDuration(User currentUser) {
        List<Match> userMatches = matchService.findMatchesByUser(currentUser);

        Integer maxDuration = userMatches.stream()
                .filter(match -> match.getStartDate() != null && match.getEndDate() != null)
                .mapToInt(match -> {
                    Integer duration = match.getEndDate().getMinute() - match.getStartDate().getMinute();
                    return duration;
                })
                .max()
                .orElse(0);

        return maxDuration;
    }

    /**
     * Duración mínima partida usuario.
     */
    public Integer getMinMatchDuration(User currentUser) {
        List<Match> userMatches = matchService.findMatchesByUser(currentUser);

        return userMatches.stream()
                .filter(match -> match.getStartDate() != null && match.getEndDate() != null)
                .mapToInt(match -> {
                    Integer duration = match.getEndDate().getMinute() - match.getStartDate().getMinute();
                    return duration;
                })
                .min()
                .orElse(0);
    }

    /**
     * Duración media global de las partidas
     */
    public Double getGlobalAverageMatchDuration() {
        List<Match> totalMatches = matchService.findAllMatches();
        Integer totalDuration = totalMatches.stream()
                .filter(match -> match.getStartDate() != null && match.getEndDate() != null)
                .mapToInt(match -> {
                    Integer duration = match.getEndDate().getMinute() - match.getStartDate().getMinute();
                    return duration;
                })
                .sum();
        return (double) totalDuration / totalMatches.size();
    }

    /**
     * Duración maxima global de las partidas
     */
    public Integer getGlobalMaxMatchDuration() {
        List<Match> totalMatches = matchService.findAllMatches();
        Integer maxDuration = totalMatches.stream()

                .filter(match -> match.getStartDate() != null && match.getEndDate() != null)
                .mapToInt(match -> {
                    Integer duration = match.getEndDate().getMinute() - match.getStartDate().getMinute();
                    return duration;
                })
                .max()
                .orElse(0);

        return maxDuration;
    }

    /**
     * Duración mínima global de las partidas
     */
    public Integer getGlobalMinMatchDuration() {
        List<Match> totalMatches = matchService.findAllMatches();
        Integer minDuration = totalMatches.stream()

                .filter(match -> match.getStartDate() != null && match.getEndDate() != null)
                .mapToInt(match -> {
                    Integer duration = match.getEndDate().getMinute() - match.getStartDate().getMinute();
                    return duration;
                })
                .min()
                .orElse(0);

        return minDuration;
    }

    /**
     * Número promedio de jugadores por partida.
     */
    public Double getAveragePlayersPerMatch(User currentUser) {
        List<Match> userMatches = matchService.findMatchesByUser(currentUser);

        return userMatches.stream()
                .mapToInt(match -> match.getPlayers().size())
                .average()
                .orElse(0.0);
    }

    /**
     * Númeor jugadores partida usuario máximo.
     */
    public Integer getMaxPlayersPerMatch(User currentUser) {
        List<Match> userMatches = matchService.findMatchesByUser(currentUser);

        return userMatches.stream()
                .mapToInt(match -> match.getPlayers().size())
                .max()
                .orElse(0);
    }

    /**
     * Númeor jugadores partida usuario mínimo.
     */
    public Integer getMinPlayersPerMatch(User currentUser) {
        List<Match> userMatches = matchService.findMatchesByUser(currentUser);

        return userMatches.stream()
                .mapToInt(match -> match.getPlayers().size())
                .min()
                .orElse(0);
    }

    /**
     * Número jugadores partida global maximo.
     */
    public Integer getMaxGlobalPlayersPerMatch() {
        List<Match> totalMatches = matchService.findAllMatches();

        return totalMatches.stream()
                .mapToInt(match -> match.getPlayers().size())
                .max()
                .orElse(0);
    }

    /**
     * Número jugadores partida global mínimo.
     */
    public Integer getMinGlobalPlayersPerMatch() {
        List<Match> totalMatches = matchService.findAllMatches();

        return totalMatches.stream()
                .mapToInt(match -> match.getPlayers().size())
                .min()
                .orElse(0);
    }

    /**
     * Número medio global de jugadores por partida
     */
    public Double getGlobalAveragePlayersPerMatch() {
        List<Match> totalMatches = matchService.findAllMatches();
        Integer totalPlayers = totalMatches.stream()
                .filter(match -> match.getStartDate() != null && match.getEndDate() != null)
                .mapToInt(match -> {
                    Integer players = match.getPlayers().size();
                    return players;
                })
                .sum();
        return (double) totalPlayers / totalMatches.size();
    }

    /**
     * Puntos promedio del usuario autenticado.
     */
    public Double getAveragePoints(User currentUser) {

        List<Player> userPlayers = playerService.getPlayersByUser(currentUser);

        return userPlayers.stream()
                .mapToInt(Player::getScore)
                .average()
                .orElse(0.0);
    }

    /**
     * Puntuación máxima obtenida por el usuario autenticado.
     */
    public Integer getMaxPoints(User currentUser) {
        return playerService.getPlayersByUser(currentUser).stream()
                .mapToInt(Player::getScore)
                .max()
                .orElse(0);
    }

    /**
     * Puntuación mínima obtenida por el usuario autenticado.
     */
    public Integer getMinPoints(User currentUser) {
        return playerService.getPlayersByUser(currentUser).stream()
                .mapToInt(Player::getScore)
                .min()
                .orElse(0);
    }

    /**
     * Porcentaje de partidas ganadas por el usuario autenticado.
     */
    public Double getWinPercentage(User currentUser) {
        List<Player> userPlayers = playerService.getPlayersByUser(currentUser);

        Integer totalMatches = userPlayers.size();
        Long wonMatches = userPlayers.stream()
                .filter(player -> player.getRole() == PlayerType.GANADOR)
                .count();

        return totalMatches > 0 ? (wonMatches * 100.0) / totalMatches : 0.0;
    }

    /**
     * Posición media del ranking del usuario autenticado.
     */
    public Double getAverageRankingPosition(User currentUser) {
        // Obtenemos todos los jugadores del usuario autenticado
        List<Player> userPlayers = playerService.getPlayersByUser(currentUser);

        return userPlayers.stream()
                .mapToInt(player -> {
                    // Obtenemos todos los jugadores de la misma partida
                    List<Player> playersInMatch = player.getMatchPlayed().getPlayers();

                    // Ordenamos los jugadores por puntuación descendente
                    List<Player> rankedPlayers = playersInMatch.stream()
                            .sorted((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore())) // Orden descendente
                            .collect(Collectors.toList());

                    // Calculamos la posición del jugador (índice + 1)
                    return rankedPlayers.indexOf(player) + 1;
                })
                .average()
                .orElse(0.0);
    }

    public Double getAverageTurnsPerMatch(User currentUser) {
        // Obtener todos los jugadores del usuario autenticado
        List<Player> userPlayers = playerService.getPlayersByUser(currentUser);

        return userPlayers.stream()
                .mapToDouble(player -> {
                    Match match = player.getMatchPlayed();
                    Integer playersMatch = match.getPlayers().size();

                    // Obtener el turno máximo o actual del partido
                    Integer totalTurnsInMatch = 0;
                    if (match.getCurrentTurn() != null) {
                        totalTurnsInMatch = match.getCurrentTurn().getTurnCount(); // Suponiendo que 'currentTurn' tiene
                                                                                   // un 'turnCount'

                    }
                    return (double) totalTurnsInMatch / playersMatch;

                })
                .average()
                .orElse(0.0);
    }

    /**
     * Devuelve hasta 7 nombres de cartas más frecuentes en las manos finales del
     * usuario autenticado.
     */
    public List<String> getMostFrequentCardsInFinalHands(User currentUser) {
        // Obtener todos los jugadores asociados al usuario autenticado
        List<Player> userPlayers = playerService.getPlayersByUser(currentUser);

        // Crear un mapa para contar la frecuencia de las cartas
        Map<String, Integer> cardFrequencyMap = new HashMap<>();

        // Iterar sobre los jugadores y sus manos finales
        userPlayers.forEach(player -> {
            if (player.getPlayerHand() != null) {
                player.getPlayerHand().forEach(card -> {
                    String cardName = card.getName();
                    cardFrequencyMap.put(cardName, cardFrequencyMap.getOrDefault(cardName, 0) + 1);
                });
            }
        });

        // Ordenar las cartas por frecuencia en orden descendente y devolver las 7 más
        // frecuentes
        return cardFrequencyMap.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue())) // Orden descendente
                .limit(7) // Limitar a las 7 más frecuentes
                .map(Map.Entry::getKey) // Obtener solo los nombres de las cartas
                .collect(Collectors.toList());
    }

    /**
     * Devuelve el número total de partidas que el usuario ha ganado.
     */
    public long getTotalWins(User currentUser) {

        List<Player> userPlayers = playerService.getPlayersByUser(currentUser);

        long wonMatches = userPlayers.stream()
                .filter(player -> player.getRole() == PlayerType.GANADOR).count();

        return wonMatches;
    };

    /**
     * Devuelve la racha actual de victorias consecutivas del usuario.
     */
    public int getCurrentWinStreak(User currentUser) {

        List<Player> userPlayers = playerService.getPlayersByUser(currentUser);

        List<Match> userMatches = userPlayers.stream()
                .map(player -> player.getMatchPlayed())
                .sorted(Comparator.comparing(Match::getEndDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        return calculateWinStreak(userMatches, currentUser);

    };

    /**
     * Calcula la racha actual de victorias consecutivas a partir de la lista de
     * partidas.
     */
    private int calculateWinStreak(List<Match> matches, User currentUser) {
        int streak = 0;

        for (Match match : matches) {
            boolean isWinner = match.getPlayers().stream()
                    .anyMatch(player -> player.getRole() == PlayerType.GANADOR && player.getUser().equals(currentUser));

            if (isWinner) {
                streak++; // Incrementar la racha si el usuario ganó esta partida
            } else {
                break; // Romper la racha si el usuario no ganó esta partida
            }
        }

        return streak;
    }

    /**
     * Retorna true si el usuario quedó en última posición en su penúltima partida
     * pero ha ganado la última jugada.
     */
    public boolean lastGameWasFromLastPlace(User user) {

        List<Player> userPlayers = playerService.getPlayersByUser(user);
        List<Match> userMatches = userPlayers.stream()
                .map(Player::getMatchPlayed)
                .distinct()
                .sorted(Comparator.comparing(Match::getEndDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        // Necesitamos al menos dos partidas para realizar la verificación
        if (userMatches.size() < 2) {
            return false; // No hay suficientes partidas para evaluar
        }

        // Obtener la última y penúltima partida
        Match lastMatch = userMatches.get(0);
        Match secondToLastMatch = userMatches.get(1);

        // Verificar si el usuario ganó la última partida
        boolean wonLastMatch = lastMatch.getPlayers().stream()
                .anyMatch(player -> player.getUser().equals(user) && player.getRole() == PlayerType.GANADOR);

        // Verificar si el usuario quedó en última posición en la penúltima partida
        boolean wasLastInSecondToLastMatch = secondToLastMatch.getPlayers().stream()
                .filter(player -> player.getUser().equals(user))
                .anyMatch(player -> {
                    // Verificar si tiene el puntaje más bajo
                    int userScore = player.getScore();
                    return secondToLastMatch.getPlayers().stream()
                            .allMatch(otherPlayer -> otherPlayer.getScore() >= userScore);
                });

        return wonLastMatch && wasLastInSecondToLastMatch;

    }

    /**
     * Devuelve true si el usuario ganó la última partida jugada.
     */
    public Match getLastWonMatch(User currentUser) {
        List<Player> userPlayers = playerService.getPlayersByUser(currentUser);

        List<Match> userMatches = userPlayers.stream()
                .map(Player::getMatchPlayed)
                .distinct()
                .sorted(Comparator.comparing(Match::getEndDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        // Obtener la última partida
        Match lastMatch = userMatches.get(0);

        // Verificar si el usuario ganó la última partida
        boolean wonLastMatch = lastMatch.getPlayers().stream()
                .anyMatch(player -> player.getUser().equals(currentUser) && player.getRole() == PlayerType.GANADOR);

        return wonLastMatch ? lastMatch : null;
    }

    public int getPointsLastMatch(User currentUser) {

        Match lastWonMatch = getLastWonMatch(currentUser);

        if (lastWonMatch == null)
            return 0;

        return lastWonMatch.getPlayers().stream().filter(player -> player.getUser().equals(currentUser)).findFirst()
                .get().getScore();
    }

    /**
     * Indica si en la última partida ganada por el usuario tenía "Rey" y "Reina".
     */
    public boolean hadKingAndQueenInLastGame(User currentUser) {

        Match lastWonMatch = getLastWonMatch(currentUser);

        if (lastWonMatch == null)
            return false;

        return lastWonMatch.getPlayers().stream()
                .filter(player -> player.getUser().equals(currentUser))
                .flatMap(player -> player.getPlayerHand().stream())
                .map(Card::getName)
                .collect(Collectors.toSet())
                .containsAll(Set.of("Rey", "Reina"));
    }

    /**
     * Retorna true si en la última partida ganada por el usuario tenía la carta
     * “Maestro de bestias”
     * y al menos 3 cartas de tipo "Bestia" en su mano final.
     */
    public boolean hadBeastmasterAnd3Beasts(User currentUser) {
        Match lastWonMatch = getLastWonMatch(currentUser);
        if (lastWonMatch == null)
            return false;

        List<Card> finalHand = lastWonMatch.getPlayers().stream()
                .filter(player -> player.getUser().equals(currentUser))
                .flatMap(player -> player.getPlayerHand().stream())
                .toList();

        boolean hasBeastmaster = finalHand.stream().anyMatch(card -> card.getName().equals("Maestro de bestias"));
        long beastCount = finalHand.stream().filter(card -> card.getCardType().equals(CardType.BESTIA)).count();

        return hasBeastmaster && beastCount >= 3;
    }

    /**
     * Indica si en la última partida ganada por el usuario tenía "Escudo de Keth" y
     * “Espada de Keth”
     * en su mano final.
     */
    public boolean hadSwordAndShieldOfKeth(User currentUser) {
        Match lastWonMatch = getLastWonMatch(currentUser);
        if (lastWonMatch == null)
            return false;

        Set<String> finalHandNames = lastWonMatch.getPlayers().stream()
                .filter(player -> player.getUser().equals(currentUser))
                .flatMap(player -> player.getPlayerHand().stream())
                .map(Card::getName)
                .collect(Collectors.toSet());

        return finalHandNames.contains("Escudo de Keth") && finalHandNames.contains("Espada de Keth");
    }

    /**
     * Retorna true si el usuario tenía las 4 cartas elementales en la mano final de
     * su última partida.
     */
    public boolean had4Elementals(User currentUser) {
        Match lastWonMatch = getLastWonMatch(currentUser);
        if (lastWonMatch == null)
            return false;

        Set<String> finalHandNames = lastWonMatch.getPlayers().stream()
                .filter(player -> player.getUser().equals(currentUser))
                .flatMap(player -> player.getPlayerHand().stream())
                .map(Card::getName)
                .collect(Collectors.toSet());

        return finalHandNames.containsAll(
                Set.of("Elemental de Fuego", "Elemental de Agua", "Elemental de Tierra", "Elemental de aire"));
    }

    /**
     * Indica si el usuario no tuvo cartas con modificadores especiales en su mano
     * final de la última partida.
     */
    public boolean hadNoRareCardsInLastGame(User currentUser) {
        Match lastWonMatch = getLastWonMatch(currentUser);
        if (lastWonMatch == null)
            return false;

        // Clases de mods que queremos excluir
        Set<Class<? extends Mod>> restrictedModClasses = Set.of(
                ChangeType.class,
                ChangeBaseValue.class,
                ChangeNameTypeShapeMirage.class,
                ChangeNameTypeShapeShifter.class,
                NecromancerMod.class,
                ChangeAllExceptBonus.class,
                ClearPenaltyIsland.class);

        // Verificar que ninguna carta del usuario en la última partida ganada tiene un
        // mod de las clases prohibidas
        return lastWonMatch.getPlayers().stream()
                .filter(player -> player.getUser().equals(currentUser))
                .flatMap(player -> player.getPlayerHand().stream())
                .flatMap(card -> card.getMods().stream())
                .noneMatch(mod -> restrictedModClasses.contains(mod.getClass()));
    }

    public int getTotalCardsPlayedByType(User currentUser, String type) {
        // Validar si el tipo existe en CardType
        CardType cType = Arrays.stream(CardType.values())
                               .filter(ct -> ct.name().equalsIgnoreCase(type))
                               .findFirst()
                               .orElseThrow(() -> new InvalidStatesException("Invalid card type: " + type));
    
        // Obtener jugadores relacionados con el usuario
        List<Player> userPlayers = playerService.getPlayersByUser(currentUser);
    
        // Contar cartas del tipo especificado
        return (int) userPlayers.stream()
                    .flatMap(player -> player.getPlayerHand().stream())
                    .filter(card -> card.getCardType().equals(cType))
                    .count();
    }

    /**
     * Devuelve un diccionario cuyas claves son el nombre del usuario y los valores
     * las veces que ha ganado una partida.
     */
    public Map<String, List<Integer>> getRanking(String sortBy) {
        // Declarar el mapa para almacenar estadísticas de usuarios
        Map<String, List<Integer>> userStats = new HashMap<>();

        // Obtener todas las partidas jugadas
        List<Match> totalMatches = matchService.findAllMatches();

        totalMatches.stream()
                .flatMap(match -> match.getPlayers().stream()) // Iterar sobre todos los jugadores de todas las partidas
                .forEach(player -> {
                    String username = player.getUser().getUsername(); // Obtener el nombre de usuario
                    int score = player.getScore(); // Obtener la puntuación del jugador
                    boolean isWinner = player.getRole().toString() == "GANADOR"; // Verificar si es ganador

                    // Si el usuario ya está en el diccionario
                    if (userStats.containsKey(username)) {
                        List<Integer> stats = userStats.get(username);
                        int wins = stats.get(0); // Número actual de victorias
                        int totalPoints = stats.get(1); // Número actual de puntos

                        // Actualizar estadísticas
                        if (isWinner) {
                            wins++; // Incrementar victorias si es ganador
                        }
                        totalPoints += score; // Sumar puntos siempre

                        // Actualizar el mapa con las nuevas estadísticas
                        userStats.put(username, Arrays.asList(wins, totalPoints));
                    } else {
                        // Si el usuario no está en el diccionario, inicializarlo
                        int wins = isWinner ? 1 : 0; // 1 victoria si es ganador, 0 en caso contrario
                        userStats.put(username, Arrays.asList(wins, score));
                    }
                });

        // Ordenar el mapa según el criterio de ordenación
        Comparator<Map.Entry<String, List<Integer>>> comparator;
        if ("POINTS".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(entry -> entry.getValue().get(1)); // Ordenar por puntos
        } else {
            comparator = Comparator.comparing(entry -> entry.getValue().get(0)); // Ordenar por victorias
        }

        return userStats.entrySet()
                .stream()
                .sorted(comparator.reversed()) // Orden descendente
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new // Mantener el orden
                ));
    }

}