package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class CoursesManager {
    private static Logger logger = LoggerFactory.getLogger(CoursesManager.class);
    private ConnectionFactory connectionFactory = new ConnectionFactory("/sda-courses-database.properties");

    public void createCoursesTable() throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS sda_courses.courses (\n" +
                    "  id INT NOT NULL AUTO_INCREMENT,\n" +
                    "  name VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,\n" +
                    "  place VARCHAR(45) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,\n" +
                    "  start_date DATE NOT NULL,\n" +
                    "  end_date DATE NOT NULL,\n" +
                    "PRIMARY KEY (id))\n" +
                    "  ENGINE = InnoDB;");
            logger.info("Table: sda_courses.courses created!");

            statement.executeUpdate("INSERT INTO courses(name, place, start_date, end_date) VALUES('JavaGda10', 'Sopot', '2018-06-01', '2018-12-12')");
            statement.executeUpdate("INSERT INTO courses(name, place, start_date, end_date) VALUES('JavaGda17', 'Gdansk', '2018-03-01', '2018-10-18')");

            logger.info("Table: sda_courses.courses filled with data!");
        }
    }

    public void createStudentsTable() throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS sda_courses.students (\n" +
                    "  id INT NOT NULL AUTO_INCREMENT,\n" +
                    "  name VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,\n" +
                    "  course_id INT DEFAULT NULL,\n" +
                    "  description VARCHAR(200) DEFAULT NULL,\n" +
                    "  seat VARCHAR(10) DEFAULT NULL,\n" +
                    "PRIMARY KEY (id),\n" +
                    "FOREIGN KEY (course_id) REFERENCES sda_courses.courses(id))\n" +
                    " ENGINE = InnoDB;");
            logger.info("Table: sda_courses.students created!");

            statement.executeUpdate("INSERT INTO students(name, course_id, description, seat) VALUES('Jarek', 1, 'Lubię kodować!', 'A.1.1')");
            statement.executeUpdate("INSERT INTO students(name) VALUES('Patrycja')");

            logger.info("Table: sda_courses.students filled with data!");
        }
    }

    public void createAttendanceListTable() throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS sda_courses.attendance_list(\n" +
                    "  id INT NOT NULL AUTO_INCREMENT,\n" +
                    "  student_id INT DEFAULT NULL,\n" +
                    "  course_id INT DEFAULT NULL,\n" +
                    "  date DATETIME DEFAULT NULL,\n" +
                    "PRIMARY KEY (id),\n" +
                    "FOREIGN KEY (student_id) REFERENCES sda_courses.students(id),\n" +
                    "FOREIGN KEY (course_id) REFERENCES sda_courses.courses(id))\n" +
                    "ENGINE = InnoDB;");
            logger.info("Table: sda_courses.attendance_list created!");

            statement.executeUpdate("INSERT INTO attendance_list(student_id, course_id, date) VALUES(1, 1, '2018-06-01 10:00:15')");

            logger.info("Table: sda_courses.attendance_list filled with data!");
        }
    }

    public void dropAllTables() throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE sda_courses.attendance_list;");
            statement.executeUpdate("DROP TABLE sda_courses.students;");
            statement.executeUpdate("DROP TABLE sda_courses.courses;");
            logger.info("All tables dropped");
        }
    }

    private void printAllCourses() throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT id, name, place, start_date, end_date FROM courses");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String place = resultSet.getString("place");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");
                logger.info("{}, {}, {}, {} - {}", id, name, place, startDate, endDate);
            }
        }
    }

    public void printAllStudents() throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT s.id, s.name, c.name AS course_name, s.description, s.seat" +
                    " FROM students AS s" +
                    " LEFT JOIN courses AS c ON s.course_id = c.id");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String seat = resultSet.getString("seat");
                String courseName = resultSet.getString("course_name");
                courseName = (courseName == null) ? "brak kursu" : courseName;
                logger.info("{}, {}, {}, {}, {}", id, name, description, seat, courseName);
            }
        }
    }

    public void printAttendanceList() throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT a.id, s.name AS student_name, c.name AS course_name, a.date" +
                    " FROM attendance_list AS a" +
                    " JOIN courses AS c ON a.course_id = c.id" +
                    " JOIN students AS s ON a.student_id = s.id");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String studentName = resultSet.getString("student_name");
                String courseName = resultSet.getString("course_name");
                Timestamp date = resultSet.getTimestamp("date");
                logger.info("{}, {}, {}, {}", id, studentName, courseName, date);
            }
        }
    }
    public static void main(String[] args) throws SQLException {
        CoursesManager coursesManager = new CoursesManager();

        //coursesManager.dropAllTables();

        //coursesManager.createCoursesTable();
        //coursesManager.createStudentsTable();
        //coursesManager.createAttendanceListTable();

        coursesManager.printAllCourses();
        coursesManager.printAllStudents();
        coursesManager.printAttendanceList();
    }
}
