package com.epam.chat.controllers.autorization;


import com.epam.chat.ChatConstants;
import com.epam.chat.dao.DAOFactory;
import com.epam.chat.dao.UserDAO;
import com.epam.chat.entity.User;
import com.epam.chat.mapper.EntityMapper;
import com.epam.chat.validation.InputsValidator;
import lombok.extern.log4j.Log4j2;
import org.owasp.encoder.Encode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet  LoginController
 *
 * @author Alexander_Filatov
 * Get full user data from db, by login and password. Also show main page.
 */
@Log4j2
@WebServlet(name = "LoginController", urlPatterns = "/login")
public class LoginController extends javax.servlet.http.HttpServlet {
    private static final long serialVersionUID = 1;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
            IOException {

        EntityMapper mapper = new EntityMapper();
        User logUser = checkLoginOpportunities(request, response);

        if (logUser == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            request.getSession()
                    .setAttribute(ChatConstants.SESSION_USER, logUser);
            log.info("Logged user is enter into chat: " + logUser);

            response.setContentType("application/json");
            response.getWriter().write(mapper.convertToJSON(logUser));
        }
    }

    private User checkLoginOpportunities(HttpServletRequest request,
                                         HttpServletResponse response) {
        User logUser = null;
        UserDAO userDAO = (UserDAO) request.getServletContext()
                .getAttribute(ChatConstants.USER_DAO);
        EntityMapper mapper = new EntityMapper();
        User user = mapper.getUser(request);

        if (!validateUser(user)) {
            log.debug("User is invalid");
            try {
                response.sendError(406);
            } catch (IOException e) {
                log.error("IOException with sendError method", e);
            }

        } else if (!userDAO.isLogged(user)) {
            try {
                log.debug("User with this login already exist");
                response.sendError(409);
            } catch (IOException e) {
                log.error("IOException with sendError method", e);
            }

        } else if (!userDAO.checkLogIn(user)) {
            try {
                log.debug("User login and password  are wrong");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } catch (IOException e) {
                log.error("IOException with sendError method", e);
            }

        } else {
            logUser = user;
        }
        return logUser;
    }

    private boolean validateUser(User user) {
        return new InputsValidator().validateUser(user);
    }

}
