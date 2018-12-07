package controllers;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

//TODO Add java doc

@ServerEndpoint("/websocket")
public class MessageController {
 private Session session;
 private static Set<MessageController> chatEndpoints = new CopyOnWriteArraySet<>();

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("User input: " + message);
        try {
            broadcast(message);
        } catch (EncodeException e) {
            e.printStackTrace();
        }


    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Client connected");
        this.session = session;
        chatEndpoints.add(this);
    }

    @OnClose
    public void onClose() {
        System.out.println("Connection closed");
    }


    private static void broadcast(String message)
            throws IOException, EncodeException {

        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().sendText(message);

                } catch (IOException  e) {
                    e.printStackTrace();
                }
            }
        });
    }




}
