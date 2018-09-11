package pl.sda.jpa.starter.relations;

import javax.persistence.*;

@Entity
@Table(name = "seat")
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String columnNumber;
    private int rowNumber;
    private int seatNumber;

    @OneToOne(mappedBy = "seat")
    private StudentEntity student;

    protected SeatEntity() {}

    public SeatEntity(String columnNumber, int rowNumber, int seatNumber) {
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
    }

    public String getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(String columnNumber) {
        this.columnNumber = columnNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "SeatEntity{" +
                "columnNumber='" + columnNumber + '\'' +
                ", rowNumber=" + rowNumber +
                ", seatNumber=" + seatNumber +
                ", student=" + student +
                '}';
    }
}
