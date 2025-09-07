package es.us.dp1.l2_05_24_25.fantasy_realms.user.auth;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.us.dp1.l2_05_24_25.fantasy_realms.configuration.jwt.JwtUtils;
import es.us.dp1.l2_05_24_25.fantasy_realms.configuration.services.UserDetailsImpl;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.auth.payload.request.LoginRequest;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.auth.payload.request.SignupRequest;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.auth.payload.response.JwtResponse;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.auth.payload.response.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.security.authentication.BadCredentialsException;

/**
 * Controlador REST que gestiona las operaciones relacionadas con la autenticación
 * y el registro de usuarios en el sistema, utilizando JWT para la autenticación.
 */
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "API de autenticación basada en JWT. Seguridad: Este controlador no requiere autenticación.")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final JwtUtils jwtUtils;
	private final AuthService authService;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtils jwtUtils,
			AuthService authService) {
		this.userService = userService;
		this.jwtUtils = jwtUtils;
		this.authenticationManager = authenticationManager;
		this.authService = authService;
	}

	/**
     * Autentica un usuario basado en sus credenciales.
     *
     * @param loginRequest Objeto que contiene el nombre de usuario y contraseña.
     * @return Token JWT y detalles del usuario autenticado.
     */
    @Operation(summary = "Iniciar sesión",
               description = "Autentica un usuario basado en sus credenciales y devuelve un token JWT.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario autenticado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Credenciales inválidas", content = @Content)
    })
	@PostMapping("/signin")
	public ResponseEntity authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		try{
			Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

			return ResponseEntity.ok().body(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
		}catch(BadCredentialsException exception){
			return ResponseEntity.badRequest().body("Bad Credentials!");
		}
	}

	/**
     * Valida la validez de un token JWT.
     *
     * @param token Token JWT a validar.
     * @return `true` si el token es válido, `false` en caso contrario.
     */
    @Operation(summary = "Validar token",
               description = "Valida la validez de un token JWT y devuelve `true` si es válido o `false` si no lo es.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token validado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Token inválido o ausente", content = @Content),
		@ApiResponse(responseCode = "401", description = "El token no es válido o ha expirado", content = @Content),
		@ApiResponse(responseCode = "403", description = "El token es válido pero no tiene permisos para acceder al recurso", content = @Content),
		@ApiResponse(responseCode = "500", description = "Error inesperado al validar el token", content = @Content)
    })
	@GetMapping("/validate")
	public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
		Boolean isValid = jwtUtils.validateJwtToken(token);
		return ResponseEntity.ok(isValid);
	}

	/**
     * Registra un nuevo usuario en el sistema.
     *
     * @param signUpRequest Datos del usuario a registrar.
     * @return Mensaje de confirmación del registro.
     */
    @Operation(summary = "Registrar usuario",
               description = "Registra un nuevo usuario en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error al registrar el usuario", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al registrar el usuario", content = @Content)
    })
	@PostMapping("/signup")	
	public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userService.existsUser(signUpRequest.getUsername()).equals(true)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		authService.createUser(signUpRequest);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

}
