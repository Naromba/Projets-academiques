#pragma once

#include <iostream>

/// <summary>
/// La longueur des suites de Collatz des nombres 1 à 100 (inclusivement).
/// L'indice zéro est laissé vide.
/// </summary>
const int _LONGUEURS[101] = {
	0,
	2, 1, 5, 2, 4, 6, 11, 3, 13, 5,
	10, 7, 7, 12, 12, 4, 9, 14, 14, 6,
	6, 11, 11, 8, 16, 8, 70, 13, 13, 13,
	67, 5, 18, 10, 10, 15, 15, 15, 23, 7,
	69, 7, 20, 12, 12, 12, 66, 9, 17, 17,
	17, 9, 9, 71, 71, 14, 22, 14, 22, 14,
	14, 68, 68, 6, 19, 19, 19, 11, 11, 11,
	65, 16, 73, 16, 11, 16, 16, 24, 24, 8,
	16, 70, 70, 8, 8, 21, 21, 13, 21, 13,
	59, 13, 13, 67, 67, 10, 75, 18, 18, 18
};

/// <summary>
/// Teste si la longueur fournie correspond à la longueur de la suite de Collatz du nombre fourni.
/// Affiche un message si la longueur fournie est incorrecte.
/// </summary>
/// <param name="nombre">Le nombre N. Valeurs permises : [1, 100].</param>
/// <param name="longueur">La longueur de la suite de Collatz du nombre N.</param>
/// <param name="silencieux">Si vrai, les messages ne sont pas affichés</param>
/// <returns>Vrai ssi la longueur fournie correspond à la longueur de la suite de Collatz du nombre fourni</returns>
bool test(int nombre, int longueur, bool silencieux = false)
{
	// voir : https://stackoverflow.com/a/54062826
	// pour les couleurs dans la console

	const int verifie = _LONGUEURS[nombre];
	
	bool succes = (longueur == verifie);

	if (!silencieux && !succes)
	{
		std::cout << "\x1B[91m>> INCORRECT! La longueur de la suite du nombre " << nombre << " est en fait " << verifie << "\033[0m\t\t" << std::endl;
	}

	return succes;
}
