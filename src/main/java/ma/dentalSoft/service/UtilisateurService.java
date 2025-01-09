package ma.dentalSoft.service;

import ma.dentalSoft.dao.UtilisateurRepo;
import ma.dentalSoft.dao.implementations.UtilisateurRepoImpl;
import ma.dentalSoft.model.Utilisateur;

public class UtilisateurService {
    private final UtilisateurRepo utilisateurRepo = new UtilisateurRepoImpl();

    public void addUtilisateur(Utilisateur utilisateur) {
        utilisateurRepo.save(utilisateur);
    }

    public Utilisateur getUtilisateur(int id) {
        return utilisateurRepo.findById(id);
    }

    public boolean validateLogin(String username, String password) {
        return utilisateurRepo.validateCredentials(username, password);
    }
}
