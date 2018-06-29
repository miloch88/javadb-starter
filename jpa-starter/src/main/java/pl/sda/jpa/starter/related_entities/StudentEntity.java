package pl.sda.jpa.starter.related_entities;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String seat;

    @OneToOne//(cascade = {CascadeType.ALL})
    //@JoinColumn(name = "add_id")
    private AddressEntity address;

    /*@ManyToOne(cascade = {CascadeType.ALL})
    private CourseEntity course;*/

    /*@ManyToMany(cascade = {CascadeType.ALL})
    private Set<SkillEntity> skills = new HashSet<>();*/

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
        //address.setStudent(this);
    }

    /*public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
        */

    /**
     * Jeżeli mamy relację dwukierunkową - sami musimy zadbać żeby obie strony miały ustawione dane
     *//*
        //course.addStudent(student);
    }*/

    /*public Set<SkillEntity> getSkills() {
        return skills;
    }

    public void addSkill(SkillEntity skill) {
        skills.add(skill);
    }*/
    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                // ", skills='" + skills + '\'' +
                ", address=" + address +
                // ", course=" + course +
                '}';
    }
}
