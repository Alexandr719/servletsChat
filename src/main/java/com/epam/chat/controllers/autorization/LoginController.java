package com.epam.chat.controllers.autorization;


import com.epam.chat.ChatConstants;
import com.epam.chat.dao.UserDAO;
import com.epam.chat.entity.ServiceMessage;
import com.epam.chat.entity.User;
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
import java.sql.SQLException;

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


    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
            IOException {


        String responseMessage = getLoginResponseMessage(request);
        response.getWriter().write(responseMessage);


    }

    private String getLoginResponseMessage(HttpServletRequest request)
            throws IOException {
        User logUser;
        EntityMapper mapper = new EntityMapper();
        User user = mapper.getUserFromRequest(request);
        String responseMessage ;
//Todo another method
        if (!validateUser(user)) {
            log.debug("User didn't pass validation");
            ServiceMessage serviceMessage = new ServiceMessage(false,
                    ChatConstants.NO_VALID_USER);
            responseMessage = mapper.convertToJSON(serviceMessage);
        } else {
            try {
                 if (!userDAO.checkAuthorization(user)) {
                    log.debug("User login and password  are wrong");
                    ServiceMessage serviceMessage = new ServiceMessage(false,
                            ChatConstants.WRONG_PASS_LOGIN);
                    responseMessage = mapper.convertToJSON(serviceMessage);
                } else {
                    //Todo Main method
                    logUser = userDAO.getUser(user);
                    request.getSession()
                            .setAttribute(ChatConstants.SESSION_USER, logUser);
                    log.info("Logged user is enter into chat: " + logUser);
                    responseMessage = mapper.convertToJSON(logUser);
                }
            } catch (SQLException e) {
                log.error("Database error: ", e);
                ServiceMessage serviceMessage = new ServiceMessage(false,
                        ChatConstants.GO_TO_ADMIN);
                responseMessage = mapper.convertToJSON(serviceMessage);
            }
        }
        return responseMessage;
    }

    private boolean validateUser(User user) {
        return new InputsValidator().validateUser(user);
    }




    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response)
            throws ServletException,
            IOException {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute(ChatConstants.SESSION_USER);
        log.info("Deleted session user id = " + user.getId());
        session.invalidate();

    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
            IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ChatConstants.SESSION_USER);

        if (user == null) {
            log.debug("New user entered into chat");
            //Todo SetStatus
            response.setStatus(500);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "message");
        } else {
            log.debug("User with id=" + user.getId() + "entered into chat");
            response.getWriter().write(new EntityMapper().convertToJSON(user));
        }
    }

}
