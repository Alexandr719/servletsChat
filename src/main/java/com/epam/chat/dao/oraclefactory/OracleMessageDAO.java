package com.epam.chat.dao.oraclefactory;

import com.epam.chat.SqlStatement;
import com.epam.chat.dao.MessageDAO;
import com.epam.chat.entity.Message;
import com.epam.chat.entity.User;
import com.epam.chat.mapper.EntityMapper;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
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
            value = "INSERT INTO SERVLETMESSAGE (MESSAGEID, USERID, TEXTMESSAGE) " +
                    "VALUES (SERVETMESSAGESSEQ.NEXTVAL, ?, ?)")
    @Override
    public void sentMessage(Message message) throws SQLException {

        DataSource dataSource = DataSourceFactory.getOracleDataSource();

        String sqlMessage = null;
        sqlMessage = getSQLstatement("ADD_NEW_MESSAGE");
        try (Connection con = dataSource.getConnection(); PreparedStatement ps =
                con.prepareStatement(sqlMessage)) {
            ps.setInt(1, message.getUser().getId());
            ps.setString(2, message.getMessage());
            ps.execute();
        } catch (SQLException e) {
            log.error("Can't add new user", e);
            throw e;

        }
    }

    @SqlStatement(key = "GET_MESSAGES",
            value = "SELECT * FROM SERVLETMESSAGE JOIN SERVLETUSER on " +
                    "SERVLETUSER.USERID = SERVLETMESSAGE.USERID WHERE 1=1 " +
                    "AND ROWNUM <= ?")
    @Override
    public List<Message> getLastMessages(int count) throws SQLException {
        Locale.setDefault(Locale.ENGLISH);
        String sqlMessage = null;
        EntityMapper mapper = new EntityMapper();


        sqlMessage = getSQLstatement("GET_MESSAGES");


        List<Message> messages = new ArrayList<>();
        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = createPreparedStatement(con,
                     sqlMessage, count);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Message message = new Message();
                User user = mapper.getUserFromDB(rs);
                message.setId(rs.getInt("MESSAGEID"));
                message.setMessage(rs.getString("TEXTMESSAGE"));
                message.setUser(user);
                messages.add(message);
            }
        } catch (SQLException e) {
            log.error("Cant get last messages: ", e);
            throw e;
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

    private String getSQLstatement(String key) {
        List<SqlStatement> sqlStatements = Arrays.stream(getClass().getMethods())
                .map(method -> method.getAnnotation(SqlStatement.class))
                .collect(Collectors.toList());

        for (SqlStatement sqlStatement : sqlStatements) {
            if (key.equals(sqlStatement.key())) {
                return sqlStatement.value();
            }
        }
        return null;
    }
}