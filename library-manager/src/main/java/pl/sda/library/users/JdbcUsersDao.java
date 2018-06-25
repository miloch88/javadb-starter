package pl.sda.library.users;

import java.util.List;

public class JdbcUsersDao implements IUsersDao {

    @Override
    public User findUser(String login, String password) {
        return null;
    }

    @Override
    public List<User> list(UserParameters userParameters) {
        return null;
    }

    @Override
    public void addUser(User user) {
    }

    @Override
    public void deleteUser(int userId) {
    }
}