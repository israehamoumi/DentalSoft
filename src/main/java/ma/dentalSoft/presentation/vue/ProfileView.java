package ma.dentalSoft.presentation.vue;

import ma.dentalSoft.model.Utilisateur;

import javax.swing.*;
import java.awt.*;

public class ProfileView extends JFrame {
    private final Utilisateur utilisateur;

    public ProfileView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        setTitle("Mon Profil");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel profilePanel = new JPanel(new GridLayout(5, 2, 10, 10));
        profilePanel.add(new JLabel("Nom:"));
        profilePanel.add(new JLabel(utilisateur.getName()));

        profilePanel.add(new JLabel("Rôle:"));
        profilePanel.add(new JLabel(utilisateur.getRole()));

        profilePanel.add(new JLabel("Email:"));
        profilePanel.add(new JLabel(utilisateur.getEmail()));

        profilePanel.add(new JLabel("Téléphone:"));
        profilePanel.add(new JLabel(utilisateur.getTelephone()));

        profilePanel.add(new JLabel("Mot de passe:"));
        profilePanel.add(new JLabel("********"));

        JButton backButton = new JButton("Retour");
        backButton.addActionListener(e -> {
            new DashboardView(utilisateur).setVisible(true);
            dispose();
        });

        add(profilePanel, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }
}
