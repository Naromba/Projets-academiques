#!/bin/bash
# Question 3 devoir4
# Membres du groupe:
# Naromba Conde
# Arthur W.Paul Levis
# Jeffson Saint-Louis
# Date : 30 Novembre 2023
# Description genereale du programme :
# ce programme convertit un fichier csv en un fichier txt
# en remplaçant les virgules par des tabulations. Le nouveau fichier est 
# copier dans le fichier txt.

# Vérification des arguments en ligne de commande
if [ "$#" -ne 2 ]; then
    echo "Erreur : Ce script nécessite deux arguments."
    exit 
fi

fichierCSV="$1"                    
fichierTXT="$2"

# Validation de l'existence du fichier CSV 
if [ -f "$fichierCSV" ]; then
    if [ ! "$fichierCSV" == *.csv ]; then
        echo "Ce fichier n'est pas un fichier CSV"
        exit
    else 
        # Conversion CSV en TXT
        tr ',' '\t' < "$fichierCSV" > "$fichierTXT"
	
	# Affichage d'un message de réussite
	echo "Conversion réussie. Le fichier '$fichierCSV' a été converti en '$fichierTXT'"
    fi
else
    echo "Ce fichier n'existe pas"
fi
