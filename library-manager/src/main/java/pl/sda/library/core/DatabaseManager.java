package pl.sda.library.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.jdbc.starter.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    public static final ConnectionFactory CONNECTION_FACTORY = new ConnectionFactory("/library-database.properties");
    private static Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

    public static void createDb() throws SQLException {
        try (Connection connection = CONNECTION_FACTORY.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS library.users (\n" +
                    "  id INT NOT NULL AUTO_INCREMENT,\n" +
                    "  login VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,\n" +
                    "  password VARCHAR(45) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,\n" +
                    "  name VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,\n" +
                    "  is_admin BOOLEAN NOT NULL\n," +
                    "PRIMARY KEY (id))\n" +
                    "  ENGINE = InnoDB;");
            logger.info("Table: library.users created!");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS library.categories (\n" +
                    "  id INT NOT NULL AUTO_INCREMENT,\n" +
                    "  name VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,\n" +
                    "PRIMARY KEY (id))\n" +
                    "  ENGINE = InnoDB;");
            logger.info("Table: library.categories created!");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS library.books (\n" +
                    "  id INT NOT NULL AUTO_INCREMENT,\n" +
                    "  category_id INT NOT NULL,\n" +
                    "  title VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,\n" +
                    "  author VARCHAR(70) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,\n" +
                    "PRIMARY KEY (id),\n" +
                    "FOREIGN KEY (category_id)\n" +
                    "REFERENCES library.categories(id)\n" +
                    "ON DELETE NO ACTION\n" +
                    "ON UPDATE NO ACTION)\n" +
                    "  ENGINE = InnoDB;");
            logger.info("Table: library.books created!");


            statement.executeUpdate("CREATE TABLE IF NOT EXISTS library.orders (\n" +
                    "  id INT NOT NULL AUTO_INCREMENT,\n" +
                    "  user_id INT NOT NULL,\n" +
                    "  book_id INT NOT NULL,\n" +
                    "  order_date DATETIME NOT NULL,\n" +
                    "  return_date DATETIME NULL,\n" +
                    "PRIMARY KEY (id),\n" +
                    "FOREIGN KEY (user_id)\n" +
                    "REFERENCES library.users(id)\n" +
                    "ON DELETE NO ACTION\n" +
                    "ON UPDATE NO ACTION,\n" +
                    "FOREIGN KEY (book_id)\n" +
                    "REFERENCES library.books(id)\n" +
                    "ON DELETE NO ACTION\n" +
                    "ON UPDATE NO ACTION)\n" +
                    "  ENGINE = InnoDB;");
            logger.info("Table: library.orders created!");
        }
    }

    public static void initializeDb() throws SQLException {
        try (Connection connection = CONNECTION_FACTORY.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO categories(name) VALUES('poezja')");
            statement.executeUpdate("INSERT INTO categories(name) VALUES('proza')");
            statement.executeUpdate("INSERT INTO categories(name) VALUES('kryminał')");
            logger.info("Table: library.categories initialized!");

            statement.executeUpdate("INSERT INTO books(category_id, title, author) VALUES(1, 'Pan Tadeusz', 'Adam Mickiewicz')");
            statement.executeUpdate("INSERT INTO books(category_id, title, author) VALUES(1, 'Balladyna', 'Juliusz Słowacki')");
            statement.executeUpdate("INSERT INTO books(category_id, title, author) VALUES(2, 'Ogniem i mieczem', 'Henryk Sienkiewicz')");
            statement.executeUpdate("INSERT INTO books(category_id, title, author) VALUES(3, 'Morderstwo w Orient Expressie', 'Agatha Christie')");
            logger.info("Table: library.books initialized!");

            statement.executeUpdate("INSERT INTO users(login, password, name, is_admin) VALUES('root', 'root', 'Pan Administrator', true)");
            statement.executeUpdate("INSERT INTO users(login, password, name, is_admin) VALUES('jarek', '123', 'Jarek', false)");
            logger.info("Table: library.users initialized!");
        }
    }

    public static void initializeDb2() throws SQLException {
        try (Connection connection = CONNECTION_FACTORY.getConnection();
             PreparedStatement categoriesInsert = connection.prepareStatement("INSERT INTO categories(name) VALUES(?)");
             PreparedStatement booksInsert = connection.prepareStatement("INSERT INTO books(category_id, title, author) VALUES(?, ?, ?)");
             PreparedStatement usersInsert = connection.prepareStatement("INSERT INTO users(login, password, name, is_admin) VALUES(?, ?, ?, ?)")
        ) {
            categoriesInsert.setString(1, "poezja");
            categoriesInsert.executeUpdate();
            categoriesInsert.setString(1, "proza");
            categoriesInsert.executeUpdate();
            categoriesInsert.setString(1, "kryminał");
            categoriesInsert.executeUpdate();
            logger.info("Table: library.categories initialized!");

            booksInsert.setInt(1, 1);
            booksInsert.setString(2, "Pan Tadeusz");
            booksInsert.setString(3, "Adam Mickiewicz");
            booksInsert.executeUpdate();

            booksInsert.setInt(1, 1);
            booksInsert.setString(2, "Balladyna");
            booksInsert.setString(3, "Juliusz Słowacki");
            booksInsert.executeUpdate();

            booksInsert.setInt(1, 2);
            booksInsert.setString(2, "Ogniem i mieczem");
            booksInsert.setString(3, "Henryk Sienkiewicz");
            booksInsert.executeUpdate();

            booksInsert.setInt(1, 3);
            booksInsert.setString(2, "Morderstwo w Orient Expressie");
            booksInsert.setString(3, "Agatha Christie");
            booksInsert.executeUpdate();
            logger.info("Table: library.books initialized!");

            usersInsert.setString(1, "root");
            usersInsert.setString(2, "root");
            usersInsert.setString(3, "Pan Administrator");
            usersInsert.setBoolean(4, true);
            usersInsert.executeUpdate();

            usersInsert.setString(1, "jarek");
            usersInsert.setString(2, "123");
            usersInsert.setString(3, "Jarek");
            usersInsert.setBoolean(4, false);
            usersInsert.executeUpdate();
            logger.info("Table: library.users initialized!");
        }
    }

    public static void dropDb() throws SQLException {
        try (Connection connection = CONNECTION_FACTORY.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE orders");
            logger.info("Table: library.orders removed!");
            statement.executeUpdate("DROP TABLE books");
            logger.info("Table: library.books removed!");
            statement.executeUpdate("DROP TABLE categories");
            logger.info("Table: library.categories removed!");
            statement.executeUpdate("DROP TABLE users");
            logger.info("Table: library.users removed!");
        }
    }

    public static void main(String[] args) throws SQLException {
        createDb();
        initializeDb2();
//        dropDb();
    }
}
