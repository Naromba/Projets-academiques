/*
 ENTETE
Cours: IFT 1170; Trimestre d'étude: Hiver 2023
Nom du fichier: NumeroC.java
Nom de l'etudiante: Naromba Condé
Matricule: 20251772 

Description du programme:  
Ce programme lit un fichier contenant des informations sur les pays et leurs capitales,
il crée une liste chaînée contenant les pays d'Amérique et effectue une recherche séquentielle , le tri pour certaines capitales, 
il ajoute aussi un nouveau pays .
Le programme définit une classe nommée "Pays", qui représente un pays avec son code, son nom, sa capitale, sa superficie et sa population.
 La classe "Pays" implémente l'interface "Comparable" pour trier les pays en fonction de leurs capitales.
 La classe principale, "NumeroC", contient deux méthodes statiques : "lireCreer" et "afficher". 
 La méthode "lireCreer" prend un nom de fichier en paramètre, lit le fichier ligne par ligne et crée une liste chaînée contenant les pays d'Amérique. 
 La méthode "afficher" prend une liste chaînée de pays, un nombre de pays à afficher et un message à afficher.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Vector;

public class NumeroC {
	// 1-Lire ce fichier, créer et retourner une liste contenant les pays
	// d’Amérique.
	static LinkedList<Pays> lireCreer(String nomFichier) throws IOException {
		LinkedList<Pays> liste = new LinkedList<Pays>();
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

				String uneLigne = entree.readLine();

				if (uneLigne == null)
					finFichier = true;
				else {
					int code = Integer.parseInt(uneLigne.substring(0, 1).trim());
					if (code == 2) {
						String nom = uneLigne.substring(1, 32).trim();
						String capitale = uneLigne.substring(36, 54).trim();

						double superficie = Double.parseDouble(uneLigne.substring(63, 69).trim());
						int population = Integer.parseInt(uneLigne.substring(74).trim());

						liste.add(new Pays(code, nom, capitale, superficie, population));
					}
				}
			}
			entree.close();
		}

		return liste;
	}

	// Affiche rliste de pays d'Amerique
	static void afficher(LinkedList<Pays> liste, int nbPays, String mess) {
		System.out.printf("%s\n", mess);
		System.out.println("   code                   Nom du pays                 Capitale         Superficie    population");
		System.out.println(
				"------------------------------------------------------------------------------------------------");

		for (int i = 0; i < nbPays; i++) {
			System.out.printf("%2d) %s\n", i + 1, liste.get(i).toString());
		}
		System.out.println();
	}

	// Recherche sequentielle
	static void rechercheSequentielle(LinkedList<Pays> liste) {
		String[] nomCap = { "Washington", "Ottawa", "Santiago" };
		for (int i = 0; i < nomCap.length; i++) {
			System.out.printf("\nOn fait la recherche de %s :\n", nomCap[i]);
			Pays aChercher = new Pays(0, " ", nomCap[i], 0.0, 0);
			boolean trouve = false;
			for (Pays pays : liste) {
				if (pays.getCapitale().equalsIgnoreCase(aChercher.getCapitale())) {
					trouve = true;
					System.out.println(pays);
				}
			}
			if (!trouve) {
				System.out.printf("\nOn ne la trouve pas\n");
			}
		}
	}

	// Ajout de pays
	static void ajouterPays(LinkedList<Pays> liste, Pays nomPays) {
		int index = Collections.binarySearch(liste, nomPays, Comparator.comparing(Pays::getCapitale));
		if (index < 0) {
			System.out.printf("l'indice du pays à ajouter sera %d en comptant a partir de 1\n", -index);
			liste.add(-index - 1, nomPays);
		} else {
			System.out.println("Le pays existe déjà dans la liste");
		}
	}

	public static void main(String[] args) throws IOException {

		// Question 1
		LinkedList<Pays> liste = lireCreer("/Users/amba/Documents/dossier_tp3/pays_bis.txt");
		System.out.printf("On vient de créer une liste de %d pays d'Amérique\n", liste.size());
		afficher(liste, 40, "\n                        Liste des pays d'Amérique après creation\n");

		// Question 2
		rechercheSequentielle(liste);

		// Question 3
		Collections.sort(liste, Comparator.comparing(Pays::getCapitale));
		afficher(liste, 7, "\n                          Liste des pays d'Amérique après tri\n");

		// Question 4
		Pays chili = new Pays(2, "CHILI", "SANTIAGO", 756950, 15328467);
		ajouterPays(liste, chili);
		afficher(liste, liste.size(), "\n                Liste triée des pays d'Amérique après ajout de CHILI :\n");

	}

}

/*EXECUTION
On vient de créer une liste de 40 pays d'Amérique

                        Liste des pays d'Amérique après creation

   code                   Nom du pays                 Capitale         Superficie    population
------------------------------------------------------------------------------------------------
 1) 2                      ETATS-UNIS                WASHINGTON         962904      291289535

 2) 2                            CUBA                 LA HAVANE          10086       11184023

 3) 2                          BRESIL                  BRASILIA         851196      174468575

 4) 2                          CANADA                    OTTAWA         998467       31499560

 5) 2                        JAMAIQUE                  KINGSTON           1099        1695867

 6) 2                       ARGENTINE              BUENOS AIRES         276689       37812817

 7) 2                         BAHAMAS                    NASSAU           1394         300529

 8) 2          REPUBLIQUE DOMINICAINE            SAINT-DOMINGUE           4873        8442533

 9) 2                         MEXIQUE                    MEXICO         197255      103400165

10) 2                        PARAGUAY                   ASUNCIO          40675        5734139

11) 2                       VENEZUALA                   CARACAS          91205       23542649

12) 2                        COLOMBIE                    BOGOTA         113891       41088227

13) 2               TRINITE-ET-TOBAGO            PORT D'ESPAGNE            512        1104209

14) 2              ANTIGUA-ET-BARBUDA               SAINT-JOHNS             44          67448

15) 2          ANTILLES NEERLANDAISES                WILLEMSTAD             80         210000

16) 2                           ARUBA                ORANJESTAD             19          69000

17) 2                         BARBADE                BRIDGETOWN             43         276607

18) 2                          BELIZE                  BELMOPAN           2296         266440

19) 2                        BERMUDES                  HAMILTON              5         113208

20) 2                         BOLIVIE                    LA PAZ         109858        8724156

21) 2                      COSTA RICA                  SAN JOSE           5110        3835000

22) 2                     EL SALVADOR              SAN SALVADOR           2104        6122075

23) 2                        EQUATEUR                     QUITO          28356       13183978

24) 2                         GRENADE             SAINT-GEORGES             34          89260

25) 2                            GUAM                     AGANA             54         160796

26) 2                       GUATEMALA          GUATEMALA CIUDAD          10889       12974361

27) 2                          GUYANA                GEORGETOWN          21497         697181

28) 2                           HAITI            PORT-AU-PRINCE           2775        7527817

29) 2                        HONDURAS               TEGUCIGALPA          11209        6249598

30) 2                    ILES CAIMANS               GEORGE TOWN             26          39000

31) 2       ILES VIERGES BRITANNIQUES                 ROAD TOWN             15          19000

32) 2                       NICARAGUA                   MANAGUA          12949        5128517

33) 2                          PANAMA                    PANAMA           7820        2845647

34) 2                           PEROU                      LIMA         128522       27949639

35) 2                      PORTO RICO                  SAN JUAN            895        3000000

36) 2              SAINT KITTSETNEVIS                BASSETERRE             26          38756

37) 2  SAINT-VINCENT-ET-LES GRENADINES                 KINGSTOWN             38         116812

38) 2                    SAINTE LUCIE                  CASTRIES             62         160145

39) 2                        SURINAME                PARAMARIBO          16327         433998

40) 2                         URUGUAY                MONTEVIDEO          17622        3360105



On fait la recherche de Washington :
2                      ETATS-UNIS                WASHINGTON         962904      291289535


On fait la recherche de Ottawa :
2                          CANADA                    OTTAWA         998467       31499560


On fait la recherche de Santiago :

On ne la trouve pas

                          Liste des pays d'Amérique après tri

   code                   Nom du pays                 Capitale         Superficie    population
------------------------------------------------------------------------------------------------
 1) 2                            GUAM                     AGANA             54         160796

 2) 2                        PARAGUAY                   ASUNCIO          40675        5734139

 3) 2              SAINT KITTSETNEVIS                BASSETERRE             26          38756

 4) 2                          BELIZE                  BELMOPAN           2296         266440

 5) 2                        COLOMBIE                    BOGOTA         113891       41088227

 6) 2                          BRESIL                  BRASILIA         851196      174468575

 7) 2                         BARBADE                BRIDGETOWN             43         276607


l'indice du pays à ajouter sera 38 en comptant a partir de 1

                Liste triée des pays d'Amérique après ajout de CHILI :

   code                   Nom du pays                 Capitale         Superficie    population
------------------------------------------------------------------------------------------------
 1) 2                            GUAM                     AGANA             54         160796

 2) 2                        PARAGUAY                   ASUNCIO          40675        5734139

 3) 2              SAINT KITTSETNEVIS                BASSETERRE             26          38756

 4) 2                          BELIZE                  BELMOPAN           2296         266440

 5) 2                        COLOMBIE                    BOGOTA         113891       41088227

 6) 2                          BRESIL                  BRASILIA         851196      174468575

 7) 2                         BARBADE                BRIDGETOWN             43         276607

 8) 2                       ARGENTINE              BUENOS AIRES         276689       37812817

 9) 2                       VENEZUALA                   CARACAS          91205       23542649

10) 2                    SAINTE LUCIE                  CASTRIES             62         160145

11) 2                    ILES CAIMANS               GEORGE TOWN             26          39000

12) 2                          GUYANA                GEORGETOWN          21497         697181

13) 2                       GUATEMALA          GUATEMALA CIUDAD          10889       12974361

14) 2                        BERMUDES                  HAMILTON              5         113208

15) 2                        JAMAIQUE                  KINGSTON           1099        1695867

16) 2  SAINT-VINCENT-ET-LES GRENADINES                 KINGSTOWN             38         116812

17) 2                            CUBA                 LA HAVANE          10086       11184023

18) 2                         BOLIVIE                    LA PAZ         109858        8724156

19) 2                           PEROU                      LIMA         128522       27949639

20) 2                       NICARAGUA                   MANAGUA          12949        5128517

21) 2                         MEXIQUE                    MEXICO         197255      103400165

22) 2                         URUGUAY                MONTEVIDEO          17622        3360105

23) 2                         BAHAMAS                    NASSAU           1394         300529

24) 2                           ARUBA                ORANJESTAD             19          69000

25) 2                          CANADA                    OTTAWA         998467       31499560

26) 2                          PANAMA                    PANAMA           7820        2845647

27) 2                        SURINAME                PARAMARIBO          16327         433998

28) 2               TRINITE-ET-TOBAGO            PORT D'ESPAGNE            512        1104209

29) 2                           HAITI            PORT-AU-PRINCE           2775        7527817

30) 2                        EQUATEUR                     QUITO          28356       13183978

31) 2       ILES VIERGES BRITANNIQUES                 ROAD TOWN             15          19000

32) 2          REPUBLIQUE DOMINICAINE            SAINT-DOMINGUE           4873        8442533

33) 2                         GRENADE             SAINT-GEORGES             34          89260

34) 2              ANTIGUA-ET-BARBUDA               SAINT-JOHNS             44          67448

35) 2                      COSTA RICA                  SAN JOSE           5110        3835000

36) 2                      PORTO RICO                  SAN JUAN            895        3000000

37) 2                     EL SALVADOR              SAN SALVADOR           2104        6122075

38) 2                           CHILI                  SANTIAGO         756950       15328467

39) 2                        HONDURAS               TEGUCIGALPA          11209        6249598

40) 2                      ETATS-UNIS                WASHINGTON         962904      291289535

41) 2          ANTILLES NEERLANDAISES                WILLEMSTAD             80         210000


 */
