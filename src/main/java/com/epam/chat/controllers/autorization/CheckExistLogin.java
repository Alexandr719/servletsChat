package com.epam.chat.controllers.autorization;


import com.epam.chat.dao.DAOFactory;
import com.epam.chat.dao.UserDAO;
import com.epam.chat.entity.ServiceMessage;
import com.epam.chat.entity.User;
import com.epam.chat.mapper.EntityMapper;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Log4j2
@WebServlet(name = "CheckExistLogin", urlPatterns = "/existlogin")
public class CheckExistLogin extends HttpServlet {

    private UserDAO userDAO;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        EntityMapper mapper = new EntityMapper();
        User user = mapper.getUserFromRequest(request);
        ServiceMessage serviceMessage = null;
        try {
            if (userDAO.isUserExist(user)) {
                serviceMessage = new ServiceMessage(false,
                        "User with this login already exist");
                log.debug("User with " + user.getLogin() +
                        " login already exist");

            } else {
                serviceMessage = new ServiceMessage(true,
                        "User with this login isn't exist");
                log.debug("User with " + user.getLogin() + "isn't exist");
            }
        } catch (SQLException e) {
            //Todo
            response.sendError(700,
                    "The error occurred, contact to the administrator");
            log.error("Error in dao", e);
        }
        response.getWriter().write(mapper.convertToJSON(serviceMessage));
    }

    @Override
    public void init() throws ServletException {
        DAOFactory dao = DAOFactory.getDAOFactory();
        userDAO = dao.getUserDAO();
    }
}
