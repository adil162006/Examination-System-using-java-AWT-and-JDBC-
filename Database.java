import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL JDBC Driver
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/exam_system", "Username", "YOUR_PASSWORD");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean validateUser(String username, String password, String role) {
        String query = "SELECT * FROM users WHERE username=? AND password=? AND role=?";
        try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean registerUser(String username, String password, String role) {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            ps.executeUpdate();

            if (role.equalsIgnoreCase("Student")) {
                try (PreparedStatement studentInsert = conn.prepareStatement("INSERT INTO students (username) VALUES (?)")) {
                    studentInsert.setString(1, username);
                    studentInsert.executeUpdate();
                }
            } else if (role.equalsIgnoreCase("Teacher")) {
                try (PreparedStatement teacherInsert = conn.prepareStatement("INSERT INTO teachers (username) VALUES (?)")) {
                    teacherInsert.setString(1, username);
                    teacherInsert.executeUpdate();
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<String> getAllStudents() {
        ArrayList<String> students = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT username FROM students");
            while (rs.next()) {
                students.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static boolean addStudentMarks(String studentUsername, int marks) {
        String query = "INSERT INTO marks (student_username, marks) VALUES (?, ?)";
        try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, studentUsername);
            ps.setInt(2, marks);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<String> getStudentMarks() {
        ArrayList<String> result = new ArrayList<>();
        String query = "SELECT student_username, marks FROM marks";
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                result.add(rs.getString("student_username") + " - Marks: " + rs.getInt("marks"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static Integer getMarksForStudent(String studentUsername) {
        String query = "SELECT marks FROM marks WHERE student_username = ?";
        try (Connection conn = connect(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, studentUsername);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("marks");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
