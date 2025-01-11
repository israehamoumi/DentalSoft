package ma.dentalSoft;

import ma.dentalSoft.presentation.vue.LoginView;

public class DentalSoftApplication {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }
}
