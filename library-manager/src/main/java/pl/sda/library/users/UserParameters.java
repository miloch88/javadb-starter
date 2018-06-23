package pl.sda.library.users;

public class UserParameters {
    private Integer id;
    private String login;
    private String password;

    private UserParameters(Integer id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public static UserParameters empty() {
        return new UserParameters(null, null, null);
    }

    public static UserParameters withId(Integer id) {
        return new UserParameters(id, null, null);
    }

    public static UserParameters withLoginAndPassword(String login, String password) {
        return new UserParameters(null, login, password);
    }

    public Integer getId() {
        return id;
    }

    public boolean hasId() {
        return id != null;
    }

    public String getLogin() {
        return login;
    }

    public boolean hasLogin() {
        return login != null;
    }

    public String getPassword() {
        return password;
    }

    public boolean hasPassword() {
        return password != null;
    }
}
