package inf353;

import java.io.Serializable;

public class BTreeWord implements BTree<Word>, Serializable {

    public Cell<Word> root;
    private Printer afficheur;

    /**
     * Consutructeur
     * 
     * @param root : la racine de l'arbre
     * @author squadSkat
     */
    public BTreeWord(Cell<Word> root) {

        this.root = root;
        this.afficheur = new Printer();
    }

    /**
     * insérer un mot dans l'arbre où l'arbre sera trié (arbre binaire de recherche)
     * après l'insertion
     * 
     * @param newWord    : la cellule qui représente le nouveau mot
     * @param parentWord : le parent du mot qu'on veux l'insérer (la position
     *                   exacte)
     * @author squadSkat
     */
    @Override
    public void insert(Cell<Word> newWord, Cell<Word> parentWord) {

        // si l'arbre n'a pas de root
        if (parentWord == null) {

            // création du root
            this.root = newWord;
        }
        // si le mot parent n'a pas de fils
        else if (parentWord.successorLeft == null && parentWord.successorRight == null) {

            // comparer le nouveau mot avec le mot parent
            int compareResult = newWord.element.getWord().compareTo(parentWord.element.getWord());
            // si le nouveau mot est superieure au mot parent
            if (compareResult > 0) {

                // le nouveau mot sera le fils droite du mot parent
                parentWord.successorRight = newWord;
            }
            // si le nouveau mot est inférieure au mot parent
            else if (compareResult < 0) {

                // le nouveau mot sera le fils gauche du mot parent
                parentWord.successorLeft = newWord;

            }
        }
        // si le mot parent parent a un fils gauche et n'a pas un fils droite
        else if (parentWord.successorLeft != null && parentWord.successorRight == null) {

            // le nouveau mot devient le fils doite du mot parent
            parentWord.successorRight = newWord;
        }
        // si le mot parent parent a un fils doite et n'a pas un fils gauche
        else if (parentWord.successorLeft == null && parentWord.successorRight != null) {

            // le nouveau mot devient le fils gauche du mot parent
            parentWord.successorLeft = newWord;
        }
    }

    /**
     * chercher récursivement dans une arbre (si l'élément est trouvé on retourne
     * l'élément sinon on retourne leur parent)
     * le champ boolean du tuple (key) indique si le mot est trouvé ou pas
     * le deuxième champ du tuple (value) retourne la cellule du mot c'est le mot
     * est trouvé sinon elle retourne leur parent
     * 
     * @param word           le mot cherché
     * @param currentCell    la cellule courante
     * @param worparentCelld le parent de la cellule courante
     * @return Pair : un tuple où le premier champt est un boolean qui représente si
     *         l'élément est trouvé ou pas et le deuxième champ et dépand
     *         du premier champ donc il est l'élément cherché si le premier champ
     *         est vrai sinon c'est le parent où on peux insérer l'élément cherché
     * @author squadSkat
     */
    private Pair<Boolean, Cell<Word>> searchRec(String word, Cell<Word> currentCell, Cell<Word> parentCell) {

        // si le parent de la cellule courante n'a pas de fils
        if (currentCell == null) {

            // le cas où la cellule n'est pas trouvée
            return new Pair<Boolean, Cell<Word>>(false, parentCell);
        }

        // si le parent de la cellule courante a des fils
        else {

            // on compare la valeur du mot de la cellule courante avec le mot cherché
            int compareResult = currentCell.element.getWord().compareTo(word);
            // si les mots sont égaux
            if (compareResult == 0) {

                return new Pair<Boolean, Cell<Word>>(true, currentCell);
            }
            // si le mot de la cellule courante est inférieure au mot cherché
            else if (compareResult < 0) {

                return searchRec(word, currentCell.successorRight, currentCell);
            }
            // si le mot de la cellule courante est superieure au mot cherché
            else {

                return searchRec(word, currentCell.successorLeft, currentCell);
            }
        }
    }

    /**
     * chercher un mot dans l'arbre
     * 
     * @param word le mot cherché
     * @return Pair : un tuple où le premier champt est un boolean qui représente si
     *         l'élément est trouvé ou pas et le deuxième champ et dépand
     *         du premier champ donc il est l'élément cherché si le premier champ
     *         est vrai sinon c'est le parent où on peux insérer l'élément cherché
     * @author squadSkat
     */
    @Override
    public Pair<Boolean, Cell<Word>> search(String word) {

        // si l'arbre est vide
        if (this.root == null) {

            return new Pair<Boolean, Cell<Word>>(false, null);
        }
        // si l'arbre n'est pas vide
        else {

            // commancer la recherche depuis le root de l'arbre
            return searchRec(word, this.root, null);
        }

    }

    /**
     * modifier un mot dans l'arbre
     * 
     * @param content le mot à remplacé
     * @param newWord la nouvelle cellule qui représente le nouveau mot
     * @author squadSkat
     */
    @Override
    public void update(Word newWord, Cell<Word> previousWord) {

        previousWord.element = newWord;
    }

