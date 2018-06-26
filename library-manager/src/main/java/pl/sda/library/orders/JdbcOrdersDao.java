package pl.sda.library.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.library.books.Book;
import pl.sda.library.core.DatabaseManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JdbcOrdersDao implements IOrdersDao {
    private static Logger logger = LoggerFactory.getLogger(JdbcOrdersDao.class);

    @Override
    public void addOrder(int userId, int bookId, LocalDateTime dateTime) {
        String sql = "INSERT INTO orders(user_id, book_id, order_date) VALUES(?, ?, ?);";
        try (Connection connection = DatabaseManager.CONNECTION_FACTORY.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, bookId);
            statement.setTimestamp(3, Timestamp.valueOf(dateTime));

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("", e);
        }
    }

    public List<Order> findOrders(OrderParameters orderParameters) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.id, user_id, book_id, title, author, c.id AS category_id, c.name AS category, order_date, return_date " +
                "FROM orders o " +
                "JOIN books b ON o.book_id=b.id " +
                "JOIN categories c ON b.category_id=c.id";

        //przygotowanie listy filtrów do dodania do klauzuli WHERE
        List<String> filters = new ArrayList<>();
        if (orderParameters.hasUserId()) {
            filters.add("o.user_id = ?");
        }

        if (orderParameters.hasBookId()) {
            filters.add("o.book_id = ?");
        }

        if (!filters.isEmpty()) {
            sql += " WHERE " + filters.stream().collect(Collectors.joining(" AND "));
        }

        logger.info("SQL: " + sql);

        try (Connection connection = DatabaseManager.CONNECTION_FACTORY.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            //zmienna i pozwala nam dodać parametry w odpowiedniej kolejności
            int i = 1;
            if (orderParameters.hasUserId()) {
                statement.setInt(i++, orderParameters.getUserId());
            }
            if (orderParameters.hasBookId()) {
                statement.setInt(i, orderParameters.getBookId());
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int bookId = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int categoryId = resultSet.getInt("category_id");
                String category = resultSet.getString("category");
                Timestamp orderTimestamp = resultSet.getTimestamp("order_date");
                Timestamp returnTimestamp = resultSet.getTimestamp("return_date");

                LocalDateTime orderDate = orderTimestamp == null ? null : orderTimestamp.toLocalDateTime();
                LocalDateTime returnDate = returnTimestamp == null ? null : returnTimestamp.toLocalDateTime();
                orders.add(new Order(orderId, userId, new Book(bookId, title, author, categoryId, category), orderDate, returnDate));
            }
        } catch (SQLException e) {
            logger.error("", e);
        }

        return orders;
    }

    @Override
    public void updateReturnDate(int id, LocalDateTime returnDate) {
        String sql = "UPDATE orders SET return_date=? WHERE id=?;";
        try (Connection connection = DatabaseManager.CONNECTION_FACTORY.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setTimestamp(1, Timestamp.valueOf(returnDate));
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("", e);
        }
    }
}