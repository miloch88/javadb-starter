package pl.sda.jpa.starter;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.commons.Utils;
import pl.sda.jpa.starter.basic_entities.CourseEntity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JpaCriteria {
    private static Logger logger = LoggerFactory.getLogger(JpaCriteria.class);
    private EntityManagerFactory entityManagerFactory;

    public void createEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter");
    }

    public void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }

    private void fillDataBase() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(new CourseEntity("JavaGda1", "Wejherowo", Utils.parse("2018-01-01"), Utils.parse("2018-09-01")));
        entityManager.persist(new CourseEntity("JavaGda5", "Gdansk", Utils.parse("2018-02-01"), Utils.parse("2018-10-01")));
        entityManager.persist(new CourseEntity("JavaGda6", "Rumia", Utils.parse("2018-02-15"), Utils.parse("2018-10-15")));
        entityManager.persist(new CourseEntity("JavaGda11", "Sopot", Utils.parse("2018-03-01"), Utils.parse("2018-11-01")));
        entityManager.persist(new CourseEntity("JavaGda15", "Rumia", Utils.parse("2018-04-01"), Utils.parse("2018-12-01")));

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private void simpleQuery() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //odpowiednik zapytania JPQL: "SELECT c FROM CourseEntity c"
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CourseEntity> criteriaQuery = criteriaBuilder.createQuery(CourseEntity.class);
        Root<CourseEntity> root = criteriaQuery.from(CourseEntity.class);
        criteriaQuery.select(root);

        //od tego momentu standardowo wyciągamy wyniki zapytania
        TypedQuery<CourseEntity> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setMaxResults(10);
        List<CourseEntity> courseEntities = typedQuery.getResultList();
        printList(courseEntities);

        //odpowiednik zapytania JPQL: "SELECT c FROM CourseEntity c WHERE c.place = :place"
        criteriaQuery = criteriaBuilder.createQuery(CourseEntity.class);
        root = criteriaQuery.from(CourseEntity.class);
        ParameterExpression<String> placeParameter = criteriaBuilder.parameter(String.class);
        criteriaQuery.select(root)
                     .where(criteriaBuilder.equal(root.get("place"), placeParameter));

        typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setParameter(placeParameter, "Sopot");
        courseEntities = typedQuery.getResultList();
        printList(courseEntities);

        //odpowiednik zapytania JPQL: "SELECT c FROM CourseEntity c WHERE c.place IN :place"
        criteriaQuery = criteriaBuilder.createQuery(CourseEntity.class);
        root = criteriaQuery.from(CourseEntity.class);
        ParameterExpression<Set> placesParameter = criteriaBuilder.parameter(Set.class);
        criteriaQuery.select(root)
                .where(root.get("place").in(placesParameter));

        typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setParameter(placesParameter, Sets.newHashSet("Sopot", "Rumia"));
        courseEntities = typedQuery.getResultList();
        printList(courseEntities);


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

    public static void main(String[] args) {
        JpaCriteria jpaCriteria = new JpaCriteria();
        try {
            jpaCriteria.createEntityManagerFactory();
            jpaCriteria.fillDataBase();
            jpaCriteria.simpleQuery();
        } finally {
            jpaCriteria.closeEntityManagerFactory();
        }
    }

}