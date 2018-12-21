package com.epam.chat.controllers.autorization;


import com.epam.chat.entity.User;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet  ExitController
 *
 * @author Alexander_Filatov
 * If user click on exit button, delete user's session
 */
@Log4j2
@WebServlet(name = "ExitController", urlPatterns = "/exit")
public class ExitController extends HttpServlet {

    private static final long serialVersionUID = 1;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
            IOException {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        if(user == null){
            //Todo null!!!
            response.sendError(404);
        }else{
            log.info("Deleted session user id = " + user.getId());
            session.invalidate();
        }


    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
            IOException {

    }
}