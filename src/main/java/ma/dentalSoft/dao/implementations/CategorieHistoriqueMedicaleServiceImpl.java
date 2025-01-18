package ma.dentalSoft.dao.implementations;

import ma.dentalSoft.dao.CategorieHistoriqueMedicaleService;
import ma.dentalSoft.model.enums.CategorieHistoriqueMedicale;

import java.util.ArrayList;
import java.util.List;

public class CategorieHistoriqueMedicaleServiceImpl implements CategorieHistoriqueMedicaleService {
    private List<CategorieHistoriqueMedicale> categories = new ArrayList<>();

    @Override
    public void ajouterCategorie(CategorieHistoriqueMedicale categorie) {
        if (!categories.contains(categorie)) {
            categories.add(categorie);
        }
    }

    @Override
    public void supprimerCategorie(CategorieHistoriqueMedicale categorie) {
        categories.remove(categorie);
    }

    @Override
    public List<CategorieHistoriqueMedicale> listerCategories() {
        return new ArrayList<>(categories);
    }
}