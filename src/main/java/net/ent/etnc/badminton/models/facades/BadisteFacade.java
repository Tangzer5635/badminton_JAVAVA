package net.ent.etnc.badminton.models.facades;

import net.ent.etnc.badminton.models.entity.Badiste;
import net.ent.etnc.badminton.models.entity.dto.BadisteDTO;
import net.ent.etnc.badminton.models.entity.references.CategorieAge;
import net.ent.etnc.badminton.models.entity.references.Discipline;
import net.ent.etnc.badminton.models.entity.references.SerieClassement;
import net.ent.etnc.badminton.models.entity.dto.TopClassementDTO;
import net.ent.etnc.badminton.models.facades.exceptions.FacadeMetierException;

import java.util.List;
import java.util.Map;

public interface BadisteFacade {

    List<Badiste> rechercherParNom(String nom) throws FacadeMetierException;
    List<TopClassementDTO> recupererTopClassement(Discipline discipline, int nb) throws FacadeMetierException;
    Badiste ajouterClassementsDepuisPoints(Long idBadiste, Integer pointsSimple,
                                           Integer pointsDouble, Integer pointsMixte) throws FacadeMetierException;
    List<Badiste> lesBadisteParCategorieAge(CategorieAge categorieAge) throws FacadeMetierException;

    Badiste supprimerBadisteParNumeroLicence(String numeroLicence) throws FacadeMetierException;

    Map<CategorieAge, List<Badiste>> lesBadisteParCategorieAge() throws FacadeMetierException;

    Map<SerieClassement, Double> lePourcentageBadisteParSerieClassement() throws FacadeMetierException;

    List<Badiste> lesBadisteParDisciplineEtSerieClassement(Discipline discipline, SerieClassement serie) throws FacadeMetierException;

    Badiste ajouterBadiste(BadisteDTO badiste) throws FacadeMetierException;
}