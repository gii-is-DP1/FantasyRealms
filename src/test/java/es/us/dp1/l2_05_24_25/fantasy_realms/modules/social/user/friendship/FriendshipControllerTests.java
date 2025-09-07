package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.friendship;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.FilterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.connections.UserConnectionService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;

@WebMvcTest(controllers = FriendshipController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
public class FriendshipControllerTests {

    @SuppressWarnings("unused")
    @Autowired
    private FriendshipController friendshipController;

    @MockBean
    private FriendshipService friendshipService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserConnectionService userConnectionService;

    @Autowired
    private MockMvc mockMvc;

    private User currentUser;
    private User friend;
    private User sender;
    private User receiver;
    private Friendship friendship;

    @BeforeEach
    void setUp() {

        currentUser = new User();
        currentUser.setId(1);
        currentUser.setUsername("currentUser");

        friend = new User();
        friend.setId(2);
        friend.setUsername("friendUser");

        sender = new User();
        sender.setId(2);
        sender.setUsername("senderUser");

        receiver = new User();
        receiver.setId(3);
        receiver.setUsername("receiverUser");

        friendship = new Friendship();
        friendship.setId(FriendshipId.create(1, 2));
        friendship.setSenderId(1);
        friendship.setReceiverId(2);
        friendship.setStatus("PENDING");
        friendship.setUser(currentUser);
        friendship.setFriend(friend);

    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    void testGetFriends() throws Exception {

        friendship.setStatus("ACCEPTED");
        when(userService.findCurrentUser()).thenReturn(currentUser);
        when(friendshipService.getFriends(1)).thenReturn(List.of(friendship));
        when(userConnectionService.isUserOnline(currentUser)).thenReturn(true);
        when(userConnectionService.isUserOnline(friend)).thenReturn(false);
    
        mockMvc.perform(get("/api/v1/friendships/friends")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("ACCEPTED"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    void testGetAllFriendships() throws Exception {

        friendship.setUser(sender);
        friendship.setFriend(receiver);
        friendship.setStatus("PENDING");

        when(userService.findCurrentUser()).thenReturn(currentUser);
        when(friendshipService.getAllFriendshipsForUser(currentUser.getId())).thenReturn(List.of(friendship));
        when(userConnectionService.isUserOnline(sender)).thenReturn(true);
        when(userConnectionService.isUserOnline(receiver)).thenReturn(false);

        mockMvc.perform(get("/api/v1/friendships/all")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].senderId").value(1))
                .andExpect(jsonPath("$[0].senderName").value("senderUser"))
                .andExpect(jsonPath("$[0].receiverId").value(2))
                .andExpect(jsonPath("$[0].receiverName").value("receiverUser"))
                .andExpect(jsonPath("$[0].status").value("PENDING"))
                .andExpect(jsonPath("$[0].senderIsOnline").value(true))
                .andExpect(jsonPath("$[0].receiverIsOnline").value(false));
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    void testDeleteFriendship() throws Exception {

        when(userService.findCurrentUser()).thenReturn(currentUser);

        mockMvc.perform(delete("/api/v1/friendships/delete")
                .with(csrf())
                .param("friendId", "2"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    void testGetPendingFriendships() throws Exception {

        FriendshipRequestDTO pendingFriendship = new FriendshipRequestDTO(1, "currentUser", 2, "friendUser", "PENDING");
        when(userService.findCurrentUser()).thenReturn(currentUser);
        when(friendshipService.getFriendshipsForUserByStatus(1, "PENDING")).thenReturn(List.of(pendingFriendship));

        mockMvc.perform(get("/api/v1/friendships/pending")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("PENDING"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    void testSendFriendRequest() throws Exception {
        
        when(userService.findCurrentUser()).thenReturn(currentUser);
        when(userService.findUser("friendUser")).thenReturn(friend);
        when(friendshipService.sendFriendRequest(1, 2)).thenReturn(friendship);
        when(userService.findUser(1)).thenReturn(currentUser);
        when(userService.findUser(2)).thenReturn(friend);
    
        mockMvc.perform(post("/api/v1/friendships/request")
                .with(csrf())
                .param("receiverUsername", "friendUser")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.senderId").value(1))
                .andExpect(jsonPath("$.senderName").value("currentUser"))
                .andExpect(jsonPath("$.receiverId").value(2))
                .andExpect(jsonPath("$.receiverName").value("friendUser"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    
        verify(friendshipService).sendFriendRequest(1, 2);
    }
    
    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    void testAcceptFriendRequest() throws Exception {

        friendship.setStatus("ACCEPTED");
        friendship.setReceiverId(3);
        friendship.setSenderId(1);

        when(userService.findCurrentUser()).thenReturn(receiver);
        when(friendshipService.acceptFriendRequest(1, 3)).thenReturn(friendship);
        when(userService.findUser(1)).thenReturn(sender);
        when(userService.findUser(3)).thenReturn(receiver);

        mockMvc.perform(post("/api/v1/friendships/accept")
                .with(csrf())
                .param("senderId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.senderId").value(1))
                .andExpect(jsonPath("$.senderName").value("senderUser"))
                .andExpect(jsonPath("$.receiverId").value(3))
                .andExpect(jsonPath("$.receiverName").value("receiverUser"))
                .andExpect(jsonPath("$.status").value("ACCEPTED"));

        verify(userService).findCurrentUser();
        verify(friendshipService).acceptFriendRequest(1, 3);
        verify(userService).findUser(1);
        verify(userService).findUser(3);
    }

}
