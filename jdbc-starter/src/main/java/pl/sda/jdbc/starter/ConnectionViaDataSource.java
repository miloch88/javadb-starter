package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionViaDataSource {
    private static Logger logger = LoggerFactory.getLogger(ConnectionViaDataSource.class);

    public static void main(String[] args) {
        logger.info("Connecting to a selected database...");
        try (Connection connection = new ConnectionFactory().getConnection()){
            logger.info("Connected database successfully...");
            logger.info("Connection = " + connection);
            logger.info("Database name = " + connection.getCatalog());
        } catch (SQLException e) {
            logger.error("Error during using connection", e);
        }
    }
}
