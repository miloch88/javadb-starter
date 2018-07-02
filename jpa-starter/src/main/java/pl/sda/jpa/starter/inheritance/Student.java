package pl.sda.jpa.starter.inheritance;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "students")
//@DiscriminatorValue("student")
public class Student extends Member {
    private int skillLevel;
    private int hoursOfCourse;

    protected Student() {}

    public Student(String name, int skillLevel, int hoursOfCourse) {
        super(name);
        this.skillLevel = skillLevel;
        this.hoursOfCourse = hoursOfCourse;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public int getHoursOfCourse() {
        return hoursOfCourse;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", skillLevel=" + skillLevel +
                ", hoursOfCourse=" + hoursOfCourse +
                '}';
    }
}