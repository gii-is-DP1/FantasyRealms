package es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.InvalidStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.MatchFinishedEvent;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.StatisticService;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;

@Service
public class AchievementService {

    private AchievementRepository achievementRepository;

    private StatisticService statisticService;

    private UserService userService;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository, StatisticService statisticService,UserService userService) {

        this.achievementRepository = achievementRepository;
        this.statisticService = statisticService;
        this.userService = userService;

    }

    // Escucha los eventos de "partida finalizada", y actualiza los logros de los jugadores
    @EventListener
    public void onMatchFinished(MatchFinishedEvent event) {
        Match finishedMatch = event.getMatch();

        // Por cada jugador, verifica y desbloquea logros
        for (Player player : finishedMatch.getPlayers()) {
            User user = player.getUser();
            checkAndUnlockAchievements(user);
        }
    }

    /**
     * Verifica todos los logros y añade a la lista de achievements del User 
     * aquellos que cumpla y aún no posea.
     */
    @Transactional
    public void checkAndUnlockAchievements(User user) {
        // 1. Obtener todos los logros
        List<Achievement> allAchievements = new ArrayList<>();
        achievementRepository.findAll().forEach(allAchievements::add);

        // 2. Para cada logro, verificar si el usuario ya lo tiene
        for (Achievement achievement : allAchievements) {
            boolean alreadyUnlocked = user.getAchievements().contains(achievement);
            if (!alreadyUnlocked) {
                // 3. Comprobar si ahora lo cumple
                boolean unlocked = isAchievementUnlocked(achievement, user);
                if (unlocked) {
                    // 4. Añadir el logro a la lista del user
                    user.getAchievements().add(achievement);
                }
            }
        }

        // 5. Guardar el user con sus nuevos logros
        userService.saveUserBasic(user);
    }

    /**
     * Lógica de verificación de cada logro, llamando a StatisticService.
     */
    public boolean isAchievementUnlocked(Achievement achievement, User user) {
        switch (achievement.getCondition()) {
            case WIN_N_GAMES:
                long totalWins = statisticService.getTotalWins(user);
                return totalWins >= achievement.getRequiredValue();

            case PLAY_N_GAMES:
                int totalMatches = statisticService.getTotalMatches(user);
                return totalMatches >= achievement.getRequiredValue();

            case WIN_STREAK:
                int streak = statisticService.getCurrentWinStreak(user);
                return streak >= achievement.getRequiredValue();

            case WIN_WITH_SPECIFIC_CARDS:
                // Se parsea achievement.getExtraData() para combos de carta: “KING,QUEEN”, etc.
                return evaluateWinWithSpecificCards(achievement, user);
            case WIN_WITH_MIN_POINTS:
                int score = statisticService.getPointsLastMatch(user);
                return score >= achievement.getRequiredValue();
            case WIN_AFTER_LAST_PLACE:
                return statisticService.lastGameWasFromLastPlace(user);
            case PLAY_N_CARDS_OF_TYPE:   
                int total = statisticService.getTotalCardsPlayedByType(user, achievement.getExtraData().toUpperCase());
                return total >= achievement.getRequiredValue();
            case WIN_WITH_NO_RARE_CARDS:
                return statisticService.hadNoRareCardsInLastGame(user);
            default:
                return false;
        }
    }

    /**
     * Método que evalúa los logros de tipo WIN_WITH_SPECIFIC_CARDS, parseando extraData para obtener qué logro exacto es y aplicarlo
     */
    public boolean evaluateWinWithSpecificCards(Achievement achievement, User user) {
        if ("KING,QUEEN".equalsIgnoreCase(achievement.getExtraData())) {
            return statisticService.hadKingAndQueenInLastGame(user);
        } else if ("BEASTMASTER,3_BEASTS".equalsIgnoreCase(achievement.getExtraData())) {
            return statisticService.hadBeastmasterAnd3Beasts(user);
        } else if("SWORD_KETH,SHIELD_KETH".equalsIgnoreCase(achievement.getExtraData())) {
            return statisticService.hadSwordAndShieldOfKeth(user);
        } else if("4_ELEMENTALS".equalsIgnoreCase(achievement.getExtraData())) {
            return statisticService.had4Elementals(user);
        }
        return false;
    }

    /**
     * Devuelve la lista de logros que ha conseguido el usuario
     */
    @Transactional(readOnly = true)
    public List<Achievement> getUnlockedAchievements(User user) {
        // Simplemente la lista en user.achievements
        return new ArrayList<>(user.getAchievements());
    }

    /**
     * Devuelve la lista de logros que no ha conseguido el usuario
     */
    @Transactional(readOnly = true)
    public List<Achievement> getLockedAchievements(User user) {
        List<Achievement> allAchievements = new ArrayList<>();
        achievementRepository.findAll().forEach(allAchievements::add);
        List<Achievement> unlocked = getUnlockedAchievements(user);
        return allAchievements.stream()
                .filter(a -> !unlocked.contains(a))
                .collect(Collectors.toList());
    }

    /**
     * Devuelve todos los logros encontrados
     */
    @Transactional(readOnly = true)
    public List<Achievement> findAll() {
        Iterable<Achievement> aFound = achievementRepository.findAll();
        List<Achievement> aList = new ArrayList<>();
        aFound.forEach(a -> aList.add(a));
        return aList;
    }

    /**
     * Devuelve un logro dado su ID
     */
    @Transactional(readOnly = true)
    public Achievement findById(Integer id) {
        Achievement existing = achievementRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Achievement", "id", id));
        return existing;
    }

    /**
     * Crea un nuevo logro y lo persiste en la base de datos.
     */
    @Transactional
    public Achievement createAchievement(Achievement achievement) throws DataAccessException {

        // Validar que el tipo de logro es permitido
        validateAchievementType(achievement.getCondition());

        return achievementRepository.save(achievement);
    }

    /**
     * Valida que el tipo de logro sea permitido.
     *
     * @param condition El tipo de logro (AchievementCondition)
     * @throws InvalidAchievementTypeException si el tipo no está permitido
     */
    private void validateAchievementType(AchievementCondition condition) {
        List<AchievementCondition> forbiddenConditions = List.of(
            AchievementCondition.WIN_WITH_SPECIFIC_CARDS,
            AchievementCondition.WIN_AFTER_LAST_PLACE,
            AchievementCondition.WIN_WITH_NO_RARE_CARDS
        );

        if (forbiddenConditions.contains(condition)) {
            throw new InvalidStatesException(
                "Cannot create achievement with type: " + condition
            );
        }
    }

    /**
     * Actualiza los campos de un logro existente. Retorna el logro actualizado.
     */
    @Transactional
    public Achievement updateAchievement(Integer achievementId, Achievement updatedData) {
        Achievement existing = findById(achievementId);
        
        // Actualizar campos (dateAchieved y icon no puede ser modificado)
        existing.setDescription(updatedData.getDescription());
        existing.setCondition(updatedData.getCondition());
        existing.setRequiredValue(updatedData.getRequiredValue());
        existing.setExtraData(updatedData.getExtraData());
        existing.setTier(updatedData.getTier());
        existing.setType(updatedData.getType());

        return achievementRepository.save(existing);
    }

    /**
     * Borra un logro por su ID
     */
    @Transactional
    public void deleteAchievement(Integer achievementId) {

        List<User> usersWithAchievement = userService.findUsersWithAchievement(achievementId);

        // Desvincular el logro de cada usuario
        usersWithAchievement.forEach(user -> 
            user.getAchievements().removeIf(ach -> ach.getId().equals(achievementId))
        );

        Achievement existing = achievementRepository.findById(achievementId)
            .orElseThrow(() -> new ResourceNotFoundException("Achievement", "id", achievementId));
        achievementRepository.delete(existing);
    }
    
}
