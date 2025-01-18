package ma.dentalSoft.presentation.vue;

import ma.dentalSoft.model.Utilisateur;
import ma.dentalSoft.service.UtilisateurService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginView extends JFrame {
    private final UtilisateurService utilisateurService = new UtilisateurService();

    public LoginView() {
        setTitle("DentalSoft - Login");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Left panel with gradient background and welcome message
        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 102, 204), 0, getHeight(), new Color(51, 153, 255));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        leftPanel.setLayout(new GridBagLayout());

        JLabel lblWelcome = new JLabel("WELCOME BACK");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblWelcome.setForeground(Color.WHITE);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(lblWelcome);
        textPanel.add(Box.createVerticalStrut(10));

        leftPanel.add(textPanel);

        // Right panel for login form
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblLoginTitle = new JLabel("Login Account");
        lblLoginTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblLoginTitle.setForeground(new Color(0, 102, 204));

        JLabel lblEmail = new JLabel("User:");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField txtEmail = new JTextField(20);
        txtEmail.setBorder(BorderFactory.createLineBorder(new Color(192, 192, 192), 1));
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JPasswordField txtPassword = new JPasswordField(20);
        txtPassword.setBorder(BorderFactory.createLineBorder(new Color(192, 192, 192), 1));
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JCheckBox chkKeepSignedIn = new JCheckBox("Keep me signed in");
        chkKeepSignedIn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkKeepSignedIn.setBackground(Color.WHITE);

        JButton btnLogin = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Dessin du fond en bleu foncé
                g2d.setColor(new Color(0, 51, 153)); // Bleu foncé
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g); // Dessine le texte par-dessus
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 76, 153)); // Couleur de la bordure
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            }

            @Override
            public void setContentAreaFilled(boolean b) {
                // Empêche Swing de remplir la zone de contenu par défaut
            }
        };
        btnLogin.setForeground(Color.WHITE); // Définit la couleur du texte en blanc
        btnLogin.setBackground(new Color(0, 76, 153));
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setPreferredSize(new Dimension(150, 40));
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.setOpaque(false); // Rend le bouton transparent pour éviter de cacher le fond personnalisé



        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(lblLoginTitle, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        rightPanel.add(lblEmail, gbc);

        gbc.gridx = 1;
        rightPanel.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        rightPanel.add(lblPassword, gbc);

        gbc.gridx = 1;
        rightPanel.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        rightPanel.add(chkKeepSignedIn, gbc);

        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(btnLogin, gbc);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        btnLogin.addActionListener(e -> {
            String email = txtEmail.getText();
            String password = new String(txtPassword.getPassword());

            Utilisateur utilisateur = utilisateurService.validateLogin(email, password);
            if (utilisateur != null) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                this.dispose();
                new DashboardView(utilisateur).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password", "Error", JOptionPane.ERROR_MESSAGE);
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
