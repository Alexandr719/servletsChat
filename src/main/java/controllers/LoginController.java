package controllers;




import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Thing;
import entity.User;
import mapper.EntityMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;




@WebServlet(name = "controllers.LoginController", urlPatterns = "/login")
public class LoginController extends javax.servlet.http.HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityMapper mapper = new EntityMapper();
        User user = mapper.getUser(request);
        System.out.println(user);



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
