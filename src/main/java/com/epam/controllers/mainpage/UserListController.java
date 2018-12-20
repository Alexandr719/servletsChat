package com.epam.controllers.mainpage;

import com.epam.dao.DAOFactory;
import com.epam.dao.UserDAO;
import com.epam.entity.User;
import com.epam.mapper.EntityMapper;
import lombok.extern.log4j.Log4j2;
import org.owasp.encoder.Encode;

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


    protected void doPost(HttpServletRequest request
            , HttpServletResponse response) throws ServletException
            , IOException {
        DAOFactory dao = DAOFactory.getDAOFactory();
        UserDAO userDAO = dao.getUserDAO();
        EntityMapper mapper = new EntityMapper();

        List<User> users = userDAO.getAllLogged(MAX_LENGTH_USERLIST);
        log.debug(MAX_LENGTH_USERLIST + " users took from db");

        response.setContentType("application/json");
        response.getWriter().println(Encode.forHtmlContent(mapper
                .convertToJSON(users)));
    }

    protected void doGet(HttpServletRequest request
            , HttpServletResponse response) throws ServletException
            , IOException {
        doPost(request, response);
    }

    @Override
    public void init() throws ServletException {

    }
}
