package pl.sda.hibernate.starter.entities;

import pl.sda.commons.Utils;

import javax.persistence.*;
import java.util.Date;

@Entity //wskazuje, że klasa Course to encja, która ma być mapowana do bazy danych
@Table(name = "courses") //adnotacja zawiera informacje na temat tabeli w której mają być zapisane dane z tej klasy
public class CourseEntity {
    @Id // wskazuje pole które ma być identyfikatorem (PK w bazie), odpowiednik elementu  <id> z pliku: Course.hbm.xml
    @GeneratedValue(strategy = GenerationType.IDENTITY) // określa strategię generowania id, patrz opis w: Course.hbm.xml
    private int id;
    private String name;
    private String place;
    @Column(name = "start_date", nullable = false) //konfiguracja kolumny w tabeli gdzie ma być zapisane to pole
    private Date startDate;
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