package net.ent.etnc.badminton.batchs.badiste;

import net.ent.etnc.badminton.models.entity.Badiste;
import net.ent.etnc.badminton.models.entity.EntitiesFactory;
import net.ent.etnc.badminton.models.entity.references.Sexe;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BadisteProcessor implements ItemProcessor<BadisteLineCSV, Badiste> {

    @Override
    public @Nullable Badiste process(BadisteLineCSV item) throws Exception {

        if (item.nom() == null || item.prenom() == null) return null;

        // Parser date et sexe depuis les String
        LocalDate dateNaissance = LocalDate.parse(item.dateNaissance());
        Sexe sexe = Sexe.valueOf(item.sexe());

        // Crée le badiste AVEC ses classements (s'il y a des points)
        return EntitiesFactory.createBadiste(
                item.nom(),
                item.prenom(),
                dateNaissance,
                item.numeroLicence(),
                item.club(),
                sexe,
                item.pointsSimple(),     // peut être null
                item.pointsDouble(),     // peut être null
                item.pointsMixte()       // peut être null
        );
    }
}
