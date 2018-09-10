package pl.sda.jpa.starter.relations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class JpaRelations {
    private static Logger logger = LoggerFactory.getLogger(JpaRelations.class);
    private EntityManagerFactory entityManagerFactory;

    public JpaRelations() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter.relations");
    }

    public void close() {
        entityManagerFactory.close();
    }

    private void oneToOne() {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            AddressEntity address = new AddressEntity("Gdańsk", "Malwinowa 1/3");
            StudentEntity student = new StudentEntity("Jan Kowalski");
            student.setAddress(address);

//            Zadanie 8. a)nie powinno działać, doda studenta ale nie adres
//            address.setStudent(student);

            entityManager.persist(student);
//            entityManager.persist(address);

            StudentEntity studentEntity = entityManager.find(StudentEntity.class, 1);
            logger.info("Student: {}", studentEntity);

            AddressEntity addressEntity = entityManager.find(AddressEntity.class, 1);
//            logger.info("AddressEntity Student: {}", addressEntity.getStudent());

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private void oneToMany() {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            List<StudentEntity> list;

            CourseEntity course = new CourseEntity("JavaGda11", "Sopot");
            CourseEntity course1 = new CourseEntity("Kurs Szydełkowania", "Ravnica");
            StudentEntity student = new StudentEntity("Jan Kowalski");
            StudentEntity student1 = new StudentEntity("Jace Beleren");
            StudentEntity student2 = new StudentEntity("Chandra Naalar");
            course.addStudent(student);
            course1.addStudent(student1);
            course1.addStudent(student2);

            list = entityManager.createQuery("FROM StudentEntity WHERE name = 'Jan Kowalski'", StudentEntity.class).getResultList();
            StudentEntity studentEntity1 = list.get(0);
//            entityManager.persist(student);
//            entityManager.persist(student1);
//            entityManager.persist(student2);


            entityManager.remove(studentEntity1);


            StudentEntity studentEntity = entityManager.find(StudentEntity.class, 1);
            logger.info("Student: {}", studentEntity);

            CourseEntity courseEntity = entityManager.find(CourseEntity.class, 1);
            logger.info("Course: {}", courseEntity);

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private void manyToMany() {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            SkillEntity skill1 = new SkillEntity("JVM Master");
            SkillEntity skill2 = new SkillEntity("JDBC Master");
            SkillEntity skill3 = new SkillEntity("Hibernate Master");
            StudentEntity student = new StudentEntity("Jan Kowalski");
            student.addSkill(skill1);
            student.addSkill(skill2);
            student.addSkill(skill3);

            entityManager.persist(student);

            StudentEntity studentEntity = entityManager.find(StudentEntity.class, 1);
            logger.info("Student: {}", studentEntity);

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public static void main(String[] args) {
        JpaRelations jpaQueries = new JpaRelations();
        try {
//            jpaQueries.oneToOne();
            jpaQueries.oneToMany();
            //jpaQueries.manyToMany();
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            jpaQueries.close();
        }
    }
}