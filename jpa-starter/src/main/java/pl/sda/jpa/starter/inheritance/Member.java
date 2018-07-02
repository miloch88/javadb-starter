package pl.sda.jpa.starter.inheritance;

import javax.persistence.*;

@MappedSuperclass

//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@Table(name = "members")
//@DiscriminatorColumn(name = "member_type")

//@Entity
//@Table(name = "members")
//@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO
    protected int id;
    protected String name;

   /* @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    protected Course course;*/

    protected Member() {}

    public Member(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /*public void setCourse(Course course) {
        this.course = course;
    }*/

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
