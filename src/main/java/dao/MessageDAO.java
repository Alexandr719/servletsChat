package dao;

import entity.Message;

import java.util.List;

public interface MessageDAO {

    void sentMessage(Message message);
    List<Message> getLastMessages(int count);
}