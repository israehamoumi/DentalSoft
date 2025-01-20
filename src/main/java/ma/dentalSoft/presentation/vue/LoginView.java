package ma.dentalSoft.presentation.vue;

import ma.dentalSoft.model.Utilisateur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginView extends JFrame {

    private final Map<Integer, String> roles = new HashMap<>(); // Pour stocker les rôles depuis role.txt

    public LoginView() {
        setTitle("DentalSoft - Login");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Charger les rôles depuis role.txt
        loadRoles();

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

        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField txtName = new JTextField(20);
        txtName.setBorder(BorderFactory.createLineBorder(new Color(192, 192, 192), 1));
        txtName.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JPasswordField txtPassword = new JPasswordField(20);
        txtPassword.setBorder(BorderFactory.createLineBorder(new Color(192, 192, 192), 1));
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel lblRole = new JLabel("Role:");
        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Bouton stylisé pour sélectionner un rôle
        JComboBox<String> cbRoles = new JComboBox<>(roles.values().toArray(new String[0])) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Dessin du fond en dégradé
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 153, 204), getWidth(), getHeight(), new Color(0, 102, 204));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Dessin de la bordure
                g2d.setColor(new Color(0, 76, 153));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                // Dessin du texte
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
                String selectedItem = getSelectedItem().toString();
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(selectedItem)) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(selectedItem, x, y);
            }

            @Override
            protected void paintBorder(Graphics g) {
                // La bordure est déjà gérée dans paintComponent
            }
        };
        cbRoles.setOpaque(false);
        cbRoles.setPreferredSize(new Dimension(200, 40));
        cbRoles.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));



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
        btnLogin.setOpaque(false);
        // Layout components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(lblLoginTitle, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        rightPanel.add(lblName, gbc);

        gbc.gridx = 1;
        rightPanel.add(txtName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        rightPanel.add(lblPassword, gbc);

        gbc.gridx = 1;
        rightPanel.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        rightPanel.add(lblRole, gbc);

        gbc.gridx = 1;
        rightPanel.add(cbRoles, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(btnLogin, gbc);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // ActionListener for the login button
        btnLogin.addActionListener(e -> {
            String name = txtName.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            String selectedRole = (String) cbRoles.getSelectedItem();

            Utilisateur utilisateur = validateLogin(name, password, selectedRole);
            if (utilisateur != null) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                this.dispose();
                new DashboardView(utilisateur).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid name, password, or role", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void loadRoles() {
        String roleFilePath = "src/main/resources/role.txt"; // Assurez-vous que ce fichier est dans le bon répertoire
        try (BufferedReader br = new BufferedReader(new FileReader(roleFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) continue; // Skip the header
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int roleId = Integer.parseInt(parts[0].trim());
                    String roleName = parts[1].trim();
                    roles.put(roleId, roleName); // Ajoute le rôle au HashMap
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading roles", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Utilisateur validateLogin(String name, String password, String role) {
        String filePath = "src/main/resources/utilisateur.txt"; // Assurez-vous que ce fichier est dans le bon répertoire
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) continue; // Skip the header
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    int id = Integer.parseInt(parts[0].trim());
                    String fileName = parts[1].trim();
                    String fileRole = parts[2].trim();
                    String fileEmail = parts[3].trim();
                    String fileTelephone = parts[4].trim();
                    String filePassword = parts[5].trim();

                    if (name.equals(fileName) && password.equals(filePassword) && role.equals(fileRole)) {
                        return new Utilisateur(id, fileName, fileRole, fileEmail, fileTelephone, filePassword);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading user file", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }
}
