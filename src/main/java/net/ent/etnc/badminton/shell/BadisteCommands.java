package net.ent.etnc.badminton.shell;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.ent.etnc.badminton.models.entity.Badiste;
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
    public void importBadistes(@ShellOption(value = {"--file", "-f"}) String filePath) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("start", System.currentTimeMillis())
                    .toJobParameters();
            jobOperator.start(importBadisteJob, jobParameters);
            shellHelper.printSuccess("badistes en base");
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
}
