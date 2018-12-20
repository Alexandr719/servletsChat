package com.epam.chat.dao;

import com.epam.chat.dao.oraclefactory.OracleMessageDAO;
import com.epam.chat.dao.oraclefactory.OracleUserDAO;

/**
 * OracleDAOFactory
 *
 * @author Alexander_Filatov
 */
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