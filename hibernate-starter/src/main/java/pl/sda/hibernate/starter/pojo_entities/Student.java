package pl.sda.hibernate.starter.pojo_entities;

public class Student {
    private int id;
    private String name;
    private String description;
    private String seat;

    public Student(int id, String name, String description, String seat) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.seat = seat;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSeat() {
        return seat;
    }
}
