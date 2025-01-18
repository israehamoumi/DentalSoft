package ma.dentalSoft.dao.implementations;

import ma.dentalSoft.dao.RisqueService;
import ma.dentalSoft.model.enums.Risque;

import java.util.ArrayList;
import java.util.List;

public class RisqueServiceImpl implements RisqueService {
    private List<Risque> risques = new ArrayList<>();

    @Override
    public void ajouterRisque(Risque risque) {
        if (!risques.contains(risque)) {
            risques.add(risque);
        }
    }

    @Override
    public void supprimerRisque(Risque risque) {
        risques.remove(risque);
    }

    @Override
    public List<Risque> listerRisques() {
        return new ArrayList<>(risques);
    }
}
