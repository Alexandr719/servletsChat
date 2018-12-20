package com.epam.chat.dao;

import com.epam.chat.entity.User;

import java.util.List;

/**
 * UserDAO
 *
 * @author Alexander_Filatov
 */
public interface UserDAO {

    void login(User loginingUser);

    boolean isLogged(User user);

    boolean checkLogIn(User user);

    List<User> getAllLogged(int count);

    User getUser(User user);

}