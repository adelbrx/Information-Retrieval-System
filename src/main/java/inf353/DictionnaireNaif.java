package inf353;

public class DictionnaireNaif implements Dictionnaire {
    public char[][] dictionnaire;
    public int taille; // taille maximale du teableau
    public int N; // nombre de mots

    public DictionnaireNaif() {

        /*
         * constructeur qui défine le nombre maximal à 1000 (par défaut) si
         * l'utilisateur l'entre pas
         * et initialise les attributs de la classe
         * 
         * Versions:
         * ----------
         * spécification : Ahmed Adel BEREKSI REGUIG V-1.0 (09/11/2023)
         * implémentation : Ahmed Adel BEREKSI REGUIG V-1.0 (09/11/2023)
         * 
         */

        this.dictionnaire = new char[1000][40];
        this.taille = 1000;
        vider();
    }

    public DictionnaireNaif(int n) {

        /*
         * constructeur qui défine le nombre maximal à n (définé par l'utilisateur de la
         * classe)
         * et initialise les attributs de la classe
         * 
         * Parametères:
         * -----------
         * n : le nombre maximal des mot de notre dictionnaire 'this.taille' (Int)
         * 
         * Versions:
         * ----------
         * spécification : Ahmed Adel BEREKSI REGUIG V-1.0 (09/11/2023)
         * implémentation : Ahmed Adel BEREKSI REGUIG V-1.0 (09/11/2023)
         * 
         */

        this.dictionnaire = new char[n][40];
        this.taille = n;
        this.vider();
    }

    @Override
    public void vider() {

        /*
         * Contruire le dictionnaire qui est vide.
         * 
         * fonction : le dictionnaire est vide.
         * 
         * Versions:
         * ----------
         * spécification : NGUYEN Duy Thang V-1.0 (11/11/2023).
         * implémentation : NGUYEN Duy Thang V-1.0 (11/11/2023).
         * 
         */

        // Définir tout le contenu du dictionnaire par défaut
        for (int i = 0; i < this.taille; i++) {
            for (int j = 0; j < 40; j++) {
                this.dictionnaire[i][j] = '0';
            }
        }

        this.N = 0; // Définir le nombre de mots du dictionnaire sur 0
    };

    /**
     * Ajoute un nouveau mot au dictionnaire, si possible.
     * Affiche des messages d'erreur si la taille du mot dépasse la limite
     * autorisée, si le dictionnaire est plein, ou si le mot existe déjà.
     *
     * @param m Le mot à ajouter (String).
     */
    @Override
    public void ajouterMot(String m) {
        // Vérifie si la longueur du mot dépasse la limite autorisée
        if (m.length() > 40) {

            System.out.println("Le mot dépasse la taille requise maximal");
        } else {
            // Vérifie si le dictionnaire est plein
            if (this.N == this.taille) {

                System.out.println("Le dictionnaire est plein.");
            } else {
                // Vérifie si le mot existe déjà dans le dictionnaire
                int indice = this.indiceMot(m);

                if (indice != -1) {

                    System.out.println("Le mot existe déjà.");
                } else {
                    // Ajoute le mot au premier emplacement disponible dans le dictionnaire
                    for (int line = 0; line < this.taille; line++) {

                        if (this.dictionnaire[line][0] == '0') {

                            for (int column = 0; column < m.length(); column++) {

                                this.dictionnaire[line][column] = m.charAt(column);
                                if (column == m.length() - 1 && column < 40) {

                                    this.dictionnaire[line][m.length()] = '0';
                                }
                            }

                            break;
                        }

                    }
                    // Incrémente le nombre de mots dans le dictionnaire
                    this.N++;
                }
            }
        }
    };

    /**
     * Recherche l'indice du mot spécifié dans le dictionnaire.
     *
     * @param m Le mot à rechercher (String).
     * @return L'indice du mot s'il est présent dans le dictionnaire, sinon -1.
     */
    @Override
    public int indiceMot(String m) {

        int position = 0;
        while (position < this.taille) {
            String mot = this.motIndice(position);
            if (mot.equals(m)) {

                return position;
            }
            position++;
        }
        return -1;

    }

