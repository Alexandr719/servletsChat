package dao.oraclefactory;

import oracle.jdbc.pool.OracleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;


public class DataSourceFactory {


    public static DataSource getOracleDataSource() {
        Properties props = new Properties();
        FileInputStream fis = null;
        OracleDataSource oracleDS = null;
        try {
            fis = new FileInputStream("src/main/resources/db.properties");
            props.load(fis);
            oracleDS = new OracleDataSource();
            oracleDS.setURL(props.getProperty("ORACLE_DB_URL"));
            oracleDS.setUser(props.getProperty("ORACLE_DB_USERNAME"));
            oracleDS.setPassword(props.getProperty("ORACLE_DB_PASSWORD"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oracleDS;
    }

//    public static void main(String[] args) {
//        DataSource  ds = DataSourceFactory.getOracleDataSource();
//        try {
//            Locale.setDefault(Locale.ENGLISH);
//            Connection con = ds.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

}