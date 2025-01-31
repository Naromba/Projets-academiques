/*
ENTETE
Cours: IFT 1170; Trimestre d'étude: Hiver 2023
Nom du fichier: Nation.java
Nom de l'etudiante: Naromba Condé
Matricule: 20251772 

Description du programme: 
Ce programme est composé de deux classes: "Pays" et "Nation".

La classe "Pays" est une classe qui représente les pays. 
Elle possède cinq attributs: le code du pays, le nom du pays, la capitale du pays, la superficie du pays et la population du pays. 
Elle possède également plusieurs méthodes, telles que "getNom()", "getPopulation()", "getCapitale()", "getCode()", "setContinent()" et "setPopulation()".

La classe "Nation" est une classe qui contient plusieurs méthodes pour manipuler un vecteur de pays.
La méthode "lireRemplir()" lit le contenu du  fichier pays_h23.txt contenant des informations sur les pays et remplit un vecteur avec ces informations.
La méthode "afficher()" affiche les 15 premiers pays du vecteur.
Les méthodes "modifieC()", "modifiePop()" et "supprimerPays()" sont utilisées pour modifier les informations sur les pays.

*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

public class Nation {

	// 1. Lire le fichier, remplir et retourner un vecteur de pays.
	static Vector<Pays> lireRemplir(String nomFichier, int capacite_Initiale)

			throws IOException {

		Vector<Pays> v = new Vector<Pays>(capacite_Initiale);
		boolean existeFichier = true;

		FileReader fr = null;

		try {
			fr = new FileReader(nomFichier);
		}

		catch (java.io.FileNotFoundException erreur) {
			System.out.println("Probleme d'ouvrir le fichier " + nomFichier);
			existeFichier = false;
		}

		if (existeFichier) {

			BufferedReader entree = new BufferedReader(fr);
			boolean finFichier = false;

			while (!finFichier) {

				// lire une ligne
				String uneLigne = entree.readLine();

				if (uneLigne == null)
					finFichier = true;
				else {
					int code = Integer.parseInt(uneLigne.substring(0, 1).trim());
					String nom = uneLigne.substring(1, 36).trim();
					String capitale = uneLigne.substring(36, 55).trim();

					double superficie = Double.parseDouble(uneLigne.substring(55, 64).trim());
					int population = Integer.parseInt(uneLigne.substring(64).trim());
					v.add(new Pays(code, nom, capitale, superficie, population));
				}
			}
			entree.close();
		}

		return v;
	}

	// 2. Afficher seulement les 15 premiers pays lus.

	static void afficher(Vector<Pays> vectPays, int nbPays, String mess) {
		System.out.printf("%s\n", mess);
		System.out.printf("   code                     Nom du pays               Capitale          Superficie     population");
		System.out.printf("\n----------------------------------------------------------------------------------------------------\n");
		for (int i = 0; i < nbPays; i++)
			System.out.printf("%3d) %s", i + 1, vectPays.get(i));
		System.out.printf("\n");

	}

	// 3.A-Modifier le continent de la Russie
//	static void modifieC(Vector<Pays> vectPays, int code, String nomPays) {
//		for (int i = 0; i < vectPays.size(); i++) {
//			if (vectPays.get(i).getNom().equals(nomPays)) {
//				vectPays.get(i).setCode(code);
//
//			}
//
//		}
//	}

	// 3.B-Changer la population de l’Allemagne
//	static void modifiePop(Vector<Pays> vectPays, String nomPays) {
//		for (int i = 0; i < vectPays.size(); i++) {
//			if (vectPays != null && vectPays.get(i).getNom().equals(nomPays)) {
//				vectPays.get(i).setPopulation();
//			}
//		}
//	}
//
//	// 3.C-Supprimer le pays « DES OURAGANS »
//	static void supprimerPays(Vector<Pays> vectPays, String nomPays) {
//		for (int i = 0; i < vectPays.size(); i++) {
//			if (vectPays.get(i).getNom().equals(nomPays)) {
//				vectPays.remove(i);
//			}
//		}
//
//	}

	public static void main(String[] args) throws IOException {

		// Question 1
		final int MAX_Pays = 225;
		Vector<Pays> vectPays = lireRemplir("/Users/amba/Documents/dossier_tp3/pays_h23.txt", MAX_Pays);

		// Question 2
		afficher(vectPays, 15, "\n                          APRES LA LECTURE DU FICHIER\n ");

		// Question3
		// Modifie le continent de la Russie
		int k1 = vectPays.indexOf(new Pays(' ', "Russie", "", 0, 0));
		if (k1 >= 0) {
			System.out.printf("On trouve la Russie a l'indice %d\n%s", k1, vectPays.get(k1));
			vectPays.get(k1).setCode(5);

			System.out.printf("Nouvelles infos de la Russie :\n%s\n", vectPays.get(k1));
		} else
			System.out.printf("Introuvable\n");

		// Modifie la population de l'Allemagne
		int k2 = vectPays.indexOf(new Pays(' ', "Allemagne", "", 0, 0));
		if (k2 >= 0) {
			System.out.printf("On trouve Allemagne a l'indice %d\n%s", k2, vectPays.get(k2));

			vectPays.get(k2).setPopulation();

			System.out.printf("Nouvelles infos de l'Allemagne :\n%s\n", vectPays.get(k2));
		} else
			System.out.printf("Introuvable\n");

		// Supprimer le pays Des Ouragans
		if (vectPays.remove(new Pays(' ', "Des Ouragans", "", 0, 0)))
			System.out.printf("\nApres avoir supprime le pays Des Ouragans, il reste %d pays \n\n",
					vectPays.size());
		else
			System.out.printf("\n On ne trouve pas Des Ouragans dans le ficher\n");

		afficher(vectPays, 20, "\n                                APRES MODIFICATION\n");

//		modifieC(vectPays, 5, "RUSSIE");
//		modifiePop(vectPays, "ALLEMAGNE");
//		supprimerPays(vectPays, "DES OURAGANS");
//		afficher(vectPays, 20, "\napres modification");

		// Question 4
		Collections.sort(vectPays);
		afficher(vectPays, 10, "\n                                 APRES LE TRI\n");

		// Question 5
		System.out.printf("                         la recherche de quelques pays");
		System.out.printf("\n-------------------------------------------------------------------------------------------------\n");
		// La recherche de Canada avec Collections.binarySearch
		Pays aChercher = new Pays(' ', "Canada", "", 0, 0);
		int k = Collections.binarySearch(vectPays, aChercher);

		if (k >= 0)
			System.out.printf("On trouve Canada a l'indice %d\n%s", k, vectPays.get(k));
		else
			System.out.printf("Introuvable\n");

		// La recherche de Mexique avec Collections.binarySearch
		aChercher = new Pays(' ', "Mexique", "", 0, 0);
		k = Collections.binarySearch(vectPays, aChercher);

		if (k >= 0) {
			System.out.printf("On trouve Mexique a l'indice %d\n%s", k, vectPays.get(k));
		}

		else
			System.out.printf("Introuvable\n");

		// La recherche de Japon avec Collections.binarySearch
		aChercher = new Pays(' ', "Japon", "", 0, 0);
		k = Collections.binarySearch(vectPays, aChercher);

		if (k >= 0) {
			System.out.printf("On trouve Japon a l'indice %d\n%s", k, vectPays.get(k));
		}

		else
			System.out.printf("Introuvable\n");

		// La recherche de Chili avec Collections.binarySearch
		aChercher = new Pays(' ', "Chili", "", 0, 0);
		k = Collections.binarySearch(vectPays, aChercher);

		if (k >= 0) {
			System.out.printf("On trouve Chili a l'indice %d\n%s", k, vectPays.get(k));
		}

		else
			System.out.printf("Introuvable\n");

	}
}

/*EXECUTION

                          APRES LA LECTURE DU FICHIER
 
   code                     Nom du pays               Capitale          Superficie     population
----------------------------------------------------------------------------------------------------
  1) 2                      ETATS-UNIS                WASHINGTON        9629047      291289535
  2) 3                           CHINE                     PEKIN        9596960     1273111290
  3) 1                          RUSSIE                    MOSCOU       17075400      143954573
  4) 4                       AUSTRALIE                  CANBERRA        7686850       19834248
  5) 3                           JAPON                     KYOTO         377835       12761000
  6) 4                    DES OURAGANS                   TEMPETE              1              1
  7) 5                       ALLEMAGNE                    BERLIN         357027        8253700
  8) 5                          FRANCE                 MARSEILLE         543964       61387038
  9) 5                          ITALIE                      ROME         301230       57715620
 10) 3                    COREE DU SUD                     SEOUL          99274       48324000
 11) 5                     ROYAUME-UNI                   LONDRES         244101       58789194
 12) 2                            CUBA                 LA HAVANE         100860       11184023
 13) 5                         UKRAINE                      KIEV         603700       48396470
 14) 5                         HONGRIE                  BUDAPEST          93030       10106017
 15) 5                        ROUMANIE                  BUCAREST         238390       22272000

On trouve la Russie a l'indice 2
1                          RUSSIE                    MOSCOU       17075400      143954573
Nouvelles infos de la Russie :
5                          RUSSIE                    MOSCOU       17075400      143954573

On trouve Allemagne a l'indice 6
5                       ALLEMAGNE                    BERLIN         357027        8253700
Nouvelles infos de l'Allemagne :
5                       ALLEMAGNE                    BERLIN         357027       82537000


Apres avoir supprime le pays Des Ouragans, il reste 197 pays 


                                APRES MODIFICATION

   code                     Nom du pays               Capitale          Superficie     population
----------------------------------------------------------------------------------------------------
  1) 2                      ETATS-UNIS                WASHINGTON        9629047      291289535
  2) 3                           CHINE                     PEKIN        9596960     1273111290
  3) 5                          RUSSIE                    MOSCOU       17075400      143954573
  4) 4                       AUSTRALIE                  CANBERRA        7686850       19834248
  5) 3                           JAPON                     KYOTO         377835       12761000
  6) 5                       ALLEMAGNE                    BERLIN         357027       82537000
  7) 5                          FRANCE                 MARSEILLE         543964       61387038
  8) 5                          ITALIE                      ROME         301230       57715620
  9) 3                    COREE DU SUD                     SEOUL          99274       48324000
 10) 5                     ROYAUME-UNI                   LONDRES         244101       58789194
 11) 2                            CUBA                 LA HAVANE         100860       11184023
 12) 5                         UKRAINE                      KIEV         603700       48396470
 13) 5                         HONGRIE                  BUDAPEST          93030       10106017
 14) 5                        ROUMANIE                  BUCAREST         238390       22272000
 15) 5                           GRECE                   ATHENES         131940       10623835
 16) 5                         NORVEGE                      OSLO         324220        4525116
 17) 5                        PAYS-BAS                 AMSTERDAM          41526       16135992
 18) 2                          BRESIL                  BRASILIA        8511965      174468575
 19) 5                           SUEDE                 STOCKHOLM         449964        8875053
 20) 5                         ESPAGNE                    MADRID         504782       40037995


                                 APRES LE TRI

   code                     Nom du pays               Capitale          Superficie     population
----------------------------------------------------------------------------------------------------
  1) 3                     AFGHANISTAN                    KABOUL         652225       29547078
  2) 1                  AFRIQUE DU SUD                  PRETORIA        1219912       42718530
  3) 5                         ALBANIE                    TIRANA          28748        3510484
  4) 1                         ALGERIE                     ALGER        2381740       31763053
  5) 5                       ALLEMAGNE                    BERLIN         357027       82537000
  6) 5                         ANDORRE          ANDORRA LA VELLA            468          67627
  7) 1                          ANGOLA                    LUANDA        1246700       10766471
  8) 2              ANTIGUA-ET-BARBUDA               SAINT-JOHNS            442          67448
  9) 2          ANTILLES NEERLANDAISES                WILLEMSTAD            800         210000
 10) 3                 ARABIE SAOUDITE                     RIYAD        1960582       23513330

                         la recherche de quelques pays
-------------------------------------------------------------------------------------------------
On trouve Canada a l'indice 36
2                          CANADA                    OTTAWA        9984670       31499560
On trouve Mexique a l'indice 118
2                         MEXIQUE                    MEXICO        1972550      103400165
On trouve Japon a l'indice 93
3                           JAPON                     KYOTO         377835       12761000
On trouve Chili a l'indice 38
3                           CHILI                  SANTIAGO         756950       15328467

 */
