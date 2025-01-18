package ma.dentalSoft.presentation.vue;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PatientView extends JPanel {
    private DefaultTableModel tableModel;
    private JTable patientTable;
    private JTextField txtNom, txtAge, txtEmail, txtAdresse, txtDateNaissance;
    private JComboBox<String> comboSexe;
    private static final String FILE_PATH = "src/main/resources/patient.txt";
    private JFrame parentFrame;

    public PatientView(JFrame parentFrame) {
        this.parentFrame = parentFrame;
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
        String[] columnNames = {"Nom Complet", "Sexe", "Âge", "Email", "Adresse", "Date de Naissance", "Dossier Médical"};
        Object[][] data = loadPatientDataFromFile(FILE_PATH);

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Rendre uniquement la colonne "Dossier Médical" cliquable
            }
        };

        patientTable = new JTable(tableModel);
        patientTable.setFont(new Font("Arial", Font.PLAIN, 14));
        patientTable.setRowHeight(30);
        patientTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        patientTable.getTableHeader().setBackground(new Color(102, 0, 153));
        patientTable.getTableHeader().setForeground(Color.WHITE);

        // Ajouter un bouton pour la colonne "Dossier Médical"
        patientTable.getColumn("Dossier Médical").setCellRenderer(new ButtonRenderer());
        patientTable.getColumn("Dossier Médical").setCellEditor(new ButtonEditor());

        JScrollPane scrollPane = new JScrollPane(patientTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(102, 0, 153), 2));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

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
        JButton btnUpdate = createButton("Modifier", e -> updateSelectedPatient());
        JButton btnDelete = createButton("Supprimer", e -> deleteSelectedPatient());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        return formPanel;
    }

    private void updateSelectedPatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow >= 0) {
            String nom = txtNom.getText();
            String sexe = comboSexe.getSelectedItem().toString();
            String age = txtAge.getText();
            String email = txtEmail.getText();
            String adresse = txtAdresse.getText();
            String dateNaissance = txtDateNaissance.getText();

            tableModel.setValueAt(nom, selectedRow, 0);
            tableModel.setValueAt(sexe, selectedRow, 1);
            tableModel.setValueAt(age, selectedRow, 2);
            tableModel.setValueAt(email, selectedRow, 3);
            tableModel.setValueAt(adresse, selectedRow, 4);
            tableModel.setValueAt(dateNaissance, selectedRow, 5);

            saveTableDataToFile();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne à modifier.");
        }
    }

    private void deleteSelectedPatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.removeRow(selectedRow);
            saveTableDataToFile();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne à supprimer.");
        }
    }

    private void saveTableDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                StringBuilder row = new StringBuilder();
                for (int j = 1; j < tableModel.getColumnCount() - 1; j++) { // Exclude first column and last column (Dossier Médical)
                    row.append(tableModel.getValueAt(i, j)).append(",");
                }
                writer.write(row.substring(0, row.length() - 1)); // Remove trailing comma
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde des données : " + e.getMessage());
        }
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
        String nom = txtNom.getText();
        String sexe = comboSexe.getSelectedItem().toString();
        String age = txtAge.getText();
        String email = txtEmail.getText();
        String adresse = txtAdresse.getText();
        String dateNaissance = txtDateNaissance.getText();

        tableModel.addRow(new Object[]{nom, sexe, age, email, adresse, dateNaissance, "Voir"});
        saveTableDataToFile();
    }

    private Object[][] loadPatientDataFromFile(String filePath) {
        List<Object[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1) continue; // Skip the first line (header)

                String[] fields = line.split(",");
                if (fields.length > 1) { // Ensure there are enough fields to avoid IndexOutOfBounds
                    data.add(new Object[]{fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], "Voir"});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données : " + e.getMessage());
        }
        return data.toArray(new Object[0][]);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Voir" : value.toString());
            setBackground(new Color(102, 0, 153));
            setForeground(Color.WHITE);
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;

        public ButtonEditor() {
            super(new JTextField());
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(new Color(102, 0, 153));
            button.setForeground(Color.WHITE);
            button.addActionListener(e -> {
                if (parentFrame != null) {
                    parentFrame.dispose(); // Close the parent frame
                }

                // Open the Dossier Médical (RendezVousView) frame
                JFrame dossierMedicalFrame = new JFrame("Dossier Médical");
                dossierMedicalFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dossierMedicalFrame.setSize(1200, 800);
                dossierMedicalFrame.setLocationRelativeTo(null);

                RendezVousView rendezVousView = new RendezVousView(dossierMedicalFrame); // Pass the new frame as parent
                dossierMedicalFrame.add(rendezVousView);

                dossierMedicalFrame.setVisible(true);
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText((value == null) ? "Voir" : value.toString());
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Voir";
        }
    }
}
