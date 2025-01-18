package ma.dentalSoft.presentation.vue;

import ma.dentalSoft.model.Utilisateur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProfileView extends JPanel {
    private final Utilisateur utilisateur;

    public ProfileView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // Title Section
        JLabel lblTitle = new JLabel("Mon Profil", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(lblTitle, BorderLayout.NORTH);

        // Profile Panel
        JPanel profilePanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, Color.WHITE, getWidth(), getHeight(), new Color(230, 230, 250));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        profilePanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        profilePanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNameTitle = new JLabel("Nom:");
        lblNameTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNameTitle.setForeground(new Color(0, 51, 153));
        JLabel lblName = new JLabel(utilisateur.getName());
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel lblRoleTitle = new JLabel("Rôle:");
        lblRoleTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblRoleTitle.setForeground(new Color(0, 51, 153));
        JLabel lblRole = new JLabel(utilisateur.getRole());
        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel lblEmailTitle = new JLabel("Email:");
        lblEmailTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblEmailTitle.setForeground(new Color(0, 51, 153));
        JLabel lblEmail = new JLabel(utilisateur.getEmail());
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel lblPhoneTitle = new JLabel("Téléphone:");
        lblPhoneTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblPhoneTitle.setForeground(new Color(0, 51, 153));
        JLabel lblPhone = new JLabel(utilisateur.getTelephone());
        lblPhone.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel lblPasswordTitle = new JLabel("Mot de passe:");
        lblPasswordTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblPasswordTitle.setForeground(new Color(0, 51, 153));
        JLabel lblPassword = new JLabel("********");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy = 0;
        profilePanel.add(lblNameTitle, gbc);
        gbc.gridx = 1;
        profilePanel.add(lblName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        profilePanel.add(lblRoleTitle, gbc);
        gbc.gridx = 1;
        profilePanel.add(lblRole, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        profilePanel.add(lblEmailTitle, gbc);
        gbc.gridx = 1;
        profilePanel.add(lblEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        profilePanel.add(lblPhoneTitle, gbc);
        gbc.gridx = 1;
        profilePanel.add(lblPhone, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        profilePanel.add(lblPasswordTitle, gbc);
        gbc.gridx = 1;
        profilePanel.add(lblPassword, gbc);

        add(profilePanel, BorderLayout.CENTER);
    }
}
