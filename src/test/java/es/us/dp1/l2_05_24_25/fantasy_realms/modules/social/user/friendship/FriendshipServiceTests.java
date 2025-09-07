package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.friendship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.match.GameInvitationRepository;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;

@ExtendWith(MockitoExtension.class)
public class FriendshipServiceTests {

    protected FriendshipService friendshipService;

    @Mock
    private UserService userService;

    @Mock
    private GameInvitationRepository gameInvitationRepository;

    @Mock
    private FriendshipRepository friendshipRepository;


    private Friendship friendship;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {

        friendshipService = new FriendshipService(friendshipRepository, userService, gameInvitationRepository);

        // Crear un objeto FriendshipId con un orden específico
        FriendshipId friendshipId = FriendshipId.create(1, 2);

        // Crear un objeto Friendship
        friendship = new Friendship();
        friendship.setId(friendshipId);
        friendship.setSenderId(1);
        friendship.setReceiverId(2);
        friendship.setStatus("PENDING");

        user1 = new User();
        user1.setId(1);
        user1.setUsername("User1");

        user2 = new User();
        user2.setId(2);
        user2.setUsername("User2");
    }

    @Test
    public void testGetAllFriendshipsForUser() {
        
        friendship.setStatus("ACCEPTED");
    
        Friendship friendship2 = new Friendship();
        friendship2.setId(FriendshipId.create(1, 3));
        friendship2.setReceiverId(3);
        friendship2.setStatus("PENDING");
    
        // Simular comportamiento del repositorio
        when(friendshipRepository.findAllByUserId(1)).thenReturn(Arrays.asList(friendship, friendship2));
    
        // Ejecutar el método a probar
        List<Friendship> result = friendshipService.getAllFriendshipsForUser(1);
    
        // Verificar resultados
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(friendship));
        assertTrue(result.contains(friendship2));
    
