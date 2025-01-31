#!/bin/bash
# Question 3 devoir4
# Membres du groupe:
# Naromba Conde
# Arthur W.Paul Levis
# Jeffson Saint-Louis
# Date : 30 Novembre 2023
# Description genereale du programme :
# ce programme fait la sauvegarde automatique d'un répertoire spécifié en un 
# fichier .zip daté.
# L'utilisateur saisit le chemin du répertoire à sauvegarder. Le script vérifie
# l'existence du répertoire, crée un fichier .zip daté, et affiche le résultat.

# Demander à l'utilisateur le répertoire à sauvegarder
echo "Entrez le chemin du répertoire à sauvegarder :"
read repertoire

# Vérifier l'existence du répertoire
if [ ! -d "$repertoire" ]; then
    echo "Le répertoire spécifié n'existe pas. Veuillez vérifier le chemin."
    exit 
fi

# Créer un nom de fichier pour la sauvegarde
dateActuelle=$(date +"%y-%m-%d")
nomFichier="sauvegarde_$dateActuelle.zip"

# Créer une sauvegarde compressée
if zip -r "$nomFichier" "$repertoire"; then
    echo "La sauvegarde a été créée avec succès. Fichier : $nomFichier"
else
    echo "Erreur lors de la création de la sauvegarde."
fi  

