package dao;

import entity.User;

import java.util.List;

public interface UserDAO {


        void login(User loginingUser);

        boolean isLogged(User user);

        boolean checkLogIn(User user);

        void kick(User kickableUser);

        void unkick(User user);

        boolean isKicked(User user);

        void logout(User logoutingUser);

        List<User> getAllLogged();

        User getUser(User user);

        void updateUser(User user);

}