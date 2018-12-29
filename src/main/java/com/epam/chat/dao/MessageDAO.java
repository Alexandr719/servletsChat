package com.epam.chat.dao;

import com.epam.chat.entity.Message;

import java.sql.SQLException;
import java.util.List;

/**
 * UserDAO
 *
 * @author Alexander_Filatov
 */
public interface MessageDAO {

    void sentMessage(Message message) throws SQLException;

    List<Message> getLastMessages(int count) throws SQLException;
}