package pl.sda.jpa.starter.related_entities;


import javax.persistence.*;

@Entity
@Table(name = "courses")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String place;

    /*@OneToMany(mappedBy = "course")
    private Set<StudentEntity> students = new HashSet<>();*/

    CourseEntity() {}

    public CourseEntity(String name, String place) {
        this.name = name;
        this.place = place;
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

    /*public Set<StudentEntity> getStudents() {
        return students;
    }

    public void addStudent(StudentEntity student) {
        students.add(student);
    }*/

    @Override
    public String toString() {
        return "CourseEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
               // ", students=" + students +
                '}';
    }
}