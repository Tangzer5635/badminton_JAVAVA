package net.ent.etnc.badminton.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import net.ent.etnc.badminton.models.entity.references.Discipline;
import net.ent.etnc.badminton.models.entity.references.StatutMatch;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "MATCH",
        // Le statut NE fait PAS partie de l'identité du match : il change au cours de sa vie
        uniqueConstraints = @UniqueConstraint(name = "uk___match___discipline__date_match__lieu", columnNames = {"discipline", "date_match", "lieu"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, of = {"discipline", "dateMatch", "lieu"})
@ToString(callSuper = true, of = {"discipline", "dateMatch", "lieu", "statut"})
public class Match extends AbstractEntity {

    //<Entite, Valeur embarquée> : le score de chaque badiste participant.
    // Score est @Embeddable : ses colonnes (score_set1/2/3) viennent de la classe Score,
    // donc PAS de @Column ici (réservé aux valeurs basiques type Integer).
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "match_badiste",
            joinColumns = @JoinColumn(name = "match_id",
                    foreignKey = @ForeignKey(name = "fk___match_badiste___match_id")))
    @MapKeyJoinColumn(name = "badiste_id",
            foreignKey = @ForeignKey(name = "fk___match_badiste___badiste_id"))
    private Map<Badiste, Score> scoreMatch = new HashMap<>();

    @Getter
    @Setter
    @NotNull(message = "dateMatch ne doit pas être null")
    @Column(name = "date_match", nullable = false)
    private LocalDateTime dateMatch;

    @Getter
    @Setter
    @NotNull(message = "lieu ne doit pas être null")
    @NotEmpty(message = "lieu ne doit pas être vide")
    @NotBlank(message = "lieu doit contenir des caractères lisibles")
    @Length(min = 3, max = 50, message = "lieu doit avoir entre 3 et 50 caractères")
    @Column(name = "lieu", length = 50, nullable = false)
    private String lieu;

    @Getter
    @Setter
    @NotNull(message = "statut ne doit pas être null")
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", length = 10, nullable = false)
    private StatutMatch statut;

    @Getter
    @Setter
    @NotNull(message = "discipline ne doit pas être null")
    @Enumerated(EnumType.STRING)
    @Column(name = "discipline", length = 5, nullable = false)
    private Discipline discipline;

    public Map<Badiste, Score> getScoreMatch() {
        return Collections.unmodifiableMap(scoreMatch);
    }

    public void addScore(@NotNull Badiste badiste, @NotNull Score score) {
        this.scoreMatch.put(badiste, score);
    }

    public void removeScore(@NotNull Badiste badiste) {
        this.scoreMatch.remove(badiste);
    }

}
