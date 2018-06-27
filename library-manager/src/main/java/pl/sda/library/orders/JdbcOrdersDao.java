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
        return null;
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