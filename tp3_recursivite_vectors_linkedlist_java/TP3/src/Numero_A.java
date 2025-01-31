/*
ENTETE
Cours: IFT 1170; Trimestre d'étude: Hiver 2023
Nom du fichier: Numero_A.java
Nom de l'etudiante: Naromba Condé
Matricule: 20251772 

Description du programme: 
 Le code suivant est un programme Java qui contient deux méthodes pour afficher un nombre entier de manière verticale. 
 Il utilise la récursivité et les boucles.
 La méthode "afficherV" utilise la récursivité pour séparer le nombre en chiffres individuels et les afficher verticalement.
 Tandis que la méthode "afficherV2" utilise une boucle pour convertir le nombre en une chaîne de caractères et afficher chaque caractère verticalement.
 */


public class Numero_A {
	
	//Affiche un entier n en vertical en utilisant la recursivite
	static void afficherV (int n) {
		if (n < 10) {
			System.out.printf("%d\n" , n);
		}
		else {
			afficherV (n / 10);
			System.out.printf("%d\n", n % 10 );
		}
		
	}
	//Affiche un entier n en vertical en utilisant les boucles
	static void afficherV2 (int n) {
		 String intEnString = Integer.toString(n);
		 for (int i =0; i < intEnString.length(); i++) {
			 System.out.printf("%s\n", intEnString.charAt(i));
		 }
	}
	
	
	public static void main(String[] args) {
		//Affichage avec fonction recursive
		System.out.printf("Affichage avec fonction recursive\n");
		System.out.printf("---------------------------------\n");
		System.out.printf("---------------------------------\n");
		System.out.printf("2731 en verticale\n");
		afficherV(2731);
		
		System.out.printf("\n41376 en verticale\n");
		afficherV(41376);

		//Affichage avec fonction iterative
		System.out.printf("Affichage avec fonction iterative\n");
		System.out.printf("---------------------------------\n");
		System.out.printf("---------------------------------\n");
		System.out.printf("2731 en verticale\n");
		afficherV2(2731);
		
		System.out.printf("\n41376 en verticale\n");
		afficherV2(41376);

	}

}
/* EXECUTION
 Affichage avec fonction recursive
---------------------------------
---------------------------------
2731 en verticale
2
7
3
1

41376 en verticale
4
1
3
7
6
Affichage avec fonction iterative
---------------------------------
---------------------------------
2731 en verticale
2
7
3
1

41376 en verticale
4
1
3
7
6
*/
