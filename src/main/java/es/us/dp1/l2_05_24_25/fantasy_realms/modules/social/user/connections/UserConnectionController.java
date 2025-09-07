package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.connections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador para gestionar el "heartbeat" (conexión) de los usuarios.
 * 
 * El usuario envía una petición periódica para indicar que sigue conectado.
 */
@Tag(name = "User Connection", description = """
        Controlador para gestionar el 'heartbeat' o estado de conexión de los usuarios.
         Seguridad: Todos los endpoints requieren autenticación Bearer.
        """)
@RestController
@RequestMapping("/api/v1/heartbeat")
@SecurityRequirement(name = "bearerAuth") // Si tu seguridad requiere token Bearer
public class UserConnectionController {

    private final UserConnectionService userConnectionService;
    private final UserService userService;

    @Autowired
    public UserConnectionController(UserConnectionService userConnectionService, UserService userService) {
        this.userConnectionService = userConnectionService;
        this.userService = userService;
    }

    /**
     * Actualiza el "heartbeat" del usuario autenticado, indicando que se mantiene 
     * en línea. Se utiliza para gestionar la conexión en tiempo real.
     *
     * @return HTTP 200 (OK) si la actualización se realizó correctamente.
     */
    @Operation(summary = "Actualizar heartbeat",
               description = "Marca que el usuario autenticado sigue conectado, actualizando su última conexión.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Heartbeat actualizado correctamente."),
        @ApiResponse(responseCode = "401", description = "Usuario no autenticado o token inválido.")
    })
    @PostMapping
    public ResponseEntity<Void> updateHeartbeat() {
        User currentUser = userService.findCurrentUser();
        userConnectionService.updateConnection(currentUser);
        return ResponseEntity.ok().build();
    }
}
