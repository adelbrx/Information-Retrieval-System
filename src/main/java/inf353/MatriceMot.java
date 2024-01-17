package inf353;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class MatriceMot {

    private int ndoc;
    private int nterm;
    public String[][] matriceMot;
    private String path;

    /**
     * le premier constructeur de la classe
     * 
     * @param ndoc  : le nomrbe de documents
     * @param nterm : le nombre des termes dans chaque document
     * @author squadSkat
     */

    public MatriceMot(int ndoc, int nterm) {

        // initialiser les attributs de la classe
        this.ndoc = ndoc;
        this.nterm = nterm;
        matriceMot = new String[ndoc][nterm];

        // parcourir les documents
        for (int document = 0; document < this.ndoc; document++) {

            // parcourir les indexes de termes
            for (int indexTerm = 0; indexTerm < this.nterm; indexTerm++) {

                matriceMot[document][indexTerm] = "";
            }
        }
    }

    /**
     * le deuxième constructeur de la classe
     * 
     * @param path : le chemain où se trouve la matrice
     * @author squadSkat
     */
    public MatriceMot(String path) {
        this.path = path;
    }

    /**
     * retourne le nombre de termes
     * 
     * @author squadSkat
     */
    public int getNterm() {
        return this.nterm;
    }

    /**
     * retourne le nombre de documents
     * 
     * @author squadSkat
     */
    public int getNdoc() {
        return this.ndoc;
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
            File fichier = new File(cheminRepertoire + nomDeFichier + "_mot.txt");

            // tester si le fichier est créé ou pas
            if (fichier.createNewFile()) {

                // création l'écrivain du ficheir
                FileWriter ecrivain = new FileWriter(cheminRepertoire + nomDeFichier + "_mot.txt");

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

                        ecrivain.write(this.matriceMot[document][indexTerm] + " ");
                    }
                    ecrivain.write("\n");
                }

                ecrivain.close();
                System.out.println("[+] entregistré dans sur le chemin : " + cheminRepertoire);

                // enregistrer l'adresse du fichier crée
                this.path = cheminRepertoire + nomDeFichier + "_mot.txt";

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
     * retourne la valeur des cordonnées spécifique dans la matrice
     * 
     * @param document : le numéro du document
     * @param term     : le numéro du terme
     * @author squadSkat
     */
    public String val(int document, int term) {

        return this.matriceMot[document][term];
    }

    /**
     * affecter une valeur dans la matrice à des cordonnées spécifique
     * 
     * @param document : le numéro du document
     * @param term     : le numéro du terme
     * @param valeur   : la nouvelle valeur du nombre d'occurence
     * @author squadSkat
     */
    public void affecte(int document, int term, String valeur) {
        this.matriceMot[document][nterm] = valeur;
    }

    /**
     * ajouter un document
     * 
     * @param document : le document à ajouté
     * @author squadSkat
     */
    public void ajouterDocument(String[] document) {

        // calculer la taille du terms
        int max_terms = maximum(document.length, this.nterm);

        // créer une matrice avec la nouvelle taille
        String[][] nouvelleMatriceMot = new String[this.ndoc + 1][max_terms];

        // copier l'ancienne matrice
        for (int doc = 0; doc < this.ndoc; doc++) {

            // parcourir les terms
            for (int term = 0; term < max_terms; term++) {

                // si la nouvelle matrice est plus grande que la précédente
                if (term >= this.nterm) {

                    nouvelleMatriceMot[doc][term] = "";
                }
                // pour les cases similaires
                else {

                    nouvelleMatriceMot[doc][term] = this.matriceMot[doc][term];
                }
            }
        }

        // copier le nouveau document
        for (int term = 0; term < max_terms; term++) {

            // si la position ne dépasse pas la taille du nouveau document
            if (term < document.length) {

                nouvelleMatriceMot[this.ndoc][term] = document[term];
            }
            // si la position ne dépasse pas la taille du nouveau document
            else {

                nouvelleMatriceMot[this.ndoc][term] = "";
            }
        }

        // écraser l'ancienne matrice et les anciennes taille
        this.matriceMot = nouvelleMatriceMot;
        this.ndoc++;
        this.nterm = max_terms;

    }

    /**
     * retourne le maximum entre deux entiers
     * 
     * @param valeur_1 : le premier nombre
     * @param valeur_2 :le deuxième nombre
     * @return
     */
    public int maximum(int valeur_1, int valeur_2) {

        if (valeur_1 > valeur_2) {

            return valeur_1;
        } else {

            return valeur_2;
        }
    }

}
