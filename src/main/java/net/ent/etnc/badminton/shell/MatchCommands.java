package net.ent.etnc.badminton.shell;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.ent.etnc.badminton.models.entity.EntitiesFactory;
import net.ent.etnc.badminton.models.entity.Match;
import net.ent.etnc.badminton.models.entity.Score;
import net.ent.etnc.badminton.models.entity.dto.BadisteDTO;
import net.ent.etnc.badminton.models.entity.references.Discipline;
import net.ent.etnc.badminton.models.entity.references.SerieClassement;
import net.ent.etnc.badminton.models.facades.MatchFacade;
import net.ent.etnc.badminton.models.facades.exceptions.FacadeMetierException;
import net.ent.etnc.badminton.shell.commons.ShellHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ShellComponent
@RequiredArgsConstructor
public class MatchCommands {

    @NonNull
    private final ShellHelper shellHelper;

    @NonNull
    private final MatchFacade matchFacade;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @ShellMethod(key = "match-add-simple", value = "Crée un match simple")
    public void ajouterMatchSimple(
            @ShellOption(value = "--j1") Long idJoueur1,
            @ShellOption(value = "--s1j1") Integer s1j1,
            @ShellOption(value = "--s2j1") Integer s2j1,
            @ShellOption(value = "--s3j1", defaultValue = "") String s3j1Str,
            @ShellOption(value = "--j2") Long idJoueur2,
            @ShellOption(value = "--s1j2") Integer s1j2,
            @ShellOption(value = "--s2j2") Integer s2j2,
            @ShellOption(value = "--s3j2", defaultValue = "") String s3j2Str,
            @ShellOption(value = "--discipline") String disciplineStr,
            @ShellOption(value = "--dateMatch") String dateMatchStr,
            @ShellOption(value = "--lieu") String lieu) {
        try {
            Discipline discipline = Discipline.valueOf(disciplineStr.toUpperCase());
            if (!discipline.isSimple()) {
                shellHelper.printWarning("Discipline doit etre SH ou SD");
                return;
            }

            LocalDateTime dateMatch = LocalDateTime.parse(dateMatchStr, DATE_FORMATTER);

            Integer s3j1 = s3j1Str.isEmpty() ? null : Integer.parseInt(s3j1Str);
            Integer s3j2 = s3j2Str.isEmpty() ? null : Integer.parseInt(s3j2Str);

            try {
                Match match = matchFacade.creerMatchSimple(
                        idJoueur1, s1j1, s2j1, s3j1,
                        idJoueur2, s1j2, s2j2, s3j2,
                        discipline, dateMatch, lieu);

                shellHelper.printSuccess("Match cree ID " + match.getId());
                shellHelper.printInfo("J1 (" + idJoueur1 + ") : " + s1j1 + "-" + s2j1 + (s3j1 != null ? "-" + s3j1 : ""));
                shellHelper.printInfo("J2 (" + idJoueur2 + ") : " + s1j2 + "-" + s2j2 + (s3j2 != null ? "-" + s3j2 : ""));


            } catch (DataIntegrityViolationException e) {
                shellHelper.printError("Un match existe déjà pour cette discipline, cette date et ce lieu.");
            }


        } catch (Exception e) {
            shellHelper.printError("Erreur : " + e.getMessage());
        }
    }

    @ShellMethod(key = "match-3sets", value = "Liste les matchs joués en 3 sets")
    public void afficherMatchsEn3Sets() {
        try {
            var matchs = matchFacade.lesMatchsJoueEnTrioSet();

            if (matchs.isEmpty()) {
                shellHelper.printWarning("Aucun match joué en 3 sets.");
                return;
            }

            shellHelper.printSuccess("Matchs joués en 3 sets :");

            for (Match match : matchs) {
                shellHelper.printInfo(
                        "ID : " + match.getId()
                                + " | " + match.getDiscipline()
                                + " | " + match.getDateMatch()
                                + " | " + match.getLieu()
                );
            }

        } catch (FacadeMetierException e) {
            shellHelper.printError(e.getMessage());
        }
    }

    @ShellMethod(key = "match-add", value = "Ajoute un badiste à un match simple")
    public void ajouterMatchSimple(
            @ShellOption(value = "--idBadiste") Long idBadiste,
            @ShellOption(value = "--s1") Integer s1,
            @ShellOption(value = "--s2") Integer s2,
            @ShellOption(value = "--s3", defaultValue = "") String s3Str,
            @ShellOption(value = "--discipline") String disciplineStr,
            @ShellOption(value = "--dateMatch") String dateMatchStr,
            @ShellOption(value = "--lieu") String lieu) {

        try {
            Discipline discipline = Discipline.valueOf(disciplineStr.toUpperCase());

            if (!discipline.isSimple()) {
                shellHelper.printWarning("La discipline doit être SH ou SD.");
                return;
            }

            LocalDateTime dateMatch = LocalDateTime.parse(dateMatchStr, DATE_FORMATTER);

            Integer s3 = s3Str.isEmpty() ? null : Integer.parseInt(s3Str);

            BadisteDTO badisteDTO = new BadisteDTO();
            badisteDTO.setId(idBadiste);

            Score score = EntitiesFactory.createScore(s1, s2, s3);

            Match match = matchFacade.ajouterMatchSimple(
                    discipline,
                    dateMatch,
                    lieu,
                    badisteDTO,
                    score
            );
            shellHelper.printSuccess("Match créé avec l'ID " + match.getId());
        } catch (FacadeMetierException e) {
            shellHelper.printError(e.getMessage());
        } catch (Exception e) {
            shellHelper.printError("Erreur : " + e.getMessage());
        }
    }

    @ShellMethod(key = "match-serie", value = "Liste les matchs avec au moins un badiste d'une série donnée")
    public void afficherMatchsParSerie(
            @ShellOption(value = "--serie") String serieStr) {

        try {
            SerieClassement serie = SerieClassement.valueOf(serieStr.toUpperCase());

            var matchs = matchFacade.lesMatchsOuAumoinsUnBadisteEstClasse(serie);

            if (matchs.isEmpty()) {
                shellHelper.printWarning("Aucun match trouvé pour la série " + serie);
                return;
            }

            shellHelper.printSuccess("Matchs contenant un badiste de série " + serie + " :");

            for (Match match : matchs) {
                shellHelper.printInfo(
                        "ID : " + match.getId()
                                + " | " + match.getDiscipline()
                                + " | " + match.getDateMatch()
                                + " | " + match.getLieu()
                );
            }

        } catch (IllegalArgumentException e) {
            shellHelper.printError("Série invalide.");
        } catch (FacadeMetierException e) {
            shellHelper.printError(e.getMessage());
        }
    }
}
