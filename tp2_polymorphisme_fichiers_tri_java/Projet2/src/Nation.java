/*ENTETE
Cours: IFT 1170; Trimestre d'étude: Hiver 2023
Nom du fichier: Nation.java
Nom de l'etudiante: Naromba Condé
Matricule: 20251772 
Description du programme:
Le programme li un fichier et rempli un tableau de pays, il compte et retourne le nombre effectif de pays lu
il affiche les 10 premiers pays lu a l'aide de la redefinition de toString
il modifie et il affiche quelques pays lu 
Le programme contient des instructions permettant d'effectuer un tri,de faire une recherche dichotomique de certains pays
Le programe cree des fichers appeles Europe.txt qui contient seulement les pays d’Europe et Asie.txt qui contient seulement les pays d’Asie
*/


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class Pays {

	private int code;
	private String nom, capitale;
	private double superficie;
	private int population;

	public Pays(int code, String nom, String capitale, double superficie, int population) {
		this.code = code;
		this.nom = nom;
		this.capitale = capitale;
		this.superficie = superficie;
		this.population = population;
	}

	public String getNom() {
		return nom;
	}

	public int getPopulation() {
		return population;
	}

	public String getCapitale() {
		return capitale;
	}

	public int getCode() {
		return code;
	}

	public String toString() {
		return String.format("%d  %22s %20s %14.0f %14d\n", code, nom, capitale, superficie, population);

	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		else if (!(obj instanceof Pays))
			return false;
		else {
			Pays autre = (Pays) obj;
			return nom.trim().equalsIgnoreCase(autre.nom.trim());
		}
	}

	public void setPopulation() {
		this.population *= 10;
	}

}

public class Nation {

	// lire et remplire le tableau
	
	static int lireRemplir(String nomFichier, Pays[] pays) throws IOException {
		boolean existeFichier = true; 
		int n = 0;

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
					String nom = uneLigne.substring(1, 35).trim();
					String capitale = uneLigne.substring(35, 61).trim();

					double superficie = Double.parseDouble(uneLigne.substring(61, 70).trim());
					int population = Integer.parseInt(uneLigne.substring(70).trim());

					pays[n++] = new Pays(code, nom, capitale, superficie, population);

				}
			}
			entree.close();

		}
		return n;
	}

