package pl.sda.hibernate.starter;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.hibernate.starter.entities.CourseEntity;

import java.util.List;

public class CourseEntityDao {
    private static Logger logger = LoggerFactory.getLogger(CourseEntityDao.class);
    private final SessionFactory sessionFactory;

    public CourseEntityDao() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        Metadata metadata = new MetadataSources(registry)
                .addAnnotatedClass(CourseEntity.class)
                .buildMetadata();

        sessionFactory = metadata.buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public CourseEntity save(CourseEntity courseEntity) {
        return null;
    }

    public CourseEntity update(CourseEntity courseEntity) {
        return null;
    }

    public void delete(CourseEntity courseEntity) {
    }

    public CourseEntity findById(int id) {
        return null;
    }

    public List<CourseEntity> list() {
        return null;
    }

    public static void main(String[] args) {
    }
}