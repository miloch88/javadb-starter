package pl.sda.jpa.zoo_keeper_jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ZooKeeperJpa {
    public static void main(String[] args) {

        EntityManagerFactory emf = null;
        EntityManager em = null;

        try{
            emf = Persistence.createEntityManagerFactory("pl.sda.jpa.zoo_keeper_jpa");
            em = emf.createEntityManager();
            AnimalTypeEntityDAO dao = new AnimalTypeEntityDAO(emf);

            EntityTransaction et = em.getTransaction();
            et.begin();

            //Dodajemy zwierzątka na dwa sposoby
            AnimalTypeEntity sowa = new AnimalTypeEntity("Sowa");
            AnimalTypeEntity lis = new AnimalTypeEntity("Lis");
            AnimalTypeEntity rys = new AnimalTypeEntity("Ryś");
            dao.add(sowa);
            dao.add(lis);
            dao.add(rys);
            dao.add(new AnimalTypeEntity("Bóbr"));
            dao.add(new AnimalTypeEntity("Jeż"));
            dao.add(new AnimalTypeEntity("Zając"));

            /*
            Pobieramy listę zwierzątek
            System.out.println(dao.list());
            */

            /*
            Pobieramy zwierzaka o id
            System.out.println(dao.get(2));
             */

            /*Usuwamy zwierzaka
            dao.delete(sowa);
            */

            dao.update(sowa,"kalafior");

            et.commit();
        }finally {
            if(em != null){
                em.close();
                emf.close();
            }
        }
    }

}