        // Verificar que el repositorio fue llamado correctamente
        verify(friendshipRepository).findAllByUserId(1);
    }
    
    @Test
    public void testSendFriendRequestAlreadyExists() {
    
        // Simular que ya existe la amistad en el repositorio
        when(friendshipRepository.existsById(friendship.getId())).thenReturn(true);
    
        // Llamar al método y verificar que lanza una excepción
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            friendshipService.sendFriendRequest(1, 2);
        });
    
        // Verificar el mensaje de la excepción
        assertEquals("Ya existe una amistad o solicitud entre ambos usuarios.", thrown.getMessage());
    
        // Verificar que el repositorio fue llamado correctamente
        verify(friendshipRepository).existsById(friendship.getId());
    } 

    @Test
    public void testSendFriendRequestSuccess() {
        
        when(friendshipRepository.existsById(friendship.getId())).thenReturn(false);
        when(userService.findUser(1)).thenReturn(user1);
        when(userService.findUser(2)).thenReturn(user2);
        when(friendshipRepository.save(any(Friendship.class))).thenReturn(friendship);

        // Llamada
        Friendship result = friendshipService.sendFriendRequest(1, 2);

        assertNotNull(result);
        assertEquals("PENDING", result.getStatus());
        verify(friendshipRepository).existsById(friendship.getId());
        verify(friendshipRepository).save(any(Friendship.class));
    }


    @Test
    void testAreFriendsWhenUserAndFriendAreSame() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            friendshipService.areFriends(1, 1);
        });
        assertEquals("El usuario y su amigo no pueden ser la misma persona.", thrown.getMessage());
    }

    @Test
    void testAreFriendsWhenUserAndFriendAreDifferent() {

        // Configurar el mock del repositorio para simular que la amistad existe
        when(friendshipRepository.existsById(friendship.getId())).thenReturn(true);

        // Ejecutar el método
        Boolean result = friendshipService.areFriends(1, 2);

        // Verificar el resultado
        assertNotNull(result);
        assertTrue(result);
        verify(friendshipRepository).existsById(friendship.getId());
    }
    

    @Test
    public void testGetFriends() {
        friendship.setStatus("ACCEPTED");
        when(friendshipRepository.findByUserAndStatus(1, "ACCEPTED")).thenReturn(Arrays.asList(friendship));

        List<Friendship> result = friendshipService.getFriends(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(friendship, result.get(0));
    }

    @Test
    public void testSendFriendRequestWhenSenderAndReceiverAreSame() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            friendshipService.sendFriendRequest(1, 1);
        });
        assertEquals("El remitente y el receptor no pueden ser la misma persona.", thrown.getMessage());
    }

    @Test
    public void testAcceptFriendRequestSuccessfully() {
        when(friendshipRepository.findById(any(FriendshipId.class))).thenReturn(Optional.of(friendship));
        when(friendshipRepository.save(any(Friendship.class))).thenReturn(friendship);

        Friendship result = friendshipService.acceptFriendRequest(1, 2);

        assertNotNull(result);
        assertEquals("ACCEPTED", result.getStatus());
    }

    @Test
    public void testAcceptFriendRequestNotPending() {
        friendship.setStatus("ACCEPTED");

        when(friendshipRepository.findById(friendship.getId())).thenReturn(Optional.of(friendship));

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            friendshipService.acceptFriendRequest(1, 2);
        });
        assertEquals("La solicitud no está en estado PENDING.", thrown.getMessage());
    }

    @Test
    public void testAcceptFriendRequestInvalidReceiver() {
        // Configurar mocks
        friendship.setReceiverId(3); // Cambiamos el receiverId para simular un caso inválido
        when(friendshipRepository.findById(any(FriendshipId.class))).thenReturn(Optional.of(friendship));

        // Ejecutar el método y verificar la excepción
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            friendshipService.acceptFriendRequest(1, 2); // Se espera que el receiverId sea 2, pero en la amistad es 3
        });

        // Verificar el mensaje de error
        assertEquals("Solo el receptor puede aceptar la solicitud.", thrown.getMessage());

        // Verificar interacciones
        verify(friendshipRepository).findById(any(FriendshipId.class));
        verify(friendshipRepository, never()).save(any(Friendship.class));
    }


    @Test
    public void testDeleteFriendshipWhenNotExists() {
        when(friendshipRepository.existsById(friendship.getId())).thenReturn(false);

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            friendshipService.deleteFriendship(1, 2);
        });
        assertEquals("No existe amistad/solicitud con esos IDs.", thrown.getMessage());
    }

    @Test
    public void testDeleteFriendshipWhenExists() {
        // Simulamos que la amistad/solicitud SÍ existe en la BD
        when(friendshipRepository.existsById(friendship.getId())).thenReturn(true);

        // Como son métodos void, usamos doNothing()
        doNothing().when(friendshipRepository).deleteById(friendship.getId());
        doNothing().when(gameInvitationRepository).deleteBySenderOrReceiver(1, 2);

        // Llamamos al método que deseamos testear
        friendshipService.deleteFriendship(1, 2);

        // Verificamos que se llamó a los repositorios con los valores esperados
        verify(friendshipRepository).existsById(friendship.getId());
        verify(friendshipRepository).deleteById(friendship.getId());
        verify(gameInvitationRepository).deleteBySenderOrReceiver(1, 2);
    }

    @Test
    public void testAcceptFriendRequestWrongReceiver() {

        // Configurar mocks con los datos del setUp global
        friendship.setStatus("PENDING");
        friendship.setReceiverId(2); // Receptor esperado

        // Generar el FriendshipId
        FriendshipId expectedId = FriendshipId.create(1, 2);
        friendship.setId(expectedId);

        when(friendshipRepository.findById(any(FriendshipId.class))).thenReturn(Optional.of(friendship));

        // Llamada con un receiverId incorrecto (3 en lugar de 2)
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            friendshipService.acceptFriendRequest(1, 3);
        });

        // Verificar que el mensaje de la excepción sea correcto
        assertEquals("Solo el receptor puede aceptar la solicitud.", thrown.getMessage());

    }

    @Test
    public void testGetFriendshipsForUserByStatus_Pending() {

        when(friendshipRepository.findByUserAndStatus(2, "PENDING")).thenReturn(List.of(friendship));
        when(userService.findUser(1)).thenReturn(user1);
        when(userService.findUser(2)).thenReturn(user2);
    
        // Ejecutar método
        List<FriendshipRequestDTO> result = friendshipService.getFriendshipsForUserByStatus(2, "PENDING");
    
        // Verificaciones
        assertEquals(1, result.size());
        FriendshipRequestDTO dto = result.get(0);
        assertEquals(1, dto.getSenderId());
        assertEquals("User1", dto.getSenderName());
        assertEquals(2, dto.getReceiverId());
        assertEquals("User2", dto.getReceiverName());
        assertEquals("PENDING", dto.getStatus());
        verify(friendshipRepository).findByUserAndStatus(2, "PENDING");
        verify(userService).findUser(1);
        verify(userService).findUser(2);
    }    

    @Test
    public void testGetFriendshipsForUserByStatus_Accepted() {

        when(friendshipRepository.findByUserAndStatus(2, "ACCEPTED"))
            .thenReturn(Collections.singletonList(friendship));

        // userService no se llamará en el .map si no pasa el .filter
        List<FriendshipRequestDTO> result = friendshipService.getFriendshipsForUserByStatus(2, "ACCEPTED");

        // filter: "PENDING".equals("ACCEPTED") => false => se descarta
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

     @Test
    public void testGetFriendshipsForUserByStatus_NotPending() {

        friendship.setStatus("ACCEPTED");
        friendship.setSenderId(1);

        when(friendshipRepository.findByUserAndStatus(2, "ACCEPTED"))
            .thenReturn(Collections.singletonList(friendship));

        // Ejecutar método
        List<FriendshipRequestDTO> result = friendshipService.getFriendshipsForUserByStatus(2, "ACCEPTED");

        // Verificaciones
        // El filtro no debería pasar porque el estado no es "PENDING"
        assertNotNull(result);
        assertTrue(result.isEmpty(), "La lista debería estar vacía porque no hay solicitudes pendientes.");

        // Verificar interacciones
        verify(friendshipRepository).findByUserAndStatus(2, "ACCEPTED");
        verifyNoInteractions(userService); // userService no debería ser llamado
    }


}
