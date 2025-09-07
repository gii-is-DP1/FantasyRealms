package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.match;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameInvitationDTO {

    private String senderUsername;
    private String receiverUsername;
    private boolean status;
    private Integer matchId;

    public GameInvitationDTO() {}

    public GameInvitationDTO(String senderUsername, String receiverUsername, boolean status, Integer matchId) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.status = status;
        this.matchId = matchId;
    }

}
