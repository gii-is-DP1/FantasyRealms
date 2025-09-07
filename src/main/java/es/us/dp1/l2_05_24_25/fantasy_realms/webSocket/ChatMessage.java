package es.us.dp1.l2_05_24_25.fantasy_realms.webSocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String avatar;
    private String playerName;
    private String message;
}
