package net.ent.etnc.badminton.shell;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.ent.etnc.badminton.models.entity.Badiste;
import net.ent.etnc.badminton.models.entity.references.CategorieAge;
import net.ent.etnc.badminton.models.entity.references.Discipline;
import net.ent.etnc.badminton.models.entity.references.SerieClassement;
import net.ent.etnc.badminton.models.facades.BadisteFacade;
import net.ent.etnc.badminton.models.facades.exceptions.FacadeMetierException;
import net.ent.etnc.badminton.shell.commons.ShellHelper;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.Map;

@ShellComponent
@RequiredArgsConstructor
public class BadisteCommands {

    @NonNull
    private final ShellHelper shellHelper;

    @NonNull
    private final BadisteFacade badisteFacade;

    @NonNull
    private final Job importBadisteJob;

    @NonNull
    private final JobOperator jobOperator;
    @ShellMethod(key = "import", value = "Importe des badistes depuis CSV")
    public void importBadistes(
            @ShellOption(value = {"--file", "-f"}) String filePath) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("filePath", filePath)
                    .addLong("start", System.currentTimeMillis())
                    .toJobParameters();

            jobOperator.start(importBadisteJob, jobParameters);
            shellHelper.printSuccess("Badistes importés.");

        } catch (Exception e) {
            shellHelper.printError("Erreur import : " + e.getMessage());
        }
    }

    @ShellMethod(key = "badiste-search", value = "Cherche des badistes par nom")
    public void searchBadiste(@ShellOption(value = {"--nom", "-n"}) String nom) {
        try {
            List<Badiste> resultats = badisteFacade.rechercherParNom(nom);
            if (resultats.isEmpty()) {
                shellHelper.printWarning("Aucun badiste trouvé");
                return;
            }
            for (Badiste b : resultats) {
                shellHelper.printInfo("[" + b.getId() + "] " + b.getNom() + " " + b.getPrenom()
                    + " - " + b.getNumeroLicence() + " - " + b.getClub());
            }
        } catch (FacadeMetierException e) {
            shellHelper.printError("Erreur : " + e.getMessage());
        }
    }

    @ShellMethod(key = "badiste-add-classements", value = "Ajoute les classements d'un badiste")
    public void ajouterClassements(
            @ShellOption("--id") Long id,
            @ShellOption(value = "--simple", defaultValue = ShellOption.NULL) Integer simple,
            @ShellOption(value = "--double", defaultValue = ShellOption.NULL) Integer doubl,
            @ShellOption(value = "--mixte", defaultValue = ShellOption.NULL) Integer mixte) {

        try {
            Badiste badiste = badisteFacade.ajouterClassementsDepuisPoints(id, simple, doubl, mixte);
            shellHelper.printSuccess("Classements ajoutés au badiste " + badiste.getNom() + " " + badiste.getPrenom());
        } catch (FacadeMetierException e) {
            shellHelper.printError(e.getMessage());
        }
    }

    @ShellMethod(key = "badiste-categorie", value = "Liste les badistes d'une catégorie")
    public void badistesCategorie(
            @ShellOption("--categorie") String categorieStr) {

        try {
            CategorieAge categorie = CategorieAge.valueOf(categorieStr.toUpperCase());

            List<Badiste> badistes = badisteFacade.lesBadisteParCategorieAge(categorie);

            if (badistes.isEmpty()) {
                shellHelper.printWarning("Aucun badiste.");
                return;
            }

            badistes.forEach(b ->
                    shellHelper.printInfo(b.getId() + " - " + b.getNom() + " " + b.getPrenom()));

        } catch (Exception e) {
            shellHelper.printError(e.getMessage());
        }
    }

    @ShellMethod(key = "badiste-delete-licence", value = "Supprime un badiste par numéro de licence")
    public void supprimerBadiste(
            @ShellOption("--licence") String licence) {

        try {
            Badiste badiste = badisteFacade.supprimerBadisteParNumeroLicence(licence);
            shellHelper.printSuccess("Badiste supprimé : "
                    + badiste.getNom() + " " + badiste.getPrenom());
        } catch (FacadeMetierException e) {
            shellHelper.printError(e.getMessage());
        }
    }

    @ShellMethod(key = "badiste-categories", value = "Affiche les badistes par catégorie")
    public void afficherCategories() {

        try {
            var map = badisteFacade.lesBadisteParCategorieAge();

            map.forEach((categorie, liste) -> {
                shellHelper.printSuccess(categorie.name());
                liste.forEach(b ->
                        shellHelper.printInfo(" - " + b.getNom() + " " + b.getPrenom()));
            });

        } catch (FacadeMetierException e) {
            shellHelper.printError(e.getMessage());
        }
    }

    @ShellMethod(key = "badiste-pourcentage-series", value = "Pourcentage des séries")
    public void pourcentageSeries() {

        try {
            Map<SerieClassement, Double> map = badisteFacade.lePourcentageBadisteParSerieClassement();

            map.forEach((serie, pourcentage) ->
                    shellHelper.printInfo(
                            serie + " : " + String.format("%.2f %%", pourcentage)));

        } catch (FacadeMetierException e) {
            shellHelper.printError(e.getMessage());
        }
    }

    @ShellMethod(key = "badiste-discipline-serie", value = "Badistes par discipline et série")
    public void badistesDisciplineSerie(
            @ShellOption("--discipline") String disciplineStr,
            @ShellOption("--serie") String serieStr) {

        try {
            Discipline discipline = Discipline.valueOf(disciplineStr.toUpperCase());
            SerieClassement serie = SerieClassement.valueOf(serieStr.toUpperCase());

            List<Badiste> badistes =
                    badisteFacade.lesBadisteParDisciplineEtSerieClassement(discipline, serie);

            if (badistes.isEmpty()) {
                shellHelper.printWarning("Aucun résultat.");
                return;
            }

            shellHelper.printSuccess("Taille : " + badistes.size());

            badistes.forEach(b ->
                    shellHelper.printInfo(
                            b.getId() + " - " + b.getNom() + " " + b.getPrenom()));

        } catch (Exception e) {
            shellHelper.printError(e.getMessage());
        }
    }
}
