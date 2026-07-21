package net.ent.etnc.badminton.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
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

    @Getter
    @Setter
    @PositiveOrZero(message = "scoreSet3 doit être positif ou nul")
    @Max(value = 30, message = "scoreSet3 ne peut pas dépasser 30")
    @Column(name = "score_set3", nullable = true)
    private Integer scoreSet3;

}
