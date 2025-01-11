package ma.dentalSoft.dao;

import ma.dentalSoft.model.Utilisateur;
import java.util.List;

public interface UtilisateurRepo {
    void save(Utilisateur utilisateur);
    Utilisateur findById(int id);
    List<Utilisateur> findAll();
    void update(Utilisateur utilisateur);
    void delete(int id);
    boolean validateCredentials(String username, String password);
}
