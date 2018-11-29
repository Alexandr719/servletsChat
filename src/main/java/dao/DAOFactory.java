package dao;

public abstract class DAOFactory {

    public abstract MessageDAO getMessageDAO();

    public abstract UserDAO getUserDAO();

    public static DAOFactory getDAOFactory() {

        return new OracleDAOFactory();

    }
}