package net.ent.etnc.badminton.models.services.crud;

import net.ent.etnc.badminton.models.entity.Badiste;
import net.ent.etnc.badminton.models.entity.dto.BadisteDTO;
import net.ent.etnc.badminton.models.entity.references.CategorieAge;
import net.ent.etnc.badminton.models.entity.references.Discipline;
import net.ent.etnc.badminton.models.entity.dto.TopClassementDTO;
import net.ent.etnc.badminton.models.entity.references.SerieClassement;
import net.ent.etnc.badminton.models.services.crud.common.CRUDService;
import net.ent.etnc.badminton.models.services.crud.common.exceptions.ServiceException;

import java.util.List;
import java.util.Map;

public interface BadisteService extends CRUDService<Badiste> {

    List<Badiste> rechercherParNom(String nom) throws ServiceException;

    List<TopClassementDTO> recupererTopParDiscipline(Discipline discipline, int nb) throws ServiceException;

    Badiste findByIdWithClassements(Long id) throws ServiceException;

    Badiste ajouterClassementsDepuisPoints(Long idBadiste, Integer pointsSimple,
                                            Integer pointsDouble, Integer pointsMixte) throws ServiceException;

    List<Badiste> findBadistesByCategorieAge(CategorieAge categorieAge) throws ServiceException;

    Badiste deleteBadisteByNumeroLicence(String numeroLicence) throws ServiceException;

    Map<CategorieAge, List<Badiste>> badistesByCategorieAge() throws ServiceException;

    Map<SerieClassement, Double> findPourcentageBadisteParSerieClassement() throws ServiceException;

    List<Badiste> lesBadisteParDisciplineEtSerieClassement(Discipline discipline, SerieClassement serie) throws ServiceException;

    Badiste ajouterBadiste(BadisteDTO badiste) throws ServiceException;
}
