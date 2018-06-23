package pl.sda.library.books;

import java.util.List;

public interface IBooksDao {
    List<Book> list();
    void add(Book book);
    void delete(int bookId);
    List<Category> listCategories();
}
