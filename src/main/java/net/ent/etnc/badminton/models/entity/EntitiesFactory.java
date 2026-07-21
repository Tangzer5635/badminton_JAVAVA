package net.ent.etnc.badminton.models.entity;

import lombok.NoArgsConstructor;
import net.ent.etnc.badminton.models.entity.communs.ValidUtils;
import net.ent.etnc.badminton.models.entity.communs.exceptions.ValidException;
import net.ent.etnc.badminton.models.entity.references.CategorieAge;
import net.ent.etnc.badminton.models.entity.references.Discipline;
import net.ent.etnc.badminton.models.entity.references.SerieClassement;
import net.ent.etnc.badminton.models.entity.references.Sexe;
import net.ent.etnc.badminton.models.entity.references.StatutMatch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class EntitiesFactory {

    public static Badiste createBadisteBatch(String nom, String prenom, LocalDate dateNaissance,
                                        String numeroLicence, String club, Sexe sexe,
                                        Integer pointsSimple, Integer pointsDouble, Integer pointsMixte) throws ValidException {

        Badiste badiste = new Badiste();

        badiste.setNom(nom);
        badiste.setPrenom(prenom);
        badiste.setDateNaissance(dateNaissance);
        badiste.setNumeroLicence(numeroLicence);
        badiste.setClub(club);
        badiste.setSexe(sexe);

        if (dateNaissance != null) {
            badiste.setCategorieAge(
                    CategorieAge.fromAnneeNaissance(
                            dateNaissance.getYear(),
                            Year.now().getValue()));
        }

        badiste.addClassement(
                createClassementBatch(pointsSimple == null ? 400 : pointsSimple,
                        sexe == Sexe.HOMME ? Discipline.SH : Discipline.SD));

        badiste.addClassement(
                createClassementBatch(pointsDouble == null ? 400 : pointsDouble,
                        sexe == Sexe.HOMME ? Discipline.DH : Discipline.DD));

        badiste.addClassement(
                createClassementBatch(pointsMixte == null ? 400 : pointsMixte,
                        Discipline.DX));

        return badiste;
    }

    public static Badiste createBadiste(String nom, String prenom, LocalDate dateNaissance,
                                             String numeroLicence, String club, Sexe sexe,
                                             Integer pointsSimple, Integer pointsDouble, Integer pointsMixte) throws ValidException {
        Badiste badiste = createBadisteBatch(nom,prenom,dateNaissance,numeroLicence,club, sexe, pointsSimple, pointsDouble, pointsMixte);
        // les setters
        ValidUtils.validate(badiste);
        return badiste;
    }

    private static void ajouterClassement(Badiste badiste,
                                          Integer points,
                                          Discipline discipline) throws ValidException {

        int pointsReels = (points == null) ? 400 : points;

        Classement classement = createClassement(pointsReels, discipline);
        badiste.addClassement(classement);
    }

    public static Classement createClassementBatch(int points, Discipline discipline) throws ValidException {
        Classement classement = new Classement();
        // les setters
        classement.setPoints(points); // recalcule automatiquement la serie via recalcSerie()
        classement.setDiscipline(discipline);
        return classement;
    }

    public static Classement createClassement(int points, Discipline discipline) throws ValidException {
        Classement classement = createClassementBatch(points, discipline);
        // les setters
        ValidUtils.validate(classement);
        return classement;
    }

    public static Score createScore(Integer scoreSet1, Integer scoreSet2, Integer scoreSet3) throws ValidException {
        Score score = new Score(scoreSet1, scoreSet2, scoreSet3); // set3 optionnel (null si 2 sets)
        ValidUtils.validate(score);
        return score;
    }

    public static Match createMatch(Discipline discipline, LocalDateTime dateMatch, String lieu) throws ValidException {
        Match match = new Match();
        // les setters
        match.setDiscipline(discipline);
        match.setDateMatch(dateMatch);
        match.setLieu(lieu);
        match.setStatut(StatutMatch.PLANIFIE);
        ValidUtils.validate(match);
        return match;
    }

}
