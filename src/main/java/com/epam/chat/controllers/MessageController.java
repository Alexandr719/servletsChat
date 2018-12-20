package com.epam.chat.controllers;

import com.epam.chat.dao.DAOFactory;
import com.epam.chat.dao.MessageDAO;
import com.epam.chat.entity.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
    //Todo not static
    private static Set<MessageController> chatEndpoints = Collections
            .synchronizedSet(new HashSet<>());


    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        ObjectMapper om = new ObjectMapper();

        try {
            Message msg = om.readValue(message, Message.class);
            messageDAO.sentMessage(msg);
            broadcast(msg.getUser().getLogin() + " : " + msg.getMessage());
        } catch (IOException | EncodeException e) {
            log.error(e);
        }

    }

    @OnOpen
    public void onOpen(Session session) {
        log.debug(session + " - session opened");
        DAOFactory dao = DAOFactory.getDAOFactory();
        messageDAO = dao.getMessageDAO();
        //Todo
        synchronized (this) {
            this.session = session;
            chatEndpoints.add(this);
            log.debug("Queue contains " + chatEndpoints.size() + " elements");
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        log.debug(session + " - session removed");
        chatEndpoints.remove(this);
        log.debug("Queue contains " + chatEndpoints.size() + " elements");

    }


    private static void broadcast(String message)
            throws IOException, EncodeException {

        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    log.error(e);
                }
            }
        });
    }


}
