package ma.dentalSoft.presentation.vue;

import ma.dentalSoft.model.Utilisateur;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurSwingApp extends JPanel {
    private static final String FICHIER_UTILISATEURS = "src/main/resources/utilisateur.txt";
    private final List<Utilisateur> utilisateurs = new ArrayList<>();
    private DefaultTableModel tableModel;

    public UtilisateurSwingApp(DashboardView dashboardView) {
        chargerUtilisateurs();
        creerInterface();
    }

    // Charger les utilisateurs depuis le fichier
    private void chargerUtilisateurs() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_UTILISATEURS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.isEmpty()) continue;
                String[] parts = line.split(",");
                Utilisateur utilisateur = new Utilisateur(
                        Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4], parts[5]
                );
                utilisateurs.add(utilisateur);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des utilisateurs : " + e.getMessage());
        }
    }

    // Sauvegarder les utilisateurs dans le fichier
    private void sauvegarderUtilisateurs() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_UTILISATEURS))) {
            writer.write("# Format: id,name,role,email,telephone,motDePasse\n");
            for (Utilisateur utilisateur : utilisateurs) {
                writer.write(utilisateur.getId() + "," +
                        utilisateur.getName() + "," +
                        utilisateur.getRole() + "," +
                        utilisateur.getEmail() + "," +
                        utilisateur.getTelephone() + "," +
                        utilisateur.getPassword() + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    // Créer l'interface graphique
    private void creerInterface() {
        setLayout(new BorderLayout());

        // Table des utilisateurs
        String[] columnNames = {"ID", "Nom", "Rôle", "Email", "Téléphone", "Mot de passe"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        // Appliquer le style au tableau
        styleTable(table);

        remplirTable();

        // Boutons
        JButton btnAjouter = createStyledButton("Ajouter");
        JButton btnModifier = createStyledButton("Modifier");
        JButton btnSupprimer = createStyledButton("Supprimer");

        // Panel des boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnSupprimer);

        // Ajouter la table et les boutons au panneau principal
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action pour ajouter un utilisateur
        btnAjouter.addActionListener(e -> {
            JTextField txtId = new JTextField();
            JTextField txtName = new JTextField();
            JTextField txtRole = new JTextField();
            JTextField txtEmail = new JTextField();
            JTextField txtTelephone = new JTextField();
            JTextField txtMotDePasse = new JTextField();

            Object[] message = {
                    "ID:", txtId,
                    "Nom:", txtName,
                    "Rôle:", txtRole,
                    "Email:", txtEmail,
                    "Téléphone:", txtTelephone,
                    "Mot de passe:", txtMotDePasse
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Ajouter un utilisateur", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    Utilisateur utilisateur = new Utilisateur(
                            Integer.parseInt(txtId.getText()),
                            txtName.getText(),
                            txtRole.getText(),
                            txtEmail.getText(),
                            txtTelephone.getText(),
                            txtMotDePasse.getText()
                    );
                    utilisateurs.add(utilisateur);
                    remplirTable();
                    sauvegarderUtilisateurs();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
                }
            }
        });

        // Action pour modifier un utilisateur
        btnModifier.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                // Récupérer l'utilisateur correspondant dans la liste
                Utilisateur utilisateur = utilisateurs.get(selectedRow);

                // Créer des champs pour afficher les données existantes
                JTextField txtId = new JTextField(String.valueOf(utilisateur.getId()));
                JTextField txtName = new JTextField(utilisateur.getName());
                JTextField txtRole = new JTextField(utilisateur.getRole());
                JTextField txtEmail = new JTextField(utilisateur.getEmail());
                JTextField txtTelephone = new JTextField(utilisateur.getTelephone());
                JTextField txtMotDePasse = new JTextField(utilisateur.getPassword());

                Object[] message = {
                        "ID:", txtId,
                        "Nom:", txtName,
                        "Rôle:", txtRole,
                        "Email:", txtEmail,
                        "Téléphone:", txtTelephone,
                        "Mot de passe:", txtMotDePasse
                };

                // Afficher la boîte de dialogue de modification
                int option = JOptionPane.showConfirmDialog(this, message, "Modifier un utilisateur", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        // Mettre à jour l'utilisateur dans la liste
                        utilisateur.setId(Integer.parseInt(txtId.getText()));
                        utilisateur.setName(txtName.getText());
                        utilisateur.setRole(txtRole.getText());
                        utilisateur.setEmail(txtEmail.getText());
                        utilisateur.setTelephone(txtTelephone.getText());
                        utilisateur.setMotDePasse(txtMotDePasse.getText());

                        // Mettre à jour la table
                        tableModel.setValueAt(utilisateur.getId(), selectedRow, 0);
                        tableModel.setValueAt(utilisateur.getName(), selectedRow, 1);
                        tableModel.setValueAt(utilisateur.getRole(), selectedRow, 2);
                        tableModel.setValueAt(utilisateur.getEmail(), selectedRow, 3);
                        tableModel.setValueAt(utilisateur.getTelephone(), selectedRow, 4);
                        tableModel.setValueAt(utilisateur.getPassword(), selectedRow, 5);

                        // Sauvegarder les modifications dans le fichier
                        sauvegarderUtilisateurs();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur à modifier.");
            }
        });


        // Action pour supprimer un utilisateur
        btnSupprimer.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                utilisateurs.remove(selectedRow);
                remplirTable();
                sauvegarderUtilisateurs();
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur à supprimer.");
            }
        });
    }

    // Appliquer le style au tableau
    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);

        // Couleurs alternées pour les lignes
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(240, 240, 240) : Color.WHITE);
                } else {
                    c.setBackground(new Color(184, 207, 229));
                }
                return c;
            }
        });

        // Style de l'en-tête
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(0, 102, 204));
        header.setForeground(Color.WHITE);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Remplir la table avec les données des utilisateurs
    private void remplirTable() {
        tableModel.setRowCount(0);
        for (Utilisateur utilisateur : utilisateurs) {
            tableModel.addRow(new Object[]{
                    utilisateur.getId(),
                    utilisateur.getName(),
                    utilisateur.getRole(),
                    utilisateur.getEmail(),
                    utilisateur.getTelephone(),
                    utilisateur.getPassword()
            });
        }
    }
}
