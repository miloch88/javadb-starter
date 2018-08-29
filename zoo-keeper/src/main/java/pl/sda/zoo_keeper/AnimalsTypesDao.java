package pl.sda.zoo_keeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalsTypesDao {
    private static Logger logger = LoggerFactory.getLogger(AnimalsTypesDao.class);

    private ConnectionFactory connectionFactory;

    public AnimalsTypesDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void add(AnimalType animalType) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO animals_types(name) VALUES(?)")) {

            statement.setString(1, animalType.getName());

            statement.executeUpdate();
        }
    }

    public List<AnimalTypeStatistics> list() throws SQLException {
        List<AnimalTypeStatistics> animalTypes = new ArrayList<>();
        String query = "SELECT t.id, t.name, COUNT(a.type_id)    AS COUNT" +
                " FROM animals_types t" +
                " LEFT JOIN animals a ON t.id=a.type_id " +
                "GROUP BY t.id, t.name";
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int count = resultSet.getInt("count");

                AnimalType animalType = new AnimalType(id, name);
                animalTypes.add(new AnimalTypeStatistics(animalType, count));
            }
        }

        return animalTypes;
    }

    public AnimalType get(int typeId) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, name FROM animals_types WHERE id=?")) {

            statement.setInt(1, typeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                return new AnimalType(id, name);
            }
        }
        return null;
    }

    public void delete(AnimalType animalType) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM animals_types WHERE id=?")) {
            statement.setInt(1, animalType.getId());
            statement.executeUpdate();
        }
    }

    public void update(AnimalType animalType) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE animals_types SET name=? WHERE id=?")) {

            statement.setString(1, animalType.getName());
            statement.setInt(2, animalType.getId());

            statement.executeUpdate();
        }
    }
}