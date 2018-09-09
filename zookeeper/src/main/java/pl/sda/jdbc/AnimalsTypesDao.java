package pl.sda.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// klasa DAO odpowiedzialna za dostęp do bazy dancyh
public class AnimalsTypesDao {

    private static Logger logger = LoggerFactory.getLogger(AnimalsTypesDao.class);

    private ConnectionFactory cf;

    public AnimalsTypesDao(ConnectionFactory cf) {
        this.cf = cf;
    }

    public void createTable() throws SQLException {

        try (Connection connection = cf.getConnection();
             Statement statement = connection.createStatement()) {

            String query =
                    "CREATE TABLE IF NOT EXISTS animals_types(" +
                            "id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                            "name VARCHAR(50)" +
                            ");";

            statement.executeUpdate(query);
        }
    }

    public void deleteTable() throws SQLException {

        try (Connection connection = cf.getConnection();
             Statement statement = connection.createStatement()) {

            String query =
                    "DROP TABLE IF EXISTS animals_types CASCADE;";

            statement.executeUpdate(query);
        }
    }

    public void delete(AnimalType animalType) throws SQLException {

        String query = "DELETE FROM animals_types WHERE id= ? AND name= ?";

        try (Connection connection = cf.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, animalType.getId());
            preparedStatement.setString(2, animalType.getName());

            preparedStatement.executeUpdate();
        }
    }


    public void add(AnimalType animalType) throws SQLException {

        String query = "INSERT INTO animals_types (name) VALUES (?)";
        try (Connection connection = cf.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, animalType.getName());

            ps.executeUpdate();
        }
    }

    public List<AnimalType> list() throws SQLException {

        List<AnimalType> list = new ArrayList<>();
        String query = "SELECT * FROM animals_types;";

        try (Connection connection = cf.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                AnimalType animalType = new AnimalType(id, name);
                list.add(animalType);

            }
        }
        return list;
    }

    public AnimalType get(int id) throws SQLException {

        String query = "SELECT * FROM animals_types WHERE id= ?;";

        try (Connection connection = cf.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int idAnimal = rs.getInt("id");
                String name = rs.getString("name");

                return new AnimalType(id, name);
            }
        }
        return null;
    }

    public void update(int id, String name) throws SQLException {
        String query = "UPDATE animals_types SET name= ? WHERE id= ?;";

        try (Connection connection = cf.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setInt(2, id);

            ps.executeUpdate();

        }
    }

}
