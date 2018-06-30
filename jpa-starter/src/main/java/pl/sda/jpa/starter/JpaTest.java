package pl.sda.jpa.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.commons.Utils;
import pl.sda.jpa.starter.basic_entities.CourseEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class JpaTest {
    private static Logger logger = LoggerFactory.getLogger(JpaTest.class);
    private EntityManagerFactory entityManagerFactory;

    public JpaTest() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter");
    }

    public void close() {
        entityManagerFactory.close();
    }

    public static void main(String[] args) {
        JpaTest test = new JpaTest();
        try {
            //test.addFewEntities();
            //test.deleteEntity(3);
            //test.removeLastFromList();
            test.updateEntity(2, "JAVA_GDA_5_!", Utils.parse("2018-06-01"));

        } catch (Exception e) {
            logger.error("Error", e);
        } finally {
            test.close();
        }
    }

    private void updateEntity(int id, String newName, Date newEndDate) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            CourseEntity courseEntity = entityManager.find(CourseEntity.class, id);
            if(courseEntity != null) {
                courseEntity.setName(newName);
                courseEntity.setEndDate(newEndDate);
            } else {
                logger.warn("CourseEntity of id:{} - not found !", id);
            }

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private void removeLastFromList() {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            TypedQuery<CourseEntity> query = entityManager.createQuery("from CourseEntity", CourseEntity.class);
            List<CourseEntity> resultList = query.getResultList();

            if(!resultList.isEmpty()) {
                CourseEntity lastEntity = resultList.get(resultList.size() - 1);
                entityManager.remove(lastEntity);
                logger.info("Last entity removed, entity:{}", lastEntity);
            } else {
                logger.warn("Can't find any of CourseEntity in database!");
            }

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private void deleteEntity(int id) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            CourseEntity courseEntity = entityManager.find(CourseEntity.class, id);
            if (courseEntity != null) {
                entityManager.remove(courseEntity);
                logger.info("Entity removed, entity:{}", courseEntity);
            } else {
                logger.warn("CourseEntity of id:{} - not found !", id);
            }

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private void addFewEntities() {
        CourseEntity courseEntity1 = new CourseEntity("JavaGda1", "Rumia", Utils.parse("2018-01-01"), Utils.parse("2018-09-01"));
        CourseEntity courseEntity2 = new CourseEntity("JavaGda5", "Sopot", Utils.parse("2018-04-11"), Utils.parse("2018-12-20"));
        CourseEntity courseEntity3 = new CourseEntity("JavaGda11", "Gdańsk", Utils.parse("2018-03-01"), Utils.parse("2018-08-31"));
        CourseEntity courseEntity4 = new CourseEntity("JavaGda16", "Sopot", Utils.parse("2018-11-01"), Utils.parse("2019-04-30"));

        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(courseEntity1);
            logger.info("New course added, id:{}", courseEntity1.getId());

            entityManager.persist(courseEntity2);
            logger.info("New course added, id:{}", courseEntity2.getId());

            entityManager.persist(courseEntity3);
            logger.info("New course added, id:{}", courseEntity3.getId());

            entityManager.persist(courseEntity4);
            logger.info("New course added, id:{}", courseEntity4.getId());

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}