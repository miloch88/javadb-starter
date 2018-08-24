package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

            //statement.executeUpdate("INSERT INTO courses(name, place, start_date, end_date) VALUES('JavaGda17', 'Sopot', '2018-06-01', '2018-12-12')");
            //statement.executeUpdate("INSERT INTO courses(name, place, start_date, end_date) VALUES('JavaGda14', 'Gdansk', '2018-03-01', '2018-10-18')");

            addCourse("JavaGda17", "Sopot", LocalDate.of(2018, 6, 1), LocalDate.of(2018, 12, 12));
            addCourse("JavaGda14", "Gdansk", LocalDate.of(2018, 3, 1), LocalDate.of(2018, 10, 18));

            logger.info("Table: sda_courses.courses filled with data!");
        }
    }

    public void addCourse(String name, String place, LocalDate startDate, LocalDate endDate) throws SQLException{
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO courses(name, place, start_date, end_date) VALUES(?, ?, ?, ?)")) {

            statement.setString(1, name);
            statement.setString(2, place);
            statement.setDate(3, Date.valueOf(startDate));
            statement.setDate(4, Date.valueOf(endDate));

            statement.executeUpdate();
        }
    }

    public void updateCourse(int id, String name) throws SQLException{
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE courses SET name=? WHERE id=?")) {

            statement.setString(1, name);
            statement.setInt(2, id);

            statement.executeUpdate();
        }
    }

    public void deleteCourse(int id) throws SQLException{
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM courses WHERE id=?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
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

            //statement.executeUpdate("INSERT INTO students(name, course_id, description, seat) VALUES('Jarek', 1, 'Lubię kodować!', 'A.1.1')");
            //statement.executeUpdate("INSERT INTO students(name) VALUES('Patrycja')");

            addStudent("Jarek", 1, "Lubię kodować!", "A.1.1");
            addStudent("Patrycja", null, null, null);
            addStudent("Ewa", 1, null, null);
            addStudent("Marcin", 2, null, null);

            logger.info("Table: sda_courses.students filled with data!");
        }
    }

    public void addStudent(String name, Integer courseId, String description, String seat) throws SQLException{
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO students(name, course_id, description, seat) VALUES(?, ?, ?, ?)")) {

            statement.setString(1, name);
            statement.setObject(2, courseId);
            statement.setString(3, description);
            statement.setString(4, seat);

            statement.executeUpdate();
        }
    }

    public void updateStudent(int id, String description, String seat) throws SQLException{
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE students SET description=? , seat=? WHERE id=?")) {

            statement.setString(1, description);
            statement.setObject(2, seat);
            statement.setInt(3, id);

            statement.executeUpdate();
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

            //statement.executeUpdate("INSERT INTO attendance_list(student_id, course_id, date) VALUES(1, 1, '2018-06-01 10:00:15')");
            addAttendance(1, 1, LocalDateTime.of(2018, 6, 1, 10, 0, 15));
            addAttendance(1, 1, LocalDateTime.of(2010, 1, 1, 11, 12, 13));

            logger.info("Table: sda_courses.attendance_list filled with data!");
        }
    }

    public void addAttendance(int studentId, int courseId, LocalDateTime dateTime) throws SQLException{
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO attendance_list(student_id, course_id, date) VALUES(?, ?, ?)")) {

            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.setTimestamp(3, Timestamp.valueOf(dateTime));

            statement.executeUpdate();
        }
    }

    public void deleteAttendance(int studentId, LocalDateTime dateTime) throws SQLException{
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM attendance_list WHERE student_id=? AND date=?")) {

            statement.setInt(1, studentId);
            statement.setTimestamp(2, Timestamp.valueOf(dateTime));

            statement.executeUpdate();
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

    public void printAllCourses() throws SQLException {
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

    public void printCoursesInCity(String city) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, name, place, start_date, end_date FROM courses WHERE place=?")) {

            statement.setString(1, city);

            ResultSet resultSet = statement.executeQuery();
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

    public void printAllStudents(int courseId) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                         "SELECT s.id, s.name, c.name AS course_name, s.description, s.seat" +
                         " FROM students AS s" +
                         " LEFT JOIN courses AS c ON s.course_id = c.id" +
                         " WHERE c.id=?")) {
            statement.setInt(1,courseId );

            ResultSet resultSet = statement.executeQuery();
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

    public void printAttendanceList(int courseId, LocalDate localDate) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT a.id, s.name AS student_name, c.name AS course_name, a.date" +
                     " FROM attendance_list AS a" +
                     " JOIN courses AS c ON a.course_id = c.id" +
                     " JOIN students AS s ON a.student_id = s.id" +
                     " WHERE c.id=? AND DATE(a.date)=?")) {
            statement.setInt(1, courseId);
            statement.setDate(2, Date.valueOf(localDate));

            ResultSet resultSet = statement.executeQuery();
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

        coursesManager.dropAllTables();

        coursesManager.createCoursesTable();
        coursesManager.createStudentsTable();
        coursesManager.createAttendanceListTable();

        //coursesManager.updateStudent(1, "Test", "X.Y.Z");
        //coursesManager.deleteCourse(2);
        //coursesManager.updateCourse(1, "JavaPro14");
        //coursesManager.deleteAttendance(1, LocalDateTime.of(2010, 1, 1, 11, 12, 13));

        logger.info("Courses:");
        //coursesManager.printAllCourses();
        coursesManager.printCoursesInCity("Sopot");
        logger.info("Students:");
        coursesManager.printAllStudents(2);
        logger.info("Attendance list:");
        coursesManager.printAttendanceList(1, LocalDate.of(2018, 6, 1));
    }
}
