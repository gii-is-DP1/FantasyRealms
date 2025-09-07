package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.connections;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;


@Service
public class UserConnectionService {

    private UserConnectionRepository userConnectionRepository;

    @Autowired
    public UserConnectionService(UserConnectionRepository userConnectionRepository) {
        this.userConnectionRepository = userConnectionRepository;
    }

    @Transactional
    public void updateConnection(User user) {
        UserConnection connection = userConnectionRepository.findByUser(user)
            .orElseGet(() -> {
                UserConnection newConnection = new UserConnection();
                newConnection.setUser(user);
                return newConnection;
            });
        connection.setLastConnection(Instant.now());
        connection.setOnline(true);
        userConnectionRepository.save(connection);
    }

    @Scheduled(fixedRate = 60000)
    public void checkInactiveUsers() {
        Instant now = Instant.now();
        List<UserConnection> connections = (List<UserConnection>) userConnectionRepository.findAll();

        for (UserConnection connection : connections) {
            if (connection.isOnline() && connection.getLastConnection() != null) {
                Duration timeSinceLastConnection = Duration.between(connection.getLastConnection(), now);
                if (timeSinceLastConnection.toSeconds() > 60) {
                    connection.setOnline(false);
                    userConnectionRepository.save(connection);
                }
            }
        }
    }

    @Transactional(readOnly = true)
    public boolean isUserOnline(User user) {
        return userConnectionRepository.findByUser(user)
            .map(UserConnection::isOnline)
            .orElse(false);
    }

}