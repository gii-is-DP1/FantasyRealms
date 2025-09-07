package es.us.dp1.l2_05_24_25.fantasy_realms.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.us.dp1.l2_05_24_25.fantasy_realms.dto.MatchDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.dto.PlayerDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.dto.PrivateMatchDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerService;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerType;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST para la gestión de partidas (Match).
 * Ofrece endpoints para crear, consultar, unirse y manipular partidas.
 * 
 * Seguridad:Todos los endpoints requieren autenticación Bearer.
 */
@RestController
@RequestMapping("api/v1/matches")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Matches", description = "Controlador REST para la gestión de partidas." +
        " Ofrece endpoints para crear, consultar, unirse y manipular partidas."
        + " Seguridad:Todos los endpoints requieren autenticación Bearer y pueden ser utilizados por cualquier usuario registrado en la aplicación.")
public class MatchController {

    private MatchService matchService;
    private UserService userService;
    private PlayerService  playerService;

    @Autowired
    private MatchController(MatchService matchService, UserService userService, PlayerService playerService) {  

        this.matchService = matchService;
        this.userService = userService;
        this.playerService = playerService;

    }
    
    /**
    * Lista partidas disponibles con filtro por estado.
    *
    * Este endpoint permite a los administradores listar partidas en diferentes estados 
    * (`lobby`, `inProgress`, `finished`), mientras que los usuarios no administradores 
    * solo pueden listar partidas en estado `lobby`.
    *
    * @param page Número de página (valor por defecto: 0).
    * @param size Tamaño de la página (valor por defecto: 10).
    * @param status Estado de las partidas a listar (`lobby`, `inProgress`, `finished`). 
    *               Por defecto, se filtra por `lobby`. Usuarios no administradores no pueden listar otros estados.
    * @return Página de partidas envueltas en un {@link ResponseEntity}.
    */
    @Operation(summary = "Listar partidas filtradas por estado", 
    description = "Devuelve partidas filtradas por estado (`lobby`, `inProgress`, `finished`) de forma paginada. " +
                "Los usuarios no administradores solo pueden listar partidas en estado `lobby`. " +
                "Los administradores pueden filtrar por cualquier estado.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Partidas devueltas con éxito"),
    @ApiResponse(responseCode = "401", description = "No autorizado (token inválido o ausente)", 
        content = @Content),
    @ApiResponse(responseCode = "403", description = "Prohibido: el usuario no tiene permisos para listar partidas en el estado solicitado", 
        content = @Content),
    @ApiResponse(responseCode = "400", description = "Parámetro 'status' inválido. Solo se permite `lobby`, `inProgress` o `finished`", 
        content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<MatchDTO>> findMatchesByStatus(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false, defaultValue = "lobby") String status) {

            User currentUser = userService.findCurrentUser();

            boolean isAdmin = currentUser.hasAuthority("ADMIN");

            if(!isAdmin && !status.equalsIgnoreCase("lobby")) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            Page<MatchDTO> matches = matchService.findMatchesByStatus(page, size, status);
            return new ResponseEntity<>(matches, HttpStatus.OK);

    }

