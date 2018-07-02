package pl.sda.jpa.starter.inheritance;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "coaches")
//@DiscriminatorValue("coach")
public class Coach extends Member {
    private int salaryPerHour;
    private int yearsOfExpierence;

    protected Coach() { }

    public Coach(String name, int salaryPerHour, int yearsOfExpierence) {
        super(name);
        this.salaryPerHour = salaryPerHour;
        this.yearsOfExpierence = yearsOfExpierence;
    }

    public int getSalaryPerHour() {
        return salaryPerHour;
    }

    public int getYearsOfExpierence() {
        return yearsOfExpierence;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", salaryPerHour=" + salaryPerHour +
                ", yearsOfExpierence=" + yearsOfExpierence +
                '}';
    }
}