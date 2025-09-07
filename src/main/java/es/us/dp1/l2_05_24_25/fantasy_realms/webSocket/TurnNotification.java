package es.us.dp1.l2_05_24_25.fantasy_realms.webSocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TurnNotification {

    private String message;

    public TurnNotification() {}

    public TurnNotification(String message) {
        this.message = message;
    }
    
}
