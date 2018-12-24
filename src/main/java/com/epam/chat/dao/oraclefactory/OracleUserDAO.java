package com.epam.chat.dao.oraclefactory;

import com.epam.chat.SqlStatement;
import com.epam.chat.dao.UserDAO;
import com.epam.chat.entity.User;
import com.epam.chat.mapper.ResourceInspector;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
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
public class OracleUserDAO implements UserDAO {

    private static final String USER_ROLE = "USER";

    @SqlStatement(value = "INSERT INTO SERVLETUSER (ID, LOGIN, FIRSTNAME, LASTNAME, EMAIL, PASSWORD, ROLE) VALUES (SERVETUSERSSEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)")
    @Override
    public void login(User loginUser) {
        Locale.setDefault(Locale.ENGLISH);
        DataSource dataSource = DataSourceFactory.getOracleDataSource();

        String sqlMessage = null;
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        sqlMessage = getAnnotationValue(methodName);
        if (sqlMessage == null) {
            log.error("SqlStatement is null");
        }

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
            log.error(e);

        }
    }

    @SqlStatement(value = "SELECT * FROM SERVLETUSER WHERE (LOGIN = ?)")
    @Override
    public boolean isLogged(User user) {
        Locale.setDefault(Locale.ENGLISH);
        DataSource dataSource = DataSourceFactory.getOracleDataSource();

        String sqlMessage = null;
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        sqlMessage = getAnnotationValue(methodName);
        if (sqlMessage == null) {
            log.error("SqlStatement is null");
        }
        try (Connection con = dataSource.getConnection(); PreparedStatement ps
                = createPreparedStatement(con, sqlMessage, user.getLogin()); ResultSet rs =
                     ps.executeQuery()) {

            if (!rs.isBeforeFirst()) {
                return false;
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return true;
    }

    @SqlStatement(value = "SELECT * FROM SERVLETUSER WHERE (LOGIN = ?)")
    @Override
    public boolean checkLogIn(User user) {
        Locale.setDefault(Locale.ENGLISH);

        User checkedUser = new User();
        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        String sqlMessage = null;
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        sqlMessage = getAnnotationValue(methodName);
        if (sqlMessage == null) {
            log.error("SqlStatement is null");
        }

        try (Connection con = dataSource.getConnection(); PreparedStatement ps
                = createPreparedStatement(con, sqlMessage, user.getLogin()); ResultSet rs =
                     ps.executeQuery()) {
            if (rs != null) {
                log.info("ResultSet isn't null");
                while (rs.next()) {
                    checkedUser.setLogin(rs.getString("LOGIN"));
                    checkedUser.setPassword(rs.getString("PASSWORD"));
                }
            }
            return (user.getPassword().equals(checkedUser.getPassword())
                    && user.getLogin().equals(checkedUser.getLogin()));

        } catch (SQLException e) {
            log.error(e);
        }


        return false;

    }

    @SqlStatement(value = "SELECT * FROM SERVLETUSER WHERE 1=1 AND ROWNUM <= ?")
    @Override
    public List<User> getAllLogged(int count) {
        Locale.setDefault(Locale.ENGLISH);

        List<User> loggedUsers = new ArrayList<>();
        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        String sqlMessage = null;
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        sqlMessage = getAnnotationValue(methodName);
        if (sqlMessage == null) {
            log.error("SqlStatement is null");
        }
        try (Connection con = dataSource.getConnection(); PreparedStatement ps
                = createPreparedStatement(con, sqlMessage, count); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setLogin(rs.getString("LOGIN"));
                user.setFirstName(rs.getString("FIRSTNAME"));
                user.setLastName(rs.getString("LASTNAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setRole(rs.getString("ROLE"));
                loggedUsers.add(user);
            }

        } catch (SQLException e) {
            log.error(e);
        }


        return loggedUsers;

    }

    @SqlStatement(value = "SELECT * FROM SERVLETUSER WHERE (LOGIN = ? AND PASSWORD = ?)")
    @Override
    public User getUser(User user) {
        Locale.setDefault(Locale.ENGLISH);
        DataSource dataSource = DataSourceFactory.getOracleDataSource();

        String sqlMessage = null;
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        sqlMessage = getAnnotationValue(methodName);
        if (sqlMessage == null) {
            log.error("SqlStatement is null");
        }

        try (Connection con = dataSource.getConnection(); PreparedStatement ps
                = createPreparedStatement(con, sqlMessage, user.getLogin(),
                user.getPassword
                        ()); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                user.setId(rs.getInt("ID"));
                user.setFirstName(rs.getString("FIRSTNAME"));
                user.setLastName(rs.getString("LASTNAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setRole(rs.getString("ROLE"));
            }

        } catch (SQLException e) {
            log.error(e);
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

    private String getAnnotationValue(String methodName) throws NullPointerException {
        SqlStatement sqlStatement = Arrays.stream(getClass().getMethods())
                .filter(method -> method.getName().equals(methodName))
                .map(method -> method.getAnnotation(SqlStatement.class))
                .collect(Collectors.toList()).get(0);
        return sqlStatement.value();
    }

}


