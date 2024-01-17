package inf353;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Query {
    
    public String query_id;
    public String run_id;
    public DictionnaireHash dictionnary;
    public String query;
    private Printer afficheur;
    private FileWriter ecrivain;


    /**
     * Constructeur de la classe query
     * @param query_id :  identificateur de la requête
     * @param run_id : un identificateur donnant le nom de l'expérience
     * @param dictionnary : le dictionnaire où on fait le recherche
     * @param query : la requêtesans les mot inutiles
     * @author squadSkat
     */
    public Query(String query_id , String run_id , DictionnaireHash dictionnary , String query , FileWriter ecrivain){

        //initialisation des attributs 
        this.query_id = query_id;
        this.run_id = run_id;
        this.dictionnary = dictionnary;
        this.query = query;
        this.afficheur = new Printer();
        this.ecrivain = ecrivain;

        //executer la reqête 
        executeQuery(query);
        //sauvgarder le résultat dans un fichier
        buildQRelsFile();

    }



    /**
     * extraire le nom de fichier à partir d'un chemin
     * @param pathFile : le chemin du fichier
     * @return le nom du fichier
     * @author squadSkat
     */
    public String getFileName(String pathFile){

        //diviser le chemin par rapport à /
        String[] directions = pathFile.split("/");
        //retourner le nom du mot 
        return directions[directions.length - 1];
    }



    /**
     * executer la requête
     * @param query : la requête sans des mots inutiles
     * @return : un tuple du nom de fichier et le pertinance
     * @author : Ahmed Adel BEREKSI REGUIG
     */
    public Pair<String[] , double[]> executeQuery(String query){

        //rechercher la requête 
        Recherche recherche = new Recherche(query , this.dictionnary);
        //calculer la taille de résultat;
        int size = recherche.getResultSearch().size();
        //la liste des document retournées dans la requête 
        String[] resultDocuments = new String[ size ];
        //les nombres de similarité pour chacun  des document retournées dans la requête 
        double[] resultPertinances = new double[ size ];
        //la position du curseur 
        int position = 0;
        //remplir les deux tableau au même temp
        for (DoCoef result : recherche.getResultSearch()){

            //stocker le document dans son tableau 
            resultDocuments[position] = getFileName(result.getPath());
            //stocker la pertinance dans son tableau 
            resultPertinances[position] = result.getCoefMot();
            //incrémenter la position
            position++;
        }

        return new Pair<String[] , double[]>(resultDocuments , resultPertinances);
    }



    /**
     * sauvgarder le resultat de la recherche dans un fichier
     * @param pathQRelsFile : le chemin du fichier
     * @author squadSkat
     */
    public void buildQRelsFile(){

        //executer la requête
        Pair<String[] , double[]> resultQuery = executeQuery(this.query);
        //la liste des document retournées dans la requête 
        String[] resultDocuments = resultQuery.getKey();
        //les nombres de similarité pour chacun  des document retournées dans la requête 
        double[] resultPertinances = resultQuery.getValue();
        //la variable rank représente le rand des documents
        int rank = 0;
        //pour chaque document retournée
        while (rank < resultDocuments.length){

            //créer la ligne de la requête 
            String line = this.query_id + "\t0\t" + getFileName(resultDocuments[rank]) + "\t" + rank + "\t" + String.valueOf(resultPertinances[rank]) + "\t" + this.run_id + "\n"; 
            try {
                
                //écrire la ligne dans le fichier
                this.ecrivain.write(line);

            } catch (IOException e) {
                
                // en cas d'erreur affichier un message
                afficheur.printErrorText("[-] Erreur dans la préparation à la l'écriture");
            }

            //incrémenter le rang
            rank++; 
        }
    }
}
