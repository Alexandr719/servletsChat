package com.epam.chat.dao.oraclefactory;

import com.epam.chat.ChatConstants;
import lombok.extern.log4j.Log4j2;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * DataSourceFactory
 *
 * @author Alexander_Filatov
 */
@Log4j2
class DataSourceFactory {

    /** Create connection to oracle DataBase
     * @return DataSource
     */
    static DataSource getOracleDataSource() {
        Context ctx = null;
        DataSource ds = null;
        try {
            ctx = new InitialContext();
            ds = (DataSource) ctx.lookup(ChatConstants.DATA_SOURSE_PATH);
        } catch (NamingException e) {
           log.error("Can't create ds connection", e);
        }
        return ds;
    }

}