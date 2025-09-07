package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.connection;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.connections.UserConnectionController;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.connections.UserConnectionService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;

import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = UserConnectionController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
public class UserConnectionControllerTests {
    
    @SuppressWarnings("unused")
    @Autowired
    private UserConnectionController userConnectionController;

    @MockBean
    private UserConnectionService userConnectionService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private User currentUser;

    @BeforeEach
    void setUp() {
        currentUser = new User();
        currentUser.setId(1);
        currentUser.setUsername("testUser");
    }

    @Test
    @WithMockUser(username = "testUser", roles = { "PLAYER" })
    void testUpdateHeartbeat_Success() throws Exception {
        // Configuración de mocks
        when(userService.findCurrentUser()).thenReturn(currentUser);

        // Realizar solicitud HTTP
        mockMvc.perform(post("/api/v1/heartbeat")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verificar interacciones
        verify(userService).findCurrentUser();
        verify(userConnectionService).updateConnection(currentUser);
    }

    @Test
    void testUpdateHeartbeat_Unauthorized() throws Exception {
        // Realizar solicitud HTTP sin usuario autenticado
        mockMvc.perform(post("/api/v1/heartbeat")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        // Verificar que no se realizan interacciones
        verifyNoInteractions(userService);
        verifyNoInteractions(userConnectionService);
    }
}
