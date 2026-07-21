package net.ent.etnc.badminton.models.services.crud.impl;

import jakarta.transaction.Transactional;
import net.ent.etnc.badminton.models.entity.Badiste;
import net.ent.etnc.badminton.models.entity.Classement;
import net.ent.etnc.badminton.models.entity.EntitiesFactory;
import net.ent.etnc.badminton.models.entity.communs.exceptions.ValidException;
import net.ent.etnc.badminton.models.entity.references.CategorieAge;
import net.ent.etnc.badminton.models.entity.references.Discipline;
import net.ent.etnc.badminton.models.entity.references.SerieClassement;
import net.ent.etnc.badminton.models.entity.references.Sexe;
import net.ent.etnc.badminton.models.entity.dto.TopClassementDTO;
import net.ent.etnc.badminton.models.repositories.BadisteRepository;
import net.ent.etnc.badminton.models.services.crud.BadisteService;
import net.ent.etnc.badminton.models.services.crud.common.CRUDServiceImpl;
import net.ent.etnc.badminton.models.services.crud.common.exceptions.ServiceException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BadisteServiceImpl extends CRUDServiceImpl<Badiste, BadisteRepository> implements BadisteService {

    public BadisteServiceImpl(BadisteRepository badisteRepository) {
        this.monrepo = badisteRepository;
    }

    @Override
    public List<Badiste> rechercherParNom(String nom) throws ServiceException {
        try {
            return this.monrepo.findByNom(nom);
        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<TopClassementDTO> recupererTopParDiscipline(Discipline discipline, int nb) throws ServiceException {
        try {
            return this.monrepo.recupererTopParDiscipline(discipline, PageRequest.of(0, nb));
        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Badiste findByIdWithClassements(Long id) throws ServiceException {
        try {
            Optional<Badiste> optionalBadiste = this.monrepo.findByIdWithClassements(id);
            if (optionalBadiste.isEmpty()) throw new ServiceException("L'objet n'a pas été trouvé");
            return optionalBadiste.get();
        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Badiste ajouterClassementsDepuisPoints(Long idBadiste, Integer pointsSimple,
                                                  Integer pointsDouble, Integer pointsMixte) throws ServiceException {
        try {
            Badiste badiste = this.findByIdWithClassements(idBadiste);
            Sexe sexe = badiste.getSexe();

            if (pointsSimple != null) {
                Discipline disciplineSimple = (sexe == Sexe.HOMME) ? Discipline.SH : Discipline.SD;
                Classement classementSimple = EntitiesFactory.createClassement(pointsSimple, disciplineSimple);
                badiste.addClassement(classementSimple);
            }

            if (pointsDouble != null) {
                Discipline disciplineDouble = (sexe == Sexe.HOMME) ? Discipline.DH : Discipline.DD;
                Classement classementDouble = EntitiesFactory.createClassement(pointsDouble, disciplineDouble);
                badiste.addClassement(classementDouble);
            }

            if (pointsMixte != null) {
                Classement classementMixte = EntitiesFactory.createClassement(pointsMixte, Discipline.DX);
                badiste.addClassement(classementMixte);
            }

            return this.save(badiste);
        } catch (ValidException | ServiceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Badiste> findBadistesByCategorieAge(CategorieAge categorieAge) throws ServiceException {
        try {
            return this.monrepo.findBadisteByCategorieAge(categorieAge);
        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Badiste deleteBadisteByNumeroLicence(String numeroLicence) throws ServiceException {
        try {
            return this.monrepo.deleteBadisteByNumeroLicence(numeroLicence);
        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Map<CategorieAge, List<Badiste>> badistesByCategorieAge() throws ServiceException {
        try {
            List<Badiste> badistes = this.monrepo.findAll();
            Map<CategorieAge, List<Badiste>> resultat = new HashMap<>();

            for (Badiste badiste : badistes) {
                CategorieAge categorie = badiste.getCategorieAge();

                resultat.putIfAbsent(categorie, new ArrayList<>());
                resultat.get(categorie).add(badiste);
            }

            return resultat;
        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Map<SerieClassement, Double> findPourcentageBadisteParSerieClassement() throws ServiceException {
        try {
            List<Badiste> badistes = monrepo.findAllWithClassements();

            Map<SerieClassement, Integer> compteur = new HashMap<>();
            int total = 0;
            for (Badiste badiste : badistes) {
                for (Classement classement : badiste.getClassements()) {
                    SerieClassement serie = classement.getSerie();
                    compteur.put(serie, compteur.getOrDefault(serie, 0) + 1);
                    total++;
                }
            }
            Map<SerieClassement, Double> resultat = new HashMap<>();
            for (SerieClassement serie : compteur.keySet()) {
                double pourcentage = compteur.get(serie) * 100.0 / total;
                resultat.put(serie, pourcentage);
            }
            return resultat;
        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Badiste> lesBadisteParDisciplineEtSerieClassement(
            Discipline discipline,
            SerieClassement serie) throws ServiceException {
        try {
            return monrepo.findByDisciplineAndSerie(discipline, serie);
        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
