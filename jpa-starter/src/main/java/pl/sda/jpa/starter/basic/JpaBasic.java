package pl.sda.jpa.starter.basic;

import javax.persistence.*;
import java.util.List;

public class JpaBasic {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            /**
             * Tworzymy nowy obiekt EntityManagerFactory z konfiguracją Persistence Unit o nazwie: "pl.sda.jpa.starter"
             */
            entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter");
            /**
              * tworzymy nową instancję EntityManager
              */
            entityManager = entityManagerFactory.createEntityManager();

            /**
             * Do pracy z bazą danych potrzebujemy transakcji
             */
            EntityTransaction transaction = entityManager.getTransaction();
            /**
             * Zaczynamy nową transakcję, każda operacja na bazie danych musi być "otoczona" transakcją
             */
            transaction.begin();

            /**
             * Zapisujemy encję w bazie danych
             */
            CoachEntity coachEntity = new CoachEntity("Vlad Mihalcea");
            entityManager.persist(coachEntity);
            entityManager.persist(new CoachEntity("Jan Borówka"));
            entityManager.persist(new CoachEntity("Mietek Kmita"));
            entityManager.persist(new CoachEntity("Ewa Wach"));

            /**
             * Wyciągamy wszystkie encje zapisane w bazie danych
             */
            TypedQuery<CoachEntity> query = entityManager.createQuery("from CoachEntity", CoachEntity.class);
            List<CoachEntity> coaches = query.getResultList();
            System.out.println("coaches = " + coaches);

            /*entityManager.remove(coaches.get(coaches.size() - 1));

            coaches = query.getResultList();
            System.out.println("coaches = " + coaches);*/

            entityManager.persist(new StudentEntity("Marek", 3));
            entityManager.persist(new StudentEntity("Natalia", 1));
            entityManager.persist(new StudentEntity("Józef", 3));

            List<StudentEntity> students = entityManager.createQuery("from StudentEntity", StudentEntity.class).getResultList();
            System.out.println("students = " + students);

            /**
             * Kończymy (commitujemy) transakcję - wszystkie dane powinny być zapisane w bazie
             */
            transaction.commit();
        } finally {
            /**
             * Kończymy pracę z entityManager, zamykamy go i tym samym zamykamy Persistence Context z nim związany
             */
            if (entityManager != null) {
                entityManager.close();
                entityManagerFactory.close();
            }
        }
    }
}
