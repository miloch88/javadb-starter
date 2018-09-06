package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

public class CoursesManager {

    private static Logger logger = LoggerFactory.getLogger(CoursesManager.class);

    private ConnectionFactory connectionFactory;

    public CoursesManager(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void createCoursesTable() throws SQLException {

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
//            String query1 = "DROP TABLE IF EXISTS courses CASCADE;";
//
//            statement.executeUpdate(query1);

            String query2 = "CREATE TABLE IF NOT EXISTS courses(" +
                    "id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(50)," +
                    "place VARCHAR(50)," +
                    "start_date DATE," +
                    "end_date DATE);";

            statement.executeUpdate(query2);

//            String query3 = "INSERT INTO courses (name, place, start_date, end_date)" +
//                    "VALUES" +
//                    " ('Java od podstaw', 'Gdańsk', '2018-06-12', '2018-11-07')," +
//                    " ('C++ od podstaw', 'Sopot', now(), '2018-12-15');";
//
//            statement.executeUpdate(query3);

            String query3 = "INSERT INTO courses (name, place, start_date, end_date) VALUES (?,?,?,?)";

            try (PreparedStatement ps = connection.prepareStatement(query3)) {

                ps.setString(1, "Java od podstaw");
                ps.setString(2, "Gdańsk");
                ps.setString(3, "2018-06-12");
                ps.setString(4, "2018-11-07");

                ps.executeUpdate();
            }

            try (PreparedStatement ps = connection.prepareStatement(query3)) {

                ps.setString(1, "C++ od podstaw");
                ps.setString(2, "Sopot");
                ps.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                ps.setDate(4, java.sql.Date.valueOf(LocalDate.of(2018, 12, 15)));

                ps.executeUpdate();
            }

            try (PreparedStatement ps = connection.prepareStatement(query3)) {

                ps.setString(1, "Kurs szydełkowania");
                ps.setString(2, "Gdynia");
                ps.setString(3, "2018-01-01");
                ps.setString(4, "2018-12-31");

                ps.executeUpdate();
            }

        }

    }

    public void updateCourseName(int id, String name) throws SQLException {

        String query = "UPDATE  courses SET name= ? WHERE id= ?";

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setInt(2, id);

            ps.executeUpdate();
        }
    }

