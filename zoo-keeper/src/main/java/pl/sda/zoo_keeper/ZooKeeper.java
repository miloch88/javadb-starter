package pl.sda.zoo_keeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class ZooKeeper {
    private static Logger logger = LoggerFactory.getLogger(ZooKeeper.class);

    public static void main(String[] args) throws SQLException {
        ConnectionFactory connectionFactory = new ConnectionFactory("/zoo-keeper-database.properties");

        AnimalsTypesDao animalsTypesDao = new AnimalsTypesDao(connectionFactory);
        AnimalsDao animalsDao = new AnimalsDao(connectionFactory, animalsTypesDao);

        /*animalsTypesDao.add(new AnimalType("Ptaki"));
        animalsTypesDao.add(new AnimalType("Ssaki"));
        animalsTypesDao.add(new AnimalType("Gady"));*/

        List<AnimalTypeStatistics> types = animalsTypesDao.list();
        logger.info("List of types:");
        types.forEach(type -> logger.info(type.toString()));

        AnimalType birdType = animalsTypesDao.get(1);
        AnimalType mammalType = animalsTypesDao.get(2);
        /*animalsDao.add(new Animal("Pelikan", 4, birdType));
        animalsDao.add(new Animal("Orzeł", 1, birdType));
        animalsDao.add(new Animal("Jaskółka", 1, null));
        animalsDao.add(new Animal("Słoń", 1, mammalType));*/

        List<Animal> animals = animalsDao.list();
        logger.info("List of all animals:");
        animals.forEach(animal -> logger.info(animal.toString()));

        animals = animalsDao.list(1);
        logger.info("List of animals of type 1:");
        animals.forEach(animal -> logger.info(animal.toString()));

        /*animalsDao.delete(animals.get(0));*/

        /*Animal animalToUpdate = animals.get(animals.size()-1);
        animalsDao.update(new Animal(animalToUpdate.getId(), animalToUpdate.getName(), 10, mammalType));*/
    }
}
