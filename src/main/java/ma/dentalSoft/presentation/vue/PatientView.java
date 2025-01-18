package ma.dentalSoft.presentation.vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PatientView extends JPanel {
    private static final String FILE_PATH = "src/main/resources/patient.txt";
    private DefaultTableModel tableModel;
    private JTable patientTable;
    private final DashboardView dashboardView;

    public PatientView(DashboardView dashboardView) {
        this.dashboardView = dashboardView;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // Titre
        JLabel title = new JLabel("Gestion des Patients", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(0, 102, 204));
        title.setBorder(new EmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        // Panneau Table
        JPanel tablePanel = createPatientTable();
        add(tablePanel, BorderLayout.CENTER);

        // Panneau Formulaire
        JPanel formPanel = createPatientForm();
        add(formPanel, BorderLayout.SOUTH);
    }

    private JPanel createPatientTable() {
        String[] columnNames = {"Nom Complet", "Sexe", "Âge", "Email", "Adresse", "Date de Naissance", "Dossier Médical"};
        Object[][] data = loadPatientDataFromFile();

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Seule la colonne "Dossier Médical" est cliquable
            }
        };

        patientTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    component.setBackground(row % 2 == 0 ? new Color(240, 240, 240) : Color.WHITE);
                } else {
                    component.setBackground(new Color(184, 207, 229));
                }
                return component;
            }
        };

        patientTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        patientTable.setRowHeight(35);

        JTableHeader header = patientTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(0, 102, 204));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createLineBorder(new Color(0, 76, 153), 2));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < patientTable.getColumnCount(); i++) {
            patientTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Bouton "Voir"
        patientTable.getColumn("Dossier Médical").setCellRenderer(new ButtonRenderer());
        patientTable.getColumn("Dossier Médical").setCellEditor(new ButtonEditor(dashboardView));

        JScrollPane scrollPane = new JScrollPane(patientTable);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createPatientForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(230, 230, 250));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtNom = new JTextField(20);
        JComboBox<String> comboSexe = new JComboBox<>(new String[]{"H", "F"});
        JTextField txtAge = new JTextField(5);
        JTextField txtEmail = new JTextField(20);
        JTextField txtAdresse = new JTextField(20);
        JTextField txtDateNaissance = new JTextField(15);

        addFormField(formPanel, gbc, 0, "Nom :", txtNom);
        addFormField(formPanel, gbc, 1, "Sexe :", comboSexe);
        addFormField(formPanel, gbc, 2, "Âge :", txtAge);
        addFormField(formPanel, gbc, 3, "Email :", txtEmail);
        addFormField(formPanel, gbc, 4, "Adresse :", txtAdresse);
        addFormField(formPanel, gbc, 5, "Date de Naissance :", txtDateNaissance);

        JButton btnAdd = createStyledButton("Ajouter");
        btnAdd.addActionListener(e -> {
            String nom = txtNom.getText();
            String sexe = comboSexe.getSelectedItem().toString();
            String age = txtAge.getText();
            String email = txtEmail.getText();
            String adresse = txtAdresse.getText();
            String dateNaissance = txtDateNaissance.getText();

            if (!nom.isEmpty() && !sexe.isEmpty() && !age.isEmpty() && !email.isEmpty() && !adresse.isEmpty() && !dateNaissance.isEmpty()) {
                tableModel.addRow(new Object[]{nom, sexe, age, email, adresse, dateNaissance, "Voir"});
                saveTableDataToFile();
                txtNom.setText("");
                comboSexe.setSelectedIndex(0);
                txtAge.setText("");
                txtEmail.setText("");
                txtAdresse.setText("");
                txtDateNaissance.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !");
            }
        });

        JButton btnUpdate = createStyledButton("Modifier");
        btnUpdate.addActionListener(e -> {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.setValueAt(txtNom.getText(), selectedRow, 0);
                tableModel.setValueAt(comboSexe.getSelectedItem(), selectedRow, 1);
                tableModel.setValueAt(txtAge.getText(), selectedRow, 2);
                tableModel.setValueAt(txtEmail.getText(), selectedRow, 3);
                tableModel.setValueAt(txtAdresse.getText(), selectedRow, 4);
                tableModel.setValueAt(txtDateNaissance.getText(), selectedRow, 5);
                saveTableDataToFile();
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne à modifier !");
            }
        });

        JButton btnDelete = createStyledButton("Supprimer");
        btnDelete.addActionListener(e -> {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
                saveTableDataToFile();
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne à supprimer !");
            }
        });

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

    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(new Color(0, 51, 153));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
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

    private Object[][] loadPatientDataFromFile() {
        List<Object[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1) continue; // Ignorer le header
                String[] fields = line.split(",");
                if (fields.length >= 6) {
                    data.add(new Object[]{fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], "Voir"});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données : " + e.getMessage());
        }
        return data.toArray(new Object[0][]);
    }

    private void saveTableDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("Nom,Sexe,Âge,Email,Adresse,Date de Naissance");
            writer.newLine();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount() - 1; j++) {
                    writer.write(tableModel.getValueAt(i, j).toString() + ",");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde des données : " + e.getMessage());
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Voir" : value.toString());
            setBackground(new Color(0, 102, 204));
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private final DashboardView dashboardView;
        private boolean clicked;

        public ButtonEditor(DashboardView dashboardView) {
            super(new JTextField());
            this.dashboardView = dashboardView;
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(new Color(0, 102, 204));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Segoe UI", Font.BOLD, 14));
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText((value == null) ? "Voir" : value.toString());
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                dashboardView.showDossierMedical();
            }
            clicked = false;
            return button.getText();
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }
}