    /**
     * trouver le mot le plus grand du cette ABR
     * 
     * @param elementRoot la racine de l'arbre
     * @return Pair : key ==> le plus grand mot (le maximum) , value ==> le parent
     *         du plus grand mot
     * @author squadSkat
     */
    private Pair<Cell<Word>, Cell<Word>> biggestWord(Cell<Word> elementRoot, Cell<Word> parentRoot) {

        // si l'élément courant n'a pas un fils droite
        if (elementRoot.successorRight == null) {

            // alors on retourne l'élément courant et son parent
            return new Pair<Cell<Word>, Cell<Word>>(elementRoot, parentRoot);
        }
        // si l'élément courant a un fils droite
        else {

            // alors on prend le plus grand élément du sous arbre droite et son parent
            return biggestWord(elementRoot.successorRight, elementRoot);
        }
    }

    /**
     * trouver le mot le plus petit du cette ABR
     * 
     * @param elementRoot la racine de l'arbre
     * @return Pair : key ==> le plus petit mot (le minimum) , value ==> le parent
     *         du plus petit mot
     * @author squadSkat
     */
    private Pair<Cell<Word>, Cell<Word>> smallestWord(Cell<Word> elementRoot, Cell<Word> parentRoot) {

        // si l'élément courant n'a pas un fils gauche
        if (elementRoot.successorLeft == null) {

            // alors on retourne l'élément courant et son parent
            return new Pair<Cell<Word>, Cell<Word>>(elementRoot, parentRoot);
        }
        // si l'élément courant a un fils gauche
        else {

            // alors on prend le plus petit élément du sous arbre gauche et son parent
            return smallestWord(elementRoot.successorLeft, elementRoot);
        }
    }

    /**
     * trouver le plus grand mot du sous arbre gauche
     * 
     * @param element la racine (le premier mot)
     * @return Pair : key ==> le plus grand mot du sous arbre gauche , value ==> son
     *         parent
     * @author squadSkat
     */
    private Pair<Cell<Word>, Cell<Word>> biggestWordInSubTreeLeft(Cell<Word> element) {

        // si l'élément courant courant est vide
        if (element == null) {

            return null;
        }
        // sinon
        else {

            // on cherche le mot le plus grand du sous arbre gauche
            return biggestWord(element.successorLeft, element);
        }
    }

    /**
     * trouver le plus petit mot du sous arbre droite
     * 
     * @param element la racine (le premier mot)
     * @return Pair : key ==> le plus petit mot du sous arbre droite , value ==> son
     *         parent
     * @author squadSkat
     */
    private Pair<Cell<Word>, Cell<Word>> smallestWordInSubTreeRight(Cell<Word> element) {

        // si l'élément courant courant est vide
        if (element == null) {

            return null;
        }
        // sinon
        else {

            // on cherche le mot le plus petit du sous arbre droite
            return smallestWord(element.successorRight, element);
        }
    }

    /**
     * supprimer un mot dans l'arbre tel que l'arbre sera triée après la suppression
     * 
     * @param word le mot à supprimé
     * @author squadSkat
     */
    @Override
    public void remove(Cell<Word> word, Cell<Word> parentWord) {

        // si le mot qu'on veux supprimer est une feuille
        if (word.successorLeft == null && word.successorRight == null) {

            // si le mot qu'on veux supprimer est le fils gauche de son parent et le mot
            // qu'on veux supprimer est une feuille
            if (parentWord.successorLeft == word) {

                // alors on supprime l'élément gauche du parent
                parentWord.successorLeft = null;
            }
            // si le mot qu'on veux supprimer est le fils droite de son parent et le mot
            // qu'on veux supprimer est une feuille
            else if (parentWord.successorRight == word) {

                // alors on supprime l'élément gauche du parent
                parentWord.successorRight = null;
            }
        }
        // si le mot qu'on veux supprimer n'est pas une feuille
        else {

            // si le mot n'a pas une sous arbre à gauche
            if (word.successorLeft == null) {

                // calculer le plus petit élément du sous arbre droite et son parent
                Pair<Cell<Word>, Cell<Word>> smallestRight = smallestWordInSubTreeRight(word);

                // on remplace le mot par le plus petit élément du sous arbre droite
                word.element = smallestRight.getKey().element;

                // on supprime le plus petit élément du sous arbre droite
                remove(smallestRight.getKey(), smallestRight.getValue());
            }
            // si le mot n'a pas une sous arbre à droite
            else if (word.successorRight == null) {

                // calculer le plus grand élément du sous arbre gauche et son parent
                Pair<Cell<Word>, Cell<Word>> biggestLeft = biggestWordInSubTreeLeft(word);

                // on remplace le mot par le plus grand élément du sous arbre gauche
                word.element = biggestLeft.getKey().element;

                // on supprime le plus petit élément du sous arbre droite
                remove(biggestLeft.getKey(), biggestLeft.getValue());
            }
        }

    }

    /**
     * afficher l'arbe par odre croissant (parcours infixe)
     * 
     * @param root la racine de l'arbre
     * @author squadSkat
     */
    public void displayTree(Cell<Word> rootTree) {

        // si le sous arbre gauche n'est pas vide
        if (rootTree.successorLeft != null) {

            // alors on l'affiche
            displayTree(rootTree.successorLeft);
        }
        // affichier la racine de cette arbre
        afficheur.printWarningText(rootTree.element.getWord());
        // si le sous arbre droite n'est pas vide
        if (rootTree.successorRight != null) {

            // alors on l'affiche
            displayTree(rootTree.successorRight);
        }
    }
}
