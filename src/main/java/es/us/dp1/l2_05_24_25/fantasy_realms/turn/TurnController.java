package es.us.dp1.l2_05_24_25.fantasy_realms.turn;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.us.dp1.l2_05_24_25.fantasy_realms.match.DecisionRequest;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.MatchService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * Controlador REST que gestiona las operaciones relacionadas con el turno
 * de una partida: cancelación de turno, turno especial, etc.
 * 
 * Seguridad: Todos los endpoints requieren autenticación Bearer.
 */
@RestController
@RequestMapping("api/v1/turns")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Turns", description = "Controlador REST que gestiona las operaciones relacionadas con el turno de la partida." +
        " Seguridad: Todos los endpoints requieren autenticación Bearer y pueden ser utilizados por cualquier usario que tenga un jugador asociado en partida.")
public class TurnController {

    private TurnService turnService;
    private UserService userService;
    private MatchService matchService;

    @Autowired
    public TurnController(TurnService turnService, UserService userService, MatchService matchService) {
        this.turnService = turnService;
        this.userService = userService;
        this.matchService = matchService;
    }

    /**
     * Permite cancelar el turno actual de la partida, revirtiendo las acciones
     * realizadas.
     *
     * @param matchId ID de la partida cuyo turno se desea cancelar.
     * @return Respuesta vacía con HTTP 200 si la cancelación se realizó con éxito.
     */
    @Operation(summary = "Cancelar turno actual",
               description = "Anula las acciones realizadas en el turno en curso de la partida, si el usuario es quien está jugando el turno.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Turno cancelado con éxito"),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Partida no encontrada", content = @Content),
        @ApiResponse(responseCode = "409", description = """
            La solicitud no puede ser procesada debido a uno o más de los siguientes motivos:
            
            Motivos:
            1. **El usuario no posee el turno actual de la partida**
               - Excepción generada: `TurnStatesException`
               - Descripción: El usuario registrado no posee el turno actual de la partida.
    
            2. **La partida no está en progreso.**
               - Excepción generada: `MatchStatesException`
               - Descripción: La partida aún no ha iniciado o ya ha finalizado.
    
            """, content = @Content),
        @ApiResponse(responseCode = "500", description = "Error al intentar cancelar el turno", content = @Content)
    })
    @PostMapping("/{matchId}/cancelTurn")
    public ResponseEntity<Void> cancelTurn(@PathVariable("matchId") Integer matchId) {

        User currentUser = userService.findCurrentUser();

        Match match = matchService.findMatchById(matchId);

        turnService.cancelTurn(currentUser, match);

        return ResponseEntity.ok().build();
    }

    /**
     * Ejecuta el turno especial (fase de scoring) para el usuario actual, aplicando
     * los modificadores dinámicos decididos por el jugador (si tiene).
     *
     * @param matchId ID de la partida.
     * @param request Objeto {@link DecisionRequest} que contiene las decisiones
     *                dinámicas del jugador.
     * @return Respuesta vacía con HTTP 200. Si es el último turno, finaliza la partida.
     */
    @Operation(summary = "Turno especial (fase de scoring)",
               description = "Aplica las decisiones dinámicas (modificadores) del jugador en el último tramo de la partida (si tiene).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Turno especial procesado con éxito"),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Partida no encontrada", content = @Content),
        @ApiResponse(responseCode = "409", description = """
            La solicitud no puede ser procesada debido a uno o más de los siguientes motivos:
            
            Motivos:
            1. **El usuario no posee el turno actual de la partida**
               - Excepción generada: `TurnStatesException`
               - Descripción: El usuario registrado no posee el turno actual de la partida.
    
            2. **La partida no está en fase de turnos especiales (scoring).**
               - Excepción generada: `MatchStatesException`
               - Descripción: La partida aún no está en fase de turnos especiales.
    
            """, content = @Content),
        @ApiResponse(responseCode = "500", description = "Error al intentar aplicar el turno especial", content = @Content)
    })
    @PostMapping("/{matchId}/specialTurn")
    public ResponseEntity<Void> specialTurn(@PathVariable("matchId") Integer matchId,
            @RequestBody DecisionRequest request) {

        User currentUser = userService.findCurrentUser();

        Match match = matchService.findMatchById(matchId);

        boolean isLastTurn = turnService.specialTurn(currentUser, match, request.getDecisions());
        
        // Si es el último turno posible, finalizamos la partida

        if (isLastTurn) {
            matchService.endMatch(match);
        }

        return ResponseEntity.ok().build();

    }

    @GetMapping("/{matchId}/timeLeft")
    public ResponseEntity<Integer> getTimeLeft(@PathVariable("matchId") Integer matchId) {

        Match match = matchService.findMatchById(matchId);
        if (match == null) {

            return ResponseEntity.notFound().build();
        }


        Turn currentTurn = match.getCurrentTurn();
        if (currentTurn == null) {

            return ResponseEntity.ok(0);
        }


        Instant turnStart = currentTurn.getTurnStartTime();
        if (turnStart == null) {

            return ResponseEntity.ok(0);
        }

        long elapsedSeconds = Duration.between(turnStart, Instant.now()).toSeconds();

        long timeLimit = 60; 


        long timeLeft = timeLimit - elapsedSeconds;
        if (timeLeft < 0) {
            timeLeft = 0;
        }


        return ResponseEntity.ok((int) timeLeft);
    }

    
}
