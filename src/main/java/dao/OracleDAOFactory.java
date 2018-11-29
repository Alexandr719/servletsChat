package dao;

import dao.oraclefactory.OracleMessageDAO;
import dao.oraclefactory.OracleUserDAO;

public class OracleDAOFactory extends DAOFactory {

    @Override
    public MessageDAO getMessageDAO() {
        return new OracleMessageDAO();
    }

    @Override
    public UserDAO getUserDAO() {
        return new OracleUserDAO();
    }
}