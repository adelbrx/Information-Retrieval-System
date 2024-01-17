package inf353;

import java.io.Serializable;

public class Cell<T> implements Serializable {

    public T element;
    public Cell<T> successorLeft;
    public Cell<T> successorRight;

    /**
     * Constructeur 1 (si aucune chose n'est défini)
     * 
     * @author squadSkat
     */
    public Cell() {

        this.element = null;
        this.successorLeft = null;
        this.successorRight = null;
    };

    /**
     * Constructeur 2 (si l'élément de la cellule est défini)
     * 
     * @param element : l'élément de la cellule
     * @author squadSkat
     */
    public Cell(T element) {

        this.element = element;
        this.successorLeft = null;
        this.successorRight = null;
    };

    /**
     * Constructeur 3 (si les successeurs de la cellule sont défini)
     * 
     * @param successorLeft  : le successeur gauche de la cellule
     * @param successorRight : le successeur droite de la cellule
     * @author squadSkat
     */
    public Cell(Cell<T> successorLeft, Cell<T> successorRight) {

        this.element = null;
        this.successorLeft = successorLeft;
        this.successorRight = successorRight;
    };

    /**
     * Constructeur 4 (si l'élément et les successeurs de la cellule et sont défini)
     * 
     * @param element        : l'élément de la cellule
     * @param successorLeft  : le successeur gauche de la cellule
     * @param successorRight : le successeur droite de la cellule
     * @author squadSkat
     */
    public Cell(T element, Cell<T> successorLeft, Cell<T> successorRight) {

        this.element = element;
        this.successorLeft = successorLeft;
        this.successorRight = successorRight;
    };

}
