package pl.sda.library.orders;

public class OrderParameters {
    private Integer userId;
    private Integer bookId;

    private OrderParameters(Integer userId, Integer bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public static OrderParameters withUserAndBook(Integer userId, Integer bookId) {
        return new OrderParameters(userId, bookId);
    }

    public static OrderParameters withUser(Integer userId) {
        return new OrderParameters(userId, null);
    }

    public Integer getUserId() {
        return userId;
    }

    public boolean hasUserId() {
        return userId != null;
    }

    public Integer getBookId() {
        return bookId;
    }

    public boolean hasBookId() {
        return bookId != null;
    }
}
