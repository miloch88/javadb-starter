package pl.sda.library.books;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.library.core.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcBooksDao implements IBooksDao {
    private static Logger logger = LoggerFactory.getLogger(JdbcBooksDao.class);

    @Override
    public List<Book> list() {
        List<Book> books = new ArrayList<>();
        //zapytanie o wrzystkie książki + wyciągniecie kategorii w postaci słownej
        String sql = "SELECT b.id, title, author, c.id category_id, c.name category " +
                "FROM books b " +
                "JOIN categories c ON b.category_id=c.id " +
                "ORDER BY id";
        try (Connection connection = DatabaseManager.CONNECTION_FACTORY.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int bookId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int categoryId = resultSet.getInt("category_id");
                String category = resultSet.getString("category");

                books.add(new Book(bookId, title, author, categoryId, category));
            }
        } catch (SQLException e) {
            logger.error("", e);
        }

        return books;
    }

    @Override
    public void add(Book book) {
        String sql = "INSERT INTO books(title, author, category_id) VALUES(?, ?, ?);";
        try (Connection connection = DatabaseManager.CONNECTION_FACTORY.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getCategoryId());

            statement.executeUpdate();

            logger.info("Book added, book:{}", book);
        } catch (SQLException e) {
            logger.error("", e);
        }
    }

    @Override
    public void delete(int bookId) {
        String sql = "DELETE FROM books WHERE id=?";
        try (Connection connection = DatabaseManager.CONNECTION_FACTORY.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bookId);

            statement.executeUpdate();

            logger.info("Book deleted, id:{}", bookId);
        } catch (SQLException e) {
            logger.error("", e);
        }
    }

    @Override
    public List<Category> listCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name FROM categories";
        try (Connection connection = DatabaseManager.CONNECTION_FACTORY.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                categories.add(new Category(id, name));
            }
        } catch (SQLException e) {
            logger.error("", e);
        }

        return categories;
    }
}