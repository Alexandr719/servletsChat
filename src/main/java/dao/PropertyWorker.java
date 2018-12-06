package dao;

import lombok.extern.log4j.Log4j2;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Log4j2
public class PropertyWorker {


    private static final String FILE_STATEMENTS_PATH = "D://Filatov/Chatv31/src/main/resources/statements.properties";
    private static final String FILE_DB_PATH = "D://Filatov/Chatv31/src/main/resources/db.properties";

    public Properties getStatementsProperties() {
        Properties props = null;
        props = getProperties(FILE_STATEMENTS_PATH);
        log.debug(props);
        return props;
    }
    public Properties getDBProperties() {
        Properties props = null;
        props = getProperties(FILE_DB_PATH);
        log.debug(props);
        return props;
    }

    private Properties getProperties(String filePath) {
        Properties props = null;
        try {
            FileInputStream fis = null;
            fis = new FileInputStream(filePath);
            props = new Properties();
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public static void main(String[] args) {
        PropertyWorker pw = new PropertyWorker();
        Properties props = null;
        props = pw.getProperties(FILE_DB_PATH);
        System.out.println(props);
    }

}
