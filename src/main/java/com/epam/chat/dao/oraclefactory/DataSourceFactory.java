package com.epam.chat.dao.oraclefactory;

import com.epam.chat.mapper.ResourceInspector;
import lombok.extern.log4j.Log4j2;
import oracle.jdbc.pool.OracleDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;


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
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/MyLocalDB");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return ds;
    }

}