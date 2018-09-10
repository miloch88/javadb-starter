package pl.sda.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

        ConnectionFactory cf = new ConnectionFactory();
        AnimalsTypesDao ato = new AnimalsTypesDao(cf);
        AnimalDAO adao = new AnimalDAO(cf);

        //Połączenie log
//        logger.info(connection.getCatalog());

//        adao.deleteAnimalTable();
//        ato.deleteTable();
//
//        ato.createTable();
//
//        ato.add(new AnimalType("Nosorożec"));
//        ato.add(new AnimalType("Zebra"));
//        ato.add(new AnimalType("Lew"));
//        ato.add(new AnimalType("Szympans"));
//        ato.add(new AnimalType("Żyrafa"));
//        ato.add(new AnimalType("Żyrafa"));
//
//        ato.update(1, "Sowa");
//        ato.update(2, "Bóbr");
//        ato.update(3, "Lis");
//        ato.update(4, "Ryś");
//        ato.update(5, "Jeż");
//        ato.update(6, "Zając");

//        List<AnimalType> list = ato.list();
//        System.out.println(ato.list());

//        System.out.println(ato.get(5));
//        ato.delete(list.get(4));


//        adao.createAnimalTable();

        //if AnimalType is not exist -> create
        adao.addAnimal(new Animal("Lucek", 6, new AnimalType(3,"Lis") ));
//        adao.addAnimal(new Animal("Rysio", 5, new AnimalType(4,"Ryś") ));
//        adao.addAnimal(new Animal("Jerzyk", 4, new AnimalType(5,"Jeż") ));
//        adao.addAnimal(new Animal("Zuzia", 3, new AnimalType(6,"Zając") ));
//        adao.addAnimal(new Animal("Zosia", 2, new AnimalType(1,"Sowa") ));
//        adao.addAnimal(new Animal("Borys", 1, new AnimalType(2,"Bóbr") ));


    }
}
