package pl.sda.jpa.starter.queries.entities;

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
    private int age;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private SeatEntity seat;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private AddressEntity address;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private CourseEntity course;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
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

    public int getAge() {
        return age;
    }

    public StudentEntity setAge(int age) {
        this.age = age;
        return this;
    }

    public SeatEntity getSeat() {
        return seat;
    }

    public StudentEntity setSeat(SeatEntity seat) {
        this.seat = seat;
        return this;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public StudentEntity setAddress(AddressEntity address) {
        this.address = address;
        return this;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public StudentEntity setCourse(CourseEntity course) {
        this.course = course;
        return this;
    }

    public Set<SkillEntity> getSkills() {
        return skills;
    }

    public StudentEntity addSkill(SkillEntity skill) {
        skills.add(skill);
        return this;
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