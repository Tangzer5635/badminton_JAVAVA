package net.ent.etnc.badminton.models.entity.dto;

import net.ent.etnc.badminton.models.entity.references.SerieClassement;

public record TopClassementDTO(
        String nom,
        String prenom,
        int points,
        SerieClassement serie
) {
}
