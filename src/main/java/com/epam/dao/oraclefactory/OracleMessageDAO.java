package com.epam.dao.oraclefactory;

import com.epam.dao.MessageDAO;
import com.epam.entity.Message;
import com.epam.entity.User;
import com.epam.mapper.ResourceInspector;
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

    @Override
    public void sentMessage(Message message) {
        Locale.setDefault(Locale.ENGLISH);


        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(ResourceInspector.getInstance()
                    .getString("SQL_ADD_NEW_MESSAGE"));
            ps.setInt(1, message.getUser().getId());
            ps.setString(2, message.getMessage());

            ps.execute();
        } catch (SQLException e) {
            log.error(e);
        } finally {
            try {
                assert ps != null;
                ps.close();
            } catch (Exception e) {
                log.error(e);
            }
            try {
                con.close();
            } catch (Exception e) {
                log.error(e);
            }
        }

    }

    @Override
    public List<Message> getLastMessages(int count) {
        Locale.setDefault(Locale.ENGLISH);

        List<Message> messages = new ArrayList<>();
        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(ResourceInspector.getInstance()
                    .getString("SQL_GET_MESSAGES"));
            ps.setInt(1, count);
            rs = ps.executeQuery();
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
        } finally {
            try {
                assert rs != null;
                rs.close();
            } catch (SQLException e) {
                log.error(e);
            }
            try {
                ps.close();
            } catch (Exception e) {
                log.error(e);
            }
            try {
                con.close();
            } catch (Exception e) {
                log.error(e);
            }
        }
        return messages;

    }
}