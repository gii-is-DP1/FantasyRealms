package es.us.dp1.l2_05_24_25.fantasy_realms.dto;

import es.us.dp1.l2_05_24_25.fantasy_realms.turn.DrawEnum;
import es.us.dp1.l2_05_24_25.fantasy_realms.turn.Turn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TurnDTO {

    private String username;

    private Integer turnCount;

    private DrawEnum drawSource;

    public TurnDTO() {}

    public TurnDTO(Turn other) {
        if (other != null) {
            this.username = other.getPlayer().getUser().getUsername();
            this.turnCount = other.getTurnCount();
            this.drawSource = other.getDrawSource();
        }
    }

    @Override
    public String toString() {
        return "TurnDTO{" +
                "username=" + username +
                ", turnCount=" + turnCount +
                ", drawSource=" + drawSource +
                '}';
    }
    
}
