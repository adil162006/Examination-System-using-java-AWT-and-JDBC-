import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentDashboard extends JFrame {
    private String studentName;

    public StudentDashboard(String studentName) {
        this.studentName = studentName;
        setTitle("Student Dashboard - " + studentName);
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Welcome, Student: " + studentName);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        JButton viewMarksButton = new JButton("View My Marks");
        viewMarksButton.addActionListener(e -> viewMyMarks());

        JPanel panel = new JPanel();
        panel.add(viewMarksButton);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void viewMyMarks() {
        Integer marks = Database.getMarksForStudent(studentName);
        if (marks != null) {
            JOptionPane.showMessageDialog(this, "Your Marks: " + marks);
        } else {
            JOptionPane.showMessageDialog(this, "No marks found.");
        }
    }
}
