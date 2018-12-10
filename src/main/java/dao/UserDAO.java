package dao;

import entity.User;

import java.util.List;

public interface UserDAO {


        void login(User loginingUser);

        boolean isLogged(User user);

        boolean checkLogIn(User user);

        List<User> getAllLogged();

        User getUser(User user);



}