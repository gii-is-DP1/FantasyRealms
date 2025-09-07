package es.us.dp1.l2_05_24_25.fantasy_realms.deck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.us.dp1.l2_05_24_25.fantasy_realms.dto.DeckDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador para gestionar operaciones relacionadas con los mazos de cartas.
 * 
 * Seguridad:Todos los endpoints requieren autenticación Bearer.
 */
@RestController
@RequestMapping("/api/v1/deck")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Decks", description = "API para gestionar mazos de cartas en el juego. Seguridad:Todos los endpoints requieren autenticación Bearer y pueden ser utilizados por cualquier usuario registrado en la aplicación.")
public class DeckController {
    
    private DeckService deckService;

    @Autowired
    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    /**
     * Busca un mazo por su ID.
     * 
     * @param id ID del mazo a buscar.
     * @return Información del mazo en formato {@link DeckDTO}.
     */
    @Operation(summary = "Buscar mazo por ID",
               description = "Devuelve la información del mazo correspondiente al ID especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mazo encontrado con éxito"),
        @ApiResponse(responseCode = "401", description = "No autorizado: se requiere autenticación Bearer", content = @Content),
        @ApiResponse(responseCode = "404", description = "Mazo no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<DeckDTO> findDeckById(@PathVariable Integer id) {
        return new ResponseEntity<>(new DeckDTO(deckService.findDeckById(id)), HttpStatus.OK);
    }

}
