# TODO

+ The sounds effects are buggy
+ Make the client disapear when in the car

+ Fancy animation when we drop off the client
+ Win screen for player 1
+ Improve timer mechanics
+ Start screen
+ Add animations for oldLadies?
+ Improve gameOver screen

# Gameplay

+ Le joueur 1 conduit la voiture, et doit déposer ses covoiturés aux quatres points de la ville.
+ Le joueur 2 s'acharne de le ralentir, au moyen de Vieilles qui sont invoquées.

## Conditions de victoires du joueur 1

+ Il arrive à livrer les 5 covoiturés dans le temps imparti
+ Il n'entre pas en collision avec une vieille ou avec les batiments.

## Conditions de victoires du joueur 2

+ Il gagne si le joueur 1 échoue.

# Fonctionnement du jeu

## Gestion des clients et de leurs destinations
+ 4 clients total, une couleur par client (rouge : 0, bleu : 1, vert : 2, rose : 3)
+ Chaque destination est colorée de la couleur du client correspondant.
+ Les destinations des passagers sont déterminés au début de la partie.

+ Passages piétons sont générés aléatoirement.

# Contrôles

## Contrôles du joueur 1 (voiture)

+ Les flèches au clavier pour bifurquer, reculer et avancer.

## Contrôles du joueur 2 

+ Invocation des Vieilles à la souris. (On ne peut invoquer des vieilles que sur les passages piétons)

