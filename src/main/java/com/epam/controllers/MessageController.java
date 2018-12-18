package com.epam.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.epam.dao.DAOFactory;
import com.epam.dao.MessageDAO;
import com.epam.entity.Message;
import lombok.extern.log4j.Log4j2;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Servlet  MessageController
 *
 * @author Alexander_Filatov
 * WebSocket controller, who rules user's messagegs into chat.
 */
@Log4j2
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
            log.error(e.getMessage());
        }

    }

    @OnOpen
    public void onOpen(Session session) {
        log.debug(session + " - session opened");
        DAOFactory dao = DAOFactory.getDAOFactory();
        messageDAO = dao.getMessageDAO();
        synchronized (this) {
            this.session = session;
            chatEndpoints.add(this);
        }


    }

    @OnClose
    public void onClose(Session session) throws IOException {
        log.debug(session + " - session removed");
        chatEndpoints.remove(this);

    }


    private static void broadcast(String message)
            throws IOException, EncodeException {

        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        });
    }


}
