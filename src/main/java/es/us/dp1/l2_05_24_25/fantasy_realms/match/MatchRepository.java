package es.us.dp1.l2_05_24_25.fantasy_realms.match;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

    @Query("SELECT m FROM Match m WHERE m.startDate IS null and m.endDate IS null")
    Page<Match> findMatchesPlayable(Pageable pageable);

    @Query("SELECT m FROM Match m WHERE m.startDate IS NOT null AND m.endDate IS null")
    Page<Match> findMatchesInProgress(Pageable pageable);

    @Query("SELECT m FROM Match m WHERE m.startDate IS NOT null AND m.endDate IS NOT null")
    Page<Match> findMatchesFinished(Pageable pageable);

    @Query("SELECT p.matchPlayed FROM Player p WHERE p.user.id = :userId")
    List<Match> findMatchesWhereUserParticipated(@Param("userId") Integer userId, Pageable pageable);

    // modulo estadistica

    @Query("SELECT m FROM Match m JOIN m.players p WHERE p.user = :user")
    List<Match> findByPlayers_User(@Param("user") User user);

    @Query("SELECT m FROM Match m WHERE m.startDate IS NOT NULL AND m.endDate IS NULL")
    List<Match> findAllMatchesInProgress();

}
