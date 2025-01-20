package ma.dentalSoft.presentation.vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CaisseView extends JPanel {
    private static final String CONSULTATION_FILE_PATH = "src/main/resources/consultation.txt";
    private static final String PATIENT_FILE_PATH = "src/main/resources/patient.txt";
    private static final String INVOICE_FILE_PATH = "src/main/resources/factures.txt";

    public CaisseView() {
        setLayout(new BorderLayout());

        // Titre
        JLabel titleLabel = new JLabel("Caisse", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 86, 174));
        add(titleLabel, BorderLayout.NORTH);

        // Tableau des factures
        String[] columns = {"N° Facture", "Date", "Nom Complet du Patient", "N° Consultation", "Montant", "Action"};
        Object[][] data = loadCaisseData();

        DefaultTableModel tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Seule la colonne Action est éditable
            }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(0, 86, 174));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Factures"));
        add(scrollPane, BorderLayout.CENTER);

        // Panneau des statistiques
        JPanel statsPanel = createStatsPanel();
        add(statsPanel, BorderLayout.SOUTH);

        // Boutons d'action (Générer)
        table.getColumnModel().getColumn(5).setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            JButton generateButton = new JButton("Générer");
            generateButton.addActionListener(e -> generateInvoice(tableModel, row));
            panel.add(generateButton);
            return panel;
        });
    }

    private Object[][] loadCaisseData() {
        List<Object[]> data = new ArrayList<>();
        try (BufferedReader consultationReader = new BufferedReader(new FileReader(CONSULTATION_FILE_PATH));
             BufferedReader patientReader = new BufferedReader(new FileReader(PATIENT_FILE_PATH))) {

            // Load patient data
            List<String[]> patients = new ArrayList<>();
            String line;
            boolean firstLine = true;
            while ((line = patientReader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip the header
                    continue;
                }
                patients.add(line.split(","));
            }

            // Load consultations and match with patients
            firstLine = true;
            while ((line = consultationReader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip the header
                    continue;
                }
                String[] consultation = line.split(",");
                if (consultation.length >= 7) {
                    String patientId = consultation[1];
                    String patientName = findPatientName(patients, patientId);
                    data.add(new Object[]{consultation[0], consultation[2], patientName, consultation[0], consultation[6], "Action"});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données : " + e.getMessage());
        }
        return data.toArray(new Object[0][]);
    }

    private String findPatientName(List<String[]> patients, String patientId) {
        for (String[] patient : patients) {
            if (patient[0].equals(patientId)) {
                return patient[1]; // Return the patient's name
            }
        }
        return "Inconnu";
    }

    private void generateInvoice(DefaultTableModel tableModel, int row) {
        String invoiceNumber = tableModel.getValueAt(row, 0).toString();
        String date = tableModel.getValueAt(row, 1).toString();
        String patientName = tableModel.getValueAt(row, 2).toString();
        String consultationCode = tableModel.getValueAt(row, 3).toString();
        String amount = tableModel.getValueAt(row, 4).toString();

        String invoiceContent = String.format(
                "Facture: %s%nDate: %s%nPatient: %s%nConsultation: %s%nMontant: %s DH%n",
                invoiceNumber, date, patientName, consultationCode, amount
        );

        saveInvoiceToFile(invoiceContent);
        JOptionPane.showMessageDialog(this, "Facture générée avec succès:\n" + invoiceContent);
    }

    private void saveInvoiceToFile(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVOICE_FILE_PATH, true))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde de la facture : " + e.getMessage());
        }
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistiques"));
        statsPanel.setBackground(Color.WHITE);

        String[] statsLabels = {
                "Recette du Jour", "Recette du Mois", "Recette de l'Année", "Dépenses du Mois"
        };

        String[] statsValues = {
                String.valueOf(getRecette("jour")) + " DH",
                String.valueOf(getRecette("mois")) + " DH",
                String.valueOf(getRecette("annee")) + " DH",
                String.valueOf(getDepenses("mois")) + " DH",
        };

        for (int i = 0; i < statsLabels.length; i++) {
            JPanel statPanel = new JPanel(new BorderLayout());
            statPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 86, 174), 2));
            statPanel.setBackground(Color.WHITE);

            JLabel valueLabel = new JLabel(statsValues[i], SwingConstants.CENTER);
            valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            valueLabel.setForeground(new Color(0, 86, 174));

            JLabel label = new JLabel(statsLabels[i], SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            label.setForeground(new Color(0, 51, 153));

            statPanel.add(valueLabel, BorderLayout.CENTER);
            statPanel.add(label, BorderLayout.SOUTH);

            statsPanel.add(statPanel);
        }

        return statsPanel;
    }

    public int getRecette(String period) {
        return calculateRevenue(period);
    }

    public int getDepenses(String period) {
        // Simulez les dépenses (remplacez par la vraie logique si nécessaire)
        if ("mois".equals(period)) {
            return 1000; // Exemple de dépenses pour le mois
        }
        return 0;
    }

    private int calculateRevenue(String period) {
        int total = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(CONSULTATION_FILE_PATH))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] consultation = line.split(",");
                if (consultation.length >= 7) {
                    String date = consultation[2];
                    int amount = Integer.parseInt(consultation[6]);

                    switch (period) {
                        case "jour":
                            if (isToday(date)) total += amount;
                            break;
                        case "mois":
                            if (isThisMonth(date)) total += amount;
                            break;
                        case "annee":
                            if (isThisYear(date)) total += amount;
                            break;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du calcul des revenus : " + e.getMessage());
        }
        return total;
    }

    private boolean isToday(String date) {
        LocalDate consultationDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return consultationDate.equals(LocalDate.now());
    }

    private boolean isThisMonth(String date) {
        LocalDate consultationDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate now = LocalDate.now();
        return consultationDate.getYear() == now.getYear() && consultationDate.getMonth() == now.getMonth();
    }

    private boolean isThisYear(String date) {
        LocalDate consultationDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return consultationDate.getYear() == LocalDate.now().getYear();
    }



}