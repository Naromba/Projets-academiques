 --Membres du groupe :
	--Naromba Condé -   20251772
    --Qiyun Ou      - 	20264284
-- Date : 28-11-2024
-- Description : Ce fichier contient l'implémentation d'un 
--interpréteur pour un langage Lisp simplifié implementer 
--avec une anatation de type.

-- TP-2  --- Implantation d'une sorte de Lisp          -*- coding: utf-8 -*-
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
import Debug.Trace(trace)
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
pSymchar    = alphaNum <|> satisfy (\c -> not (isAscii c)
                                          || c `elem` "!@$%^&*_+-=:|/?<>")
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

-- Déclare que notre analyseur syntaxique peut être utilisé pour la fonction
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

data Type = Terror String        -- Utilisé quand le type n'est pas connu.
          | Tnum                 -- Type des nombres entiers.
          | Tbool                -- Type des booléens.
          | Tfob [Type] Type     -- Type des fobjets.
          deriving (Show, Eq)

data Lexp = Lnum Int             -- Constante entière.
          | Lbool Bool           -- Constante Booléenne.
          | Lvar Var             -- Référence à une variable.
          | Ltype Lexp Type      -- Annotation de type.
          | Ltest Lexp Lexp Lexp -- Expression conditionelle.
          | Lfob [(Var, Type)] Lexp -- Construction de fobjet.
          | Lsend Lexp [Lexp]    -- Appel de fobjet.
          | Llet Var Lexp Lexp   -- Déclaration non-récursive.
          -- Déclaration d'une liste de variables qui peuvent être
          -- mutuellement récursives.
          | Lfix [(Var, Lexp)] Lexp
          deriving (Show, Eq)

---------------------------------------------------------------------------

-- Fonction principale de conversion de Sexp en Lexp
s2l :: Sexp -> Lexp
-- Gestion des constantes entières
s2l (Snum n) = Lnum n

-- Gestion des constantes booléennes
s2l (Ssym "true") = Lbool True
s2l (Ssym "false") = Lbool False

-- Gestion des variables
s2l (Ssym s) = Lvar s

-- Gestion des annotations de type : (: e t)
s2l (Snode (Ssym ":") [e, t]) =
    Ltype (s2l e) (s2type t)
s2l (Snode (Ssym ":") _) =
    error "Annotation de type invalide"

-- Gestion des expressions conditionnelles : (if e1 e2 e3)
s2l (Snode (Ssym "if") [cond, thenBranch, elseBranch]) =
    Ltest (s2l cond) (s2l thenBranch) (s2l elseBranch)
s2l (Snode (Ssym "if") _) =
    error "Format incorrect pour 'if'"

-- Gestion des déclarations locales avec annotation de type : (let (x τ e1) e2)
s2l (Snode (Ssym "let") [Snode (Snode (Ssym x) [t]) [e1], e2]) =
    Llet x (Ltype (s2l e1) (s2type t)) (s2l e2)

-- Gestion des déclarations locales sans annotation de type : (let (x e1) e2)
s2l (Snode (Ssym "let") [Snode (Ssym x) [e1], e2]) =
    Llet x (s2l e1) (s2l e2)

s2l (Snode (Ssym "let") _) =
    error "Format incorrect pour 'let'"

-- Gestion des fonctions objets (fob)
s2l (Snode (Ssym "fob") [params, body]) =
    Lfob (parseParams (sexpToList params)) (s2l body)
s2l (Snode (Ssym "fob") _) =
    error "Format incorrect pour 'fob'"

-- Gestion des déclarations récursives : (fix decls body)
s2l (Snode (Ssym "fix") [declsNode, body]) =
    let decls = map parseDecl (s2list declsNode)
    in Lfix decls (s2l body)
s2l (Snode (Ssym "fix") _) =
    error "Format incorrect pour 'fix'"

-- Gestion des appels de fonctions ou d'objets : (f arg1 ... argn)
s2l (Snode func args) =
    Lsend (s2l func) (map s2l args)

-- Gestion des cas inconnus
s2l se = error ("Expression Sexp inconnue : " ++ showSexp se)

-- Fonction auxiliaire pour convertir une Sexp en Type
s2type :: Sexp -> Type
s2type (Ssym "Num")  = Tnum
s2type (Ssym "Bool") = Tbool
s2type se = error ("Type inconnu : " ++ showSexp se)

-- Fonction pour vérifier si une Sexp représente un type valide
isType :: Sexp -> Bool
isType (Ssym "Num") = True
isType (Ssym "Bool") = True
isType _ = False


