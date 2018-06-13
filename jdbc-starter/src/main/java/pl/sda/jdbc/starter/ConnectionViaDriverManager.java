package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionViaDriverManager {
    private static Logger logger = LoggerFactory.getLogger(ConnectionViaDriverManager.class);

    private static final String DB_URL = "";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        try {
            //STEP 1: Register JDBC driver - optional since JDBC 4.0
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Error during loading db driver", e);
        }

        //STEP 2: Open a connection
        logger.info("Connecting to a selected database...");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            logger.info("Connected database successfully...");

            //STEP 3: Get db info
            logger.info("connection = " + connection);
            logger.info("catalog = " + connection.getCatalog());
        } catch (SQLException e) {
            logger.error("Error during using connection", e);
        } finally {
            //STEP 4: Close connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error("Error during closing connection", e);
            }
        }
    }
}