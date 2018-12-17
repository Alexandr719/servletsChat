package com.epam.dao.oraclefactory;

import com.epam.dao.ResourceInspector;
import lombok.extern.log4j.Log4j2;
import oracle.jdbc.pool.OracleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;



@Log4j2
class DataSourceFactory {

    static DataSource getOracleDataSource() {

        OracleDataSource oracleDS = null;
        try {
            oracleDS = new OracleDataSource();
            oracleDS.setURL(ResourceInspector.getInstance().getString("ORACLE_DB_URL"));
            oracleDS.setUser(ResourceInspector.getInstance().getString("ORACLE_DB_USERNAME"));
            oracleDS.setPassword(ResourceInspector.getInstance().getString("ORACLE_DB_PASSWORD"));
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return oracleDS;
    }

}