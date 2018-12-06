package dao.oraclefactory;

import dao.PropertyWorker;
import lombok.extern.log4j.Log4j2;
import oracle.jdbc.pool.OracleDataSource;



import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;


@Log4j2
class DataSourceFactory {

    static DataSource getOracleDataSource() {
        log.info("get oracle ds");
        PropertyWorker pw = new PropertyWorker();
        Properties props = pw.getDBProperties();
        log.info(props);
        OracleDataSource oracleDS = null;
        try {
            oracleDS = new OracleDataSource();
            oracleDS.setURL(props.getProperty("ORACLE_DB_URL"));
            oracleDS.setUser(props.getProperty("ORACLE_DB_USERNAME"));
            oracleDS.setPassword(props.getProperty("ORACLE_DB_PASSWORD"));
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return oracleDS;
    }

}