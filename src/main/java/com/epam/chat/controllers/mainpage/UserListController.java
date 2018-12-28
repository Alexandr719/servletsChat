package com.epam.chat.controllers.mainpage;

import com.epam.chat.dao.DAOFactory;
import com.epam.chat.dao.UserDAO;
import com.epam.chat.entity.User;
import com.epam.chat.mapper.EntityMapper;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet  UserListController
 *
 * @author Alexander_Filatov
 * Gets users from dataBase
 */
@Log4j2
@WebServlet(name = "UserListController", urlPatterns = "/getusers")
public class UserListController extends HttpServlet {
    private static final long serialVersionUID = 1;
    private final static int MAX_LENGTH_USERLIST = 100;
    private UserDAO userDAO;

    //Todo goget
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
            IOException {
        EntityMapper mapper = new EntityMapper();

        List<User> users = userDAO.getUsersList(MAX_LENGTH_USERLIST);
        log.debug(MAX_LENGTH_USERLIST + " users took from db");
//todo
        response.setContentType("application/json");
        response.getWriter().println(mapper.convertToJSON(users));
    }


    @Override
    public void init() throws ServletException {
        DAOFactory dao = DAOFactory.getDAOFactory();
        userDAO = dao.getUserDAO();
    }
}
