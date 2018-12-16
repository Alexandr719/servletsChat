package com.epam.controllers.autorization;

import com.epam.entity.User;
import com.epam.mapper.EntityMapper;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Log4j2
@WebServlet(name = "CheckLogController", urlPatterns = "/checklog")
public class CheckLogController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            log.debug("New user entered into chat");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            log.debug("User with id=" + user.getId() + "entered into chat");
            response.setContentType("application/json");
            response.getWriter().println(new EntityMapper().objectToJSON(user));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doPost(request, response);
    }
}
