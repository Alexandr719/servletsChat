package com.epam.chat.controllers.autorization;

import com.epam.chat.ChatConstants;
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

@Log4j2
@WebServlet(name = "CheckExistLogin", urlPatterns = "/existlogin")
public class CheckExistLogin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDAO userDAO = (UserDAO) request.getServletContext()
                .getAttribute(ChatConstants.USER_DAO);
        EntityMapper mapper = new EntityMapper();
        User user = mapper.getUser(request);
        ServiceMessage serviceMessage = null;
        if (userDAO.isUserExist(user)) {
            serviceMessage = new ServiceMessage(false, "User with this login already exist");
            log.debug("User with " + user.getLogin() + " login already exist");

        } else {
            serviceMessage = new ServiceMessage(true, "User with this login isn't exist");
            log.debug("User with " + user.getLogin() + "isn't exist");
        }
        response.getWriter().write(mapper.convertToJSON(serviceMessage));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
