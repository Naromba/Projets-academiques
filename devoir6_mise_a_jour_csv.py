# Nom: Naromba Conde
# Matricule: 20251772
# Date: 18 Novembre 2023

# Description générale du programme:
# Ce programme met à jour automatiquement un fichier CSV en effectuant des 
# calculs de sommes et de moyennes sur les colonnes de la grille du fichier.

import functools

def calculer_somme(colonne):
    valeur = list(map(lambda x: int(x) if x.isdecimal() else 0, colonne))
    somme = functools.reduce(lambda x,y: x+y, valeur,0)
    return str(somme)



def calculer_moyenne(colonne):
    valeur = list(map(lambda x: int(x) if x.isdecimal() else 0, colonne))
    valeur = list(filter(lambda x: x != 0, valeur))
    somme = int(calculer_somme(map(str,valeur)))
    moyenne = somme / len(valeur) if len(valeur) > 0 else 0
    return str(moyenne)

def traiter_colonne(colonne):
    somme_op = 'somme='
    moy_op = 'moy='
    if colonne and colonne[0].lower() == somme_op.lower():
        colonne[0] = somme_op + calculer_somme(colonne[1:])
    elif colonne and colonne[0].lower() == moy_op.lower():
        colonne[0] = moy_op + calculer_moyenne(colonne[1:])
    return colonne

def ecrire_csv(path, matrice):
    write_file(path, '\n'.join(map(lambda rangee: ','.join(rangee), matrice)))
    
# Procédure mise_a_jour:
# Traite chaque colonne de la matrice et remplace les textes spécifiés par le calcul de la somme
# ou de la moyenne des valeurs dans cette colonne. Enregistre ensuite la matrice mise à jour dans le fichier CSV.

def mise_a_jour(path, matrice):

    bloc1 = list(map(lambda ligne: ligne[0],matrice))
    bloc2 = list(map(lambda ligne: ligne[1],matrice))
    rep_bloc1 = traiter_colonne(bloc1)
    rep_bloc2 = traiter_colonne(bloc2)
    matrice[0][0] = rep_bloc1[0]
    matrice[0][1] = rep_bloc2[0]
  

    # Écrire la matrice mise à jour dans le fichier CSV
    ecrire_csv(path, matrice)

    

#--- Tests unitaires
'''
# Fonction pour tester calculer_somme(colonne):
def test_calculer_somme():
    assert calculer_somme(['a','2','b']) == '2'
    assert calculer_somme(['0']) == '0'
    assert calculer_somme(['a','c','b']) == '0'
    
# Fonction pour tester calculer_moyenne(colonne)   
def test_calculer_moyenne():
    assert calculer_moyenne(['a','c','b']) == '0'
    assert calculer_moyenne(['a','2','b']) == '2.0'
    assert calculer_moyenne(['']) == '0'    

# Fonction pour tester traiter_colone(colonne,somme_op,moy_op)
#POURQUOI IL NE FONCTIONNE PAAAAAASSSS!!!!!!!??????
def test_traiter_colonne(): 
    traiter_colonne(["moy=",'']) == ['moy=0.0', '']
    traiter_colonne(["SOmme=",'b','1']) == ['somme=1', 'b', '1']
    traiter_colonne(["MOy=",'a','b']) == ['moy=0.0', 'a', 'b']   
   
'''

#----------UTILISATION DU CODE FOURNI DANS L'ENNONCE DU DEVOIR-------------
# *** Ne rien modifier en dessous de cette ligne ***

# La fonction obtenir_lignes prend le paramètre 'contenu', un texte
# correspondant au contenu d'un fichier. Elle retourne un tableau des
# lignes du fichier.

def obtenir_lignes(contenu):
    lignes = contenu.split('\r\n')
    if len(lignes) == 1:
        lignes = contenu.split('\r')
        if len(lignes) == 1:
            lignes = contenu.split('\n')
    if lignes[-1] == '':
        lignes.pop()
    return lignes

# La fonction lire_csv prend le paramètre 'path', un texte
# représentant un chemin d'accès d'un fichier CSV. Elle lit le fichier
# et retourne une matrice de textes (les cellules de la grille).

def lire_csv(path):
    return list(map(lambda ligne: ligne.split(','),
                    obtenir_lignes(read_file(path))))

# La procédure ecrire_csv prend un paramètre 'path', un texte représentant
# un chemin d'accès d'un fichier CSV et un paramètre 'matrice', une matrice
# de textes. Elle écrit le contenu de 'matrice' dans le fichier CSV.

def ecrire_csv(path, matrice):
    write_file(path, '\n'.join(map(lambda rangee: ','.join(rangee), matrice)))

# Préfixes pour chaque opération

somme_op = 'somme='
moy_op   = 'moy='

# La procédure chiffrier prend un paramètre 'path', un texte représentant
# un chemin d'accès d'un fichier CSV. Elle met à jour périodiquement
# le fichier situé au chemin 'path' à l'aide de la procédure 'mise_a_jour'
# lorsque le contenu du fichier change (par exemple lorsque l'utilisateur
# change une case de la grille de tableur dans codeBoot).

def chiffrier(path):
    show_file(path)
    matrice = lire_csv(path)
    mise_a_jour(path, matrice)
    while True:
        sleep(0.1)
        nouvelle_matrice = lire_csv(path)
        if nouvelle_matrice != matrice:
            mise_a_jour(path, nouvelle_matrice)
            matrice = lire_csv(path)

chiffrier('data.csv')

#----------FIN DU CODE FOURNI DANS L'ENNONCE DU DEVOIR---------------------

'''
#Appels des tests unitaires
test_calculer_somme()
test_calculer_moyenne()
test_traiter_colonne()
'''









    