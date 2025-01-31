
//ENTETE
//Cours: IFT 1170; Trimestre d'étude: Hiver 2023
//Nom du fichier: Tp1a.java
//Nom de l'etudiante: Naromba Condé
//Matricule: 20251772
//Description du programme:
//Le programme parcourt un tableau de caractere et un tableau d'entier,
//il calcul ou/et affiche certaines informations contenues dans le tableau.


public class Tp1a {

//1-Afficher le contenu des deux tableaux.
	static void affichage(char[] poste, int[] nbcafe, int nbEmp) {
		System.out.printf("le contenu des tableaux est \n");
		System.out.printf("Indice   Poste   NbCafe\n");
		for (int i = 0; i < nbEmp; i++)
			System.out.printf("%3d) %7s %7d \n", i, poste[i], nbcafe[i]);
	}

//2-Compter et afficher: les nombres d'operateurs,d'analistes et de secretaires.
	static int compter(char[] poste, char carVoulu) {
		int nbEmp = poste.length;
		int compteur = 0;
		for (int i = 0; i < nbEmp; i++) {
			if (poste[i] == carVoulu) {
				compteur++;

			}
		}
		return compteur++;

	}
//2-d-Compte et afficher le nombre de programmeurs qui consomment 3 tasses ou plus de cafe.

	static int nbreCPro(char[] poste, int[] nbcafe, int nbEmp) {
		int n = 0;
		for (int i = 0; i < nbEmp; i++) {
			if (poste[i] == 'P' && nbcafe[i] >= 3) {
				n++;
			}
		}
		return n++;

	}

//3-Calculer et afficher la consommation moyenne des programmeurs.
	static double nbreMoyC(char[] poste, int[] nbcafe, int nbEmp, int nbreP) {
		double nbreMoyenCafe = 0;
		double total = 0;

		for (int i = 0; i < nbEmp; i++) {
			if (poste[i] == 'P') {
				total += nbcafe[i];
			}
		}

		nbreMoyenCafe = total / nbreP;

		return nbreMoyenCafe;
	}

//4-Calculer et afficher la consommation maximale de cafes des analystes
	static int nbreMaxCA(char[] poste, int[] nbcafe, int nbEmp) {
		int max = 0;

		for (int i = 0; i < nbEmp; i++) {
			if (poste[i] == 'A' && max <= nbcafe[i]) {
				max = nbcafe[i];
			}
		}

		return max;

	}

//5-Calculer et afficher la consommation minimale de cafe des operateurs.
	static int nbreMinCO(char[] poste, int[] nbcafe, int nbEmp) {
		int min = Integer.MAX_VALUE;

		for (int i = 0; i < nbEmp; i++) {
			if (poste[i] == 'O' && min >= nbcafe[i]) {
				min = nbcafe[i];
			}
		}

		return min;
	}

	public static void main(String[] arg) {

		char[] poste = { 'P', 'P', 'O', 'P', 'A', 'A', 'O', 'A', 'P' };
		int[] nbCafe = { 3, 1, 4, 0, 4, 2, 2, 5, 1 };
		int nbEmp = poste.length;
		// 1-Afficher le contenu des deux tableaux;
		affichage(poste, nbCafe, nbEmp);
		System.out.printf("\n");

		// 2-Compter et afficher:
		System.out.printf("Il ya:\n");
		System.out.printf("%d operateur(s)\n", compter(poste, 'O'));
		System.out.printf("%d analyste(s)\n", compter(poste, 'A'));
		System.out.printf("%d secretaire(S)\n", compter(poste, 'S'));
		System.out.printf("%d programmeur(s)qui cosomment au moins trois(3) cafes\n", nbreCPro(poste, nbCafe, nbEmp));
		System.out.printf("\n");

		// 3-Calculer et afficher la consommation moyenne des programmeurs.
		System.out.printf("la consommation moyenne de cafe  des programmeurs est: %.2f\n",
				nbreMoyC(poste, nbCafe, nbEmp, compter(poste, 'P')));
		System.out.printf("\n");

		// 4-Calculer et afficher la consommation maximale de cafe des analystes.
		System.out.printf("la consommation maximale de cafe  des analystes est: %d\n", nbreMaxCA(poste, nbCafe, nbEmp));
		System.out.printf("\n");

		// 5-Calculer et afficher la consommation minimale de cafe des operateurs.
		System.out.printf("la consommation minimale de cafe  des operateurs est: %d\n",
				nbreMinCO(poste, nbCafe, nbEmp));
		System.out.printf("\n");

	}
}
