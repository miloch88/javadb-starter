package pl.sda.jdbc.starter;

import com.mysql.cj.jdbc.MysqlDataSource;
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
    private static final int DB_PORT = 3306;

    public static void main(String[] args) throws SQLException {
        //Krok 1: Tworzymy obiekt klasy DataSource
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(DB_SERVER_NAME);
        dataSource.setDatabaseName(DB_NAME);
        dataSource.setUser(DB_USER);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setPort(DB_PORT);
        dataSource.setServerTimezone("Europe/Warsaw");
        dataSource.setUseSSL(false);
        dataSource.setCharacterEncoding("UTF-8");

        logger.info("Connecting to a selected database...");

        //Krok 2: Otwieramy połączenie do bazy danych
        try (Connection connection = dataSource.getConnection()){
            logger.info("Connected database successfully...");

            //Krok 3: Pobieramy informacje o bazie danych i połączeniu
            logger.info("Connection = " + connection);
            logger.info("Database name = " + connection.getCatalog());
        } catch (SQLException e) {
            logger.error("Error during using connection", e);
        }
        //Krok 4: Zawsze zamykamy połączenie po skończonej pracy! - tutaj w sposób niejawny przez mechanizm try-with-resource - dostępny od Java 7
    }
}
