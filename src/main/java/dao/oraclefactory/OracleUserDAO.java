package dao.oraclefactory;

import dao.DAOFactory;
import dao.PropertyWorker;
import dao.UserDAO;
import entity.User;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

@Log4j2
public class OracleUserDAO implements UserDAO {

    private static final String USER_ROLE = "USER";
    private static final String ADMIN_ROLE = "ADMIN";


    @Override
    public void login(User loginingUser) {
        Locale.setDefault(Locale.ENGLISH);

        DataSource dataSource = DataSourceFactory.getOracleDataSource();
        PropertyWorker pw = new PropertyWorker();
        Properties prop = pw.getStatementsProperties();
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement psUser = con.prepareStatement(prop.getProperty("SQL_ADD_NEW_USER"));
            psUser.setString(1, loginingUser.getLogin());
            psUser.setString(2, loginingUser.getFirstName());
            psUser.setString(3, loginingUser.getLastName());
            psUser.setString(4, loginingUser.getEmail());
            psUser.setString(5, loginingUser.getPassword());
            psUser.setString(6, USER_ROLE);
            psUser.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//TODO finally

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
        return false;
    }

    @Override
    public void kick(User kickableUser) {

    }

    @Override
    public void unkick(User user) {

    }

    @Override
    public boolean isKicked(User user) {
        return false;
    }

    @Override
    public void logout(User logoutingUser) {

    }

    @Override
    public List<User> getAllLogged() {
        return null;
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

    @Override
    public void updateUser(User user) {

    }


    public static void main(String[] args) {
        DAOFactory dao = DAOFactory.getDAOFactory();
        UserDAO userDAO = dao.getUserDAO();
        userDAO.login(new User());

    }


}