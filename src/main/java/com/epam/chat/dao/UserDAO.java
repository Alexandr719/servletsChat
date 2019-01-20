package com.epam.chat.dao;

import com.epam.chat.entity.User;
import com.epam.chat.exeptions.ChatExeption;

import java.sql.SQLException;
import java.util.List;

/**
 * UserDAO
 *
 * @author Alexander_Filatov
 */
public interface UserDAO {

    void addUser(User loginingUser) throws ChatExeption;

    boolean isUserExist(User user) throws ChatExeption;

    boolean checkAuthorization(User user) throws ChatExeption;

    List<User> getUsersList(int count) throws ChatExeption;

    User getUser(User user) throws ChatExeption;

}