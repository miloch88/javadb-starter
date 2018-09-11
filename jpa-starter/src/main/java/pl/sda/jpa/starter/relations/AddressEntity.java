package pl.sda.jpa.starter.relations;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String city;
    private String street;

    @OneToOne(mappedBy = "address") //wskazanie na właściciela w 28 StudentEntetity
    private StudentEntity student;

    /*
    Zadanie 8.2. d) zamieniamy właściciela, teraz jest nim students a zamieniamy na addresses
    @OneToOne(cascade = {CascadeType.ALL})
    private StudentEntity student;
    */

    protected AddressEntity() {} //To jest niezbędne do działanai Hibernatie

    public AddressEntity(String city, String street) {
        this.city = city;
        this.street = street;
    }

    public Integer getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
