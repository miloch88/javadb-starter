package pl.sda.jdbc.starter;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

    //Tutaj może być singleton, ale my tworzymy coś innego

    private MysqlDataSource dataSource;

    public ConnectionFactory(String filename) {

        try {

            Properties properties = getDataBaseProperties(filename);

            dataSource = new MysqlDataSource();
            dataSource.setServerName(properties.getProperty("pl.sda.jdbc.db.server"));
            dataSource.setDatabaseName(properties.getProperty("pl.sda.jdbc.db.name"));
            dataSource.setUser(properties.getProperty("pl.sda.jdbc.db.user"));
            dataSource.setPassword(properties.getProperty("pl.sda.jdbc.db.password"));
            String portString = properties.getProperty("pl.sda.jdbc.db.port");
            dataSource.setPort(Integer.parseInt(portString));
            dataSource.setServerTimezone("Europe/Warsaw");
            dataSource.setUseSSL(false);
            dataSource.setCharacterEncoding("UTF-8");
        } catch (SQLException e) {
            logger.error("Error during creating MysqlDataSource", e);
            return;
        }
    }

    public ConnectionFactory(){
        //crtl + spacja szuka
        this("/database.properties");
    }

    private Properties getDataBaseProperties(String filename) {
        Properties properties = new Properties();
        try {
            /**
             * Pobieramy zawartość pliku za pomocą classloadera, plik musi znajdować się w katalogu ustawionym w CLASSPATH
             */
            InputStream propertiesStream = ConnectionFactory.class.getResourceAsStream(filename);
            if (propertiesStream == null) {
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

    //Taki który trzeba obsłyżć check "Niech się inni martwią"
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void main(String[] args) throws SQLException {

        ConnectionFactory connectionFactory =new ConnectionFactory("/remote-database.properties");
        Connection connection = connectionFactory.getConnection();
        logger.info(connection.getCatalog());

    }
}