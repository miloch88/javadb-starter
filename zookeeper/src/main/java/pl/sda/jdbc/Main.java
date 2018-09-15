package pl.sda.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

        ConnectionFactory cf = new ConnectionFactory();
        AnimalsTypesDao typeDAO = new AnimalsTypesDao(cf);
        AnimalDAO animalDAO = new AnimalDAO(cf);

//        Połączenie log
//        logger.info(connection.getCatalog());

//        Usuwamy tablice
//        animalDAO.deleteAnimalTable();
//        typeDAO.deleteTable();

//        Tworzymy tablice
//        typeDAO.createTable();
//        animalDAO.createAnimalTable();

//        Dodajemy typy zqwietzątek
//        AnimalType rhino = new AnimalType("Nosorożec");
//        AnimalType zebra = new AnimalType("Zebra");
//        AnimalType lion = new AnimalType("Lew");
//        AnimalType monkey = new AnimalType("Małpka");

//        typeDAO.add(rhino);
//        typeDAO.add(zebra);
//        typeDAO.add(lion);
//        typeDAO.add(monkey);
//        typeDAO.add(new AnimalType("Żyrafa"));
//        typeDAO.add(new AnimalType("Żyrafa"));

//        Updateujemy zwierzątka
//        typeDAO.update(1, "Sowa");
//        typeDAO.update(2, "Bóbr");
//        typeDAO.update(3, "Lis");
//        typeDAO.update(4, "Ryś");
//        typeDAO.update(5, "Jeż");
//        typeDAO.update(6, "Zając");

//        Wyświetlamy tablicę zwierzątek
//        System.out.println(typeDAO.list());

//        Zwraca obiekt o pdanym id
//        System.out.println(typeDAO.get(2));

//        if AnimalType is not exist -> create

//        animalDAO.addAnimal(new Animal("Lucek", 5, new AnimalType(3,"Lis") ));
//        animalDAO.addAnimal(new Animal("Rysio", 15, typeDAO.get(4) ));
//        animalDAO.addAnimal(new Animal("Jerzyk", 20, typeDAO.get(5) ));
//        animalDAO.addAnimal(new Animal("Zuzia", 10, new AnimalType(6,"Zając") ));
//        animalDAO.addAnimal(new Animal("Zosia", 25, typeDAO.get(1) ));
//        animalDAO.addAnimal(new Animal("Borys", 30, typeDAO.get(2) ));

        System.out.println(animalDAO.list());
        animalDAO.delete(6);
        System.out.println(animalDAO.list());

    }
}
