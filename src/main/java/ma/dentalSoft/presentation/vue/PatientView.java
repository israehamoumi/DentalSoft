package ma.dentalSoft.presentation.vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PatientView extends JPanel {
    private int totalPatients = 0;
    private int malePatients = 0;
    private int femalePatients = 0;
    private DefaultTableModel tableModel;
    private JTextField txtNom, txtPrenom, txtAge, txtEmail, txtAdresse, txtDateNaissance;
    private JComboBox<String> comboSexe;

    public PatientView() {
        // Définir la mise en page principale
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Titre
        JLabel title = new JLabel("Liste des Patients", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(102, 0, 153)); // Violet
        title.setBorder(new EmptyBorder(20, 10, 20, 10));
        title.setOpaque(true);
        title.setBackground(new Color(243, 236, 255)); // Couleur de fond claire
        add(title, BorderLayout.NORTH);

        // Table des patients
        JPanel tablePanel = createPatientTable();
        add(tablePanel, BorderLayout.CENTER);

        // Panneau des statistiques
        JPanel statsPanel = createStatsPanel();
        add(statsPanel, BorderLayout.EAST);

        // Formulaire moderne
        JPanel formPanel = createPatientForm();
        add(formPanel, BorderLayout.SOUTH);
    }

    private JPanel createPatientTable() {
        String[] columnNames = {"Nom Complet", "Sexe", "Âge", "Email", "Adresse", "Date de Naissance"};
        Object[][] data = loadPatientDataFromFile("src/main/resources/patient.txt");

        tableModel = new DefaultTableModel(data, columnNames);
        JTable patientTable = new JTable(tableModel);
        patientTable.setFont(new Font("Arial", Font.PLAIN, 14));
        patientTable.setRowHeight(25);
        patientTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        patientTable.getTableHeader().setBackground(new Color(102, 0, 153)); // Violet
        patientTable.getTableHeader().setForeground(Color.WHITE);
        patientTable.setGridColor(new Color(230, 230, 230)); // Lignes discrètes

        JScrollPane scrollPane = new JScrollPane(patientTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setBackground(Color.WHITE);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(102, 0, 153), 2, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        tablePanel.setBackground(Color.WHITE);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        statsPanel.setBackground(new Color(243, 236, 255)); // Fond violet clair
        statsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        statsPanel.add(createCircle("Total Patients", totalPatients, new Color(102, 0, 153)));
        statsPanel.add(createCircle("Hommes", malePatients, new Color(0, 102, 204)));
        statsPanel.add(createCircle("Femmes", femalePatients, new Color(255, 51, 153)));

        return statsPanel;
    }

    private JPanel createCircle(String labelText, int value, Color circleColor) {
        JPanel circlePanel = new JPanel();
        circlePanel.setLayout(new BorderLayout());
        circlePanel.setBackground(new Color(243, 236, 255)); // Fond violet clair

        JLabel circle = new JLabel(String.valueOf(value), SwingConstants.CENTER);
        circle.setFont(new Font("Arial", Font.BOLD, 20));
        circle.setForeground(Color.WHITE);
        circle.setOpaque(true);
        circle.setBackground(circleColor);
        circle.setPreferredSize(new Dimension(100, 100));
        circle.setHorizontalAlignment(SwingConstants.CENTER);
        circle.setVerticalAlignment(SwingConstants.CENTER);
        circle.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));

        JLabel label = new JLabel(labelText, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(new Color(102, 0, 153));

        circlePanel.add(circle, BorderLayout.CENTER);
        circlePanel.add(label, BorderLayout.SOUTH);

        return circlePanel;
    }

    private JPanel createPatientForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(243, 236, 255)); // Fond clair violet
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Espacement

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement entre les champs
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Champs de formulaire
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createLabeledField("Nom :", txtNom = new JTextField()), gbc);

        gbc.gridx = 1;
        formPanel.add(createLabeledField("Prénom :", txtPrenom = new JTextField()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(createLabeledField("Âge :", txtAge = new JTextField()), gbc);

        gbc.gridx = 1;
        formPanel.add(createLabeledField("Sexe :", comboSexe = new JComboBox<>(new String[]{"H", "F"})), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(createLabeledField("Email :", txtEmail = new JTextField()), gbc);

        gbc.gridx = 1;
        formPanel.add(createLabeledField("Adresse :", txtAdresse = new JTextField()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(createLabeledField("Date de Naissance :", txtDateNaissance = new JTextField()), gbc);

        // Boutons (Effacer et Valider)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(243, 236, 255)); // Fond clair
        JButton saveButton = new JButton("Valider");
        styleButton(saveButton, new Color(102, 0, 153), Color.WHITE);

        JButton clearButton = new JButton("Effacer");
        styleButton(clearButton, Color.RED, Color.WHITE);

        saveButton.addActionListener(e -> handleSaveAction());
        clearButton.addActionListener(e -> clearFormFields());

        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Boutons sur toute la largeur
        formPanel.add(buttonPanel, gbc);

        return formPanel;
    }

    private JPanel createLabeledField(String labelText, JComponent inputField) {
        JPanel fieldPanel = new JPanel(new BorderLayout(10, 10));
        fieldPanel.setBackground(new Color(243, 236, 255)); // Fond violet clair
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(new Color(102, 0, 153)); // Violet
        fieldPanel.add(label, BorderLayout.WEST);
        fieldPanel.add(inputField, BorderLayout.CENTER);
        return fieldPanel;
    }

    private void styleButton(JButton button, Color background, Color foreground) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private Object[][] loadPatientDataFromFile(String fileName) {
        List<Object[]> patientList = new ArrayList<>();
        totalPatients = 0; // Réinitialiser les statistiques
        malePatients = 0;
        femalePatients = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1) {
                    continue; // Ignorer la première ligne (en-tête)
                }
                String[] fields = line.split(",");
                Object[] row = new Object[fields.length - 1]; // Ignorer la première colonne
                System.arraycopy(fields, 1, row, 0, fields.length - 1);
                patientList.add(row);

                // Mettre à jour les statistiques
                totalPatients++;
                if (fields[2].trim().equalsIgnoreCase("H")) malePatients++;
                else if (fields[2].trim().equalsIgnoreCase("F")) femalePatients++;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la lecture du fichier : " + e.getMessage());
        }
        updateStatsPanel(); // Mettre à jour le panneau des statistiques
        return patientList.toArray(new Object[0][]);
    }


    private void handleSaveAction() {
        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        String age = txtAge.getText();
        String sexe = comboSexe.getSelectedItem().toString();
        String email = txtEmail.getText();
        String adresse = txtAdresse.getText();
        String dateNaissance = txtDateNaissance.getText();

        // Ajouter la ligne dans le tableau
        tableModel.addRow(new Object[]{nom + " " + prenom, sexe, age, email, adresse, dateNaissance});

        // Sauvegarder les données dans le fichier
        savePatientDataToFile("src/main/resources/patient.txt", nom, prenom, sexe, age, email, adresse, dateNaissance);

        // Mettre à jour les statistiques
        totalPatients++;
        if (sexe.equals("H")) malePatients++;
        else if (sexe.equals("F")) femalePatients++;

        updateStatsPanel(); // Mettre à jour l'affichage des statistiques
    }
    private void updateStatsPanel() {
        removeAll(); // Supprimer tous les composants actuels
        JPanel statsPanel = createStatsPanel(); // Créer un nouveau panneau avec des statistiques mises à jour
        add(statsPanel, BorderLayout.EAST); // Ajouter le panneau mis à jour
        revalidate(); // Recalculer la disposition
        repaint(); // Redessiner l'interface
    }


    private void clearFormFields() {
        txtNom.setText("");
        txtPrenom.setText("");
        txtAge.setText("");
        txtEmail.setText("");
        txtAdresse.setText("");
        txtDateNaissance.setText("");
        comboSexe.setSelectedIndex(0);
    }

    private void savePatientDataToFile(String fileName, String nom, String prenom, String sexe, String age, String email, String adresse, String dateNaissance) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(nom + "," + prenom + "," + sexe + "," + age + "," + email + "," + adresse + "," + dateNaissance);
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }
}
