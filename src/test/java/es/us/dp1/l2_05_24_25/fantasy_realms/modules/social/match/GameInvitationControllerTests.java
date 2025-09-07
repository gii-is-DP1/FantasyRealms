package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.match;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.MatchService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;

import org.springframework.context.annotation.FilterType;

@WebMvcTest(controllers = GameInvitationController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
public class GameInvitationControllerTests {
    
    private static final String BASE_URL = "/api/v1/game-invitation";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private GameInvitationService gameInvitationService;

    @MockBean
    private MatchService matchService;

    private User currentUser;
    private User receiver;
    private Match match;

    @BeforeEach
    void setUp() {
        // Mock del usuario actual
        currentUser = new User();
        currentUser.setId(1);
        currentUser.setUsername("currentUser");

        // Mock del receptor
        receiver = new User();
        receiver.setId(2);
        receiver.setUsername("receiverUser");

        // Mock de la partida
        match = new Match();
        match.setId(1);
        match.setName("Test Match");

        when(userService.findCurrentUser()).thenReturn(currentUser);
        when(userService.findUser("receiverUser")).thenReturn(receiver);
        when(matchService.findMatchById(1)).thenReturn(match);
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    void shouldGetGameInvitations() throws Exception {
        GameInvitationDTO invitationDTO = new GameInvitationDTO("currentUser", "receiverUser", false, 1);
        when(gameInvitationService.getGameInvitations(currentUser)).thenReturn(List.of(invitationDTO));

        mockMvc.perform(get(BASE_URL)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].senderUsername").value("currentUser"))
                .andExpect(jsonPath("$[0].receiverUsername").value("receiverUser"))
                .andExpect(jsonPath("$[0].status").value(false))
                .andExpect(jsonPath("$[0].matchId").value(1));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    void shouldSendGameInvitation() throws Exception {
        mockMvc.perform(post(BASE_URL + "/send/{receiver}/{match}", "receiverUser", 1)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(gameInvitationService).sendGameInvitation(currentUser, receiver, match);
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    void shouldAcceptGameInvitation() throws Exception {
        mockMvc.perform(post(BASE_URL + "/accept/{match}/{sender}", 1, "receiverUser")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(gameInvitationService).acceptGameInvitation(currentUser, receiver, match);
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    void shouldRejectGameInvitation() throws Exception {
        mockMvc.perform(post(BASE_URL + "/reject/{match}", 1)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(gameInvitationService).rejectGameInvitation(currentUser, match);
    }
    
}
