package net.ent.etnc.badminton.models.services.crud;

import net.ent.etnc.badminton.models.entity.Match;
import net.ent.etnc.badminton.models.entity.references.Discipline;
import net.ent.etnc.badminton.models.services.crud.common.CRUDService;
import net.ent.etnc.badminton.models.services.crud.common.exceptions.ServiceException;

import java.time.LocalDateTime;

public interface MatchService extends CRUDService<Match> {

    Match creerMatchSimple(Long idJoueur1, Integer s1J1, Integer s2J1, Integer s3J1, Long idJoueur2, Integer s1J2, Integer s2J2, Integer s3J2, Discipline discipline, LocalDateTime dateMatch, String lieu) throws ServiceException;
}