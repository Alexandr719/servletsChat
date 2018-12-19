package com.epam.dao.oraclefactory;

import com.epam.dao.UserDAO;
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
public class OracleUserDAO implements UserDAO {

    private static final String USER_ROLE = "USER";

    @Override
    public void login(User loginUser) {
        Locale.setDefault(Locale.ENGLISH);

        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(ResourceInspector.getInstance().getString("SQL_ADD_NEW_USER"));
            ps.setString(1, loginUser.getLogin());
            ps.setString(2, loginUser.getFirstName());
            ps.setString(3, loginUser.getLastName());
            ps.setString(4, loginUser.getEmail());
            ps.setString(5, loginUser.getPassword());
            ps.setString(6, USER_ROLE);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
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
    public boolean isLogged(User user) {
        Locale.setDefault(Locale.ENGLISH);


        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(ResourceInspector.getInstance().getString("SQL_WAS_LOGGED"));
            ps.setString(1, user.getLogin());
            rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                return false;
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
        return true;
    }


    @Override
    public boolean checkLogIn(User user) {
        Locale.setDefault(Locale.ENGLISH);

        User checkedUser = new User();
        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(ResourceInspector.getInstance().getString("SQL_WAS_LOGGED"));
            ps.setString(1, user.getLogin());
            rs = ps.executeQuery();

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

        return false;

    }


    @Override
    public List<User> getAllLogged(int count) {
        Locale.setDefault(Locale.ENGLISH);

        List<User> loggedUsers = new ArrayList<>();
        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(ResourceInspector.getInstance().getString("SQL_GET_USER_LIST"));
            ps.setInt(1, count);
            rs = ps.executeQuery();
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
            log.error(e.getMessage());
        } finally {
            try {
                assert rs != null;
                rs.close();
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
            try {
                ps.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            try {
                con.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        return loggedUsers;

    }

    @Override
    public User getUser(User user) {
        Locale.setDefault(Locale.ENGLISH);

        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(ResourceInspector.getInstance().getString("SQL_GET_USER"));
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            rs = ps.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("ID"));
                user.setFirstName(rs.getString("FIRSTNAME"));
                user.setLastName(rs.getString("LASTNAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setRole(rs.getString("ROLE"));
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            try {
                assert rs != null;
                rs.close();
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
            try {
                ps.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            try {
                con.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return user;

    }

}


