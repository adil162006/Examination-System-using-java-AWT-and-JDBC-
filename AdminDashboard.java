import javax.swing.*;

public class AdminDashboard extends JFrame {
    public AdminDashboard(String adminName) {
        setTitle("Admin Dashboard - " + adminName);
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Welcome, Admin: " + adminName);
        add(label);

        setVisible(true);
    }
}
