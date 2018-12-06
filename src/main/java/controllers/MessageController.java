package controllers;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

//TODO Add java doc

@ServerEndpoint("/websocket")
public class MessageController {


    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("User input: " + message);
        session.getBasicRemote().sendText(message);


    }

    @OnOpen
    public void onOpen() {
        System.out.println("Client connected");
    }

    @OnClose
    public void onClose() {
        System.out.println("Connection closed");
    }
}
