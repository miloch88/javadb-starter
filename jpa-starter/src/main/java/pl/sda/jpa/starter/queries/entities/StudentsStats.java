package pl.sda.jpa.starter.queries.entities;

public class StudentsStats {

    private int age;
    private long countAge;

    public StudentsStats(int age, long countAge) {
        this.age = age;
        this.countAge = countAge;
    }

    public int getAge() {
        return age;
    }

    public long getCountAge() {
        return countAge;
    }

    @Override
    public String toString() {
        return "StudentsStats{" +
                "age=" + age +
                ", countAge=" + countAge +
                '}';
    }
}
