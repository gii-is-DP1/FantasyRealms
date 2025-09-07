package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.connections;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;

public interface UserConnectionRepository extends JpaRepository<UserConnection, Integer> {
    Optional<UserConnection> findByUser(User user);
}
