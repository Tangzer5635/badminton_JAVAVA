package net.ent.etnc.badminton.models.entity.references;

/**
 * Séries de classement FFBAD (réforme 2025-2026).
 * Ordre décroissant de niveau : N1 (meilleur) → NC (non classé).
 *
 * N1-N3 : Nationale
 * R4-R6 : Régionale
 * D7-D9 : Départementale
 * P10-P12 : Promotion
 * NC : Non classé
 *
 * Points de référence (indicatifs, seuils FFBAD 2025-2026) :
 *   N1  > 2400pts (H) / > 2200pts (F)
 *   N2  2100–2400 (H) / 1900–2200 (F)
 *   N3  1900–2100 (H) / 1700–1900 (F)
 *   R4  1700–1900 (H) / 1500–1700 (F)
 *   R5  1500–1700 (H) / 1300–1500 (F)
 *   R6  1300–1500 (H) / 1100–1300 (F)
 *   D7  1100–1300 (H) / 900–1100 (F)
 *   D8  900–1100  (H) / 750–900  (F)
 *   D9  750–900   (H) / 620–750  (F)
 *   P10 620–750   (H) / 520–620  (F)
 *   P11 520–620   (H) / 460–520  (F)
 *   P12 460–520   (H) / 420–460  (F)
 *   NC  400–460
 */
public enum SerieClassement {

    N1  ("N1",  "Nationale 1",     2401, 3000),
    N2  ("N2",  "Nationale 2",     2101, 2400),
    N3  ("N3",  "Nationale 3",     1901, 2100),
    R4  ("R4",  "Régionale 4",     1701, 1900),
    R5  ("R5",  "Régionale 5",     1501, 1700),
    R6  ("R6",  "Régionale 6",     1301, 1500),
    D7  ("D7",  "Départementale 7",1101, 1300),
    D8  ("D8",  "Départementale 8", 901, 1100),
    D9  ("D9",  "Départementale 9", 751,  900),
    P10 ("P10", "Promotion 10",     621,  750),
    P11 ("P11", "Promotion 11",     521,  620),
    P12 ("P12", "Promotion 12",     461,  520),
    NC  ("NC",  "Non Classé",       400,  460);

    private final String code;
    private final String libelle;
    /** Seuil minimum de points (indicatif, basé sur les hommes) */
    private final int pointsMin;
    /** Seuil maximum de points (indicatif) */
    private final int pointsMax;

    SerieClassement(String code, String libelle, int pointsMin, int pointsMax) {
        this.code      = code;
        this.libelle   = libelle;
        this.pointsMin = pointsMin;
        this.pointsMax = pointsMax;
    }

    public String getCode()      { return code; }
    public String getLibelle()   { return libelle; }
    public int getPointsMin()    { return pointsMin; }
    public int getPointsMax()    { return pointsMax; }

    /**
     * Détermine la série à partir des points (seuils indicatifs hommes).
     * En réalité la FFBAD utilise une méthode par percentile,
     * mais cette approximation est suffisante pour l'application.
     */
    public static SerieClassement fromPoints(int points) {
        for (SerieClassement serie : values()) {
            if (points >= serie.pointsMin && points <= serie.pointsMax) {
                return serie;
            }
        }
        // Au-dessus du plafond -> N1, en dessous du plancher -> NC
        return points > N1.pointsMax ? N1 : NC;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", code, libelle);
    }
}
