package es.us.dp1.l2_05_24_25.fantasy_realms.player;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.MatchService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;

@WebMvcTest(controllers = PlayerController.class)
class PlayerControllerTests {

    @MockBean
    private PlayerService playerService;

    @MockBean
    private UserService userService;

    @MockBean
    private MatchService matchService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    void testDrawCardFromDeck_Success() throws Exception {
        // Mock User
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("TestUser");
    
        // Mock Player
        Player mockPlayer = new Player();
        mockPlayer.setPlayerHand(new ArrayList<>());
        mockPlayer.setId(1);
        mockPlayer.setUser(mockUser);
    
        // Mock Match
        Match mockMatch = new Match();
        mockMatch.setId(1); 
    
        // Mock behavior
        when(userService.findCurrentUser()).thenReturn(mockUser);
        when(matchService.findMatchById(1)).thenReturn(mockMatch); 
        when(playerService.drawCardFromDeck(eq(mockUser), eq(mockMatch))).thenReturn(mockPlayer);
    
        // Perform the POST request
        mockMvc.perform(post("/api/v1/players/drawCard/deck/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    
        // Verify interactions
        verify(userService, times(1)).findCurrentUser();
        verify(matchService, times(1)).findMatchById(1);
        verify(playerService, times(1)).drawCardFromDeck(eq(mockUser), eq(mockMatch));
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    void testDrawCardFromDiscard_Success() throws Exception {
        // Mock User
        User mockUser = new User();
        mockUser.setId(1);
    
        // Mock Player
        Player mockPlayer = new Player();
        mockPlayer.setUser(mockUser);
        mockPlayer.setPlayerHand(new ArrayList<>());
    
        // Mock Match
        Match mockMatch = new Match();
        mockMatch.setId(1);
    
        // Mock behavior
        when(userService.findCurrentUser()).thenReturn(mockUser);
        when(matchService.findMatchById(1)).thenReturn(mockMatch);
        when(playerService.drawCardFromDiscard(eq(mockUser), eq(mockMatch), eq(5))).thenReturn(mockPlayer);
    
        // Perform the POST request
        mockMvc.perform(post("/api/v1/players/drawCard/discardPile/1/5")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    
        // Verify interactions
        verify(userService, times(1)).findCurrentUser();
        verify(matchService, times(1)).findMatchById(1);
        verify(playerService, times(1)).drawCardFromDiscard(eq(mockUser), eq(mockMatch), eq(5));
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    void testDiscardCard_Success() throws Exception {
        // Mock User
        User mockUser = new User();
        mockUser.setId(1);
    
        // Mock Player
        Player mockPlayer = new Player();
        mockPlayer.setUser(mockUser);
        mockPlayer.setPlayerHand(new ArrayList<>());
    
        // Mock Match
        Match mockMatch = new Match();
        mockMatch.setId(1); 
    
        // Mock behavior
        when(userService.findCurrentUser()).thenReturn(mockUser);
        when(matchService.findMatchById(1)).thenReturn(mockMatch); 
        when(playerService.discardCard(eq(mockUser), eq(mockMatch), eq(10))).thenReturn(mockPlayer);
    
        // Perform the POST request
        mockMvc.perform(post("/api/v1/players/discard/1/10")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    
        // Verify interactions
        verify(userService, times(1)).findCurrentUser();
        verify(matchService, times(1)).findMatchById(1);
        verify(playerService, times(1)).discardCard(eq(mockUser), eq(mockMatch), eq(10));
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    void testDrawCardFromDeck_Unauthorized() throws Exception {
        // Mock behavior to simulate unauthorized access
        when(userService.findCurrentUser()).thenReturn(null);
    
        // Perform the POST request
        mockMvc.perform(post("/api/v1/players/drawCard/deck/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    
        // Verify interactions
        verify(userService, times(1)).findCurrentUser();
        verify(playerService, times(0)).drawCardFromDeck(any(User.class), any(Match.class)); 
    }

}
