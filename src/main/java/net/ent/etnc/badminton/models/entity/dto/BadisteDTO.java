package net.ent.etnc.badminton.models.entity.dto;

import lombok.*;
import net.ent.etnc.badminton.models.entity.references.CategorieAge;
import net.ent.etnc.badminton.models.entity.references.Sexe;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BadisteDTO {

    private Long id;

    private String nom;

    private String prenom;

    private LocalDate dateNaissance;

    private String numeroLicence;

    private String club;

    private Sexe sexe;

    private CategorieAge categorieAge;

}