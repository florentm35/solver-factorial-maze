# solver-factorial-maze

## Introduction

Ce programme est là pour répondre au défis 13 du serveur discord NaN : https://discord.gg/notaname sur les labyrinthe fractal

## Algorithme

Le principe utilisé est le brute force

On calcule à la main tout les chemins possible direct d'un point (Data.java)

En suite on va initialisé un chemin avec comme point d'entré 0 (Here)

Ensuite pour chaques path :
- On verifie si il a une sortie terminal (solution trouvé)
- Sinon on va calculer tout les chemins possibles
- On recommence avec les path calculer

## Solution

La solutione st trouvé lors de la 25 itérations :
```
Total time : 5,00 ms - Iteration time : 5,00 ms - Profondeur : 1 - Nombre path : 1 - memory usage : 8 Mo
Total time : 27,00 ms - Iteration time : 22,00 ms - Profondeur : 2 - Nombre path : 2 - memory usage : 8 Mo
Total time : 29,00 ms - Iteration time : 2,00 ms - Profondeur : 3 - Nombre path : 5 - memory usage : 8 Mo
Total time : 29,00 ms - Iteration time : 0,00 ms - Profondeur : 4 - Nombre path : 9 - memory usage : 8 Mo
Total time : 30,00 ms - Iteration time : 1,00 ms - Profondeur : 5 - Nombre path : 18 - memory usage : 8 Mo
Total time : 31,00 ms - Iteration time : 1,00 ms - Profondeur : 6 - Nombre path : 33 - memory usage : 8 Mo
Total time : 41,00 ms - Iteration time : 10,00 ms - Profondeur : 7 - Nombre path : 60 - memory usage : 8 Mo
Total time : 49,00 ms - Iteration time : 8,00 ms - Profondeur : 8 - Nombre path : 123 - memory usage : 8 Mo
Total time : 52,00 ms - Iteration time : 3,00 ms - Profondeur : 9 - Nombre path : 231 - memory usage : 8 Mo
Total time : 57,00 ms - Iteration time : 5,00 ms - Profondeur : 10 - Nombre path : 438 - memory usage : 8 Mo
Total time : 69,00 ms - Iteration time : 12,00 ms - Profondeur : 11 - Nombre path : 860 - memory usage : 8 Mo
Total time : 99,00 ms - Iteration time : 30,00 ms - Profondeur : 12 - Nombre path : 1636 - memory usage : 8 Mo
Total time : 108,00 ms - Iteration time : 9,00 ms - Profondeur : 13 - Nombre path : 3115 - memory usage : 9 Mo
Total time : 117,00 ms - Iteration time : 9,00 ms - Profondeur : 14 - Nombre path : 6022 - memory usage : 10 Mo
Total time : 130,00 ms - Iteration time : 13,00 ms - Profondeur : 15 - Nombre path : 11487 - memory usage : 12 Mo
Total time : 223,00 ms - Iteration time : 93,00 ms - Profondeur : 16 - Nombre path : 21884 - memory usage : 20 Mo
Total time : 323,00 ms - Iteration time : 100,00 ms - Profondeur : 17 - Nombre path : 42001 - memory usage : 35 Mo
Total time : 505,00 ms - Iteration time : 182,00 ms - Profondeur : 18 - Nombre path : 80114 - memory usage : 63 Mo
Total time : 1,03 s - Iteration time : 528,00 ms - Profondeur : 19 - Nombre path : 152748 - memory usage : 127 Mo
Total time : 1,79 s - Iteration time : 761,00 ms - Profondeur : 20 - Nombre path : 292183 - memory usage : 229 Mo
Total time : 2,61 s - Iteration time : 817,00 ms - Profondeur : 21 - Nombre path : 557443 - memory usage : 490 Mo
Total time : 4,36 s - Iteration time : 1,75 s - Profondeur : 22 - Nombre path : 1063399 - memory usage : 1027 Mo
Total time : 7,60 s - Iteration time : 3,24 s - Profondeur : 23 - Nombre path : 2031467 - memory usage : 1937 Mo
Total time : 12,79 s - Iteration time : 5,19 s - Profondeur : 24 - Nombre path : 3876580 - memory usage : 4159 Mo
Total time : 12,88 s - Iteration time : 99,00 ms - Profondeur : 25 - Nombre path : 353874 - memory usage : 4159 Mo
0(0),3(1),1(2),B12(1),11(2),10(3),4(4),6(5),8(6),D10(5),B7(4),C7(3),A9(2),B8(1),3(2),4(3),6(4),8(5),D10(4),B7(3),10(4),D8(3),B6(2),D4(1),A5(0)
```
Et si on vérifie à la main le résultat (Un check suplémentaire se trouve dans la classe Test.java):

```
0 => A3 // A
3 => B1 // AB
1 => 12 // A
B12 => B11 // AB
11 => A10 // ABA
10 => C4 // ABAC
4 => B6 // ABACB
6 => D8 // ABACBD
8 => 10 // ABACB
D10 => 7 // ABAC
B7 => 7 // ABA
C7 => 9 // AB
A9 => 8 // A
B8 => D3 // AD
3 => B4 // ADB
4 => B6 // ADBB
6 => D8 // ADBBD
8 => 10 // ADBB
D10 => 7 // ADB
B7 => D10 // ADBD
10 => 8 // ADB
D8 => 6 // AD
B6 => 4 // A
D4 => 5 //
A5 => 1/12
```

A noter qu'aucunne solution n'est trouvé après 28 iterations cela prend déjà 25 Go de ram qui est la limite de mon matériel, 
il serait possible d'aller plus loins en passant par un implémentation sur disque plutôt que mémoire.

```
Total time : 16,75 s - Iteration time : 6,23 s - Profondeur : 24 - Nombre path : 3876580 - memory usage : 3441 Mo
0(0),3(1),1(2),B12(1),11(2),10(3),4(4),6(5),8(6),D10(5),B7(4),C7(3),A9(2),B8(1),3(2),4(3),6(4),8(5),D10(4),B7(3),10(4),D8(3),B6(2),D4(1),A5(0)
Total time : 33,60 s - Iteration time : 16,85 s - Profondeur : 25 - Nombre path : 7397346 - memory usage : 6830 Mo
Total time : 55,59 s - Iteration time : 21,99 s - Profondeur : 26 - Nombre path : 14124567 - memory usage : 13631 Mo
Total time : 1,77 m - Iteration time : 50,52 s - Profondeur : 27 - Nombre path : 26957087 - memory usage : 24712 Mo
```

En générale la mémoire explose au alentoure des 20 millions de chemin à parcourir, l'alogithme est donc très dépendant des chemins possibles.
