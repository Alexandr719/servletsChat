package dao.oraclefactory;

import dao.DAOFactory;
import dao.MessageDAO;
import dao.PropertyWorker;
import dao.UserDAO;
import entity.User;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

@Log4j2
public class OracleUserDAO implements UserDAO {

    private static final String USER_ROLE = "USER";

    @Override
    public void login(User loginUser) {
        Locale.setDefault(Locale.ENGLISH);

        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        PropertyWorker pw = new PropertyWorker();
        Properties prop = pw.getStatementsProperties();
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement psUser = con.prepareStatement(prop.getProperty("SQL_ADD_NEW_USER"));
            psUser.setString(1, loginUser.getLogin());
            psUser.setString(2, loginUser.getFirstName());
            psUser.setString(3, loginUser.getLastName());
            psUser.setString(4, loginUser.getEmail());
            psUser.setString(5, loginUser.getPassword());
            psUser.setString(6, USER_ROLE);
            psUser.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean isLogged(User user) {
        Locale.setDefault(Locale.ENGLISH);


        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        PropertyWorker pw = new PropertyWorker();
        Properties prop = pw.getStatementsProperties();
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(prop.getProperty("SQL_WAS_LOGGED"));
            preparedStatement.setString(1, user.getLogin());
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.isBeforeFirst()) {
               return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return true;
    }






    @Override
    public boolean checkLogIn(User user) {
        Locale.setDefault(Locale.ENGLISH);

        User checkedUser = new User();
        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        PropertyWorker pw = new PropertyWorker();
        Properties prop = pw.getStatementsProperties();
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(prop.getProperty("SQL_WAS_LOGGED"));
            preparedStatement.setString(1, user.getLogin());
            ResultSet rs = preparedStatement.executeQuery();

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
            e.printStackTrace();
        }

        return false;

    }


    @Override
    public List<User> getAllLogged(int count) {
        Locale.setDefault(Locale.ENGLISH);

        List<User> loggedUsers = new ArrayList<>();
        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        PropertyWorker pw = new PropertyWorker();
        Properties prop = pw.getStatementsProperties();
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(prop.getProperty("SQL_GET_USER_LIST"));
            preparedStatement.setInt(1, count);
                      ResultSet rs = preparedStatement.executeQuery();
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
            e.printStackTrace();
        }
        return loggedUsers;

    }

    @Override
    public User getUser(User user) {
        Locale.setDefault(Locale.ENGLISH);

        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        PropertyWorker pw = new PropertyWorker();
        Properties prop = pw.getStatementsProperties();
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(prop.getProperty("SQL_GET_USER"));
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("ID"));
                user.setFirstName(rs.getString("FIRSTNAME"));
                user.setLastName(rs.getString("LASTNAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setRole(rs.getString("ROLE"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;

    }


    public static void main(String[] args) {
        DAOFactory dao = DAOFactory.getDAOFactory();
//        UserDAO userDAO = dao.getUserDAO();
//        userDAO.getAllLogged().forEach(System.out::println);

        MessageDAO userDAO = dao.getMessageDAO();
        userDAO.getLastMessages(100).forEach(System.out::println);

    }


}