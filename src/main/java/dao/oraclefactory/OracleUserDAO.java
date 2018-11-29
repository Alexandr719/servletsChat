package dao.oraclefactory;

import dao.UserDAO;
import entity.User;

import java.util.List;

public class OracleUserDAO implements UserDAO {
    @Override
    public void login(User loginingUser) {

    }

    @Override
    public boolean isLogged(User user) {
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
}