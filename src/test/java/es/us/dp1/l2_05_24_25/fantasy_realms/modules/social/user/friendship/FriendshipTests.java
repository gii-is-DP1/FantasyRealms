package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.friendship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;

public class FriendshipTests {

    private Friendship friendship;
    private User user1;
    private User user2;
    private FriendshipId friendshipId;

    @BeforeEach
    void setUp() {

        user1 = mock(User.class);
        user2 = mock(User.class);
        friendshipId = mock(FriendshipId.class);


        friendship = new Friendship();
        friendship.setId(friendshipId);
        friendship.setUser(user1);
        friendship.setFriend(user2);
        friendship.setStatus("PENDING");
        friendship.setSenderId(1);
        friendship.setReceiverId(2);
    }

    @Test
    void testCreateFriendship() {
        assertNotNull(friendship);
        assertEquals(user1, friendship.getUser());
        assertEquals(user2, friendship.getFriend());
        assertEquals("PENDING", friendship.getStatus());
        assertEquals(1, friendship.getSenderId());
        assertEquals(2, friendship.getReceiverId());
    }

    @Test
    void testStatusChange() {

        friendship.setStatus("ACCEPTED");

        assertEquals("ACCEPTED", friendship.getStatus());
    }

    @Test
    void testSenderAndReceiver() {

        assertEquals(1, friendship.getSenderId());
        assertEquals(2, friendship.getReceiverId());
    }

    @Test
    void testFriendshipId() {

        assertEquals(friendshipId, friendship.getId());
    }

    @Test
    void testUserAndFriend() {

        assertEquals(user1, friendship.getUser());
        assertEquals(user2, friendship.getFriend());
    }
}
