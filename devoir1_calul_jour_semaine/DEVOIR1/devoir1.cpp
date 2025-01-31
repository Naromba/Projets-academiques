// INFO1101 - DEVOIR 1
// Donnez une brève explication du devoir ici
// Nom:Naromba CondŽ
// Matricule: A00217607

#include <iostream>

// //////////////////////////// //
// NE PAS SUPPRIMER CETTE LIGNE //
// //////////////////////////// //
#include "devoir1-test.h"

using namespace std;

int main()
{
    // 1) Obtenez trois valeurs entières de l'utilisatrice ou de l'utilisateur, 
    // qui correspondent à l'année, au mois et au jour. 
    // Vous devez utiliser un type énuméré appelé Mois pour représenter le mois.
    
    enum class Mois {
        janvier=1,
        fevrier=2,
        mars=3,
        avril=4,
        mai=5,
        juin=6,
        juillet=7,
        aout=8,
        septembre=9,
        octobre=10,
        novembe=11,
        decembre=12
    };
    int jour,_mois,annee;
    cout<<"donnez le jour le mois et l'annee\n";
    cin>>jour >>_mois >>annee;
    
    Mois mois;
    mois=(Mois)_mois;
    
    bool anneeBissextile;
    if (annee%400==0) {
        anneeBissextile=1;
    }
    else if (annee%4==0){
        if(annee%100==0)
            anneeBissextile=0;
        else
            anneeBissextile=1;
    }
    else
        anneeBissextile=0;
    
    int resultat=annee%100;
    resultat=resultat/4;
    resultat=resultat+jour;
    
    switch (mois) {
        case Mois::janvier:
            resultat=resultat+1;
            break;
        case Mois::fevrier:
            resultat=resultat+4;
            break;
        case Mois::mars:
            resultat=resultat+4;
            break;
        case Mois::avril:
            resultat=resultat+0;
            break;
        case Mois::mai:
            resultat=resultat+2;
            break;
        case Mois::juin:
            resultat=resultat+5;
            break;
        case Mois::juillet:
            resultat=resultat+0;
            break;
        case Mois::aout:
            resultat=resultat+3;
            break;
        case Mois::septembre:
            resultat=resultat+6;
            break;
        case Mois::octobre:
            resultat=resultat+1;
            break;
        case Mois::novembe:
            resultat=resultat+4;
            break;
        case Mois::decembre:
            resultat=resultat+6;
            break;
        default:
            resultat=0;
            break;
    }
    
    if ((mois==Mois::janvier||mois==Mois::fevrier)&&(anneeBissextile==true)) {
        resultat-=1;
    }
    switch (annee/100) {
        case 18:
            resultat+=2;
            break;
        case 19:
            resultat+=0;
            break;
        case 20:
            resultat+=6;
            break;
        default:
            resultat=resultat;
            break;
    }
    
    resultat=resultat+(annee%100);
    resultat=resultat%7;
    if(resultat==0)
        resultat=7;
    
    switch (resultat) {
        case 1:
            cout<<"dimanche";
            break;
        case 2:
            cout<<"lundi";
            break;
        case 3:
            cout<<"mardi";
            break;
        case 4:
            cout<<"mercredi";
            break;
        case 5:
            cout<<"jeudi";
            break;
        case 6:
            cout<<"vendredi";
            break;
        case 7:
            cout<<"samedi";
            break;
        default:
            cout<<" ";
            break;
    }
    
    
    return 0;
}

