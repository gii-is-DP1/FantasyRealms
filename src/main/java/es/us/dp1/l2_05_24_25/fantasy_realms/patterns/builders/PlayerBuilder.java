package es.us.dp1.l2_05_24_25.fantasy_realms.patterns.builders;

import org.jpatterns.gof.BuilderPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerType;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * "PlayerBuilder" - construye objetos Player.
 */
@Getter
@Setter
@Accessors(chain = true) // Para que los setter retornen PlayerBuilder y no void, y funcione
@BuilderPattern.ConcreteBuilder
public class PlayerBuilder implements Builder<Player>{

    private User user;
    private PlayerType role;

    @Override
    public Player build() {
        return new Player(user, role);
    }
    
    
}
