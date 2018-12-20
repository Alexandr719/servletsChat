package com.epam.chat.dao;

import com.epam.chat.entity.Message;

import java.util.List;

/**
 * UserDAO
 *
 * @author Alexander_Filatov
 */
public interface MessageDAO {

    void sentMessage(Message message);

    List<Message> getLastMessages(int count);
}