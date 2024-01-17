package inf353;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Word implements Serializable {

    private String word;
    private BTreeCoef files;

    /**
     * Contructeur 1 qui initialise l'attribut mot
     * 
     * @param word : le nouveau mot
     * @author squadSkat
     */
    Word(String word) {

        this.word = word;
        this.files = null;
    }

    /**
     * Contructeur 2 qui initialise les attributs
     * 
     * @param word  : le nouveau mot
     * @param files : l'arbre des documents
     * @author squadSkat
     */
    Word(String word, BTreeCoef files) {

        this.word = word;
        this.files = files;
    }

    /**
     * getter du mot
     * 
     * @author squadSkat
     */
    public String getWord() {

        return this.word;
    }

    /**
     * setter du mot
     * 
     * @param word : le mot
     * @author squadSkat
     */
    public void setWord(String word) {

        this.word = word;
    }

    /**
     * getter des documents
     * 
     * @author squadSkat
     */
    public BTreeCoef getFiles() {

        return this.files;
    }

    /**
     * setter des documents
     * 
     * @param files : l'arbre des document qui contient ce mot
     * @author squadSkat
     */
    public void setFiles(BTreeCoef files) {

        this.files = files;
    }

    /**
     * Returns an array of elements from the BTreeCoef nodes.
     *
     * @return An array of elements from the BTreeCoef nodes.
     * @author squadSkat
     */
    public DoCoef[] getCoefArray() {
        List<DoCoef> coefList = new ArrayList<>();
        populateCoefList(files.root, coefList);
        coefList.removeIf(coef -> coef.getCoefMot() <= 1);
        return coefList.toArray(new DoCoef[0]);
    }

    /**
     * retourne une array avec tous les elements de l'arbre
     *
     * @param root root BTreeCoef.
     * @param list la liste a remplir avec les elements de l'arbre
     * @author squadSkat
     */
    private void populateCoefList(Cell<DoCoef> root, List<DoCoef> list) {
        if (root != null) {
            populateCoefList(root.successorLeft, list);
            // on calcule le poids de chaque mots , si un mot occure dans un nombre petits
            // des documents du corpus il est plus rare et a une plus grande importance
            double coef = (92000 / files.countNodes()) * root.element.getCoefMot();
            DoCoef newCell = new DoCoef(root.element.getPath(),
                    coef);
            list.add(newCell);
            populateCoefList(root.successorRight, list);
        }
    }

}
