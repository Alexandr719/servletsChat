package controllers;


import dao.DAOFactory;
import dao.UserDAO;
import entity.User;
import lombok.extern.log4j.Log4j2;
import mapper.EntityMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Log4j2
@WebServlet(name = "controllers.RegistrationController", urlPatterns = "/registration")
public class RegistrationController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityMapper mapper = new EntityMapper();
        User user = mapper.getUser(request);
        log.info("New register user = " + user);
        DAOFactory dao = DAOFactory.getDAOFactory();
        UserDAO userDAO = dao.getUserDAO();
        userDAO.login(user);



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
