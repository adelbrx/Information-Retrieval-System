En premier lieu , on a pris un moment de réflexion , ou on a essayer de Brainstorm les differents cas de Test pour les méthodes qui vont etre realiser aujourd'hui ,on a pu conclure ceci : 

methode vider : 
                EI : this.dictionnaire remplie
                EF: this.dictionnaire vide

                EI : this.dictionnaire vide
                EF: this.dictionnaire vide ( sans erreurs)



methode ajouterMot : EI : dictionnaire quit ne contient pas m
                     EF : dictionnaire a m comme dernier élement.

                     EI : dictionnaire contient deja M
                     EF : retourne dictionnaire sans changement

                     EI: dictionnaire avec mot nul ou >40 Char
                     EF : dictionnaire sans changement

                     Ei: dictionnaire avec N mots ( nombre maximale de mots)
                     EF : dictionnaire sans changements
        

methode indiceMot: Ei : dictionnaire avec mot a l'indice i
                   EF : pas de changements , on retourne I

                   EI: dictionnaire avec m n'existe pas dans ce dernier
                   EF : sans changements, on retourne " mot non existant"

                   EI : dictionnaire avec mot null ou > 40 CHAR
                   EF: renvoie erreur mot non conforme
                   EI: dictionaiire avec mot i et parametre m non majuscule 
                   EF: pas de changements , on retourne I


methode motIndice: Ei : dictionnaire avec indice du mot i
                   EF : pas de changements , on retourne le mot

                   EI: dictionnaire avec la case d'indice est vide 
                   EF : sans changements, on retourne " mot non existant"
                   
                   EI : dictionnaire avec l'indice > N ou <0
                   EF: renvoie erreur indice corrompu
                   
methode contient(m) :  Ei : dictionnaire avec mot m
                   EF : pas de changements , on retourne vrai

                   EI: dictionnaire avec m n'existe pas dans ce dernier
                   EF : sans changements, on retourne faux

                   EI : dictionnaire avec mot null ou > 40 CHAR
                   EF: renvoie erreur mot non conforme

                   EI: dictionaiire avec mot i et parametre m non majuscule 
                   EF: pas de changements , on retourne vrai

methode nbMots (): 
                   EI : Dictionnaire avec n mots
                   EF : renvoi N

                   Ei: dictionnaire avec 0 mots
                   EF: renvoi 0 

methode contienPrefixe (P) :
                   EI: dictionnaire avec mot m qui contient prefixe P
                   EF: pas de changements on renvoi vrai

                   EI : dicitonnaire avec aucun mot m qui contient prefixe P
                   EF: renvoie faux

                   EI: dicitionnaire avec plusiers mots avec prefixe P
                   EF: renvoie vrai

                   Ei: dictionnaire avec mot m tels que m=p
                   EF: renvoie faux 

                   EI: dictionnaire avec p null ou >40
                   EF: renvoie faux


methode plusLongPrefixe(mot):
                   EI : dicitonnaire avec mot et prefixe 
                   EF: renvoie string tels que p<m et p existe dans D0
                   
                   EI: dictionnaire avec mot null 
                   Ei: renvoie erreur 

                   EI: dictonnaire avec mot et sans prefixe
                   EF: renvoie rien
                   
                   Ei: dictionnaire avec mots nulls 
                   EF : renvoi erreur

                   EI: dictionnaire avec m comme son seul mot
                   EF: renvoie rien

                   Ei: dicitionnaire qui contient pas le mot m
                   EF: renvoi mot non existant 


                