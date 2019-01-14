package com.epam.chat.controllers.autorization;


import com.epam.chat.ChatConstants;
import com.epam.chat.dao.DAOFactory;
import com.epam.chat.dao.UserDAO;
import com.epam.chat.entity.ServiceMessage;
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
import java.sql.SQLException;

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

        String responseMessage = registrateUser(request);
        response.getWriter().write(responseMessage);

    }

//Todo rename
    private String registrateUser(HttpServletRequest request)
            throws IOException {

        EntityMapper mapper = new EntityMapper();
        User user = mapper.getUserFromRequest(request);
        String responseMessage;

        if (!validateUser(user)) {
            log.debug("User didn't pass validation");
            ServiceMessage serviceMessage = new ServiceMessage(false,
                    ChatConstants.NO_VALID_USER);
            responseMessage = mapper.convertToJSON(serviceMessage);
        } else {
            try {
                if (userDAO.isUserExist(user)) {
                    log.debug("User with this addUser already exist");
                    ServiceMessage serviceMessage = new ServiceMessage(false,
                            ChatConstants.EXISTED_USER_LOGIN);
                    responseMessage = mapper.convertToJSON(serviceMessage);
                } else {
                    userDAO.addUser(user);
                    User regUser = userDAO.getUser(user);
                    request.getSession()
                            .setAttribute(ChatConstants.SESSION_USER, regUser);
                    responseMessage = mapper.convertToJSON(regUser);
                }
            } catch (SQLException e) {
                log.error("Error with database: ", e);
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

    @Override
    public void init() throws ServletException {
        DAOFactory dao = DAOFactory.getDAOFactory();
        userDAO = dao.getUserDAO();
    }
}

