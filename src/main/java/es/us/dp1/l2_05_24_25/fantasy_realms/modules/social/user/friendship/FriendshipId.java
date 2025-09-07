package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.friendship;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class FriendshipId {

    private Integer userId;
    private Integer friendId;

    public static FriendshipId create(Integer id1, Integer id2) {
        FriendshipId fid = new FriendshipId();
        fid.setUserId(Math.min(id1, id2));
        fid.setFriendId(Math.max(id1, id2));
        return fid;
    }

}
