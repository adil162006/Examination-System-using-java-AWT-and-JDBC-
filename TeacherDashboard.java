import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TeacherDashboard extends JFrame {
    public TeacherDashboard(String teacherName) {
        setTitle("Teacher Dashboard - " + teacherName);
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Welcome, Teacher: " + teacherName);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton viewStudentsButton = new JButton("View Student Info");
        viewStudentsButton.addActionListener(e -> {
            ArrayList<String> students = Database.getAllStudents();
            JOptionPane.showMessageDialog(this, String.join("\n", students), "Students", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton addMarksButton = new JButton("Add Marks");
        addMarksButton.addActionListener(e -> {
            String studentUsername = JOptionPane.showInputDialog(this, "Enter Student Username:");
            String marksStr = JOptionPane.showInputDialog(this, "Enter Marks:");
            try {
                int marks = Integer.parseInt(marksStr);
                if (Database.addStudentMarks(studentUsername, marks)) {
                    JOptionPane.showMessageDialog(this, "Marks added successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add marks.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid marks input.");
            }
        });

        JButton showMarksButton = new JButton("Show All Marks");
        showMarksButton.addActionListener(e -> {
            ArrayList<String> marks = Database.getStudentMarks();
            JOptionPane.showMessageDialog(this, String.join("\n", marks), "Student Marks", JOptionPane.INFORMATION_MESSAGE);
        });

        panel.add(viewStudentsButton);
        panel.add(addMarksButton);
        panel.add(showMarksButton);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
}
