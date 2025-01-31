#Auteur : Naromba Conde
#Date : 28 Septembre 2023
#Description : Ce programme calcule la valeur décimale de π en utilisant la 
               #formule donnée dans l'énoncé.
#Note : dans ce  programme , je reutiliserai le code donné comme solution à 
#l'exercice 8 de la demntration de cette semaine 

decimales = 60             #nombre de decimales souhaitees
termesCalcules = 1         #nombre de termes deja calcule                      
n = 0                      #Compteur 
numerateur= 0              #Numerateur initialisé à zero
denominateur=1             #Denominateur initialisé à un
encpre = ''                #Econdage de la valeur de pi  

while True :

    k = 2  * n + 1
    newNumerateur = 4 * ((2**k) + 3**k)*((-1) **(n))    #Nouveau numerateur 
    newDenominateur = k * 6**k                          #Nouveau denominateur

    
    #Utilisation du code fourni par le professeur!---------------------------- 
    #Extrait de la demo 4 Exo 8, IFT1015-A
    index = -decimales
    x =  10 ** decimales*numerateur // denominateur 
    enc = ''
    
    while x > 0 or index <= 0 :
        if index == 0:
            enc = '.' + enc
        enc = str(x % 10) + enc
        x = x // 10
        index += 1
    
    # Fin d'utilisation du code fourni par le professeur----------------------
    
  #Saut de 10 termes  
    if termesCalcules % 10 == 0 :
        print(termesCalcules, "termes:", enc + "...")

   # Vérification de la précision souhaitée  
    if enc == encpre:
        print(decimales, "décimales de précision obtenues après",
              termesCalcules, "termes")
        print("pi =", enc, "....")
        break

    #Incrémentation des variables après itération
    encpre = enc
    numerateur = numerateur * newDenominateur +  newNumerateur * denominateur
    denominateur = denominateur * newDenominateur

    termesCalcules += 1
    n += 1