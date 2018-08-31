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
        try {
            CourseEntityDao dao = new CourseEntityDao(entityManagerFactory);
            try {
                /*dao.save(new CourseEntity("JavaGda1", "Rumia", Utils.parse("2018-01-01"), Utils.parse("2018-09-01")));
                dao.save(new CourseEntity("JavaGda2", "Reda", Utils.parse("2018-01-11"), Utils.parse("2018-09-011")));
                dao.save(new CourseEntity("JavaGda5", "Gdańsk", Utils.parse("2018-03-01"), Utils.parse("2018-11-01")));*/

                /*CourseEntity courseEntity = dao.findById(2);
                courseEntity.setName("Kurs spadochronowy");
                courseEntity.setPlace("Warszawa");
                dao.update(courseEntity);*/

                /*List<CourseEntity> courseEntities = dao.list();
                if (!courseEntities.isEmpty()) {
                    dao.delete(courseEntities.get(0));
                }*/

                /*CourseEntity courseEntity = dao.findById(3);
                dao.delete(courseEntity);*/

            } catch (Exception e) {
                logger.error("Error", e);
            }
        } finally {
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        }
    }
}
