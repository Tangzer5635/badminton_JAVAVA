package net.ent.etnc.badminton.batchs.badiste;

/**
 * Record pour une ligne du CSV badistes.
 * Les pointsSimple/Double/Mixte sont optionnels (peuvent être null/vides).
 */
public record BadisteLineCSV(
        String nom,
        String prenom,
        String dateNaissance,      // String à parser
        String numeroLicence,
        String sexe,               // String à parser en Sexe enum
        String club,
        Integer pointsSimple,      // optionnel (nullable)
        Integer pointsDouble,      // optionnel (nullable)
        Integer pointsMixte        // optionnel (nullable)
) {
}
