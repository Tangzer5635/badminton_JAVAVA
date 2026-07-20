package net.ent.etnc.badminton.models.entity.references;

/**
 * Disciplines de badminton selon la FFBAD.
 * Simple = 1 joueur par équipe (2 participants au total)
 * Double = 2 joueurs par équipe (4 participants au total)
 */
public enum Discipline {

    SH("Simple Hommes",    2),
    SD("Simple Dames",     2),
    DH("Double Hommes",    4),
    DD("Double Dames",     4),
    DX("Double Mixte",     4);

    private final String libelle;
    /** Nombre total de joueurs dans un match (2 pour simple, 4 pour double) */
    private final int nombreJoueurs;

    Discipline(String libelle, int nombreJoueurs) {
        this.libelle       = libelle;
        this.nombreJoueurs = nombreJoueurs;
    }

    public String getLibelle()       { return libelle; }
    public int getNombreJoueurs()    { return nombreJoueurs; }
    public boolean isSimple()        { return nombreJoueurs == 2; }
    public boolean isDouble()        { return nombreJoueurs == 4; }

    /** Renvoie le nombre de joueurs par équipe (1 en simple, 2 en double) */
    public int getNombreJoueursParEquipe() {
        return nombreJoueurs / 2;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", name(), libelle);
    }
}
