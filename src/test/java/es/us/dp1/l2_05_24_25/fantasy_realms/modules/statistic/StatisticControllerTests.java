package es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;

@WebMvcTest(controllers = StatisticsController.class)
class StatisticControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticService statisticService;

    @MockBean
    private UserService userService;

    private User currentUser;

    @BeforeEach
    public void setup() {

        // Configurar un usuario autenticado simulado
        currentUser = new User();
        currentUser.setId(1);
        currentUser.setUsername("TestUser");

        when(userService.findCurrentUser()).thenReturn(currentUser);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testGetTotalMatches() throws Exception {
        when(statisticService.getTotalMatches(currentUser)).thenReturn(5);

        mockMvc.perform(get("/api/v1/statistics/total-matches")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));

        verify(statisticService, times(1)).getTotalMatches(currentUser);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testGetAverageMatchDuration() throws Exception {
        when(statisticService.getAverageMatchDuration(currentUser)).thenReturn(15.5);

        mockMvc.perform(get("/api/v1/statistics/average-match-duration")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("15.5"));

        verify(statisticService, times(1)).getAverageMatchDuration(currentUser);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testGetAveragePlayersPerMatch() throws Exception {
        when(statisticService.getAveragePlayersPerMatch(currentUser)).thenReturn(3.0);

        mockMvc.perform(get("/api/v1/statistics/average-players-per-match")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("3.0"));

        verify(statisticService, times(1)).getAveragePlayersPerMatch(currentUser);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testGetAveragePoints() throws Exception {
        when(statisticService.getAveragePoints(currentUser)).thenReturn(50.0);

        mockMvc.perform(get("/api/v1/statistics/average-points")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("50.0"));

        verify(statisticService, times(1)).getAveragePoints(currentUser);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testGetMaxPoints() throws Exception {
        when(statisticService.getMaxPoints(currentUser)).thenReturn(100);

        mockMvc.perform(get("/api/v1/statistics/max-points")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("100"));

        verify(statisticService, times(1)).getMaxPoints(currentUser);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testGetMinPoints() throws Exception {
        when(statisticService.getMinPoints(currentUser)).thenReturn(10);

        mockMvc.perform(get("/api/v1/statistics/min-points")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));

        verify(statisticService, times(1)).getMinPoints(currentUser);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testGetWinPercentage() throws Exception {
        when(statisticService.getWinPercentage(currentUser)).thenReturn(75.0);

        mockMvc.perform(get("/api/v1/statistics/win-percentage")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("75.0"));

        verify(statisticService, times(1)).getWinPercentage(currentUser);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testGetAverageRankingPosition() throws Exception {
        when(statisticService.getAverageRankingPosition(currentUser)).thenReturn(2.5);

        mockMvc.perform(get("/api/v1/statistics/average-ranking-position")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("2.5"));

        verify(statisticService, times(1)).getAverageRankingPosition(currentUser);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testGetAverageTurnsPerMatch() throws Exception {
        when(statisticService.getAverageTurnsPerMatch(currentUser)).thenReturn(8.0);

        mockMvc.perform(get("/api/v1/statistics/average-turns-per-match")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("8.0"));

        verify(statisticService, times(1)).getAverageTurnsPerMatch(currentUser);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testGetMostFrequentCards() throws Exception {
        List<String> mockCards = Arrays.asList("Caballería Ligera", "Buque de Guerra");
        when(statisticService.getMostFrequentCardsInFinalHands(currentUser)).thenReturn(mockCards);

        mockMvc.perform(get("/api/v1/statistics/most-frequent-cards")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"Caballería Ligera\", \"Buque de Guerra\"]"));

        verify(statisticService, times(1)).getMostFrequentCardsInFinalHands(currentUser);
    }

    @Test
    public void testUnauthorizedAccess() throws Exception {
        when(userService.findCurrentUser()).thenReturn(null);

        mockMvc.perform(get("/api/v1/statistics/total-matches")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void getTotalMatchDuration_ReturnsTotalDuration() throws Exception {
        User mockUser = new User();
        mockUser.setId(1);
        ;
        Mockito.when(userService.findCurrentUser()).thenReturn(mockUser);
        Mockito.when(statisticService.getTotalMatchDuration(mockUser)).thenReturn(120);

        mockMvc.perform(get("/api/v1/statistics/total-match-duration")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("120"));

        Mockito.verify(statisticService, Mockito.times(1)).getTotalMatchDuration(mockUser);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void getMaxMatchDuration_ReturnsMaxDuration() throws Exception {
        User mockUser = new User();
        mockUser.setId(1);
        ;
        Mockito.when(userService.findCurrentUser()).thenReturn(mockUser);
        Mockito.when(statisticService.getMaxMatchDuration(mockUser)).thenReturn(45);

        mockMvc.perform(get("/api/v1/statistics/max-match-duration")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("45"));

        Mockito.verify(statisticService, Mockito.times(1)).getMaxMatchDuration(mockUser);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void getMinMatchDuration_ReturnsMinDuration() throws Exception {
        User mockUser = new User();
        mockUser.setId(1);
        Mockito.when(userService.findCurrentUser()).thenReturn(mockUser);
        Mockito.when(statisticService.getMinMatchDuration(mockUser)).thenReturn(10);

        mockMvc.perform(get("/api/v1/statistics/min-match-duration")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));

        Mockito.verify(statisticService, Mockito.times(1)).getMinMatchDuration(mockUser);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void getGlobalAverageMatchDuration_ReturnsAverageDuration() throws Exception {
        Mockito.when(statisticService.getGlobalAverageMatchDuration()).thenReturn(30.5);

        mockMvc.perform(get("/api/v1/statistics/global-match-duration-average")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("30.5"));

        Mockito.verify(statisticService, Mockito.times(1)).getGlobalAverageMatchDuration();
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void getGlobalMaxMatchDuration_ReturnsGlobalMaxDuration() throws Exception {
        Mockito.when(statisticService.getGlobalMaxMatchDuration()).thenReturn(60);

        mockMvc.perform(get("/api/v1/statistics/global-match-max-duration")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("60"));

        Mockito.verify(statisticService, Mockito.times(1)).getGlobalMaxMatchDuration();
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void getGlobalMinMatchDuration_ReturnsGlobalMinDuration() throws Exception {
        Mockito.when(statisticService.getGlobalMinMatchDuration()).thenReturn(5);

        mockMvc.perform(get("/api/v1/statistics/global-match-min-duration")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));

        Mockito.verify(statisticService, Mockito.times(1)).getGlobalMinMatchDuration();
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testGetMaxPlayersPerMatch() throws Exception {
        when(statisticService.getMaxPlayersPerMatch(currentUser)).thenReturn(6);

        mockMvc.perform(get("/api/v1/statistics/match-players-max")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("6"));

        verify(statisticService, times(1)).getMaxPlayersPerMatch(currentUser);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testGetMinPlayersPerMatch() throws Exception {
        when(statisticService.getMinPlayersPerMatch(currentUser)).thenReturn(2);

        mockMvc.perform(get("/api/v1/statistics/match-players-min")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));

        verify(statisticService, times(1)).getMinPlayersPerMatch(currentUser);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testGetMaxGlobalPlayersPerMatch() throws Exception {
        when(statisticService.getMaxGlobalPlayersPerMatch()).thenReturn(8);

        mockMvc.perform(get("/api/v1/statistics/global-match-players-max")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("8"));

        verify(statisticService, times(1)).getMaxGlobalPlayersPerMatch();
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testGetMinGlobalPlayersPerMatch() throws Exception {
        when(statisticService.getMinGlobalPlayersPerMatch()).thenReturn(1);

        mockMvc.perform(get("/api/v1/statistics/global-match-players-min")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(statisticService, times(1)).getMinGlobalPlayersPerMatch();
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testGetGlobalAveragePlayersPerMatch() throws Exception {
        when(statisticService.getGlobalAveragePlayersPerMatch()).thenReturn(4.5);

        mockMvc.perform(get("/api/v1/statistics/global-match-players-average")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("4.5"));

        verify(statisticService, times(1)).getGlobalAveragePlayersPerMatch();
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testGetGlobalRanking() throws Exception {
        // Mock de datos de ranking
        Map<String, List<Integer>> mockRanking = new HashMap<>();
        mockRanking.put("User1", Arrays.asList(5, 100)); // 5 victorias, 100 puntos
        mockRanking.put("User2", Arrays.asList(3, 80)); // 3 victorias, 80 puntos
        mockRanking.put("User3", Arrays.asList(1, 50)); // 1 victoria, 50 puntos

        // Simulación del servicio
        when(statisticService.getRanking("WINS")).thenReturn(mockRanking);

        // Realizar la petición al endpoint
        mockMvc.perform(get("/api/v1/statistics/global-ranking")
                .with(csrf())
                .param("sortBy", "WINS")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"User1\":[5,100],\"User2\":[3,80],\"User3\":[1,50]}"));

        // Verificación de que el servicio fue llamado con el parámetro correcto
        verify(statisticService, times(1)).getRanking("WINS");
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testGetGlobalRankingDefaultSort() throws Exception {
        // Mock de datos de ranking (usando el valor por defecto del parámetro)
        Map<String, List<Integer>> mockRanking = new HashMap<>();
        mockRanking.put("User1", Arrays.asList(5, 100)); // 5 victorias, 100 puntos
        mockRanking.put("User2", Arrays.asList(3, 80)); // 3 victorias, 80 puntos
        mockRanking.put("User3", Arrays.asList(1, 50)); // 1 victoria, 50 puntos

        // Simulación del servicio
        when(statisticService.getRanking("WINS")).thenReturn(mockRanking);

        // Realizar la petición sin el parámetro sortBy (valor por defecto "WINS")
        mockMvc.perform(get("/api/v1/statistics/global-ranking")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"User1\":[5,100],\"User2\":[3,80],\"User3\":[1,50]}"));

        // Verificación de que el servicio fue llamado con el valor por defecto
        verify(statisticService, times(1)).getRanking("WINS");
    }

}
