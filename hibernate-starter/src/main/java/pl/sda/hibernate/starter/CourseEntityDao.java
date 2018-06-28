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
import pl.sda.hibernate.starter.entities.CourseEntity;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CourseEntityDao {
    private static Logger logger = LoggerFactory.getLogger(CourseEntityDao.class);
    private final SessionFactory sessionFactory;

    public CourseEntityDao() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate-2.cfg.xml")
                .build();

        Metadata metadata = new MetadataSources(registry)
                .addAnnotatedClass(CourseEntity.class)
                .buildMetadata();

        sessionFactory = metadata.buildSessionFactory();
    }

    public void finish() {
        sessionFactory.close();
    }

    public CourseEntity save(CourseEntity courseEntity) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(courseEntity);
            transaction.commit();
        }
        return courseEntity;
    }

    public CourseEntity update(CourseEntity courseEntity) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(courseEntity);
            transaction.commit();
        }
        return courseEntity;
    }

    public void delete(CourseEntity courseEntity) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(courseEntity);
            transaction.commit();
        }
    }

    public CourseEntity findById(int id) {
        CourseEntity courseEntity;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            courseEntity = session.get(CourseEntity.class, id);
            transaction.commit();
        }
        return courseEntity;
    }

    public List<CourseEntity> list() {
        List<CourseEntity> entityList;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            entityList = session.createQuery("from CourseEntity").list();
            transaction.commit();
        }
        return entityList;
    }

    public static void main(String[] args) {
        CourseEntityDao dao = new CourseEntityDao();

        CourseEntity courseEntity = new CourseEntity("Test", "test", new Date(123), new Date(900));

        dao.save(courseEntity);

        courseEntity.setName("XYZ");
        dao.update(courseEntity);

        CourseEntity byId = dao.findById(courseEntity.getId());
        logger.info("byId: {}", byId);

        List<CourseEntity> list = dao.list();
        logger.info("entityList: \n{}", list.stream()
                .map(CourseEntity::toString)
                .collect(Collectors.joining("\n")));

        dao.delete(courseEntity);

        list = dao.list();
        logger.info("entityList: \n{}", list.stream()
                .map(CourseEntity::toString)
                .collect(Collectors.joining("\n")));

        dao.finish();
    }
}