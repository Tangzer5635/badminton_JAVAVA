package net.ent.etnc.badminton.models.facades;

import net.ent.etnc.badminton.models.entity.Match;
import net.ent.etnc.badminton.models.entity.references.Discipline;
import net.ent.etnc.badminton.models.entity.references.SerieClassement;
import net.ent.etnc.badminton.models.facades.exceptions.FacadeMetierException;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchFacade {

    List<Match> findAllMatchs() throws FacadeMetierException;

    void saveAllMatchs(List<Match> matchs) throws FacadeMetierException;


    Match creerMatchSimple(Long idJoueur1, Integer s1J1, Integer s2J1, Integer s3J1,
                           Long idJoueur2, Integer s1J2, Integer s2J2, Integer s3J2,
                           Discipline discipline, LocalDateTime dateMatch, String lieu) throws FacadeMetierException;

    List<Match> lesMatchsOuAumoinsUnBadisteEstClasse(SerieClassement serie) throws FacadeMetierException;

    Match ajouterMatchDouble(Discipline discipline, LocalDateTime dateMatch, String lieu,
                             Long idJoueur1, Long idJoueur2, Long idJoueur3, Long idJoueur4,
                             Integer s1, Integer s2, Integer s3) throws FacadeMetierException;

    List<Match> lesMatchsJoueEnTrioSet() throws FacadeMetierException;
}
