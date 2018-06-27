package pl.sda.library.books;

import java.util.ArrayList;
import java.util.List;

public class JdbcBooksDao implements IBooksDao {
    @Override
    public List<Book> list() {
        return new ArrayList<>();
    }

    @Override
    public void add(Book book) {
    }

    @Override
    public void delete(int bookId) {
    }

    @Override
    public List<Category> listCategories() {
        return new ArrayList<>();
    }
}