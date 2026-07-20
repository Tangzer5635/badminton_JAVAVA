package net.ent.etnc.badminton.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import net.ent.etnc.badminton.models.entity.references.Discipline;
import net.ent.etnc.badminton.models.entity.references.SerieClassement;

@Entity
@Table(name = "CLASSEMENT",
        // Un badiste n'a qu'UN classement par discipline.
        // badiste_id est la colonne de jointure posée par @JoinColumn côté Badiste.
        uniqueConstraints = @UniqueConstraint(name = "uk___classement___badiste_id__discipline", columnNames = {"badiste_id", "discipline"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, of = {"serie", "discipline", "points"})
@ToString(callSuper = true, of = {"points", "serie", "discipline"})
public class Classement extends AbstractEntity {

    // La cote FFBAD s'étend de 400 (NC) à 3000 (cf. sujet)
    @Getter
    @Min(value = 400, message = "points doit être au minimum 400")
    @Max(value = 3000, message = "points ne peut pas dépasser 3000")
    @Column(name = "points", nullable = false)
    private int points;

    // La série est TOUJOURS dérivée des points (cohérence avec l'énumération, cf. sujet)
    @Getter
    @Setter
    @NotNull(message = "serie ne doit pas être null")
    @Enumerated(EnumType.STRING)
    @Column(name = "serie", length = 5, nullable = false)
    private SerieClassement serie;

    @Getter
    @Setter
    @NotNull(message = "discipline ne doit pas être null")
    @Enumerated(EnumType.STRING)
    @Column(name = "discipline", length = 5, nullable = false)
    private Discipline discipline;

    /**
     * Modifier les points recalcule automatiquement la série (cf. DCLAC : setPoints + recalcSerie).
     */
    public void setPoints(int points) {
        this.points = points;
        recalcSerie();
    }

    /**
     * Recalcule la série à partir des points via l'énumération.
     */
    protected void recalcSerie() {
        this.serie = SerieClassement.fromPoints(this.points);
    }

}
