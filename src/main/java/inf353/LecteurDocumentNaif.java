package inf353;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/*
 * class qui implemente un lecteur de document mot par mot utilisant u  parcours sequentiel modele 1
* @author squadSkat
 */
public class LecteurDocumentNaif implements AccesSequentielModele1<String> {
    private String elementCrt;
    private String pathFile;
    private Scanner scanner;
    private Printer afficheur;

    public LecteurDocumentNaif(String path) {

        this.pathFile = path;
        this.afficheur = new Printer();
        this.scanner = new Scanner(path);
    }

    @Override
    public void demarrer() {

        // affecter l'encodage des caractères dans le système
        System.setProperty("file.encoding", "UTF-8");

        try {

            // Ouvrir le fichier
            File file = new File(this.pathFile);

            // Si le ficheir existe
            if (file.exists()) {

                // créer un scanner pour le fichier
                this.scanner = new Scanner(file);
                // définir le délimiteur des espaces blancs la lecture dans le fichier
                scanner.useDelimiter("[\\s']+");
                // test si le fichier est vide ou non
                if (this.scanner.hasNext()) {

                    // lire le premier mot
                    this.elementCrt = this.scanner.next().replaceAll("[^a-zA-Z0-9éèêàùâôûç]", "");
                }

                // message d'affichage que le fichier est en cours de lecture
                afficheur.printSuccessText("[+] Le fichier : " + file.getName() + " est en cours de lecture ...");
            }

            // Si le fichier n'existe pas
            else {

                // message d'affichage que le fichier n'existe pas
                afficheur.printErrorText("[-] Le fichier n'existe pas ...");
            }
        } catch (FileNotFoundException error) {

            afficheur.printErrorText("[-] Le fichier n'est pas trouvé dans ce chemin : " + this.pathFile);
        } catch (IOException error) {

            afficheur.printErrorText("[-] " + error.getMessage());
        }
    }

    @Override
    public void avancer() {

        // s'il reste un mot à lire dans le fichier
        if (this.scanner.hasNext()) {

            // affecter la prochain mot à l'attribut elementCrt
            this.elementCrt = this.scanner.next().replaceAll("[^a-zA-Z0-9éèêàùâôûç]", "");
        }
        // si on est dans la fin du fichier
        else {

            // si fin de séquence on affecte l'élément null au élément courant
            this.elementCrt = null;
        }
    }

    @Override
    public boolean finDeSequence() {

        if (this.elementCrt == null) {

            // fermer le fichier
            this.scanner.close();
            return true;
        } else {

            return false;
        }
    }

    @Override
    public String elementCourant() {

        return this.elementCrt;
    }
}