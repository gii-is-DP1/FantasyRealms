package es.us.dp1.l2_05_24_25.fantasy_realms.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.us.dp1.l2_05_24_25.fantasy_realms.dto.PlayerDTO;
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
 * Controlador REST para gestionar las acciones que realizan los jugadores
 * dentro de una partida, como robar cartas o descartar.
 * 
 * Seguridad:Todos los endpoints requieren autenticación Bearer.
 */
@RestController
@RequestMapping("/api/v1/players")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Players", description = "Controlador REST para gestionar las acciones que realizan los jugadores" +
        " dentro de una partida, como robar cartas o descartar."
        + " Seguridad:Todos los endpoints requieren autenticación Bearer y pueden ser utilizados por cualquier usuario que tenga un jugador asociado en partida.")
public class PlayerController {

    private PlayerService playerService;

    private MatchService matchService;

    private UserService userService;

    @Autowired
    public PlayerController(PlayerService playerService, UserService userService, MatchService matchService) {
        this.playerService = playerService;
        this.userService = userService;
        this.matchService = matchService;
    }

    /**
     * Permite al jugador actual robar una carta del mazo principal de la partida.
     * 
     * @param matchId ID de la partida en la que se va a robar una carta.
     * @return Un {@link PlayerDTO} con el estado del jugador tras robar.
     */
    @Operation(summary = "Robar carta del mazo principal",
               description = "El jugador actual roba la carta superior del mazo en la partida dada.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carta robada con éxito, se devuelve el jugador actualizado"),
        @ApiResponse(responseCode = "401", description = "No autorizado (token inválido o ausente)", content = @Content),
        @ApiResponse(responseCode = "409", description = """
            La solicitud no puede ser procesada debido a uno o más de los siguientes motivos:
            
            Motivos:
            1. **No hay jugador en la partida asociado al usuario registrado.**
               - Excepción generada: `PlayerStatesException`
               - Descripción: El usuario registrado no está vinculado como jugador a esta partida.
    
            2. **La partida no está en progreso.**
               - Excepción generada: `MatchStatesException`
               - Descripción: La partida aún no ha iniciado o ya ha finalizado.
    
            3. **La partida está en fase de turnos especiales.**
               - Excepción generada: `MatchStatesException`
               - Descripción: No es posible realizar la acción porque la partida está en una fase de turnos especiales.
    
            4. **El usuario registrado no tiene el turno actual de la partida.**
               - Excepción generada: `TurnStatesException`
               - Descripción: El usuario intentó realizar una acción fuera de su turno.
    
            5. **El usuario ha realizado acciones previas en su turno.**
               - Excepción generada: `TurnStatesException`
               - Descripción: El usuario ya completó las acciones permitidas para su turno actual.
            """, content = @Content),
        @ApiResponse(responseCode = "404", description = "Partida no encontrada o jugador inexistente", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error al tratar de robar del mazo", content = @Content)
    })
    @PostMapping("/drawCard/deck/{matchId}")
    public ResponseEntity<PlayerDTO> drawCardFromDeck(@PathVariable("matchId") Integer matchId) {

        Match match = matchService.findMatchById(matchId);

        User current = userService.findCurrentUser();
        if (current == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Player player = playerService.drawCardFromDeck(current, match);

        if (player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new PlayerDTO(player), HttpStatus.OK);
    }

    /**
     * Permite al jugador actual robar una carta específica de la zona de descarte.
     * 
     * @param matchId ID de la partida.
     * @param cardId  ID de la carta a robar desde el descarte.
     * @return Un {@link PlayerDTO} con el estado del jugador tras robar.
     */
    @Operation(summary = "Robar carta de la pila de descartes",
               description = "El jugador actual roba la carta con ID 'cardId' de la zona de descarte de la partida.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carta robada con éxito, se devuelve el jugador actualizado"),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Partida o carta no encontrada", content = @Content),
        @ApiResponse(responseCode = "409", description = """
            La solicitud no puede ser procesada debido a uno o más de los siguientes motivos:
            
            Motivos:
            1. **No hay jugador en la partida asociado al usuario registrado.**
               - Excepción generada: `PlayerStatesException`
               - Descripción: El usuario registrado no está vinculado como jugador a esta partida.
    
            2. **La partida no está en progreso.**
               - Excepción generada: `MatchStatesException`
               - Descripción: La partida aún no ha iniciado o ya ha finalizado.
    
            3. **La partida está en fase de turnos especiales.**
               - Excepción generada: `MatchStatesException`
               - Descripción: No es posible realizar la acción porque la partida está en una fase de turnos especiales.
    
            4. **El usuario registrado no tiene el turno actual de la partida.**
               - Excepción generada: `TurnStatesException`
               - Descripción: El usuario intentó realizar una acción fuera de su turno.
    
            5. **El usuario ha realizado acciones previas en su turno.**
               - Excepción generada: `TurnStatesException`
               - Descripción: El usuario ya completó las acciones permitidas para su turno actual.

            6. **Se está intentando robar de la zona de descarte en el primer turno.**
               - Excepción generada: `TurnStatesException`
               - Descripción: No se permite robar del descarte en el primer turno.
            """, content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al tratar de robar de la zona de descarte", content = @Content)
    })
    @PostMapping("/drawCard/discardPile/{matchId}/{cardId}")
    public ResponseEntity<PlayerDTO> drawCardFromDiscard(@PathVariable("matchId") Integer matchId,
        @PathVariable("cardId") Integer cardId) {

        Match match = matchService.findMatchById(matchId);

        User current = userService.findCurrentUser();

        Player player = playerService.drawCardFromDiscard(current, match, cardId);

        return new ResponseEntity<>(new PlayerDTO(player), HttpStatus.OK);

    }

    /**
     * Permite al jugador actual descartar una carta de su mano.
     * 
     * @param matchId ID de la partida.
     * @param cardId  ID de la carta que se desea descartar.
     * @return Un {@link PlayerDTO} representando el estado del jugador tras descartar.
     */
    @Operation(summary = "Descartar una carta",
               description = "El jugador actual descarta la carta con ID 'cardId' en la partida especificada.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carta descartada con éxito, se devuelve el jugador actualizado"),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Partida o carta no encontrada", content = @Content),
        @ApiResponse(responseCode = "409", description = """
            La solicitud no puede ser procesada debido a uno o más de los siguientes motivos:
            
            Motivos:
            1. **No hay jugador en la partida asociado al usuario registrado.**
               - Excepción generada: `PlayerStatesException`
               - Descripción: El usuario registrado no está vinculado como jugador a esta partida.
    
            2. **La partida no está en progreso.**
               - Excepción generada: `MatchStatesException`
               - Descripción: La partida aún no ha iniciado o ya ha finalizado.
    
            3. **La partida está en fase de turnos especiales.**
               - Excepción generada: `MatchStatesException`
               - Descripción: No es posible realizar la acción porque la partida está en una fase de turnos especiales.
    
            4. **El usuario registrado no tiene el turno actual de la partida.**
               - Excepción generada: `TurnStatesException`
               - Descripción: El usuario intentó realizar una acción fuera de su turno.
    
            5. **El usuario no ha realizado acciones previas en su turno.**
               - Excepción generada: `TurnStatesException`
               - Descripción: El usuario no ha completado las acciones previas de su turno (robo).

            6. **Ya se ha descartado una carta en el turno actual.**
               - Excepción generada: `TurnStatesException`
               - Descripción: Solo se permite descartar una vez por turno.

            7. **La carta que se quiere descartar no pertenece a la mano del jugador**
               - Excepción generada: `CardStatesException`
               - Descripción: El jugador solo puede descartar una carta que esté disponible en su mano.
            """, content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al intentar descartar una carta", content = @Content)
    })
    @PostMapping("/discard/{matchId}/{cardId}")
    public ResponseEntity<PlayerDTO> discardCard(@PathVariable("matchId") Integer matchId,
        @PathVariable("cardId") Integer cardId) {

        Match match = matchService.findMatchById(matchId);

        User current = userService.findCurrentUser();

        Player player = playerService.discardCard(current, match, cardId);

        return new ResponseEntity<>(new PlayerDTO(player), HttpStatus.OK);
    }

}
