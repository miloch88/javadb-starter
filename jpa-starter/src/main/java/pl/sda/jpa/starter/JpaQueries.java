package pl.sda.jpa.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.commons.Utils;
import pl.sda.jpa.starter.basic_entities.CourseEntity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JpaQueries {
    private static Logger logger = LoggerFactory.getLogger(JpaQueries.class);
    private EntityManagerFactory entityManagerFactory;

    public JpaQueries() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter");
    }

    public void close() {
        entityManagerFactory.close();
    }

    public static void main(String[] args) {
        JpaQueries jpaQueries = new JpaQueries();
        try {
            jpaQueries.fillDataBase();
            jpaQueries.simpleQuery();
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            jpaQueries.close();
        }
    }

    private void simpleQuery() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        /**
         *  krótka forma: "FROM CourseEntity"
         */
        Query simpleQuery = entityManager.createQuery("SELECT c FROM CourseEntity c");
        List resultList = simpleQuery.getResultList();
        printList(resultList);

        /**
         *  to samo co wyżej ale używamy TypeQuery
         */
        TypedQuery<CourseEntity> typedQuery = entityManager.createQuery("SELECT c FROM CourseEntity c", CourseEntity.class);
        /**
         *  polecenie SELECT na bazie jest wykonywane dopiero przy wywołaniu metod pobierających wyniki, np.: getResultList(), getResultStream()
         */
        List<CourseEntity> courseEntities = typedQuery.getResultList();
        printList(courseEntities);

        /**
         *  możemy też pobrać wyniki jako Stream, zauważ że polecenie SELECT jest ponownie wykonywane
         */
        typedQuery.getResultStream().forEach(entity -> logger.info("{} : {}", entity.getName(), entity.getPlace()));

        /**
         *  pobieramy tylko nazwy i miasto za pomocą NamedQuery, zapytanie jest zapisane w CourseEntity, tutaj tylko wykonujemy je po nazwie
         */
        simpleQuery = entityManager.createNamedQuery("CourseEntity.selectNameAndPlace");
        resultList = simpleQuery.getResultList();
        printList(resultList);

        /**
         *  sortujemy po nazwie
         */
        simpleQuery = entityManager.createQuery("SELECT c.name, c.place FROM CourseEntity c ORDER BY c.name ASC");
        resultList = simpleQuery.getResultList();
        printList(resultList);

        /**
         *  zawężamy tylko do kursów z Sopotu
         */
        simpleQuery = entityManager.createQuery("SELECT c.name, c.place FROM CourseEntity c WHERE c.place = :place");
        simpleQuery.setParameter("place", "Sopot");
        resultList = simpleQuery.getResultList();
        printList(resultList);

        /**
         *  zawężamy tylko do kursów z Gdynia i pobieramy tylko jeden
         */
        typedQuery = entityManager.createQuery("SELECT c FROM CourseEntity c WHERE c.place = :place", CourseEntity.class);
        typedQuery.setParameter("place", "Gdynia");
        typedQuery.setMaxResults(1);
        CourseEntity courseInGdynia = typedQuery.getSingleResult();
        logger.info("Single result: {}", courseInGdynia);

        /**
         *  dodajemy stronicowanie
         */
        simpleQuery = entityManager.createQuery("FROM CourseEntity");
        simpleQuery.setFirstResult(1);
        simpleQuery.setMaxResults(2);
        resultList = simpleQuery.getResultList();
        printList(resultList);

        /**
         *  czemu EntityManage nie implementuje AutoClosable? https://github.com/javaee/jpa-spec/issues/77
         */
        entityManager.close();
    }

    private void fillDataBase() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(new CourseEntity("JavaGda1", "Sopot", Utils.parse("2018-01-01"), Utils.parse("2018-09-01")));
        entityManager.persist(new CourseEntity("JavaGda5", "Gdansk", Utils.parse("2018-02-01"), Utils.parse("2018-10-01")));
        entityManager.persist(new CourseEntity("JavaGda6", "Gdynia", Utils.parse("2018-02-15"), Utils.parse("2018-10-15")));
        entityManager.persist(new CourseEntity("JavaGda11", "Sopot", Utils.parse("2018-03-01"), Utils.parse("2018-11-01")));
        entityManager.persist(new CourseEntity("JavaGda15", "Gdynia", Utils.parse("2018-04-01"), Utils.parse("2018-12-01")));

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @SuppressWarnings("unchecked")
    private void printList(List list) {
        logger.info("entityList: \n{}", list.stream()
                .map(element ->
                        {
                            if (element instanceof Object[]) {
                                return Arrays.stream((Object[]) element).map(Object::toString).collect(Collectors.joining(", "));
                            } else {
                                return element.toString();
                            }
                        }
                )
                .collect(Collectors.joining("\n")));
    }
}