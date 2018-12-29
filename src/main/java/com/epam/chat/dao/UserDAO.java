package com.epam.chat.dao;

import com.epam.chat.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * UserDAO
 *
 * @author Alexander_Filatov
 */
public interface UserDAO {

    void login(User loginingUser) throws SQLException;

    boolean isUserExist(User user);

    boolean checkAuthorization(User user);

    List<User> getUsersList(int count);

    User getUser(User user);

}