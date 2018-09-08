package pl.sda.jpa.starter.basic;

import pl.sda.jpa.starter.inheritance.Student;

import javax.persistence.*;
import java.util.List;

public class JpaBasic {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try {
            /**
             * Tworzymy nowy obiekt EntityManagerFactory z konfiguracją Persistence Unit
             * o nazwie: "pl.sda.jpa.starter"
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

//            StudentEntity studentEntity1 = new StudentEntity("Kamila",1);
//            StudentEntity studentEntity2 = new StudentEntity("Jacek",2);
//            StudentEntity studentEntity3 = new StudentEntity("Basia",3);
//            StudentEntity studentEntity4 = new StudentEntity("Kasia",4);
//            StudentEntity studentEntity5 = new StudentEntity("Andrzej",5);
//
//            entityManager.persist(studentEntity1);
//            entityManager.persist(studentEntity2);
//            entityManager.persist(studentEntity3);
//            entityManager.persist(studentEntity4);
//            entityManager.persist(studentEntity5);

//            CoachEntity coachEntity = new CoachEntity("Vlad Mihalcea");
//            entityManager.persist(coachEntity);
//            entityManager.remove(coachEntity);

            /**
             * Wyciągamy wszystkie encje zapisane w bazie danych
             */
//            TypedQuery<CoachEntity> query = entityManager.createQuery("from CoachEntity", CoachEntity.class);
//            List<CoachEntity> coaches = query.getResultList();
//            System.out.println("coaches = " + coaches);

            TypedQuery<StudentEntity> query = entityManager.createQuery("FROM StudentEntity", StudentEntity.class);
            List<StudentEntity> students = query.getResultList();
//            System.out.println(students);

            entityManager.remove(students.get(students.size()-1));



            /**
             * Kończymy (commitujemy) transakcję - wszystkie dane powinny być zapisane w bazie
             */
            transaction.commit();
        } finally {
            /**
             * Kończymy pracę z entityManager, zamykamy go i tym samym zamykamy Persistence Context z nim związany
             * Czemu EntityManager nie implementuje AutoClosable? https://github.com/javaee/jpa-spec/issues/77
             */

            if (entityManager != null) {
                entityManager.close();
                entityManagerFactory.close();
            }
        }
    }
}
