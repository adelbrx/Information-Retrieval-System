package inf353;

import java.io.FileNotFoundException;

public interface MatriceIndex {

    /**
     * Sauvegarde de la matrice d'occurence dans le fichier nomDeFichier. Le format
     * est libre,
     * mais doit privilégier la vitesse de chargement et la compacité (taille du
     * fichier).
     *
     * @param nomDeFichier
     */
    public abstract void sauver(String nomDeFichier) throws FileNotFoundException;

    /**
     * retourne le nombre d'occurences du terme numéro nterm dans le document numéro
     * ndoc.
     * 
     * @param ndoc  le numéro du document
     * @param nterm le numéro du terme
     * @return le nombre d'occurences du terme dans le document
     */
    public abstract int val(int ndoc, int nterm);

    /**
     * Ajoute 1 au nombre d'occurences du terme numéro nterm dans le document numéro
     * ndoc.
     * 
     * @param ndoc  le numéro du document
     * @param nterm le numéro du terme
     */
    public abstract void incremente(int ndoc, int nterm);

    /**
     * affecte à val le numéro d'occurences du terme numéro nterm dans le document
     * numéro ndoc.
     * 
     * @param ndoc  le numéro du document
     * @param nterm le numéro du terme
     * @param val   la nouvelle valeur du nombre d'occurence
     */
    public abstract void affecte(int ndoc, int nterm, int val);

}
