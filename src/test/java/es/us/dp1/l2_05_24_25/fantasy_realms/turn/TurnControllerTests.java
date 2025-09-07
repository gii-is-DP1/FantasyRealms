package es.us.dp1.l2_05_24_25.fantasy_realms.turn;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import es.us.dp1.l2_05_24_25.fantasy_realms.match.DecisionRequest;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.MatchService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;

@WebMvcTest(controllers = TurnController.class)
public class TurnControllerTests {

    @MockBean
    private TurnService turnService;

    @MockBean
    private UserService userService;

    @MockBean
    private MatchService matchService;

    @Autowired
    private MockMvc mockMvc;

    private User mockUser;
    private Match mockMatch;

    @BeforeEach
    void setUp() {

        
        mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("TestUser");

        
        mockMatch = new Match();
        mockMatch.setId(1);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    void testCancelTurn_Success() throws Exception {
        
        when(userService.findCurrentUser()).thenReturn(mockUser);
        when(matchService.findMatchById(1)).thenReturn(mockMatch);

        
        mockMvc.perform(post("/api/v1/turns/{matchId}/cancelTurn", 1)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        
        verify(userService, times(1)).findCurrentUser();
        verify(matchService, times(1)).findMatchById(1);
        verify(turnService, times(1)).cancelTurn(eq(mockUser), eq(mockMatch));
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    void testSpecialTurn_Success() throws Exception {
        
        when(userService.findCurrentUser()).thenReturn(mockUser);
        when(matchService.findMatchById(1)).thenReturn(mockMatch);
        when(turnService.specialTurn(eq(mockUser), eq(mockMatch), anyList())).thenReturn(true);

        DecisionRequest request = new DecisionRequest();
        // Establecer decisiones en el request
        request.setDecisions(new ArrayList<>()); // Ejemplo de decisión vacía

        // Realizar POST request para el turno especial
        mockMvc.perform(post("/api/v1/turns/{matchId}/specialTurn", 1)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"decisions\": []}"))
                .andExpect(status().isOk());

        // Verificar interacciones
        verify(userService, times(1)).findCurrentUser();
        verify(matchService, times(1)).findMatchById(1);
        verify(turnService, times(1)).specialTurn(eq(mockUser), eq(mockMatch), anyList());
        verify(matchService, times(1)).endMatch(eq(mockMatch));
    }
}
