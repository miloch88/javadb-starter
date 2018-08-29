package pl.sda.zoo_keeper;

public class AnimalTypeStatistics {
    private AnimalType animalType;
    private int count;

    public AnimalTypeStatistics(AnimalType animalType, int count) {
        this.animalType = animalType;
        this.count = count;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "AnimalTypeStatistics{" +
                "animalType=" + animalType +
                ", count=" + count +
                '}';
    }
}