package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.match;

import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.model.BaseEntity;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "game_invitation")
public class GameInvitation extends BaseEntity {

    @NotNull
    @ManyToOne
    private User sender;

    @NotNull
    @ManyToOne
    private User receiver;

    @NotNull
    private boolean status;

    @NotNull
    @ManyToOne
    private Match match;
}
