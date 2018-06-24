package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class CoursesManager {
    private Logger logger = LoggerFactory.getLogger(CoursesManager.class);

    private void listCourses(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT id, name, place FROM courses");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String place = resultSet.getString("place");
                logger.info("{}, {}, {}", id, name, place);
            }
        }
    }

    private void insertStudent(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO students(name, course_id, description) VALUES('Jarek', 3, 'Lubię kodować!')");
        }
    }

    private void updateStudent(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("UPDATE students SET seat='1.1.1' WHERE id=1");
        }
    }

    private void listStudents(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT s.id, s.name, c.name AS course_name, s.description, s.seat" +
                                                         " FROM students AS s" +
                                                         " JOIN courses AS c ON s.course_id = c.id");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String courseName = resultSet.getString("course_name");
                String description = resultSet.getString("description");
                String seat = resultSet.getString("seat");
                logger.info("{}, {}, {}, {}, {}", id, name, courseName, description, seat);
            }
        }
    }

    private void insertAttendance(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO attendance_list(student_id, course_id, date) VALUES(1, 3, '2018-06-25')");
        }
    }

    private void listAttendances(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT a.id, s.name AS student_name, c.name AS course_name, a.date" +
                                                         " FROM attendance_list AS a" +
                                                         " JOIN courses AS c ON a.course_id = c.id" +
                                                         " JOIN students AS s ON a.student_id = s.id");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String studentName = resultSet.getString("student_name");
                String courseName = resultSet.getString("course_name");
                Date date = resultSet.getDate("date");
                logger.info("{}, {}, {}, {}", id, studentName, courseName, date);
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        CoursesManager manager = new CoursesManager();
        try (Connection connection = new ConnectionFactory("/remote-database.properties").getConnection()) {
            manager.listCourses(connection);

            //manager.insertStudent(connection);

            //manager.updateStudent(connection);

            manager.listStudents(connection);

            //manager.insertAttendance(connection);

            manager.listAttendances(connection);
        }
    }
}
