package ma.dentalSoft.presentation.vue;

import ma.dentalSoft.service.UtilisateurService;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private final UtilisateurService utilisateurService = new UtilisateurService();

    public LoginView() {
        setTitle("Login");
        setSize(400, 250); // Set a smaller, more compact size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen
        setLayout(new BorderLayout()); // Use BorderLayout for flexibility

        // Create a panel for input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout()); // Center components within the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Components
        JLabel lblUsername = new JLabel("Username:");
        JTextField txtUsername = new JTextField(20); // Set width of the text field
        JLabel lblPassword = new JLabel("Password:");
        JPasswordField txtPassword = new JPasswordField(20);
        JButton btnLogin = new JButton("Login");

        // Add components to input panel
        inputPanel.add(lblUsername, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(lblPassword, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Center-align the button
        inputPanel.add(btnLogin, gbc);

        // Add input panel to the frame
        add(inputPanel, BorderLayout.CENTER);

        // Event handling
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            if (utilisateurService.validateLogin(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                this.dispose(); // Close the login window
                new DashboardView(username).setVisible(true); // Open the dashboard
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }
}
