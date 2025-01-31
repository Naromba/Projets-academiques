//Naromba CondŽ
//Matricule: A00217607
#include <iostream>

#include "devoir3-test.h"

using namespace std;

int choisirIndiceSolutionAuHasard (int nombreSolution);
void mettreMotEnMajuscules(char* tableau);
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
    
    bool utilises[5];
    for(int i=0; i<LONGUEUR_MOT;i++){
        if (reponse[i]==solution[i]){
            resultat[i]=ResultatLettre::CORRECTE;
            utilises[i]=true;
        }
    for(int i=0; i<LONGUEUR_MOT;i++){
        if (resultat[i]==ResultatLettre::CORRECTE)
         {continue;}
    }
    for(int j=0; j<LONGUEUR_MOT;j++){
        if(utilises[j]==true)
        {continue;}
    
        
        if (reponse[i]==solution[j]){
            resultat[i]=ResultatLettre::PRESENTE;
            utilises[j]=true;
            break;
        }
    }
    }
        
         

}

int main()
{
	// démarrer le jeu

	jouer(
		choisirIndiceSolutionAuHasard,
		mettreMotEnMajuscules,
		evaluerReponse,
		Langue::FRANCAIS, // mettre Langue::ANGLAIS pour jouer en anglais
		true // mettre à true pour afficher la solution et permettre d'entrer n'importe quel mot de 5 lettres (false pour appliquer les vraies règles)
	);

	return 0;
}
