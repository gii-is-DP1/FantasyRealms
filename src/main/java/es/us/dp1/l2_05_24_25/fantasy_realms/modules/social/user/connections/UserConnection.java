package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.connections;

import java.time.Instant;

import es.us.dp1.l2_05_24_25.fantasy_realms.model.BaseEntity;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter
@Setter
@Table(name = "user_connection")
public class UserConnection extends BaseEntity {


    @Column(name = "is_online", nullable = false)
    private boolean isOnline;

    @Column(name = "last_heartbeat", nullable = true)
    private Instant lastConnection;

    @OneToOne
    @NotNull
    @JoinColumn(name = "user_id")
    private User user;
    
}
