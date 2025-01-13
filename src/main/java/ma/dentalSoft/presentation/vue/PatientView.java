package ma.dentalSoft.presentation.vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PatientView extends JPanel {
    private DefaultTableModel tableModel;
    private JTable patientTable;
    private JTextField txtNom, txtAge, txtEmail, txtAdresse, txtDateNaissance, txtSearch;
    private JComboBox<String> comboSexe;
    private static final String FILE_PATH = "src/main/resources/patient.txt";

    public PatientView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("Gestion des Patients", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(102, 0, 153));
        title.setBorder(new EmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = createPatientTable();
        add(tablePanel, BorderLayout.CENTER);

        // Form Panel
        JPanel formPanel = createPatientForm();
        add(formPanel, BorderLayout.SOUTH);
    }

    private JPanel createPatientTable() {
        String[] columnNames = {"ID", "Nom", "Sexe", "Âge", "Email", "Adresse", "Date de Naissance"};
        Object[][] data = loadPatientDataFromFile(FILE_PATH);

        tableModel = new DefaultTableModel(data, columnNames);
        patientTable = new JTable(tableModel);
        patientTable.setFont(new Font("Arial", Font.PLAIN, 14));
        patientTable.setRowHeight(25);
        patientTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        patientTable.getTableHeader().setBackground(new Color(102, 0, 153));
        patientTable.getTableHeader().setForeground(Color.WHITE);

        patientTable.setPreferredScrollableViewportSize(new Dimension(800, 400)); // Bigger table

        JScrollPane scrollPane = new JScrollPane(patientTable);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        searchPanel.add(new JLabel("Rechercher :"));

        txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Rechercher");
        btnSearch.addActionListener(e -> searchPatient());

        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        tablePanel.add(searchPanel, BorderLayout.NORTH);

        return tablePanel;
    }

    private JPanel createPatientForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(243, 236, 255));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNom = new JTextField(25);
        txtAge = new JTextField(25);
        txtEmail = new JTextField(25);
        txtAdresse = new JTextField(25);
        txtDateNaissance = new JTextField(25);
        comboSexe = new JComboBox<>(new String[]{"H", "F"});

        addFormField(formPanel, gbc, 0, "Nom :", txtNom);
        addFormField(formPanel, gbc, 1, "Sexe :", comboSexe);
        addFormField(formPanel, gbc, 2, "Âge :", txtAge);
        addFormField(formPanel, gbc, 3, "Email :", txtEmail);
        addFormField(formPanel, gbc, 4, "Adresse :", txtAdresse);
        addFormField(formPanel, gbc, 5, "Date de Naissance (JJ/MM/AAAA) :", txtDateNaissance);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = createButton("Ajouter", e -> addPatient());
        JButton btnUpdate = createButton("Modifier", e -> updatePatient());
        JButton btnDelete = createButton("Supprimer", e -> deletePatient());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        return formPanel;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    private void addPatient() {
        int newId = generateNewId();
        String nom = txtNom.getText();
        String sexe = comboSexe.getSelectedItem().toString();
        String age = txtAge.getText();
        String email = txtEmail.getText();
        String adresse = txtAdresse.getText();
        String dateNaissance = txtDateNaissance.getText();

        if (!isValidDate(dateNaissance)) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une date de naissance au format JJ/MM/AAAA.");
            return;
        }

        tableModel.addRow(new Object[]{newId, nom, sexe, age, email, adresse, dateNaissance});
        saveToFile(FILE_PATH, newId + "," + nom + "," + sexe + "," + age + "," + email + "," + adresse + "," + dateNaissance);
        clearFields();
    }

    private void updatePatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un patient à modifier.");
            return;
        }

        String nom = txtNom.getText();
        String sexe = comboSexe.getSelectedItem().toString();
        String age = txtAge.getText();
        String email = txtEmail.getText();
        String adresse = txtAdresse.getText();
        String dateNaissance = txtDateNaissance.getText();

        if (!isValidDate(dateNaissance)) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une date de naissance au format JJ/MM/AAAA.");
            return;
        }

        tableModel.setValueAt(nom, selectedRow, 1);
        tableModel.setValueAt(sexe, selectedRow, 2);
        tableModel.setValueAt(age, selectedRow, 3);
        tableModel.setValueAt(email, selectedRow, 4);
        tableModel.setValueAt(adresse, selectedRow, 5);
        tableModel.setValueAt(dateNaissance, selectedRow, 6);

        writeTableToFile(FILE_PATH);
        clearFields();
    }

    private void deletePatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un patient à supprimer.");
            return;
        }

        tableModel.removeRow(selectedRow);
        writeTableToFile(FILE_PATH);
        clearFields();
    }

    private void searchPatient() {
        String searchTerm = txtSearch.getText().toLowerCase();
        boolean found = false;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                String value = tableModel.getValueAt(i, j).toString().toLowerCase();
                if (value.contains(searchTerm)) {
                    patientTable.setRowSelectionInterval(i, i);
                    patientTable.scrollRectToVisible(patientTable.getCellRect(i, 0, true));
                    found = true;
                    break;
                }
            }
            if (found) break;
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Aucun patient trouvé.");
        }
    }

    private int generateNewId() {
        int highestId = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            highestId = Math.max(highestId, Integer.parseInt(tableModel.getValueAt(i, 0).toString()));
        }
        return highestId + 1;
    }

    private boolean isValidDate(String date) {
        return Pattern.matches("\\d{2}/\\d{2}/\\d{4}", date);
    }

    private void saveToFile(String filePath, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    private void writeTableToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("id,name,sexe,age,email,adresse,dateDeNaissance");
            writer.newLine();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                StringBuilder row = new StringBuilder();
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    row.append(tableModel.getValueAt(i, j)).append(",");
                }
                writer.write(row.substring(0, row.length() - 1));
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    private Object[][] loadPatientDataFromFile(String filePath) {
        List<Object[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1) continue; // Skip header
                data.add(line.split(","));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données : " + e.getMessage());
        }
        return data.toArray(new Object[0][]);
    }

    private void clearFields() {
        txtNom.setText("");
        comboSexe.setSelectedIndex(0);
        txtAge.setText("");
        txtEmail.setText("");
        txtAdresse.setText("");
        txtDateNaissance.setText("");
    }
}
