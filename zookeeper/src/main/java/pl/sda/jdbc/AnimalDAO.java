package pl.sda.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {

    private AnimalsTypesDao animalsTypesDao;
    private ConnectionFactory cf;


    public AnimalDAO(AnimalsTypesDao animalsTypesDao, ConnectionFactory cf) {
        this.animalsTypesDao = animalsTypesDao;
        this.cf = cf;
    }

    public AnimalDAO(ConnectionFactory cf) {
        this.cf = cf;
    }

    public void createAnimalTable() throws SQLException {

        try (Connection connection = cf.getConnection();
             Statement statement = connection.createStatement()) {

            String query =
                    "CREATE TABLE IF NOT EXISTS animals(" +
                            "id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                            "name VARCHAR(50)," +
                            "age INTEGER," +
                            "type_id INTEGER," +
                            "FOREIGN KEY (type_id) REFERENCES animals_types(id)" +
                            ");";

            statement.executeUpdate(query);
        }
    }

    public void deleteAnimalTable() throws SQLException {

        try(Connection connection = cf.getConnection();
            Statement statement = connection.createStatement()){

            String query =
                    "DROP TABLE IF EXISTS animals CASCADE;";

            statement.executeUpdate(query);
        }
    }

    public void addAnimal(Animal animal) throws SQLException {

        String query =
                "INSERT INTO animals (name, age, type_id) VALUES (?,?,?);";
        try(Connection connection = cf.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)){

            ps.setString(1,animal.getName());
            ps.setInt(2,animal.getAge());
            ps.setInt(3,animal.getAnimalType().getId());

            ps.executeUpdate();
        }
    }

    public List<Animal> list() throws SQLException {

        AnimalsTypesDao dao = new AnimalsTypesDao(cf);
        List<Animal> list = new ArrayList<>();
        String query = "SELECT * FROM animals;";

        try(Connection connection = cf.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)){

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String name  = rs.getString("name");
                int age  = rs.getInt("age");
                int animalID  = rs.getInt("type_id");

                AnimalType type = dao.get(animalID);

                list.add(new Animal(id, name, age, type));
            }
        }
        return list;
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM animals WHERE id= ?";

        try(Connection connection = cf.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1,id);

            ps.executeUpdate();
        }
    }

}