    @Override
    public String motIndice(int i) {
        /*
         * fonction retourne le mot de l'indice 'i' dans notre dictionnaire
         * 
         * Parametères:
         * -----------
         * i : l'indice du mot (Int)
         * 
         * Retournes:
         * --------
         * mot : le mot de l'indice 'i' (Str)
         * 
         * Versions:
         * ----------
         * spécification : Ahmed Adel BEREKSI REGUIG V-1.0 (09/11/2023)
         * implémentation : Ahmed Adel BEREKSI REGUIG V-1.0 (09/11/2023)
         * implémentation : Ahmed Adel BEREKSI REGUIG V-1.1 (14/11/2023)
         * 
         */

        // si le i ne dépasse pas la taille maximal du dictionnaire et aussi le premier
        // élément dans la ligne 'i' n'est pas '0'
        if (i < this.taille && this.dictionnaire[i][0] != '0') {

            // initialisation de la variable 'mot' par le mot vide
            String mot = "";
            int j = 0;

            // tant que (la case précédente est '\' et la case courante est '0') ou bien (la
            // case courante est déffirente de '0') alors entrer dans la boucle
            while ((j != 0 && (this.dictionnaire[i][j - 1] == '\\' && this.dictionnaire[i][j] == '0'))
                    || (this.dictionnaire[i][j] != '0')) {

                // construire le mot de cette ligne dans le variable 'mot'
                mot += this.dictionnaire[i][j];
                j++;
            }

            return mot;
        } else {

            if (i < this.taille && this.dictionnaire[i][0] == '0') {

                return "";
            } else {

                return "erreur : l'indice dépasse la taille";
            }
        }

    }

    /**
     * Vérifie si le dictionnaire contient le mot spécifié.
     *
     * @param m Le mot à rechercher (String).
     * @return true si le mot est présent dans le dictionnaire, sinon false.
     */
    @Override
    public boolean contient(String m) {

        return this.indiceMot(m) != -1;
    };

    @Override
    public int nbMots() {
        /*
         * fonction : vrai ssi il existe m dans D0 tel que il exist u tq m = p.u
         * 
         * Retournes:
         * --------
         * N : le nombre de mots.
         * 
         * Versions:
         * ----------
         * spécification : NGUYEN Duy Thang V-1.0 (11/11/2023).
         * implémentation : NGUYEN Duy Thang V-1.0 (11/11/2023).
         * 
         */

        return this.N;
    };

    /**
     * Vérifie si le dictionnaire contient un mot qui commence par le préfixe
     * spécifié.
     *
     * @param p Le préfixe à vérifier (String).
     * @return true si le dictionnaire contient un mot avec le préfixe, sinon false.
     */
    @Override
    public boolean contientPrefixe(String p) {

        // parcourir les lignes (tous les mots)
        for (int line = 0; line < this.taille; line++) {
            // Extrait le mot de cette ligne
            String mot = motIndice(line);
            // Vérifie si le mot commence par le préfixe
            if (mot.startsWith(p)) {
                return true;
            }

        }
        return false;

    };

    @Override
    public String plusLongPrefixeDe(String mot) {

        /*
         * fonction retourne le plus grand préfixe dans notre dictionnaire
         * et qui est au même temp un mot dans le dictionnaire.
         * 
         * Parametères:
         * -----------
         * mot : le mot cherché qui contient le préfixe (Str)
         * 
         * Retournes:
         * --------
         * prefixe : le plus grand préfixe du dictionnaire (Str)
         * 
         * Versions:
         * ----------
         * spécification : Ahmed Adel BEREKSI REGUIG V-1.0 (12/11/2023)
         * implémentation : Ahmed Adel BEREKSI REGUIG V-1.0 (12/11/2023)
         * implémentation : Ahmed Adel BEREKSI REGUIG V-1.1 (14/11/2023)
         * 
         */

        String prefixe = mot;

        // variant : préfixe courant = ancient préfixe sauf le dernier caractère et é.i:
        // ancient préfixe = mot
        while (prefixe != "") {

            // parcourire tous les mot du dictionnaire
            for (int line = 0; line < this.taille; line++) {

                // extrraire le mot de cette ligne
                String mot_tmp = motIndice(line);

                // si ce mot == préfixe donc c'est le plus grand préfixe
                if (mot_tmp.equals(prefixe)) {

                    return prefixe;
                }
            }

            // préfixe courant = ancient préfixe sauf le dernier caractère
            prefixe = prefixe.substring(0, prefixe.length() - 1);

        }

        // le plus grand préfixe est le mot vide "" donc il n'existe pas
        return prefixe;
    };

    public void afficherDictionnaire() {
        /*
         * afficher le contenu du dictionnaire
         * 
         * Versions :
         * ----------
         * specification : Ahmed Adel BEREKSI REGUIG V-1.0 (20/11/2024)
         * implementation : Ahmed Adel BEREKSI REGUIG V-1.0 (20/11/2024)
         */

        System.out.println("________________________________________");
        for (int line = 0; line < this.taille; line++) {

            System.out.println(this.motIndice(line));
            System.out.println("________________________________________");
        }
    }

}
