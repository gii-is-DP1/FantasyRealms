package es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements;

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
import org.springframework.web.bind.annotation.RestController;

import es.us.dp1.l2_05_24_25.fantasy_realms.dto.AchievementDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

/**
 * Controlador REST que gestiona las operaciones relacionadas con los logros 
 * del sistema, incluyendo creación, actualización, eliminación y consulta de logros.
 * 
 * Seguridad: Todos los endpoints requieren autenticación Bearer.
 * - Los endpoints de creación, actualización y eliminación están restringidos a usuarios con rol ADMIN.
 * - Los endpoints de consulta son accesibles para todos los usuarios autenticados.
 */

@RestController
@RequestMapping("/api/v1/achievements")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Achievements", description = """
    API para la gestión de las operaciones relacionadas con los logros. Todos los endpoints requieren autenticación Bearer. Las restricciones de seguridad se definen en cada endpoint.
    """)

public class AchievementController {

    private AchievementService achievementService;

    private UserService userService;

    @Autowired
    public AchievementController(AchievementService achievementService, UserService userService) {
        this.achievementService = achievementService;
        this.userService = userService;
    }

    /**
     * Crea un nuevo logro (solo para administradores).
     *
     * @param achievementDTO DTO que contiene los datos del nuevo logro.
     * @return El logro creado envuelto en un {@link ResponseEntity} con código HTTP 201 (Created).
     */
    @Operation(summary = "Crear un nuevo logro. Accesible solo por administradores.", 
               description = "Permite a los administradores crear un nuevo logro.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Logro creado con éxito"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado: solo los administradores pueden crear logros", content = @Content),
        @ApiResponse(responseCode = "400", description = "Existe un problema con los datos proporcionados con el cliente", content = @Content),
        @ApiResponse(responseCode = "409", description = "Problema de unicidad en la base de datos", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno inesperado en el servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<AchievementDTO> createAchievement(@RequestBody AchievementDTO achievementDTO) {

        User currentUser = userService.findCurrentUser();

        if(!currentUser.hasAuthority("ADMIN")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Achievement saved = achievementService.createAchievement(new Achievement(achievementDTO));
        return new ResponseEntity<>(new AchievementDTO(saved), HttpStatus.CREATED);
    }

    /**
     * Actualiza un logro existente (solo para administradores).
     *
     * @param achievementId ID del logro a actualizar.
     * @param achievementDTO DTO que contiene los nuevos datos del logro.
     * @return El logro actualizado envuelto en un {@link ResponseEntity} con código HTTP 200 (OK).
     */
    @Operation(summary = "Actualizar un logro existente. Accesible solo por administradores.", 
               description = "Permite a los administradores actualizar un logro existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logro actualizado con éxito"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado: solo los administradores pueden actualizar logros", content = @Content),
        @ApiResponse(responseCode = "404", description = "Logro no encontrado", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno inesperado en el servidor", content = @Content)
    })
    @PutMapping("/{achievementId}")
    public ResponseEntity<AchievementDTO> updateAchievement(
        @PathVariable Integer achievementId,
        @RequestBody AchievementDTO achievementDTO) {

        User currentUser = userService.findCurrentUser();

        if(!currentUser.hasAuthority("ADMIN")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Achievement updated = achievementService.updateAchievement(achievementId, new Achievement(achievementDTO));
        return ResponseEntity.ok(new AchievementDTO(updated));
    }

    /**
     * Borra un logro existente (solo administradores).
     *
     * @param achievementId ID del logro a eliminar.
     * @return Respuesta vacía con código HTTP 204 (No Content).
     */
    @Operation(summary = "Eliminar un logro existente. Accesible solo por administradores.", 
               description = "Permite a los administradores eliminar un logro existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Logro eliminado con éxito"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado: solo los administradores pueden eliminar logros", content = @Content),
        @ApiResponse(responseCode = "404", description = "Logro no encontrado", content = @Content)
    })
    @DeleteMapping("/{achievementId}")
    public ResponseEntity<Void> deleteAchievement(@PathVariable Integer achievementId) {

        User currentUser = userService.findCurrentUser();

        if(!currentUser.hasAuthority("ADMIN")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        achievementService.deleteAchievement(achievementId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista todos los logros (accesible por admin y usuarios).
     *
     * @return Una lista de logros envuelta en un {@link ResponseEntity} con código HTTP 200 (OK).
     */
    @Operation(summary = "Listar todos los logros. Accesible por cualquier usuario.", 
               description = "Devuelve una lista de todos los logros.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de logros recuperada con éxito")
    })
    @GetMapping
    public ResponseEntity<List<AchievementDTO>> findAll() {
        List<AchievementDTO> list = achievementService.findAll().stream().map(AchievementDTO::new).toList();
        return ResponseEntity.ok(list);
    }

    /**
     * Retorna un logro por su ID.
     *
     * @param id ID del logro.
     * @return El logro encontrado envuelto en un {@link ResponseEntity} con código HTTP 200 (OK).
     */
    @Operation(summary = "Obtener un logro por ID. Accesible por cualquier usuario.", 
               description = "Recupera un logro por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logro recuperado con éxito"),
        @ApiResponse(responseCode = "404", description = "Logro no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<AchievementDTO> findById(@PathVariable Integer id) {

        Achievement achievement = achievementService.findById(id);

        return new ResponseEntity<>(new AchievementDTO(achievement), HttpStatus.OK);
        
    }

}
