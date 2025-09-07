package es.us.dp1.l2_05_24_25.fantasy_realms.websocket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.us.dp1.l2_05_24_25.fantasy_realms.webSocket.MatchWebSocket;
import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;

public class MatchWebSocketTests {
    private MatchWebSocket matchWebSocket;

    @Mock
    private Session mockSession;

    @Mock
    private RemoteEndpoint.Basic mockRemoteEndpoint;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        matchWebSocket = new MatchWebSocket();
    }

    @Test
    public void testOnOpen() {
        String matchId = "testMatchId";

        matchWebSocket.onOpen(mockSession, matchId);

    }

    @Test
    public void testOnMessage() {
        String matchId = "testMatchId";
        String message = "Test message";

        matchWebSocket.onMessage(message, matchId);

        
    }

    @Test
    public void testOnClose() {
        String matchId = "testMatchId";

        matchWebSocket.onClose(mockSession, matchId);

        
    }

}
