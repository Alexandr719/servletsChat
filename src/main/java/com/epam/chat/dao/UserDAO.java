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

    void addUser(User loginingUser) throws SQLException;

    boolean isUserExist(User user) throws SQLException;

    boolean checkAuthorization(User user) throws SQLException;

    List<User> getUsersList(int count) throws SQLException;

    User getUser(User user) throws SQLException;

}