package es.us.dp1.l2_05_24_25.fantasy_realms.achievements;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.InvalidStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.MatchFinishedEvent;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.StatisticService;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.Achievement;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.AchievementCondition;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.AchievementRepository;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.AchievementService;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.AchievementType;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.TierType;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerType;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTests {

    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private StatisticService statisticService;

    @Mock
    private UserService userService;

    protected AchievementService achievementService;

    private User user;
    private Achievement newAchievement;
    private Achievement updatedAchievement;
    private List<Achievement> allAchievements;

    @BeforeEach
    void setUp() {

        achievementService = new AchievementService(achievementRepository, statisticService, userService);

        user = new User();
        user.setId(1);
        user.setUsername("TestUser");
        user.setAchievements(new ArrayList<>());

        allAchievements = List.of(
            new Achievement(1, 10, TierType.INTERMEDIO, "Gana 10 partidas", null, null, AchievementCondition.WIN_N_GAMES, AchievementType.HABILIDAD),
            new Achievement(2, 1, TierType.FACIL, "Juega 1 partida", null, null, AchievementCondition.PLAY_N_GAMES, AchievementType.PROGRESO),
            new Achievement(5, 3, TierType.INTERMEDIO, "Obtén una racha de 3 victorias", null, null, AchievementCondition.WIN_STREAK, AchievementType.HABILIDAD),
            new Achievement(6, 180, TierType.INTERMEDIO, "Gana una partida con 180 puntos o más", null, null, AchievementCondition.WIN_WITH_MIN_POINTS, AchievementType.HABILIDAD),
            new Achievement(8,  null, TierType.FACIL, "Gana una partida después de quedar último", null, null, AchievementCondition.WIN_AFTER_LAST_PLACE, AchievementType.PROGRESO),
            new Achievement(13, 100, TierType.DIFICIL, "Juega 100 cartas de tipo Llama", "LLAMA", null, AchievementCondition.PLAY_N_CARDS_OF_TYPE, AchievementType.PROGRESO),
            new Achievement(14, 100, TierType.DIFICIL, "Juega 100 cartas de tipo Inundacion", "INUNDACION", null, AchievementCondition.PLAY_N_CARDS_OF_TYPE, AchievementType.PROGRESO),
            new Achievement(15, null, TierType.FACIL, "Gana una partida con una mano que no tenga ninguna carta especial", null, null, AchievementCondition.WIN_WITH_NO_RARE_CARDS, AchievementType.PROGRESO)
        );

        newAchievement = new Achievement(
            1, null, TierType.FACIL, 
            "Nuevo logro de prueba", "KING,QUEEN", "icon1.png", 
            AchievementCondition.WIN_WITH_SPECIFIC_CARDS, AchievementType.HABILIDAD
        );

        updatedAchievement = new Achievement(
            1, null, TierType.INTERMEDIO, 
            "Logro actualizado", "SWORD_KETH,SHIELD_KETH", "icon2.png", 
            AchievementCondition.WIN_WITH_SPECIFIC_CARDS, AchievementType.PROGRESO
        );
    }

    @Test
    public void testOnMatchFinished() {
        // Crear un evento simulado de partida finalizada
        Match match = new Match();
        Player player = new Player(user, PlayerType.GANADOR);
        match.setPlayers(List.of(player));
        MatchFinishedEvent event = new MatchFinishedEvent(this, match);

        // Mockear el repositorio para devolver todos los logros
        when(achievementRepository.findAll()).thenReturn(allAchievements);

        // Ejecutar el método
        achievementService.onMatchFinished(event);

        // Verificar que se llamó a checkAndUnlockAchievements para el usuario
        verify(achievementRepository, times(1)).findAll();
        verify(userService, times(1)).saveUserBasic(user);
    }

    @Test
    public void testCheckAndUnlockAchievements() {
        // Configurar estadísticas para que el usuario cumpla con ciertos logros
        when(statisticService.getTotalWins(user)).thenReturn((long) 10); // Cumple "Gana 10 partidas"
        when(statisticService.getCurrentWinStreak(user)).thenReturn(3); // Cumple "Obtén una racha de 3 victorias"

        user.getAchievements().add(allAchievements.get(3)); // suponemos que el usuario ya tiene el último logro

        // Mockear el repositorio para devolver todos los logros
        when(achievementRepository.findAll()).thenReturn(allAchievements);

        // Ejecutar el método
        achievementService.checkAndUnlockAchievements(user);

        // Verificar que el usuario desbloqueó los logros esperados (2 + 1 (predefinido))
        assertEquals(3, user.getAchievements().size(), "El usuario debería haber desbloqueado 2 logros.");
        assertTrue(
            user.getAchievements().stream().anyMatch(ach -> ach.getDescription().equals("Gana 10 partidas")),
            "El usuario debería haber desbloqueado el logro de 'Gana 10 partidas'."
        );
        assertTrue(
            user.getAchievements().stream().anyMatch(ach -> ach.getDescription().equals("Obtén una racha de 3 victorias")),
            "El usuario debería haber desbloqueado el logro de 'Obtén una racha de 3 victorias'."
        );

        // Verificar que se guardó el usuario con los logros actualizados
        verify(userService, times(1)).saveUserBasic(user);
    }

    @Test
    public void testWinWithMinPointsAchievement() {
        Achievement achievement = allAchievements.get(3); // "Gana una partida con 180 puntos o más"

        // Configurar estadísticas
        when(statisticService.getPointsLastMatch(user)).thenReturn(200); // Usuario tiene 200 puntos

        // Ejecutar método
        boolean unlocked = achievementService.isAchievementUnlocked(achievement, user);

        // Verificar resultado
        assertTrue(unlocked, "El logro debería desbloquearse porque el usuario tiene 200 puntos en la última partida.");
    }

    @Test
    public void testWinAfterLastPlaceAchievement() {
        Achievement achievement = allAchievements.get(4); // "Gana una partida después de quedar último"

        // Configurar estadísticas
        when(statisticService.lastGameWasFromLastPlace(user)).thenReturn(true); // Usuario cumple condición

        // Ejecutar método
        boolean unlocked = achievementService.isAchievementUnlocked(achievement, user);

        // Verificar resultado
        assertTrue(unlocked, "El logro debería desbloquearse porque el usuario ganó tras quedar último.");
    }

    @Test
    public void testPlayNCardsOfTypeFireAchievement() {
        Achievement achievement = allAchievements.get(5); // "Juega 100 cartas de tipo Llama"

        // Configurar estadísticas
        when(statisticService.getTotalCardsPlayedByType(user,achievement.getExtraData())).thenReturn(100); // Usuario jugó 100 cartas de tipo Llama

        // Ejecutar método
        boolean unlocked = achievementService.isAchievementUnlocked(achievement, user);

        // Verificar resultado
        assertTrue(unlocked, "El logro debería desbloquearse porque el usuario jugó 100 cartas de tipo Llama.");
    }

    @Test
    public void testPlayNCardsOfTypeFloodAchievement() {
        Achievement achievement = allAchievements.get(6); // "Juega 100 cartas de tipo Inundacion"

        // Configurar estadísticas
        when(statisticService.getTotalCardsPlayedByType(user,achievement.getExtraData())).thenReturn(100); // Usuario jugó 100 cartas de tipo Inundacion

        // Ejecutar método
        boolean unlocked = achievementService.isAchievementUnlocked(achievement, user);

        // Verificar resultado
        assertTrue(unlocked, "El logro debería desbloquearse porque el usuario jugó 100 cartas de tipo Inundacion.");
    }

    @Test
    public void testWinWithNoRareCardsAchievement() {
        Achievement achievement = allAchievements.get(7); // "Gana una partida con una mano sin cartas especiales"

        // Configurar estadísticas
        when(statisticService.hadNoRareCardsInLastGame(user)).thenReturn(true); // Usuario cumple condición

        // Ejecutar método
        boolean unlocked = achievementService.isAchievementUnlocked(achievement, user);

        // Verificar resultado
        assertTrue(unlocked, "El logro debería desbloquearse porque el usuario ganó sin cartas especiales en su mano.");
    }

    @Test
    public void testEvaluateWinWithSpecificCards_KingAndQueen() {
        // Configurar logro con "KING,QUEEN" en extraData
        Achievement achievement = new Achievement();
        achievement.setExtraData("KING,QUEEN");

        // Configurar mock para que el usuario cumpla el logro
        when(statisticService.hadKingAndQueenInLastGame(user)).thenReturn(true);

        // Ejecutar el método
        boolean result = achievementService.evaluateWinWithSpecificCards(achievement,user);

        // Verificar el resultado
        assertTrue(result, "El usuario debería cumplir el logro con 'KING,QUEEN'.");
        verify(statisticService, times(1)).hadKingAndQueenInLastGame(user);
    }

    @Test
    public void testEvaluateWinWithSpecificCards_BeastmasterAnd3Beasts() {
        // Configurar logro con "BEASTMASTER,3_BEASTS" en extraData
        Achievement achievement = new Achievement();
        achievement.setExtraData("BEASTMASTER,3_BEASTS");

        // Configurar mock para que el usuario cumpla el logro
        when(statisticService.hadBeastmasterAnd3Beasts(user)).thenReturn(true);

        // Ejecutar el método
        boolean result = achievementService.evaluateWinWithSpecificCards(achievement,user);

        // Verificar el resultado
        assertTrue(result, "El usuario debería cumplir el logro con 'BEASTMASTER,3_BEASTS'.");
        verify(statisticService, times(1)).hadBeastmasterAnd3Beasts(user);
    }

    @Test
    public void testEvaluateWinWithSpecificCards_SwordAndShieldOfKeth() {
        // Configurar logro con "SWORD_KETH,SHIELD_KETH" en extraData
        Achievement achievement = new Achievement();
        achievement.setExtraData("SWORD_KETH,SHIELD_KETH");

        // Configurar mock para que el usuario cumpla el logro
        when(statisticService.hadSwordAndShieldOfKeth(user)).thenReturn(true);

        // Ejecutar el método
        boolean result = achievementService.evaluateWinWithSpecificCards(achievement,user);

        // Verificar el resultado
        assertTrue(result, "El usuario debería cumplir el logro con 'SWORD_KETH,SHIELD_KETH'.");
        verify(statisticService, times(1)).hadSwordAndShieldOfKeth(user);
    }

    @Test
    public void testEvaluateWinWithSpecificCards_4Elementals() {
        // Configurar logro con "4_ELEMENTALS" en extraData
        Achievement achievement = new Achievement();
        achievement.setExtraData("4_ELEMENTALS");

        // Configurar mock para que el usuario cumpla el logro
        when(statisticService.had4Elementals(user)).thenReturn(true);

        // Ejecutar el método
        boolean result = achievementService.evaluateWinWithSpecificCards(achievement,user);

        // Verificar el resultado
        assertTrue(result, "El usuario debería cumplir el logro con '4_ELEMENTALS'.");
        verify(statisticService, times(1)).had4Elementals(user);
    }

    @Test
    public void testEvaluateWinWithSpecificCards_InvalidExtraData() {
        // Configurar logro con extraData no reconocida
        Achievement achievement = new Achievement();
        achievement.setExtraData("INVALID");

        // Ejecutar el método
        boolean result = achievementService.evaluateWinWithSpecificCards(achievement,user);

        // Verificar que devuelve false
        assertFalse(result, "El método debería devolver false para extraData no reconocida.");
    }

    @Test
    public void testGetUnlockedAchievements() {
        // Configurar logros desbloqueados para el usuario
        user.getAchievements().add(allAchievements.get(0)); // Gana 10 partidas
        user.getAchievements().add(allAchievements.get(1)); // Juega 1 partida

        // Ejecutar el método
        List<Achievement> unlockedAchievements = achievementService.getUnlockedAchievements(user);

        // Verificar el tamaño de los logros desbloqueados
        assertEquals(2, unlockedAchievements.size(), "El usuario debería tener 2 logros desbloqueados.");

        // Verificar que los logros correctos están desbloqueados
        assertTrue(unlockedAchievements.contains(allAchievements.get(0)), "El usuario debería tener el logro 'Gana 10 partidas'.");
        assertTrue(unlockedAchievements.contains(allAchievements.get(1)), "El usuario debería tener el logro 'Juega 1 partida'.");
    }

    @Test
    public void testGetLockedAchievements() {
        // Configurar logros desbloqueados para el usuario
        user.getAchievements().add(allAchievements.get(0)); // Gana 10 partidas

        // Mock del repositorio para devolver todos los logros
        when(achievementRepository.findAll()).thenReturn(allAchievements);

        // Ejecutar el método
        List<Achievement> lockedAchievements = achievementService.getLockedAchievements(user);

        // Verificar el tamaño de los logros bloqueados
        assertEquals(7, lockedAchievements.size(), "El usuario debería tener 7 logros bloqueados.");

        // Verificar que los logros correctos están bloqueados
        assertFalse(lockedAchievements.contains(allAchievements.get(0)), "El logro 'Gana 10 partidas' no debería estar bloqueado.");
        assertTrue(lockedAchievements.contains(allAchievements.get(1)), "El logro 'Juega 1 partida' debería estar bloqueado.");
        assertTrue(lockedAchievements.contains(allAchievements.get(2)), "El logro 'Obtén una racha de 3 victorias' debería estar bloqueado.");
        assertTrue(lockedAchievements.contains(allAchievements.get(3)), "El logro 'Gana una partida con 180 puntos o más' debería estar bloqueado.");
    }

    @Test
    public void testFindAllAchievements() {
        // Mock del repositorio para devolver todos los logros
        when(achievementRepository.findAll()).thenReturn(allAchievements);

        // Ejecutar el método
        List<Achievement> foundAchievements = achievementService.findAll();

        // Verificar que se devuelven todos los logros
        assertEquals(allAchievements.size(), foundAchievements.size(), "El método debería devolver todos los logros.");
        assertTrue(foundAchievements.containsAll(allAchievements), "Los logros devueltos deberían incluir todos los logros definidos.");
    }

    @Test
    public void testFindAchievementById_Found() {
        // Mock del repositorio para devolver un logro específico
        Achievement achievement = allAchievements.get(0);
        when(achievementRepository.findById(achievement.getId())).thenReturn(Optional.of(achievement));

        // Ejecutar el método
        Achievement foundAchievement = achievementService.findById(achievement.getId());

        // Verificar que se encontró el logro
        assertNotNull(foundAchievement, "El logro debería haberse encontrado.");
        assertEquals(achievement, foundAchievement, "El logro devuelto debería ser el esperado.");
    }

    @Test
    public void testFindAchievementById_NotFound() {
        // Mock del repositorio para devolver un resultado vacío
        when(achievementRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {achievementService.findById(999);});
        
    }

    /*
     * A continuación probamos los test sencillos del servicio para mejorar la cobertura
    */

    @Test
    void testCreateAchievement_ShouldBadRequest() {
    
        // Ejecutar el método y ver que lanza la excepción

        assertThrows(InvalidStatesException.class, () -> {achievementService.createAchievement(newAchievement);});
    }

    @Test
    void testCreateAchievement_ShouldBeCreated() {

        // Cambiamos el tipo al logro para que se pueda crear -> Juega 2 partidas
        newAchievement.setCondition(AchievementCondition.PLAY_N_GAMES);
        newAchievement.setExtraData(null);
        newAchievement.setRequiredValue(2);
        
        when(achievementRepository.save(newAchievement)).thenReturn(newAchievement);

        // Ejecutar el método
        Achievement created = achievementService.createAchievement(newAchievement);

        // Verificar el resultado
        assertNotNull(created, "El logro creado no debería ser nulo.");
        assertEquals("Nuevo logro de prueba", created.getDescription(), "La descripción del logro no coincide.");
        assertEquals(AchievementCondition.PLAY_N_GAMES, created.getCondition(), "La condición del logro no coincide.");

        // Verificar que el repositorio se llamó correctamente
        verify(achievementRepository, times(1)).save(newAchievement);
    }

    @Test
    void testUpdateAchievement() {
        // Simular que el logro existe en el repositorio
        when(achievementRepository.findById(1)).thenReturn(Optional.of(newAchievement));
        when(achievementRepository.save(newAchievement)).thenReturn(updatedAchievement);

        // Ejecutar el método
        Achievement updated = achievementService.updateAchievement(1, updatedAchievement);

        // Verificar el resultado
        assertNotNull(updated, "El logro actualizado no debería ser nulo.");
        assertEquals("Logro actualizado", updated.getDescription(), "La descripción del logro no coincide.");
        assertEquals(AchievementCondition.WIN_WITH_SPECIFIC_CARDS, updated.getCondition(), "La condición del logro no coincide.");
        assertEquals(TierType.INTERMEDIO, updated.getTier(), "El nivel del logro no coincide.");

        // Verificar que el repositorio se llamó correctamente
        verify(achievementRepository, times(1)).findById(1);
        verify(achievementRepository, times(1)).save(newAchievement);
    }

    @Test
    void testDeleteAchievement() {
        // Simular que el logro existe en el repositorio
        when(achievementRepository.findById(1)).thenReturn(Optional.of(newAchievement));

        // Ejecutar el método
        achievementService.deleteAchievement(1);

        // Verificar que el repositorio eliminó el logro
        verify(achievementRepository, times(1)).findById(1);
        verify(achievementRepository, times(1)).delete(newAchievement);
    }

    @Test
    void testDeleteAchievementThrowsExceptionWhenNotFound() {
        // Simular que el logro no existe en el repositorio
        when(achievementRepository.findById(1)).thenReturn(Optional.empty());

        // Ejecutar el método y verificar que lanza una excepción
        assertThrows(ResourceNotFoundException.class, () -> achievementService.deleteAchievement(1), 
            "Debería lanzar ResourceNotFoundException si el logro no existe.");

        // Verificar que el repositorio se llamó correctamente
        verify(achievementRepository, times(1)).findById(1);
        verify(achievementRepository, times(0)).delete(any(Achievement.class));
    }
} 
