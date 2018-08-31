package pl.sda.hibernate.starter;

import pl.sda.commons.Utils;

import java.util.Date;

/**
 * Standardowa klasa typu JavaBean - tego nie narzuca Hibernate, ale jest dobrą praktyką programowania obiektowego.
 */
public class Course {
    private int id;
    private String name;
    private String place;
    private Date startDate;
    private Date endDate;

    /**
     * Jedyny wymóg Hibernate to istnienie bezargumentowego konstruktora (najlepiej z widocznością pakietową albo public!)
     */
    protected Course() {}

    public Course(String name, String place, Date startDate, Date endDate) {
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

    public String getPlace() {
        return place;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", startDate=" + Utils.dateFormat(startDate)+
                ", endDate=" + Utils.dateFormat(endDate) +
                '}';
    }
}