package ma.dentalSoft.presentation.vue;

import ma.dentalSoft.model.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfileView extends JFrame {

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Utilisateur utilisateur; // Utilisateur connecté

    public ProfileView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur; // Associer l'utilisateur connecté
        setTitle("DentalSoft Dashboard");
        setSize(1200, 800); // Taille ajustée
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer la fenêtre

        // Disposition principale
        setLayout(new BorderLayout());

        // Panneau latéral
        JPanel sidebarPanel = createSidebarPanel();
        add(sidebarPanel, BorderLayout.WEST);

        // Zone principale avec CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(createProfilePanel(), "Mon Profile");
        mainPanel.add(createPlaceholderPanel("Agenda"), "Agenda");
        mainPanel.add(createPlaceholderPanel("Patients"), "Patients");
        mainPanel.add(createPlaceholderPanel("Caisse"), "Caisse");
        mainPanel.add(createPlaceholderPanel("Personnel"), "Personnel");
        mainPanel.add(createPlaceholderPanel("Paramètres"), "Paramètres");
        add(mainPanel, BorderLayout.CENTER);

        // Afficher le panneau par défaut
        cardLayout.show(mainPanel, "Mon Profile");
    }

    // ====================
    // Méthodes principales
    // ====================

    private JPanel createProfilePanel() {
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BorderLayout());
        profilePanel.setBackground(Color.WHITE);

        // En-tête
        JLabel lblHeader = new JLabel("Dr. " + utilisateur.getName() + " - " + utilisateur.getRole(), SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 24));
        lblHeader.setForeground(new Color(102, 0, 153));
        profilePanel.add(lblHeader, BorderLayout.NORTH);

        // Détails du profil
        JPanel detailsPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        detailsPanel.add(createLabel("Nom :"));
        detailsPanel.add(new JLabel(utilisateur.getName()));

        detailsPanel.add(createLabel("Adresse e-mail :"));
        detailsPanel.add(new JLabel(utilisateur.getEmail()));

        detailsPanel.add(createLabel("Téléphone :"));
        detailsPanel.add(new JLabel(utilisateur.getTelephone()));

        detailsPanel.add(createLabel("Rôle :"));
        detailsPanel.add(new JLabel(utilisateur.getRole()));

        detailsPanel.add(createLabel("Mot de passe (crypté) :"));
        detailsPanel.add(new JLabel("********"));

        profilePanel.add(detailsPanel, BorderLayout.CENTER);

        return profilePanel;
    }

    private JPanel createSidebarPanel() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(8, 1, 10, 10));
        sidebar.setBackground(new Color(102, 0, 153));

        String[] buttons = {"Mon Profile", "Agenda", "Patients", "Caisse", "Personnel", "Paramètres", "", "Déconnexion"};
        for (String button : buttons) {
            if (button.isEmpty()) {
                sidebar.add(Box.createVerticalStrut(10));
            } else {
                JButton btn = new JButton(button);
                btn.setForeground(Color.WHITE);
                btn.setBackground(new Color(102, 0, 153));
                btn.setFont(new Font("Arial", Font.BOLD, 16));
                btn.setFocusPainted(false);
                btn.setBorderPainted(false);

                if (button.equals("Déconnexion")) {
                    btn.setBackground(Color.RED);
                    btn.addActionListener(e -> {
                        JOptionPane.showMessageDialog(this, "Déconnexion...");
                        dispose();
                    });
                } else {
                    btn.addActionListener(e -> cardLayout.show(mainPanel, button));
                }

                sidebar.add(btn);
            }
        }

        return sidebar;
    }

    private JPanel createPlaceholderPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(title + " Panel", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(new Color(102, 0, 153));
        panel.add(label);
        return panel;
    }

    // ====================
    // Méthodes utilitaires
    // ====================

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        return label;
    }

    public static List<Utilisateur> readUsersFromFile(String filePath) {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("#") && !line.isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length == 6) {
                        Utilisateur utilisateur = new Utilisateur(
                                Integer.parseInt(parts[0]),
                                parts[1],
                                parts[2],
                                parts[3],
                                parts[4],
                                parts[5]
                        );
                        utilisateurs.add(utilisateur);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    public static void main(String[] args) {
        // Lire les utilisateurs à partir du fichier
        List<Utilisateur> utilisateurs = readUsersFromFile("Utilisateur.txt");

        // Exemple : sélectionner le premier utilisateur
        if (!utilisateurs.isEmpty()) {
            Utilisateur utilisateur = utilisateurs.get(0); // Exemple avec le premier utilisateur
            SwingUtilities.invokeLater(() -> {
                new ProfileView(utilisateur).setVisible(true);
            });
        } else {
            System.out.println("Aucun utilisateur trouvé dans le fichier !");
        }
    }
}
