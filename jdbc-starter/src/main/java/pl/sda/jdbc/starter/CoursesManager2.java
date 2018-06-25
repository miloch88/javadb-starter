package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;

public class CoursesManager2 {
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

    private void insertStudent(Connection connection, String name,  int courseId, String description) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO students(name, course_id, description) VALUES(?, ?, ?)")) {
            statement.setString(1, name);
            statement.setInt(2, courseId);
            statement.setString(3, description);
            statement.executeUpdate();
        }
    }

    private void updateStudent(Connection connection, int studentId, String seat) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE students SET seat=? WHERE id=?")) {
            statement.setString(1, seat);
            statement.setInt(2, studentId);
            statement.executeUpdate();
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

    private void insertAttendance(Connection connection, int studentId, int courseId, LocalDate date) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO attendance_list(student_id, course_id, date) VALUES(?, ?, ?)")) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.setDate(3, Date.valueOf(date));
            statement.executeUpdate();
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
        CoursesManager2 manager = new CoursesManager2();
        try (Connection connection = new ConnectionFactory().getConnection()) {
            manager.listCourses(connection);

            //manager.insertStudent(connection, "Jarek", 3, "ĄŹŹźżćżźćęłóó");

            //manager.updateStudent(connection, 1, "1.1.1");

            manager.listStudents(connection);

            //manager.insertAttendance(connection, 1, 3, LocalDate.now());

            manager.listAttendances(connection);
        }
    }}
