package pl.sda.jdbc.starter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlInjectionSample {

    public void createTable() throws SQLException {
        try (Connection connection = new ConnectionFactory().getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute("CREATE TABLE admins (" +
                    "  id INT NOT NULL AUTO_INCREMENT, " +
                    "  login VARCHAR(50) NOT NULL, " +
                    "  password VARCHAR(45) NOT NULL, " +
                    "PRIMARY KEY (id))"
            );
        }
    }

    public String findAdmin(String login, String password) throws SQLException {
        try (Connection connection = new ConnectionFactory().getConnection();
             Statement statement = connection.createStatement()) {
            String sql = String.format("SELECT id, login, password FROM admins WHERE login='%s' AND password='%s';", login, password);
            System.out.println("sql = " + sql);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                login = resultSet.getString("login");
                password = resultSet.getString("password");
                return id + ", " + login + ", " + password;
            }
            return null;
        }
    }

    public void addAdmin(String login, String password) throws SQLException {
        try (Connection connection = new ConnectionFactory().getConnection();
             Statement statement = connection.createStatement()) {

            String sql = String.format("INSERT INTO admins(login, password) VALUES('%s', '%s');", login, password);
            System.out.println("sql = " + sql);
            statement.executeUpdate(sql);
        }
    }

    public static void main (String[] args) throws SQLException {
        SqlInjectionSample sample = new SqlInjectionSample();

        //sample.createTable();
        //sample.addAdmin("top", "secret");
        //System.out.println("admin = " + sample.findAdmin("top", "secret"));
        //System.out.println("not-admin = " + sample.findAdmin("top", "123"));

        //dodanie OR'a
        //String admin = sample.findAdmin("", "' OR 1='1");
        //System.out.println("admin = " + admin);

        //dodanie znaku początku komentarza
        //String admin = sample.findAdmin("top'#", "123");
        //System.out.println("admin = " + admin);

        //możemy nawet się zalogować nie znając loginu i hasła
        //String admin = sample.findAdmin("' OR 1=1;#", "123");
        //System.out.println("admin = " + admin);

        //MultiQueries
        //sample.addAdmin("123", "');DROP TABLE admins;#");
    }

}
