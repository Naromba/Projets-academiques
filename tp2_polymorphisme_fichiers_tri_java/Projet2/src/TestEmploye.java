
/*ENTETE
Cours: IFT 1170; Trimestre d'étude: Hiver 2023
Nom du fichier: TestEmploye.java
Nom de l'etudiante: Naromba Condé
Matricule: 20251772 
Description du programme:
Le programme contient des constructeurs qui permettent d'instancier les elements de la classe employe
Le programme permet d'afficher certains elements de la classe Employe
Le programme contient un tableau de type Employe que l'on affiche et dont on se sert pour apporter des modifications ou afficher des elements de ce tableau
Le programme contient un ficher text appele empTri.dta qui affiche les information de toutes les personnes dont le salaireest supperieur a 11100$
   
*/

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class Employe {
	private String NAS;
	private double salHebdo;

	public Employe(String NAS, double salHebdo) {
		this.NAS = NAS;
		this.salHebdo = salHebdo;
	}

	public Employe(String NAS) {
		this.NAS = NAS;
		this.salHebdo = 1250.25;

	}

	public Employe(String NAS, double nbrH, double tauxH) {
		this(NAS, (nbrH * tauxH));
	}

	public String toString() {
		return String.format("%15s || %10.2f\n", NAS, salHebdo);
	}

	// AFFICHER
	public void afficher(String mess) {
		System.out.printf("%s NAS:%15s  Salaire: %7.2f$\n", mess, NAS, salHebdo);
	}

	public String getNAS() {
		return NAS;
	}

	public double getSalHebdo() {
		return salHebdo;
	}

	public Employe(String NAS, int nbreHeure, double tauxHoraire) {
		this.NAS = NAS;
		this.salHebdo = nbreHeure * tauxHoraire;

	}

	public void setSalHebdo(Employe emp1, Employe emp2) {
		this.salHebdo = emp1.getSalHebdo() + emp2.getSalHebdo();
	}
}

public class TestEmploye {

	// Affiche le contenu du tableau employe

	static void affTableau(Employe[] emp, int nbreEmp, String mess) {
		System.out.printf(" %s \n\n", mess);
		System.out.printf("       Indice    ||   TABLEAU D'EMPLOYES ");
		System.out.printf("\n--------------------------------------------\n");

		for (int i = 0; i < nbreEmp; i++) {
			System.out.printf(i + emp[i].toString());
		}

	}

	// Compte et affiche le nombre d'employés

	static int compte(Employe[] emp, double salaireHeb, String NASContient) {
		int compteur = 0;

		for (int i = 0; i < emp.length; i++) {
			if (emp[i].getSalHebdo() < salaireHeb && emp[i].getNAS().contains(NASContient) == true) {
				compteur++;
			}
		}

		return compteur;
	}

	// Adaptation du tri rapide
	static int partitionner(Employe[] T, int debut, int fin) {
		int g = debut, d = fin;
		String valPivot = T[debut].getNAS();
		Employe tempo;

		do {
			while (g <= d && (T[g].getNAS().compareTo(valPivot) <= 0))
				g++;
			while (T[d].getNAS().compareTo(valPivot) > 0)
				d--;

			if (g < d)

			{
				tempo = T[g];
				T[g] = T[d];
				T[d] = tempo;
			}
		} while (g <= d);

		tempo = T[debut];
		T[debut] = T[d];
		T[d] = tempo;

		return d;
	}

	static void quickSort(Employe[] T, int gauche, int droite) {
		if (droite > gauche) /* au moins 2 ?l?ments */
		{
			int indPivot = partitionner(T, gauche, droite);
			quickSort(T, gauche, indPivot - 1);
			quickSort(T, indPivot + 1, droite);
		}
	}

