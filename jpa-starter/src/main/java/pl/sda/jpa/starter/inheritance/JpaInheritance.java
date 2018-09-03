package pl.sda.jpa.starter.inheritance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.jpa.starter.queries.JpaQueries;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.stream.Collectors;

public class JpaInheritance {
    private static Logger logger = LoggerFactory.getLogger(JpaQueries.class);
    private EntityManagerFactory entityManagerFactory;

    public JpaInheritance() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter.inheritance");
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void close() {
        entityManagerFactory.close();
    }

    public static void main(String[] args) {
        JpaInheritance jpaInheritance = new JpaInheritance();
        try {
            jpaInheritance.fillDataBase(jpaInheritance.getEntityManagerFactory());
            jpaInheritance.findAllMembers();
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            jpaInheritance.close();
        }
    }

    private void findAllMembers() {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            /*List<Member> members = entityManager.createQuery("from Member", Member.class).getResultList();
            printList(members);*/

            List<Student> students = entityManager.createQuery("from Student", Student.class).getResultList();
            printList(students);

            List<Coach> coaches = entityManager.createQuery("from Coach", Coach.class).getResultList();
            printList(coaches);

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private void fillDataBase(EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Coach lech = new Coach("Lech", 15, 4);
            entityManager.persist(lech);
            Coach czech = new Coach("Czech", 35, 7);
            entityManager.persist(czech);
            Coach miech = new Coach("Miech", 75, 14);
            entityManager.persist(miech);

            Student monika = new Student("Monika", 4, 200);
            entityManager.persist(monika);
            Student lukasz = new Student("Lukasz", 5, 230);
            entityManager.persist(lukasz);
            Student marek = new Student("Marek", 10, 300);
            entityManager.persist(marek);

           /* Course course = new Course("JavaGda11");
            course.addMember(lech);
            course.addMember(czech);
            course.addMember(miech);
            course.addMember(monika);
            course.addMember(lukasz);
            course.addMember(marek);*/

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private void printList(List<?> list) {
        logger.info("\nList: \n{}", list.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")));
    }
}
