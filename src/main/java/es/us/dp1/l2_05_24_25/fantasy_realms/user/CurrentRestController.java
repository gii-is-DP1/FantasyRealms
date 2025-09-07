package es.us.dp1.l2_05_24_25.fantasy_realms.user;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.us.dp1.l2_05_24_25.fantasy_realms.configuration.services.UserDetailsImpl;
import es.us.dp1.l2_05_24_25.fantasy_realms.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador para gestionar las operaciones relacionadas con el usuario actual.
 * 
 * Seguridad:Todos los endpoints requieren autenticación Bearer.
 */
@RestController
@RequestMapping("/api/v1/currentuser")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Current User", description = "API para consultar información del usuario autenticado."
    + "Seguridad:Todos los endpoints requieren autenticación Bearer y pueden ser utilizados por cualquier usuario registrado en la aplicación.")
public class CurrentRestController {

    UserService userService;

    @Autowired
    public CurrentRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Obtiene la información del usuario autenticado.
     * 
     * @param principal Información del usuario autenticado proporcionada por Spring Security.
     * @return Información del usuario en formato {@link UserDTO}.
     */
    @Operation(summary = "Obtener usuario actual",
               description = "Devuelve la información del usuario actualmente autenticado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario obtenido con éxito"),
        @ApiResponse(responseCode = "401", description = "No autorizado: El usuario no está autenticado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error al intentar obtener la información del usuario registrado", content = @Content)
    })
    @GetMapping
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {

        // Verificamos si el usuario está autenticado
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UsernamePasswordAuthenticationToken principalToken = (UsernamePasswordAuthenticationToken) principal;
        UserDetailsImpl userDetails = (UserDetailsImpl) principalToken.getPrincipal(); // contiene la información del usuario

        // Crear el DTO del usuario autenticado
        User user = userService.findUser(userDetails.getId());
        UserDTO userDTO = new UserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    
}