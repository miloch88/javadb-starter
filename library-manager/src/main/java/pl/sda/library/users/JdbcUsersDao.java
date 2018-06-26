package pl.sda.library.users;

import java.util.List;

public class JdbcUsersDao implements IUsersDao {

    @Override
    public User findUser(String login, String password) {
        try (Connection connection = DatabaseManager.CONNECTION_FACTORY.getConnection();
             Statement statement = connection.createStatement()) {
            String format = String.format("SELECT id, login, password, name, is_admin FROM users WHERE login='%s' AND password='%s';", login, password);
            ResultSet resultSet = statement.executeQuery(format);
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                login = resultSet.getString("login");
                password = resultSet.getString("password");
                String name = resultSet.getString("name");
                boolean isAdmin = resultSet.getBoolean("is_admin");
                return new User(id, login, password, name, isAdmin);
            }
            return null;
        } catch (SQLException e) {
            logger.error("", e);
        }

        return null;
    }

    @Override
    public List<User> list(UserParameters userParameters) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, login, password, name, is_admin FROM users";
        try (Connection connection = DatabaseManager.CONNECTION_FACTORY.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            //przygotowanie listy filtrów do dodania do klauzuli WHERE
            List<String> filters = new ArrayList<>();
            if (userParameters.hasId()) {
                filters.add("id = ?");
            }

            if (userParameters.hasLogin()) {
                filters.add("login = ?");
            }

            if (userParameters.hasPassword()) {
                filters.add("password = ?");
            }

            if (!filters.isEmpty()) {
                sql += " WHERE " + filters.stream().collect(Collectors.joining(" AND "));
            }

            logger.info("SQL: " + sql);

            //zmienna i pozwala nam dodać parametry w odpowiedniej kolejności
            int i = 1;
            if (userParameters.hasId()) {
                statement.setInt(i++, userParameters.getId());
            }
            if (userParameters.hasLogin()) {
                statement.setString(i++, userParameters.getLogin());
            }
            if (userParameters.hasPassword()) {
                statement.setString(i, userParameters.getPassword());
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int bookId = resultSet.getInt("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String name = resultSet.getString("name");
                boolean isAdmin = resultSet.getBoolean("is_admin");

                users.add(new User(bookId, login, password, name, isAdmin));
            }
        } catch (SQLException e) {
            logger.error("", e);
        }

        return users;
    }

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO users(login, password, name, is_admin) VALUES(?, ?, ?, ?);";
        try (Connection connection = DatabaseManager.CONNECTION_FACTORY.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setBoolean(4, user.isAdmin());

            statement.executeUpdate();

            logger.info("User added, user:{}", user);
        } catch (SQLException e) {
            logger.error("", e);
        }
    }

    @Override
    public void deleteUser(int userId) {
    }
}