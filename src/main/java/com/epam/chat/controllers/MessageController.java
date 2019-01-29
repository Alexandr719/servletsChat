package com.epam.chat.controllers;

import com.epam.chat.ChatConstants;
import com.epam.chat.dao.DAOFactory;
import com.epam.chat.dao.MessageDAO;
import com.epam.chat.dao.OracleDAOFactory;
import com.epam.chat.entity.Message;
import com.epam.chat.exeptions.ChatExeption;
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
@ServerEndpoint("/user/message")
public class MessageController {

    private MessageDAO messageDAO;
    private Session session;
    private static Set<MessageController> chatEndpoints = Collections
            .synchronizedSet(new HashSet<>());


    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        ObjectMapper om = new ObjectMapper();
        try {
            Message msg = om.readValue(message, Message.class);
            System.out.println(msg);
            messageDAO.sentMessage(msg);
            broadcast(msg.getUser().getLogin() + " : " + msg.getMessage());
        } catch (ChatExeption e) {
            log.error("Error with sending message", e);
            session.getBasicRemote().sendText(ChatConstants.GO_TO_ADMIN);
        }
    }


    @OnOpen
    public void onOpen(Session session) {
        DAOFactory dao = new OracleDAOFactory();
        messageDAO = dao.getMessageDAO();
        log.debug(session + " - session opened");
        this.session = session;
        chatEndpoints.add(this);
        log.debug("Queue contains " + chatEndpoints.size() + " elements");

    }

    @OnClose
    public void onClose(Session session) throws IOException {
        log.debug(session + " - session removed");
        chatEndpoints.remove(this);
        log.debug("Queue contains " + chatEndpoints.size() + " elements");

    }
    private static void broadcast(String message)
            {
        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    log.error("Error with notify", e);

                }
            }
        });
    }


}
