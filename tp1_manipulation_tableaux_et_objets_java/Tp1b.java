
//ENTETE
//Cours: IFT 1170; Trimestre d'étude: Hiver 2023
//Nom du fichier: Tp1b.java
//Nom de l'etudiante: Naromba Condé
//Matricule: 20251772
//Description du programme:
//Le programme parcourt un tableau de string. Il  affiche le contenu du tableau sans le modifier, 
//il modifie le tableau en mettant les lettres en majuscules et en les reaffichant sns espace ni caractere accentués,
//il parcourt un nouveau tableau construit en inversnt le tableau de string et dit si la chaine de caractere  etudiéeest un palindrome ou non.



public class Tp1b {

	// Pour afficher les phrases
	static void affichagePhrase(String[] phrase) {

		for (int i = 0; i < phrase.length; i++) {
			System.out.printf("La phrase telle quelle: %s\n", phrase[i]);
			System.out.printf("La Phrase transformee: %s\n", transfMajEtEnlEspace(phrase[i]));

			if (palindrome(phrase[i]) == true) {
				System.out.printf("La phrase examinee est un palindrome\n");
			} else {
				System.out.printf("La phrase examinee n'est pas un palindrome\n");
			}

			System.out.printf("\n");
			System.out.printf("\n");
		}

	}

    //Transforme en majuscule et affiche que leslettres
	static String transfMajEtEnlEspace(String phrase) {

		String phraseT = phrase.toUpperCase().replaceAll("[^a-zA-Z]", "");

		return phraseT;
	}
    
	//Verifie si la phrase etudiee est un palindrome
	static boolean palindrome(String phrase) {
		String phraseInv = "";

		for (int i = phrase.length() - 1; i >= 0; --i) {
			phraseInv = phraseInv + phrase.charAt(i);
		}

		if (transfMajEtEnlEspace(phrase).equals(transfMajEtEnlEspace(phraseInv))) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] arg) {
		String[] phrase = { "Laval", "Montreal", "Tu l'as trop ecrase,Cesar,ce Port Salut!",
				"Et la marine va, papa, venir a Malte" };

		affichagePhrase(phrase);

	}
}
