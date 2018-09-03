package pl.sda.jpa.starter.queries.entities;

public class CourseInfo {
    private String name;
    private String place;

    public CourseInfo(String name, String place) {
        this.name = name;
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    @Override
    public String toString() {
        return "CourseInfo{" +
                "name='" + name + '\'' +
                ", place='" + place + '\'' +
                '}';
    }
}
