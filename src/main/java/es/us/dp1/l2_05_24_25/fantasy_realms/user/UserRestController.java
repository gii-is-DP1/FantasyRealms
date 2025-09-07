/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.us.dp1.l2_05_24_25.fantasy_realms.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.us.dp1.l2_05_24_25.fantasy_realms.dto.UserDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.dto.UserUpdateProfileDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.AccessDeniedException;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.MatchService;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.auth.payload.response.MessageResponse;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.authorities.Authorities;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.authorities.AuthoritiesService;
import es.us.dp1.l2_05_24_25.fantasy_realms.util.RestPreconditions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;	

/**
 * Controlador REST que gestiona las operaciones relacionadas con los usuarios,
 * incluyendo la creación, actualización, eliminación y consulta de usuarios.
 * 
 * Seguridad: Todos los endpoints requieren autenticación Bearer.
 */
@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Users", description = """
        Controlador REST que permite la gestión de usuarios en el sistema, incluyendo la creación,
        actualización, eliminación y consultas de usuarios. Seguridad: Todos los endpoints requieren autenticación Bearer.
        """)
class UserRestController {

	private final UserService userService;
	private final AuthoritiesService authService;
    private final PlayerService playerService;
    private final MatchService matchService;

	@Autowired
	public UserRestController(UserService userService, AuthoritiesService authService, MatchService matchService, PlayerService playerService) {
		this.userService = userService;
		this.authService = authService;
        this.matchService = matchService;
        this.playerService = playerService;
	}

	/**
     * Devuelve una lista paginada de usuarios.
     * Permite filtrar, ordenar y paginar los usuarios registrados en el sistema.
     *
     * @param page Página solicitada (valor por defecto: 0).
     * @param size Tamaño de la página (valor por defecto: 5).
     * @param sortBy Campo para ordenar (valor por defecto: "username").
     * @param sortDirection Dirección de orden (ascendente o descendente, por defecto: "asc").
     * @return Página de usuarios en formato {@link UserDTO}.
     */
    @Operation(summary = "Listar usuarios",
    description = "Devuelve una lista paginada de usuarios, permitiendo ordenar y filtrar por diferentes criterios. Debes ser administrador para acceder a este endpoint.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida con éxito"),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
    })
	@GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "username") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // El currentUser debe ser admin

		User currentUser = userService.findCurrentUser();

        if(!(currentUser.hasAuthority("ADMIN"))) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

        Page<User> usersPage = userService.findAll(pageable);

        // Mapear a UserDTO para evitar enviar entidades directamente
        Page<UserDTO> userDTOPage = usersPage.map(user -> new UserDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getAuthority().getAuthority(),
            user.getAvatar()
        ));

        return ResponseEntity.ok(userDTOPage);
    }
	
	/**
     * Devuelve la lista de todas las autoridades del sistema.
     * 
     * @return Lista de objetos {@link Authorities}.
     */
    @Operation(summary = "Listar autoridades",
    description = "Devuelve una lista de todas las autoridades disponibles en el sistema. Debes ser administrador para acceder a este endpoint.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de autoridades obtenida con éxito"),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acceso denegado: Se necesita tener permisos de administrador para acceder a este endpoint", content = @Content)
    })
	@GetMapping("authorities")
	public ResponseEntity<List<Authorities>> findAllAuths() {

        // El currentUser debe ser admin

		User currentUser = userService.findCurrentUser();

        if(!(currentUser.hasAuthority("ADMIN"))) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		List<Authorities> res = (List<Authorities>) authService.findAll();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
     * Devuelve los detalles de un usuario por su ID.
     * 
     * @param id ID del usuario.
     * @return Objeto {@link UserDTO} con los detalles del usuario.
     */
    @Operation(summary = "Buscar usuario por ID",
               description = "Devuelve los detalles de un usuario específico por su ID. Debes ser administrador o el propio usuario buscado para acceder a este endpoint.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado con éxito"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acceso denegado: Se necesita tener permisos de administrador o ser el propio usuario para acceder a este endpoint", content = @Content)
    })
	@GetMapping(value = "{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable("id") Integer id) {

        // El currentUser debe ser admin o el propio usuario

		User currentUser = userService.findCurrentUser();

		if(!(currentUser.hasAuthority("ADMIN") || userService.findUser(id).getId().equals(currentUser.getId()))) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		User user = userService.findUser(id);

		UserDTO userDTO = new UserDTO(user);
		
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}

	/**
     * Crea un nuevo usuario en el sistema.
     * 
     * @param user Datos del usuario a crear.
     * @return Usuario creado.
     */
    @Operation(summary = "Crear usuario",
               description = "Crea un nuevo usuario en el sistema. Debes ser administrador para acceder a este endpoint.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado con éxito"),
        @ApiResponse(responseCode = "400", description = "Error al validar los datos para la creación del usuario", content = @Content),
		@ApiResponse(responseCode = "409", description = "El usuario no puede ser creado porque los datos proporcionados generan un conflicto", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acceso denegado: Se necesita tener permisos de administrador para acceder a este endpoint", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error al intentar crear el usuario", content = @Content)
    })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<User> create(@RequestBody @Valid UserUpdateProfileDTO user) {

        // El currentUser debe ser admin

		User currentUser = userService.findCurrentUser();

        if(!(currentUser.hasAuthority("ADMIN"))) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		User savedUser = userService.saveUser(user);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

	/**
     * Actualiza los datos de un usuario existente.
     * 
     * @param id ID del usuario a actualizar.
     * @param userUpdateProfileDTO Datos actualizados.
     * @return Datos del usuario actualizado.
     */
    @Operation(summary = "Actualizar usuario",
               description = "Actualiza los datos de un usuario existente en el sistema. Debes ser administrador o el propio usuario a actualizar para acceder a este endpoint.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado con éxito"),
		@ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
		@ApiResponse(responseCode = "403", description = "Acceso denegado: El usuario no tiene permisos para realizar esta acción", content = @Content),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
		@ApiResponse(responseCode = "409", description = "El usuario no puede ser creado porque los datos proporcionados generan un conflicto", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error al intentar actualizar el usuario", content = @Content)
    })
	@PutMapping(value = "{userId}")
	public ResponseEntity<UserDTO> update(@PathVariable("userId") Integer id, @RequestBody @Valid UserUpdateProfileDTO userUpdateProfileDTO) {

		RestPreconditions.checkNotNull(userService.findUser(id), "User", "ID", id);

		// El currentUser debe ser admin o el propio usuario

		User currentUser = userService.findCurrentUser();

		if(!(currentUser.hasAuthority("ADMIN") || userService.findUser(id).getId().equals(currentUser.getId()))) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<>(new UserDTO(this.userService.updateUser(userUpdateProfileDTO, id)), HttpStatus.OK);
	}

	/**
     * Elimina un usuario del sistema.
     * 
     * @param id ID del usuario a eliminar.
     * @return Mensaje indicando que el usuario fue eliminado.
     */
    @Operation(summary = "Eliminar usuario",
                description = "Elimina un usuario del sistema por su ID. Debes ser administrador o el propio usuario a eliminar para acceder a este endpoint.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado con éxito"),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acceso denegado: El usuario no tiene permisos para realizar esta acción", content = @Content),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error al intentar eliminar el usuario", content = @Content)
    })
	@DeleteMapping(value = "{userId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<MessageResponse> delete(@PathVariable("userId") int id) {
		RestPreconditions.checkNotNull(userService.findUser(id), "User", "ID", id);

		// El currentUser debe ser admin o el propio usuario

		User currentUser = userService.findCurrentUser();

		if(!(currentUser.hasAuthority("ADMIN") || userService.findUser(id).getId().equals(currentUser.getId()))) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

        // Obtenemos las partidas creadas por el usuario (deben ser borradas)

        List<Match> matchesToDelete = playerService.getMatchesCreatedByUser(id);

        // Borramos las partidas

        matchesToDelete.stream().forEach(match -> matchService.deleteMatch(match.getId(), id));

		if (userService.findCurrentUser().getId() != id) {
			userService.deleteUser(id);
			return new ResponseEntity<>(new MessageResponse("User deleted!"), HttpStatus.OK);
		} else
			throw new AccessDeniedException("You can't delete yourself!");
	}

}
