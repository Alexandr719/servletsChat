package com.epam.dao;

import com.epam.entity.User;

import java.util.List;

public interface UserDAO {

        void login(User loginingUser);

        boolean isLogged(User user);

        boolean checkLogIn(User user);

        List<User> getAllLogged(int count);

        User getUser(User user);

}