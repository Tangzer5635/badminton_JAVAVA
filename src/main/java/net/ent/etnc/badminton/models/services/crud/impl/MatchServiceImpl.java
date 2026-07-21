package net.ent.etnc.badminton.models.services.crud.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.ent.etnc.badminton.models.entity.Badiste;
import net.ent.etnc.badminton.models.entity.EntitiesFactory;
import net.ent.etnc.badminton.models.entity.Match;
import net.ent.etnc.badminton.models.entity.Score;
import net.ent.etnc.badminton.models.entity.communs.exceptions.ValidException;
import net.ent.etnc.badminton.models.entity.dto.BadisteDTO;
import net.ent.etnc.badminton.models.entity.references.Discipline;
import net.ent.etnc.badminton.models.entity.references.Sexe;
import net.ent.etnc.badminton.models.entity.references.StatutMatch;
import net.ent.etnc.badminton.models.repositories.MatchRepository;
import net.ent.etnc.badminton.models.services.crud.BadisteService;
import net.ent.etnc.badminton.models.services.crud.MatchService;
import net.ent.etnc.badminton.models.services.crud.common.CRUDServiceImpl;
import net.ent.etnc.badminton.models.services.crud.common.exceptions.ServiceException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MatchServiceImpl extends CRUDServiceImpl<Match, MatchRepository> implements MatchService {

    private final BadisteService badisteCRUDService;

    public MatchServiceImpl(MatchRepository matchRepository,
                            BadisteService badisteCRUDService) {
        this.monrepo = matchRepository;
        this.badisteCRUDService = badisteCRUDService;
    }


    @Override
    public Match creerMatchSimple(Long idJoueur1, Integer s1J1, Integer s2J1, Integer s3J1, Long idJoueur2, Integer s1J2, Integer s2J2, Integer s3J2, Discipline discipline, LocalDateTime dateMatch, String lieu) throws ServiceException {
        try {
            Badiste joueur1 = badisteCRUDService.findById(idJoueur1);
            Badiste joueur2 = badisteCRUDService.findById(idJoueur2);
            Match match = EntitiesFactory.createMatch(discipline, dateMatch, lieu);
            Score scoreJoueur1 = EntitiesFactory.createScore(s1J1, s2J1, s3J1);
            Score scoreJoueur2 = EntitiesFactory.createScore(s1J2, s2J2, s3J2);
            match.addScore(joueur1, scoreJoueur1);
            match.addScore(joueur2, scoreJoueur2);
            match.setStatut(StatutMatch.TERMINE);
            return this.monrepo.save(match);
        } catch (IllegalArgumentException | ValidException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Match> findMatchIn3Sets() throws ServiceException {
        return this.monrepo.findMatchIn3Sets();
    }

    @Override
    public Match ajouterMatchSimple(Discipline discipline,
                                    LocalDateTime dateMatch,
                                    String lieu,
                                    BadisteDTO badisteDTO,
                                    Score score) throws ServiceException {
        try {
            Badiste badiste = badisteCRUDService.findById(badisteDTO.getId());

            Match match = EntitiesFactory.createMatch(discipline, dateMatch, lieu);
            match.addScore(badiste, score);
            match.setStatut(StatutMatch.TERMINE);

            return monrepo.save(match);

        } catch (IllegalArgumentException | ValidException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}