#!/bin/bash
# Question 2 devoir4
# Membres du groupe:
# Naromba Conde
# Arthur W.Paul Levis
# Jeffson Saint-Louis
# Date : 30 Novembre 2023
# Description genereale du programme:
# Ce script Bash permet à l'utilisateur de rechercher un mot-clé spécifié
# dans un fichier donné. Il vérifie d'abord la présence d'un fichier en
# argument et son existence. Ensuite, il invite l'utilisateur à saisir un
# mot-clé, recherche ce mot-clé dans le fichier et affiche les lignes
# correspondantes avec leur numéro. Si aucune occurrence n'est trouvée,
# un message approprié est affiché.


# Vérifier si le fichier est spécifié en argument
if [ "$#" -ne 1 ];  then
    echo "Il n'y a aucun fichier passé en argument"
    exit 1
fi

fichier="$1"

# Vérifier si le fichier existe
if [ ! -f "$fichier" ]; then
    echo "Ce fichier n'existe pas"
    exit 1
fi


# Demander a # Demander à l'utilisateur de saisir un mot-clé
echo "Renter le mot-clef"
read motClef

# Rechercher le mot-clé dans le fichier et afficher les lignes correspondantes
if grep -n "$motClef" "$fichier"; then
    echo "occurences de ' $motClef ' trouvés dans '$fichier'"
else
    echo "Aucune occurrence dans le fichier" '$fichier'
fi
