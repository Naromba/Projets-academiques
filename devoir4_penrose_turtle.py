# Auteur : Naromba Conde
# Matricule : 20251772
# Date : 5 Novembre 2023

def losange(angle,cote):
    lt(angle)
    for _ in range(2):
        fd(cote)
        lt(angle)
        fd(cote)
        lt(180 - angle)
        

def depart(angle,cote):
    pu()
    fd(cote)
    lt(angle)
    pd()
    
    
def exterieur(angle,cote):
    rt(angle+18)
    for i in range(1):
        for j in range(3):
            losange(angle, cote)
        lt(angle/2)
        #fd(35)
       # depart(90, 72)
      #  lt(45+98)
        
#exterieur(72, 35)

def penrose(dist):
    goto(0,0)
    lt(90)
    for i in range(5):
        losange(72,dist)
        
    #rt(90)
    
    for _ in range(5):
        depart(0, 57)
        rt(90)
        exterieur(72,dist)
        pu()
        goto(0,0)
        pd()
    ht()

penrose(35)