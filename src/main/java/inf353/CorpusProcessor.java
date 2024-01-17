package inf353;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CorpusProcessor {
    public String[] globalUniqueWords;
    private MatriceIndexNaive matriceOccurence;
    private MatriceMot wordMatrice;
    private List<String> stoplist;
    private Printer afficheur;

    public CorpusProcessor() {

        // initialisation des attributs
        this.globalUniqueWords = new String[0];
        this.afficheur = new Printer();

        // Lire le fichier stop_list qui contient les mot inutile en Français et les
        // stocker dans la list stoplist
        read_stoplist(System.getProperty("user.dir") + "/stop_list.txt");

        // créer une matrice des mots et l'initialisée
        this.wordMatrice = new MatriceMot(0, 0);

    }

    /**
     * vérifie que le corpus n’est pas null et appelle ProcessusFileInDirectory
     * 
     * @param directoryPath : le chemin du répertoire générale
     * @author squadSkat
     */
    public void processCorpus(String directoryPath) {

        // lire le path
        File directory = new File(directoryPath);

        // si le path existe et représente un répertoire
        if (directory.exists() && directory.isDirectory()) {

            // traiter les fichiers dans ce répertoire
            processFilesInDirectory(directory);

        } else {

            // Affichage du message d'erreur
            afficheur.printErrorText("[-] erreur corpus corrumpu: " + directoryPath);
        }
    }

    /**
     * parcours le corpus et tout ses fichiers d’une maniere recursive
     * 
     * @param directory : le chemin du répertoire courant
     * @author squadSkat
     */
    public void processFilesInDirectory(File directory) {

        // stocker le contenu de ce répertoire dans un tableau (chaque case représente
        // le nom du fichier ou d'un répertoire)
        File[] files = directory.listFiles();

        // si le répertoire donnée contient au moin un fichier ou un autre répertoire
        if (files != null) {

            // Parcourir chaque fichier et répertoire dans ce tableau
            for (File file : files) {

                // si le fichier courant est un répertoire
                if (file.isDirectory()) {

                    // traiter les fichiers dans ce répertoire
                    processFilesInDirectory(file);

                    // si le fichier courant est un fichier
                } else {

                    // afficher le nom du fichier courant
                    afficheur.printSuccessText("[+] Le fichier courant est : " + file.getName());

                    // extraire toutes les mots de ce fichier sans redondance et les stocker dand un
                    // tableau qui s'appelle uniqueWords
                    String[] uniqueWords = processFile(file);

                    // ajouter touts les mots de ce fichier dans la matrice des mots en supprimant
                    // les mots unitile (chaque fichier représente une ligne dans la matrice des
                    // mots)
                    this.wordMatrice.ajouterDocument(removeUselessWords(uniqueWords));

                    // ajouter touts les mots de ce fichier dans la matrice des mots en supprimant
                    // les mots unitile (ce tableau globalUniqueWords contient les mots de toutes
                    // les fichiers données)
                    globalUniqueWords = mergeUniqueArrays(globalUniqueWords, removeUselessWords(uniqueWords));
                }
            }
        }

        // sauvgarder la matrice des mots
        try {

            // sauvgader la matrice des mots dans le répertoire squadskat
            this.wordMatrice.sauver("matrice");

        } catch (FileNotFoundException erreur) {

            // en cas d'erreur affichiier l'erreur
            erreur.printStackTrace();
        }

    }

    /**
     * parser un fichier texte en utilisant la class lecteurDocumentNaive
     * 
     * @param file : le fichier courant
     * @return uniqueWords : tableau de mots de ce fichier en supprimant les
     *         redondance et les les mots inutiles
     * @author squadSkat
     */
    public String[] processFile(File file) {

        // lire le fichier
        LecteurDocumentNaif lecteur = new LecteurDocumentNaif(file.getAbsolutePath());

        // démmarer la lecture
        lecteur.demarrer();

        // créer un tableau qui stocke les mots et l'initialisé
        String[] uniqueWords = new String[0];

        // lire mot par mot jusque le mot avant dernier
        while (!lecteur.finDeSequence()) {

            // lire le mot courant
            String currentWord = lecteur.elementCourant();
            // vérifier si ce mot n'est pas traité
            if (!containsWord(uniqueWords, currentWord)) {

                // stocker le mot dans la tableau uniqueWords
                uniqueWords = addWord(uniqueWords, currentWord);
            }

            // avancer vers le mot suivant
            lecteur.avancer();
        }

        // retourné la liste des mots de ce fichier sans redondance
        return uniqueWords;
    }

    /**
     * parcours un tableau et verifier qu’un mots n’existe pas déjà dedan
     * 
     * @param array : le tableau où on cherche le mot
     * @param word  : le mot cherché
     * @return boolean : si le mot est trouvé ou pas
     * @author squadSkat
     */
    public boolean containsWord(String[] array, String word) {

        // parcourir tous les mots de ce tableau
        for (String str : array) {

            // si le mot est trouvé
            if (str != null && str.equals(word)) {

                return true;
            }
        }
        return false;
    }

    /**
     * ajouter un mot à un tableau et renvoi un nouveau tableau avec ce dernier
     * 
     * @param array : le nouveau tableau qui ne contient pas le mot
     * @param word  : le mot a ajouté
     * @return newArray : le nouveau tableau qui contient le mot
     * @author squadSkat
     */
    public String[] addWord(String[] array, String word) {

        // créer un nouveau tableau avec une taille supérieure à l'ancien tableau
        // (nouvelle taille = ancienne taille + 1)
        String[] newArray = new String[array.length + 1];
        // copier l'ancien tableau dans le nouveau tableau
        System.arraycopy(array, 0, newArray, 0, array.length);
        // stocker le mot dans la dernière case du nouveau tableau
        newArray[array.length] = word;

        return newArray;
    }

    /**
     * fusionne deux tableaux tout en garantissant que les éléments
     * uniques sont inclus dans le résultat.
     * 
     * @param array1 : le premier tableau
     * @param array2 : le deuxième tableau
     * @return array3 : la nouveau tableau qui merge les deux tableaux array1 et
     *         array2
     *@author squadSkat
     */
    public String[] mergeUniqueArrays(String[] array1, String[] array2) {

        // transformer le tableau 1 vers une liste
        List<String> mergedList = new ArrayList<>(Arrays.asList(array1));

        // pour chaque mot dans le tableau 2
        for (String word : array2) {

            // si cette nouvelle liste ne contienne pas ce mot
            if (!mergedList.contains(word)) {

                // ajouter ce mot dans la liste
                mergedList.add(word);
            }
        }

        // convertit la liste en un tableau de chaînes de caractères et le retoune
        return mergedList.toArray(new String[0]);
    }

    /**
     * lire les mots de stop en français à partir d'un fichier
     * 
     * @param path : le chemin du fichier qui contient les mots inutile en français
     * @author squadSkat
     */
    public void read_stoplist(String path) {

        // la liste des mots a ignorés
        this.stoplist = new ArrayList<>();

        try {

            // lire le fichier stop_list.txt
            File fichier = new File(path);
            // lancer le lecteur
            Scanner lecteur = new Scanner(fichier);

            // tant qu'il reste des mots à lire
            while (lecteur.hasNextLine()) {

                // lire le porchain mot
                String mot = lecteur.nextLine();
                // supprimer les espaces blancs avant de l'ajouter dans la liste
                this.stoplist.add(mot.trim());
            }

            // fermer le lecteur
            lecteur.close();

        } catch (FileNotFoundException e) {

            // en cas d'erreur alors on affiche sa trace
            afficheur.printErrorText("An error occurred.");
            e.printStackTrace();
        }

    }

    /**
     * supprimer les mots unitiles
     * 
     * @param words : un tableau qui contient tous les mots utiles et inutiles
     * @return array : le nouveau tableau qui ne contient que les mots utiles
     * @author squadSkat
     */
    public String[] removeUselessWords(String[] words) {

        // créer une liste des mots utiles
        List<String> UseWords = new ArrayList<>();

        // lire chaque mot
        for (String word : words) {

            // s'il n'est pas un mot a ignoré alors on l'ajoute dans le nouvelle liste des
            // mots utiles
            if (!this.stoplist.contains(word)) {

                UseWords.add(word);
            }
        }

        // on transforme la liste à un tableau
        String[] unUselessWords = new String[UseWords.size()];

        // convertit la liste en un tableau de chaînes de caractères et le retoune
        return UseWords.toArray(unUselessWords);
    }

    /**
     * parcours le corpus et tout ses fichiers d’une maniere recursive
     * 
     * @param words : un tableau de mots
     * @param file  : le chemin où on veux sauvgarder le tableau des mots
     *      * @author squadSkat
     */
    public void saveArrayToFile(String[] words, String filePath) {

        try {
            // création du fichier
            File fichier = new File(filePath + "_unique_words.txt");

            // si ce fichier exist
            if (fichier.createNewFile()) {

                // initialiser l'écrivain du fichier
                FileWriter writer = new FileWriter(filePath + "_unique_words.txt");

                // parcourir chaque mot dans le tablaeu
                for (String word : words) {

                    // écrire chaque mot dans une ligne
                    writer.write(word + System.lineSeparator());
                }

                // message d'affichage que c'est stocké sans erreur
                afficheur.printSuccessText("[+] fichier sauvgarder a : " + filePath + "_unique_words.txt");
                filePath = filePath + "_unique_words.txt";
            }

            else {

                // affichier un message
                afficheur.printWarningText("[!] ce fichier _unique_words.txt existe déjà");
            }

        } catch (IOException e) {

            // en cas d'erreur affichier sa trace
            e.printStackTrace();
        }
    }

    /**
     * cherche un mot dans tous les fichiers
     * 
     * @param mot : le mot cherché
     * @return nombreOccurence : le nombre d'occurence dans chaque fichier
     * @author squadSkat
     */
    public int[] findWordInFiles(String word) {

        // le nombre de fichier
        int nbFiles = this.wordMatrice.getNdoc();

        // créer une tableau de taille égale au nombre de fichiers et chaque case
        // représente le nomrbe d'occurence du mot dans le fichier qui à une position
        // équivalente
        int[] listFiles = new int[nbFiles];

        // pour chaque fichier
        for (int filePosition = 0; filePosition < nbFiles; filePosition++) {

            // calculer le nombre d'occurende du mot dans ce fichier
            listFiles[filePosition] = findWordInFile(word, filePosition);
        }

        return listFiles;
    }

    /**
     * cherche un mot dans un seule fichier
     * 
     * @param mot          : le mot cherché
     * @param filePosition : le position du fichier dans le matrice des mots
     * @return nombreOccurence : le nombre d'occurence dans ce fichier
     * @author squadSkat
     */
    public int findWordInFile(String word, int filePosition) {

        // le nombre d'occurence du mot
        int nombreOccurence = 0;

        // le nombre de termes
        int nbTerms = this.wordMatrice.getNterm();

        // pour chaque mot dans le fichier
        for (int termPosition = 0; termPosition < nbTerms; termPosition++) {

            // si le term de cette position est le égale au mot
            if (this.wordMatrice.matriceMot[filePosition][termPosition].equals(word)) {

                // inrémenter le nombre d'occurence
                nombreOccurence++;
            }
        }

        return nombreOccurence;
    }

    /**
     * cherche un mot dans un seule fichier mais le resultat est binaire
     * 
     * @param mot          : le mot cherché
     * @param filePosition : le position du fichier dans le matrice des mots
     * @return nombreOccurence : le nombre d'occurence dans ce fichier (1 ou 0)
     * @author squadSkat
     */
    public int findWordInFileBinary(String word, int filePosition) {

        // le nombre de termes
        int nbTerms = this.wordMatrice.getNterm();

        // pour chaque mot dans le fichier
        for (int termPosition = 0; termPosition < nbTerms; termPosition++) {

            // si le term de cette position est le égale au mot
            if (this.wordMatrice.matriceMot[filePosition][termPosition].equals(word)) {

                // inrémenter le nombre d'occurence
                return 1;
            }
        }

        return 0;
    }

    /**
     * contruire la matrice d'occurence
     * 
     * @param is_binary : le le resultat est binaire ou non
     * @author squadSkat
     */
    public void buildMatrixOccur(boolean is_binary) {

        // calculer le nombre de fichiers et le nombre de mots
        int nbFiles = this.wordMatrice.getNdoc();
        int nbWords = this.globalUniqueWords.length;

        // initialiser la matrice d'occurence avec la taille corrècte
        this.matriceOccurence = new MatriceIndexNaive(nbFiles, nbWords);

        // la position du mot
        int termPosition = 0;

        // pour chaque mot dans le vecteur globalUniqueWords
        for (String word : this.globalUniqueWords) {

            // parcourir touts les fichiers dans la matrice des mots
            for (int filePosition = 0; filePosition < nbFiles; filePosition++) {

                // soit le resultat est binaire ou non (selon l'argument is_binary)
                // si on veux un résultat binaire
                if (is_binary) {

                    // affecter la valeur à la matrice d'occurence dans cette position
                    this.matriceOccurence.matriceIndex[filePosition][termPosition] = findWordInFileBinary(word,
                            filePosition);
                }
                // si on ne veux pas un résultat binaire
                else {

                    // affecter la valeur à la matrice d'occurence dans cette position
                    this.matriceOccurence.matriceIndex[filePosition][termPosition] = findWordInFile(word, filePosition);
                }
            }

            // incrémenter la position du term
            termPosition++;
        }

        try {

            // sauvgarder la matrice dans un fichier
            this.matriceOccurence.sauver("matrice_occurence.txt");

            // afficher que la matrice a été bien sauvgarder
            afficheur.printSuccessText("[+] la matrice d'occurence a été bien sauvgarder");

        } catch (IOException error) {

            // en cas d'erreur affichier le message
            afficheur.printErrorText("[-] " + error.getMessage());
        }
    }
}
