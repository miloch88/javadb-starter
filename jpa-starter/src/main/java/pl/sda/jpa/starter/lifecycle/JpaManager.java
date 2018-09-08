package pl.sda.jpa.starter.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.commons.Utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class JpaManager {
    private static Logger logger = LoggerFactory.getLogger(JpaManager.class);

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter.lifecycle");

        //Musimy wykonywać w try
        try {

            CourseEntityDao courseEntityDao = new CourseEntityDao(entityManagerFactory);

            CourseEntity javaGda11 = new CourseEntity("JavaGda11", "Sopot", Utils.parse("2018-01-01"), Utils.parse("2018-09-01"));
            CourseEntity javaGda22 = new CourseEntity("JavaGda22", "Gdynia", Utils.parse("2018-02-15"), Utils.parse("2018-10-13"));
            CourseEntity javaGda33 = new CourseEntity("Kurs Szydełkowania", "Gdansk", Utils.parse("2018-03-20"), Utils.parse("2018-11-05"));

            //Dodawanie
//            courseEntityDao.save(javaGda11);
//            courseEntityDao.save(javaGda33);
//            courseEntityDao.save(javaGda22);

            //Usuwanie
//            courseEntityDao.delete(javaGda33);
//            courseEntityDao.deleteID(1);

            //Znajdowanie
//            System.out.println(courseEntityDao.findById(1));

            //Pobieranie listy
//            List<CourseEntity> list = courseEntityDao.list();
//            System.out.println(list);
//            // Hibernate i db zaczyna liczyć od 1
//            courseEntityDao.deleteID(list.size());
//            System.out.println(list);

            //Update
//            courseEntityDao.update(2, "Yaba Daba Doo", Utils.parse("2220-02-22"));

            entityManagerFactory.close();

        } catch (Exception e) {
            logger.error("Error", e);
        } finally {
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        }
    }

}
