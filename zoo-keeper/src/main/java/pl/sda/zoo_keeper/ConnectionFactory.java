package pl.sda.zoo_keeper;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
    private static DataSource dataSource;

    public ConnectionFactory(String filename) {
        try {
            Properties properties = getDataBaseProperties(filename);
            dataSource = getDataSource(properties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Properties getDataBaseProperties(String filename) {
        Properties properties = new Properties();
        try {
            InputStream propertiesStream = ConnectionFactory.class.getResourceAsStream(filename);
            properties.load(propertiesStream);
        } catch (IOException e) {
            logger.error("Error during fetching properties for database", e);
            return null;
        }

        return properties;
    }

    private DataSource getDataSource(Properties properties) throws SQLException {
        String databaseName = properties.getProperty("pl.sda.jdbc.db.name");
        String serverName = properties.getProperty("pl.sda.jdbc.db.server");
        String user = properties.getProperty("pl.sda.jdbc.db.user");
        String password = properties.getProperty("pl.sda.jdbc.db.password");
        int port = Integer.valueOf(properties.getProperty("pl.sda.jdbc.db.port"));

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(serverName);
        dataSource.setDatabaseName(databaseName);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setPort(port);
        dataSource.setCharacterEncoding("UTF-8");
        dataSource.setServerTimezone("Europe/Warsaw");
        dataSource.setUseSSL(false);

        return dataSource;
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource is not created!");
        }
        return dataSource.getConnection();
    }
}