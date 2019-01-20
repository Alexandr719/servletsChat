package com.epam.chat.dao;

import com.epam.chat.entity.Message;
import com.epam.chat.exeptions.ChatExeption;

import java.sql.SQLException;
import java.util.List;

/**
 * UserDAO
 *
 * @author Alexander_Filatov
 */
public interface MessageDAO {

    void sentMessage(Message message) throws ChatExeption;

    List<Message> getLastMessages(int count) throws ChatExeption;
}