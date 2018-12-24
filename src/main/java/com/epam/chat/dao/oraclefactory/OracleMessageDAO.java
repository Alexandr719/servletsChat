package com.epam.chat.dao.oraclefactory;

import com.epam.chat.SqlStatement;
import com.epam.chat.dao.MessageDAO;
import com.epam.chat.entity.Message;
import com.epam.chat.entity.User;
import com.epam.chat.mapper.ResourceInspector;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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

    @SqlStatement(value = "INSERT INTO SERVLETMESSAGE (ID, USERID, TEXTMESSAGE) VALUES (SERVETMESSAGESSEQ.NEXTVAL, ?, ?)")
    @Override
    public void sentMessage(Message message) {
        Locale.setDefault(Locale.ENGLISH);
        String sqlMessage = null;

        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        sqlMessage = getAnnotationValue(methodName);
        if (sqlMessage == null) {
            log.error("SqlStatement is null");
        }


        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        try (Connection con = dataSource.getConnection(); PreparedStatement
                ps = createPreparedStatement(con, sqlMessage,
                message.getUser().getId(), message.getMessage())) {

            ps.execute();
        } catch (SQLException e) {
            log.error("SQLExeption :" + e);
        }

    }

    @SqlStatement(value = "SELECT * FROM SERVLETMESSAGE JOIN SERVLETUSER on SERVLETUSER.id = SERVLETMESSAGE.USERID WHERE 1=1 AND ROWNUM <= ?")
    @Override
    public List<Message> getLastMessages(int count) {
        Locale.setDefault(Locale.ENGLISH);
        String sqlMessage = null;

        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();

        sqlMessage = getAnnotationValue(methodName);
        if (sqlMessage == null) {
            log.error("SqlStatement is null");
        }


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


    private String getAnnotationValue(String methodName) throws NullPointerException {
        SqlStatement sqlStatement = Arrays.stream(getClass().getMethods())
                .filter(method -> method.getName().equals(methodName))
                .map(method -> method.getAnnotation(SqlStatement.class))
                .collect(Collectors.toList()).get(0);
        return sqlStatement.value();
    }
}