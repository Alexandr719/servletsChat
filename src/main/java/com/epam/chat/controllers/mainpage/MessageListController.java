package com.epam.chat.controllers.mainpage;

import com.epam.chat.dao.DAOFactory;
import com.epam.chat.dao.MessageDAO;
import com.epam.chat.dao.UserDAO;
import com.epam.chat.entity.Message;
import com.epam.chat.mapper.EntityMapper;
import lombok.extern.log4j.Log4j2;
import org.owasp.encoder.Encode;

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
@WebServlet(name = "MessageListController", urlPatterns = "/getmessages")
public class MessageListController extends HttpServlet {

    private static final long serialVersionUID = 1;
    private final static int MAX_LENGTH_MESSAGESLIST = 100;
    private MessageDAO messageDAO;

    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
            IOException {

         EntityMapper mapper = new EntityMapper();
        List<Message> messages = messageDAO
                .getLastMessages(MAX_LENGTH_MESSAGESLIST);
        log.debug(MAX_LENGTH_MESSAGESLIST + " messages took from db");

        response.setContentType("application/json");
        response.getWriter().println(mapper.convertToJSON(messages));
    }



    @Override
    public void init() throws ServletException {
        DAOFactory dao = DAOFactory.getDAOFactory();
        messageDAO = dao.getMessageDAO();
    }

}
