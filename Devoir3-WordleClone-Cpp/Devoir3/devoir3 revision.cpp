
#include <iostream>

#include "devoir3-test.h"

using namespace std;

int choisirIndiceSolutionAuHasard (int nombreSolution);
void mettreMotEnMajuscules(char tableau[]);
void evaluerReponse(char* Tableau, char* solution, ResultatLettre* resultat);
// TODO: Partie 1 - Compléter ici
int choisirIndiceSolutionAuHasard (int nombreSolution)
{
    srand((unsigned int)time(NULL));
    int  resultat = rand() % nombreSolution;
    
    return resultat;
}

// TODO: Partie 2 - Compléter ici
void mettreMotEnMajuscules(char* tableau)
{
    for(int i=0;i<LONGUEUR_MOT;i++){
        tableau[i]-=32;
    }
}

// TODO: Partie 3 - Compléter ici
void evaluerReponse(char* reponse, char* solution, ResultatLettre* resultat)
{
    for(int i=0; i<LONGUEUR_MOT;i++){
        if (reponse[i]==solution[i]){
            resultat[i]=ResultatLettre::CORRECTE;
        }
        else{
            resultat[i]=ResultatLettre::ABSENTE;
        }
    }
    for (int j=0; j<LONGUEUR_MOT; j++) {
        for (int k=0; k<LONGUEUR_MOT; k++) {
            if (reponse[j]==solution[k] && resultat[j]!=ResultatLettre::CORRECTE) {
                resultat[j]=ResultatLettre::PRESENTE;
            }
        }
    }
}

int main()
{
//    srand((unsigned int)time(NULL));
//    int nombreSolution=6;
//    choisirIndiceSolutionAuHasard (nombreSolution);
//    char tableau[] ="ambac";
//    mettreMotEnMajuscules(tableau);
//    char reponse[]="naram";
//    char solution[]="azazb";
//    ResultatLettre resultat[5];
//    evaluerReponse(reponse,solution,resultat);
//
	// démarrer le jeu

	jouer(
		choisirIndiceSolutionAuHasard,
		mettreMotEnMajuscules,
		evaluerReponse,
		Langue::FRANCAIS, // mettre Langue::ANGLAIS pour jouer en anglais
		false // mettre à true pour afficher la solution et permettre d'entrer n'importe quel mot de 5 lettres (false pour appliquer les vraies règles)
	);

	return 0;
}
