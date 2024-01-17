package inf353;

public class BTreeCoefTest {
    public static void main(String[] args) {
        // Creating a BTreeCoef instance with a root node
        BTreeCoef bTree = new BTreeCoef("pathA");

        // Inserting nodes into the tree
        Cell<DoCoef> pathB = new Cell<>(new DoCoef("pathB"));
        bTree.insert(bTree.root, pathB);
        Cell<DoCoef> path = new Cell<>(new DoCoef("path"));
        System.err.println("test" + bTree.root.successorRight.element.getPath());
        bTree.insert(bTree.root, path);
        System.err.println("test" + bTree.root.element.getPath());
        System.err.println("test" + bTree.root.successorLeft.element.getPath());
        // Searching for a node in the tree
        String searchKey = "pathB";
        Pair<Boolean, Cell<DoCoef>> searchResult = bTree.search(searchKey);

        if (searchResult.getKey()) {
            System.out.println("Node with path " + searchKey + " found in the tree.");
        } else {
            System.out.println("Node with path " + searchKey +
                    " not found in the tree.");
        }

        // Updating the coefficient of a node in the tree

        if (searchResult.getKey()) {
            Cell<DoCoef> foundNode = searchResult.getValue();
            foundNode.element.setCoefMot(2);
            bTree.update(foundNode.element, foundNode);
            System.out.println("Coefficient of the node with path " + searchKey +
                    " updated to: " +
                    foundNode.element.getCoefMot());
        }

    }
}
