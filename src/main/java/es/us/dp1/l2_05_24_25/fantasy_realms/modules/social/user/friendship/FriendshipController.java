package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.friendship;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.connections.UserConnectionService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controlador para gestionar todas las operaciones de amistad (friendships)
 * como enviar solicitudes, aceptar, eliminar, listar amigos, etc.
 */
@RestController
@RequestMapping("api/v1/friendships")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Friendship", description = """
        Controlador para las peticiones y gestión de amistad entre usuarios.
         Seguridad: Todos los endpoints requieren autenticación Bearer.
        """)
public class FriendshipController {

    private final FriendshipService friendshipService;
    private final UserService userService;
    private final UserConnectionService userConnectionService;

    @Autowired
    public FriendshipController(FriendshipService friendshipService, 
                                UserService userService, 
                                UserConnectionService userConnectionService) {
        this.friendshipService = friendshipService;
        this.userService = userService;
        this.userConnectionService = userConnectionService;
    }

    /**
     * Obtiene la lista de amigos del usuario autenticado.
     *
     * @return Lista de objetos FriendshipRequestDTO con información de cada amistad.
     */
    @Operation(summary = "Obtener lista de amigos",
               description = "Retorna la lista de amigos para el usuario actual.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de amigos obtenida con éxito"),
        @ApiResponse(responseCode = "401", description = "No autorizado (sin token o inválido)")
    })
    @GetMapping("/friends")
    public ResponseEntity<List<FriendshipRequestDTO>> getFriends() {
        User currentUser = userService.findCurrentUser();
        List<Friendship> friends = friendshipService.getFriends(currentUser.getId());
        List<FriendshipRequestDTO> friendsDTO = friends.stream()
            .map(f -> {
                FriendshipRequestDTO dto = new FriendshipRequestDTO();
                dto.setSenderId(f.getId().getUserId());
                dto.setSenderName(f.getUser().getUsername());
                dto.setReceiverId(f.getId().getFriendId());
                dto.setReceiverName(f.getFriend().getUsername());
                dto.setStatus(f.getStatus());
                dto.setSenderIsOnline(userConnectionService.isUserOnline(f.getUser()));
                dto.setReceiverIsOnline(userConnectionService.isUserOnline(f.getFriend()));
                return dto;
            })
            .toList();
        return ResponseEntity.ok(friendsDTO);
    }

    /**
     * Obtiene todas las amistades (en cualquier estado) del usuario.
     *
     * @return Lista de objetos FriendshipRequestDTO con información de todas las amistades.
     */
    @Operation(summary = "Obtener todas las amistades",
               description = "Retorna todas las amistades (en cualquier estado) para el usuario actual.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de amistades obtenida con éxito"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @GetMapping("/all")
    public ResponseEntity<List<FriendshipRequestDTO>> getAllFriendships() {
        User currentUser = userService.findCurrentUser();
        List<Friendship> friendships = friendshipService.getAllFriendshipsForUser(currentUser.getId());
        List<FriendshipRequestDTO> friendshipsDTO = friendships.stream()
            .map(f -> {
                FriendshipRequestDTO dto = new FriendshipRequestDTO();
                dto.setSenderId(f.getId().getUserId());
                dto.setSenderName(f.getUser().getUsername());
                dto.setReceiverId(f.getId().getFriendId());
                dto.setReceiverName(f.getFriend().getUsername());
                dto.setStatus(f.getStatus());
                dto.setSenderIsOnline(userConnectionService.isUserOnline(f.getUser()));
                dto.setReceiverIsOnline(userConnectionService.isUserOnline(f.getFriend()));
                return dto;
            })
            .toList();
        return ResponseEntity.ok(friendshipsDTO);
    }

    /**
     * Envía una solicitud de amistad al usuario con el nombre indicado.
     *
     * @param receiverUsername Nombre de usuario del destinatario de la solicitud.
     * @return Objeto FriendshipRequestDTO con la información de la solicitud creada.
     */
    @Operation(summary = "Enviar solicitud de amistad",
               description = "Envía una solicitud de amistad al usuario especificado por 'receiverUsername'.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitud de amistad enviada con éxito"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PostMapping("/request")
    public ResponseEntity<FriendshipRequestDTO> sendFriendRequest(
            @Valid @RequestParam("receiverUsername") String receiverUsername) {
        User currentUser = userService.findCurrentUser();
        User receiver = userService.findUser(receiverUsername);
        Friendship f = friendshipService.sendFriendRequest(currentUser.getId(), receiver.getId());
        FriendshipRequestDTO dto = new FriendshipRequestDTO(
            f.getSenderId(),
            userService.findUser(f.getSenderId()).getUsername(),
            f.getReceiverId(),
            userService.findUser(f.getReceiverId()).getUsername(),
            f.getStatus()
        );
        return ResponseEntity.ok(dto);
    }

    /**
     * Acepta una solicitud de amistad.
     *
     * @param senderId ID del usuario que envió la solicitud.
     * @return Objeto FriendshipRequestDTO con la información de la amistad actualizada.
     */
    @Operation(summary = "Aceptar solicitud de amistad",
               description = "Acepta la solicitud de amistad enviada por 'senderId'.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitud de amistad aceptada"),
        @ApiResponse(responseCode = "404", description = "Solicitud o usuario no encontrada"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PostMapping("/accept")
    public ResponseEntity<FriendshipRequestDTO> acceptFriendRequest(
            @Valid @RequestParam("senderId") Integer senderId) {
        User currentUser = userService.findCurrentUser();
        Friendship friendship = friendshipService.acceptFriendRequest(senderId, currentUser.getId());
        FriendshipRequestDTO dto = new FriendshipRequestDTO(
            friendship.getSenderId(),
            userService.findUser(friendship.getSenderId()).getUsername(),
            friendship.getReceiverId(),
            userService.findUser(friendship.getReceiverId()).getUsername(),
            friendship.getStatus()
        );
        return ResponseEntity.ok(dto);
    }

    /**
     * Elimina la amistad con el usuario indicado por friendId.
     *
     * @param friendId ID del amigo con quien se desea eliminar la amistad.
     * @return Respuesta vacía en caso de éxito.
     */
    @Operation(summary = "Eliminar amistad",
               description = "Elimina la amistad con el usuario proporcionado en el parámetro 'friendId'.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Amistad eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "Usuario o amistad no encontrada"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFriendship(@Valid @RequestParam("friendId") Integer friendId) {
        User currentUser = userService.findCurrentUser();
        friendshipService.deleteFriendship(currentUser.getId(), friendId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene todas las solicitudes de amistad pendientes del usuario.
     *
     * @return Lista de FriendshipRequestDTO que representan las solicitudes pendientes.
     */
    @Operation(summary = "Obtener solicitudes pendientes",
               description = "Retorna todas las solicitudes de amistad con estado 'PENDING' para el usuario actual.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de solicitudes pendientes obtenida con éxito"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @GetMapping("/pending")
    public ResponseEntity<List<FriendshipRequestDTO>> getPendingFriendships() {
        User currentUser = userService.findCurrentUser();
        List<FriendshipRequestDTO> pending = friendshipService.getFriendshipsForUserByStatus(currentUser.getId(), "PENDING");
        return ResponseEntity.ok(pending);
    }
}
