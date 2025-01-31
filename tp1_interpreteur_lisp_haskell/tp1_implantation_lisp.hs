 --Membres du groupe :
	--Naromba Condé -   20251772
    --Qiyun Ou      - 	20264284
-- Date : 15-10-2024
-- Description : Ce fichier contient l'implémentation d'un 
--                 interpréteur pour un langage Lisp simplifié.
-- TP-1  --- Implantation d'une sorte de Lisp          -*- coding: utf-8 -*-
{-# OPTIONS_GHC -Wall #-}
--
-- Ce fichier défini les fonctionalités suivantes:
-- - Analyseur lexical
-- - Analyseur syntaxique
-- - Pretty printer
-- - Implantation du langage

---------------------------------------------------------------------------
-- Importations de librairies et définitions de fonctions auxiliaires    --
---------------------------------------------------------------------------

import Text.ParserCombinators.Parsec -- Bibliothèque d'analyse syntaxique.
import Data.Char                -- Conversion de Chars de/vers Int et autres.
import System.IO                -- Pour stdout, hPutStr

---------------------------------------------------------------------------
-- La représentation interne des expressions de notre language           --
---------------------------------------------------------------------------
data Sexp = Snil                        -- La liste vide
          | Ssym String                 -- Un symbole
          | Snum Int                    -- Un entier
          | Snode Sexp [Sexp]           -- Une liste non vide
          -- Génère automatiquement un pretty-printer et une fonction de
          -- comparaison structurelle.
          deriving (Show, Eq)

-- Exemples:
-- (+ 2 3) ==> Snode (Ssym "+")
--                   [Snum 2, Snum 3]
--
-- (/ (* (- 68 32) 5) 9)
--     ==>
-- Snode (Ssym "/")
--       [Snode (Ssym "*")
--              [Snode (Ssym "-")
--                     [Snum 68, Snum 32],
--               Snum 5],
--        Snum 9]

---------------------------------------------------------------------------
-- Analyseur lexical                                                     --
---------------------------------------------------------------------------

pChar :: Char -> Parser ()
pChar c = do { _ <- char c; return () }

-- Les commentaires commencent par un point-virgule et se terminent
-- à la fin de la ligne.
pComment :: Parser ()
pComment = do { pChar ';'; _ <- many (satisfy (\c -> not (c == '\n')));
                (pChar '\n' <|> eof); return ()
              }
-- N'importe quelle combinaison d'espaces et de commentaires est considérée
-- comme du blanc.
pSpaces :: Parser ()
pSpaces = do { _ <- many (do { _ <- space ; return () } <|> pComment);
               return () }

-- Un nombre entier est composé de chiffres.
integer     :: Parser Int
integer = do c <- digit
             integer' (digitToInt c)
          <|> do _ <- satisfy (\c -> (c == '-'))
                 n <- integer
                 return (- n)
    where integer' :: Int -> Parser Int
          integer' n = do c <- digit
                          integer' (10 * n + (digitToInt c))
                       <|> return n

-- Les symboles sont constitués de caractères alphanumériques et de signes
-- de ponctuations.
pSymchar :: Parser Char
pSymchar    = alphaNum <|> satisfy (\c -> c `elem` "!@$%^&*_+-=:|/?<>")
pSymbol :: Parser Sexp
pSymbol= do { s <- many1 (pSymchar);
              return (case parse integer "" s of
                        Right n -> Snum n
                        _ -> Ssym s)
            }

---------------------------------------------------------------------------
-- Analyseur syntaxique                                                  --
---------------------------------------------------------------------------

-- La notation "'E" est équivalente à "(quote E)"
pQuote :: Parser Sexp
pQuote = do { pChar '\''; pSpaces; e <- pSexp;
              return (Snode (Ssym "quote") [e]) }

-- Une liste est de la forme:  ( {e} [. e] )
pList :: Parser Sexp
pList  = do { pChar '('; pSpaces;
              ses <- pTail;
                    return (case ses of [] -> Snil
                                        se : ses' -> Snode se ses')
            }
pTail :: Parser [Sexp]
pTail  = do { pChar ')'; return [] }
     -- <|> do { pChar '.'; pSpaces; e <- pSexp; pSpaces;
     --          pChar ')' <|> error ("Missing ')' after: " ++ show e);
     --          return e }
     <|> do { e <- pSexp; pSpaces; es <- pTail; return (e : es) }

-- Accepte n'importe quel caractère: utilisé en cas d'erreur.
pAny :: Parser (Maybe Char)
pAny = do { c <- anyChar ; return (Just c) } <|> return Nothing

-- Une Sexp peut-être une liste, un symbol ou un entier.
pSexpTop :: Parser Sexp
pSexpTop = do { pSpaces;
                pList <|> pQuote <|> pSymbol
                <|> do { x <- pAny;
                         case x of
                           Nothing -> pzero
                           Just c -> error ("Unexpected char '" ++ [c] ++ "'")
                       }
              }

-- On distingue l'analyse syntaxique d'une Sexp principale de celle d'une
-- sous-Sexp: si l'analyse d'une sous-Sexp échoue à EOF, c'est une erreur de
-- syntaxe alors que si l'analyse de la Sexp principale échoue cela peut être
-- tout à fait normal.
pSexp :: Parser Sexp
pSexp = pSexpTop <|> error "Unexpected end of stream"

-- Une séquence de Sexps.
pSexps :: Parser [Sexp]
pSexps = do pSpaces
            many (do e <- pSexpTop
                     pSpaces
                     return e)

-- Déclare que notre analyseur syntaxique peut-être utilisé pour la fonction
-- générique "read".
instance Read Sexp where
    readsPrec _p s = case parse pSexp "" s of
                      Left _ -> []
                      Right e -> [(e,"")]

---------------------------------------------------------------------------
-- Sexp Pretty Printer                                                   --
---------------------------------------------------------------------------

showSexp' :: Sexp -> ShowS
showSexp' Snil = showString "()"
showSexp' (Snum n) = showsPrec 0 n
showSexp' (Ssym s) = showString s
showSexp' (Snode h t) =
    let showTail [] = showChar ')'
        showTail (e : es) =
            showChar ' ' . showSexp' e . showTail es
    in showChar '(' . showSexp' h . showTail t

-- On peut utiliser notre pretty-printer pour la fonction générique "show"
-- (utilisée par la boucle interactive de GHCi).  Mais avant de faire cela,
-- il faut enlever le "deriving Show" dans la déclaration de Sexp.
{-
instance Show Sexp where
    showsPrec p = showSexp'
-}

-- Pour lire et imprimer des Sexp plus facilement dans la boucle interactive
-- de Hugs/GHCi:
readSexp :: String -> Sexp
readSexp = read
showSexp :: Sexp -> String
showSexp e = showSexp' e ""

---------------------------------------------------------------------------
-- Représentation intermédiaire Lexp                                     --
---------------------------------------------------------------------------

type Var = String

data Lexp = Lnum Int             -- Constante entière.
          | Lbool Bool           -- Constante Booléenne.
          | Lvar Var             -- Référence à une variable.
          | Ltest Lexp Lexp Lexp -- Expression conditionelle.
          | Lfob [Var] Lexp      -- Construction de fobjet.
          | Lsend Lexp [Lexp]    -- Appel de fobjet.
          | Llet Var Lexp Lexp   -- Déclaration non-récursive.
          -- Déclaration d'une liste de variables qui peuvent être
          -- mutuellement récursives.
          | Lfix [(Var, Lexp)] Lexp
          deriving (Show, Eq)

-- Fonction principale de conversion de Sexp en Lexp
s2l :: Sexp -> Lexp
s2l (Snum n) = Lnum n
s2l (Ssym s) = Lvar s
-- Convertit les symboles "True" et "False" en valeurs booléennes
s2l (Ssym "True") = Lbool True
s2l (Ssym "False") = Lbool False

-- Conversion d'un objet fonctionnel (fob) en Lexp
s2l (Snode (Ssym "fob") [paramsSexp, body]) =
    let params = sexpToList paramsSexp
        -- Extraction des variables de paramètres
        paramVars = [v | Ssym v <- params]
    in if null paramVars 
       then error "Les paramètres de fob sont vides"
       else Lfob paramVars (s2l body)
s2l (Snode (Ssym "fob") _) = error "Format incorrect pour fob"

-- Conversion d'une expression fix pour les déclarations récursives
s2l (Snode (Ssym "fix") [declsSexp, body]) =
    let declsList = sexpToList declsSexp
    in Lfix (map parseDecl declsList) (s2l body)

-- Expressions conditionnels
s2l (Snode (Ssym "if") [test, consequence, alternative ]) = 
    Ltest (s2l test) (s2l consequence) (s2l alternative)
s2l (Snode (Ssym "if") _) = error "Format incorrect pour if"

--Expression let
s2l (Snode (Ssym "let") [Snode (Ssym v) [valExp], body]) = 
    Llet v (s2l valExp) (s2l body)
s2l (Snode (Ssym "let") _) = error "Format incorrect pour let"

--Gère les appels de  fonctions et objets
s2l (Snode expr args) = Lsend (s2l expr) (map s2l args)

-- Gérer les autres cas non spécifiés
s2l se = error ("Expression Psil inconnue: " ++ showSexp se)

-- Analyse une déclaration en une paire (Var, Lexp)
parseDecl :: Sexp -> (Var, Lexp)
-- Déclaration de variable : (x e)
parseDecl (Snode (Ssym x) [e]) = (x, s2l e)
-- Déclaration de fonction : ((f x1 ... xn) e)
parseDecl (Snode (Snode (Ssym f) params) [e]) =
    (f, Lfob (parseParams params) (s2l e))
parseDecl _ = error "Déclaration invalide dans l'expression 'fix'"


-- Convertit une liste de Sexp en une liste de variables
parseParams :: [Sexp] -> [Var]
parseParams params = map fromSsym params

-- Convertit un Sexp représentant une liste en une liste de Sexp
sexpToList :: Sexp -> [Sexp]
sexpToList Snil = []
sexpToList (Snode h t) = h : t
-- Traite un élément unique comme une liste à un seul élément
sexpToList s = [s] 


-- Extrait un nom de variable d'un Sexp de type Ssym
fromSsym :: Sexp -> Var
fromSsym (Ssym s) = s
fromSsym _ = error "Un symbole était attendu dans la liste des paramètres"


---------------------------------------------------------------------------
-- Représentation du contexte d'exécution                                --
---------------------------------------------------------------------------

-- Type des valeurs manipulées à l'exécution.
data Value = Vnum Int
           | Vbool Bool
           | Vbuiltin ([Value] -> Value)
           | Vfob VEnv [Var] Lexp

instance Show Value where
    showsPrec p (Vnum n) = showsPrec p n
    showsPrec p (Vbool b) = showsPrec p b
    showsPrec _ (Vbuiltin _) = showString "<primitive>"
    showsPrec _ (Vfob _ _ _) = showString "<fobjet>"

type VEnv = [(Var, Value)]

-- L'environnement initial qui contient les fonctions prédéfinies.
env0 :: VEnv
env0 = let binop f op =
              Vbuiltin (\vs -> case vs of
                         [Vnum n1, Vnum n2] -> f (n1 `op` n2)
                         [_, _] -> error "Pas un nombre"
                         _ -> error "Nombre d'arguments incorrect")

          in [("+", binop Vnum (+)),
              ("*", binop Vnum (*)),
              ("/", binop Vnum div),
              ("-", binop Vnum (-)),
              ("<", binop Vbool (<)),
              (">", binop Vbool (>)),
              ("≤", binop Vbool (<=)),
              ("≥", binop Vbool (>=)),
              ("=", binop Vbool (==)),
              ("true",  Vbool True),
              ("false", Vbool False)]

---------------------------------------------------------------------------
-- Évaluateur                                                            --
---------------------------------------------------------------------------

-- Fonction principale d'évaluation qui prend un environnement et une expression.
eval :: VEnv -> Lexp -> Value
-- Cas de base : constantes numériques
eval _ (Lnum n) = Vnum n
-- Cas de base : constantes booléennes
eval _ (Lbool b) = Vbool b

--Evaluateur pour les variables
eval env (Lvar v ) = case lookup v env of 
                        Just val -> val
                        Nothing -> error ( v ++ " est non definie")

-- Evaluateur pour les expressions conditionnelles
eval env (Ltest test consequence alternative) =
    case eval env test of 
        Vbool True -> eval env consequence
        Vbool False -> eval env alternative
        _ -> error " Il n'y a pas de condition booléenne dans le if"

-- Évaluateur pour l'expression let
eval env (Llet v valExp body) = 
    let val = eval env valExp
    in eval ((v, val) : env) body

-- Évaluateur pour les déclarations récursives avec 'fix'
eval env (Lfix decls body) =
    let recEnv = [(x, makeValue recEnv e) | (x, e) <- decls] ++ env
    -- Fonction auxiliaire pour créer des valeurs dans l'environnement récursif
        makeValue recEnv (Lfob vars e) = Vfob recEnv vars e
        makeValue recEnv e = eval recEnv e
    in eval recEnv body

-- Évaluateur pour gérer les objets fonctionnels fob
eval env (Lfob  vars body) = 
    Vfob env vars body

-- Évaluateur pour gérer les appels de fonctions et objets
eval env (Lsend func args) = 
    case eval env func of
    -- Appel d'une fonction prédéfinie
        Vbuiltin f -> f (map (eval env) args)
        Vfob fEnv vars body ->
     -- Création d'un nouvel environnement avec les arguments
            let newEnv = zip vars (map (eval env) args) ++ fEnv
     -- Évaluation du corps de l'objet fonctionnel avec le nouvel environnement
            in eval newEnv body
        _ -> error "Appel non valide"
               
---------------------------------------------------------------------------
-- Toplevel                                                              --
---------------------------------------------------------------------------

evalSexp :: Sexp -> Value
evalSexp = eval env0 . s2l

-- Lit un fichier contenant plusieurs Sexps, les évalues l'une après
-- l'autre, et renvoie la liste des valeurs obtenues.
run :: FilePath -> IO ()
run filename =
    do inputHandle <- openFile filename ReadMode 
       hSetEncoding inputHandle utf8
       s <- hGetContents inputHandle
       (hPutStr stdout . show)
           (let sexps s' = case parse pSexps filename s' of
                             Left _ -> [Ssym "#<parse-error>"]
                             Right es -> es
            in map evalSexp (sexps s))
       hClose inputHandle

sexpOf :: String -> Sexp
sexpOf = read

lexpOf :: String -> Lexp
lexpOf = s2l . sexpOf

valOf :: String -> Value
valOf = evalSexp . sexpOf