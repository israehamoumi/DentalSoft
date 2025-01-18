package ma.dentalSoft.presentation.vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RendezVousView extends JPanel {
    public static final String FILE_PATH = "src/main/resources/rendezVous.txt";
    private DefaultTableModel tableModel;
    private JTable table;

    public RendezVousView(JFrame parentFrame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Header
        add(createHeader(), BorderLayout.NORTH);

        // Main Content
        add(createMainContent(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Gestion de Dossier Medical", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 76, 153));

        header.add(lblTitle, BorderLayout.CENTER);
        return header;
    }

    private JPanel createMainContent() {
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(Color.WHITE);
        mainContent.add(createTabbedPane(), BorderLayout.CENTER);
        return mainContent;
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Consultations", new ConsultationView());
        tabbedPane.addTab("Rendez-vous", createRendezVousTab());
        tabbedPane.addTab("Ordonnances", new OrdonnanceView());
        return tabbedPane;
    }


    private JPanel createRendezVousTab() {
        JPanel rendezVousTab = new JPanel(new BorderLayout());
        rendezVousTab.setBackground(Color.WHITE);

        // Table
        String[] columnNames = {"Date", "Heure", "Statut", "Motif"};
        Object[][] data = loadRendezVousData();

        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(0, 76, 153));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        rendezVousTab.add(scrollPane, BorderLayout.CENTER);

        // Form Panel
        rendezVousTab.add(createRendezVousForm(), BorderLayout.SOUTH);

        return rendezVousTab;
    }

    private JPanel createRendezVousForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(255, 255, 255));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtDate = new JTextField(15);
        JTextField txtHeure = new JTextField(15);
        JComboBox<String> comboStatut = new JComboBox<>(new String[]{"En attente", "Approuvé", "Annulé"});
        JTextField txtMotif = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Date:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtDate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Heure:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtHeure, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Statut:"), gbc);
        gbc.gridx = 1;
        formPanel.add(comboStatut, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Motif:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtMotif, gbc);

        // Buttons
        JButton btnSave = new JButton("Valider");
        JButton btnClear = new JButton("Effacer");
        JButton btnModify = new JButton("Modifier");
        JButton btnDelete = new JButton("Supprimer");

        btnSave.addActionListener(e -> {
            String date = txtDate.getText();
            String heure = txtHeure.getText();
            String statut = comboStatut.getSelectedItem().toString();
            String motif = txtMotif.getText();

            // Add data to the table
            tableModel.addRow(new Object[]{date, heure, statut, motif});

            // Save data to file
            saveRendezVousData(date, heure, statut, motif);

            // Clear the form fields
            txtDate.setText("");
            txtHeure.setText("");
            txtMotif.setText("");
            comboStatut.setSelectedIndex(0);
        });

        btnClear.addActionListener(e -> {
            txtDate.setText("");
            txtHeure.setText("");
            txtMotif.setText("");
            comboStatut.setSelectedIndex(0);
        });

        btnModify.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                txtDate.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtHeure.setText(tableModel.getValueAt(selectedRow, 1).toString());
                comboStatut.setSelectedItem(tableModel.getValueAt(selectedRow, 2).toString());
                txtMotif.setText(tableModel.getValueAt(selectedRow, 3).toString());

                // Remove the selected row
                tableModel.removeRow(selectedRow);
                updateRendezVousFile();
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne à modifier.");
            }
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
                updateRendezVousFile();
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne à supprimer.");
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnModify);
        buttonPanel.add(btnDelete);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        return formPanel;
    }

    private JPanel createOrdonnancesTab() {
        JPanel ordonnancesTab = new JPanel();
        ordonnancesTab.setBackground(Color.WHITE);
        ordonnancesTab.add(new JLabel("Gestion des ordonnances"));
        return ordonnancesTab;
    }

    private Object[][] loadRendezVousData() {
        List<Object[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1) continue; // Skip the first line (header)

                String[] fields = line.split(",");
                if (fields.length >= 4) { // Ensure at least 4 columns (ID, Date, Heure, Motif)
                    // Load relevant fields and add default values for missing ones
                    String date = fields[1];
                    String heure = fields[2];
                    String motif = fields[3];
                    String statut = (fields.length > 4) ? fields[4] : "N/A"; // Default to "N/A" if missing
                    data.add(new Object[]{date, heure, statut, motif, "Modifier/Supprimer"});
                } else {
                    System.err.println("Skipping invalid row: " + line);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des rendez-vous : " + e.getMessage());
        }
        return data.toArray(new Object[0][]);
    }

    private void saveRendezVousData(String date, String heure, String statut, String motif) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write("ID," + date + "," + heure + "," + statut + "," + motif);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde du rendez-vous : " + e.getMessage());
        }
    }

    private void updateRendezVousFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("ID,Date,Heure,Statut,Motif");
            writer.newLine();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String date = tableModel.getValueAt(i, 0).toString();
                String heure = tableModel.getValueAt(i, 1).toString();
                String statut = tableModel.getValueAt(i, 2).toString();
                String motif = tableModel.getValueAt(i, 3).toString();
                writer.write("ID," + date + "," + heure + "," + statut + "," + motif);
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour du fichier des rendez-vous : " + e.getMessage());
        }
    }
}
