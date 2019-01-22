package com.epam.chat.controllers.autorization;


import com.epam.chat.ChatConstants;
import com.epam.chat.dao.DAOFactory;
import com.epam.chat.dao.UserDAO;
import com.epam.chat.entity.ServiceMessage;
import com.epam.chat.entity.User;
import com.epam.chat.exeptions.ChatExeption;
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
@WebServlet(name = "RegistrationController", urlPatterns = "/user")
public class RegistrationController extends HttpServlet {

    private static final long serialVersionUID = 1;

    private UserDAO userDAO;


    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws
            ServletException, IOException {

        EntityMapper mapper = new EntityMapper();
        User user = mapper.getUserFromRequest(request);
        try {
                checkRegistrationValidation(user);
                authorizedUser(user, request);
                User sessionUser = (User) request.getSession()
                        .getAttribute(ChatConstants.SESSION_USER);
                response.getWriter().write(mapper.convertToJSON(sessionUser));

        } catch (ChatExeption e) {
            response.sendError(500, e.getMessage());
        }
    }


    private boolean validateUserForm(User user) {
        return new InputsValidator().validateUser(user);
    }

    private void authorizedUser(User user, HttpServletRequest request) {
        userDAO.addUser(user);
        User regUser = userDAO.getUser(user);
        request.getSession()
                .setAttribute(ChatConstants.SESSION_USER, regUser);
    }

    private void checkRegistrationValidation(User user)
            throws ChatExeption {
            if (!validateUserForm(user)) {
                log.debug("User didn't pass validation");
                throw new ChatExeption(ChatConstants.NO_VALID_USER);
            }
            if (userDAO.isUserExist(user)) {
                log.debug("User with this login already exist");
                throw new ChatExeption(ChatConstants.EXISTED_USER_LOGIN);
            }
    }

    @Override
    public void init() throws ServletException {
        DAOFactory dao = DAOFactory.getDAOFactory();
        userDAO = dao.getUserDAO();
    }
}

