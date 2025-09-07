package es.us.dp1.l2_05_24_25.fantasy_realms.achievements;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.Achievement;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.AchievementCondition;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.AchievementType;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.TierType;

public class AchievementTests {

    private Achievement achievement;

    @BeforeEach
    void setUp() {
        achievement = new Achievement(
            1,
            10,
            TierType.FACIL,
            "This is a test achievement description.",
            "KING,QUEEN",
            "test_icon.png",
            AchievementCondition.WIN_WITH_SPECIFIC_CARDS,
            AchievementType.HABILIDAD
        );
        achievement.setDateAchieved(LocalDateTime.  now());
    }

    @Test
    void testAchievementCreation() {
        assertNotNull(achievement);
        assertEquals(1, achievement.getId());
        assertEquals(10, achievement.getRequiredValue());
        assertEquals(TierType.FACIL, achievement.getTier());
        assertEquals("This is a test achievement description.", achievement.getDescription());
        assertEquals("KING,QUEEN", achievement.getExtraData());
        assertEquals("test_icon.png", achievement.getIcon());
        assertEquals(AchievementCondition.WIN_WITH_SPECIFIC_CARDS, achievement.getCondition());
        assertEquals(AchievementType.HABILIDAD, achievement.getType());
        assertNotNull(achievement.getDateAchieved());
    }

}

