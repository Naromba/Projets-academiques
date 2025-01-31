#!/bin/bash
# Question 1 devoir4
# Membres du groupe:
# Naromba Conde
# Arthur W.Paul Levis
# Jefsson Saint-Louis
# Date : 30 Novembre 2023
# Description genereale du programme:
# Ce script Bash prend deux fichiers en tant qu'arguments en ligne de commande. 
#Il vérifie d'abord si le bon nombre d'arguments (deux) est fourni. 
#Ensuite, il vérifie l'existence du deuxième fichier. Si le deuxième fichier n'existe pas, le script affiche une erreur. En revanche, s'il existe, le script vérifie l'existence du premier fichier. Si le premier fichier n'existe pas, le script le crée en copiant le contenu du deuxième fichier. Si le premier fichier existe déjà, le script ajoute simplement le contenu du deuxième fichier à la fin du premier fichier. Le script fournit des messages informatifs pour indiquer les actions effectuées, que ce soit la création du premier fichier ou l'ajout du contenu au fichier existant.

# Vérifier le nombre d'arguments en ligne de commande
if [ "$#" -ne 2 ]; then
    echo "Erreur : Ce script nécessite deux fichiers en tant qu'arguments."
    exit 
fi

fichier1="$1"
fichier2="$2"

# Vérifier si le deuxième fichier existe
if [ ! -f "$fichier2" ]; then
    echo "Erreur : Le fichier $fichier2 n'existe pas."
    exit 
fi

# Vérifier si le premier fichier existe
if [ ! -f "$fichier1" ]; then
    # Si le premier fichier n'existe pas, le créer en copiant le contenu du deuxième fichier
    cp "$fichier2" "$fichier1"
    echo "$fichier1 a été créé en copiant le contenu de $fichier2."
else
    # Si le premier fichier existe, ajouter le contenu du deuxième fichier à la fin
    cat "$fichier2" >> "$fichier1"
    echo "Le contenu de $fichier2 a été ajouté à la fin de $fichier1."
fi




