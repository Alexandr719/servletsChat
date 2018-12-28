package com.epam.chat.dao.oraclefactory;

import com.epam.chat.SqlStatement;
import com.epam.chat.dao.MessageDAO;
import com.epam.chat.entity.Message;
import com.epam.chat.entity.User;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;

import java.security.InvalidParameterException;
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
//Todo add key annotation
    @SqlStatement(key = "ADD_NEW_MESSAGE",
            value = "INSERT INTO SERVLETMESSAGE (ID, USERID, TEXTMESSAGE) " +
                    "VALUES (SERVETMESSAGESSEQ.NEXTVAL, ?, ?)")
    @Override
    public void sentMessage(Message message) {

        DataSource dataSource = DataSourceFactory.getOracleDataSource();

        String sqlMessage = null;
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        sqlMessage = getAnnotationValue(methodName);
        if (sqlMessage == null) {
            log.error("SqlStatement is null");
        }
        System.out.println(sqlMessage);
        try (Connection con = dataSource.getConnection(); PreparedStatement ps =
                con.prepareStatement(sqlMessage)) {
            ps.setInt(1, message.getUser().getId());
            ps.setString(2, message.getMessage());
            ps.execute();
        } catch (SQLException e) {
            log.error("Can't add new user" + e);

        }


    }

    @SqlStatement(key ="GET_MESSAGES",
            value = "SELECT * FROM SERVLETMESSAGE JOIN SERVLETUSER on " +
                    "SERVLETUSER.id = SERVLETMESSAGE.USERID WHERE 1=1 " +
                    "AND ROWNUM <= ?")
    @Override
    public List<Message> getLastMessages(int count) {
        Locale.setDefault(Locale.ENGLISH);
        String sqlMessage = null;

        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        sqlMessage = getAnnotationValue(methodName);

         List<Message> messages = new ArrayList<>();
        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = createPreparedStatement(con,
                     sqlMessage, count);
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
            log.error("Cant get last messages: " , e);
        }
        System.out.println(messages);
        return messages;
    }

    private PreparedStatement createPreparedStatement(Connection con,
                                                      String sql, int count)
            throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, count);
        return ps;
    }

    private String getAnnotationValue(String methodName)
            throws InvalidParameterException {
        //todo get method name
        SqlStatement sqlStatement = Arrays.stream(getClass().getMethods())
                .filter(method -> method.getName().equals(methodName))
                .map(method -> method.getAnnotation(SqlStatement.class))
                .collect(Collectors.toList()).get(0);
        if(sqlStatement.value() == null){
            throw new InvalidParameterException();
        }
        return sqlStatement.value();
    }
}