package dao;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;

public class PropertyWorker {


    private static final String FILE_STATEMENTS_PATH = "src/main/resources/statements.properties";
    private static final String FILE_DB_PATH = "src/main/resources/db.properties";

    public Properties getStatementsProperties() {
        Properties props = null;
        props = getProperties(props, FILE_STATEMENTS_PATH);

        return props;
    }
    public Properties getDBProperties() {
        Properties props = null;
        props = getProperties(props, FILE_DB_PATH);

        return props;
    }

    private Properties getProperties(Properties props, String filePath) {
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

}
