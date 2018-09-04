package pl.sda.jpa.starter.queries.entities;

public class StudentsStats {
    int age;
    long studentsNumber;

    public StudentsStats(int age, long studentsNumber) {
        this.age = age;
        this.studentsNumber = studentsNumber;
    }

    public int getAge() {
        return age;
    }

    public long getStudentsNumber() {
        return studentsNumber;
    }

    @Override
    public String toString() {
        return "StudentsStats{" +
                "age=" + age +
                ", studentsNumber=" + studentsNumber +
                '}';
    }
}
