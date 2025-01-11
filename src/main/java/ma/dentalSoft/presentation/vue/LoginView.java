package ma.dentalSoft.presentation.vue;

import ma.dentalSoft.model.Utilisateur;
import ma.dentalSoft.service.UtilisateurService;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private final UtilisateurService utilisateurService = new UtilisateurService();

    public LoginView() {
        setTitle("Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUsername = new JLabel("Username:");
        JTextField txtUsername = new JTextField(20);
        JLabel lblPassword = new JLabel("Password:");
        JPasswordField txtPassword = new JPasswordField(20);
        JButton btnLogin = new JButton("Login");

        gbc.gridx = 0;
        gbc.gridy = 0;
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
        inputPanel.add(btnLogin, gbc);

        add(inputPanel, BorderLayout.CENTER);

        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            Utilisateur utilisateur = utilisateurService.validateLogin(username, password);
            if (utilisateur != null) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                this.dispose();
                new DashboardView(utilisateur).setVisible(true);
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
