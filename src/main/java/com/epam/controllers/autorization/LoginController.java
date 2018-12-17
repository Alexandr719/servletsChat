package com.epam.controllers.autorization;


import com.epam.dao.DAOFactory;
import com.epam.dao.UserDAO;
import com.epam.entity.User;
import lombok.extern.log4j.Log4j2;
import com.epam.mapper.EntityMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@WebServlet(name = "controllers.LoginController", urlPatterns = "/login")
public class LoginController extends javax.servlet.http.HttpServlet {

    private UserDAO userDAO;


    @Override
    public void init() throws ServletException {
        DAOFactory dao = DAOFactory.getDAOFactory();
        userDAO = dao.getUserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityMapper mapper = new EntityMapper();

        User logUser = userDAO.getUser((User) request.getAttribute("user"));
        request.getSession().setAttribute("user", logUser);
        log.info(logUser);



        response.setContentType("application/json");
        response.getWriter().println(mapper.objectToJSON(logUser));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
