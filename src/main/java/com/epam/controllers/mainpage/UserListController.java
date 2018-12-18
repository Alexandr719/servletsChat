package com.epam.controllers.mainpage;

import com.epam.dao.DAOFactory;
import com.epam.dao.UserDAO;
import com.epam.entity.User;
import com.epam.mapper.EntityMapper;
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

    private final static int MAX_LENGTH_USERLIST = 100;
    private static UserDAO userDAO;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityMapper mapper = new EntityMapper();
        List<User> users = userDAO.getAllLogged(MAX_LENGTH_USERLIST);
        log.debug(MAX_LENGTH_USERLIST + " users took from db");

        response.setContentType("application/json");
        response.getWriter().println(mapper.convertObjectToJSON(users));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void init() throws ServletException {
        DAOFactory dao = DAOFactory.getDAOFactory();
        userDAO = dao.getUserDAO();
    }
}
