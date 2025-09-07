package es.us.dp1.l2_05_24_25.fantasy_realms.websocket;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.us.dp1.l2_05_24_25.fantasy_realms.webSocket.ChatMessage;

public class ChatMessageTests {

    private ChatMessage chatMessage;

    @BeforeEach
    public void setUp() {
        chatMessage = new ChatMessage();
    }

    @Test
    public void testAvatarGetterSetter() {
        String avatar = "avatar.png";
        chatMessage.setAvatar(avatar);
        assertEquals(avatar, chatMessage.getAvatar(), "The avatar should be set correctly.");
    }

    @Test
    public void testPlayerNameGetterSetter() {
        String playerName = "player1";
        chatMessage.setPlayerName(playerName);
        assertEquals(playerName, chatMessage.getPlayerName(), "The player name should be set correctly.");
    }

    @Test
    public void testMessageGetterSetter() {
        String message = "Hello, world!";
        chatMessage.setMessage(message);
        assertEquals(message, chatMessage.getMessage(), "The message should be set correctly.");
    }
}
