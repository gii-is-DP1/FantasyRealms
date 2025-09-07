package es.us.dp1.l2_05_24_25.fantasy_realms.achievements;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.Achievement;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.AchievementCondition;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.AchievementController;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.AchievementService;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.AchievementType;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.TierType;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.authorities.Authorities;

@WebMvcTest(controllers = AchievementController.class, 
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
public class AchievementControllerTests {

    @SuppressWarnings("unused")
    @Autowired
    private AchievementController achievementController;

    @MockBean
    private AchievementService achievementService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private Authorities auth1;
    private Authorities auth2;
    private User adminUser;
    private User normalUser;
    private Achievement mockAchievement;

    @BeforeEach
    void setUp() {

        // Mock usuarios

        auth1 = new Authorities();
		auth1.setId(1);
		auth1.setAuthority("ADMIN");

        auth2 = new Authorities();
		auth2.setId(2);
		auth2.setAuthority("PLAYER");

        adminUser = new User();
        adminUser.setId(1);
        adminUser.setUsername("AdminUser");
        adminUser.setAuthority(auth1);

        normalUser = new User();
        normalUser.setId(2);
        normalUser.setUsername("NormalUser");
        normalUser.setAuthority(auth2);

        // Mock logro
        mockAchievement = new Achievement(
            1, 10, TierType.FACIL, 
            "Nuevo logro de prueba", "KING,QUEEN", "icon.png", 
            AchievementCondition.WIN_WITH_SPECIFIC_CARDS, AchievementType.HABILIDAD
        );

    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testCreateAchievement_AsAdmin() throws Exception {
        when(userService.findCurrentUser()).thenReturn(adminUser);
        when(achievementService.createAchievement(any(Achievement.class)))
                .thenReturn(mockAchievement);
    
        mockMvc.perform(post("/api/v1/achievements")
                .with(csrf()) // sin el token simulado no nos deja realizar la llamada!
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"Nuevo logro\", \"requiredValue\":10, \"tier\":\"FACIL\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.description").value("Nuevo logro de prueba"))
            .andExpect(jsonPath("$.requiredValue").value(10));
    
        verify(userService).findCurrentUser();
        verify(achievementService).createAchievement(any(Achievement.class));
    }
    
    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    void testCreateAchievement_AsNormalUser() throws Exception {
        when(userService.findCurrentUser()).thenReturn(normalUser);

        mockMvc.perform(post("/api/v1/achievements")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"Nuevo logro\", \"requiredValue\":10, \"tier\":\"FACIL\"}"))
            .andExpect(status().isForbidden());

        verify(userService).findCurrentUser();
        verify(achievementService, never()).createAchievement(any(Achievement.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testUpdateAchievement_AsAdmin() throws Exception {
        when(userService.findCurrentUser()).thenReturn(adminUser);
        when(achievementService.updateAchievement(eq(1), any(Achievement.class))).thenReturn(mockAchievement);

        mockMvc.perform(put("/api/v1/achievements/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"Logro actualizado\", \"requiredValue\":10, \"tier\":\"INTERMEDIO\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value("Nuevo logro de prueba"))
            .andExpect(jsonPath("$.requiredValue").value(10));

        verify(userService).findCurrentUser();
        verify(achievementService).updateAchievement(eq(1), any(Achievement.class));
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    void testUpdateAchievement_AsUser() throws Exception {
        when(userService.findCurrentUser()).thenReturn(normalUser);
        when(achievementService.updateAchievement(eq(1), any(Achievement.class))).thenReturn(mockAchievement);

        mockMvc.perform(put("/api/v1/achievements/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"Logro actualizado\", \"requiredValue\":10, \"tier\":\"INTERMEDIO\"}"))
            .andExpect(status().isForbidden());

        verify(userService).findCurrentUser();
        verify(achievementService, never()).updateAchievement(anyInt(), any(Achievement.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testDeleteAchievement_AsAdmin() throws Exception {
        when(userService.findCurrentUser()).thenReturn(adminUser);

        mockMvc.perform(delete("/api/v1/achievements/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(userService).findCurrentUser();
        verify(achievementService).deleteAchievement(1);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    void testDeleteAchievement_AsUser() throws Exception {
        when(userService.findCurrentUser()).thenReturn(normalUser);

        mockMvc.perform(delete("/api/v1/achievements/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        verify(userService).findCurrentUser();
        verify(achievementService, never()).deleteAchievement(anyInt());
    }

    @Test
    @WithMockUser
    void testFindAllAchievements() throws Exception {
        when(achievementService.findAll()).thenReturn(List.of(mockAchievement));

        mockMvc.perform(get("/api/v1/achievements")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].description").value("Nuevo logro de prueba"));

        verify(achievementService).findAll();
    }

    @Test
    @WithMockUser
    void testFindAchievementById() throws Exception {
        when(achievementService.findById(1)).thenReturn(mockAchievement);

        mockMvc.perform(get("/api/v1/achievements/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value("Nuevo logro de prueba"));

        verify(achievementService).findById(1);
    }
    
}
