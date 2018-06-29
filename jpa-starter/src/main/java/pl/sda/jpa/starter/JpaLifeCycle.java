package pl.sda.jpa.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.commons.Utils;
import pl.sda.jpa.starter.basic_entities.CourseEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaLifeCycle {
    private static Logger logger = LoggerFactory.getLogger(JpaLifeCycle.class);
    private EntityManagerFactory entityManagerFactory;

    public JpaLifeCycle() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter");
    }

    public void close() {
        entityManagerFactory.close();
    }

    public static void main(String[] args) {
        /**
         * inicjalizujemy EntityManagerFactory (w konstruktorze), a tym samym inicjalizujemy Persistence Unit o nazwie: "pl.sda.hibernate.starter"
         */
        JpaLifeCycle jpaLifeCycle = new JpaLifeCycle();
        try {
            /**
             * wykonujemy logikę biznesową naszej aplikacji
             */
            jpaLifeCycle.managedToDetachedToManagedTransition();

            /**
             * zamykamy obiekt EntityManagerFactory, kończąc pracę z powiązanym Persistence Unit (o nazwie: "pl.sda.hibernate.starter")
             */
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            jpaLifeCycle.close();
        }
    }

    private CourseEntity transientToManagedTransition() {
        /**
         * tworzymy nowy obiekt courseEntity, stan encji: Transient
         */
        CourseEntity courseEntity = new CourseEntity("JavaGda11", "Sopot", Utils.parse("2018-01-01"), Utils.parse("2018-09-01"));

        EntityManager entityManager = null;
        try {
            /**
             * tworzymy nową instancję EntityManager, tym samym rozpoczynamy działanie Persistence Context (w języku Hibernate - sesji)
             */
            entityManager = entityManagerFactory.createEntityManager();

            /**
             * pobieramy i rozpoczynamy transakcję bazodanową
             */
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            /**
             * zapisujemy nowy obiekt w Persistence Context, stan encji: Managed
             * nie zawsze oznacza to natychmiastowy zapis w bazie danych!
             */
            entityManager.persist(courseEntity);

            /**
             * commitujemy transakcję, wszystkie zmiany dotąd niezapisane w bazie muszą być zapisane
             */
            transaction.commit();
        } finally {
            //kończymy pracę z entityManager, zamykamy go i tym samym zamykamy Persistence Context z nim związany
            if (entityManager != null) {
                entityManager.close();
            }
        }

        /**
         * stan encji: Detached
         */
        return courseEntity;
    }

    private void managedToDetachedToManagedTransition() {
        /**
         * dodajemy nową encję do bazy danych, stan encji: Detached
         */
        CourseEntity oldCourseEntity = transientToManagedTransition();

        EntityManager entityManager = null;
        try {
            /**
             * tworzy zupełnie nowy Persistence Context - w porównaniu do tego z metody transientToManagedTransition()
             */
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            /**
             * pobieramy wcześniej zapisaną encję z bazy danych
             */
            CourseEntity courseEntity = entityManager.find(CourseEntity.class, oldCourseEntity.getId());
            logger.info("oldCourseEntity == courseEntity: {}", oldCourseEntity == courseEntity);

            /**
             * zmieniamy encję, zmiany są zapisane na razie tylko w Persistence Context
             */
            courseEntity.setName("ZZZ");

            /**
             * synchronizujemy zmiany z bazą danych - wszystkie zmiany z Persistence Context (które do tej pory nie były zapisane) są zapisane w bazie
             */
            entityManager.flush();

            /**
             * ponownie zmieniamy encję
             */
            courseEntity.setName("YYY");

            /**
             * usuwamy encję z Persistence Context, zmiany które zostały zrobiona nie zostają zapisane w bazie, stan encji: Detached
             */
            entityManager.detach(courseEntity);
            entityManager.flush();

            /**
             * ponownie dodajemy encję do Persistence Context, jej zmiany są znowu zapisane w Persistence Context i przy najbliższej okazji zostaną zapisane w bazie
             */
            CourseEntity mergedCourseEntity = entityManager.merge(courseEntity);
            logger.info("mergedCourseEntity == courseEntity: {}", mergedCourseEntity == courseEntity);

            /**
             * dostęp do transakcji mamy zawsze przez entityManager.getTransaction()
             */
            entityManager.getTransaction().commit();
        } finally {
            /**
             * zamykamy Persistence Context
             */
            entityManager.close();
        }
    }
}