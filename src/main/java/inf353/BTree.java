package inf353;

public interface BTree<T> {
    /* arbre de recherche binaire basique */

    /**
     * é.i: arbre sans element
     * é.f.: arbre avec un element
     * 
     * @author squadSkat
     */
    public void insert(Cell<T> element, Cell<T> parentElement);

    /**
     * recherche si un element existe dans l'arbre/renvoie null si l'element
     * n'existe pas
     * 
     * @param element a rechercher
     * @author squadSkat
     */
    public Pair<Boolean, Cell<T>> search(String element);

    /**
     * é.i: arbre avec un element
     * é.f.: arbre sans cet element
     * 
     * @param element a supprimer
     * @author squadSkat
     */

    public void remove(Cell<T> element, Cell<T> parentElement);

    /**
     * mis a jour le contenu d'un elemnt dans notre arbre
     * 
     * @param content le nouveau contenu, element l'element a modifier
     * @author squadSkat
     */
    public void update(T newContent, Cell<T> previousContent);

}