package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.connection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.connections.UserConnection;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.connections.UserConnectionRepository;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.connections.UserConnectionService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;

@ExtendWith(MockitoExtension.class)
public class UserConnectionServiceTests {

    protected UserConnectionService userConnectionService;

    @Mock
    private UserConnectionRepository userConnectionRepository;

    private User user;
    private User friend;
    private UserConnection userConnection;

    @BeforeEach
    void setUp() {

        userConnectionService = new UserConnectionService(userConnectionRepository);

        user = new User();
        user.setId(1);
        user.setUsername("testUser");

        friend = new User();
        friend.setId(2);
        friend.setUsername("friendUser");

        userConnection = new UserConnection();
        userConnection.setUser(user);
        userConnection.setOnline(false);
        userConnection.setLastConnection(null);
    }

    @Test
    void testUpdateConnection_NewConnection() {
        // Simular que no existe ninguna conexión para el usuario
        when(userConnectionRepository.findByUser(user)).thenReturn(Optional.empty());

        // Llamar al método a probar
        userConnectionService.updateConnection(user);

        // Verificar que se guarda una nueva conexión
        verify(userConnectionRepository).save(any(UserConnection.class));
    }

    @Test
    void testUpdateConnection_ExistingConnection() {
        // Simular una conexión existente
        when(userConnectionRepository.findByUser(user)).thenReturn(Optional.of(userConnection));

        // Llamar al método a probar
        userConnectionService.updateConnection(user);

        // Verificar que la conexión existente se actualiza
        assertTrue(userConnection.isOnline());
        assertNotNull(userConnection.getLastConnection());
        verify(userConnectionRepository).save(userConnection);
    }

    @Test
    void testIsUserOnline_UserIsOnline() {
        // Simular que el usuario está en línea
        userConnection.setOnline(true);
        when(userConnectionRepository.findByUser(user)).thenReturn(Optional.of(userConnection));

        // Llamar al método a probar
        boolean isOnline = userConnectionService.isUserOnline(user);

        // Verificar que devuelve true
        assertTrue(isOnline);
    }

    @Test
    void testIsUserOnline_UserIsOffline() {
        // Simular que el usuario está desconectado
        userConnection.setOnline(false);
        when(userConnectionRepository.findByUser(user)).thenReturn(Optional.of(userConnection));

        // Llamar al método a probar
        boolean isOnline = userConnectionService.isUserOnline(user);

        // Verificar que devuelve false
        assertFalse(isOnline);
    }

    @Test
    void testIsUserOnline_NoConnection() {
        // Simular que no existe conexión para el usuario
        when(userConnectionRepository.findByUser(user)).thenReturn(Optional.empty());

        // Llamar al método a probar
        boolean isOnline = userConnectionService.isUserOnline(user);

        // Verificar que devuelve false
        assertFalse(isOnline);
    }

    @Test
    void testCheckInactiveUsers() {
        // Simular usuarios conectados
        Instant lastConnection = Instant.now().minus(Duration.ofSeconds(90));
        userConnection.setOnline(true);
        userConnection.setLastConnection(lastConnection);
        when(userConnectionRepository.findAll()).thenReturn(List.of(userConnection));

        // Llamar al método a probar
        userConnectionService.checkInactiveUsers();

        // Verificar que el usuario se marca como desconectado
        assertFalse(userConnection.isOnline());
        verify(userConnectionRepository).save(userConnection);
    }

    @Test
    void testCheckInactiveUsers_UserStillActive() {
        // Simular un usuario que sigue activo
        Instant lastConnection = Instant.now().minus(Duration.ofSeconds(30));
        userConnection.setOnline(true);
        userConnection.setLastConnection(lastConnection);
        when(userConnectionRepository.findAll()).thenReturn(List.of(userConnection));

        // Llamar al método a probar
        userConnectionService.checkInactiveUsers();

        // Verificar que el usuario sigue marcado como online
        assertTrue(userConnection.isOnline());
        verify(userConnectionRepository, never()).save(userConnection);
    }

    @Test
    void testCheckInactiveUsersWhenOfflineOrNoLastConnection() {
        // Simular conexiones de usuario
        UserConnection connection1 = new UserConnection();
        connection1.setUser(user);
        connection1.setOnline(false); // Usuario no está online

        UserConnection connection2 = new UserConnection();
        connection2.setUser(friend);
        connection2.setOnline(true);
        connection2.setLastConnection(null); // lastConnection es null

        when(userConnectionRepository.findAll()).thenReturn(List.of(connection1, connection2));

        // Ejecutar el método
        userConnectionService.checkInactiveUsers();

        // Verificar que no se guarda ninguna conexión porque el primer if no se cumple
        verify(userConnectionRepository, never()).save(any(UserConnection.class));
    }
    
}
