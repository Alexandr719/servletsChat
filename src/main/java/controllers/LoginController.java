package controllers;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;




@WebServlet(name = "controllers.LoginController", urlPatterns = "/login")
public class LoginController extends javax.servlet.http.HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//ToDO auto parse add
//        EntityMapper entityMapper = new EntityMapper();
//        User user = entityMapper.getLoginUser(request);

        String s = request.getParameter("login");
        System.out.println(s);



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getParameter("login"));
;
    }
}
