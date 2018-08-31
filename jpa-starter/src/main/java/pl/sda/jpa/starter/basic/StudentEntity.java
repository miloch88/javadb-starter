package pl.sda.jpa.starter.basic;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(name = "year_of_study")
    private int yearOfStudy;

    StudentEntity() {}

    public StudentEntity(String name, int yearOfStudy) {
        this.name = name;
        this.yearOfStudy = yearOfStudy;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", yearOfStudy=" + yearOfStudy +
                '}';
    }
}
