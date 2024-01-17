package inf353;

import static org.junit.Assert.*;

import java.beans.Transient;

import inf353.DictionnaireNaif;
import inf353.LecteurDocumentNaif;
import org.junit.Test;

// package TEST;

// import static org.junit.Assert.*;

// import java.beans.Transient;

//

// import static org.junit.Test;

public class TestLecteurDocumentNaif {
    @Test
    public void testDemarrerFichierExistant() {
        LecteurDocumentNaif lecteur = new LecteurDocumentNaif("src\\TEST\\FileTest\\FichierNonVide.txt");
        lecteur.demarrer();
        //assertNotNull(lecteur.scanner);
        assertFalse(lecteur.finDeSequence());
        assertNotNull(lecteur.elementCourant());
    }

    @Test
    public void testDemarrerFichierInexistant() {
        LecteurDocumentNaif lecteur = new LecteurDocumentNaif("C:\\Fichier\\Inexistant.txt");
        lecteur.demarrer();
        //assertNull(lecteur.scanner);
        assertTrue(lecteur.finDeSequence());
        assertNull(lecteur.elementCourant());
    }

    @Test
    public void testAvancer() {
        LecteurDocumentNaif lecteur = new LecteurDocumentNaif("src\\TEST\\FileTest\\FichierNonVide.txt");
        lecteur.demarrer();
        String premierElement = lecteur.elementCourant();
        lecteur.avancer();
        assertNotEquals(premierElement, lecteur.elementCourant());
    }

    @Test
    public void testFinDeSequence() {
        LecteurDocumentNaif lecteur = new LecteurDocumentNaif("src\\TEST\\FileTest\\FichierNonVide.txt");
        lecteur.demarrer();
        while (!lecteur.finDeSequence()) {
            lecteur.avancer();
        }
        assertTrue(lecteur.finDeSequence());
    }

    @Test
    public void testElementCourant() {
        LecteurDocumentNaif lecteur = new LecteurDocumentNaif("src\\TEST\\FileTest\\FichierNonVide.txt");
        lecteur.demarrer();
        assertNotNull(lecteur.elementCourant());
    }

    @Test
    public void testFichierVide() {
        LecteurDocumentNaif lecteur = new LecteurDocumentNaif("src\\TEST\\FileTest\\FichierVide.txt");
        lecteur.demarrer();
        assertTrue(lecteur.finDeSequence());
    }

    @Test
    public void testFichierAvecMotsSpeciaux() {
        LecteurDocumentNaif lecteur = new LecteurDocumentNaif("src\\TEST\\FileTest\\FichierCaractereSpeciaux.txt");
        lecteur.demarrer();
        assertFalse(lecteur.finDeSequence());
        assertNotNull(lecteur.elementCourant());
    }

    @Test
    public void testFichierAvecAccents() {
        LecteurDocumentNaif lecteur = new LecteurDocumentNaif("src\\TEST\\FileTest\\FichierAvecAccent.txt");
        lecteur.demarrer();
        assertFalse(lecteur.finDeSequence());
        assertNotNull(lecteur.elementCourant());
    }

    @Test
    public void testFichierAvecEspacesMultiples() {
        LecteurDocumentNaif lecteur = new LecteurDocumentNaif("src\\TEST\\FileTest\\FichierEspaceMultiples.txt");
        lecteur.demarrer();

        assertFalse(lecteur.finDeSequence());
        assertNotNull(lecteur.elementCourant());
        assertNotEquals("", lecteur.elementCourant());
    }

    @Test
    public void testFichierAvecPlusieursLignes() {
        LecteurDocumentNaif lecteur = new LecteurDocumentNaif("src\\TEST\\FileTest\\FichierPlusieurLigne.txt");
        lecteur.demarrer();
        assertFalse(lecteur.finDeSequence());
        assertNotNull(lecteur.elementCourant());
        lecteur.avancer();
        assertFalse(lecteur.finDeSequence());
        assertNotNull(lecteur.elementCourant());
    }

    @Test
    public void testAvancerApresFinSeq() {
        LecteurDocumentNaif lecteur = new LecteurDocumentNaif("src\\TEST\\FileTest\\FichierNonVide.txt");
        lecteur.demarrer();

        // Avancer jusqu'à la fin de la séquence
        while (!lecteur.finDeSequence()) {
            lecteur.avancer();
        }
        
        // Appeler avancer() après la fin de la séquence
        assertTrue(lecteur.finDeSequence());
    }

    @Test
    public void testAvancerSurFichierVide() {
        LecteurDocumentNaif lecteur = new LecteurDocumentNaif("src\\TEST\\FileTest\\FichierVide.txt");
        lecteur.demarrer();

        // Appeler avancer() sur un fichier vide
        lecteur.avancer();

        assertTrue(lecteur.finDeSequence());
        assertNull(lecteur.elementCourant());
    }
}
