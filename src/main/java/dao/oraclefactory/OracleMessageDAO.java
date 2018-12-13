package dao.oraclefactory;

import dao.MessageDAO;
import dao.ResourceInspector;
import entity.Message;
import entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class OracleMessageDAO implements MessageDAO {



    @Override
    public void sentMessage(Message message) {
        Locale.setDefault(Locale.ENGLISH);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("statements");
        System.out.println(resourceBundle.getString("SQL_ADD_NEW_USER"));


        DataSource dataSource = DataSourceFactory.getOracleDataSource();

        try {
            Connection con = dataSource.getConnection();
            PreparedStatement psUser = con.prepareStatement(ResourceInspector.getInstance().getString("SQL_ADD_NEW_MESSAGE"));
            psUser.setInt(1, message.getUser().getId());
            psUser.setString(2, message.getMessage());

            psUser.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Message> getLastMessages(int count) {
        Locale.setDefault(Locale.ENGLISH);

        List<Message> messages = new ArrayList<>();
        DataSource dataSource = DataSourceFactory.getOracleDataSource();

        try {
            Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(ResourceInspector.getInstance().getString("SQL_GET_MESSAGES"));
            preparedStatement.setInt(1, count);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message();
                User user = new User();
                message.setId(rs.getInt("ID"));
                user.setId(rs.getInt("USERID"));
                message.setMessage(rs.getString("TEXTMESSAGE"));
                user.setLogin(rs.getString("LOGIN"));
                user.setFirstName(rs.getString("FIRSTNAME"));
                user.setLastName(rs.getString("LASTNAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setRole(rs.getString("ROLE"));
                message.setUser(user);
                messages.add(message);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;

    }
}