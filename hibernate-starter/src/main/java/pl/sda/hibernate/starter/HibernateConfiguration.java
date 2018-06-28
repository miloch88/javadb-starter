package pl.sda.hibernate.starter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import pl.sda.commons.Utils;
import pl.sda.hibernate.starter.entities.CourseEntity;

public class HibernateConfiguration {
    public static void main(String[] args) {
        /**
         * Krok 1: Konfiguracja Hibernate - ustawiamy parametry Hibernate (dostęp do bazy danych, parametry, cache itp)
         */
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml") // pobiera parametry z pliku hibernate.cfg.xml
                /**
                 * Uwaga! ustawiając parametry przez applySetting dodajemy prefix hibernate.* do nazwy parametru !
                 * ustwiająć w ten sposób parametry nadpisujemy parametry z pliku
                 */
                //.applySetting("hibernate.show_sql", false)
                //.applySetting("hibernate.connection.username", "jarek")
                .build();

        /**
         * Krok 2: Konfiguracja Hibernate - ustawiamy mapowania klas-encji
         */
        Metadata metadata = new MetadataSources(registry)
                /**
                 * Można dodać pojedynczą klasę-encję
                 */
                //.addAnnotatedClass(CourseEntity.class)
                /**
                 * można dodać cały pakiet
                 *///.addPackage("pl.sda.hibernate.starter.entities")
                .buildMetadata();

        try(SessionFactory sessionFactory = metadata.buildSessionFactory();
            Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();
            session.save(new CourseEntity("JavaGda11", "Sopot", Utils.parse("2018-01-01"), Utils.parse("2018-09-01")));
            transaction.commit();

        }
    }
}
