package ma.dentalSoft.presentation.vue;

import ma.dentalSoft.model.Act;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultationView extends JPanel {

    private static final String CONSULTATION_FILE_PATH = "src/main/resources/consultation.txt";
    private static final String ACTS_FILE_PATH = "src/main/resources/acts.txt";

    private DefaultTableModel tableModel;
    private JTable table;
    private List<Act> actes;

    public ConsultationView() {
        actes = loadActs();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Header
        add(createHeader(), BorderLayout.NORTH);

        // Table
        add(createTablePanel(), BorderLayout.CENTER);

        // Form
        add(createFormPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Gestion des Consultations", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(102, 0, 153));

        header.add(lblTitle, BorderLayout.CENTER);
        return header;
    }

    private JScrollPane createTablePanel() {
        String[] columnNames = {"Patient ID", "Date", "Temps", "Actes", "Nombre de dents", "Prix", "Actions"};
        Object[][] data = loadConsultations();

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Seule la colonne "Actions" est modifiable
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(102, 0, 153));
        table.getTableHeader().setForeground(Color.WHITE);

        return new JScrollPane(table);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(243, 236, 255));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtPatientId = new JTextField(10);
        JTextField txtDate = new JTextField(15);
        JTextField txtTemps = new JTextField(10);
        JComboBox<Act> comboActes = new JComboBox<>(actes.toArray(new Act[0]));
        JTextField txtNombreDents = new JTextField(5);
        JTextField txtPrix = new JTextField(10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Patient ID :"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtPatientId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Date :"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtDate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Temps :"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtTemps, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Actes :"), gbc);
        gbc.gridx = 1;
        formPanel.add(comboActes, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Nombre de dents :"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtNombreDents, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Prix :"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtPrix, gbc);

        // Bouton Ajouter
        JButton btnAdd = new JButton("Ajouter");
        btnAdd.addActionListener(e -> {
            String patientId = txtPatientId.getText();
            String date = txtDate.getText();
            String temps = txtTemps.getText();
            Act act = (Act) comboActes.getSelectedItem();
            String nombreDents = txtNombreDents.getText();
            String prix = txtPrix.getText();

            if (!patientId.isEmpty() && !date.isEmpty() && !temps.isEmpty() && act != null && !nombreDents.isEmpty() && !prix.isEmpty()) {
                tableModel.addRow(new Object[]{patientId, date, temps, act, nombreDents, prix, "Modifier/Supprimer"});
                saveConsultationToFile(patientId, date, temps, act.getId(), nombreDents, prix);

                txtPatientId.setText("");
                txtDate.setText("");
                txtTemps.setText("");
                comboActes.setSelectedIndex(0);
                txtNombreDents.setText("");
                txtPrix.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            }
        });

        // Bouton Modifier
        JButton btnUpdate = new JButton("Modifier");
        btnUpdate.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String patientId = txtPatientId.getText();
                String date = txtDate.getText();
                String temps = txtTemps.getText();
                Act act = (Act) comboActes.getSelectedItem();
                String nombreDents = txtNombreDents.getText();
                String prix = txtPrix.getText();

                if (!patientId.isEmpty() && !date.isEmpty() && !temps.isEmpty() && act != null && !nombreDents.isEmpty() && !prix.isEmpty()) {
                    tableModel.setValueAt(patientId, selectedRow, 0);
                    tableModel.setValueAt(date, selectedRow, 1);
                    tableModel.setValueAt(temps, selectedRow, 2);
                    tableModel.setValueAt(act, selectedRow, 3);
                    tableModel.setValueAt(nombreDents, selectedRow, 4);
                    tableModel.setValueAt(prix, selectedRow, 5);

                    updateConsultationsFile();
                    JOptionPane.showMessageDialog(this, "Consultation mise à jour avec succès.");
                } else {
                    JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne à modifier.");
            }
        });

        // Bouton Supprimer
        JButton btnDelete = new JButton("Supprimer");
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
                updateConsultationsFile();
                JOptionPane.showMessageDialog(this, "Consultation supprimée avec succès.");
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne à supprimer.");
            }
        });

        // Ajouter les boutons au panneau
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        return formPanel;
    }

    private Object[][] loadConsultations() {
        List<Object[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CONSULTATION_FILE_PATH))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1) continue; // Ignore le header

                String[] fields = line.split(",");
                if (fields.length >= 7) {
                    Long actId = Long.parseLong(fields[4]);
                    Act act = findActById(actId);
                    data.add(new Object[]{fields[1], fields[2], fields[3], act, fields[5], fields[6], "Modifier/Supprimer"});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des consultations : " + e.getMessage());
        }
        return data.toArray(new Object[0][]);
    }

    private Act findActById(Long actId) {
        return actes.stream().filter(act -> act.getId().equals(actId)).findFirst().orElse(null);
    }

    private List<Act> loadActs() {
        List<Act> actes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ACTS_FILE_PATH))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1) continue; // Ignore le header

                String[] fields = line.split(",");
                if (fields.length >= 4) {
                    Long id = Long.parseLong(fields[0]);
                    String nomActe = fields[1];
                    String description = fields[2];
                    double cout = Double.parseDouble(fields[3]);
                    actes.add(new Act(id, nomActe, description, cout));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des actes : " + e.getMessage());
        }
        return actes;
    }

    private void saveConsultationToFile(String patientId, String date, String temps, Long actId, String nombreDents, String prix) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONSULTATION_FILE_PATH, true))) {
            writer.write(patientId + "," + date + "," + temps + "," + actId + "," + nombreDents + "," + prix);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde de la consultation : " + e.getMessage());
        }
    }

    private void updateConsultationsFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONSULTATION_FILE_PATH))) {
            writer.write("Patient ID,Date,Temps,Actes,Nombre de dents,Prix");
            writer.newLine();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String patientId = tableModel.getValueAt(i, 0).toString();
                String date = tableModel.getValueAt(i, 1).toString();
                String temps = tableModel.getValueAt(i, 2).toString();
                Act act = (Act) tableModel.getValueAt(i, 3);
                String nombreDents = tableModel.getValueAt(i, 4).toString();
                String prix = tableModel.getValueAt(i, 5).toString();
                writer.write(patientId + "," + date + "," + temps + "," + act.getId() + "," + nombreDents + "," + prix);
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour des consultations : " + e.getMessage());
        }
    }
}
