package pl.sda.library.gui;

import com.google.common.collect.Lists;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import pl.sda.commons.Utils;
import pl.sda.library.books.Book;
import pl.sda.library.books.Category;
import pl.sda.library.core.LibraryService;
import pl.sda.library.orders.Order;
import pl.sda.library.users.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Text IO driven GUI - documentation: http://text-io.beryx.org/releases/latest/#introduction
 */
public class LibraryManager {
    private static final String BEGIN_BOOKMARK = "BEGIN";
    private static final String NEW_MENU_BOOKMARK = "NEW_MENU";
    private TextIO textIO = TextIoFactory.getTextIO();
    private LibraryService libraryService = new LibraryService();

    private User currentUser;

    public void runIt() {
        while (true) {
            textIO.getTextTerminal().setBookmark(BEGIN_BOOKMARK);
            currentUser = null;
            while (currentUser == null) {
                textIO.getTextTerminal().resetToBookmark(BEGIN_BOOKMARK);
                currentUser = login();
            }
            textIO.getTextTerminal().resetToBookmark(BEGIN_BOOKMARK);
            printHeader();
            if (currentUser.isAdmin()) {
                printAdminMenu();
            } else {
                printUserMenu();
            }
            textIO.getTextTerminal().resetToBookmark(BEGIN_BOOKMARK);
        }
    }

    private void printHeader() {
        textIO.getTextTerminal().printf("Witaj %s, data logowania: %s\n", currentUser.getName(), Utils.dateFormat(LocalDateTime.now()));
        textIO.getTextTerminal().setBookmark(NEW_MENU_BOOKMARK);
    }

    private void printAdminMenu() {
        int option = 1;
        while (option > 0) {
            TextTerminal<?> textTerminal = textIO.getTextTerminal();
            textTerminal.resetToBookmark(NEW_MENU_BOOKMARK);
            textTerminal.println();
            textTerminal.print(Lists.newArrayList(
                    "Menu:",
                    "1. Lista wszystkich użytkowników",
                    "2. Dodaj użytkownika",
                    "3. Usuń użytkownika",
                    "4. Lista wszystkich kategorii książek",
                    "5. Lista wszystkich książek",
                    "6. Dodaj książkę",
                    "7. Usuń książkę",
                    "0. Wyloguj",
                    ""
            ));
            textTerminal.println();

            option = textIO.newIntInputReader()
                    .withMinVal(0)
                    .withMaxVal(7)
                    .read("Twój wybór");

            switch (option) {
                case 1:
                    printUsers();
                    break;
                case 2:
                    addUser();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 4:
                    printCategories();
                    break;
                case 5:
                    printBooks();
                    break;
                case 6:
                    addBook();
                    break;
                case 7:
                    deleteBook();
                    break;
            }
        }
    }

    private void printUserMenu() {
        int option = 1;
        while (option > 0) {
            TextTerminal<?> textTerminal = textIO.getTextTerminal();
            textTerminal.resetToBookmark(NEW_MENU_BOOKMARK);
            textTerminal.println();
            textTerminal.print(Lists.newArrayList(
                    "Menu:",
                    "1. Lista wszystkich książek",
                    "2. Lista wypożyczonych książek",
                    "3. Wypożycz książkę",
                    "4. Zwróc książkę",
                    "0. Wyloguj",
                    ""
            ));
            textTerminal.println();

            option = textIO.newIntInputReader()
                    .withMinVal(0)
                    .withMaxVal(4)
                    .read("Twój wybór");

            switch (option) {
                case 1:
                    printBooks();
                    break;
                case 2:
                    printOrders();
                    break;
                case 3:
                    addOrder();
                    break;
                case 4:
                    returnBook();
                    break;
            }
        }
    }

    private void printUsers() {
        List<User> books = libraryService.findUsers();
        printList(books);
    }

    private void addUser() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.resetToBookmark(NEW_MENU_BOOKMARK);

        String login = textIO.newStringInputReader()
                .read("Login: ");
        String password = textIO.newStringInputReader()
                .read("Hasło: ");
        String name = textIO.newStringInputReader()
                .read("Nazwa: ");
        boolean isAdmin = textIO.newBooleanInputReader()
                .read("Czy administrator: ");

        libraryService.addUser(login, password, name, isAdmin);
    }

    private void deleteUser() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.resetToBookmark(NEW_MENU_BOOKMARK);

        int userId = textIO.newIntInputReader()
                .read("Podaj id usera:");
        libraryService.deleteUser(userId);
    }

    private void printCategories() {
        List<Category> categories = libraryService.findCategories();
        printList(categories);
    }

    private void printBooks() {
        List<Book> books = libraryService.findBooks();
        printList(books);
    }

    private void addBook() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.resetToBookmark(NEW_MENU_BOOKMARK);

        String title = textIO.newStringInputReader()
                .read("Tytuł: ");
        String author = textIO.newStringInputReader()
                .read("Autor: ");
        int categoryId = textIO.newIntInputReader()
                .read("Id kategorii: ");

        libraryService.addBook(title, author, categoryId);
    }

    private void deleteBook() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.resetToBookmark(NEW_MENU_BOOKMARK);

        int bookId = textIO.newIntInputReader()
                .read("Podaj id książki:");
        libraryService.deleteBook(bookId);
    }

    private void printOrders() {
        List<Order> orders = libraryService.findOrders(currentUser.getId());
        List<String> messages = new ArrayList<>();
        messages.add("Historia wypożyczeń:");
        orders.stream()
                .filter(order -> order.getReturnDate() != null)
                .forEach(order -> messages.add(order.toString()));

        messages.add("");
        messages.add("Aktualne wypożyczenia:");
        orders.stream()
                .filter(order -> order.getReturnDate() == null)
                .forEach(order -> messages.add(order.toString()));
        printList(messages);
    }

    private void printList(List<?> list) {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.resetToBookmark(NEW_MENU_BOOKMARK);
        List<String> messages = new ArrayList<>();
        if (list.isEmpty()) {
            messages.add("Lista jest pusta.");
        } else {
            messages.addAll(list.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList()));
        }

        textTerminal.println();
        textTerminal.print(messages);
        textTerminal.println();
        textTerminal.println();
        textIO.newIntInputReader()
                .withMinVal(0)
                .withMaxVal(0)
                .read("Naciśnij 0 żeby wrócić");
    }

    private void addOrder() {
        int bookId = textIO.newIntInputReader()
                .read("Podaj id książki:");
        libraryService.addOrder(currentUser.getId(), bookId, LocalDateTime.now());
    }

    private void returnBook() {
        int bookId = textIO.newIntInputReader()
                .read("Podaj id książki:");
        libraryService.returnBook(currentUser.getId(), bookId);
    }

    private User login() {
        String login = textIO.newStringInputReader()
                .read("Login");
        String password = textIO.newStringInputReader()
                .withInputMasking(true)
                .read("Hasło");

        return libraryService.findUser(login, password);
    }

    public static void main(String[] args) {
        new LibraryManager().runIt();
        System.exit(0);
    }
}
