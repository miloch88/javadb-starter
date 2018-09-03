package pl.sda.jpa.starter.relations;

import javax.persistence.*;

@Entity
@Table(name = "seats")
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String columnNumber;
    private int rowNumber;

    protected SeatEntity() {}

    private int seatNumber;

    public SeatEntity(String columnNumber, int rowNumber, int seatNumber) {
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
    }

    public String getColumnNumber() {
        return columnNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    @Override
    public String toString() {
        return "SeatEntity{" +
                "id=" + id +
                ", columnNumber='" + columnNumber + '\'' +
                ", rowNumber=" + rowNumber +
                ", seatNumber=" + seatNumber +
                '}';
    }
}
