package pl.sda.jpa.starter.basic;

import javax.persistence.*;
import java.util.List;

public class JpaStarter {
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
            transaction.begin();

            /**
             * Zapisujemy encję w bazie danych
             */
            CoachEntity coachEntity = new CoachEntity("Vlad Mihalcea");
            entityManager.remove(coachEntity);

            /**
             * Wyciągamy wszystkie encje zapisane w bazie danych
             */
            TypedQuery<CoachEntity> query = entityManager.createQuery("from CoachEntity", CoachEntity.class);
            List<CoachEntity> coaches = query.getResultList();
            System.out.println("coaches = " + coaches);

            /**
             * commitujemy transakcję, wszystkie zmiany dotąd niezapisane w bazie muszą być zapisane
             */
            transaction.commit();
        } finally {
            /**
             * kończymy pracę z entityManager, zamykamy go i tym samym zamykamy Persistence Context z nim związany
             */
            if (entityManager != null) {
                entityManager.close();
                entityManagerFactory.close();
            }
        }
    }
}
