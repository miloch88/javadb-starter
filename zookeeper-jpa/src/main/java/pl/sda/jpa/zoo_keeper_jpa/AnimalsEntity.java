package pl.sda.jpa.zoo_keeper_jpa;


import javax.persistence.*;

@Entity
@Table(name = "animals")
public class AnimalsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "type_id")
    private AnimalTypeEntity animalTypeEntity;

    public AnimalsEntity() {
    }

    public AnimalsEntity(String name, int age, AnimalTypeEntity animalTypeEntity) {
        this.name = name;
        this.age = age;
        this.animalTypeEntity = animalTypeEntity;
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

    public AnimalTypeEntity getAnimalTypeEntity() {
        return animalTypeEntity;
    }

    @Override
    public String toString() {
        return "AnimalsEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", animalTypeEntity=" + animalTypeEntity +
                '}';
    }
}