-- Fonction pour convertir une liste de Sexp en liste de Lexp
parseDecl :: Sexp -> (Var, Lexp)

-- Cas 1 : Déclaration de fonction avec type de retour via `(: ...)`
parseDecl (Snode (Snode (Ssym fname) params)
          [Snode (Ssym ":") [body, returnType]]) =
    (fname,
     Lfob (parseParams params)
          (Ltype (s2l body)
                 (s2type returnType)))

-- Cas 2 : Déclaration de fonction avec type de retour spécifié séparément
parseDecl (Snode (Snode (Ssym fname) params)
          [returnType, body])
    | isType returnType =
    (fname,
     Lfob (parseParams params)
          (Ltype (s2l body)
                 (s2type returnType)))

-- Cas 3 : Déclaration de fonction sans annotation de type
parseDecl (Snode (Snode (Ssym fname) params)
          [body]) =
    (fname,
     Lfob (parseParams params)
          (s2l body))

-- Cas 4 : Déclaration de variable typée : (x τ e)
parseDecl (Snode (Ssym x)
          [t, e])
    | isType t =
    (x,
     Ltype (s2l e)
           (s2type t))

-- Cas 5 : Déclaration de variable sans type : (x e)
parseDecl (Snode (Ssym x)
          [e]) =
    (x,
     s2l e)

-- Cas par défaut : Déclaration invalide
parseDecl se =
    error ("Déclaration invalide dans 'fix' : "
           ++ showSexp se)
-- Fonction pour analyser un paramètre typé (x τ)
parseParam :: Sexp -> (Var, Type)
parseParam (Snode (Ssym x) [t]) | isType t =
    (x, s2type t)
parseParam se = error ("Argument invalide dans la déclaration : "
                       ++ showSexp se)

-- Fonction pour analyser les paramètres d'une fonction
parseParams :: [Sexp] -> [(Var, Type)]
parseParams = map parseParam

-- Fonction pour convertir une Sexp en liste de Sexp
sexpToList :: Sexp -> [Sexp]
sexpToList Snil = []
sexpToList (Snode h t) = h : t
sexpToList s = [s]

-- Fonction pour convertir une Sexp en liste
s2list :: Sexp -> [Sexp]
s2list Snil = []
s2list (Snode h t) = h : t
s2list se = error ("Pas une liste valide : " ++ showSexp se)

---------------------------------------------------------------------------
-- Représentation du contexte d'exécution                                --
---------------------------------------------------------------------------

-- Type des valeurs manipulées à l'exécution.
data Value = Vnum Int
           | Vbool Bool
           | Vbuiltin ([Value] -> Value)
           | Vfob VEnv Int Dexp -- L'entier indique le nombre d'arguments.

instance Show Value where
    showsPrec p (Vnum n) = showsPrec p n
    showsPrec p (Vbool b) = showsPrec p b
    showsPrec _ (Vbuiltin _) = showString "<primitive>"
    showsPrec _ (Vfob _ _ _) = showString "<fobjet>"

type Env = [(Var, Type, Value)]

-- L'environnement initial qui contient les fonctions prédéfinies.
env0 :: Env
env0 = let binop f op =
              Vbuiltin (\vs -> case vs of
                         [Vnum n1, Vnum n2] -> f (n1 `op` n2)
                         [_, _] -> error "Pas un nombre"
                         _ -> error "Nombre d'arguments incorrect")
           intbin = Tfob [Tnum, Tnum] Tnum
           boolbin = Tfob [Tnum, Tnum] Tbool

       in [("+", intbin,  binop Vnum (+)),
           ("*", intbin,  binop Vnum (*)),
           ("/", intbin,  binop Vnum div),
           ("-", intbin,  binop Vnum (-)),
           ("<", boolbin, binop Vbool (<)),
           (">", boolbin, binop Vbool (>)),
           ("≤", boolbin, binop Vbool (<=)),
           ("≥", boolbin, binop Vbool (>=)),
           ("=", boolbin, binop Vbool (==)),
           ("true",  Tbool, Vbool True),
           ("false", Tbool, Vbool False)]

---------------------------------------------------------------------------
-- Vérification des types                                                --
---------------------------------------------------------------------------

type TEnv = [(Var, Type)]

-- `check c Γ e` renvoie le type de `e` dans l'environnement `Γ`.
-- Si `c` est vrai, on fait une vérification complète, alors que s'il
-- est faux, alors on présume que le code est typé correctement et on
-- se contente de chercher le type.

