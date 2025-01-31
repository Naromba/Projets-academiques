# Marc Olivier Jean Paul : 20241763
# Abdelghafour Rahmouni : 20246224
# Aymane Benameur : 20143047
# Mercredi 22 Novembre 2023
# Ce programme utilise l'agorithme d'Euclide 
# pour trouver le PGCD entre deux nombre.

x = int(input("Entrez un premier nombre: "))
y = int(input("Entrez un deuxième nombre: "))

if x == 0:                          # Si la valeur de x est nulle
    print("Le PGCD est : ", y)      # alors le PGCD est y
    
elif y == 0:                        # Si la valeur de y est nulle
    print("Le PGCD est : ", x)      # alors le PGCD est x
    
else :    
    while x != y:                   # Tant que x n'est pas égal à y
        if x > y:                   # Si x est plus grand que y
            x = x - y               # alors on soustrait y à x
        else:                       # Sinon
            y = y - x               # on soustrait x à y
            
print("Le PGCD est : ", x)      # On affiche le PGCD