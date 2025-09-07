package es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement,Integer>{
    
}
