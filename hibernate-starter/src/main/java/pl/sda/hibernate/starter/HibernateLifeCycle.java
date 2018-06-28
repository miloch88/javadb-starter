package pl.sda.hibernate.starter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.commons.Utils;
import pl.sda.hibernate.starter.entities.CourseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class HibernateLifeCycle {
    private static Logger logger = LoggerFactory.getLogger(HibernateLifeCycle.class);
    private final SessionFactory sessionFactory;

    public HibernateLifeCycle() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate-2.cfg.xml")
                .build();

        Metadata metadata = new MetadataSources(registry)
                .addAnnotatedClass(CourseEntity.class)
                .buildMetadata();

        sessionFactory = metadata.buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private void sessionLifeCycleTest() {
        CourseEntity javaGda11 = new CourseEntity("JavaGda11", "Sopot", Utils.parse("2018-01-01"), Utils.parse("2018-09-01"));
        CourseEntity javaGda15 = new CourseEntity("JavaGda15", "Gdansk", Utils.parse("2018-05-05"), Utils.parse("2018-12-10"));
        CourseEntity javaGda22 = new CourseEntity("JavaGda22", "Sopot", Utils.parse("2019-01-01"), Utils.parse("2019-10-05"));

        /**
         * Początek sesji logicznej
         */
        try(SessionFactory sessionFactory = getSessionFactory();
            Session session = sessionFactory.openSession()) {
            /**
             * Początek 1 transakcji bazodanowej
             */
            Transaction transaction = session.beginTransaction();
            logger.info("Before: {}", javaGda11);
            session.save(javaGda11);
            /**
             * Czy teraz obiekt session zarządza encją javaGda11?
             */
            logger.info("Contains: {}", session.contains(javaGda11));
            logger.info("After: {}", javaGda11);

            session.save(javaGda15);
            session.save(javaGda22);
            /**
             * Koniec transakcji 1 poprzez commit
             */
            transaction.commit();

            /**
             * Początek 2 transakcji bazodanowej
             */
            transaction = session.beginTransaction();

            /**
             * Pobieramy jedną encję po id z bazy danych
             */
            CourseEntity javaGda11FromDb = session.find(CourseEntity.class, javaGda11.getId());
            /**
             * Czy encja pobrana z bazy to ta sama encja którą dodaliśmy wcześniej ?
             */
            logger.info("javaGda11 == javaGda11FromDb: {}", javaGda11 == javaGda11FromDb);

            javaGda11.setName("XYZ");

            /**
             * Koniec transakcji 2 poprzez commit
             */
            transaction.commit();

            /**
             * Początek 3 transakcji bazodanowej
             */
            transaction = session.beginTransaction();
            /**
             * Pobieramy wszystkie encje z bazy
             */
            List<CourseEntity> entityList = session.createQuery("from CourseEntity").list();
            logger.info("entityList: \n{}", entityList.stream()
                    .map(CourseEntity::toString)
                    .collect(Collectors.joining("\n")));

            /**
             * Czy 1 encja pobrana z bazy to ta sama encja którą dodaliśmy wcześniej ?
             */
            CourseEntity javaGda15FromDb = entityList.get(1);
            logger.info("javaGda15 == javaGda15FromDb: {}", javaGda15 == javaGda15FromDb);

            /**
             * Czy session nadal zarządza encją javaGda11?
             */
            logger.info("Contains: {}", session.contains(javaGda11));

            /**
             * Koniec transakcji 3 poprzez commit
             */
            transaction.commit();

            /**
             * Niejawnie zamykamy sesję i kończymy tym sammy naszą sejse/transakcję logiczną
             */
        }
    }

    public static void main(String[] args) {
        new HibernateLifeCycle().sessionLifeCycleTest();
    }
}