package ma.dentalSoft.presentation.vue;

import ma.dentalSoft.model.Utilisateur;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JFrame {
    private final Utilisateur utilisateur;

    public DashboardView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        setTitle("DentalSoft Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        // Add components to the dashboard
        add(createHeader(), BorderLayout.NORTH);
        add(createNavigationPanel(), BorderLayout.WEST);
        add(createMainPanel(), BorderLayout.CENTER);
    }

    // Create Header
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);

        // Title
        JLabel lblTitle = new JLabel("DentalSoft", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(102, 0, 153));

        // User Info
        JLabel lblUser = new JLabel("Dr. " + utilisateur.getName(), SwingConstants.RIGHT);
        lblUser.setFont(new Font("Arial", Font.BOLD, 18));
        lblUser.setForeground(Color.BLACK);
        lblUser.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

        // Add to header
        header.add(lblTitle, BorderLayout.CENTER);
        header.add(lblUser, BorderLayout.EAST);

        return header;
    }

    // Create Navigation Panel
    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel(new GridLayout(8, 1, 5, 5));
        navPanel.setBackground(new Color(102, 0, 153)); // Purple background

        // Navigation buttons
        String[] navItems = {"Mon Profile", "Agenda", "Patients", "Caisse", "Personnel", "Paramètres", "", "Déconnexion"};
        for (String item : navItems) {
            JButton btn = new JButton(item);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(102, 0, 153)); // Purple buttons
            btn.setFont(new Font("Arial", Font.PLAIN, 16));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);

            if (item.isEmpty()) {
                navPanel.add(Box.createVerticalStrut(10)); // Add space
            } else if (item.equals("Déconnexion")) {
                btn.setBackground(Color.RED);
                btn.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, "Déconnexion...");
                    dispose();
                    new LoginView().setVisible(true);
                });
            } else if (item.equals("Mon Profile")) {
                btn.addActionListener(e -> {
                    new ProfileView(utilisateur).setVisible(true);
                    dispose();
                });
            } else if (item.equals("Patients")) { // Action pour le bouton "Patients"
                btn.addActionListener(e -> {
                    JFrame patientFrame = new JFrame("Patients");
                    patientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    patientFrame.setSize(800, 600);

                    PatientView patientView = new PatientView(patientFrame); // Vue des patients
                    patientFrame.add(patientView);

                    patientFrame.setVisible(true);
                    dispose();
                });
            }

            navPanel.add(btn);
        }

        return navPanel;
    }

    // Create Main Panel
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Placeholder for main content
        JLabel lblPlaceholder = new JLabel("Bienvenue sur le tableau de bord DentalSoft!", SwingConstants.CENTER);
        lblPlaceholder.setFont(new Font("Arial", Font.BOLD, 18));
        lblPlaceholder.setForeground(new Color(102, 0, 153));
        mainPanel.add(lblPlaceholder, BorderLayout.CENTER);

        return mainPanel;
    }
}
