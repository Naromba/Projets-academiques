
//ENTETE
//Cours: IFT 1170; Trimestre d'étude: Hiver 2023
//Nom du fichier: Tp1C.java
//Nom de l'etudiante: Naromba Condé
//Matricule: 20251772
//Description du programme: 
//Le programme parcourt un  tableau de type Personne,
// il affiche les informations contenues dans le tableau.
//il modifie et affiche le contenu du tableau a l'aide des guetteurs et des setteurs de la classe Personne
//il fait le tri et compte certains éléments du tableau de type Personne



class Personne {
	private String naissance;
	private int nbCafe;

	public Personne(String naissance, int nbCafe) {
		this.naissance = naissance;
		this.nbCafe = nbCafe;

	}

	public Personne(String naissance) {
		this.naissance = naissance;
		this.nbCafe = 1;

	}
	public String toString() {
		return String.format("%s %s ", naissance,nbCafe);
	}

	public String getNaissance() {
		return naissance;
	}

	public int getNbCafe() {
		return nbCafe;
	}

	public int getAnnee() {
		return Integer.parseInt(naissance.substring(6));
	}

	public int getMois() {
		return Integer.parseInt(naissance.substring(3, 5));
	}

	public int getJour() {
		return Integer.parseInt(naissance.substring(0, 2));
	}

	public void setNbCafe(int plusCafe) {
		this.nbCafe = plusCafe;
	}
}

public class Tp1C {
	// Affiche le contenu du tableau personne
	static void afficher(Personne[] pers, int nbreP, String mess) {
		System.out.printf(" %s :\n\n", mess);
		System.out.printf("%2s %-8s %n", "Indice", "Tableau pers ");
		System.out.printf("--------------------------------------\n");
		for (int i = 0; i < nbreP; i++) {
			System.out.printf("%2d \"%-8s\" %2d tasses\n", i, pers[i].getNaissance(), pers[i].getNbCafe());
		}
		System.out.printf("\n\n");
	}

	// Determine et affiche les informations d'une personne qui consomme le moins de
	// cafe
	static void affPersCafe(Personne[] pers, int nbreP, String mess) {
		System.out.printf(" %s :\n\n", mess);

		int min = Integer.MAX_VALUE;
		int position = 0;

		for (int i = 0; i < nbreP; i++) {
			if (pers[i].getNbCafe() < min && pers[i].getNbCafe() >= 0) {
				min = pers[i].getNbCafe();
				position = i ;
			}
		}
		System.out.printf("Date de naissance: %s, Nombre de cafés: %2d", pers[position].getNaissance(), min);
		System.out.printf("\n\n");
	}

	static void reduire1EtAff(Personne[] pers, int nbreP) {
		for (int i = 0; i < nbreP; i++) {
			if (pers[i].getNbCafe() == 0) {
				pers[i].setNbCafe(0);
			} else {
				pers[i].setNbCafe(pers[i].getNbCafe() - 1);
			}

		}
	}

	// Compare les annees, les mois et les jours de naissance
	static boolean compareMoisNaiss(Personne anneeNaiss1, Personne anneeNaiss2) {
		if (anneeNaiss1.getAnnee() == anneeNaiss2.getAnnee()) {
			if (anneeNaiss1.getMois() == anneeNaiss2.getMois()) {
				if (anneeNaiss1.getJour() <= anneeNaiss2.getJour()) {
					return true;
				} else {
					return false;
				}
			} else if (anneeNaiss1.getMois() <= anneeNaiss2.getMois()) {
				return true;

			} else {
				return false;

			}
		} else if (anneeNaiss1.getAnnee() <= anneeNaiss2.getAnnee()) {
			return true;
		} else {
			return false;
		}
	}

	// Effectue le tri
	static void trier(Personne[] pers, int nbreP) {
		for (int i = 0; i < nbreP - 1; i++) {
			int indMin = i;
			for (int j = i + 1; j < nbreP; j++)
				if (compareMoisNaiss(pers[j], pers[indMin]))
					indMin = j;
			if (indMin != i) {
				Personne tempo = pers[i];
				pers[i] = pers[indMin];
				pers[indMin] = tempo;
			}
		}
	}

	// Compte et affiche le nombre de personne nees en 1990
	static int compteAffiche90(Personne[] pers, int nbreP, int annee) {
		int nbrePers = 0;

		for (int i = 0; i < nbreP; i++) {
			if (pers[i].getAnnee() == 1990) {
				nbrePers += 1;
			}
		}

		return nbrePers;
	}

	public static void main(String[] arg) {
		Personne[] pers = { new Personne("16/05/1992", 2), new Personne("02/01/1990"), new Personne("23/05/1990", 5),
				new Personne("19/02/1985", 0), new Personne("30/05/1991", 2), new Personne("31/01/1990", 4), };
		int nbPers = pers.length;

		afficher(pers, nbPers, "Affiche le contenu du tableau personne");
		affPersCafe(pers, nbPers, "Les informations de la personne qui consomme le moins de cafe sont");
		reduire1EtAff(pers, nbPers);
		afficher(pers, nbPers, "Réduit de 1 la consommation de cafe et reaffiche le tableau");
		trier(pers, nbPers);
		afficher(pers, nbPers, "Tri et affiche le contenu du tableau personne");
		System.out.printf("Le nombre de personnes nées en 1990 est: %d\n", compteAffiche90(pers, nbPers, 1990));

	}

}