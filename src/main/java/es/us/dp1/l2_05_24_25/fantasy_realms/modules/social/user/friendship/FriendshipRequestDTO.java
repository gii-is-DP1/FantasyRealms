package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.friendship;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendshipRequestDTO {

    @JsonProperty("senderId")
    private Integer senderId;
    private String senderName;
    @JsonProperty("receiverId")
    private Integer receiverId;
    private String receiverName;
    private String status;
    private boolean senderIsOnline;
    private boolean receiverIsOnline;

    public FriendshipRequestDTO() {}
    
    public FriendshipRequestDTO(Integer senderId, String senderName,
                         Integer receiverId, String receiverName,
                         String status) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.status = status;
    }
}