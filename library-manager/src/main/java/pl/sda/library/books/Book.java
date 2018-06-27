package pl.sda.library.books;

public class Book {
    private Integer id;
    private String title;
    private String author;
    private int categoryId;
    private String category;

    public Book(Integer id, String title, String author, int categoryId, String category) {
        this.id = id;
        this.categoryId = categoryId;
        this.category = category;
        this.title = title;
        this.author = author;
    }

    public Book(String title, String author, int categoryId) {
        this(null, title, author, categoryId, null);
    }

    public Integer getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }


    @Override
    public String toString() {
        String idString = id != null ? id + ", " : "";
        String categoryString = category != null ? "[" + category + "]" : "";
        return String.format("%s %s - %s %s", idString, title, author, categoryString);
    }
}
