package net.ent.etnc.badminton.models.facades.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.ent.etnc.badminton.models.entity.Badiste;
import net.ent.etnc.badminton.models.entity.dto.BadisteDTO;
import net.ent.etnc.badminton.models.entity.references.CategorieAge;
import net.ent.etnc.badminton.models.entity.references.Discipline;
import net.ent.etnc.badminton.models.entity.references.SerieClassement;
import net.ent.etnc.badminton.models.facades.BadisteFacade;
import net.ent.etnc.badminton.models.entity.dto.TopClassementDTO;
import net.ent.etnc.badminton.models.facades.exceptions.FacadeMetierException;
import net.ent.etnc.badminton.models.services.crud.BadisteService;
import net.ent.etnc.badminton.models.services.crud.common.exceptions.ServiceException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BadisteFacadeImpl implements BadisteFacade {

    @NonNull
    private final BadisteService badisteCRUDService;

    @Override
    public List<Badiste> rechercherParNom(String nom) throws FacadeMetierException {
        try {
            return badisteCRUDService.rechercherParNom(nom);
        } catch (ServiceException e) {
            throw new FacadeMetierException(e.getMessage(), e);
        }
    }

    @Override
    public List<TopClassementDTO> recupererTopClassement(Discipline discipline, int nb) throws FacadeMetierException {
        try {
            return badisteCRUDService.recupererTopParDiscipline(discipline, nb);
        } catch (ServiceException e) {
            throw new FacadeMetierException(e.getMessage(), e);
        }
    }

    @Override
    public Badiste ajouterClassementsDepuisPoints(Long idBadiste, Integer pointsSimple,
                                                  Integer pointsDouble, Integer pointsMixte) throws FacadeMetierException {
        try {
            return badisteCRUDService.ajouterClassementsDepuisPoints(idBadiste, pointsSimple, pointsDouble, pointsMixte);
        } catch (ServiceException e) {
            throw new FacadeMetierException(e.getMessage(), e);
        }
    }

    @Override
    public List<Badiste> lesBadisteParCategorieAge(CategorieAge categorieAge) throws FacadeMetierException {
        try {
            return badisteCRUDService.findBadistesByCategorieAge(categorieAge);
        } catch (ServiceException e) {
            throw new FacadeMetierException(e.getMessage(), e);
        }
    }

    @Override
    public Badiste supprimerBadisteParNumeroLicence(String numeroLicence) throws FacadeMetierException {
        try {
            return badisteCRUDService.deleteBadisteByNumeroLicence(numeroLicence);
        } catch (ServiceException e) {
            throw new FacadeMetierException(e.getMessage(), e);
        }
    }

    @Override
    public Map<CategorieAge, List<Badiste>> lesBadisteParCategorieAge() throws FacadeMetierException {
        try {
            return badisteCRUDService.badistesByCategorieAge();
        } catch (ServiceException e) {
            throw new FacadeMetierException(e.getMessage(), e);
        }
    }

    @Override
    public Map<SerieClassement, Double> lePourcentageBadisteParSerieClassement() throws FacadeMetierException {
        try {
            return badisteCRUDService.findPourcentageBadisteParSerieClassement();
        } catch (ServiceException e) {
            throw new FacadeMetierException(e.getMessage(), e);
        }
    }

    @Override
    public List<Badiste> lesBadisteParDisciplineEtSerieClassement(
            Discipline discipline,
            SerieClassement serie) throws FacadeMetierException {
        try {
            return badisteCRUDService.lesBadisteParDisciplineEtSerieClassement(discipline, serie);
        } catch (ServiceException e) {
            throw new FacadeMetierException(e.getMessage(), e);
        }
    }

    @Override
    public Badiste ajouterBadiste(BadisteDTO badiste) throws FacadeMetierException {
        try {
            return badisteCRUDService.ajouterBadiste(badiste);
        }catch (ServiceException e) {
            throw new FacadeMetierException(e.getMessage(), e);
        }
    }


}
