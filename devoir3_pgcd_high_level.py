
# DEVOIR-3 IFT1215 QUESTION-a 
# MEMBRES DU GROUPE : 
# NAROMBA CONDE
# ARTHUR LEWIS W.PAUL
# JEFFSON SAINT-LOUIS
# DATE :20 NOV 2023

# DESCRIPTON :
# Ce programme prend deux nombres en entrée et calcule leur plus grand commun 
# diviseur (PGCD).

# Saisie de deux nombres par l'utilisateur
x = int(input("Donnez un nombre"))
y = int(input("Donnez un nombre"))

# On verifie le cas ou l'une des deux entrées est 0
if x == 0:
    print("Le plus grand commun diviseur est :",y)
    
elif y == 0:
    print("Le plus grand commun diviseur est :",x)
else:
    
 # Algorithme d'Euclide pour calculer le PGCD
    while not (x == y):  # Tant que x et y sont differents
        if x > y:
            x = x - y    # Soustraction du plus petit nombre du plus grand
            
        else:
            y = y - x    # Soustraction du plus petit nombre du plus grand

# Affichage du résultat
    print("Le plus grand commun diviseur est :",x)