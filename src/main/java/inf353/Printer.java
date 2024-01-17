package inf353;

import java.io.Serializable;

public class Printer implements Serializable {

    private String redText;
    private String yellowText;
    private String greenText;
    private String resetColor;

    Printer() {

        // COULEUR ROUGE
        this.redText = "\u001B[31m";

        // COULEUR JAUNE
        this.yellowText = "\u001B[33m";

        // COULEUR VERTE
        this.greenText = "\u001B[32m";

        // COULEUR PAR DEFAUT
        this.resetColor = "\u001B[0m";
    }

    /**
     * affichage d'un message normal avec une couleur blanche
     * 
     * @param text
     * @author squadSkat
     */
    public void printText(String text) {

        System.out.println(text);
    }

    /**
     * affichage d'un message de succès avec une couleur verte
     * 
     * @param text
    * @author squadSkat
     */
    public void printSuccessText(String text) {

        System.out.println(greenText + text + this.resetColor);
    }

    /**
     * affichage d'un message d'alert avec une couleur Jaune
     * 
     * @param text
    * @author squadSkat
     */
    public void printWarningText(String text) {

        System.out.println(yellowText + text + this.resetColor);
    }

    /**
     * affichage d'un message d'erreur avec une couleur rouge
     * 
     * @param text
    * @author squadSkat
     */
    public void printErrorText(String text) {

        System.out.println(redText + text + this.resetColor);
    }

}
