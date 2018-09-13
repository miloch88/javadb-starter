package pl.sda.jpa.zoo_keeper_jpa;

import javax.persistence.*;

@Entity
@Table(name = "animal_type")
public class AnimalTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    public AnimalTypeEntity() {
    }

    public AnimalTypeEntity(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AnimalTypeEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
