package pl.sda.zoo_keeper;

public class Animal {
    private int id;
    private String name;
    private int age;
    private AnimalType animalType;

    public Animal(int id, String name, int age, AnimalType animalType) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.animalType = animalType;
    }

    public Animal(String name, int age, AnimalType animalType) {
        this.name = name;
        this.age = age;
        this.animalType = animalType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", animalType=" + animalType +
                '}';
    }
}