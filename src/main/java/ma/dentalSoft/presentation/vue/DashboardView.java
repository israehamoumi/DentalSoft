package ma.dentalSoft.presentation.vue;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JFrame {

    public DashboardView(String username) {
        setTitle("DentalSoft Dashboard");
        setSize(1200, 800); // Adjust size to match the design
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        // Add components to the dashboard
        add(createHeader(username), BorderLayout.NORTH);
        add(createNavigationPanel(), BorderLayout.WEST);
        add(createMainPanel(), BorderLayout.CENTER);
    }

    // Create Header
    private JPanel createHeader(String username) {
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(Color.WHITE);

        // Title
        JLabel lblTitle = new JLabel("DentalSoft", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(102, 0, 153));

        // User Info
        JLabel lblUser = new JLabel("Dr. " + username, SwingConstants.RIGHT);
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
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(8, 1, 5, 5)); // 8 items
        navPanel.setBackground(new Color(102, 0, 153)); // Purple background

        // Navigation buttons
        String[] navItems = {"Mon Profiles", "Agenda", "Patients", "Caisse", "Personnel", "Paramètres", "", "Déconnexion"};
        for (String item : navItems) {
            JButton btn = new JButton(item);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(102, 0, 153));
            btn.setFont(new Font("Arial", Font.PLAIN, 16));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);

            if (item.isEmpty()) {
                navPanel.add(Box.createVerticalStrut(10)); // Add space
            } else if (item.equals("Déconnexion")) {
                btn.setBackground(Color.RED);
                btn.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, "Logging out...");
                    dispose();
                    new LoginView().setVisible(true);
                });
            }

            navPanel.add(btn);
        }

        return navPanel;
    }

    // Create Main Panel
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Add sections
        mainPanel.add(createStatisticsSection(), BorderLayout.NORTH);
        mainPanel.add(createQueueSection(), BorderLayout.CENTER);
        mainPanel.add(createAppointmentsSection(), BorderLayout.SOUTH);

        return mainPanel;
    }

    // Create Statistics Section
    private JPanel createStatisticsSection() {
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2, 3, 10, 10)); // 2 rows, 3 columns
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add statistics circles
        String[] stats = {"Recette du Jour", "Recette du mois", "Recette de l'année",
                "Dépenses du mois", "Nbr de Consultations du Jour", "Nbr de Consultations de l'année"};
        for (String stat : stats) {
            JPanel statPanel = new JPanel();
            statPanel.setLayout(new BorderLayout());
            statPanel.setBackground(Color.WHITE);
            statPanel.setBorder(BorderFactory.createLineBorder(new Color(102, 0, 153), 2));

            JLabel lblTitle = new JLabel(stat, SwingConstants.CENTER);
            lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
            lblTitle.setForeground(new Color(102, 0, 153));

            JLabel lblValue = new JLabel("... DH", SwingConstants.CENTER);
            lblValue.setFont(new Font("Arial", Font.BOLD, 20));
            lblValue.setForeground(Color.BLACK);

            statPanel.add(lblTitle, BorderLayout.NORTH);
            statPanel.add(lblValue, BorderLayout.CENTER);

            statsPanel.add(statPanel);
        }

        return statsPanel;
    }

    // Create Queue Section
    private JPanel createQueueSection() {
        JPanel queuePanel = new JPanel();
        queuePanel.setLayout(new FlowLayout());
        queuePanel.setBackground(Color.WHITE);
        queuePanel.setBorder(BorderFactory.createTitledBorder("File d'attente"));

        // Add waiting patients (example)
        for (int i = 1; i <= 5; i++) {
            JLabel lblPatient = new JLabel("Patient " + i);
            lblPatient.setIcon(new ImageIcon("src/main/resources/patient_icon.png")); // Add icon if available
            lblPatient.setHorizontalTextPosition(SwingConstants.CENTER);
            lblPatient.setVerticalTextPosition(SwingConstants.BOTTOM);
            queuePanel.add(lblPatient);
        }

        return queuePanel;
    }

    // Create Appointments Section
    private JPanel createAppointmentsSection() {
        JPanel appointmentsPanel = new JPanel();
        appointmentsPanel.setLayout(new BorderLayout());
        appointmentsPanel.setBackground(Color.WHITE);
        appointmentsPanel.setBorder(BorderFactory.createTitledBorder("Rendez-Vous du Jour"));

        // Table data
        String[] columnNames = {"Patient", "Heure", "Statut", "Motif"};
        Object[][] data = {
                {"Amine El Alaoui", "10:00", "Approuvé", "Soin de carie"},
                {"Sara El Alami", "10:35", "Approuvé", "Traitement canalaire"},
                {"Tanae El Ghali", "11:25", "Approuvé", "Extraction de dent de sagesse"}
        };

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        appointmentsPanel.add(scrollPane, BorderLayout.CENTER);

        return appointmentsPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DashboardView("John Doe").setVisible(true);
        });
    }
}
