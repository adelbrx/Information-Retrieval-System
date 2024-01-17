package inf353;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManipulator {

    public String path;
    private Scanner lecteur;
    private Printer afficheur;

    /**
     * constructeur
     * 
     * @param path : le chemin du fichier
     * @author squadSkat
     */
    FileManipulator(String path) {

        // initialisation du chemin
        this.path = path;
        // initialisation de l'afficheur en couleurs
        this.afficheur = new Printer();
    }

    /**
     * créer un nouveau fichier
     * 
     * @author squadSkat
     */
    public void createFile() {

        try {

            // initialisation du fichier
            File file = new File(this.path);
            // créer le fichier s'il n'existe pas
            if (file.createNewFile()) {

                // afficher un message que le fichier à été créé
                afficheur.printSuccessText("[+] Le fichier " + file.getName() + " est créé ...");
            } else {

                // affichier un message d'erreur en jaune
                afficheur.printWarningText("[!] le fichier existe déjà");
            }
        } catch (FileNotFoundException error) {

            // afficher l'erreur en rouge
            afficheur.printErrorText("[-]" + error.getMessage());
        } catch (IOException error) {

            // afficher l'erreur en rouge
            afficheur.printErrorText("[-]" + error.getMessage());
        }
    }

    /**
     * supprimer un fichier s'il existe
     * 
     * @author squadSkat
     */
    public void removeFile() {

        // initialisation du fichier
        File file = new File(this.path);
        // créer le fichier s'il n'existe pas
        if (file.delete()) {

            // afficher un message que le fichier à été créé
            afficheur.printSuccessText("[+] Le fichier " + file.getName() + " a été bien supprimé ...");
        } else {

            // affichier un message d'erreur en jaune
            afficheur.printWarningText("[!] Échec de la suppression du fichier : aucun fichier existe dans ce chemin");
        }

    }

    /**
     * extraire tous les lignes du fichier et les stockées dans un tableau
     * 
     * @return lines : tableau contient toutes les lignes du fichier
     * @author squadSkat
     */
    public String[] readLines() {

        List<String> lines = new ArrayList<>();

        try {

            // initialisation du fichier
            File file = new File(this.path);
            // initialisation du lecteur
            this.lecteur = new Scanner(file);

            // tant qu'il reste des ligne à lire
            while (lecteur.hasNextLine()) {

                // lire la porchaine ligne
                String mot = lecteur.nextLine();
                // supprimer les espaces blancs avant de l'ajouter dans la liste
                lines.add(mot.trim());
            }

            // fermer le lecteur
            lecteur.close();

        } catch (FileNotFoundException error) {

            // afficher l'erreur en rouge
            afficheur.printErrorText("[-]" + error.getMessage());
        }

        // transformer la list vers un tableau et le retourner
        return lines.toArray(new String[lines.size()]);

    }

    /**
     * extraire tous les mots du fichier et les stockés dans un tableau
     * 
     * @return words : tableau contient tous les mots du fichier
     * @author squadSkat
     */
    public String[] readWords() {

        // initlialiser la liste des mots
        List<String> words = new ArrayList<>();
        // initialiser le lecteur de document qui lit mot par mot
        LecteurDocumentNaif lecteurDoc = new LecteurDocumentNaif(this.path);

        // démmarer la lecture du fichier
        lecteurDoc.demarrer();

        // tant qu'il reste un mot à lire
        while (!lecteurDoc.finDeSequence()) {

            // stocker le mot courant dans la liste (le dernier mot n'est pas concerné)
            words.add(lecteurDoc.elementCourant());
            // lire le mot suivant
            lecteurDoc.avancer();
        }

        // transformer la list vers un tableau et le retourner 
        return words.toArray(new String[words.size()]);

    }

    /**
     * lire le contenu du fichier
     * 
     * @return String : le text du document
     * @author squadSkat
     */
    public String readContent() {

        // initialisation du contenu
        String content = "";
        // lire tous les lignes du fichier
        String[] lines = readLines();

        // concaténer tous les lignes dans une seul chaine de caractères
        for (String line : lines) {

            content += (line + "\n");
        }

        // retourner le contenu du fichier with removing \n of the last line
        return content;
    }

    /**
     * écrite un texte dans un fichier
     * 
     * @param text : la chaine de caractère à écrire
     * @author squadSkat
     */
    public void writeContent(String text) {

        try {

            // initialisation du fichier
            File file = new File(this.path);
            // initialisation de l'écrivain dans le fichier
            FileWriter ecrivain = new FileWriter(file);
            // écrire le texte
            ecrivain.write(text);
            // fermer le fichier
            ecrivain.close();
            // tester le cas du texte vide
            if (text != "") {

                // affichier un message que le texte a été ajouté
                afficheur.printSuccessText("[+] Le texte a été ajouté avec succès");
            } else {

                // affichier un message que le texte a été supprimé
                afficheur.printSuccessText("[+] Le contenu du fichier a été supprimé avec succès");
            }
        } catch (FileNotFoundException error) {

            // afficher l'erreur en rouge
            afficheur.printErrorText("[-]" + error.getMessage());
        } catch (IOException error) {

            // afficher l'erreur en rouge
            afficheur.printErrorText("[-]" + error.getMessage());
        }
    }

    /**
     * écrite des lignes d'un texte dans un fichier
     * 
     * @param lines : tableau continet les lignes du texte à écrire
     * @author squadSkat
     * 
     */
    public void writeLines(String[] lines) {

        // initialisation du texte complet
        String content = "";
        // concaténation de toutes les lignes
        for (String line : lines) {

            // la concaténation
            content += (line + "\n");
        }

        // stocker les contenu dans un fichier
        writeContent(content);
    }

    /**
     * ajouter un nouveau text dans un fichier
     * 
     * @param newText : le nouveau texte a ajouté
     * @author squadSkat
     */
    public void updateContent(String newText) {

        String content = readContent() + newText;
        writeContent(content);
    }

    /**
     * supprimer le contenu d'un fichier
     * 
     * @param newText : le nouveau texte a ajouté
     * @author squadSkat
     */
    public void removeContent() {

        writeContent("");
    }

}
