package com.epam.controllers.autorization;


import com.epam.dao.DAOFactory;
import com.epam.dao.UserDAO;
import com.epam.entity.User;
import com.epam.mapper.EntityMapper;
import lombok.extern.log4j.Log4j2;
import org.owasp.encoder.Encode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Servlet  LoginController
 * @author Alexander_Filatov
 * Get full user data from db, by login and password. Also show main page.
 */
@Log4j2
@WebServlet(name = "LoginController", urlPatterns = "/login")
public class LoginController extends javax.servlet.http.HttpServlet {
    private static final long serialVersionUID = 1;





    protected void doPost(HttpServletRequest request
            , HttpServletResponse response) throws ServletException,
            IOException {
        DAOFactory dao = DAOFactory.getDAOFactory();
        UserDAO userDAO = dao.getUserDAO();
        EntityMapper mapper = new EntityMapper();

        User logUser = userDAO.getUser((User) request
                .getAttribute("user"));
        request.getSession().setAttribute("user", logUser);
        log.info("User is enter into chat: "+ logUser);

        response.setContentType("application/json");
        response.getWriter().write(Encode.forHtmlContent(mapper
                .convertToJSON(logUser)));
    }

    protected void doGet(HttpServletRequest request
            , HttpServletResponse response) throws ServletException,
            IOException {
        doPost(request, response);
    }
}
