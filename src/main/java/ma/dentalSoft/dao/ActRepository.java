package ma.dentalSoft.dao;

import ma.dentalSoft.model.Act;

import java.util.List;

public interface ActRepository {
    void ajouter(Act act);
    void mettreAJour(Act act);
    void supprimer(Long id);
    Act trouverParId(Long id);
    List<Act> listerTous();
}
