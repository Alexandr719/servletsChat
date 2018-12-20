package com.epam.chat.controllers.autorization;


import com.epam.chat.dao.DAOFactory;
import com.epam.chat.dao.UserDAO;
import com.epam.chat.entity.User;
import com.epam.chat.mapper.EntityMapper;
import lombok.extern.log4j.Log4j2;
import org.owasp.encoder.Encode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet  RegistrationController
 * @author Alexander_Filatov
 * Add new user into db, show main page
 */
@Log4j2
@WebServlet(name = "RegistrationController", urlPatterns = "/registration")
public class RegistrationController extends HttpServlet {

    private static final long serialVersionUID = 1;

       protected void doPost (HttpServletRequest request,
                              HttpServletResponse response) throws
        ServletException, IOException {
           UserDAO userDAO = (UserDAO) request.getServletContext()
                   .getAttribute("userDAO");
            EntityMapper mapper = new EntityMapper();
            User user = userDAO.getUser((User) request
                   .getAttribute("regUser"));
            //ToDO regUser - const(class req or session)
            request.getSession().setAttribute("user", user);
            log.info("User is enter into chat: "+ user);


            response.setContentType("application/json");
            response.getWriter().write(mapper.convertToJSON(user));
        }

        protected void doGet (HttpServletRequest request,
                              HttpServletResponse response) throws
        ServletException, IOException {

        }
    }

