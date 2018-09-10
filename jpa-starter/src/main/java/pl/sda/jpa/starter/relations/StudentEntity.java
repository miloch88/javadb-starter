package pl.sda.jpa.starter.relations;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String seat;

    //Tak to by wyglądało w SQL : "FOREIGN KEY (add_id) REFERENCE adresses(id)"

    //Zadanie 8. b) nie będzie działało usunięcie atrybutu CASCADE
    @OneToOne(cascade = {CascadeType.ALL})
    //Zadanie 8. c) powinniśmy dwa razy uruchomić program (za pierwszym razem będzie błąd FK),
    // i utworzy nową nazwę tej kolumny
    @JoinColumn(name = "add_id")
    private AddressEntity address;

    //Zadanie 8. d) zamieniamy właściciela, teraz jest nim students a zamieniamy na addresses
//    @OneToOne(mappedBy = "student") // @JoinColumn nada nazwę domyślną


    /**
     * Uwaga w adnotacji @ManyToOne brak atrybutu: mappedBy ! - w tej relacje zawsze strona z @ManyToOne jest właścicielem!
     */
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "Yaba_Daba_Doo")
    private CourseEntity course;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "students_skills",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")})
    private Set<SkillEntity> skills = new HashSet<>();

    protected StudentEntity() {
    }

    public StudentEntity(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
        /**
         * Jeżeli mamy relację dwukierunkową - sami musimy zadbać żeby obie strony miały ustawione dane
         */
        address.setStudent(this);
    }

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public Set<SkillEntity> getSkills() {
        return skills;
    }

    public void addSkill(SkillEntity skill) {
        skills.add(skill);
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", skills='" + skills + '\'' +
                ", address=" + address +
                ", course=" + ((course == null) ? "brak" : course.getName()) +
                '}';
    }
}