package pl.sda.library.core;

import pl.sda.library.books.Book;
import pl.sda.library.books.Category;
import pl.sda.library.books.IBooksDao;
import pl.sda.library.orders.IOrdersDao;
import pl.sda.library.orders.Order;
import pl.sda.library.orders.OrderParameters;
import pl.sda.library.users.IUsersDao;
import pl.sda.library.users.User;
import pl.sda.library.users.UserParameters;

import java.time.LocalDateTime;
import java.util.List;

public class LibraryService {
    private IUsersDao usersDao = DaoFactory.getUsersDao();
    private IBooksDao booksDao = DaoFactory.getBooksDao();
    private IOrdersDao ordersDao = DaoFactory.getOrdersDao();

    public User findUser(String login, String password) {
        User user = usersDao.findUser(login, password);
        if(user == null) {
            return null;
        }

        return user;
    }

    public List<User> findUsers() {
        return usersDao.list(UserParameters.empty());
    }

    public void deleteUser(int userId) {
        usersDao.deleteUser(userId);
    }

    public void addUser(String login, String password, String name, boolean isAdmin) {
        User user = new User(login, password, name, isAdmin);
        usersDao.addUser(user);
    }

    public List<Order> findOrders(int userId) {
        return ordersDao.findOrders(OrderParameters.withUser(userId));
    }

    public void addOrder(int userId, int bookId, LocalDateTime dateTime) {
        ordersDao.addOrder(userId, bookId, dateTime);
    }

    public void returnBook(int userId, int bookId) {
        List<Order> orders = ordersDao.findOrders(OrderParameters.withUserAndBook(userId, bookId));
        if(!orders.isEmpty()) {
            Order order = orders.get(0);
            ordersDao.updateReturnDate(order.getId(), LocalDateTime.now());
        }
    }

    public List<Book> findBooks() {
        return booksDao.list();
    }

    public void addBook(String title, String author, int categoryId) {
        booksDao.add(new Book(title, author, categoryId));
    }

    public void deleteBook(int bookId) {
        booksDao.delete(bookId);
    }

    public List<Category> findCategories() {
        return booksDao.listCategories();
    }
}