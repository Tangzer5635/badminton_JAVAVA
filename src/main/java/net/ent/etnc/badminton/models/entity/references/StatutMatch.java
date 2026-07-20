package net.ent.etnc.badminton.models.entity.references;

public enum StatutMatch {
    PLANIFIE ("Planifié"),
    EN_COURS ("En cours"),
    TERMINE  ("Terminé"),
    ANNULE   ("Annulé");

    private final String libelle;
    StatutMatch(String libelle) { this.libelle = libelle; }
    public String getLibelle() { return libelle; }
}