-- Fonction de vérification des types
check :: Bool -> TEnv -> Lexp -> Type
check _ _ (Lnum _) = Tnum
check _ _ (Lbool _) = Tbool

-- Pour les variables
check _ tenv (Lvar x) = case lookup x tenv of
    Just t -> t
    Nothing -> Terror ("Variable inconnue : " ++ x )

-- Pour les annotations de type
check c tenv (Ltype e t) =
    let t' = check c tenv e
    in if not c || t' == t
        then t
        else Terror ("Type attendu: " ++ show t ++ ", obtenue: " ++ show t')

-- Pour les expressions conditionnelles
check c tenv (Ltest e1 e2 e3) =
    let t1 = check c tenv e1
        t2 = check c tenv e2
        t3 = check c tenv e3
    in if not c || t1 == Tbool then
        if t2 == t3
            then t2
            else Terror ("Branches de types differents dans l'expression 'if': "
                         ++ "then est " ++ show t2 ++ "  else est " ++ show t3)
        else Terror "Condition de l'expression 'if' n'est pas un booléen"

-- Pour les appels de fonctions
check c tenv (Lsend e0 es) =
    let t0 = check c tenv e0
        ts = map (check c tenv) es
    in case t0 of
        Tfob paramTypes retType ->
            if length paramTypes == length ts &&
               all (uncurry (==)) (zip paramTypes ts)
            then retType
            else Terror ("Arguments incompatibles pour l'appel de fonction : "
                         ++ show t0 ++ " avec " ++ show ts)
        _ -> Terror ("Appel d'une expression non-fonctionnelle : " ++ show e0)


-- Pour les fonctions objets
check c tenv (Lfob args body) =
    let
        (vars, types) = unzip args
        tenv' = args ++ tenv
        tBody = check c tenv' body
    in if not c || not (isTerror tBody)
        then Tfob types tBody
        else Terror ("Erreur dans le corps de la fonction objet: "
                     ++ show tBody)

-- Pour les déclarations non récursives
check c tenv (Llet x e1 e2) =
    let t1 = check c tenv e1
        tenv' = (x, t1) : tenv
        t2 = check c tenv' e2
    in if not c || (not (isTerror t1) && not (isTerror t2))
        then t2
        else Terror ( show t1)

-- Pour les déclarations récursives
check c tenv (Lfix decls body) =
    let
        vars = map fst decls
        exps = map snd decls

        -- Première passe : inférer les types sans vérifier les erreurs
        tenvInit = tenv ++ zip vars
                            (repeat (Terror "Type en cours de définition"))
        typesFirstPass = map (check False tenvInit) exps

        -- Mettre à jour l'environnement avec les types inférés
        tenvInferred = tenv ++ zip vars typesFirstPass

        -- Deuxième passe : vérifier les déclarations avec les types inférés
        typesSecondPass = map (check True tenvInferred) exps

        -- Vérifier s'il y a des erreurs dans les déclarations
        hasError = any isTerror typesSecondPass

        -- Environnement final pour le corps
        tenvFinal = tenv ++ zip vars typesSecondPass
        tBody = check c tenvFinal body
    in
        if hasError || isTerror tBody
        then Terror (show typesSecondPass)
        else tBody

-- Fonction pour vérifier si un Type est une erreur
isTerror :: Type -> Bool
isTerror (Terror _) = True
isTerror _ = False

---------------------------------------------------------------------------
-- Pré-évaluation
---------------------------------------------------------------------------

-- Dexp simplifie le code en éliminant deux aspects qui ne sont plus
-- utiles lors de l'évaluation:
-- - Les annotations de types.
-- - Les noms de variables, remplacés par des entiers qui représentent
--   la position de la variable dans l'environnement.  On appelle ces entiers
--   des [indexes de De Bruijn](https://en.wikipedia.org/wiki/De_Bruijn_index).

type VarIndex = Int

data Dexp = Dnum Int             -- Constante entière.
          | Dbool Bool           -- Constante Booléenne.
          | Dvar VarIndex        -- Référence à une variable.
          | Dtest Dexp Dexp Dexp -- Expression conditionelle.
          | Dfob Int Dexp        -- Construction de fobjet de N arguments.
          | Dsend Dexp [Dexp]    -- Appel de fobjet.
          | Dlet Dexp Dexp       -- Déclaration non-récursive.
          -- Déclaration d'une liste de variables qui peuvent être
          -- mutuellement récursives.
          | Dfix [Dexp] Dexp
          deriving (Show, Eq)

-- Renvoie le "De Bruijn index" de la variable, i.e. sa position
-- dans le contexte.
lookupDI :: TEnv -> Var -> Int -> Int
lookupDI ((x1, _) : xs) x2 n =
    if x1 == x2 then n else lookupDI xs x2 (n + 1)
lookupDI _ x _ = error ("Variable inconnue: " ++ show x)

-- Conversion de Lexp en Dexp.
-- Les types contenus dans le "TEnv" ne sont en fait pas utilisés.
l2d :: TEnv -> Lexp -> Dexp
l2d _ (Lnum n) = Dnum n
l2d _ (Lbool b) = Dbool b
l2d tenv (Lvar v) = Dvar (lookupDI tenv v 0)

-- On ignore les annotations de type
l2d tenv (Ltype e _) = l2d tenv e

-- Expressions conditionnelles
l2d tenv (Ltest e1 e2 e3) =
    Dtest (l2d tenv e1) (l2d tenv e2) (l2d tenv e3)

-- Appels de fonctions
l2d tenv (Lsend f args) =
    Dsend (l2d tenv f) (map (l2d tenv) args)

-- Fonctions objets
l2d tenv (Lfob args body) =
    let
        vars = map fst args
        types = map snd args
        -- Ajouter les variables en tête de tenv
        tenv' = zip vars types ++ tenv
    in
        Dfob (length args) (l2d tenv' body)

-- Déclarations non récursives
l2d tenv (Llet x e1 e2) =
    let
        d1 = l2d tenv e1
        t1 = check False tenv e1  -- Utiliser le type de e1
        tenv' = (x, t1) : tenv  -- Ajouter la nouvelle variable en tête
    in
        Dlet d1 (l2d tenv' e2)

-- Déclarations récursives
l2d tenv (Lfix decls body) =
    let vars = map fst decls
        types = map (const Tnum) decls  -- Assigner Tnum par défaut
        -- Étendre l'environnement avec les nouvelles variables
        tenv' = zip vars types ++ tenv
        decls' = map (l2d tenv' . snd) decls
    in Dfix decls' (l2d tenv' body)

---------------------------------------------------------------------------
-- Évaluateur                                                            --
---------------------------------------------------------------------------

type VEnv = [Value]

eval :: VEnv -> Dexp -> Value
eval _   (Dnum n) = Vnum n
eval _   (Dbool b) = Vbool b

-- Recherche de variable
eval env (Dvar idx) = env !! idx

-- Pour les expressions conditionnelles.
eval env (Dtest condExpr thenExpr elseExpr) =
    case eval env condExpr of
        Vbool True -> eval env thenExpr
        Vbool False -> eval env elseExpr
        _ -> error "Condition non booléenne"

-- Pour les appels de fonctions.
eval env (Dsend fExpr argExprs) =
    let func = eval env fExpr
        args = map (eval env) argExprs
    in case func of
        Vbuiltin f -> f args
        Vfob closure n body ->
            if length args == n
            then eval (args ++ closure) body
            else error "Nombre d'arguments incorrect"
        _ -> error "Appel d'une expression qui n'est pas une fonction"

-- Pour les fonctions objets.
eval env (Dfob n body) = Vfob env n body

-- Pour les déclarations non-récursives.
eval env (Dlet e1 e2) =
    let val = eval env e1
    in eval (val : env) e2

-- Pour les déclarations récursives.
eval env (Dfix decls body) =
    let vals = map (eval env') decls
        env' = vals ++ env
    in eval env' body

---------------------------------------------------------------------------
-- Toplevel                                                              --
---------------------------------------------------------------------------

tenv0 :: TEnv
tenv0 = map (\(x,t,_v) -> (x,t)) env0

venv0 :: VEnv
venv0 = map (\(_x,_t,v) -> v) env0

-- Évaluation sans vérification de types.
evalSexp :: Sexp -> Value
evalSexp = eval venv0 . l2d tenv0 . s2l

checkSexp :: Sexp -> Type
checkSexp = check True tenv0 . s2l
tevalSexp :: Sexp -> Either (Type, Value) String
tevalSexp se = let le = s2l se
               in case check True tenv0 le of
                    Terror err -> Right err
                    t -> Left (t, eval venv0 (l2d tenv0 le))

-- Lit un fichier contenant plusieurs Sexps, les évalue l'une après
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
            in map tevalSexp (sexps s))
       hClose inputHandle

sexpOf :: String -> Sexp
sexpOf = read

lexpOf :: String -> Lexp
lexpOf = s2l . sexpOf

valOf :: String -> Value
valOf = evalSexp . sexpOf