package com.epam.chat.controllers.autorization;


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

        if(logUser == null){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            request.getSession().setAttribute("user", logUser);
            log.info("User is enter into chat: " + logUser);

            response.setContentType("application/json");
            response.getWriter().write(mapper.convertToJSON(logUser));
        }
    }

    private User checkLoginOpportunities(HttpServletRequest request,
                                         HttpServletResponse response) {
        User logUser = null;
        UserDAO userDAO = (UserDAO) request.getServletContext()
                .getAttribute("userDAO");
        EntityMapper mapper = new EntityMapper();
        User user = mapper.getUser(request);

        if (validateUser(user) && userDAO.isLogged(user)
                && userDAO.checkLogIn(user)) {
            log.debug("User login and password are right");
            logUser = user;
        } else {
            //toDo another error
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } catch (IOException e) {
                log.error("IOException with sendError method", e);
            }
            log.debug("User login and password  are wrong");
        }
        return logUser;
    }

    private boolean validateUser(User user) {
        return new InputsValidator().validateUser(user);
    }

}