//	// AFFICHE LES 10 PREMIERS PAYS LUS
	
	static void afficher(Pays[] pays, int nbPays, String message) {
		System.out.printf("Contenu du tableau de %d pays %s :\n", nbPays, message);
		System.out.printf("code           Nom du pays    Capitale         Superficie    population");
		System.out.printf("\n-----------------------------------------------------------------------------\n");

		for (int i = 0; i < nbPays; i++)
			System.out.printf(pays[i].toString());
		System.out.printf("\n\n");
	}

	// CHANGE ET AFFICHE LES 8 PREMIERS PAYS LUS APRES MODIFICATION
	
	static void modifiePop(Pays[] pays, String nomPays, int nbPays) {
		for (int i = 0; i < nbPays; i++) {
			if (pays != null && pays[i].getNom().equals(nomPays)) {
				pays[i].setPopulation();
			}
		}
	}

	// AFFICHE LES PAYS AYANT LE MEME NOM QUE LA CAPITALE
	
	static void compare(Pays[] pays, int nbPays) {
		System.out.printf("Les pays dont les noms sont identiques aux nom de leurs capitales sont\n");
		System.out.printf("code           Nom du pays    Capitale         Superficie    population");
		System.out.printf("\n-----------------------------------------------------------------------------\n");
		for (int i = 0; i < nbPays; i++) {
			if (pays != null && pays[i].getNom().equals(pays[i].getCapitale())) {
				System.out.printf(pays[i].toString());
			}
		}
	}

	// Effectue le tri rapide
	
	static int partitionner(Pays[] pays, int debut, int fin) {
		int g = debut, d = fin;
		String valPivot = pays[debut].getNom();
		Pays tempo;

		do {
			while (g <= d && (pays[g].getNom().compareTo(valPivot) <= 0))
				g++;
			while (pays[d].getNom().compareTo(valPivot) > 0)
				d--;

			if (g < d)

			{
				tempo = pays[g];
				pays[g] = pays[d];
				pays[d] = tempo;
			}
		} while (g <= d);
		tempo = pays[debut];
		pays[debut] = pays[d];
		pays[d] = tempo;

		return d;
	}

	static void quickSort(Pays[] pays, int gauche, int droite) {
		if (droite > gauche) 
		{
			int indPivot = partitionner(pays, gauche, droite);
			quickSort(pays, gauche, indPivot - 1);
			quickSort(pays, indPivot + 1, droite);
		}
	}

	// Adaptation de la recherche dichotomique
	
	static int indDicho(String aChercher, int nbElem, Pays[] pays) {
		int mini = 0, maxi = nbElem - 1;

		while (mini <= maxi) {
			int milieu = (mini + maxi) / 2;
			if (aChercher.trim().compareToIgnoreCase(pays[milieu].getNom().trim()) < 0)
				maxi = milieu - 1;
			else if (aChercher.trim().compareToIgnoreCase(pays[milieu].getNom().trim()) > 0)
				mini = milieu + 1;
			else
				return milieu;
		}
		return -1; 
	}

	// création d'un fichier de type texte
	
	static void creerTexte(int code, Pays[] pays, int nbPays, String nomACreer) throws IOException {

		boolean probleme = false;
		FileWriter fw = null;
		try {
			fw = new FileWriter(nomACreer);
		} catch (java.io.FileNotFoundException erreur) {
			System.out.println("Problème de préparer l'écriture\n");
			probleme = true;
		}
		if (!probleme) {
			System.out.println("Début de la création du fichier\n");
			PrintWriter aCreer = new PrintWriter(fw);

			for (int i = 0; i < nbPays; i++)
				if (pays[i].getCode() == code)
					aCreer.printf("%s\n", pays[i]);

			aCreer.close();
			System.out.println("Fin de la création du fichier " + nomACreer + "\n\n");
		}

	}

	public static void main(String[] args) throws IOException {
		final int MAX_PAYS = 250;
		Pays[] pays = new Pays[MAX_PAYS];
		int nbPays = lireRemplir("/Users/amba/Documents/dossier_tp2/pays.txt", pays);
		// question 1
		System.out.printf("le  nombre de pays lus dans le fichier est %d\n", nbPays);
		System.out.printf("\n\n");

		if (nbPays > 0) {

			// question 2
			afficher(pays, 10, "avant modification");
			System.out.printf("\n\n");

			// question 3
			modifiePop(pays, "ALLEMAGNE", nbPays);
			afficher(pays, 8, "apres modification");
			System.out.printf("\n\n");

			// question 4
			compare(pays, nbPays);
			System.out.printf("\n\n");

			// question 5
			quickSort(pays, 0, nbPays - 1);
			afficher(pays, 10, "Apres le tri");
			System.out.printf("\n\n");

			// question 6
			String[] aChercher = { "Canada", "France", "Japon", "Mexique" };
			int nomPaysR = aChercher.length;

			for (int i = 0; i < nomPaysR; i++) {
				System.out.printf("La recherche dichotomique de %s\n", aChercher[i]);
				int k = indDicho(aChercher[i], nbPays, pays);
				if (k < 0)
					System.out.printf("On ne trouve pas ce paysn\n");
				else {

					int debut = k, fin = k;
					while (debut >= 0 && pays[debut].equals(pays[k]))
						debut--;
					debut++;

					while (fin < nbPays && pays[fin].equals(pays[k]))
						fin++;
					fin--;


					if (debut == fin)
						System.out.printf("L'unique pays trouve :\n%s\n", pays[k]);
					else
						for (int z = debut; z <= fin; z++)
							System.out.printf("%5d) %s\n", z, pays[z]);
				}
			}

		}

		creerTexte(5, pays, nbPays, "/Users/amba/Documents/dossier_tp2/Europe.txt");
		
		creerTexte(3, pays, nbPays, "/Users/amba/Documents/dossier_tp2/Asie.txt");

	}
}

