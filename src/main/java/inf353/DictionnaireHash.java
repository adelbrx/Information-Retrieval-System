package inf353;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DictionnaireHash implements Serializable {

    public BTreeWord[] dictionnary;
    public String pathDirectory;
    public BTreeStopList stopList;
    private Printer afficheur;

    /**
     * Contructeur de la classe qui fait la création d'un dictionnaire de hashage
     * 
     * 
     * 
     * 
     * @param pathDirectory : le chemin du corpus
     * @param filePath      : le chemin où on veux stocker le dictionnaire de
     *                      hashage
     * @author squadSkat
     */
    public DictionnaireHash(String pathDirectory, String filePath) {

        // initialisation des attributs
        this.dictionnary = new BTreeWord[200000];
        this.pathDirectory = pathDirectory;
        this.stopList = new BTreeStopList(null);
        this.afficheur = new Printer();

        // contruire l'arbre (ABR) des mots inutiles
        buildStopList();
        // construire le dictionnaire
        processCorpus();
        // sauvgarder l'objet créer par cette classe
        saveDataStructures(filePath);
    }

    /**
     * Contructeur de la classe qui lit le dictionnaire de hashage à partir d'un
     * fichier
     * 
     * @param filePath : le chemin où le dictionnaire est stocké
     * @author squadSkat
     */
    public DictionnaireHash(String filePath) {
        // initialisation de l'afficheur
        this.afficheur = new Printer();

        // lire le dictionnaire
        DictionnaireHash dict = readDataStructure(filePath);

        // initialisation des attributs
        this.dictionnary = dict.dictionnary;
        this.pathDirectory = dict.pathDirectory;
        this.stopList = dict.stopList;

    }

    /**
     * construire la liste des mots inutiles dans une arbre binaire de recherche
     * (ABR)
     * 
     * @author squadSkat
     */
    public void buildStopList() {

        // initialiser le lecteur mot par mot
        LecteurDocumentNaif lecteur = new LecteurDocumentNaif(System.getProperty("user.dir") + "/stop_list.txt");
        // démmarer la lecture de fichier
        lecteur.demarrer();
        // tant que on la fichier n'a pas encore terminé
        while (!lecteur.finDeSequence()) {

            // stocker le mot dans une variable
            String word = lecteur.elementCourant().toLowerCase();
            // chercher si le mot existe déja dans l'arbre
            Pair<Boolean, Cell<String>> searchResult = this.stopList.search(word);
            // si le mot n'existe pas
            if (!searchResult.getKey()) {

                // on insère le mot dans l'abre
                this.stopList.insert(new Cell<String>(word), searchResult.getValue());
            }

            // avancer vers le mot suivant
            lecteur.avancer();
        }
    }

    /**
     * calculer la position d'un mot dans un tableau de n taille
     * tel que la position est correspondante à son hash
     * 
     * @param word : le mot
     * @param size : la taille du tableau
     * @return position : la position du mot dans le tableau
     */
    public int getPosition(String word, int size) {

        // la valeur absolue c'est pour avoir des valeurs positifs
        return Math.abs(word.hashCode() % size);
    }

    /**
     * insérer un mot dans la l'arbre de la case du dictionnaire
     * 
     * @param word        : le mot
     * @param currenfFile : le ficher courrant
     * @author squadSkat
     */
    private void processWord(String word, File currentFile) {

        // calculer la position
        int position = getPosition(word, 200000);
        // stocker dans une variable le pointeur de cette position qui représente une
        // arbre binaire de recherche
        // BTreeWord treeHashWords = this.dictionnary[position];
        // s'il y a pas d'arbre dans cette case du tebleau
        if (this.dictionnary[position] == null) {

            // initialisation de l'arbre
            this.dictionnary[position] = new BTreeWord(null);
        }
        // chercher le mot dans cette arbre des mot
        Pair<Boolean, Cell<Word>> searchingWordResult = this.dictionnary[position].search(word);
        // si le mot n'est pas trouvé
        if (searchingWordResult.getKey() == false) {

            // création de l'arbre des fichier (contient que le fichier courant)
            BTreeCoef treeFiles = new BTreeCoef(currentFile.getAbsolutePath());
            // création du nouveau mot
            Word newWord = new Word(word, treeFiles);
            // création la céllule du mot
            Cell<Word> cellWord = new Cell<>(newWord);
            // stocker dans une variable le mot
            Cell<Word> parentWord = searchingWordResult.getValue();
            // insérer le mot
            this.dictionnary[position].insert(cellWord, parentWord);
        }
        // si le mot n'est pas trouvé
        else {

            // stocker dans une variable le mot qu'on veux modifier (ajouter le document
            // courant s'il n'existe pas sinon on incrémente leur nombre d'occurence)
            Cell<Word> searchedWord = searchingWordResult.getValue();
            // stocker dans une variable l'arbre qui contient la liste des documents de ce
            // mot
            BTreeCoef treeFiles = searchedWord.element.getFiles();
            // stocker dans une variable le nom du fichier courant
            String currentFilePath = currentFile.getAbsolutePath();
            // chercher est ce que le document courant existe dans la liste des documents de
            // ce mot
            Pair<Boolean, Cell<DoCoef>> searchingFileResult = treeFiles.search(currentFilePath);
            // si le nom du fichier courant n'est pas trouvé
            if (searchingFileResult.getKey() == false) {

                // créer une nouvelle cellule pour ce fichier
                Cell<DoCoef> cellFile = new Cell<DoCoef>(new DoCoef(currentFilePath));
                // stocker dans une variable le parent de la courante cellule où on dois le
                // stocker
                Cell<DoCoef> parentFile = searchingFileResult.getValue();
                // insérer la nouvelle cellule qui représente ce document dans l'arbre des
                // documents de ce mot
                treeFiles.insert(parentFile, cellFile);
            }
            // si le nom du fichier courant est trouvé
            else {

                // stocker dans une variable la courante trouvé qui représente ce document
                Cell<DoCoef> currentFindedFile = searchingFileResult.getValue();
                // incrémenter le nombre d'occurence de ce mot dans ce fichier courant
                currentFindedFile.element.setCoefMot(currentFindedFile.element.getCoefMot() + 1);
            }
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
    private void processFile(File file) {

        // lire le fichier
        LecteurDocumentNaif lecteur = new LecteurDocumentNaif(file.getAbsolutePath());

        // démmarer la lecture
        lecteur.demarrer();

        // parcourir tous les mots de ce fichier
        while (!lecteur.finDeSequence()) {

            // stocker le mot dans une variable en miniscule
            String word = lecteur.elementCourant().toLowerCase();
            // si le mot n'est pas dans la liste des mot inutiles et le mot n'est pas vide
            if (!word.equals("") && !(word.length() < 1 && word.matches("[a-zA-Z]+"))
                    && !this.stopList.search(word).getKey()) {
                // traiter le mot (en miniscule)
                processWord(word, file);
            }
            // aller au mot suivanet
            lecteur.avancer();
        }
    }

    /**
     * parcours et traitement d'un répertoire
     * 
     * @param directory : le chemin du répertoire courant
     * @author squadSkat
     */
    private void processDirectory(File directory) {

        // stocker le contenu de ce répertoire dans un tableau (chaque case représente
        // le nom du fichier ou d'un répertoire)
        File[] content = directory.listFiles();

        // si le répertoire donnée contient au moin un fichier ou un autre répertoire
        if (content != null) {

            // Parcourir chaque fichier et répertoire dans ce tableau
            for (File element : content) {

                // si l'élément courant est un répertoire
                if (element.isDirectory()) {

                    // traiter ce répertoire
                    processDirectory(element);

                }
                // si l'élément courant est un fichier
                else if (element.isFile()) {

                    // traiter ce fichier
                    processFile(element);
                }
            }
        }
    }

    /**
     * traiter le corpus (traimenet les deux cas si le corpus est un répertoire ou
     * s'il est un fichier)
     * 
     * @param directoryPath : le chemin du répertoire générale
     * @author squadSkat
     */
    private void processCorpus() {

        // lire le path
        File directory = new File(this.pathDirectory);

        // si le path existe et représente un répertoire
        if (directory.exists()) {

            // s'il est un répertoire
            if (directory.isDirectory()) {

                // traiter les fichiers dans ce répertoire
                processDirectory(directory);
            }
            // s'il est un fichier
            else if (directory.isFile()) {

                // traiter les fichiers dans ce répertoire
                processFile(directory);
            }
        } else {

            // Affichage du message d'erreur
            afficheur.printErrorText("[-] Ce chemin n'existe pas");
        }
    }

    /**
     * sauvgarder cette structure de donnée (ce dictionnaire de hashage) par la
     * sérialisation
     * 
     * @param filePath : le chemin où on veux sauvgarder le dictionnaire de hashage
     * @author squadSkat
     */
    public void saveDataStructures(String filePath) {

        // Sérialisation ce dictionnaire dans un fichier
        try {

            // le chemin du fichier où on sauvgarde le dictionnaire de hashage
            FileOutputStream fileDict = new FileOutputStream(filePath + "/HashDictionnary.ser");
            // indiquer que l'objet transformé en bytes sera stocké dans ce fichier
            ObjectOutputStream oos = new ObjectOutputStream(fileDict);
            // transformer notre dictionnaire en bytes et le sauvgarder
            oos.writeObject(this);
            // message de succès
            this.afficheur.printSuccessText(
                    "[+] Le dictionnaire de hashage est bien sauvgarder dans le fichier HashDictionnary.ser par la sérialisation");

        } catch (IOException error) {

            // afficher un message d'erreur
            this.afficheur.printErrorText("[-] " + error.getMessage());
        }
    }

    /**
     * lire cette structure de donnée (ce dictionnaire de hashage) par la
     * désérialisation
     * 
     * @param pathDataStructure : le chemin où le dictionnaire est sauvgarder
     * @author squadSkat
     */
    private DictionnaireHash readDataStructure(String pathDataStructure) {

        try {

            // le fichier où notre dictionnaire est stocké
            FileInputStream fileDataStructure = new FileInputStream(pathDataStructure + "/HashDictionnary.ser");
            // lire la structure de données à partir des bytes
            ObjectInputStream ois = new ObjectInputStream(fileDataStructure);
            // lire le dictionnaire de byte vers un objet
            DictionnaireHash dictionnaireHash = (DictionnaireHash) ois.readObject();
            // message de succès
            this.afficheur.printSuccessText(
                    "[+] Le dictionnaire de hashage est bien lit à partir le fichier HashDictionnary.ser par la désérialisation");
            // retourner le dictionnaire
            return dictionnaireHash;
        } catch (IOException error) {

            // afficher un message d'erreur
            this.afficheur.printErrorText("[-] " + error.getCause());
        } catch (ClassNotFoundException error) {

            // afficher un message d'erreur
            this.afficheur.printErrorText("[-] " + error.getCause());
        }

        return null;
    }
}
