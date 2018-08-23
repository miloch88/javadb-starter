package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class ConnectionFactory {
    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
    private static DataSource dataSource;

    public ConnectionFactory(String propertiesFilename) {
        try {
            dataSource = getDataSource(propertiesFilename);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Properties getDataBaseProperties(String filename) {
        Properties properties = new Properties();
        try {
            /**
             * Pobieramy zawartość pliku za pomocą classloadera, plik musi znajdować się w katalogu ustawionym w CLASSPATH
             */
            InputStream propertiesStream = ConnectionFactory.class.getResourceAsStream(filename);
            if(propertiesStream == null) {
                throw new IllegalArgumentException("Can't find file: " + filename);
            }
            /**
             * Pobieramy dane z pliku i umieszczamy w obiekcie klasy Properties
             */
            properties.load(propertiesStream);
        } catch (IOException e) {
            logger.error("Error during fetching properties for database", e);
            return null;
        }

        return properties;
    }

    private DataSource getDataSource(String propertiesFilename) throws SQLException {
        Properties properties = new Properties();
        try {
            InputStream propertiesStream = ConnectionFactory.class.getResourceAsStream(propertiesFilename);
            if(propertiesStream == null) {
                throw new IllegalArgumentException("Can't find file: " + propertiesFilename);
            }
            properties.load(propertiesStream);
        } catch (IOException e) {
            logger.error("Error during fetching properties for database", e);
            return null;
        }

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(properties.getProperty("pl.sda.jdbc.db.server"));
        dataSource.setDatabaseName(properties.getProperty("pl.sda.jdbc.db.name"));
        dataSource.setUser(properties.getProperty("pl.sda.jdbc.db.user"));
        dataSource.setPassword(properties.getProperty("pl.sda.jdbc.db.password"));
        int port = Integer.valueOf(properties.getProperty("pl.sda.jdbc.db.port"));
        dataSource.setPort(port);
        dataSource.setCharacterEncoding("UTF-8");
        dataSource.setServerTimezone("Europe/Warsaw");
        dataSource.setUseSSL(false);
        //ustawienie tego parametru ułatwia przeprowadzenie SQLInjection typu MultiQueries
        //dataSource.setAllowMultiQueries(true);

        return dataSource;
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource is not created!");
        }
        return dataSource.getConnection();
    }

    public static void main(String[] args) {

    }
}