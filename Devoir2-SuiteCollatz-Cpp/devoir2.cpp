// INFO1101 - DEVOIR 2
// Donnez une brève explication du devoir ici: Dans ce devoir, on commence par calculer la longueur de Collatz de nombres entiers compris entre 0 et 100 qu'on stock dans un tableau. Ensuite, on parcours le tableau pour afficher des astŽrisques correspondant ˆ chaque longueur de Collatz stockŽe dans le tableau.
// Nom: CondŽ Naromba
// Matricule: A00217607

#include <iostream>

// //////////////////////////// //
// NE PAS SUPPRIMER CETTE LIGNE //
// //////////////////////////// //
#include "devoir2-test.h"

using namespace std;

int main()
{
	// Partie 1 : Calcul
    
    int Tab[101]={};
    int longueur=0;

	// TODO: répétition définie (100 itérations)
    
    for (int i = 1; i < 101; i++) {
          int N = i;
        
          do {
        
              if(N%2 == 0) {
                  N = N / 2;
              } else {
                  N = ((N*3) + 1) / 2;
              }
              longueur+=1;
          } while(N != 1);
             
              Tab[i] = longueur;
              longueur = 0;
        
          test(
              i, // <-- mettez ici la variable qui contient le nombre courant (un int)
              Tab[i] // <-- mettez ici la variable qui contient la longueur de la suite (un int)
        );
      }
    
	// Partie 2 : Affichage

	// TODO: compléter ici
    for (int i = 1; i <= 100; i++) {
        int nEtoile = Tab[i];

        if(i <= 9){
            cout<<"  "<<i <<" | ";
        }
        else if( i >= 10 && i <= 99){
            cout<<" "<<i <<" | ";

        }
        else {
            cout<<i <<" | ";

        }
          do{
              cout<<"*";
              nEtoile -= 1;
            }while(nEtoile >= 1);
        
        cout<<endl;
      }
    
	return 0;
}