/*EXECUTION
 le  nombre de pays lus dans le fichier est 197


Contenu du tableau de 10 pays avant modification :
code           Nom du pays    Capitale         Superficie    population
-----------------------------------------------------------------------------
2              ETATS-UNIS           WASHINGTON        9629047      291289535
3                   CHINE                PEKIN        9596960     1273111290
5                  RUSSIE               MOSCOU       17075400      143954573
4               AUSTRALIE             CANBERRA        7686850       19834248
3                   JAPON                TOKYO         377835       12761000
5               ALLEMAGNE               BERLIN         357027        8253700
5                  FRANCE                PARIS         543964       61387038
5                  ITALIE                 ROME         301230       57715620
3            COREE DU SUD                SEOUL          99274       48324000
5             ROYAUME-UNI              LONDRES         244101       58789194




Contenu du tableau de 8 pays apres modification :
code           Nom du pays    Capitale         Superficie    population
-----------------------------------------------------------------------------
2              ETATS-UNIS           WASHINGTON        9629047      291289535
3                   CHINE                PEKIN        9596960     1273111290
5                  RUSSIE               MOSCOU       17075400      143954573
4               AUSTRALIE             CANBERRA        7686850       19834248
3                   JAPON                TOKYO         377835       12761000
5               ALLEMAGNE               BERLIN         357027       82537000
5                  FRANCE                PARIS         543964       61387038
5                  ITALIE                 ROME         301230       57715620




Les pays dont les noms sont identiques aux nom de leurs capitales sont
code           Nom du pays    Capitale         Superficie    population
-----------------------------------------------------------------------------
1                DJIBOUTI             DJIBOUTI          22000         460700
3                  KOWEIT               KOWEIT          17820        2041961
5              LUXEMBOURG           LUXEMBOURG           2586         442972
5                  MONACO               MONACO            195          31842
2                  PANAMA               PANAMA          78200        2845647
5             SAINT MARIN          SAINT MARIN             61          27336
1                SAO TOME             SAO TOME           1001         165034


Contenu du tableau de 10 pays Apres le tri :
code           Nom du pays    Capitale         Superficie    population
-----------------------------------------------------------------------------
3             AFGHANISTAN               KABOUL         652225       29547078
1          AFRIQUE DU SUD             PRETORIA        1219912       42718530
5                 ALBANIE               TIRANA          28748        3510484
1                 ALGERIE                ALGER        2381740       31763053
5               ALLEMAGNE               BERLIN         357027       82537000
5                 ANDORRE     ANDORRA LA VELLA            468          67627
1                  ANGOLA               LUANDA        1246700       10766471
2      ANTIGUA-ET-BARBUDA          SAINT-JOHNS            442          67448
2  ANTILLES NEERLANDAISES           WILLEMSTAD            800         210000
3         ARABIE SAOUDITE                RIYAD        1960582       23513330




La recherche dichotomique de Canada
L'unique pays trouve :
2                  CANADA               OTTAWA        9984670       31499560

La recherche dichotomique de France
L'unique pays trouve :
5                  FRANCE                PARIS         543964       61387038

La recherche dichotomique de Japon
L'unique pays trouve :
3                   JAPON                TOKYO         377835       12761000

La recherche dichotomique de Mexique
L'unique pays trouve :
2                 MEXIQUE               MEXICO        1972550      103400165

Début de la création du fichier

Fin de la création du fichier /Users/amba/Documents/dossier_tp2/Europe.txt


Début de la création du fichier

Fin de la création du fichier /Users/amba/Documents/dossier_tp2/Asie.txt



 */


