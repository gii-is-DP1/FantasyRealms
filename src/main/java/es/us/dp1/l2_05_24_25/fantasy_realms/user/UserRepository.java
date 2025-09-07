package es.us.dp1.l2_05_24_25.fantasy_realms.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface UserRepository extends  JpaRepository<User, Integer>{			


	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Optional<User> findById(Integer id);
	
	@Query("SELECT u FROM User u WHERE u.authority.authority = :auth")
    Page<User> findAllByAuthority(String auth, Pageable pageable);

	
	//@Query("DELETE FROM Player o WHERE o.user.id = :userId")
	//@Modifying
	//void deletePlayerRelation(int userId);
	
	@Query("SELECT u FROM User u JOIN u.achievements a WHERE a.id = :achievementId")
	List<User> findUsersWithAchievement(@Param("achievementId") Integer achievementId);

	
}
