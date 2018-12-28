package com.epam.chat.controllers.autorization;


import com.epam.chat.ChatConstants;
import com.epam.chat.dao.DAOFactory;
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
    private UserDAO userDAO;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws
            ServletException, IOException {

        EntityMapper mapper = new EntityMapper();
        User user = checkRegisteredOpportunities(request, response);
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            request.getSession().setAttribute(ChatConstants.SESSION_USER, user);
            log.info("New user is enter into chat: " + user);

            response.setContentType("application/json");
            response.getWriter().write(mapper.convertToJSON(user));
        }

    }

    private User checkRegisteredOpportunities(HttpServletRequest request,
                                              HttpServletResponse response) {
        User regUser = null;
        EntityMapper mapper = new EntityMapper();
        User user = mapper.getUser(request);


        if (!validateUser(user)) {
            log.debug("User didn't pass validation");
            try {
                response.sendError(778, "User didn't pass validation");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (userDAO.isUserExist(user)) {
            log.debug("User with this login already exist");
            try {
                response.sendError(777, "User with this login already" +
                        " exist");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            userDAO.login(user);
            regUser = userDAO.getUser(user);
            log.debug("Success registration");
        }
        return regUser;
    }

    private boolean validateUser(User user) {
        return new InputsValidator().validateUser(user);
    }

    @Override
    public void init() throws ServletException {
        DAOFactory dao = DAOFactory.getDAOFactory();
        userDAO = dao.getUserDAO();
    }
}

