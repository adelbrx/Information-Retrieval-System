package inf353;

import java.io.Serializable;

public class BTreeCoef implements BTree<DoCoef>, Serializable {
    /**
     * arbre de recherche binaire , chaque noeud est une cellule qui contient le
     * path du fichier ou ce trouve le mot et combien de fois il est répéter dans ce
     * fichier
     * 
     * @author squadSkatsquadSkat
     */

    public Cell<DoCoef> root;
    private Printer afficheur;

    /**
     * constructeur de la classe
     * 
     * @param path le path du fichier courant
     * @author squadSkat 
     */
    public BTreeCoef(String path) {

        this.root = new Cell<>(new DoCoef(path));
        this.afficheur = new Printer();
    }

    /**
     * insert un nouveau noeud dans notre un noed dans notre arbre
     * 
     * @param path   le path du fichier courant
     * @param parent le noeud parent du noeud a inserer
     * @author squadSkat
     */
    @Override
    public void insert(Cell<DoCoef> parent, Cell<DoCoef> Newpath) {

        if (parent == null) {

            this.root = Newpath;
        } else {

            int compareResult = Newpath.element.getPath().compareTo(parent.element.getPath());
            if (compareResult < 0) {

                parent.successorLeft = Newpath;
            } else if (compareResult > 0) {

                parent.successorRight = Newpath;
            }
        }
    }

    /**
     * recherche un noeud dans notre arbre
     * 
     * @param key le path du fichier a rechercher
     * @return une pair qui indique si le noeud est trouver ou non , si il est
     *         trouvé
     *         il retourne le noeud sinon il retourne null et indique le noeud
     *         parent
     * @author squadSkat
     */
    public Pair<Boolean, Cell<DoCoef>> search(String key) {

        if (this.root == null) {

            return new Pair<Boolean, Cell<DoCoef>>(false, null);
        } else {

            return searchRecursive(this.root, key, null);
        }
    }

    /**
     * recherche un noeud dans notre arbre
     * 
     * @param key le path du fichier a rechercher
     * @return une pair qui indique si le noeud est trouver ou non , si il est
     *         trouvé
     *         il retourne le noeud sinon il retourne null et indique le noeud
     *         parent
     * @author squadSkat
     */
    private Pair<Boolean, Cell<DoCoef>> searchRecursive(Cell<DoCoef> root, String key, Cell<DoCoef> parent) {

        if (root == null) {

            return new Pair<>(false, parent); // Not found, return false and parent
        }
        int compareResult = key.compareTo(root.element.getPath());
        if (compareResult == 0) {

            return new Pair<>(true, root);
        } else if (compareResult < 0) {

            return searchRecursive(root.successorLeft, key, root);
        } else {

            return searchRecursive(root.successorRight, key, root);
        }
    }

    /**
     * trouver le mot le plus grand du cette ABR
     * 
     * @param elementRoot la racine de l'arbre
     * @return Pair : key ==> le plus grand mot (le maximum) , value ==> le parent
     *         du plus grand mot
     * @author squadSkat
     */
    private Pair<Cell<DoCoef>, Cell<DoCoef>> biggestWord(Cell<DoCoef> elementRoot, Cell<DoCoef> parentRoot) {

        // si l'élément courant n'a pas un fils droite
        if (elementRoot.successorRight == null) {

            // alors on retourne l'élément courant et son parent
            return new Pair<Cell<DoCoef>, Cell<DoCoef>>(elementRoot, parentRoot);
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
    private Pair<Cell<DoCoef>, Cell<DoCoef>> smallestWord(Cell<DoCoef> elementRoot, Cell<DoCoef> parentRoot) {

        // si l'élément courant n'a pas un fils gauche
        if (elementRoot.successorLeft == null) {

            // alors on retourne l'élément courant et son parent
            return new Pair<Cell<DoCoef>, Cell<DoCoef>>(elementRoot, parentRoot);
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
    private Pair<Cell<DoCoef>, Cell<DoCoef>> biggestWordInSubTreeLeft(Cell<DoCoef> element) {

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
     * */
    private Pair<Cell<DoCoef>, Cell<DoCoef>> smallestWordInSubTreeRight(Cell<DoCoef> element) {

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
    public void remove(Cell<DoCoef> word, Cell<DoCoef> parentWord) {

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
                Pair<Cell<DoCoef>, Cell<DoCoef>> smallestRight = smallestWordInSubTreeRight(word);

                // on remplace le mot par le plus petit élément du sous arbre droite
                word.element = smallestRight.getKey().element;

                // on supprime le plus petit élément du sous arbre droite
                remove(smallestRight.getKey(), smallestRight.getValue());
            }
            // si le mot n'a pas une sous arbre à droite
            else if (word.successorRight == null) {

                // calculer le plus grand élément du sous arbre gauche et son parent
                Pair<Cell<DoCoef>, Cell<DoCoef>> biggestLeft = biggestWordInSubTreeLeft(word);

                // on remplace le mot par le plus grand élément du sous arbre gauche
                word.element = biggestLeft.getKey().element;

                // on supprime le plus petit élément du sous arbre droite
                remove(biggestLeft.getKey(), biggestLeft.getValue());
            }
        }

    }

    /**
     * modifier un fichier dans l'arbre
     * 
     * @param previousContent le fichier à remplacé
     * @param newContent      la nouvelle cellule qui représente le nouveau fichier
     * @author squadSkat
     */
    @Override
    public void update(DoCoef newContent, Cell<DoCoef> previousContent) {

        previousContent.element = newContent;
    }

    /**
     * afficher l'arbe par odre croissant (parcours infixe)
     * 
     * @param root la racine de l'arbre
     */
    public void displayTree(Cell<DoCoef> rootTree) {

        // si le sous arbre gauche n'est pas vide
        if (rootTree.successorLeft != null) {

            // alors on l'affiche
            displayTree(rootTree.successorLeft);
        }
        // affichier la racine de cette arbre
        afficheur.printWarningText(rootTree.element.getPath() + " " + rootTree.element.getCoefMot());
        // si le sous arbre droite n'est pas vide
        if (rootTree.successorRight != null) {
            // alors on l'affiche

            displayTree(rootTree.successorRight);
        }
    }

    public int countNodes() {
        return countNode(root);
    }

    private int countNode(Cell<DoCoef> root) {
        if (root == null) {
            return 0;
        } else {

            // Count this node as well
            return 1 + countNode(root.successorLeft) + countNode(root.successorRight);
        }
    }
}