    /**
    * Obtiene una lista de partidas asociadas al usuario actual.
    *
    * Este endpoint devuelve las partidas en las que el usuario actual ha participado.
    * Si el parámetro `onlyCreator` es `true`, se filtrarán únicamente las partidas creadas por el usuario.
    *
    * @param page Número de página para la paginación (por defecto: 0).
    * @param size Tamaño de la página para la paginación (por defecto: 10).
    * @param onlyCreator Filtro opcional para devolver solo partidas creadas por el usuario (por defecto: false).
    * @return Página de partidas en formato {@link MatchDTO}.
    */
    @Operation(
        summary = "Obtener partidas del usuario actual",
        description = "Devuelve una lista de partidas en las que el usuario actual ha participado, con opción de filtrar las creadas por él."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Partidas obtenidas con éxito"),
        @ApiResponse(responseCode = "401", description = "No autorizado: se necesita autenticación bearer para acceder a este endpoint", content = @Content)
    })
    @GetMapping("/myMatches")
    public ResponseEntity<Page<MatchDTO>> findMatchesForCurrentUser(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "false") boolean onlyCreator
    ) {
        User currentUser = userService.findCurrentUser();
        int userId = currentUser.getId();

        Page<MatchDTO> matches = matchService.findMatchesForUser(userId, page, size, onlyCreator);
        return ResponseEntity.ok(matches);
    }

    /**
     * Busca una partida por su ID.
     *
     * @param id Identificador de la partida.
     * @return La partida encontrada, en formato {@link MatchDTO}.
     */
    @Operation(summary = "Buscar partida por ID",
               description = "Devuelve la información de la partida con el ID especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Partida encontrada con éxito"),
        @ApiResponse(responseCode = "404", description = "Partida no encontrada", 
                     content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado", 
                     content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> findMatchById(@PathVariable Integer id) {

        return new ResponseEntity<>(new MatchDTO(matchService.findMatchById(id)), HttpStatus.OK);

    }

    /**
     * Obtiene el jugador creador de la partida.
     *
     * @param id Identificador de la partida.
     * @return El jugador creador en un {@link PlayerDTO}.
     */
    @Operation(summary = "Obtener creador de la partida",
               description = "Devuelve el Player que creó la partida con ID especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Creador devuelto con éxito"),
        @ApiResponse(responseCode = "404", description = "Partida no encontrada", 
                     content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado", 
                     content = @Content)
    })
    @GetMapping("/{id}/creator")
    public ResponseEntity<PlayerDTO> findCreatorOfMatch(@PathVariable Integer id) {

        Match match = matchService.findMatchById(id);

        return new ResponseEntity<>(new PlayerDTO(match.getCreador()), HttpStatus.OK);

    }

    /**
     * Crea una nueva partida con el usuario actual como creador.
     *
     * @param matchName Nombre de la partida a crear.
     * @return La partida recién creada (DTO) con estado HTTP 201.
     */
    @Operation(summary = "Crear nueva partida",
               description = "Crea una partida con el usuario actual como CREADOR.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Partida creada con éxito"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la petición", 
                     content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado", 
                     content = @Content),
        @ApiResponse(responseCode = "403", description = "El usuario tiene un jugador asociado que está en partida", 
                     content = @Content),
        @ApiResponse(responseCode = "404", description = "Mazo universal no encontrado", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Error al crear la partida", 
                     content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<MatchDTO> createMatch(@RequestParam("matchName") String matchName) {

        User currentUser = userService.findCurrentUser();

        Match match = matchService.createMatch(currentUser, matchName);

        return new ResponseEntity<>(new MatchDTO(match), HttpStatus.CREATED);

    }

    /**
     * Permite a un usuario unirse a la partida con el ID especificado.
     *
     * @param id ID de la partida a la que se quiere unir.
     * @return La partida actualizada tras unirse el usuario.
     */
    @Operation(summary = "Unirse a una partida",
               description = "El usuario actual se une como participante a la partida dada.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Unión a la partida exitosa"),
        @ApiResponse(responseCode = "404", description = "Partida no encontrada", 
                     content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado", 
                     content = @Content),
        @ApiResponse(responseCode = "403", description = "El usuario tiene un jugador asociado que está en partida", 
                     content = @Content),
        @ApiResponse(responseCode = "409", description = """
            La solicitud no puede ser procesada debido a un conflicto en el estado de la partida:
    
            Motivos:
            1. **La partida está en progreso o ha finalizado.**
               - Excepción generada: `MatchStatesException`
               - Descripción: No se pueden realizar acciones en partidas que ya están en progreso o han concluido.
    
            2. **La partida ha alcanzado el límite de jugadores.**
               - Excepción generada: `MatchStatesException`
               - Descripción: No se pueden añadir más jugadores a la partida porque se ha alcanzado el número máximo permitido.
            """, 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Error al intentar unirse a la partida", 
                     content = @Content)
    })
    @PostMapping("/{id}/join")
    public ResponseEntity<MatchDTO> joinMatch(@PathVariable Integer id) {

        User currentUser = userService.findCurrentUser();

        Match match = matchService.joinMatch(id, currentUser);

        return new ResponseEntity<>(new MatchDTO(match), HttpStatus.OK);

    }

    /**
     * Inicia una partida, estableciendo su fecha de inicio y repartiendo cartas.
     * Además, solo el creador de la partida tiene permisos para iniciarla.</p>
     *
     * @param id ID de la partida.
     * @return La partida actualizada (DTO) con estado HTTP 200.
     */
    @Operation(summary = "Iniciar la partida",
               description = "Solo el usuario creador de la partida puede iniciarla.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Partida iniciada con éxito"),
        @ApiResponse(responseCode = "403", description = "El usuario actual no es creador", 
                     content = @Content),
        @ApiResponse(responseCode = "404", description = "Partida no encontrada", 
                     content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado", 
                     content = @Content),
        @ApiResponse(responseCode = "409", description = """
            La solicitud no puede ser procesada debido a un conflicto en el estado de la partida:
    
            Motivos:
            1. **La partida está en progreso o ha finalizado.**
               - Excepción generada: `MatchStatesException`
               - Descripción: No se pueden realizar acciones en partidas que ya están en progreso o han concluido.
    
            2. **La partida no tiene entre 3 y 6 jugadores.**
               - Excepción generada: `MatchStatesException`
               - Descripción: La partida no cumple con el requisito de tener entre 3 y 6 jugadores para continuar con esta acción.
            """, 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Error al comenzar la partida", 
                     content = @Content),             
    })
    @PostMapping("/{id}/start")
    public ResponseEntity<?> startMatch(@PathVariable Integer id) {
        User currentUser = userService.findCurrentUser();
        
        Match match = matchService.startMatch(id, currentUser.getId());
        return ResponseEntity.ok(new MatchDTO(match));

    }

    /**
     * Elimina una partida si está en lobby (no iniciada) y el usuario es el creador.
     *
     * @param id ID de la partida a eliminar.
     * @return Respuesta vacía con estado NO_CONTENT si se elimina con éxito.
     */
    @Operation(summary = "Borrar partida",
               description = "Solo se puede borrar si la partida no ha comenzado y el usuario es el creador.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Partida eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "Partida no encontrada", 
                     content = @Content),
        @ApiResponse(responseCode = "403", description = "El usuario no es el creador de la partida", 
                     content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado", 
                     content = @Content),
        @ApiResponse(responseCode = "409", description = "La partida está en progreso o ha finalizado", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Error al intentar eliminar la partida", 
                     content = @Content)
    })
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteMatch(@PathVariable Integer id) {

        User currentUser = userService.findCurrentUser();

        matchService.deleteMatch(id, currentUser.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PostMapping("/{matchId}/toggle-role")
    public ResponseEntity<Void> toggleRole(@PathVariable Integer matchId) {
        User currentUser = userService.findCurrentUser();

        matchService.togglePlayerRole(matchId, currentUser);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{matchId}/remove-player/{userIdToRemove}")
    public ResponseEntity<MatchDTO> removePlayerFromMatch(
            @PathVariable Integer matchId,
            @PathVariable Integer userIdToRemove
    ) {
        User currentUser = userService.findCurrentUser();
        Match updatedMatch = matchService.removePlayerFromMatch(
                matchId,
                currentUser.getId(),
                userIdToRemove
        );
        return ResponseEntity.ok(new MatchDTO(updatedMatch));
    }

    /**
     * Obtiene el rol del jugador actual en una partida específica.
     *
     * @param id Identificador de la partida.
     * @return Rol del jugador en la partida con estado HTTP 200, o 404 si no se encuentra.
     */
    @Operation(
        summary = "Obtener rol del jugador en la partida",
        description = "Devuelve el rol del usuario actual en la partida especificada."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol obtenido con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró el rol para el jugador en la partida", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    @GetMapping("/{id}/player-role")
    public ResponseEntity<String> getPlayerRole(@PathVariable Integer id) {

        User currentUser = userService.findCurrentUser();
        
        PlayerType role = playerService.findRoleByMatchIdAndUsername(id, currentUser.getUsername());

        if (role == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(role.toString(), HttpStatus.OK);
    }

    @GetMapping("/{id}/private")
    public ResponseEntity<PrivateMatchDTO> findPrivateMatchById(@PathVariable Integer id) {
        Match match = matchService.findMatchById(id);
        if (match == null) {
            return ResponseEntity.notFound().build();
        }

        User currentUser = userService.findCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        PrivateMatchDTO privateMatchDTO = new PrivateMatchDTO(match, currentUser);
        return new ResponseEntity<>(privateMatchDTO, HttpStatus.OK);
    }


}