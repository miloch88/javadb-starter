package pl.sda.jpa.starter.queries.entities;

import javax.persistence.*;

@Entity
@Table(name = "skills")
public class SkillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    protected SkillEntity() {}

    public SkillEntity(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SkillEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
