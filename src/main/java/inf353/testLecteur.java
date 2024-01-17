package inf353;

public class testLecteur {
    public static void main(String[] args) {

        Word word1 = new Word("1");
        Word word2 = new Word("2");
        Word word3 = new Word("3");
        Word word4 = new Word("4");
        Word word5 = new Word("5");
        Word word6 = new Word("6");
        Word word7 = new Word("7");
        Word word8 = new Word("8");

        Cell<Word> cell1 = new Cell<>(word1);
        Cell<Word> cell2 = new Cell<>(word2);
        Cell<Word> cell3 = new Cell<>(word3);
        Cell<Word> cell4 = new Cell<>(word4);
        Cell<Word> cell5 = new Cell<>(word5);
        Cell<Word> cell6 = new Cell<>(word6);
        Cell<Word> cell7 = new Cell<>(word7);
        Cell<Word> cell8 = new Cell<>(word8);

        BTreeWord arbre = new BTreeWord(null);
        arbre.insert(cell1, arbre.search(cell1.element.getWord()).getValue());
        arbre.insert(cell2, arbre.search(cell2.element.getWord()).getValue());
        arbre.insert(cell3, arbre.search(cell3.element.getWord()).getValue());
        arbre.insert(cell4, arbre.search(cell4.element.getWord()).getValue());
        arbre.insert(cell5, arbre.search(cell5.element.getWord()).getValue());
        arbre.remove(cell3, cell1);
        arbre.insert(new Cell<Word>(new Word("3")), arbre.search("3").getValue());
        arbre.displayTree(arbre.root);
        System.out.println(arbre.search("4").getKey());

    }
}
