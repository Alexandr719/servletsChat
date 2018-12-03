package dao.oraclefactory;

import dao.PropertyWorker;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.log4j.Logger;


import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;



class DataSourceFactory {
    private final static Logger logger = Logger.getLogger(DataSourceFactory.class);

    static DataSource getOracleDataSource() {
        PropertyWorker pw = new PropertyWorker();
        Properties props = pw.getDBProperties();
        OracleDataSource oracleDS = null;
        try {
            oracleDS = new OracleDataSource();
            oracleDS.setURL(props.getProperty("ORACLE_DB_URL"));
            oracleDS.setUser(props.getProperty("ORACLE_DB_USERNAME"));
            oracleDS.setPassword(props.getProperty("ORACLE_DB_PASSWORD"));
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return oracleDS;
    }



}