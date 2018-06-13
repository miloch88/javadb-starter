package pl.sda.jdbc.starter;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionViaDataSource {
    private static Logger logger = LoggerFactory.getLogger(ConnectionViaDataSource.class);

    private static final String DB_SERVER_NAME = "";
    private static final String DB_NAME = "";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        //STEP 1: Create a data source
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(DB_SERVER_NAME);
        dataSource.setDatabaseName(DB_NAME);
        dataSource.setUser(DB_USER);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setPort(3306);

        logger.info("Connecting to a selected database...");

        //STEP 2: Get a connection
        try (Connection connection = dataSource.getConnection()){
            logger.info("Connected database successfully...");

            //STEP 3: Get db info
            logger.info("connection = " + connection);
            logger.info("catalog = " + connection.getCatalog());
        } catch (SQLException e) {
            logger.error("Error during using connection", e);
        }
    }
}
