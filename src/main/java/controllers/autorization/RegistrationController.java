package controllers.autorization;


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

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory dao = DAOFactory.getDAOFactory();
        userDAO = dao.getUserDAO();
    }




    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityMapper mapper = new EntityMapper();
        User user = userDAO.getUser((User) request.getAttribute("regUser"));
        request.getSession().setAttribute("user", user);
        log.info(user);


        response.setContentType("application/json");
        response.getWriter().println(mapper.objectToJSON(user));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
