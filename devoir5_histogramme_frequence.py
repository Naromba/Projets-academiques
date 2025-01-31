def lettres(texte) :
    resultat = ''
    for caractere in texte:
        caractere = caractere.lower()
        if caractere.isalpha():
            if caractere in ['à', 'á', 'â', 'ä']:
                caractere = 'a'
                
            elif caractere in ['è', 'é', 'ê', 'ë']:
                caractere = 'e'
                
            elif caractere in ['ì', 'í', 'î', 'ï']:
                caractere = 'i'
                
            elif caractere in ['ò', 'ó', 'ô', 'ö']:
                caractere = 'o'
                
            elif caractere in ['ù', 'ú', 'û', 'ü']:
                caractere = 'u'
                
            elif caractere in [ 'ç']:
                caractere = 'c'
                
            resultat += caractere
            
        if caractere in [ '&']:
            caractere = 'et'
            resultat += caractere
 
    return resultat 


def testLettres():
    assert lettres("J'ai hâte à Noël!") == "jaihateanoel"
    assert lettres("je PrÉfÈre Lire des Livres & Apprendre.") == \
                    "jeprefereliredeslivresetapprendre"

def occurrences(texte) :
    tab = [0]*26
    
    for caractere in texte:
        position = ord(caractere) - ord('a') 
        tab[position] += 1
        
    return tab

def testOccurrences():
    assert occurrences("jeprefereliredeslivresetapprendre") == [1, 0, 0, 2,\
                        10, 1, 0, 0, 2, 1, 0, 2, 0, 1, 0, 3, 0, 6, 2, 1, 0, \
                                                               1, 0, 0, 0, 0]

def frequences(texte, norme) :
    occurrence = occurrences(lettres(texte))
    lettreMaxOccurence = chr(occurrence.index(max(occurrence)) + ord('a'))
    maxOccurrence = max(occurrence)
    tabFrequences = []
    
    for i in occurrence:
        freq = round((i / maxOccurrence) * norme)
        tabFrequences.append(freq)

    return tabFrequences

def testfrequences():
    assert frequences("Aâa! bb'BB cç Ddéd eúe Ffff", 2) == [2, 2, 1, 2,\
                       2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,\
                                                            0, 0, 0, 0, 0]

def histogramme(texte, norme) :
    frequenceResultat = frequences(texte,norme)
    nouveauHistogramme = ""
    for i in range(len(frequenceResultat)):
        lettre = chr(ord('a') + i)
        frequence = frequenceResultat[i]
        
        ligne = lettre + " | " + "#" * frequence
        nouveauHistogramme += ligne + "\n"
        
    return nouveauHistogramme
    

codeboot_org = 'https://raw.githubusercontent.com/codeboot-org/'
path = codeboot_org + 'data/main/litterature/rousseau.txt'
texte = read_file(path)

print(histogramme(texte, 79))
#testLettres()
#testOccurrences()
#testfrequences()