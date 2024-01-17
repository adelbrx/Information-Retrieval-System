package inf353;

import java.io.Serializable;

/**
 * elemnet de notre arbre de coef , il contient le path du document et le coef
 * du mot associé
 *
 * @author squadSkat
 */

public class DoCoef implements Serializable {

    private String path;
    private double coefMot;

    /**
     * contructeur de la classe
     * 
     * @param path le chemin vers le fichier
     * @author squadSkat
     */
    public DoCoef(String path) {

        this.path = path;
        this.coefMot = 1;
    }

    public DoCoef(String path, double coefMot) {

        this.path = path;
        this.coefMot = coefMot;
    }

    /**
     * Getter du path du document.
     * 
     * @return The path of the document.
     * @author squadSkat
     */
    public String getPath() {

        return path;
    }

    /**
     * setter du path de notre document
     * 
     * @param path The new path for the document.
     * @author squadSkat
     */
    public void setPath(String path) {

        this.path = path;
    }

    /**
     * Getter de la valeur du coefficient pour le mot.
     * 
     * @author squadSkat
     */
    public double getCoefMot() {

        return coefMot;
    }

    /**
     * Setter de la valeur du coefficient pour le mot.
     * 
     * @param d nouvelle coef du mot
     * @author squadSkat
     */
    public void setCoefMot(double d) {

        this.coefMot = d;
    }

    public void printDocCoef() {

        System.out.println("Path : " + this.path + " Coef : " + this.coefMot);
    }

}
