package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionViaDriverManager {
    private static Logger logger = LoggerFactory.getLogger(ConnectionViaDriverManager.class);

    private static final String DB_URL = "jdbc:mysql://{host:localhost}:{port:3306}/{dbname:jdbc_test}?useSSL=false&serverTimezone=Europe/Warsaw";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        try {
            //Krok 1: Rejestrujemy sterownik JDBC - od wersji JDBC 4.0 krok opcjonalny
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Error during loading db driver", e);
        }

        //Krok 2: Otwieramy połączenie do bazy danych
        logger.info("Connecting to a selected database...");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            logger.info("Connected database successfully...");

            //Krok 3: Pobieramy informacje o bazie danych i połączeniu
            logger.info("Connection = " + connection);
            logger.info("Database name = " + connection.getCatalog());
        } catch (SQLException e) {
            logger.error("Error during using connection", e);
        } finally {
            //Krok 4: Zawsze zamykamy połączenie po skończonej pracy!
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