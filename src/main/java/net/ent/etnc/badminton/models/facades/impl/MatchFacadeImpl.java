package net.ent.etnc.badminton.models.facades.impl;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import net.ent.etnc.badminton.models.entity.Badiste;
import net.ent.etnc.badminton.models.entity.EntitiesFactory;
import net.ent.etnc.badminton.models.entity.Match;
import net.ent.etnc.badminton.models.entity.Score;
import net.ent.etnc.badminton.models.entity.communs.exceptions.ValidException;
import net.ent.etnc.badminton.models.entity.dto.BadisteDTO;
import net.ent.etnc.badminton.models.entity.references.Discipline;
import net.ent.etnc.badminton.models.entity.references.SerieClassement;
import net.ent.etnc.badminton.models.entity.references.Sexe;
import net.ent.etnc.badminton.models.entity.references.StatutMatch;
import net.ent.etnc.badminton.models.facades.MatchFacade;
import net.ent.etnc.badminton.models.facades.exceptions.FacadeMetierException;
import net.ent.etnc.badminton.models.services.crud.BadisteService;
import net.ent.etnc.badminton.models.services.crud.MatchService;
import net.ent.etnc.badminton.models.services.crud.common.exceptions.ServiceException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class MatchFacadeImpl implements MatchFacade {

    @NonNull
    private final MatchService matchCRUDService;

    @Override
    public List<Match> findAllMatchs() throws FacadeMetierException {
        try {
            return matchCRUDService.findAll();
        } catch (ServiceException e) {
            throw new FacadeMetierException(e.getMessage(), e);
        }
    }

    @Override
    public void saveAllMatchs(List<Match> matchs) throws FacadeMetierException {
        try {
            matchCRUDService.saveAll(matchs);
        } catch (ServiceException e) {
            throw new FacadeMetierException(e.getMessage(), e);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Match creerMatchSimple(Long idJoueur1, Integer s1J1, Integer s2J1, Integer s3J1,
                                  Long idJoueur2, Integer s1J2, Integer s2J2, Integer s3J2,
                                  Discipline discipline, LocalDateTime dateMatch, String lieu) throws FacadeMetierException {
        try {
            return matchCRUDService.creerMatchSimple(idJoueur1, s1J1, s2J1, s3J1, idJoueur2, s1J2, s2J2, s3J2, discipline, dateMatch, lieu);
        } catch (ServiceException e) {
            throw new FacadeMetierException(e.getMessage(), e);
        }
    }

    @Override
    public List<Match> lesMatchsOuAumoinsUnBadisteEstClasse(SerieClassement serie) throws FacadeMetierException {
        //TODO
        return List.of();
    }

    @Override
    public Match ajouterMatchDouble(Discipline discipline, LocalDateTime dateMatch, String lieu, Long idJoueur1, Long idJoueur2, Long idJoueur3, Long idJoueur4, Integer s1, Integer s2, Integer s3) throws FacadeMetierException {
        //TODO
        return null;
    }

    public Match ajouterMatchSimple(Discipline discipline, LocalDateTime dateMatch, String lieu, BadisteDTO badisteDTO, Score score) throws FacadeMetierException {
        try {
            return matchCRUDService.ajouterMatchSimple(discipline,dateMatch,lieu,badisteDTO,score);
        }catch (ServiceException e) {
            throw new FacadeMetierException(e.getMessage(), e);
        }
    }

    @Override
    public List<Match> lesMatchsJoueEnTrioSet() throws FacadeMetierException {
        try {
            return matchCRUDService.findMatchIn3Sets();
        } catch (ServiceException e) {
            throw new FacadeMetierException(e.getMessage(), e);
        }
    }
}
