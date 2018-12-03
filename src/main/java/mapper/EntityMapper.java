package mapper;


import entity.User;

import javax.servlet.http.HttpServletRequest;

public class EntityMapper {

    public User getLoginUser(HttpServletRequest req){
        User user = new User();
        System.out.println(req.getParameter("login"));
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        return user;
    }
public User getRegisterUser(HttpServletRequest req){
    User user = new User();
    user.setLogin("login");
    user.setEmail("email");
    user.setPassword("password");
    user.setFirstName("firstName");
    user.setLastName("lastName");

return user;
}


}