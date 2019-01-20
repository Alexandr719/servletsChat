package com.epam.chat.dao.oraclefactory;

import com.epam.chat.ChatConstants;
import com.epam.chat.SqlStatement;
import com.epam.chat.dao.UserDAO;
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
public class OracleUserDAO implements UserDAO {

    private static final String USER_ROLE = "USER";

    @SqlStatement(key = "LOGIN_USER",
            value = "INSERT INTO USERS (USERID, LOGIN, FIRSTNAME, LASTNAME," +
                    " EMAIL, PASSWORD, ROLE) VALUES " +
                    "(SERVETUSERSSEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)")
    @Override
    public void addUser(User loginUser) throws ChatExeption {
        Locale.setDefault(Locale.ENGLISH);
        DataSource dataSource = DataSourceFactory.getOracleDataSource();

        String sqlMessage = null;
        sqlMessage = getSQLstatement("LOGIN_USER");
        try (Connection con = dataSource.getConnection(); PreparedStatement ps =
                con.prepareStatement(sqlMessage)) {

            ps.setString(1, loginUser.getLogin());
            ps.setString(2, loginUser.getFirstName());
            ps.setString(3, loginUser.getLastName());
            ps.setString(4, loginUser.getEmail());
            ps.setString(5, loginUser.getPassword());
            ps.setString(6, USER_ROLE);
            ps.execute();
        } catch (SQLException e) {
            log.error("Can't add new user", e);
            throw new ChatExeption(ChatConstants.GO_TO_ADMIN);

        }
    }

    @SqlStatement(key = "IS_USER_EXIST", value = "SELECT * FROM USERS " +
            "WHERE (LOGIN = ?)")
    @Override
    public boolean isUserExist(User user) throws ChatExeption {
        Locale.setDefault(Locale.ENGLISH);
        DataSource dataSource = DataSourceFactory.getOracleDataSource();


        String sqlMessage = getSQLstatement("IS_USER_EXIST");

        boolean isUserExist = false;
        try (Connection con = dataSource.getConnection(); PreparedStatement ps
                = createPreparedStatement(con, sqlMessage, user.getLogin());
             ResultSet rs =
                     ps.executeQuery()) {
            isUserExist = rs.isBeforeFirst();
        } catch (SQLException e) {
            log.error("Can't check is Logging ", e);
            throw new ChatExeption(ChatConstants.GO_TO_ADMIN);
        }
        return isUserExist;
    }

    @SqlStatement(key = "CHECK_AUTORIZATION", value = "SELECT * FROM USERS " +
            "WHERE (LOGIN = ?)")
    @Override
    public boolean checkAuthorization(User user) throws ChatExeption {
        Locale.setDefault(Locale.ENGLISH);

        boolean authorizationCkeck ;
        User checkedUser = new User();
        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        String sqlMessage = null;
        sqlMessage = getSQLstatement("CHECK_AUTORIZATION");
        try (Connection con = dataSource.getConnection(); PreparedStatement ps
                = createPreparedStatement(con, sqlMessage, user.getLogin());
             ResultSet rs =
                     ps.executeQuery()) {
            if (rs != null) {
                log.info("ResultSet isn't null");
                while (rs.next()) {
                    checkedUser.setLogin(rs.getString("LOGIN"));
                    checkedUser.setPassword(rs.getString("PASSWORD"));
                }
            }
            authorizationCkeck =
                    (user.getPassword().equals(checkedUser.getPassword())
                            && user.getLogin().equals(checkedUser.getLogin()));

        } catch (SQLException e) {
            log.error("Can't check user", e);
            throw new ChatExeption(ChatConstants.GO_TO_ADMIN);

        }


        return authorizationCkeck;

    }

    @SqlStatement(key = "GET_USER_LIST", value = "SELECT * FROM USERS " +
            "WHERE 1=1 AND ROWNUM <= ?")
    @Override
    public List<User> getUsersList(int count) throws ChatExeption {
        Locale.setDefault(Locale.ENGLISH);

        List<User> loggedUsers = new ArrayList<>();
        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        String sqlMessage = null;
        sqlMessage = getSQLstatement("GET_USER_LIST");
        EntityMapper mapper = new EntityMapper();
        try (Connection con = dataSource.getConnection(); PreparedStatement ps
                = createPreparedStatement(con, sqlMessage, count);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = mapper.getUserFromDB(rs);
                loggedUsers.add(user);
            }

        } catch (SQLException e) {
            log.error("Can't take all users from db", e);
            throw new ChatExeption(ChatConstants.GO_TO_ADMIN);
        }

        return loggedUsers;

    }

    @SqlStatement(key = "GET_USER",
            value = "SELECT * FROM USERS WHERE (LOGIN = ? " +
                    "AND PASSWORD = ?)")
    @Override
    public User getUser(User user) throws ChatExeption {
        Locale.setDefault(Locale.ENGLISH);
        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        EntityMapper mapper = new EntityMapper();

        String sqlMessage = null;
        sqlMessage = getSQLstatement("GET_USER");
        try (Connection con = dataSource.getConnection(); PreparedStatement ps
                = createPreparedStatement(con, sqlMessage, user.getLogin(),
                user.getPassword
                        ()); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                user = mapper.getUserFromDB(rs);
            }
        } catch (SQLException e) {
            log.error("Can't take user from db ", e);
            throw new ChatExeption(ChatConstants.GO_TO_ADMIN);
        }
        return user;
    }


    private PreparedStatement createPreparedStatement(Connection con,
                                                      String sql, int count)
            throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, count);
        return ps;
    }


    private PreparedStatement createPreparedStatement(Connection
                                                              con, String
                                                              sql, String login)
            throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, login);
        return ps;
    }

    private PreparedStatement createPreparedStatement(Connection con, String
            sql, String login, String password) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, login);
        ps.setString(2, password);
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


