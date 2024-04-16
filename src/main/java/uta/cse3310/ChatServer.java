package uta.cse3310;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")

public class ChatServer {

    private static Set<Session> sessions = new HashSet<>();

    @OnOpen
    public void onOpen(Session session) {
        // Add the new session to the set of sessions
        sessions.add(session);
        // Notify all clients that a new user has joined
        broadcast("User connected: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // Broadcast the received message to all clients
        broadcast(session.getId() + ": " + message);
    }

    @OnClose
    public void onClose(Session session) {
        // Remove the session from the set of sessions
        sessions.remove(session);
        // Notify all clients that a user has disconnected
        broadcast("User disconnected: " + session.getId());
    }

    private static void broadcast(String message) {
        for (Session session : sessions) {
            // Send the message to each client session
            session.getAsyncRemote().sendText(message);
        }
    }
}
