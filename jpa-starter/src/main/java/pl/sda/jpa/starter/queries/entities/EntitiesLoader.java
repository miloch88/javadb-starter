package pl.sda.jpa.starter.queries.entities;

import pl.sda.commons.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EntitiesLoader {
    public static void fillDataBase(EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            CourseEntity javaGda1 = new CourseEntity("JavaSop1", "Sopot", Utils.parse("2018-01-01"), Utils.parse("2018-09-01"));
            entityManager.persist(javaGda1);
            entityManager.persist(new CourseEntity("JavaGda5", "Gdansk", Utils.parse("2018-02-01"), Utils.parse("2018-10-01")));
            CourseEntity javaGda6 = new CourseEntity("AngularGdy6", "Gdynia", Utils.parse("2018-02-15"), Utils.parse("2018-10-15"));
            entityManager.persist(javaGda6);
            CourseEntity javaGda11 = new CourseEntity("JavaSop11", "Sopot", Utils.parse("2018-03-01"), Utils.parse("2018-11-01"));
            entityManager.persist(javaGda11);
            CourseEntity javaGda15 = new CourseEntity("C++Gdy15", "Gdynia", Utils.parse("2018-04-01"), Utils.parse("2018-12-01"));
            entityManager.persist(javaGda15);

            SkillEntity hibernateMaster = new SkillEntity("Hibernate Master");
            SkillEntity jdbcMaster = new SkillEntity("JDBC Master");
            SkillEntity jvmMaster = new SkillEntity("JVM Master");
            SkillEntity testsMaster = new SkillEntity("Tests Master");
            SkillEntity oopMaster = new SkillEntity("OOP Master");

            StudentEntity kowalski = new StudentEntity("Jan Kowalski");
            kowalski.setCourse(javaGda1)
                    .setAge(20)
                    .setAddress(new AddressEntity("Sopot", "Malinowa 1/3"))
                    .addSkill(hibernateMaster)
                    .addSkill(jdbcMaster)
                    .setSeat(new SeatEntity("A", 1, 2));
            entityManager.persist(kowalski);

            StudentEntity malinowski = new StudentEntity("Adam Malinowski");
            malinowski.setCourse(javaGda1)
                    .setAge(30)
                    .setAddress(new AddressEntity("Gdańsk", "Jantarowa 3/3"))
                    .addSkill(hibernateMaster)
                    .addSkill(jdbcMaster)
                    .addSkill(testsMaster)
                    .setSeat(new SeatEntity("A", 4, 1));
            entityManager.persist(malinowski);

            StudentEntity nowak = new StudentEntity("Eliza Nowak");
            nowak.setCourse(javaGda1)
                    .setAge(20)
                    .setAddress(new AddressEntity("Sopot", "Haffera 23/3"))
                    .addSkill(oopMaster)
                    .setSeat(new SeatEntity("A", 4, 2));
            entityManager.persist(nowak);

            StudentEntity zloto = new StudentEntity("Urszula Złoto");
            zloto.setCourse(javaGda1)
                    .setAge(18)
                    .setAddress(new AddressEntity("Rumia", "Srebrna 103"))
                    .addSkill(oopMaster)
                    .addSkill(testsMaster)
                    .addSkill(jvmMaster)
                    .setSeat(new SeatEntity("B", 3, 1));
            entityManager.persist(zloto);

            StudentEntity rolnik = new StudentEntity("Wiesław Rolnik");
            rolnik.setCourse(javaGda6)
                    .setAge(30)
                    .setAddress(new AddressEntity("Kościerzyna", "Mała 4"))
                    .addSkill(oopMaster)
                    .addSkill(testsMaster)
                    .addSkill(hibernateMaster)
                    .addSkill(jdbcMaster)
                    .addSkill(jvmMaster)
                    .setSeat(new SeatEntity("C", 1, 2));
            entityManager.persist(rolnik);

            StudentEntity szybki = new StudentEntity("Mieczysław Szybki");
            szybki.setCourse(javaGda6)
                    .setAge(50)
                    .setAddress(new AddressEntity("Kościerzyna", "Duża 14"))
                    .setSeat(new SeatEntity("B", 3, 1));
            entityManager.persist(szybki);

            StudentEntity szara = new StudentEntity("Wioletta Szara");
            szara.setCourse(javaGda15)
                    .setAge(20)
                    .setAddress(new AddressEntity("Pruszcz Gdański", "Polna 4/8"))
                    .addSkill(testsMaster)
                    .addSkill(jvmMaster)
                    .setSeat(new SeatEntity("B", 5, 2));
            entityManager.persist(szara);

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