//    public void printAllStudentsFromCourse(int id) throws SQLException {
//
//        try (Connection connection = connectionFactory.getConnection();
//             Statement statement = connection.createStatement()) {
//
//            String query = "SELECT * FROM students s LEFT JOIN courses c ON s.course_id = c.id WHERE c.id = ?;";
//            ResultSet resultSet = statement.executeQuery(query);
//
//            while (resultSet.next()) {
//                int id = resultSet.getInt("s.id");
//                String name = resultSet.getString("s.name");
//                int course_id = resultSet.getInt("s.course_id");
//                String description = resultSet.getString("s.description");
//                String seat = resultSet.getString("s.seat");
//                String name_course = resultSet.getString("c.name");
//
//                if (name_course == null) {
//                    name_course = "Brak kursu";
//                }
//
//                logger.info("id: {}, name: {}, course id: {}, description: {}, seat: {}, name course: {}",
//                        id, name, course_id, description, seat, name_course);
//            }
//        }
//    }

    public void createStudentsTable() throws SQLException {

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS students(" +
                    "id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(50)," +
                    "course_id INT," +
                    "description VARCHAR(200)," +
                    "seat VARCHAR(10)," +
                    "FOREIGN KEY (course_id) REFERENCES courses(id)" +
                    ");";

            statement.executeUpdate(query);
        }
    }

    public void addStudent(String name, Integer course_id, String description, String seat) throws SQLException {

        String query = "INSERT INTO students (name, course_id, description, seat) VALUES (?,?,?,?)";
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setObject(2, course_id);
            ps.setString(3, description);
            ps.setString(4, seat);

            ps.executeUpdate();

//            Version I
//                    String query = "INSERT INTO students (name, course_id, description, seat)" +
//                    "VALUES ('Michał', 1, 'Co ja tutaj robię!', 'B.2.1')," +
//                    "('Tomek', null, 'Nudzi mi się!', 'A.1.2'), " +
//                    "('Jaromir', 1, 'Potrzebuję kasy!', 'A.2.2')," +
//                    "('Klaudia', 2, 'Mama mi kazała!', 'C.2.2')," +
//                    "('Mateusz', 2, 'Kurs szydełkowania był już zajęty!', 'D.2.1');";
//
//            statement.executeUpdate(query);

//            Version II
//            String query1 = "INSERT INTO students (name, course_id, description, seat) VALUES (?,?,?,?)";
//
//            try (PreparedStatement ps = connection.prepareStatement(query1)) {
//
//                ps.setString(1, "Michał");
//                ps.setInt(2, 1);
//                ps.setString(3, "Co ja tutuaj robię?");
//                ps.setString(4, "B.2.1");
//
//                ps.executeUpdate();
//            }

        }
    }

    public void updateStudent(int id, String description, String seat) throws SQLException {

        String query = "UPDATE  students SET description= ?, seat = ? WHERE id = ?";
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, description);
            ps.setString(2, seat);
            ps.setInt(3, id);

            ps.executeUpdate();

        }
    }

    public void createAttendanceListTable() throws SQLException {

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS attendance_list(" +
                    "id	INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "student_id	INT," +
                    "course_id INT," +
                    "date DATETIME," +
                    "FOREIGN KEY (course_id) REFERENCES courses(id)," +
                    "FOREIGN KEY (student_id) REFERENCES students(id));";

            statement.executeUpdate(query);

//            String query1 = "INSERT INTO attendance_list (student_id, course_id, date)" +
//                    "VALUES " +
//                    "(1, 1, '2018-09-05')," +
//                    "(3, 1, '2018-09-05'), " +
//                    "(4, 2, '2018-09-05')," +
//                    "(5, 2, '2018-09-05')," +
//                    "(1, 2, '2018-09-06')," +
//                    "(3, 2, '2018-09-06')," +
//                    "(5, 2, '2018-09-06');";
//
//            statement.executeUpdate(query1);

            String query1 = "INSERT INTO attendance_list (student_id, course_id, date) VALUES (?,?,?)";

            try (PreparedStatement ps = connection.prepareStatement(query1)) {

                ps.setInt(1, 1);
                ps.setInt(2, 2);
                ps.setDate(3, java.sql.Date.valueOf(LocalDate.of(2018, 9, 05)));

                ps.executeUpdate();

            }

            try (PreparedStatement ps = connection.prepareStatement(query1)) {

                ps.setInt(1, 3);
                ps.setInt(2, 1);
                ps.setDate(3, java.sql.Date.valueOf(LocalDate.of(2018, 9, 05)));

                ps.executeUpdate();

            }

            try (PreparedStatement ps = connection.prepareStatement(query1)) {

                ps.setInt(1, 4);
                ps.setInt(2, 2);
                ps.setDate(3, java.sql.Date.valueOf(LocalDate.of(2018, 9, 05)));

                ps.executeUpdate();

            }

            try (PreparedStatement ps = connection.prepareStatement(query1)) {

                ps.setInt(1, 5);
                ps.setInt(2, 2);
                ps.setDate(3, java.sql.Date.valueOf(LocalDate.of(2018, 9, 05)));

                ps.executeUpdate();

            }

            try (PreparedStatement ps = connection.prepareStatement(query1)) {

                ps.setInt(1, 1);
                ps.setInt(2, 1);
                ps.setDate(3, java.sql.Date.valueOf(LocalDate.of(2018, 9, 06)));

                ps.executeUpdate();

            }

            try (PreparedStatement ps = connection.prepareStatement(query1)) {

                ps.setInt(1, 3);
                ps.setInt(2, 2);
                ps.setDate(3, java.sql.Date.valueOf(LocalDate.of(2018, 9, 06)));

                ps.executeUpdate();

            }

            try (PreparedStatement ps = connection.prepareStatement(query1)) {

                ps.setInt(1, 5);
                ps.setInt(2, 2);
                ps.setDate(3, java.sql.Date.valueOf(LocalDate.of(2018, 9, 06)));

                ps.executeUpdate();

            }

        }
    }

    public void deleteAttendanceInDate(int id, LocalDate date) throws SQLException {

        String query = "DELETE FROM attendance_list WHERE id= ? AND date= ?";
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.setDate(2, java.sql.Date.valueOf(date));

            ps.executeUpdate();
        }
    }

    public void dropAllTables() throws SQLException {

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            // dataSource.setAllowMultiQueries(true) w klasie Connection Factory
            String query = "DROP TABLE IF EXISTS attendance_list CASCADE;" +
                    "DROP TABLE IF EXISTS students CASCADE;" +
                    "DROP TABLE IF EXISTS courses CASCADE;";

            statement.executeUpdate(query);
        }
    }

    public void deleteCourse(int id) throws SQLException {

        String query = "DELETE FROM courses WHERE id= ?";
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);

            ps.executeUpdate();
        }
    }

    public void printAllCourses() throws SQLException {

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM courses;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
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
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM students s LEFT JOIN courses c ON s.course_id = c.id;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
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

    public void printAttendanceList() throws SQLException {

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT s.name, c.name, a.date FROM students s " +
                    "LEFT JOIN courses c ON s.course_id = c.id " +
                    "LEFT JOIN attendance_list a ON a.student_id = s.id;";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String name = resultSet.getString("s.name");
                String name_course = resultSet.getString("c.name");
                Date attendanceDate = resultSet.getDate("a.date");

                if (name_course == null) {
                    name_course = "Brak kursu";
                }

                logger.info("name: {}, name course: {}, attendane date: {}",
                        name, name_course, attendanceDate);
            }
        }
    }

    public static void main(String[] args) throws SQLException {

        ConnectionFactory connectionFactory = new ConnectionFactory("/sda_courses.database.properties");
        CoursesManager coursesManager = new CoursesManager(connectionFactory);

        //Usuwanie tablic
        coursesManager.dropAllTables();

        //Tworzenie nowch tablic
        coursesManager.createCoursesTable();

        coursesManager.deleteCourse(3);
        coursesManager.updateCourseName(2, "C++ dla zaawansowanych");

        coursesManager.createStudentsTable();
        coursesManager.addStudent("Michał", 1, "Co ja tutaj robię!", "B.2.1");
        coursesManager.addStudent("Tomek", null, "Nudzi mi się!", "A.1.2");
        coursesManager.addStudent("Jaromir", 1, "Potrzebuję kasy!", "B.2.2");
        coursesManager.addStudent("Klaudia", 2, "Mama mi kazała", "C.2.2");
        coursesManager.addStudent("Mateusz", 2, "Kurs szydełkowania był już zajęty", "C.1.2");

        coursesManager.updateStudent(1, "Zbłądziłem", "A.1.1");

        coursesManager.createAttendanceListTable();

        coursesManager.deleteAttendanceInDate(1, LocalDate.of(2018,9,5));

        coursesManager.printAllCourses();

        System.out.println();

        coursesManager.printAllStudents();

        System.out.println();

        coursesManager.printAttendanceList();

    }
}
