package net.ent.etnc.badminton.models.entity.references;

/**
 * Catégories d'âge FFBAD (Fédération Française de Badminton).
 *
 * Règle de calcul : l'âge est apprécié au 1er janvier de la saison en cours.
 * Tableau officiel saison 2025/2026 :
 *   Vétérans  → né(e)s en 1985 et avant   (40 ans et +)
 *   Séniors   → né(e)s entre 1986 et 2007 (18 à 39 ans)
 *   Juniors   → né(e)s en 2008-2009        (16-17 ans)
 *   Cadets    → né(e)s en 2010-2011        (14-15 ans)
 *   Minimes   → né(e)s en 2012-2013        (12-13 ans)
 *   Benjamins → né(e)s en 2014-2015        (10-11 ans)
 *   Poussins  → né(e)s en 2016 et après   (moins de 10 ans)
 *   Minibad   → moins de 9 ans             (non classés FFBAD)
 */
public enum CategorieAge {

    MINIBAD  ("Minibad",   "Moins de 9 ans",  0,  8),
    POUSSIN  ("Poussin",   "9 à 10 ans",      9, 10),
    BENJAMIN ("Benjamin",  "11 à 12 ans",    11, 12),
    MINIME   ("Minime",    "13 à 14 ans",    13, 14),
    CADET    ("Cadet",     "15 à 16 ans",    15, 16),
    JUNIOR   ("Junior",    "17 à 18 ans",    17, 18),
    SENIOR   ("Senior",    "19 à 40 ans",    19, 40),
    VETERAN  ("Vétéran",   "41 ans et plus", 41, 999);

    private final String libelle;
    private final String description;
    private final int ageMin;
    private final int ageMax;

    CategorieAge(String libelle, String description, int ageMin, int ageMax) {
        this.libelle     = libelle;
        this.description = description;
        this.ageMin      = ageMin;
        this.ageMax      = ageMax;
    }

    public String getLibelle()      { return libelle; }
    public String getDescription()  { return description; }
    public int getAgeMin()          { return ageMin; }
    public int getAgeMax()          { return ageMax; }

    /**
     * Calcule la catégorie d'âge à partir de l'année de naissance
     * et de l'année de la saison en cours.
     *
     * @param anneeNaissance   l'année de naissance du joueur
     * @param anneeSaison      l'année de début de saison (ex: 2025 pour 2025/2026)
     * @return la catégorie d'âge FFBAD correspondante
     */
    public static CategorieAge fromAnneeNaissance(int anneeNaissance, int anneeSaison) {
        // L'age est apprecie au 1er janvier de la saison en cours :
        // pour la saison 2025/2026, reference = 1er janvier 2026 = anneeSaison + 1
        return fromAge(anneeSaison + 1 - anneeNaissance);
    }

    /**
     * Calcule la catégorie d'âge à partir de l'âge en années.
     */
    public static CategorieAge fromAge(int age) {
        for (CategorieAge categorie : values()) {
            if (age >= categorie.ageMin && age <= categorie.ageMax) {
                return categorie;
            }
        }
        return age < 0 ? MINIBAD : VETERAN;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", libelle, description);
    }
}
