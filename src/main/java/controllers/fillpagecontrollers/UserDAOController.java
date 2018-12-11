package controllers.fillpagecontrollers;

import dao.DAOFactory;
import dao.UserDAO;
import entity.User;
import mapper.EntityMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UserDAOController" ,urlPatterns = "/getusers")
public class UserDAOController extends HttpServlet {
    private UserDAO userDAO;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityMapper mapper = new EntityMapper();
        //TODO
        List<User> users = userDAO.getAllLogged(100);

        response.setContentType("application/json");
        response.getWriter().println(mapper.objectToJSON(users));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    public void init() throws ServletException {
        DAOFactory dao = DAOFactory.getDAOFactory();
        userDAO = dao.getUserDAO();
    }
}
