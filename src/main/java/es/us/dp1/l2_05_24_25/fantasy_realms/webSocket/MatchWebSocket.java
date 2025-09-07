package es.us.dp1.l2_05_24_25.fantasy_realms.webSocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint("/ws/game/{matchId}")
public class MatchWebSocket {

    // Guardamos las sesiones de los usuarios conectados, identificados por matchId
    private static Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("matchId") String matchId) {
        sessions.put(matchId, session);
        System.out.println("Conexión abierta para el juego: " + matchId);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("matchId") String matchId) {
        // Aquí puedes manejar los mensajes recibidos desde el cliente.
        System.out.println("Mensaje recibido: " + message);
    }

    @OnClose
    public void onClose(Session session, @PathParam("matchId") String matchId) {
        sessions.remove(matchId);
        System.out.println("Conexión cerrada para el juego: " + matchId);
    }

    // Método para enviar mensajes a un cliente específico
    public static void sendMessage(String matchId, String message) throws IOException {
        Session session = sessions.get(matchId);
        if (session != null && session.isOpen()) {
            session.getBasicRemote().sendText(message);
        }
    }
}
