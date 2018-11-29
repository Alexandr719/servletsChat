package dao.oraclefactory;

import dao.MessageDAO;
import entity.Message;

import java.util.List;

public class OracleMessageDAO implements MessageDAO {
    @Override
    public void sentMessage(Message message) {

    }

    @Override
    public List<Message> getLast(int count) {
        return null;
    }
}