package pl.sda.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

        ConnectionFactory cf = new ConnectionFactory();
        Connection connection = cf.getConnection();
        logger.info(connection.getCatalog());
    }
}
