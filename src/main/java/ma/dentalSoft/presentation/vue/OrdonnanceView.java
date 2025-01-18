package ma.dentalSoft.presentation.vue;

import ma.dentalSoft.model.enums.CategorieHistoriqueMedicale;
import ma.dentalSoft.model.enums.Risque;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrdonnanceView extends JPanel {
    private DefaultTableModel ordonnanceTableModel;
    private JTable ordonnanceTable;
    private JTextField txtDate, txtMotif;
    private JComboBox<CategorieHistoriqueMedicale> comboCategorie;
    private JComboBox<Risque> comboRisque;
    private static final String ORDONNANCE_FILE_PATH = "src/main/resources/ordonnace.txt";

    public OrdonnanceView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("Gestion des Ordonnances", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(102, 0, 153));
        title.setBorder(new EmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        // Main Content
        add(createOrdonnanceSection(), BorderLayout.CENTER);
    }

    private JPanel createOrdonnanceSection() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Table
        String[] columnNames = {"Date", "Motif", "Catégorie", "Risque"};
        Object[][] data = loadDataFromFile(ORDONNANCE_FILE_PATH, 4);

        ordonnanceTableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre les cellules non éditables
            }
        };

        ordonnanceTable = new JTable(ordonnanceTableModel);
        ordonnanceTable.setRowHeight(30);
        ordonnanceTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        ordonnanceTable.getTableHeader().setBackground(new Color(102, 0, 153));
        ordonnanceTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(ordonnanceTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Form
        panel.add(createOrdonnanceForm(), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createOrdonnanceForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(243, 236, 255));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtDate = new JTextField(25);
        txtMotif = new JTextField(25);
        comboCategorie = new JComboBox<>(CategorieHistoriqueMedicale.values());
        comboRisque = new JComboBox<>(Risque.values());

        addFormField(formPanel, gbc, 0, "Date :", txtDate);
        addFormField(formPanel, gbc, 1, "Motif :", txtMotif);
        addFormField(formPanel, gbc, 2, "Catégorie :", comboCategorie);
        addFormField(formPanel, gbc, 3, "Risque :", comboRisque);

        JButton btnAdd = createButton("Ajouter", e -> {
            String date = txtDate.getText();
            String motif = txtMotif.getText();
            CategorieHistoriqueMedicale categorie = (CategorieHistoriqueMedicale) comboCategorie.getSelectedItem();
            Risque risque = (Risque) comboRisque.getSelectedItem();

            ordonnanceTableModel.addRow(new Object[]{date, motif, categorie.name(), risque.name()});
            saveDataToFile(ORDONNANCE_FILE_PATH, date + "," + motif + "," + categorie.name() + "," + risque.name());

            txtDate.setText("");
            txtMotif.setText("");
            comboCategorie.setSelectedIndex(0);
            comboRisque.setSelectedIndex(0);
        });

        JButton btnDelete = createButton("Supprimer", e -> {
            int selectedRow = ordonnanceTable.getSelectedRow();
            if (selectedRow >= 0) {
                ordonnanceTableModel.removeRow(selectedRow);
                saveTableDataToFile(ordonnanceTableModel, ORDONNANCE_FILE_PATH);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne à supprimer.");
            }
        });

        JButton btnModify = createButton("Modifier", e -> {
            int selectedRow = ordonnanceTable.getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    String date = (String) ordonnanceTableModel.getValueAt(selectedRow, 0);
                    String motif = (String) ordonnanceTableModel.getValueAt(selectedRow, 1);

                    // Nettoyer et convertir la chaîne en énumération
                    String categorieString = ((String) ordonnanceTableModel.getValueAt(selectedRow, 2)).trim().toUpperCase();
                    String risqueString = ((String) ordonnanceTableModel.getValueAt(selectedRow, 3)).trim().toUpperCase();

                    CategorieHistoriqueMedicale categorie = CategorieHistoriqueMedicale.valueOf(categorieString);
                    Risque risque = Risque.valueOf(risqueString);

                    txtDate.setText(date);
                    txtMotif.setText(motif);
                    comboCategorie.setSelectedItem(categorie);
                    comboRisque.setSelectedItem(risque);

                    ordonnanceTableModel.removeRow(selectedRow);
                    saveTableDataToFile(ordonnanceTableModel, ORDONNANCE_FILE_PATH);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, "Données invalides : impossible de convertir en énumération.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne à modifier.");
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnModify);

        gbc.gridx = 0;
        gbc.gridy = 4;
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

    private Object[][] loadDataFromFile(String filePath, int expectedColumns) {
        List<Object[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] fields = line.split(",", -1); // Accepter les lignes avec moins de colonnes
                if (fields.length >= expectedColumns) {
                    data.add(fields);
                } else {
                    // Compléter les colonnes manquantes
                    String[] extendedFields = new String[expectedColumns];
                    System.arraycopy(fields, 0, extendedFields, 0, fields.length);
                    for (int i = fields.length; i < expectedColumns; i++) {
                        extendedFields[i] = "N/A"; // Valeur par défaut
                    }
                    data.add(extendedFields);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données : " + e.getMessage());
        }
        return data.toArray(new Object[0][]);
    }

    private void saveDataToFile(String filePath, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    private void saveTableDataToFile(DefaultTableModel model, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (int i = 0; i < model.getRowCount(); i++) {
                StringBuilder row = new StringBuilder();
                for (int j = 0; j < model.getColumnCount(); j++) {
                    row.append(model.getValueAt(i, j)).append(",");
                }
                writer.write(row.substring(0, row.length() - 1)); // Remove trailing comma
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde des données : " + e.getMessage());
        }
    }
}
