package net.ent.etnc.badminton.shell;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.ent.etnc.badminton.models.entity.references.Discipline;
import net.ent.etnc.badminton.models.facades.BadisteFacade;
import net.ent.etnc.badminton.models.entity.dto.TopClassementDTO;
import net.ent.etnc.badminton.models.facades.exceptions.FacadeMetierException;
import net.ent.etnc.badminton.shell.commons.ShellHelper;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ClassementCommands {

    @NonNull
    private final ShellHelper shellHelper;

    @NonNull
    private final BadisteFacade badisteFacade;

    @ShellMethod(key = "classement-top", value = "Top N d'une discipline")
    public void afficherTopClassement(
            @ShellOption(value = {"--discipline", "-d"}) String disciplineStr,
            @ShellOption(value = {"--nb", "-n"}, defaultValue = "10") int nb) {
        try {
            Discipline discipline = Discipline.valueOf(disciplineStr.toUpperCase());
            List<TopClassementDTO> top = badisteFacade.recupererTopClassement(discipline, nb);
            
            if (top.isEmpty()) {
                shellHelper.printWarning("Aucun classement pour " + discipline);
                return;
            }

            shellHelper.printSuccess("Top " + nb + " - " + discipline);
            int rang = 1;
            for (TopClassementDTO player : top) {
                shellHelper.printInfo(rang + ". " + player.nom() + " " + player.prenom()
                    + " - " + player.points() + " pts");
                rang++;
            }
        } catch (IllegalArgumentException e) {
            shellHelper.printError("Discipline invalide");
        } catch (FacadeMetierException e) {
            shellHelper.printError("Erreur : " + e.getMessage());
        }
    }
}
