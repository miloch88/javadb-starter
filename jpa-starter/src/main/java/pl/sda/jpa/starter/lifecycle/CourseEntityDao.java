package pl.sda.jpa.starter.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.jpa.starter.inheritance.Course;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Date;
import java.util.List;

public class CourseEntityDao {
    private static Logger logger = LoggerFactory.getLogger(CourseEntityDao.class);

    private EntityManagerFactory entityManagerFactory;

    public CourseEntityDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public CourseEntity save(CourseEntity courseEntity) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(courseEntity);
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return courseEntity;
    }

    public void update(int id, String name, Date date) {

        EntityManager entityManager = null;
        CourseEntity ce = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            entityManager
                    .createQuery(String.format("UPDATE CourseEntity SET name = '%s' WHERE id= %d", name, id))
                    .executeUpdate();
            entityManager
                    .createQuery(String.format("UPDATE CourseEntity SET endDate = '%tF' WHERE id=%d", date, id))
                    .executeUpdate();

            entityManager.getTransaction().commit();

        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

    }

    //Napierw wyciągniej z listy a następnie usuń
    public void delete(CourseEntity courseEntity) {

        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

//            Moja wersja
            CourseEntity entity = entityManager.find(CourseEntity.class, courseEntity.getId());
            entityManager.remove(entity);


//            Twoja wersja
//            courseEntity = entityManager.merge(courseEntity);
//            entityManager.remove(courseEntity);

            entityManager.getTransaction().commit();

        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void deleteID(int id) {

        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            entityManager.remove(entityManager.find(CourseEntity.class, id));
            entityManager.getTransaction().commit();

        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public CourseEntity findById(int id) {

        EntityManager entityManager = null;
        CourseEntity ce;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            ce = entityManager.find(CourseEntity.class, id);

            entityManager.getTransaction().commit();

        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return ce;
    }

    public List<CourseEntity> list() {

        EntityManager entityManager = null;
        List<CourseEntity> listCE;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            listCE = entityManager.createQuery("FROM CourseEntity", CourseEntity.class).getResultList();
            entityManager.getTransaction().commit();

        } finally {
            if (entityManager != null) {
                entityManager.close();
            }

        }
        return listCE;
    }

}