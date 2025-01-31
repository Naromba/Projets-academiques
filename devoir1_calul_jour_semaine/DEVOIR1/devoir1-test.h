#pragma once

#include <iostream>

/// <summary>
/// Le nom des mois.
/// </summary>
const char* _MOIS[13] = {
	"pas utilise",
	"janvier",
	"fevrier",
	"mars",
	"avril",
	"mai",
	"juin",
	"juillet",
	"aout",
	"septembre",
	"octobre",
	"novembre",
	"decembre"
};

/// <summary>
/// Le nom des jours de la semaine.
/// </summary>
const char* _JOURSEMAINE[8] = {
	"pas utilise",
	"dimanche",
	"lundi",
	"mardi",
	"mercredi",
	"jeudi",
	"vendredi",
	"samedi"
};

/// <summary>
/// Retourne le jour de la semaine pour la date donnée.
/// </summary>
/// <param name="annee">L'année à 4 chiffres (exemple: 2022). Valeurs permises : [1800, 2099].</param>
/// <param name="mois">Le mois (à partir de 1); janvier=1, février=2, etc.</param>
/// <param name="jour">Le jour (à partir de 1)</param>
/// <returns>Le jour de la semaine (à partir de 1); dimanche=1, lundi=2, etc.</returns>
int _jourSemaine(int annee, int mois, int jour)
{
	annee -= mois < 3;

	const int resultat = (
		annee 
		+ annee / 4 
		- annee / 100 
		+ annee / 400 
		+ "-bed=pen+mad."[mois] 
		+ jour
	) % 7; // [0, 6]; 0=dimanche, 1=lundi, etc.

	return resultat + 1; // [1, 7]; 1=dimanche, 2=lundi, etc.
}

/// <summary>
/// Teste si le jour de la semaine fourni correspond à la date fournie.
/// Affiche un message qui affiche le succès ou insuccès.
/// </summary>
/// <param name="annee">L'année à 4 chiffres (exemple: 2022). Valeurs permises : [1800, 2099].</param>
/// <param name="mois">Le mois (à partir de 1); janvier=1, février=2, etc.</param>
/// <param name="jour">Le jour (à partir de 1)</param>
/// <param name="jourSemaine">Le jour de la semaine (à partir de 1); dimanche=1, lundi=2, etc.</param>
/// <param name="silencieux">Si vrai, les messages ne sont pas affichés</param>
/// <returns>Vrai ssi le jour de la semaine fourni correspond à la date fournie</returns>
bool test(int annee, int mois, int jour, int jourSemaine, bool silencieux = false)
{
	// voir : https://stackoverflow.com/a/54062826
	// pour les couleurs dans la console

	const int verifie = _jourSemaine(annee, mois, jour);

	if (!silencieux)
	{
		std::cout << std::endl;

		std::cout << ">> Vous avez dit que le "
			<< jour << " "
			<< _MOIS[mois] << " "
			<< annee << " "
			<< "est un "
			<< _JOURSEMAINE[jourSemaine]
			<< std::endl
			;
	}
	
	bool succes = (jourSemaine == verifie);

	if (!silencieux)
	{
		if (succes)
		{
			std::cout << "\x1B[92m>> CORRECT! C'est en fait un " << _JOURSEMAINE[verifie] << "\033[0m\t\t";
		}
		else
		{
			std::cout << "\x1B[91m>> INCORRECT! C'est en fait un " << _JOURSEMAINE[verifie] << "\033[0m\t\t";
		}

		std::cout << std::endl;
	}

	return succes;
}
