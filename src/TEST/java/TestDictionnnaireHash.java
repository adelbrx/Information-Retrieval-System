package inf353;

import static org.junit.Assert.*;

import java.beans.Transient;

import inf353.DictionnaireHash;
import inf353.BTreeWord;
import inf353.DictionnaireNaif;
import inf353.LecteurDocumentNaif;
import org.junit.Test;

public class TestDictionnnaireHash {

    // //Cas de test pour la construction du dictionnaire
    // @Test
    // public void testConstructionDictionnaire(){
    //     String pathDirectory = "src\\main\\java\\inf353\\ressources\\echantillon_100";
    //     String filePath = "src\\main\\java\\inf353\\ressources";
    //     DictionnaireHash dictionnaire = new DictionnaireHash(pathDirectory, filePath);

    //     // Vérifier que le dictionnaire n'est pas null
    //     assertNotNull(dictionnaire);

    //     // Vérifier que le tableau de mots n'est pas null
    //     assertNotNull(dictionnaire.dictionnary);

    //     // Vérifier que le chemin du répertoire est correctement défini
    //     assertEquals(pathDirectory, dictionnaire.pathDirectory);

    //     // Vérifier que la liste des mots inutiles (stoplist) est initialisée
    //     assertNotNull(dictionnaire.stopList);
    // }

    // //Cas de test pour la lecture du dictionnaire
    // @Test
    // public void testLectureDictionnaire() {
    //     String filePath = "src\\main\\java\\inf353\\ressources";
    //     DictionnaireHash dictionnaire = new DictionnaireHash(filePath);

    //     // Vérifier que le dictionnaire n'est pas null
    //     assertNotNull(dictionnaire);

    //     // Vérifier que le tableau de mots n'est pas null
    //     assertNotNull(dictionnaire.dictionnary);

    //     // Vérifier que le chemin du répertoire est correctement défini
    //     assertNotNull(dictionnaire.pathDirectory);

    //     // Vérifier que la liste des mots inutiles (stoplist) est initialisée
    //     assertNotNull(dictionnaire.stopList);
    // }

    // //Cas de test pour la recherche de mots dans la liste des mots inutiles (stoplist): la methode buildStopList
    // @Test
    // public void testBuildStopList() {
    //     DictionnaireHash dictionnaire = new DictionnaireHash("src\\main\\java\\inf353\\ressources\\echantillon_100", "src\\main\\java\\inf353\\ressources");

    //     // Appele de la méthode buildStopList en utilisant le fichier de stoplist spécifié
    //     dictionnaire.buildStopList();

    //     // Vérifier que la stoplist a été construite correctement
    //     assertNotNull(dictionnaire.stopList);

    //     // Vérifier que certains mots de la stoplist sont présents dans l'arbre des mots inutiles
    //     assertTrue(dictionnaire.stopList.search("le").getKey());
    //     assertTrue(dictionnaire.stopList.search("et").getKey());
    //     assertTrue(dictionnaire.stopList.search("de").getKey());

    //     // Vérifier que des mots qui ne sont pas dans la stoplist ne sont pas présents
    //     assertFalse(dictionnaire.stopList.search("Gouvernement").getKey());
    //     assertFalse(dictionnaire.stopList.search("nonexistent").getKey());
    // }

    // //Cas de test pour la recherche de mots dans le dictionnaire
    // @Test
    // public void testRechercheMotDansDictionnaire() {
    //     String filePath = "src\\main\\java\\inf353\\ressources";
    //     DictionnaireHash dictionnaire = new DictionnaireHash(filePath);
    //     assertNotNull(dictionnaire);

    //     // Rechercher des mots qui devraient être présents dans le dictionnaire
    //     int i=Math.abs("publications".hashCode() % 200000);
    //     int j=Math.abs("avertissements".hashCode() % 200000);
    //     int k=Math.abs("protection".hashCode() % 200000);
    //     int l=Math.abs("ThangBadBoy".hashCode() % 200000);
    //     int m=Math.abs("absentDansLeDict".hashCode() % 200000);
    //     assertTrue(dictionnaire.dictionnary[i].search("publications").getKey());
    //     assertTrue(dictionnaire.dictionnary[j].search("avertissements").getKey());
    //     assertTrue(dictionnaire.dictionnary[k].search("protection").getKey());

    //     // Vérifier que des mots qui ne devraient pas être présents ne sont pas trouvés
    //     if(dictionnaire.dictionnary[l]!=null){
    //     assertFalse(dictionnaire.dictionnary[l].search("ThangBadBoy").getKey());}

    //      if(dictionnaire.dictionnary[m]!=null){
    //     assertFalse(dictionnaire.dictionnary[m].search("absentDansLeDict").getKey());}
    // }

    // //Cas de test pour la sérialisation et la désérialisation
    // @Test
    // public void testSerializationAndDeserialization() {
    //     String directoryPath = "src\\main\\java\\inf353\\ressources\\echantillon_100";
    //     // le chemin du fichier de test pour la sérialisation
    //     String filePath = "src\\main\\java\\inf353\\ressources";

    //     // instance de DictionnaireHash en utilisant le constructeur de traitement de corpus
    //     DictionnaireHash dictionnaire = new DictionnaireHash(directoryPath, filePath);

    //     assertNotNull(dictionnaire);

    //     // Sérialiser le dictionnaire
    //     dictionnaire.saveDataStructures(filePath);

    //     // une nouvelle instance de DictionnaireHash en utilisant le constructeur de lecture
    //     DictionnaireHash nouveauDictionnaire = new DictionnaireHash(filePath);

    //     // Vérifier que le nouveau dictionnaire n'est pas null
    //     assertNotNull(nouveauDictionnaire);

    //     // Vérifier que les données du nouveau dictionnaire correspondent aux données d'origine
    //     assertEquals(dictionnaire.dictionnary[0], nouveauDictionnaire.dictionnary[0]);
    //     assertEquals(dictionnaire.pathDirectory, nouveauDictionnaire.pathDirectory);
    // }
}
