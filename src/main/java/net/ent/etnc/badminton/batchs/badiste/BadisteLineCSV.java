package net.ent.etnc.badminton.batchs.badiste;
public record BadisteLineCSV(
        String nom,
        String prenom,
        String dateNaissance,
        String numeroLicence,
        String sexe,
        String club,
        Integer pointsSimple,
        Integer pointsDouble,
        Integer pointsMixte
) {
}
