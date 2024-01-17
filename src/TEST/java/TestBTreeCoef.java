package inf353;

import static org.junit.Assert.*;

import java.beans.Transient;

import inf353.DictionnaireNaif;
import inf353.LecteurDocumentNaif;
import org.junit.Test;

public class TestBTreeCoef {

    // @Test
    // public void testInsert() {
    //     BTreeCoef bTree = new BTreeCoef("testPath");
    //     Cell<DoCoef> newNode = new Cell<>(new DoCoef("newPath"));
    //     bTree.insert(bTree.root, newNode);

    //     assertEquals(newNode, bTree.root.successorLeft); // Vérifie si le nouveau nœud a été inséré correctement
    // }
    // @Test
    // public void testSearchCheminExistant() {
    //     BTreeCoef bTree = new BTreeCoef("testPath");
    //     Pair<Boolean, Cell<DoCoef>> result = bTree.search("testPath");

    //     assertTrue(result.getKey()); // Vérifie si le chemin de fichier existe dans l'arbre
    //     assertEquals(bTree.root, result.getValue()); // Vérifie si le nœud trouvé est la racine
    // }

    // @Test
    // public void testSearchNonExistingKey() {
    //     BTreeCoef bTree = new BTreeCoef("testPath");
    //     Pair<Boolean, Cell<DoCoef>> result = bTree.search("nonExistingPath");

    //     assertFalse(result.getKey());
    //     assertEquals(bTree.root.element,result.getValue().element);
    // }

    // // Tests pour la méthode "remove"


    // // Suppression d'une feuille à gauche
    // @Test
    // public void testRemoveNoeudFeuilleLeft() {
    //     BTreeCoef bTree = new BTreeCoef("testPath");
    //     Cell<DoCoef> noeudFeuille = new Cell<>(new DoCoef("leafPath"));
    //     bTree.insert(bTree.root, noeudFeuille);

    //     // Vérifie que le nœud feuille existe avant la suppression
    //     assertNotNull(bTree.root.successorLeft);
    //     assertEquals(noeudFeuille, bTree.root.successorLeft);

    //     // Supprime le nœud feuille
    //     bTree.remove(noeudFeuille, bTree.root);

    //     // Vérifie que le nœud feuille a été supprimé
    //     assertNull(bTree.root.successorLeft);
    // }

    // //Suppression d'une feuille à droite
    // @Test
    // public void testRemoveNoeudFeuilleRight() {
    //     BTreeCoef bTree = new BTreeCoef("parentPath");
    //     Cell<DoCoef> noeudFeuille = new Cell<>(new DoCoef("rightChildPath"));
    //     bTree.root.successorRight = noeudFeuille;

    //     assertNotNull(bTree.root.successorRight);
    //     assertEquals(noeudFeuille, bTree.root.successorRight);

    //     bTree.remove(noeudFeuille, bTree.root);

    //     assertNull(bTree.root.successorRight);
    // }

    // //Suppression d'un nœud avec des enfant à gauche
    // @Test
    // public void testRemoveNoeudAvecEnfantsGauche() {
    //     BTreeCoef bTree = new BTreeCoef("parentPath");
    //     Cell<DoCoef> parentNode = new Cell<>(new DoCoef("leftChildPath"));
    //     Cell<DoCoef> noeudFilsGauche = new Cell<>(new DoCoef("leftChildChildPath"));
    //     Cell<DoCoef> noeudFilsGauche1 = new Cell<>(new DoCoef("leftChildChildPath"));
    //     Cell<DoCoef> noeudFilsGauche2 = new Cell<>(new DoCoef("leftChildChildPath"));

    //     bTree.root.successorRight = parentNode;
    //     parentNode.successorLeft = noeudFilsGauche;
    //     noeudFilsGauche.successorLeft = noeudFilsGauche1;
    //     noeudFilsGauche.successorRight = noeudFilsGauche2;

    //     //On verifie
    //     assertNotNull(bTree.root.successorRight);
    //     assertNotNull(parentNode.successorLeft);
    //     assertEquals(parentNode, bTree.root.successorRight);
    //     assertEquals(noeudFilsGauche, parentNode.successorLeft);

    //     bTree.remove(parentNode, bTree.root);

    //     assertEquals(noeudFilsGauche2.element, bTree.root.successorRight.element);
    //     assertNull(noeudFilsGauche.successorRight);
    // }

    // //Suppression d'un nœud avec des enfant à Droite
    // @Test
    // public void testRemoveNoeudAvecEnfantsDroite() {
    //     BTreeCoef bTree = new BTreeCoef("parentPath");
    //     Cell<DoCoef> parentNode = new Cell<>(new DoCoef("leftChildPath"));
    //     Cell<DoCoef> noeudFilsDroite = new Cell<>(new DoCoef("leftChildChildPath1"));
    //     Cell<DoCoef> noeudFilsDroite1 = new Cell<>(new DoCoef("leftChildChildPath2"));
    //     Cell<DoCoef> noeudFilsDroite2 = new Cell<>(new DoCoef("leftChildChildPath3"));

    //     bTree.root.successorLeft = parentNode;
    //     parentNode.successorRight = noeudFilsDroite;
    //     noeudFilsDroite.successorLeft = noeudFilsDroite1;
    //     noeudFilsDroite.successorRight = noeudFilsDroite2;

    //     //On verifie
    //     assertNotNull(bTree.root.successorLeft);
    //     assertNotNull(parentNode.successorRight);
    //     assertEquals(parentNode, bTree.root.successorLeft);
    //     assertEquals(noeudFilsDroite, parentNode.successorRight);

    //     bTree.remove(parentNode, bTree.root);
    //     assertEquals(noeudFilsDroite1.element, bTree.root.successorLeft.element);
    //     assertNull(noeudFilsDroite.successorLeft);
    // }

    // //Suppression d'un nœud avec des enfants à gauche et à droite 


    // //Suppression d'un nœud avec des enfants à gauche et à droite


    // //Suppression d'un nœud avec des enfants à gauche et à droite


    // @Test
    // public void testUpdate() {
    //     // Créez une instance de BTreeCoef et ajoutez un fichier existant
    //     BTreeCoef bTree = new BTreeCoef("root");
    //     DoCoef previousContent = new DoCoef("path/to/previous/file", 10);
    //     bTree.insert(null, new Cell<>(previousContent));

    //     // Modifiez le fichier existant avec un nouveau contenu
    //     DoCoef newContent = new DoCoef("path/to/new/file", 15);
    //     bTree.update(newContent, bTree.root);

    //     // Vérifiez que le fichier a été correctement mis à jour
    //     assertEquals(newContent, bTree.root.element);
    // }





    

}
