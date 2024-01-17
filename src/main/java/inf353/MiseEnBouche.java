package inf353;

public class MiseEnBouche {

    public static final int SCALENE = 0;
    public static final int ISOCELE = 1;
    public static final int EQUILATERAL = 2;
    public static final int ERREUR = 3;

    /**
     * prend en entrée trois entiers. Ces trois entiers sont interprétés
     * comme représentant les longueurs des cotés d’un triangle.
     *
     * renvoie un résultat précisant si il s’agit d’un triangle scalène (ni plat,
     * ni isocèle),
     * isocèle ou équilatéral.
     *
     * @param a la première longueur
     * @param b la seconde longueur
     * @param c la troisième longueur
     * @return un entier indiquant le type de rectangle.
     */
    public static int typeDeTriangle(float a, float b, float c) {
        // à implémenter
        return ERREUR;
    }

    public static void usage() {
        System.err.println("Usage: java inf234.inf353.MiseEnBouche l1 l2 l3");
        System.err.println("l1, l2, l3 sont des entiers représentant les longueurs des trois cotés d'un triangle.");
    }

    public static void main(String args[]) {
        if (args.length != 3) {
            usage();
            System.exit(1);
        }

        float a = Float.parseFloat(args[0]);
        float b = Float.parseFloat(args[1]);
        float c = Float.parseFloat(args[2]);

        int resultat = typeDeTriangle(a, b, c);

        switch (resultat) {
            case SCALENE:
                System.out.println("Le triangle est scalène");
                break;
            case ISOCELE:
                System.out.println("Le triangle est isocèle");
                break;
            case EQUILATERAL:
                System.out.println("Le triangle est équilatéral");
                break;
            case ERREUR:
                System.out.println("Le triangle est dégénéré");
                break;
            default:
                System.out.println("réponse inattendue");
                break;
        }
    }
}
