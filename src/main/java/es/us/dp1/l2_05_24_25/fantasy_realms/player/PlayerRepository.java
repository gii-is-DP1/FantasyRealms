package es.us.dp1.l2_05_24_25.fantasy_realms.player;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {

    // Buscar Player a partir del usuario (sirve para obtener el player asociado al current user)

    @Query("SELECT p FROM Player p WHERE p.user.id = :id")
    Optional<Player> findByUserId(Integer id);

    // Encontrar la lista de partidas creadas por un usuario a partir de matchPlayed

    @Query("SELECT p.matchPlayed FROM Player p WHERE p.user.id = :userId AND p.role = 'CREADOR'")
    List<Match> findMatchesCreatedByUser(@Param("userId") Integer userId);

    @Query("SELECT p.role FROM Player p WHERE p.matchPlayed.id = :matchId AND p.user.username = :username")
    PlayerType findRoleByMatchIdAndUsername(@Param("matchId") Integer matchId, @Param("username") String username);

    // modulo estadisticas

    Integer countByUser(User user);

    List<Player> findByUser(User user);

}
