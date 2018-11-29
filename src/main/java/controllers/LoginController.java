package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.User;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "controllers.LoginController", urlPatterns = "/login")
public class LoginController extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//ToDO auto parse add
        System.out.println(request.getParameter("login"));


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setLogin(request.getParameter("Login"));
    }
}
