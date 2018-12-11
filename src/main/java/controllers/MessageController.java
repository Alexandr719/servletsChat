package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DAOFactory;
import dao.MessageDAO;
import entity.Message;
import entity.User;
import mapper.EntityMapper;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

//TODO Add java doc

@ServerEndpoint("/websocket")
public class MessageController {
    private MessageDAO messageDAO;
    private Session session;
    private static Set<MessageController> chatEndpoints = new CopyOnWriteArraySet<>();



    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        ObjectMapper om = new ObjectMapper();

        try {
            Message msg = om.readValue(message, Message.class);
            messageDAO.sentMessage(msg);
            broadcast(msg.getUser().getLogin() + " : " + msg.getMessage());
        } catch (IOException | EncodeException e) {
            //TODO log
            e.printStackTrace();
        }

    }

    @OnOpen
    public void onOpen(Session session) {
        DAOFactory dao = DAOFactory.getDAOFactory();
        messageDAO = dao.getMessageDAO();

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

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
