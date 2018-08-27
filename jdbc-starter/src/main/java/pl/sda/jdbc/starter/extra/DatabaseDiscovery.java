package pl.sda.jdbc.starter.extra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.jdbc.starter.ConnectionFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseDiscovery {
    private static Logger logger = LoggerFactory.getLogger(DatabaseDiscovery.class);

    public static void main(String[] args) throws SQLException {
        try(Connection connection = new ConnectionFactory().getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            logger.info("Database name: {}", connection.getCatalog());

            ResultSet resultSet = metaData.getColumns(null, null, null, null);
            while(resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                String columnName = resultSet.getString("COLUMN_NAME");
                logger.info("table: {}, columnName: {}", tableName, columnName);
            }
        }
    }

}
