package com.epam.chat.controllers.mainpage;

import com.epam.chat.ChatConstants;
import com.epam.chat.dao.MessageDAO;
import com.epam.chat.entity.Message;
import com.epam.chat.exeptions.ChatExeption;
import com.epam.chat.mapper.EntityMapper;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet  MessageListController
 *
 * @author Alexander_Filatov
 * Gets messages from dataBase
 */
@Log4j2
@WebServlet(name = "MessageListController", urlPatterns = "/users/messages")
public class MessageListController extends HttpServlet {

    private static final long serialVersionUID = 1;
    private final static int MAX_LENGTH_MESSAGESLIST = 100;
    @Inject
    private MessageDAO messageDAO;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
            IOException {
        String responseMessage;
        EntityMapper mapper = new EntityMapper();
        List<Message> messages;
        try {
            messages = messageDAO
                    .getLastMessages(MAX_LENGTH_MESSAGESLIST);
            responseMessage = mapper.convertToJSON(messages);
            log.debug(MAX_LENGTH_MESSAGESLIST + " messages took from db");
            response.setContentType("application/json");
            response.getWriter().println(responseMessage);
        } catch (ChatExeption e) {
            log.error("Can't get last messages ", e);
            response.sendError(500, ChatConstants.GO_TO_ADMIN);

        }

    }
}
