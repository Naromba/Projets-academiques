# Nom: Naromba Condé

# Matricule:20251772

# Date: 5 Octobre 2023

# Description générale du programme :

# Ce programme convertit un nombre entier non-négatif en tonalités du code morse

#L'utilisateur est invité à entrer un nombre, puis,le programme joue

# l'encodage morse de ce nombre.
# Procédure pour jouer les tonalités correspondant au code morse d'un chiffre

#Cette Procédure utilise trois paramètres: un entier positif, la durée 

# d'un "ti" (en seconde) et  la frécence (en Hz) 

def morse (nombreAEncoder,dureeTi,frequence):
    
    if nombreAEncoder == 0:
        chiffre(0, dureeTi, frequence)
        silence(3 * dureeTi)
        
    else:
        diviseur = 1
        
        # Trouver le diviseur pour extraire les chiffres du nombre
        
        while diviseur <= nombreAEncoder:
            diviseur *= 10
        diviseur //= 10
        
        # Extraire les chiffres du nombre un par un
        
        while diviseur > 0:
            K = nombreAEncoder // diviseur
            chiffre(K, dureeTi, frequence)
            silence(3 * dureeTi)
            nombreAEncoder%= diviseur
            diviseur //= 10
            
# Procédure pour jouer le code morse d'un chiffre entre 0 et 9  

# Cette Procédure utilise trois paramètres: un entier positif, la durée 

# d'un "ti" (en seconde) et  la frécence (en Hz) 

def chiffre (chiffreAEncoder, dureeTi,frequence):
    
    if chiffreAEncoder == 0:
        générerSéquenceSonore(5, False,dureeTi,frequence)
        
    elif chiffreAEncoder == 1:
        générerSéquenceSonore(1, True,dureeTi,frequence)
        
    elif chiffreAEncoder == 2:
        générerSéquenceSonore(2, True,dureeTi,frequence)
        
    elif chiffreAEncoder == 3:
        générerSéquenceSonore(3, True,dureeTi,frequence)
        
    elif chiffreAEncoder == 4:
        générerSéquenceSonore(4, True,dureeTi,frequence)
        
    elif chiffreAEncoder == 5:
        générerSéquenceSonore(5, True,dureeTi,frequence)
        
    elif chiffreAEncoder == 6:
        générerSéquenceSonore(1, False,dureeTi,frequence)
        
    elif chiffreAEncoder == 7:
        générerSéquenceSonore(2, False,dureeTi,frequence)
        
    elif chiffreAEncoder == 8:
        générerSéquenceSonore(3, False,dureeTi,frequence)
        
    elif chiffreAEncoder == 9:
        générerSéquenceSonore(4, False,dureeTi,frequence)
        
# Procédure pour jouer une tonalité "ti" ou "taah"

# Cette Procédure utilise trois paramètres: un boléen (ti ou taah), la durée 

# d'un "ti" (en seconde) et  la frécence (en Hz) 

def tonalite (ti,dureeTi, frequence):
    
    # Jouer une tonalité "ti"
    
    if ti:
        beep (dureeTi,frequence)         
        
    # Jouer une tonalité "taah"
    else :
        beep (3*dureeTi, frequence)      
        
# Procédure pour générer un silence d'une certaine durée

#Cette Procédure utilise un  paramètre: nombre indiquant la durée du silence 

#à générer.Permet de générer un silence en utilisant une fréquence.

def silence (dureeSilence):
    
    beep (dureeSilence,0) 
    
    
# La Procédure "générerSéquenceSonore" prend plusieurs paramètres, 

# notamment le nombre d'itérations, une valeur booléenne "ti", une durée et une 

# fréquence. Elle génère une séquence de tonalités et de silences.   

def générerSéquenceSonore(n, ti,dureeTi,frequence):
    
    max = 5
    
    for i in range(n):
        tonalite (ti,dureeTi,frequence)
        if (i < max - 1):
            silence(dureeTi)
            
    for i in range(n, max):
        tonalite (not ti,dureeTi,frequence)
        if (i < max - 1):
            silence(dureeTi)
    
  # Utilisation du code du professeur-----------------------------------------  
def main():

    duree_ti  = 0.1    # durée d'un "ti" en secondes
    frequence = 1000   # fréquence de la tonalité en Hz

    msg = ''

    while True:
        texte_lu = prompt(msg + 'Entrez un nombre à convertir en morse')
        if texte_lu == None or texte_lu == '':
            break
        a_convertir = int(texte_lu)
        if a_convertir >= 0:
            morse(a_convertir, duree_ti, frequence)
            silence(7*duree_ti)  # silence entre chaque "mot"
            msg = ''
        else:
            beep(0.2, 100)  # signaler une erreur avec une alarme sonore
            msg = 'Un nombre >= 0 SVP.  '
            

main()

 # Utilisation du code du professeur-----------------------------------------
 
