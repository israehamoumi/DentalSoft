package ma.dentalSoft.dao.implementations;


import ma.dentalSoft.model.Act;
import ma.dentalSoft.dao.ActRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActRepositoryImpl implements ActRepository {

    private final List<Act> actes = new ArrayList<>();

    @Override
    public void ajouter(Act act) {
        actes.add(act);
    }

    @Override
    public void mettreAJour(Act act) {
        Optional<Act> existingAct = actes.stream()
                .filter(a -> a.getId().equals(act.getId()))
                .findFirst();
        existingAct.ifPresent(a -> {
            a.setNomActe(act.getNomActe());
            a.setDescription(act.getDescription());
            a.setCout(act.getCout());
        });
    }

    @Override
    public void supprimer(Long id) {
        actes.removeIf(a -> a.getId().equals(id));
    }

    @Override
    public Act trouverParId(Long id) {
        return actes.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Act> listerTous() {
        return new ArrayList<>(actes);
    }
}
