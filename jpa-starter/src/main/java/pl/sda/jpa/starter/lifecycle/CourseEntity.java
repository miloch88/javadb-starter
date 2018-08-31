package pl.sda.jpa.starter.lifecycle;

import pl.sda.commons.Utils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "courses")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String place;
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "end_date", nullable = false)
    private Date endDate;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", startDate=" + Utils.dateFormat(startDate) +
                ", endDate=" + Utils.dateFormat(endDate) +
                '}';
    }
}