	// création d'un fichier de type texte
	static void creerTexte(double salaire, Employe[] emp, int nbEmp, String nomACreer)
			throws IOException {

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

			for (int i = 0; i < nbEmp; i++)
				if (emp[i].getSalHebdo() >= salaire)
					aCreer.printf("%s\n", emp[i]);

			aCreer.close();
			System.out.println("Fin de la création du fichier " +
					nomACreer + "\n\n");
		}

	}

	public static void main(String[] args) throws IOException {

		Employe emp1 = new Employe("250 642 753", 1234.56), emp2 = new Employe("123 456 789"), // par défaut 1250.25$
				emp3 = new Employe("250 343 678", 40.00, 25.75),
				// salHebdo = 40 heures par semaine x taux d’horaire 25.75$
				emp4 = new Employe("450 279 321", 1750.75);

		Employe[] emp = { new Employe("250 642 753", 1234.25), new Employe("123 456 789"),
				new Employe("250 343 654", 40.00, 17.25), new Employe("231 467 890", 1671.50),
				new Employe("478 343 689", 1750.75), new Employe("371 238 432", 50, 20.25) };
		int nbEmp = emp.length;

		// afficher les informations de emp3 et emp4
		emp3.afficher("\nLes informations du 3e employer sont");

		emp4.afficher("\nLes informations du 4e employer sont");

		// MODIFIE SALAIRE

		emp2.setSalHebdo(emp1, emp3);

		emp2.afficher("\nLes nouvelles informations du 2e employer sont ");

		// Affiche le tableau d'employer

		affTableau(emp, nbEmp, "\nAffiche le tableau d'employes");

		// Compte le nombre d'employer
		System.out.printf(
				"\nle nombre d'employe qui gagnent moins de 1300.00$ par semaine dont le NAS contient ‘5’ est : %d\n",
				compte(emp, 1300, "5"));

		System.out.printf(
				"\nle nombre d'employe qui gagnent moins de 750.00$ par semaine dont le NAS contient ‘3’est: %d\n",
				compte(emp, 750, "3"));

		// Effectue le tri rapide

		quickSort(emp, 0, nbEmp - 1);

		affTableau(emp, nbEmp, "Le tableau apres le tri\n");

		// Creation du ficher text selon la salaire d'un employé
		creerTexte(1100, emp, nbEmp, "/Users/amba/Documents/dossier_tp2/empTri.dta.txt");

	}
}

/*
 * EXECUTION
 * 
 * Les informations du 3e employer sont NAS: 250 343 678 Salaire: 1030,00$
 * 
 * Les informations du 4e employer sont NAS: 450 279 321 Salaire: 1750,75$
 * 
 * Les nouvelles informations du 2e employer sont NAS: 123 456 789 Salaire:
 * 2264,56$
 * 
 * Affiche le tableau d'employes :
 * 
 * Indice || TABLEAU D'EMPLOYES
 * --------------------------------------------
 * 0 250 642 753 || 1234,25
 * 1 123 456 789 || 1250,25
 * 2 250 343 654 || 690,00
 * 3 231 467 890 || 1671,50
 * 4 478 343 689 || 1750,75
 * 5 371 238 432 || 1012,50
 * 
 * le nombre d'employe qui gagnent moins de 1300.00$ par semaine dont le NAS
 * contient ‘5’ est : 3
 * 
 * le nombre d'employe qui gagnent moins de 750.00$ par semaine dont le NAS
 * contient ‘3’est: 1
 * Le tableau apres le tri
 * :
 * 
 * Indice || TABLEAU D'EMPLOYES
 * --------------------------------------------
 * 0 123 456 789 || 1250,25
 * 1 231 467 890 || 1671,50
 * 2 250 343 654 || 690,00
 * 3 250 642 753 || 1234,25
 * 4 371 238 432 || 1012,50
 * 5 478 343 689 || 1750,75
 * Début de la création du fichier
 * 
 * Fin de la création du fichier /Users/amba/Documents/dossier_tp2/empTri.dta
 * 
 */