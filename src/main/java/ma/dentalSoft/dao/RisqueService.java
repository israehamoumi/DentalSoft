package ma.dentalSoft.dao;

import ma.dentalSoft.model.enums.Risque;

import java.util.List;

// Interface pour le service de gestion de Risque
public interface RisqueService {
    void ajouterRisque(Risque risque);
    void supprimerRisque(Risque risque);
    List<Risque> listerRisques();
}