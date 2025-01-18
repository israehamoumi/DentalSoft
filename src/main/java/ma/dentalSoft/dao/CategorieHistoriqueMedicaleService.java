package ma.dentalSoft.dao;

import ma.dentalSoft.model.enums.CategorieHistoriqueMedicale;

import java.util.List;

public interface CategorieHistoriqueMedicaleService {
    void ajouterCategorie(CategorieHistoriqueMedicale categorie);
    void supprimerCategorie(CategorieHistoriqueMedicale categorie);
    List<CategorieHistoriqueMedicale> listerCategories();
}
