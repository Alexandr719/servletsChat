package com.epam.dao;

import com.epam.entity.Message;

import java.util.List;

public interface MessageDAO {

    void sentMessage(Message message);
    List<Message> getLastMessages(int count);
}