package inf353;

/**
 * Un Dictionnaire est un ensemble de L mot (tous différents). Chaque mot est
 * associé à un entier
 * compris entre 0 et L-1.
 *
 * @author serasset
 *
 */
public interface Dictionnaire {

    /**
     * é.i: qcq
     * é.f.: le dictionnaire est vide.
     */
    public void vider();

    /**
     * é.i.: le dictionnaire contient D0 (un ensemble de N mots).
     * é.f.: si m appartient à D0; le dictionnaire est inchangé
     * sinon, le dictionnaire contient D0 U {m}
     *
     * @param m
     */
    public void ajouterMot(String m);

    /**
     * renvoie l'entier associé à m;
     * 
     * @param m
     * @return
     */
    public int indiceMot(String m);

    /**
     * renvoie le mot associé à l'entier i;
     * 
     * @param i l'indice du mot à renvoyer
     * @return
     */
    public String motIndice(int i);

    /**
     * renvoie vrai ssi m est dans le dictionnaire.
     * 
     * @param m
     * @return
     */
    public boolean contient(String m);

    /**
     * renvoie le nombre de mots de m.
     * 
     * @return
     */
    public int nbMots();

    /**
     * vrai ssi il existe m dans D0 tel que il exist u tq m = p.u
     *
     * (vari si un mot de D0 commence par p)
     * 
     * @param p le préfixe recherché
     * @return
     */
    public boolean contientPrefixe(String p);

    /**
     * renvoie la chaîne de caractères s telle que
     * s est dans D0
     * et m commence par s
     * et il n'existe pas de chaîne s' ds D plus longue que s tq m commence par s.
     *
     * @param mot
     * @return
     */
    public String plusLongPrefixeDe(String mot);

}
