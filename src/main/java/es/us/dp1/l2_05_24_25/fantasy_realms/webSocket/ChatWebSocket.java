package es.us.dp1.l2_05_24_25.fantasy_realms.webSocket;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint("/ws/chat")
public class ChatWebSocket {

    private static Set<Session> chatEndpoints = new CopyOnWriteArraySet<>();
    private static ObjectMapper objectMapper = new ObjectMapper();

    @OnOpen
    public void onOpen(Session session) {
        chatEndpoints.add(session);
        System.out.println("Nueva conexión de chat: " + session.getId());
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        try {
            ChatMessage chatMessage = objectMapper.readValue(message, ChatMessage.class);
            String broadcastMessage = objectMapper.writeValueAsString(chatMessage);
            broadcast(broadcastMessage);
        } catch (IOException e) {
            System.err.println("Error al procesar el mensaje: " + e.getMessage());
        }
    }


    @OnClose
    public void onClose(Session session) {
        chatEndpoints.remove(session);
        System.out.println("Conexión de chat cerrada: " + session.getId());
    }

    private void broadcast(String message) {
        for (Session session : chatEndpoints) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    System.err.println("Error al enviar mensaje: " + e.getMessage());
                }
            }
        }
    }
}
