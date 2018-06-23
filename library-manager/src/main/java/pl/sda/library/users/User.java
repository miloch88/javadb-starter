package pl.sda.library.users;

public class User {
    private Integer id;
    private String login;
    private String password;
    private String name;
    private boolean admin;

    public User(Integer id, String login, String password, String name, boolean admin) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.admin = admin;
    }

    public User(String login, String password, String name, boolean admin) {
        this(null, login, password, name, admin);
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return admin;
    }

    @Override
    public String toString() {
        return String.format("%s, %s:%s [%s]", id, login, password, admin? "admin" : "czytelnik");
    }
}
