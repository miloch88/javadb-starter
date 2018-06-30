package pl.sda.jpa.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.commons.Utils;
import pl.sda.jpa.starter.basic_entities.CourseEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class CourseEntityDao {
    private static Logger logger = LoggerFactory.getLogger(CourseEntityDao.class);
    private EntityManagerFactory entityManagerFactory;

    public CourseEntityDao() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter");
    }

    public void close() {
        entityManagerFactory.close();
    }

    public static void main(String[] args) {
        CourseEntityDao dao = new CourseEntityDao();
        try {
            dao.save(new CourseEntity("JavaGda1", "Rumia", Utils.parse("2018-01-01"), Utils.parse("2018-09-01")));

            /*CourseEntity courseEntity = dao.findById(2);
            courseEntity.setName("Kurs spadochronowy");
            courseEntity.setPlace("Warszawa");
            dao.update(courseEntity);*/

            /*List<CourseEntity> courseEntities = dao.list();
            if(!courseEntities.isEmpty()) {
                dao.delete(courseEntities.get(0));
            }*/

        } catch (Exception e) {
            logger.error("Error", e);
        } finally {
            dao.close();
        }
    }

    public CourseEntity save(CourseEntity courseEntity) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(courseEntity);
            logger.info("New course added, courseEntity:{}", courseEntity);

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return courseEntity;
    }

    public CourseEntity update(CourseEntity courseEntity) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            courseEntity = entityManager.merge(courseEntity);
            logger.info("Course updated, courseEntity:{}", courseEntity);

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return courseEntity;
    }

    public void delete(CourseEntity courseEntity) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            courseEntity = entityManager.merge(courseEntity);
            entityManager.remove(courseEntity);
            logger.info("Course removed, courseEntity:{}", courseEntity);

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public CourseEntity findById(int id) {
        EntityManager entityManager = null;
        CourseEntity courseEntity;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            courseEntity = entityManager.find(CourseEntity.class, id);

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return courseEntity;
    }

    public List<CourseEntity> list() {
        EntityManager entityManager = null;
        List<CourseEntity> courseEntities;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            TypedQuery<CourseEntity> query = entityManager.createQuery("from CourseEntity", CourseEntity.class);
            courseEntities = query.getResultList();

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return courseEntities;
    }
}