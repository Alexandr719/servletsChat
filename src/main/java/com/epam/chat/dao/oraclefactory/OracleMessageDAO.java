package com.epam.chat.dao.oraclefactory;

import com.epam.chat.dao.MessageDAO;
import com.epam.chat.entity.Message;
import com.epam.chat.entity.User;
import com.epam.chat.mapper.ResourceInspector;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * OracleMessageDAO
 *
 * @author Alexander_Filatov
 */
@Log4j2
public class OracleMessageDAO implements MessageDAO {
    //TODO sql annotation + key
    @Override
    public void sentMessage(Message message) {
        Locale.setDefault(Locale.ENGLISH);

        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        try (Connection con = dataSource.getConnection(); PreparedStatement
                ps = createPreparedStatement(con,
                ResourceInspector.getInstance()
                        .getString("SQL_ADD_NEW_MESSAGE"),
                message.getUser().getId(), message.getMessage())) {

            ps.execute();
        } catch (SQLException e) {
            log.error(e);
        }

    }

    @Override
    public List<Message> getLastMessages(int count) {
        Locale.setDefault(Locale.ENGLISH);

        List<Message> messages = new ArrayList<>();
        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = createPreparedStatement(con,
                     ResourceInspector.getInstance().getString
                             ("SQL_GET_MESSAGES"), count);
             ResultSet rs = ps.executeQuery()) {
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
            log.error(e);
        }
        return messages;

    }

    private PreparedStatement createPreparedStatement(Connection con,
                                                      String sql, int count)
            throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, count);
        return ps;
    }

    private PreparedStatement createPreparedStatement(Connection con, String
            sql, int userId, String message) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setString(2, message);
        return ps;
    }


}