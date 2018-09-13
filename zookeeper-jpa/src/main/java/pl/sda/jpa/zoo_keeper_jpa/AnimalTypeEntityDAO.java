package pl.sda.jpa.zoo_keeper_jpa;

import org.slf4j.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class AnimalTypeEntityDAO {
    private static Logger logger = LoggerFactory.getLogger(AnimalTypeEntityDAO.class);

    private EntityManagerFactory emf;

    public AnimalTypeEntityDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public AnimalTypeEntity add(AnimalTypeEntity ate){
        EntityManager em = null;
        try{

            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(ate);
            em.getTransaction().commit();

        }finally {
            if(em != null){
                em.close();
            }
        }
        return ate;
    }

    public List<AnimalTypeEntity> list(){
        EntityManager em = null;
        List<AnimalTypeEntity> list;
        try{

            em = emf.createEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("FROM AnimalTypeEntity");
            list = query.getResultList();

            em.getTransaction().commit();

        }finally {
            if(em != null){
                em.close();
            }
        }
        return list;
    }

    public AnimalTypeEntity get(int id){
        EntityManager em = null;
        AnimalTypeEntity ate;
        try{

            em = emf.createEntityManager();
            em.getTransaction().begin();

            ate = em.find(AnimalTypeEntity.class, id);

            em.getTransaction().commit();

        }finally {
            if(em != null){
                em.close();
            }
        }
        return ate;
    }

    public void delete(AnimalTypeEntity ato){
        EntityManager em = null;
        List<AnimalTypeEntity> list;
        try{

            em = emf.createEntityManager();
            em.getTransaction().begin();

            Query q = em.createQuery("SELECT a FROM AnimalTypeEntity a WHERE a.id = :id")
                    .setParameter("id", ato.getId());
            List result = q.getResultList();
            System.out.println(result);
            em.remove(result.get(0));

            em.getTransaction().commit();

        }finally {
            if(em != null){
                em.close();
            }
        }
    }

    public void update(AnimalTypeEntity ato, String name){
        EntityManager em = null;
        AnimalTypeEntity ate;
        try{

            em = emf.createEntityManager();
            em.getTransaction().begin();

            int id = ato.getId();

            em
                    .createQuery(String.format("UPDATE AnimalTypeEntity SET name = '%s' WHERE id= %d", name, id))
                    .executeUpdate();

            em.getTransaction().commit();

        }finally {
            if(em != null){
                em.close();
            }
        }
    }
}
