package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class CoursesManager {

    private static Logger logger = LoggerFactory.getLogger(CoursesManager.class);

    private ConnectionFactory connectionFactory;

    public CoursesManager(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void createCoursesTable() throws SQLException {

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement())
        {
//            String query1 = "DROP TABLE IF EXISTS courses CASCADE;";
//
//            statement.executeUpdate(query1);

            //CREATE TABLE IF NOT EXISTS
            String query2 = "CREATE TABLE IF NOT EXISTS courses(" +
                            "id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                            "name VARCHAR(50)," +
                            "place VARCHAR(50)," +
                            "start_date DATE," +
                            "end_date DATE);" ;

            statement.executeUpdate(query2);

            String query3 = "INSERT INTO courses (name, place, start_date, end_date)" +
                            "VALUES" +
                            " ('Java od podstaw', 'Gdańsk', '2018-06-12', '2018-11-07')," +
                            " ('C++ od podstaw', 'Sopot', now(), '2018-12-15');";

            statement.executeUpdate(query3);

        }

    }

    public void createStudentsTable() throws SQLException {

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement())
        {
            String query =  "CREATE TABLE IF NOT EXISTS students(" +
                            "id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                            "name VARCHAR(50)," +
                            "course_id INT," +
                            "description VARCHAR(200)," +
                            "seat VARCHAR(10)," +
                            "FOREIGN KEY (course_id) REFERENCES courses(id)" +
                            ");";

            statement.executeUpdate(query);

            String query1 = "INSERT INTO students (name, course_id, description, seat)" +
                    "VALUES ('Michał', 1, 'Co ja tutaj robię!', 'B.2.1')," +
                    "('Tomek', null, 'Nudzi mi się!', 'A.1.2'), " +
                    "('Jaromir', 1, 'Potrzebuję kasy!', 'A.2.2')," +
                    "('Klaudia', 2, 'Mama mi kazała!', 'C.2.2')," +
                    "('Mateusz', 2, 'Kurs szydełkowania był już zajęty!', 'D.2.1');";

            statement.executeUpdate(query1);
        }
    }

    public void createAttendanceListTable() throws SQLException {

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement())
        {
            String query =  "CREATE TABLE IF NOT EXISTS attendance_list(" +
                            "id	INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                            "student_id	INT," +
                            "course_id INT," +
                            "date DATETIME," +
                            "FOREIGN KEY (course_id) REFERENCES courses(id)," +
                            "FOREIGN KEY (student_id) REFERENCES students(id));";

            statement.executeUpdate(query);
        }
    }

    public void dropAllTables() throws SQLException {

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement())
        {
            // dataSource.setAllowMultiQueries(true) w klasie Connection Factory
            String query =  "DROP TABLE IF EXISTS attendance_list CASCADE;" +
                            "DROP TABLE IF EXISTS students CASCADE;" +
                            "DROP TABLE IF EXISTS courses CASCADE;";

            statement.executeUpdate(query);
        }

    }

    public void printAllCourses() throws SQLException {

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement())
        {

            String query =  "SELECT * FROM courses;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String place = resultSet.getString("place");
                Date start_date = resultSet.getDate("start_date");
                Date end_date = resultSet.getDate("end_date");
                logger.info("id: {}, name: {}, place: {}, start date: {}, end start: {}",
                        id, name, place, start_date, end_date);
            }
        }
    }

    public void printAllStudents() throws SQLException {

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement())
        {

            String query =  "SELECT * FROM students s LEFT JOIN courses c ON s.course_id = c.id;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("s.id");
                String name = resultSet.getString("s.name");
                int course_id = resultSet.getInt("s.course_id");
                String description = resultSet.getString("s.description");
                String seat = resultSet.getString("s.seat");
                String name_course = resultSet.getString("c.name");

                if (name_course == null) {
                    name_course = "Brak kursu";
                }

                logger.info("id: {}, name: {}, course id: {}, description: {}, seat: {}, name course: {}",
                        id, name, course_id, description, seat, name_course);
            }
        }
    }

    public static void main(String[] args) throws SQLException {

        ConnectionFactory connectionFactory = new ConnectionFactory("/sda_courses.database.properties");
        CoursesManager coursesManager = new CoursesManager(connectionFactory);

        coursesManager.createCoursesTable();
        coursesManager.createStudentsTable();
        coursesManager.createAttendanceListTable();

        coursesManager.dropAllTables();

        coursesManager.printAllCourses();

        System.out.println();

        coursesManager.printAllStudents();

    }
}
