package inf353;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class MatriceIndexNaive implements MatriceIndex {

    private int ndoc;
    private int nterm;
    public int[][] matriceIndex;
    private String path;

    /**
     * le premier constructeur de la classe
     * 
     * @param ndoc  : le nomrbe de documents
     * @param nterm : le nombre des termes dans chaque document
     * @author squadSkat
     */
    public MatriceIndexNaive(int ndoc, int nterm) {

        // initialiser les attributs de la classe
        this.ndoc = ndoc;
        this.nterm = nterm;
        matriceIndex = new int[ndoc][nterm];

        // parcourir les documents
        for (int document = 0; document < this.ndoc; document++) {

            // parcourir les indexes de termes
            for (int indexTerm = 0; indexTerm < this.nterm; indexTerm++) {

                matriceIndex[document][indexTerm] = 0;
            }
        }
    }

    /**
     * le deuxième constructeur de la classe
     * 
     * @param path : le chemain où se trouve la matrice
     * @author squadSkat
     */
    public MatriceIndexNaive(String path) {

        this.path = path;
    }

    /**
     * Sauvegarde de la matrice d'occurence dans le fichier nomDeFichier. Le format
     * est libre,
     * mais doit privilégier la vitesse de chargement et la compacité (taille du
     * fichier).
     * 
     * @param nomDeFichier
     * @author squadSkat
     */
    @Override
    public void sauver(String nomDeFichier) throws FileNotFoundException {

        // le chemin où on sauvgarde les fichiers créés
        String cheminRepertoire = System.getProperty("user.dir") + "/src/main/java/inf353/ressources/";

        try {

            // le répertoire
            File repertoire = new File(cheminRepertoire);

            // création du répertoire s'il n'existe pas
            if (!repertoire.exists()) {

                // création du répertoire avec succès
                if (repertoire.mkdir()) {

                    // message affiché que le répertoire est créé
                    System.out.println("[+] Répertoire créé : " + cheminRepertoire);

                    // échec dans le création du répertoire
                } else {

                    // message affiché que le répertoire n'a pas été créé
                    System.out.println("[-] Échec de la création du répertoire.");
                }
            }

            // création du fichier
            File fichier = new File(cheminRepertoire + nomDeFichier + ".txt");

            // tester si le fichier est créé ou pas
            if (fichier.createNewFile()) {

                // création l'écrivain du ficheir
                FileWriter ecrivain = new FileWriter(cheminRepertoire + nomDeFichier + ".txt");

                // écrire le nombre de documents dans la première ligne
                ecrivain.write("" + ndoc);
                ecrivain.write("\n");

                // écrire le nombre de mots dans la première ligne
                ecrivain.write("" + nterm);
                ecrivain.write("\n");

                // parcourir les documents
                for (int document = 0; document < this.ndoc; document++) {

                    // parcourir les indexes de termes
                    for (int indexTerm = 0; indexTerm < this.nterm; indexTerm++) {

                        ecrivain.write(this.matriceIndex[document][indexTerm] + " ");
                    }
                    ecrivain.write("\n");
                }

                ecrivain.close();
                System.out.println("[+] entregistré dans sur le chemin : " + cheminRepertoire);

                // enregistrer l'adresse du fichier crée
                this.path = cheminRepertoire + nomDeFichier + ".txt";

            } else {

                // message affiché que le fichier n'a pas été créé
                System.out.println(" [!] le fichier existe déjà.");
            }

        } catch (IOException e) {

            System.out.println("An error occurred : ");
            e.printStackTrace();
        }
    }

    /**
     * Méthode val avec les paramètres ndoc et nterm
     * 
     * @param ndoc  : nomrbe des documents
     * @param nterm : nombre des termes
     */
    @Override
    public int val(int ndoc, int nterm) {

        return matriceIndex[ndoc][nterm]; // Renvoie la valeur de la matrice
    }

    /**
     * Ajoute 1 au nombre d'occurences du terme numéro nterm dans le document numéro
     * ndoc.
     * 
     * @param ndoc  le numéro du document
     * @param nterm le numéro du terme
     */
    @Override
    public void incremente(int ndoc, int nterm) {

        matriceIndex[ndoc][nterm]++; // Augmente la valeur de la matrice de 1
    }

    /**
     * affecte à val le numéro d'occurences du terme numéro nterm dans le document
     * numéro ndoc.
     * 
     * @param ndoc  le numéro du document
     * @param nterm le numéro du terme
     * @param val   la nouvelle valeur du nombre d'occurence
     */
    @Override
    public void affecte(int ndoc, int nterm, int val) {

        matriceIndex[ndoc][nterm] = val; // Affecte une nouvelle valeur à la matrice;
    }

    public String getPath() {
        return this.path;
    }
}
