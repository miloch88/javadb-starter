package pl.sda.hibernate.starter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.commons.Utils;
import pl.sda.hibernate.starter.pojo_entities.Course;

public class SimpleHibernateConfiguration {
    private static Logger logger = LoggerFactory.getLogger(SimpleHibernateConfiguration.class);

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Course course = new Course("JavaGda11", "Sopot", Utils.parse("2018-01-01"), Utils.parse("2018-09-01"));
        logger.info("Before: {}", course);
        Integer id = (Integer) session.save(course);
        logger.info("Id: {}", id);
        logger.info("After: {}", course);

        transaction.commit();
        session.close();
        factory.close();
    }
}