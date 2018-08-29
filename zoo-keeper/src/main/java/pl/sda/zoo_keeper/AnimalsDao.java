package pl.sda.zoo_keeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalsDao {
    private static Logger logger = LoggerFactory.getLogger(AnimalsDao.class);

    private ConnectionFactory connectionFactory;
    private AnimalsTypesDao animalsTypesDao;

    public AnimalsDao(ConnectionFactory connectionFactory, AnimalsTypesDao animalsTypesDao) {
        this.connectionFactory = connectionFactory;
        this.animalsTypesDao = animalsTypesDao;
    }

    public void add(Animal animal) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO animals(name, age, type_id) VALUES(?, ?, ?)")) {

            statement.setString(1, animal.getName());
            statement.setInt(2, animal.getAge());
            statement.setObject(3, getTypeId(animal));

            statement.executeUpdate();
        }
    }

    public List<Animal> list() throws SQLException {
        return list(0);
    }

    public List<Animal> list(int typeId) throws SQLException {
        List<Animal> animals = new ArrayList<>();
        String query = "SELECT id, name, age, type_id FROM animals";
        if (typeId > 0) {
            query += " WHERE type_id = ?";
        }

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            if (typeId > 0) {
                statement.setInt(1, typeId);
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int animalTypeId = resultSet.getInt("type_id");

                AnimalType animalType = null;
                if(animalTypeId > 0) {
                    animalType = animalsTypesDao.get(animalTypeId);
                }

                animals.add(new Animal(id, name, age, animalType));
            }
        }

        return animals;
    }

    public void delete(Animal animal) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM animals WHERE id=?")) {
            statement.setInt(1, animal.getId());
            statement.executeUpdate();
        }
    }

    public void update(Animal animal) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE animals SET name=?, age=?, type_id=? WHERE id=?")) {

            statement.setString(1, animal.getName());
            statement.setInt(2, animal.getAge());
            statement.setInt(3, getTypeId(animal));
            statement.setInt(4, animal.getId());

            statement.executeUpdate();
        }
    }

    private Integer getTypeId(Animal animal) {
        return (animal.getAnimalType() == null) ? null : animal.getAnimalType().getId();
    }
}