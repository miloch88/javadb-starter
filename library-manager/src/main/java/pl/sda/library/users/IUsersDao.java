package pl.sda.library.users;

import java.util.List;

public interface IUsersDao {
    User findUser(String login, String password);
    List<User> list(UserParameters userParameters);

    void addUser(User user);
    void deleteUser(int userId);
}
