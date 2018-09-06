package pl.sda.jdbc;

public class AnimalsTypesDao {

    AnimalType animalType;

    public AnimalType addAnimalType(int id, String name){
        animalType = new AnimalType(id, name);
        return animalType;
    }
}
