package pl.sda.library.orders;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrdersDao {
    void addOrder(int userId, int bookId, LocalDateTime dateTime);

    List<Order> findOrders(OrderParameters orderParameters);

    void updateReturnDate(int id, LocalDateTime now);
}
