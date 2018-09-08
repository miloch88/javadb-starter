package pl.sda.jpa.starter.basic;

import javax.persistence.*;

@Entity

@Table(name = "student")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "yearOfStudy")
    private int year;

    public StudentEntity(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public StudentEntity() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
    }
}
