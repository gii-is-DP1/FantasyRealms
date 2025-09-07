package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.match;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.MatchService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controlador para gestionar invitaciones a partidas (Game Invitations).
 * Permite enviar, aceptar y rechazar invitaciones, así como listar las invitaciones actuales.
 */
@RestController
@RequestMapping("/api/v1/game-invitation")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Game Invitation", description = """
        Controlador para las invitaciones a partida.
         Seguridad: Todos los endpoints requieren autenticación Bearer.
        """)
public class GameInvitationController {

    private final UserService userService;
    private final GameInvitationService gameInvitationService;
    private final MatchService matchService;

    @Autowired
    public GameInvitationController(UserService userService,
                                    GameInvitationService gameInvitationService,
                                    MatchService matchService) {
        this.userService = userService;
        this.gameInvitationService = gameInvitationService;
        this.matchService = matchService;
    }

    /**
     * Obtiene la lista de invitaciones de juego para el usuario autenticado.
     * 
     * @return Lista de DTOs con información de cada invitación.
     */
    @Operation(
        summary = "Listar invitaciones de juego",
        description = "Retorna todas las invitaciones de partidas dirigidas al usuario actual."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de invitaciones obtenida con éxito"),
        @ApiResponse(responseCode = "401", description = "No autorizado, token inválido o ausente")
    })
    @GetMapping()
    public List<GameInvitationDTO> getGameInvitations() {
        User currentUser = userService.findCurrentUser();
        return gameInvitationService.getGameInvitations(currentUser);
    }

    /**
     * Envía una invitación de juego a un usuario específico para una partida concreta.
     * 
     * @param receiverUsername Nombre de usuario que recibirá la invitación.
     * @param matchId ID de la partida a la que se le invita.
     */
    @Operation(
        summary = "Enviar invitación de juego",
        description = "Envía una invitación de juego al usuario indicado para la partida especificada."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Invitación enviada con éxito"),
        @ApiResponse(responseCode = "404", description = "Usuario o partida no encontrada"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PostMapping("/send/{receiver}/{match}")
    public void sendGameInvitation(
            @Parameter(description = "Nombre de usuario que recibirá la invitación",
                       example = "john_doe")
            @Valid @PathVariable("receiver") String receiverUsername,
            
            @Parameter(description = "ID de la partida a la que se invita",
                       example = "1001")
            @Valid @PathVariable("match") int matchId) {

        User currentUser = userService.findCurrentUser();
        User receiver = userService.findUser(receiverUsername);
        Match match = matchService.findMatchById(matchId);

        gameInvitationService.sendGameInvitation(currentUser, receiver, match);
    }

    /**
     * Acepta una invitación de juego previamente enviada.
     *
     * @param matchId ID de la partida cuya invitación se acepta.
     * @param senderUsername Nombre de usuario que envió la invitación.
     */
    @Operation(
        summary = "Aceptar invitación de juego",
        description = "Acepta la invitación de juego enviada por 'senderUsername' para la partida 'matchId'."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Invitación aceptada con éxito"),
        @ApiResponse(responseCode = "404", description = "Usuario, partida o invitación no encontrada"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PostMapping("/accept/{match}/{sender}")
    public void acceptGameInvitation(
            @Parameter(description = "ID de la partida", example = "1001")
            @Valid @PathVariable("match") int matchId,
            
            @Parameter(description = "Nombre de usuario que envió la invitación",
                       example = "john_doe")
            @Valid @PathVariable("sender") String senderUsername) {

        User currentUser = userService.findCurrentUser();
        User sender = userService.findUser(senderUsername);
        Match match = matchService.findMatchById(matchId);

        gameInvitationService.acceptGameInvitation(currentUser, sender, match);
    }

    /**
     * Rechaza una invitación de juego para la partida indicada.
     *
     * @param matchId ID de la partida cuya invitación se rechaza.
     */
    @Operation(
        summary = "Rechazar invitación de juego",
        description = "Rechaza la invitación de juego para la partida con ID 'matchId'."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Invitación rechazada con éxito"),
        @ApiResponse(responseCode = "404", description = "Partida o invitación no encontrada"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PostMapping("/reject/{match}")
    public void rejectGameInvitation(
            @Parameter(description = "ID de la partida", example = "1001")
            @Valid @PathVariable("match") int matchId) {

        User currentUser = userService.findCurrentUser();
        Match match = matchService.findMatchById(matchId);

        gameInvitationService.rejectGameInvitation(currentUser, match);
    }
}
