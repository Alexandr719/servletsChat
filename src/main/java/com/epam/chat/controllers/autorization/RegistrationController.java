package com.epam.chat.controllers.autorization;


import com.epam.chat.ChatConstants;
import com.epam.chat.dao.UserDAO;
import com.epam.chat.entity.User;
import com.epam.chat.mapper.EntityMapper;
import com.epam.chat.validation.InputsValidator;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet  RegistrationController
 *
 * @author Alexander_Filatov
 * Add new user into db, show main page
 */
@Log4j2
@WebServlet(name = "RegistrationController", urlPatterns = "/registration")
public class RegistrationController extends HttpServlet {

    private static final long serialVersionUID = 1;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws
            ServletException, IOException {

        EntityMapper mapper = new EntityMapper();
        User user = checkRegisteredOpportunities(request, response);
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            request.getSession().setAttribute(ChatConstants.SESSION_USER, user);
            log.info("User is enter into chat: " + user);

            response.setContentType("application/json");
            response.getWriter().write(mapper.convertToJSON(user));
        }

    }

    private User checkRegisteredOpportunities(HttpServletRequest request,
                                              HttpServletResponse response) {
        User regUser = null;
        UserDAO userDAO = (UserDAO) request.getServletContext()
                .getAttribute(ChatConstants.USER_DAO);
        EntityMapper mapper = new EntityMapper();
        User user = mapper.getUser(request);


        if (validateUser(user) && !userDAO.isLogged(user)) {
            log.debug("User with this login doesn't exist");
            userDAO.login(user);
            regUser = user;
            log.debug("Success registration");
        } else {
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } catch (IOException e) {
                log.error("IOException with sendError method", e);
            }
        }
        return regUser;
    }

    private boolean validateUser(User user) {
        return new InputsValidator().validateUser(user);
    }

}

