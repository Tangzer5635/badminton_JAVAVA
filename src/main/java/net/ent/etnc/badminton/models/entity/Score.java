package net.ent.etnc.badminton.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

/**
 * Score d'un badiste sur un match (jusqu'à 3 sets).
 * Classe VALEUR (pas une entité) : elle est embarquée dans la Map
 * scoreMatch de Match via @ElementCollection.
 * Un set se joue en 21 points, prolongations jusqu'à 30 maximum.
 */
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE) // utilisé par EntitiesFactory
@EqualsAndHashCode(of = {"scoreSet1", "scoreSet2", "scoreSet3"})
@ToString(of = {"scoreSet1", "scoreSet2", "scoreSet3"})
public class Score {

    @Getter
    @Setter
    @NotNull(message = "scoreSet1 ne doit pas être null")
    @PositiveOrZero(message = "scoreSet1 doit être positif ou nul")
    @Max(value = 30, message = "scoreSet1 ne peut pas dépasser 30")
    @Column(name = "score_set1", nullable = false)
    private Integer scoreSet1;

    @Getter
    @Setter
    @NotNull(message = "scoreSet2 ne doit pas être null")
    @PositiveOrZero(message = "scoreSet2 doit être positif ou nul")
    @Max(value = 30, message = "scoreSet2 ne peut pas dépasser 30")
    @Column(name = "score_set2", nullable = false)
    private Integer scoreSet2;

    // Le 3e set est optionnel (joué uniquement en cas d'égalité 1-1)
    @Getter
    @Setter
    @PositiveOrZero(message = "scoreSet3 doit être positif ou nul")
    @Max(value = 30, message = "scoreSet3 ne peut pas dépasser 30")
    @Column(name = "score_set3", nullable = true)
    private Integer scoreSet3;

}
