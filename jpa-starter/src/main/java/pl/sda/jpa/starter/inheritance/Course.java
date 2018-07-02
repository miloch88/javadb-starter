package pl.sda.jpa.starter.inheritance;

import javax.persistence.*;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

   /* @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private Set<Member> members = new HashSet<>();*/

    Course() {}

    public Course(String name) {
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

    /*public void addMember(Member member) {
        members.add(member);
        member.setCourse(this);

    }

    public void removeStudent(Member member) {
        members.remove(member);
        member.setCourse(null);
    }*/

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
               // ", members=" + members +
                '}';
    }
}