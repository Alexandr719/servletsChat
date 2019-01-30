package com.epam.chat.controllers.autorization;


import com.epam.chat.ChatConstants;
import com.epam.chat.dao.UserDAO;
import com.epam.chat.entity.User;
import com.epam.chat.exeptions.ChatExeption;
import com.epam.chat.mapper.EntityMapper;
import com.epam.chat.validation.InputsValidator;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet  LoginController
 *
 * @author Alexander_Filatov
 * Get full user data from db, by addUser and password. Also show main page.
 */
@Log4j2
@WebServlet(name = "LoginController", urlPatterns = "/user/session")
public class LoginController extends javax.servlet.http.HttpServlet {
    private static final long serialVersionUID = 1;
    @Inject
    private UserDAO userDAO;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
            IOException {

        EntityMapper mapper = new EntityMapper();
        User user = mapper.getUserFromRequest(request);
        try {
            checkLoginValidation(user);
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

    private void checkLoginValidation(User user)
            throws ChatExeption {
        boolean validationResult = false;

        if (!validateUserForm(user)) {
            log.info("User didn't pass form validation");
            throw new ChatExeption(ChatConstants.NO_VALID_USER);
        }
        if (!userDAO.checkAuthorization(user)) {
            log.debug("User login and password  are wrong");
            throw new ChatExeption(ChatConstants.WRONG_PASS_LOGIN);
        }
    }

    private void authorizedUser(User user, HttpServletRequest request) {
        User logUser = userDAO.getUser(user);
        request.getSession()
                .setAttribute(ChatConstants.SESSION_USER, logUser);
        log.info("Logged user is enter into chat: " + logUser);
    }

@Override
    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response)
            throws ServletException,
            IOException {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute(ChatConstants.SESSION_USER);
        log.info("Deleted session user id = " + user);
        session.invalidate();

    }
@Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
            IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ChatConstants.SESSION_USER);
//TODO
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            log.debug("User " + user + "entered into chat");
            response.getWriter().write(new EntityMapper().convertToJSON(user));
        }
    }

}
