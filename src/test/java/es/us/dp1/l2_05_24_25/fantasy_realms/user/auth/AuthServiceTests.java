package es.us.dp1.l2_05_24_25.fantasy_realms.user.auth;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.InvalidStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.auth.payload.request.SignupRequest;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.authorities.AuthoritiesService;

@SpringBootTest
public class AuthServiceTests {

	@Autowired
	protected AuthService authService;
	@Autowired
	protected UserService userService;	
	@Autowired
	protected AuthoritiesService authoritiesService;

	// Mockear para que cargue el contexto correctamente. Si no da fallo al cargar WebSocket
	@MockBean
    private ServerEndpointExporter serverEndpointExporter;

    @Test
    public void shouldCreateAdminUser() {
        
        SignupRequest request = createRequest("ADMIN", "adminUser");

        // Contar usuarios iniciales
        Page<User> initialUsers = userService.findAll(PageRequest.of(0, 10));
        long initialUserCount = initialUsers.getTotalElements();

        // Ejecutar el método a probar
        authService.createUser(request);

        // Contar usuarios después de crear el nuevo usuario
        Page<User> finalUsers = userService.findAll(PageRequest.of(0, 10));
        long finalUserCount = finalUsers.getTotalElements();

        // Verificar que el usuario se creó correctamente
        assertEquals(initialUserCount + 1, finalUserCount);

        // Verificar que el usuario tiene el rol correcto
        User createdUser = userService.findUser("adminUser");
        assertNotNull(createdUser);
        assertEquals("ADMIN", createdUser.getAuthority().getAuthority());
    }

    @Test
    public void shouldCreatePlayerUser() {
        // Crear una solicitud para un usuario con rol PLAYER
        SignupRequest request = createRequest("PLAYER", "playerUser");

        // Contar usuarios iniciales
        Page<User> initialUsers = userService.findAll(PageRequest.of(0, 10));
        long initialUserCount = initialUsers.getTotalElements();

        // Ejecutar el método a probar
        authService.createUser(request);

        // Contar usuarios después de crear el nuevo usuario
        Page<User> finalUsers = userService.findAll(PageRequest.of(0, 10));
        long finalUserCount = finalUsers.getTotalElements();

        // Verificar que el usuario se creó correctamente
        assertEquals(initialUserCount + 1, finalUserCount);

        // Verificar que el usuario tiene el rol correcto
        User createdUser = userService.findUser("playerUser");
        assertNotNull(createdUser);
        assertEquals("PLAYER", createdUser.getAuthority().getAuthority());
    }

    @Test
    public void shouldThrowExceptionForInvalidRole() {
        // Crear una solicitud con un rol inválido
        SignupRequest request = createRequest("INVALID_ROLE", "invalidUser");

        // Verificar que se lanza una excepción al intentar crear un usuario con un rol inválido
        InvalidStatesException exception = assertThrows(InvalidStatesException.class, () -> {
            authService.createUser(request);
        });
        assertNotNull(exception);
    }

    

    private SignupRequest createRequest(String authority, String username) {
        SignupRequest request = new SignupRequest();
        request.setUsername(username);
        request.setPassword("password123");
        request.setAuthority(authority);
        return request;
    }



}
