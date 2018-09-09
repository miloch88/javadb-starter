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

        //Połączenie log
//        logger.info(connection.getCatalog());

        ato.deleteTable();

        ato.createTable();

        AnimalType zyrafa = new AnimalType("Żyrafa");

        ato.add(new AnimalType("Nosorożec"));
        ato.add(new AnimalType("Zebra"));
        ato.add(new AnimalType("Lew"));
        ato.add(new AnimalType("Szympans"));
        ato.add(new AnimalType("Żyrafa"));

        ato.update(2, "Jeż");
        ato.update(4, "Ryś");

        List<AnimalType> list = ato.list();

//        System.out.println(ato.get(5));

        ato.delete(list.get(4));

        System.out.println(ato.list());
    }
}
