package inf353;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class Recherche {

    private DictionnaireHash dictionnary;
    private String requestUser;
    private List<DoCoef> resultSearch;

    /**
     * Constructeur
     * 
     * @param request : la requête entrée par l'utilisateur
     * @author squadSkat
     */
    Recherche(String request , DictionnaireHash dictionnary) {

        this.dictionnary = dictionnary;
        this.requestUser = request.toLowerCase();
        String cleanedQuery =this.removeUselessWords();
        System.out.println("Requête nettoyée : " + cleanedQuery);
        List<DoCoef[]> lista = this.GetWordsArrays(cleanedQuery, dictionnary);
        this.resultSearch = this.getFileArray(lista);

    }



    /**
     * getter du résultat de la recherche
     * @return liste des fichier de type DoCoef
     * @author squadSkat
     */
    public List<DoCoef> getResultSearch(){

        return this.resultSearch;
    }
    

    /**
     * supprimer les mot inutiles dans la requête entrée par l'utilisateur
     * 
     * @return unUselessRequest : la nouvelle requête sans les mots inutiles
     * @author squadSkat
     */
    public String removeUselessWords() {

        String[] words = this.requestUser.split("[\\s?.,']+"); // Utilisez "\\s+" pour diviser par espace

        // Créer une liste contenant les mots de la stoplist

        // Créer une nouvelle liste pour stocker les mots utiles
        List<String> usefulWords = new ArrayList<>();

        // Comparer chaque mot avec les mots de la stoplist
        for (String word : words) {
             //Supprimer les accents et caractères spéciaux
            String normalizedWord = normalizeString(word).toLowerCase();
             //Si le mot n'est pas dans la stoplist, l'ajouter à la liste des mots utiles
            if (!this.dictionnary.stopList.search(word).getKey()) {
                usefulWords.add(word.toLowerCase());
            }
        }

        // Construire la nouvelle requête après avoir supprimé les mots inutiles
        String unUselessRequest = String.join(" ", usefulWords);
        unUselessRequest = returnFull(unUselessRequest, loadAbbreviations(System.getProperty("user.dir") + "/abr.txt"));
        unUselessRequest = processSynonyms(unUselessRequest,
                loadSynonyms(System.getProperty("user.dir") + "/syno.txt"));
        // Retourner la nouvelle requête
//String unUselessRequest=returnFull(this.requestUser, loadAbbreviations(System.getProperty("user.dir") + "/syno.txt"));
        return removeRepeatedWords(unUselessRequest.toLowerCase());
    }



    /**
     * Normalise une chaîne de caractères en supprimant les diacritiques (accents)
     * et d'autres caractères spéciaux.
     *
     * @param input La chaîne de caractères à normaliser.
     * @return La chaîne normalisée sans diacritiques ni caractères spéciaux.
     */
    private String normalizeString(String input) {
        // Utiliser Normalizer pour supprimer les accents
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Utiliser une expression régulière pour supprimer les caractères non-ASCII
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }




    /*
     * retourne une liste avec tous les fichiers qui contient les mots de la requete
     * 
     * @param request : la requete entrée par l'utilisateur
     * 
     * @param dict2 : le dictionnaire
     * 
     * @return result : la liste des fichiers qui contient les mots de la requete
     * 
     ** @author squadSkat
     */

    public List<DoCoef[]> GetWordsArrays(String request, DictionnaireHash dict2) {
        String[] words = request.split("\\s+");
        List<DoCoef[]> wordsArrays = new ArrayList<>();
        for (String word : words) {
            BTreeWord bTreeWord = dict2.dictionnary[getPosition(word, 200000)];
            if (bTreeWord != null) {
                Pair<Boolean, Cell<Word>> pair = bTreeWord.search(word);
                if (pair.getKey()) {
                    wordsArrays.add(pair.getValue().element.getCoefArray());
                }
            }
        }
        return wordsArrays;

    }



    /*
     * retourne une liste avec tous les fichiers qui contient les mots de la requete
     * et leurs coefficients additionnés
     * 
     * @param wordsArrays : la liste des fichiers qui contient les mots de la
     * requete
     * 
     * @return result : la liste des fichiers qui contient les mots de la requete et
     * leurs coefficients additionnés
     * 
     ** @author squadSkat
     */
    public List<DoCoef> getFileArray(List<DoCoef[]> wordsArrays) {
        Map<String, Double> pathCoefMap = new HashMap<>();
        List<DoCoef> result = new ArrayList<>();

        for (DoCoef[] array : wordsArrays) {
            for (DoCoef doCoef : array) {
                String path = doCoef.getPath();

                if (pathCoefMap.containsKey(path)) {
                    double currentCoef = pathCoefMap.get(path) + doCoef.getCoefMot();
                    pathCoefMap.put(path, currentCoef);

                } else {
                    pathCoefMap.put(path, doCoef.getCoefMot());
                }
            }
        }

        for (Map.Entry<String, Double> entry : pathCoefMap.entrySet()) {
            String path = entry.getKey();
            Double aggregatedCoef = entry.getValue();
            result.add(new DoCoef(path, aggregatedCoef));
        }
        Collections.sort(result, Comparator.comparingDouble(DoCoef::getCoefMot).reversed());

        return result.subList(0, Math.min(result.size(),200));
    }



    private int getPosition(String word, int i) {
        return Math.abs(word.hashCode() % i);
    }

    public Map<String, String> loadAbbreviations(String filePath) {
        Map<String, String> abbreviations = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String abbreviation = parts[0].trim();
                    String fullForm = parts[1].trim();
                    abbreviations.put(abbreviation, fullForm);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return abbreviations;
    }

    public String findFullForm(String word, Map<String, String> abbreviations) {
        return abbreviations.get(word);
    }

    public String returnFull(String request, Map<String, String> map) {
        String[] words = request.split("[\\s']+");
        List<String> usefulWords = new ArrayList<>();
        for (String word : words) {
            if (findFullForm(word, map) != null)
                usefulWords.add(findFullForm(word, map));
        }
        return this.requestUser = request.concat(" ").concat(String.join(" ", usefulWords));

    }

    public Map<String, List<String>> loadSynonyms(String filePath) {
        Map<String, List<String>> synonyms = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 2) {
                    String word = parts[0].trim();
                    String[] synonymArray = parts[1].trim().split(",\\s*");

                    // Convert the array to a List
                    List<String> synonymList = new ArrayList<>();
                    for (String synonym : synonymArray) {
                        synonymList.add(synonym);
                    }

                    synonyms.put(word, synonymList);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return synonyms;
    }

    public String processSynonyms(String request, Map<String, List<String>> synonymsMap) {
        String[] words = request.split("[\\s']+");
        List<String> usefulWords = new ArrayList<>();

        for (String word : words) {
            List<String> synonyms = findSynonyms(word, synonymsMap);
            if (synonyms != null && synonyms.size() < 4) {
                usefulWords.addAll(synonyms);
                usefulWords.add(word);

            } else {
                usefulWords.add(word);
            }
        }
        return String.join(" ", usefulWords);
    }

    private List<String> findSynonyms(String word, Map<String, List<String>> synonymsMap) {
        return synonymsMap.get(word);
    }

    public static String removeRepeatedWords(String sentence) {
        // Split the sentence into an array of words
        String[] words = sentence.split("\\s+");

        // Create a set to store unique words
        Set<String> uniqueWords = new HashSet<>();

        // Use a StringBuilder to build the modified sentence
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            // Check if the word is not already in the set
            if (uniqueWords.add(word.toLowerCase())) {
                result.append(word).append(" ");
            }
        }

        // Remove the trailing space and return the result
        return result.toString().trim();
    }
}
