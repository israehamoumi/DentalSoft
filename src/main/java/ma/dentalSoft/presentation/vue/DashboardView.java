package ma.dentalSoft.presentation.vue;

import ma.dentalSoft.model.Utilisateur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardView extends JFrame {
    private final Utilisateur utilisateur;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public DashboardView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        setTitle("DentalSoft Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer la fenêtre
        setLayout(new BorderLayout());

        // Gestionnaire de disposition pour les pages
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Ajouter les différentes pages au tableau de bord
        mainPanel.add(createDashboardPanel(), "Dashboard");
        mainPanel.add(new ProfileView(utilisateur), "Profile"); // Page Profil
        mainPanel.add(new PatientView(this), "Patient"); // Page Patients
        mainPanel.add(new RendezVousView(this), "DossierMedical"); // Page Dossier Médical

        // Ajouter les composants principaux
        add(createHeader(), BorderLayout.NORTH);
        add(createNavigationPanel(), BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        // Afficher le tableau de bord par défaut
        cardLayout.show(mainPanel, "Dashboard");
    }

    // Méthode pour afficher la page Dossier Médical
    public void showDossierMedical() {
        cardLayout.show(mainPanel, "DossierMedical");
    }

    // Créer l'en-tête
    private JPanel createHeader() {
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 102, 204), 0, getHeight(), new Color(51, 153, 255));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setPreferredSize(new Dimension(1200, 80));
        header.setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("DentalSoft", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitle.setForeground(Color.WHITE);

        JLabel lblUser = new JLabel("Dr. " + utilisateur.getName(), SwingConstants.RIGHT);
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblUser.setForeground(Color.WHITE);
        lblUser.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

        header.add(lblTitle, BorderLayout.CENTER);
        header.add(lblUser, BorderLayout.EAST);

        return header;
    }

    // Créer le panneau de navigation
    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel(new GridLayout(8, 1, 10, 10)){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 102, 204), 0, getHeight(), new Color(51, 153, 255));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        navPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] navItems = {"Dashboard", "Mon Profile", "Patients", "Caisse", "Personnel", "Paramètres", "Déconnexion"};
        for (String item : navItems) {
            if (item.isEmpty()) {
                navPanel.add(Box.createVerticalStrut(10)); // Add space
                continue;
            }
            JButton btn = new JButton(item) {

                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    // Set background matching the navigation panel
                    GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 102, 204), 0, getHeight(), new Color(51, 153, 255));
                    g2d.setPaint(gradient);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                    super.paintComponent(g);
                }
                @Override
                protected void paintBorder(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 102, 204), 0, getHeight(), new Color(51, 153, 255));
                    g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                }

                @Override
                public void setContentAreaFilled(boolean b) {
                }
            };
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(0, 51, 153));

            btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            btn.setFocusPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setOpaque(false);

            btn.addActionListener(e -> {
                switch (item) {
                    case "Dashboard" -> cardLayout.show(mainPanel, "Dashboard");
                    case "Mon Profile" -> cardLayout.show(mainPanel, "Profile");
                    case "Patients" -> cardLayout.show(mainPanel, "Patient");
                    case "Déconnexion" -> {
                        JOptionPane.showMessageDialog(this, "Déconnexion...");
                        dispose();
                        new LoginView().setVisible(true);
                    }
                }
            });

            navPanel.add(btn);
        }

        return navPanel;
    }

    // Panneau de tableau de bord principal
    private JPanel createDashboardPanel() {
        JPanel dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(Color.WHITE);

        JPanel statsPanel = new JPanel(new GridLayout(2, 4, 20, 20));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        statsPanel.setBackground(Color.WHITE);

        String[] statsLabels = {
                "Recette du Jour", "Recette du mois", "Recette de l'année", "Dépenses du mois",
                "Nbr de Consultations du Jour", "Nbr de Consultations du mois", "Nbr de Consultations de l'année"
        };

        String[] statsValues = {"2000 DH", "... DH", "... DH", "... DH", "15", "400", "5000"};

        for (int i = 0; i < statsLabels.length; i++) {
            JPanel circlePanel = new JPanel(new BorderLayout());
            circlePanel.setBackground(Color.WHITE);
            circlePanel.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 174), 2));

            JLabel lblValue = new JLabel(statsValues[i], SwingConstants.CENTER);
            lblValue.setFont(new Font("Segoe UI", Font.BOLD, 20));
            lblValue.setForeground(new Color(0, 86, 174));

            JLabel lblStat = new JLabel(statsLabels[i], SwingConstants.CENTER);
            lblStat.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblStat.setForeground(new Color(0, 51, 153));

            circlePanel.add(lblValue, BorderLayout.CENTER);
            circlePanel.add(lblStat, BorderLayout.SOUTH);

            statsPanel.add(circlePanel);
        }

        JPanel lowerPanel = new JPanel(new BorderLayout(20, 20));
        lowerPanel.setBackground(Color.WHITE);
        lowerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] columns = {"Date", "Heure", "Statut", "Motif"};
        Object[][] data = loadRendezVousData();

        JTable rdvTable = new JTable(new DefaultTableModel(data, columns));
        rdvTable.setRowHeight(30);
        rdvTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        rdvTable.getTableHeader().setBackground(new Color(0, 86, 174));
        rdvTable.getTableHeader().setForeground(Color.WHITE);
        rdvTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane tableScrollPane = new JScrollPane(rdvTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Rendez-Vous du Jour"));

        lowerPanel.add(tableScrollPane, BorderLayout.CENTER);

        dashboardPanel.add(statsPanel, BorderLayout.NORTH);
        dashboardPanel.add(lowerPanel, BorderLayout.CENTER);

        return dashboardPanel;
    }

    private Object[][] loadRendezVousData() {
        List<Object[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RendezVousView.FILE_PATH))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1) continue;

                String[] fields = line.split(",");
                if (fields.length >= 4) {
                    data.add(new Object[]{fields[1], fields[2], fields[3], fields[4]});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des rendez-vous : " + e.getMessage());
        }
        return data.toArray(new Object[0][]);
    }
}
