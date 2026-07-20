package net.ent.etnc.badminton.models.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import net.ent.etnc.badminton.models.entity.references.CategorieAge;
import net.ent.etnc.badminton.models.entity.references.Sexe;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "BADISTE",
        // Le badiste est identifié de façon UNIQUE par son numéro de licence (cf. sujet)
        uniqueConstraints = @UniqueConstraint(name = "uk___badiste___numero_licence", columnNames = {"numero_licence"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, of = {"numeroLicence"})
@ToString(callSuper = true, of = {"nom", "prenom", "numeroLicence", "categorieAge", "sexe"})
public class Badiste extends AbstractEntity {

    // 1..3 : un classement par discipline maximum (SH/DH/DX ou SD/DD/DX)
    @Valid
    @Size(max = 3, message = "Un badiste ne peut pas avoir plus de 3 classements")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "badiste_id", foreignKey = @ForeignKey(name = "fk___classement___badiste_id"))
    private List<Classement> classements = new ArrayList<>();

    @Getter
    @Setter
    @NotNull(message = "dateNaissance ne doit pas être null")
    @PastOrPresent(message = "dateNaissance ne doit pas être dans le futur")
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Getter
    @Setter
    @NotNull(message = "Prenom ne doit pas être null")
    @NotEmpty(message = "Prenom ne doit pas être vide")
    @NotBlank(message = "Prenom doit contenir des caractères lisibles")
    @Length(min = 1, max = 50, message = "Prenom doit avoir entre 1 et 50 caractères")
    @Column(name = "prenom", length = 50, nullable = false)
    private String prenom;

    @Getter
    @Setter
    @NotNull(message = "Nom ne doit pas être null")
    @NotEmpty(message = "Nom ne doit pas être vide")
    @NotBlank(message = "Nom doit contenir des caractères lisibles")
    @Length(min = 1, max = 50, message = "Nom doit avoir entre 1 et 50 caractères")
    @Column(name = "nom", length = 50, nullable = false)
    private String nom;

    @Getter
    @Setter
    @NotNull(message = "numeroLicence ne doit pas être null")
    @Pattern(regexp = "\\d{7,10}", message = "numeroLicence doit contenir entre 7 et 10 chiffres")
    @Column(name = "numero_licence", length = 10, nullable = false)
    private String numeroLicence;

    // Un badiste peut prendre une licence SANS club (cf. sujet) -> nullable, pas de @NotNull
    @Getter
    @Setter
    @Length(max = 50, message = "club ne doit pas dépasser 50 caractères")
    @Column(name = "club", length = 50, nullable = true)
    private String club;

    // Catégorie recalculée automatiquement à chaque saison :
    // toujours dérivée de dateNaissance via CategorieAge.fromAnneeNaissance (cf. EntitiesFactory)
    @Getter
    @Setter
    @NotNull(message = "CategorieAge ne doit pas être null")
    @Enumerated(EnumType.STRING)
    @Column(name = "categorie_age", length = 10, nullable = false)
    private CategorieAge categorieAge;

    @Getter
    @Setter
    @NotNull(message = "Sexe ne doit pas être null")
    @Enumerated(EnumType.STRING)
    @Column(name = "sexe", length = 10, nullable = false)
    private Sexe sexe;

    public List<Classement> getClassements() {
        return Collections.unmodifiableList(classements);
    }

    public void addClassement(@NotNull Classement classement) {
        this.classements.add(classement);
    }

    public void removeClassement(@NotNull Classement classement) {
        this.classements.remove(classement);
    }

}
