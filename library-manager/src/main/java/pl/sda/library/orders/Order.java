package pl.sda.library.orders;

import pl.sda.commons.Utils;
import pl.sda.library.books.Book;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private int userId;
    private Book book;
    private LocalDateTime orderDate;
    private LocalDateTime returnDate;

    public Order(int id, int userId, Book book, LocalDateTime orderDate, LocalDateTime returnDate) {
        this.id = id;
        this.userId = userId;
        this.book = book;
        this.orderDate = orderDate;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public Book getBook() {
        return book;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(book.toString());
        stringBuilder.append(", data wypożyczenia: ").append(Utils.dateFormat(orderDate));
        if(returnDate != null) {
            stringBuilder.append(", data zwrotu: ").append(Utils.dateFormat(returnDate));
        }

        return stringBuilder.toString();
    }
}