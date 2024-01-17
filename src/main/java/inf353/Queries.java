package inf353;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class Queries {

    private String pathQueries;
    private DictionnaireHash dictionnary;
    private int query_id;
    private Printer afficheur;
    private FileWriter ecrivain;
    

    /**
     * constructeur qui execute toutes les requêtes
     * @param pathQueries : le chemin de fichier qui contient les requêtes
     * @author squadSkat
     */
    public Queries(String pathQueries){

        this.pathQueries = pathQueries;
        this.dictionnary = new DictionnaireHash(System.getProperty("user.dir"));
        this.query_id = 1;
        this.afficheur = new Printer();

        try {

            //préparer le fichier pour l'écriture 
            File qRelsFile = new File(System.getProperty("user.dir") + "/qrelsfileVF200.txt");
            //préparer l'écrivain du fichier
            this.ecrivain = new FileWriter(qRelsFile);
            System.out.println("pathQueries");
        } catch (IOException e) {

            // en cas d'erreur affichier un message
            afficheur.printErrorText("[-] Erreur dans la préparation à la l'écriture");
        }

            //traiter la requête
            processRequests(pathQueries); 
            //terminer l'écriture
            try {
                this.ecrivain.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
    private void processFile(File fileRequest) {

        //prendre le nom du fichier 
        String fileName = fileRequest.getName();
        //si le nom a une extension
        if (fileName.lastIndexOf('.') != -1) {
            // supprimer l'extension
            fileName = fileName.substring(0, fileName.lastIndexOf('.'));
        } 
        //tester si le fichier est un fichier de requête
        if (fileName.startsWith("C") && !fileName.equals("qrels_french")){

            // lire le fichier
            FileManipulator fileManipulator = new FileManipulator( fileRequest.getAbsolutePath() );
            //lire le contenu
            String contentQuery = fileManipulator.readContent();
            //executer la requête
            new Query(String.valueOf( Integer.valueOf(fileName.substring(1,fileName.length()))), "TEST_" + this.query_id, this.dictionnary, contentQuery , this.ecrivain);
            //changer query_id
            this.query_id++;

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

        if (content != null) {

            Arrays.sort(content, new Comparator<File>() {

                @Override
                public int compare(File file1, File file2) {
                    
                    return file1.getName().compareTo(file2.getName());
                }
            });
        }
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
     * traiter les fichiers des requêtes (traimenet les deux cas si le chemin est un répertoire ou
     * s'il est un fichier)
     * 
     * @param pathRequests : le chemin du répertoire générale
     * @author squadSkat
     */
    private void processRequests(String pathRequests) {

        // lire le path
        File directory = new File(pathRequests);

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
            this.afficheur.printErrorText("[-] Ce chemin n'existe pas");
        }
    }


}
