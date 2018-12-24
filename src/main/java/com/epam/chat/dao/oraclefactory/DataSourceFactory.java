package com.epam.chat.dao.oraclefactory;

import com.epam.chat.mapper.ResourceInspector;
import lombok.extern.log4j.Log4j2;
import oracle.jdbc.pool.OracleDataSource;

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

        OracleDataSource oracleDS = null;
        try {
            oracleDS = new OracleDataSource();
            oracleDS.setURL(ResourceInspector.getInstance()
                    .getString("ORACLE_DB_URL_HOME"));
            oracleDS.setUser(ResourceInspector.getInstance()
                    .getString("ORACLE_DB_USERNAME_HOME"));
            oracleDS.setPassword(ResourceInspector.getInstance()
                    .getString("ORACLE_DB_PASSWORD_HOME"));
        } catch (SQLException e) {
            log.error(e);
        }
        return oracleDS;
    }

}