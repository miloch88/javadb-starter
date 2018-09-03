package pl.sda.jpa.starter.queries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.jpa.starter.queries.entities.EntitiesLoader;
import pl.sda.jpa.starter.queries.entities.StudentEntity;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StudentEntityDaoExt {
    private static Logger logger = LoggerFactory.getLogger(CourseEntityDaoExt.class);
    private EntityManagerFactory entityManagerFactory;

    public StudentEntityDaoExt() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter.queries");
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void close() {
        entityManagerFactory.close();
    }

    public static void main(String[] args) {
        StudentEntityDaoExt dao = new StudentEntityDaoExt();
        try {
            EntitiesLoader.fillDataBase(dao.getEntityManagerFactory());
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            dao.close();
        }
    }

    /**
     * Metoda wyszukuje wszystkich studentów zapisanych do kursu o nazwie podanej w parametrze 'courseName'
     * siedzących w rzędzie wskazanym przez parametr 'rowId'.
     */
    public List<StudentEntity> findBySeatRow(String courseName, int rowId) {
        return new ArrayList<>();
    }

    /**
     * Metoda wyszukuje wszystkich studentów mieszkających w mieście wskazanym przez parametr 'cityName', posortowanych według nazwy miasta rosnąco .
     */
    public List<StudentEntity> findByCityAddress(String cityName) {
        return new ArrayList<>();
    }

    /**
     * Metoda wyszukuje wszystkich studentów posiadających co najmniej jedną umiejętności ze zbioru podanego w parametrze 'skillsNames'.
     */
    public List<StudentEntity> findBySkills(Set<String> skillsNames) {
        return new ArrayList<>();
    }

    /**
     * Metoda wyszukuje studentów posiadających co najmniej tyle umiejętności ile wskazuje parametr 'minSkillsCount'.
     * Metoda ma zwrócić maksymalnie tylu studentów ile wskazuje parametr 'studentsLimit', posortowanych od tych którzy mają najwięcej umięjętności.
     */
    public List<StudentEntity> findTheMostSkilled(int studentsLimit, long minSkillsCount) {
        return new ArrayList<>();
    }
}
