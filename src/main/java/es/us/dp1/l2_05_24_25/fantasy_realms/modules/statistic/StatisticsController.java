
package es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/statistics")
@Tag(name = "Statistics", description = """
    API para la gestión de estadísticas. Todos los endpoints requieren autenticación Bearer y pueden ser utilizados por cualquier usuario registrado en la aplicación.
    """)
public class StatisticsController {

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private UserService userService; // Para obtener el usuario autenticado

    private User getCurrentUser() {
        User currentUser = userService.findCurrentUser();
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
        }
        return currentUser;
    }

    /**
     * Obtiene el total de partidas jugadas por el usuario actual.
     *
     * Este endpoint devuelve el número total de partidas jugadas por el usuario
     * actual.
     *
     * @return Número total de partidas jugadas por el usuario actual.
     */
    @Operation(summary = "Obtener total de partidas jugadas por el usuario", description = "Devuelve el número total de partidas jugadas por el usuario actual.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total de partidas obtenidas con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })

    @GetMapping("/total-matches")
    public ResponseEntity<Integer> getTotalMatches() {
        User currentUser = getCurrentUser();
        Integer totalMatches = statisticService.getTotalMatches(currentUser);
        return ResponseEntity.ok(totalMatches);
    }

    /*
     * @GetMapping("/global-matches-average")
     * public ResponseEntity<Double> getGlobalAverageMatches() {
     * Double averageMatches = statisticService.getGlobalAverageMatches();
     * return ResponseEntity.ok(averageMatches);
     * }
     */

    /**
     * Obtiene la duración promedio de las partidas jugadas por el usuario actual.
     *
     * Este endpoint devuelve la duración promedio de las partidas jugadas por el
     * usuario actual.
     *
     * @return Duración promedio de las partidas.
     */
    @Operation(summary = "Obtener duración promedio de las partidas del usuario", description = "Devuelve la duración promedio de las partidas jugadas por el usuario actual.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Duración promedio obtenida con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/average-match-duration")
    public ResponseEntity<Double> getAverageMatchDuration() {
        User currentUser = getCurrentUser();
        Double averageMatchDuration = statisticService.getAverageMatchDuration(currentUser);
        return ResponseEntity.ok(averageMatchDuration);
    }

    /**
     * Obtiene la duración total de las partidas jugadas por el usuario actual.
     *
     * Este endpoint devuelve la suma total de las duraciones de las partidas
     * jugadas por el usuario actual.
     *
     * @return Duración total de las partidas.
     */
    @Operation(summary = "Obtener duración total de las partidas jugadas", description = "Devuelve la suma de las duraciones de las partidas jugadas por el usuario actual.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Duración total obtenida con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/total-match-duration")
    public ResponseEntity<Integer> getTotalMatchDuration() {
        User currentUser = getCurrentUser();
        Integer totalMatchDuration = statisticService.getTotalMatchDuration(currentUser);
        return ResponseEntity.ok(totalMatchDuration);
    }

    /**
     * Obtiene la duración máxima de las partidas jugadas por el usuario actual.
     *
     * Este endpoint devuelve la duración máxima registrada de las partidas jugadas
     * por el usuario actual.
     *
     * @return Duración máxima de las partidas.
     */
    @Operation(summary = "Obtener duración máxima de las partidas jugadas", description = "Devuelve la duración máxima registrada de las partidas jugadas por el usuario actual.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Duración máxima obtenida con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/max-match-duration")
    public ResponseEntity<Integer> getMaxMatchDuration() {
        User currentUser = getCurrentUser();
        Integer maxMatchDuration = statisticService.getMaxMatchDuration(currentUser);
        return ResponseEntity.ok(maxMatchDuration);
    }

    /**
     * Obtiene la duración mínima de las partidas jugadas por el usuario actual.
     *
     * Este endpoint devuelve la duración mínima registrada de las partidas jugadas
     * por el usuario actual.
     *
     * @return Duración mínima de las partidas.
     */
    @Operation(summary = "Obtener duración mínima de las partidas jugadas", description = "Devuelve la duración mínima registrada de las partidas jugadas por el usuario actual.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Duración mínima obtenida con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/min-match-duration")
    public ResponseEntity<Integer> getMinMatchDuration() {
        User currentUser = getCurrentUser();
        Integer minMatchDuration = statisticService.getMinMatchDuration(currentUser);
        return ResponseEntity.ok(minMatchDuration);
    }

    /**
     * Obtiene la duración promedio de las partidas a nivel global.
     *
     * Este endpoint devuelve el promedio de las duraciones de todas las partidas
     * registradas en el sistema.
     *
     * @return Duración promedio global de las partidas.
     */
    @Operation(summary = "Obtener duración promedio global de las partidas", description = "Devuelve la duración promedio de todas las partidas registradas en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Duración promedio global obtenida con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/global-match-duration-average")
    public ResponseEntity<Double> getGlobalAverageMatchDuration() {
        Double averageDuration = statisticService.getGlobalAverageMatchDuration();
        return ResponseEntity.ok(averageDuration);
    }

    /**
     * Obtiene la duración máxima de las partidas a nivel global.
     *
     * Este endpoint devuelve la duración máxima registrada de todas las partidas
     * registradas en el sistema.
     *
     * @return Duración máxima global de las partidas.
     */
    @Operation(summary = "Obtener duración máxima global de las partidas", description = "Devuelve la duración máxima registrada de todas las partidas en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Duración máxima global obtenida con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/global-match-max-duration")
    public ResponseEntity<Integer> getGlobalMaxMatchDuration() {
        Integer maxDuration = statisticService.getGlobalMaxMatchDuration();
        return ResponseEntity.ok(maxDuration);
    }

    /**
     * Obtiene la duración mínima de las partidas a nivel global.
     *
     * Este endpoint devuelve la duración mínima registrada de todas las partidas
     * registradas en el sistema.
     *
     * @return Duración mínima global de las partidas.
     */
    @Operation(summary = "Obtener duración mínima global de las partidas", description = "Devuelve la duración mínima registrada de todas las partidas en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Duración mínima global obtenida con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/global-match-min-duration")
    public ResponseEntity<Integer> getGlobalMinMatchDuration() {
        Integer minDuration = statisticService.getGlobalMinMatchDuration();
        return ResponseEntity.ok(minDuration);
    }

    /**
     * Obtiene el número promedio de jugadores por partida.
     *
     * Este endpoint devuelve el número promedio de jugadores por partida en las
     * partidas jugadas por el usuario actual.
     *
     * @return Promedio de jugadores por partida.
     */
    @Operation(summary = "Obtener número promedio de jugadores por partida", description = "Devuelve el número promedio de jugadores por partida en las partidas jugadas por el usuario actual.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promedio de jugadores por partida obtenido con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/average-players-per-match")
    public ResponseEntity<Double> getAveragePlayersPerMatch() {
        User currentUser = getCurrentUser();
        Double averagePlayersPerMatch = statisticService.getAveragePlayersPerMatch(currentUser);
        return ResponseEntity.ok(averagePlayersPerMatch);
    }

    /**
     * Obtiene el número máximo de jugadores en una partida jugada por el usuario
     * actual.
     *
     * Este endpoint devuelve el número máximo de jugadores en las partidas jugadas
     * por el usuario actual.
     *
     * @return Número máximo de jugadores en una partida.
     */
    @Operation(summary = "Obtener número máximo de jugadores por partida del usuario", description = "Devuelve el número máximo de jugadores en las partidas jugadas por el usuario actual.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Número máximo de jugadores obtenido con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/match-players-max")
    public ResponseEntity<Integer> getMaxPlayersPerMatch() {
        User currentUser = getCurrentUser();
        Integer maxPlayers = statisticService.getMaxPlayersPerMatch(currentUser);
        return ResponseEntity.ok(maxPlayers);
    }

    /**
     * Obtiene el número mínimo de jugadores en una partida jugada por el usuario
     * actual.
     *
     * Este endpoint devuelve el número mínimo de jugadores en las partidas jugadas
     * por el usuario actual.
     *
     * @return Número mínimo de jugadores en una partida.
     */
    @Operation(summary = "Obtener número mínimo de jugadores por partida del usuario", description = "Devuelve el número mínimo de jugadores en las partidas jugadas por el usuario actual.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Número mínimo de jugadores obtenido con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/match-players-min")
    public ResponseEntity<Integer> getMinPlayersPerMatch() {
        User currentUser = getCurrentUser();
        Integer minPlayers = statisticService.getMinPlayersPerMatch(currentUser);
        return ResponseEntity.ok(minPlayers);
    }

    /**
     * Obtiene el número máximo de jugadores en una partida a nivel global.
     *
     * Este endpoint devuelve el número máximo de jugadores registrados en todas las
     * partidas del sistema.
     *
     * @return Número máximo de jugadores en una partida global.
     */
    @Operation(summary = "Obtener número máximo de jugadores por partida a nivel global", description = "Devuelve el número máximo de jugadores registrados en todas las partidas del sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Número máximo de jugadores global obtenido con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/global-match-players-max")
    public ResponseEntity<Integer> getMaxGlobalPlayersPerMatch() {
        Integer maxPlayers = statisticService.getMaxGlobalPlayersPerMatch();
        return ResponseEntity.ok(maxPlayers);
    }

    /**
     * Obtiene el número mínimo de jugadores en una partida a nivel global.
     *
     * Este endpoint devuelve el número mínimo de jugadores registrados en todas las
     * partidas del sistema.
     *
     * @return Número mínimo de jugadores en una partida global.
     */
    @Operation(summary = "Obtener número mínimo de jugadores por partida a nivel global", description = "Devuelve el número mínimo de jugadores registrados en todas las partidas del sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Número mínimo de jugadores global obtenido con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/global-match-players-min")
    public ResponseEntity<Integer> getMinGlobalPlayersPerMatch() {
        Integer minPlayers = statisticService.getMinGlobalPlayersPerMatch();
        return ResponseEntity.ok(minPlayers);
    }

    /**
     * Obtiene el número promedio de jugadores por partida a nivel global.
     *
     * Este endpoint devuelve el promedio de jugadores registrados en todas las
     * partidas del sistema.
     *
     * @return Número promedio de jugadores por partida global.
     */
    @Operation(summary = "Obtener número promedio de jugadores por partida a nivel global", description = "Devuelve el promedio de jugadores registrados en todas las partidas del sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Número promedio de jugadores global obtenido con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/global-match-players-average")
    public ResponseEntity<Double> getGlobalAveragePlayersPerMatch() {
        Double averagePlayers = statisticService.getGlobalAveragePlayersPerMatch();
        return ResponseEntity.ok(averagePlayers);
    }

    /**
     * Obtiene el puntaje promedio de las partidas del usuario actual.
     *
     * Este endpoint devuelve el puntaje promedio obtenido por el usuario en las
     * partidas en las que ha participado.
     *
     * @return Puntaje promedio obtenido por el usuario.
     */
    @Operation(summary = "Obtener puntaje promedio del usuario", description = "Devuelve el puntaje promedio obtenido por el usuario en las partidas en las que ha participado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Puntaje promedio obtenido con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/average-points")
    public ResponseEntity<Double> getAveragePoints() {
        User currentUser = getCurrentUser();
        Double averagePoints = statisticService.getAveragePoints(currentUser);
        return ResponseEntity.ok(averagePoints);
    }

    /**
     * Obtiene el puntaje máximo obtenido por el usuario en las partidas.
     *
     * Este endpoint devuelve el puntaje máximo obtenido por el usuario en las
     * partidas en las que ha participado.
     *
     * @return Puntaje máximo obtenido por el usuario.
     */
    @Operation(summary = "Obtener puntaje máximo del usuario", description = "Devuelve el puntaje máximo obtenido por el usuario en las partidas en las que ha participado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Puntaje máximo obtenido con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/max-points")
    public ResponseEntity<Integer> getMaxPoints() {
        User currentUser = getCurrentUser();
        Integer maxPoints = statisticService.getMaxPoints(currentUser);
        return ResponseEntity.ok(maxPoints);
    }

    /**
     * Obtiene el puntaje mínimo obtenido por el usuario en las partidas.
     *
     * Este endpoint devuelve el puntaje mínimo obtenido por el usuario en las
     * partidas en las que ha participado.
     *
     * @return Puntaje mínimo obtenido por el usuario.
     */
    @Operation(summary = "Obtener puntaje mínimo del usuario", description = "Devuelve el puntaje mínimo obtenido por el usuario en las partidas en las que ha participado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Puntaje mínimo obtenido con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/min-points")
    public ResponseEntity<Integer> getMinPoints() {
        User currentUser = getCurrentUser();
        Integer minPoints = statisticService.getMinPoints(currentUser);
        return ResponseEntity.ok(minPoints);
    }

    /**
     * Obtiene el porcentaje de victorias del usuario en las partidas.
     *
     * Este endpoint devuelve el porcentaje de victorias del usuario en todas las
     * partidas que ha jugado.
     *
     * @return Porcentaje de victorias del usuario.
     */
    @Operation(summary = "Obtener porcentaje de victorias del usuario", description = "Devuelve el porcentaje de victorias del usuario en todas las partidas que ha jugado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Porcentaje de victorias obtenido con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/win-percentage")
    public ResponseEntity<Double> getWinPercentage() {
        User currentUser = getCurrentUser();
        Double winPercentage = statisticService.getWinPercentage(currentUser);
        return ResponseEntity.ok(winPercentage);
    }

    /**
     * Obtiene la posición promedio de ranking del usuario en las partidas.
     *
     * Este endpoint devuelve la posición promedio en el ranking del usuario en las
     * partidas que ha jugado.
     *
     * @return Posición promedio en el ranking del usuario.
     */
    @Operation(summary = "Obtener posición promedio en el ranking del usuario", description = "Devuelve la posición promedio en el ranking del usuario en las partidas que ha jugado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posición promedio en el ranking obtenida con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/average-ranking-position")
    public ResponseEntity<Double> getAverageRankingPosition() {
        User currentUser = getCurrentUser();
        Double averageRankingPosition = statisticService.getAverageRankingPosition(currentUser);
        return ResponseEntity.ok(averageRankingPosition);
    }

    /**
     * Obtiene el promedio de turnos por partida del usuario.
     *
     * Este endpoint devuelve el promedio de turnos jugados por el usuario en cada
     * partida.
     *
     * @return Promedio de turnos por partida del usuario.
     */
    @Operation(summary = "Obtener promedio de turnos por partida del usuario", description = "Devuelve el promedio de turnos jugados por el usuario en cada partida.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promedio de turnos por partida obtenido con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/average-turns-per-match")
    public ResponseEntity<Double> getAverageTurnsPerMatch() {
        User currentUser = getCurrentUser();
        Double averageTurnsPerMatch = statisticService.getAverageTurnsPerMatch(currentUser);
        return ResponseEntity.ok(averageTurnsPerMatch);
    }

    /**
     * Obtiene las cartas más frecuentes en las manos finales del usuario.
     *
     * Este endpoint devuelve una lista con los nombres de las cartas más frecuentes
     * en las manos finales del usuario.
     *
     * @return Lista de nombres de las cartas más frecuentes en las manos finales
     *         del usuario.
     */
    @Operation(summary = "Obtener cartas más frecuentes en las manos finales del usuario", description = "Devuelve una lista con los nombres de las cartas más frecuentes en las manos finales del usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cartas más frecuentes obtenidas con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/most-frequent-cards")
    public ResponseEntity<List<String>> getMostFrequentCards() {
        User currentUser = getCurrentUser();
        List<String> mostFrequentCards = statisticService.getMostFrequentCardsInFinalHands(currentUser);
        return ResponseEntity.ok(mostFrequentCards);
    }

    /**
     * Obtiene el ranking global del juego.
     *
     * Este endpoint devuelve un diccionario que relaciona cada usuario tanto con
     * sus partidas ganadas
     * como con sus puntos conseguidos.
     *
     * @return diccionario en el que las claves son los nombres de los usuarios y
     *         los valores listas con dos elementos cada una,
     *         el primero es el número de partidas ganadas po ese usuarios y el
     *         segundo el numero total de puntos que ha conseguido
     *         el usuario en todas sus partidas.
     */
    @Operation(summary = "Obtener cartas más frecuentes en las manos finales del usuario", description = "Devuelve una lista con los nombres de las cartas más frecuentes en las manos finales del usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ranking obtenido con éxito"),
            @ApiResponse(responseCode = "400", description = "Parámetro 'sortBy' invalido. Solo se permite 'WINS' y 'POINTS'"),
            @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })

    @GetMapping("/global-ranking")
    public ResponseEntity<Map<String, List<Integer>>> getRanking(
            @RequestParam(defaultValue = "WINS") String sortBy // Parametro opcional con valor por defecto "WINS"
    ) {
        Map<String, List<Integer>> ranking = statisticService.getRanking(sortBy);
        return ResponseEntity.ok(ranking);
    }

}
