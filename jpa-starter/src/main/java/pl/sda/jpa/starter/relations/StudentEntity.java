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

    @OneToOne(cascade = {CascadeType.ALL})
    private SeatEntity seat;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "add_id")
    private AddressEntity address;

    /**
     * Uwaga w adnotacji @ManyToOne brak atrybutu: mappedBy ! - w tej relacje zawsze strona z @OneToMany jest właścicielem!
     */
    @ManyToOne(cascade = {CascadeType.ALL})
    private CourseEntity course;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "students_skills",
            joinColumns = {@JoinColumn(name ="student_id")},
            inverseJoinColumns = {@JoinColumn(name ="skill_id")})
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

    public SeatEntity getSeat() {
        return seat;
    }

    public void setSeat(SeatEntity seat) {
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
                ", seat='" + seat + '\'' +
                ", skills='" + skills + '\'' +
                ", address=" + address +
                ", course=" + ((course == null) ? "brak" : course.getName()) +
                '}';
    }
}