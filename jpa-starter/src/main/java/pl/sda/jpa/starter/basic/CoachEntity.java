package pl.sda.jpa.starter.basic;

import javax.persistence.*;

/**
 * Adnotacja @Entity wskazuje, że klasa CoachEntity to encja, która ma być mapowana do tabeli w bazie danych
 */
@Entity
/**
 * Adnotacja @Table zawiera informacje na temat tabeli w której mają być zapisane dane z tej klasy
 */
@Table(name = "coaches")
public class CoachEntity {
    /**
     * Adnotacja @Id wskazuje pole które ma być identyfikatorem (PK w bazie)
     */
    @Id
    /**
     * Adnotacja @GeneratedValue określa strategię generowania id czyli klucza głównego (PK)
     * W naszym przypadku korzystamy z funkcjonalności AUTO-INCREMENT bazy MySQL.
     * Więcej: http://www.onlinetutorialspoint.com/hibernate/generator-classes-in-hibernate.html
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public CoachEntity(String name) {
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
        return "CoachEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
