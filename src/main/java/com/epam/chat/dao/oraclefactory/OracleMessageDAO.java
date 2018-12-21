package com.epam.chat.dao.oraclefactory;

import com.epam.chat.SqlStatement;
import com.epam.chat.dao.MessageDAO;
import com.epam.chat.entity.Message;
import com.epam.chat.entity.User;
import com.epam.chat.mapper.ResourceInspector;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * OracleMessageDAO
 *
 * @author Alexander_Filatov
 */
@Log4j2
public class OracleMessageDAO implements MessageDAO {
    //TODO sql annotation + key
    @SqlStatement(key = "two", value = "tro lo lo two")
    @Override
    public void sentMessage(Message message) {
        Locale.setDefault(Locale.ENGLISH);

        SqlStatement insertAnnotation = getAnnotation(SqlStatement.class,
                "two");
        log.info("MESSAGE" + insertAnnotation.value());
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

    @SqlStatement(key = "one", value = "tro lo lo")
    @Override
    public List<Message> getLastMessages(int count) {
        Locale.setDefault(Locale.ENGLISH);
        String sqlMessage = null;
        SqlStatement insertAnnotation =
                getAnnotation(SqlStatement.class, "one");
        log.info("MESSAGE" + insertAnnotation.value());
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

    private SqlStatement getAnnotation(Class<? extends Annotation> annotation
            , String key) {
        List<Annotation> annotationList =
                Arrays.stream(getClass().getMethods())
                        .filter(method -> method
                                .isAnnotationPresent(annotation))
                        .map(method -> method.getAnnotation(annotation))
                        .collect(Collectors.toList());

        for (Annotation annotation1 : annotationList) {
            SqlStatement insertAnnotation = (SqlStatement) annotation1;
            if (insertAnnotation.key().equals(key)) {
                return insertAnnotation;
            }
        }
        return null;
    }

}