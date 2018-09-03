package pl.sda.jpa.starter.queries.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@NamedQueries({
        @NamedQuery(name="CourseEntity.findByCity" ,
                query="SELECT c.name, c.place FROM CourseEntity c WHERE c.place = :place"),
        @NamedQuery(name="CourseEntity.selectNameAndPlace" ,
                query="SELECT c.name, c.place FROM CourseEntity c")
})
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String place;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private Set<StudentEntity> students = new HashSet<>();

    CourseEntity() {}

    public CourseEntity(String name, String place, Date startDate, Date endDate) {
        this.name = name;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Set<StudentEntity> getStudents() {
        return students;
    }

    public void addStudent(StudentEntity student) {
        students.add(student);
        /**
         * Jeżeli mamy relację dwukierunkową - sami musimy zadbać żeby obie strony miały ustawione dane
         */
        student.setCourse(this);

    }

    public void removeStudent(StudentEntity student) {
        students.remove(student);
        /**
         * Jeżeli mamy relację dwukierunkową - sami musimy zadbać żeby obie strony miały ustawione dane
         */
        student.setCourse(null);
    }

    @Override
    public String toString() {
        return "CourseEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", students=" + students +
                '}';
    }
}