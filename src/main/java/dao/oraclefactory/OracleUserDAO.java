package dao.oraclefactory;

import dao.DAOFactory;
import dao.PropertyWorker;
import dao.UserDAO;
import entity.User;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class OracleUserDAO implements UserDAO {

    private DataSource dataSource = DataSourceFactory.getOracleDataSource();
    private Properties prop = null;

    @Override
    public void login(User loginingUser) {
        Locale.setDefault(Locale.ENGLISH);
        PreparedStatement psUser = null;
        Connection con = null;
        try {
            con = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PropertyWorker pw = new PropertyWorker();
        prop = pw.getStatementsProperties();
        try {
            psUser = con.prepareStatement(prop.getProperty("SQL_ADD_NEW_USER"));
            psUser.setString(1, "Log2");
            psUser.setString(2, "FIrst2");
            psUser.setString(3, "LAst2");
            psUser.setString(4, "Email2");
            psUser.setString(5, "pass2");
            psUser.setString(6, "user2");
            psUser.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }



    @Override
    public boolean isLogged(User user) {
        dataSource = DataSourceFactory.getOracleDataSource();
        return false;
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
    public User getUserByNick(String nick) {
        return null;
    }

    @Override
    public void updateUser(User user) {

    }


    public static void main(String[] args) {
        DAOFactory dao =  DAOFactory.getDAOFactory();
        UserDAO userDAO = dao.getUserDAO();
        userDAO.login(new User());


    }



}