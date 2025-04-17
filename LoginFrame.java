import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

    public class LoginFrame extends JFrame {
        JTextField usernameField;
        JPasswordField passwordField;
        JComboBox<String> roleBox;

        public LoginFrame() {
            setTitle("Login");
            setSize(300, 200);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridLayout(4, 2));
            panel.add(new JLabel("Username:"));
            usernameField = new JTextField();
            panel.add(usernameField);

            panel.add(new JLabel("Password:"));
            passwordField = new JPasswordField();
            panel.add(passwordField);

            panel.add(new JLabel("Role:"));
            roleBox = new JComboBox<>(new String[]{"Student", "Admin", "Teacher"});
            panel.add(roleBox);

            JButton loginButton = new JButton("Login");
            loginButton.addActionListener(e -> login());
            panel.add(loginButton);

            JButton registerButton = new JButton("Register");
            registerButton.addActionListener(e -> register());
            panel.add(registerButton);

            add(panel);
            setVisible(true);
        }

        private void login() {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleBox.getSelectedItem();

            if (Database.validateUser(username, password, role)) {
                dispose();
                if (role.equals("Admin")) {
                    new AdminDashboard(username);
                } else if (role.equals("Teacher")) {
                    new TeacherDashboard(username);
                } else {
                    new StudentDashboard(username);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        }

        private void register() {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleBox.getSelectedItem();

            if (Database.registerUser(username, password, role)) {
                JOptionPane.showMessageDialog(this, "Registered successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed.");
            }
        }
    }


