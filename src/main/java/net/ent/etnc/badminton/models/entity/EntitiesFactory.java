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

    /**
     * Crée un Badiste avec ses classements (si points fournis).
     * Les classements sont créés automatiquement basés sur le sexe et les points du CSV.
     */
    public static Badiste createBadiste(String nom, String prenom, LocalDate dateNaissance,
                                        String numeroLicence, String club, Sexe sexe,
                                        Integer pointsSimple, Integer pointsDouble, Integer pointsMixte) throws ValidException {
        Badiste badiste = new Badiste();
        // les setters
        badiste.setNom(nom);
        badiste.setPrenom(prenom);
        badiste.setDateNaissance(dateNaissance);
        badiste.setNumeroLicence(numeroLicence);
        badiste.setClub(club); // nullable : licence sans club possible
        badiste.setSexe(sexe);
        // La categorie n'est PAS saisie : elle est derivee de la date de naissance (cf. sujet)
        if (dateNaissance != null) {
            badiste.setCategorieAge(CategorieAge.fromAnneeNaissance(
                    dateNaissance.getYear(), Year.now().getValue()));
        }

        // Créer les classements basés sur les points (optionnels) AVANT validation
        if (pointsSimple != null) {
            Discipline disciplineSimple = (sexe == Sexe.HOMME) ? Discipline.SH : Discipline.SD;
            Classement classementSimple = createClassement(pointsSimple, disciplineSimple);
            badiste.addClassement(classementSimple);
        }

        if (pointsDouble != null) {
            Discipline disciplineDouble = (sexe == Sexe.HOMME) ? Discipline.DH : Discipline.DD;
            Classement classementDouble = createClassement(pointsDouble, disciplineDouble);
            badiste.addClassement(classementDouble);
        }

        if (pointsMixte != null) {
            Classement classementMixte = createClassement(pointsMixte, Discipline.DX);
            badiste.addClassement(classementMixte);
        }

        // Valider APRÈS avoir ajouté les classements
        ValidUtils.validate(badiste);
        
        return badiste;
    }

    /**
     * Version simple : crée juste un Badiste sans classements.
     * Utilisée pour les tests ou la création manuelle.
     */
    public static Badiste createBadiste(String nom, String prenom, LocalDate dateNaissance,
                                        String numeroLicence, String club, Sexe sexe) throws ValidException {
        return createBadiste(nom, prenom, dateNaissance, numeroLicence, club, sexe, null, null, null);
    }

    public static Classement createClassement(int points, Discipline discipline) throws ValidException {
        Classement classement = new Classement();
        // les setters
        classement.setPoints(points); // recalcule automatiquement la serie via recalcSerie()
        classement.setDiscipline(discipline);
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
        // Le match est planifie a l'avance (cf. sujet)
        match.setStatut(StatutMatch.PLANIFIE);
        ValidUtils.validate(match);
        return match;
    }

}
