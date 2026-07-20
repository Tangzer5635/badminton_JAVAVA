package net.ent.etnc.badminton.models.entity.references;

public enum Sexe {
    HOMME("Homme"),
    FEMME("Femme");

    private final String libelle;

    Sexe(String libelle) { this.libelle = libelle; }
    public String getLibelle() { return libelle; }
}
