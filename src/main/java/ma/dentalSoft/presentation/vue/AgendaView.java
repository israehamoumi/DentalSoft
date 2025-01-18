package ma.dentalSoft.presentation.vue;

import javax.swing.*;
import java.awt.*;

public class AgendaView extends JPanel {
    public AgendaView() {
        setLayout(new BorderLayout());

        // Ajouter un titre
        JLabel titleLabel = new JLabel("Agenda des Rendez-vous", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Ajouter une grille de calendrier
        JPanel calendarPanel = new JPanel(new GridLayout(6, 7, 5, 5));
        calendarPanel.setBorder(BorderFactory.createTitledBorder("Mois Courant"));

        String[] days = {"Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"};
        for (String day : days) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            dayLabel.setForeground(Color.BLUE);
            calendarPanel.add(dayLabel);
        }

        // Ajouter des jours du mois
        for (int i = 1; i <= 42; i++) { // Grille de 6 semaines
            if (i <= 30) { // Simuler un mois de 30 jours
                JButton dayButton = new JButton(String.valueOf(i));
                dayButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                dayButton.setBackground(Color.LIGHT_GRAY);
                calendarPanel.add(dayButton);
            } else {
                calendarPanel.add(new JLabel()); // Cases vides pour remplir la grille
            }
        }
        add(calendarPanel, BorderLayout.CENTER);

        // Ajouter un tableau pour les rendez-vous
        String[] columns = {"Date", "Heure", "Statut", "Motif"};
        Object[][] data = {
                {"12-06-2022", "10:00", "En cours", "Soin de carie"},
                {"13-06-2022", "14:30", "Terminé", "Extraction dent sagesse"}
        };

        JTable rdvTable = new JTable(data, columns);
        rdvTable.setRowHeight(30);
        rdvTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane tableScrollPane = new JScrollPane(rdvTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Rendez-vous à venir"));

        add(tableScrollPane, BorderLayout.SOUTH);
    }
}
