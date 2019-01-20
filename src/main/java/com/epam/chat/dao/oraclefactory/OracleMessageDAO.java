package com.epam.chat.dao.oraclefactory;

import com.epam.chat.ChatConstants;
import com.epam.chat.SqlStatement;
import com.epam.chat.dao.MessageDAO;
import com.epam.chat.entity.Message;
import com.epam.chat.entity.User;
import com.epam.chat.exeptions.ChatExeption;
import com.epam.chat.mapper.EntityMapper;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * OracleMessageDAO
 *
 * @author Alexander_Filatov
 */
@Log4j2
public class OracleMessageDAO implements MessageDAO {
   @SqlStatement(key = "ADD_NEW_MESSAGE",
            value = "INSERT INTO MESSAGES (MESSAGEID, USERID, TEXTMESSAGE) " +
                    "VALUES (SERVETMESSAGESSEQ.NEXTVAL, ?, ?)")
    @Override
    public void sentMessage(Message message) throws ChatExeption {

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
            throw new ChatExeption(ChatConstants.GO_TO_ADMIN);

        }
    }

    @SqlStatement(key = "GET_MESSAGES",
            value = "SELECT * FROM MESSAGES JOIN USERS on " +
                    "USERS.USERID = MESSAGES.USERID WHERE 1=1 " +
                    "AND ROWNUM <= ?")
    @Override
    public List<Message> getLastMessages(int count) throws ChatExeption {
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
            throw new ChatExeption(ChatConstants.GO_TO_ADMIN);
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
        return Objects.requireNonNull(Arrays.stream(getClass().getMethods())
                .map(method -> method.getAnnotation(SqlStatement.class))
                .filter(sqlStatement -> key.equals(sqlStatement.key()))
                .findAny()
                .orElse(null))
                .value();
    }
}