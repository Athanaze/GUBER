# Gameplay

+ Le joueur 1 conduit la voiture, et doit déposer ses covoiturés aux quatres points de la ville.
+ Le joueur 2 s'acharne à le ralentir, au moyen de Vieilles qui sont invoquées en cliquant sur les passages piétons.
+ Au maximum 5 Vieilles peuvent être dans le monde en même temps.
+ Les vieilles disparaissent au bon d'un certain temps. Quand une Vieille disparait, le "compteur de Vieilles" est incrémenté.
+ Un compte à rebours de 60 sec est lancé au début de la partie.

## Conditions de victoires du joueur 1

+ Il arrive à livrer les 5 covoiturés dans le temps imparti
+ Il n'entre pas en collision ni avec une Vieille, ni avec les batiments (ou arbre, fontaine, hélipad...), ni avec les autres voitures.

## Conditions de victoires du joueur 2

+ Il gagne si le joueur 1 échoue.

# Fonctionnement du jeu

# Création du monde aléatoire
+ Les emplacement des passages piétons sont choisis aléatoirement.
+ Le type de batiment (immeuble, fontaine, héliport ou parc) est choisi aléatoirement
+ Les voitures "Non Player Character" accélèrent aléatoirement

## Gestion des clients et de leurs destinations
+ 4 clients total, une couleur par client (rouge : 0, bleu : 1, vert : 2, rose : 3)
+ Chaque destination est colorée de la couleur du client correspondant.
+ Les destinations des passagers sont déterminées au début de la partie.

# Contrôles

## Contrôles du joueur 1 (voiture)

+ Les flèches au clavier pour bifurquer, reculer et avancer.

## Contrôles du joueur 2 

+ Invocation des Vieilles à la souris. (On ne peut invoquer des vieilles que sur les passages piétons)

# Sons

La majorité des effets sonores ont été enregistré par nous-mêmes.

Certains effets ont été créés par des tiers. Ceux-ci sont listés ici conformément à leur licence (<https://www.zapsplat.com/license-type/standard-license/>)

Sound effects obtained from <https://www.zapsplat.com>:

"zapsplat_multimedia_game_tone_advance_proceed_synth_delayed_46034.mp3" : <https://www.zapsplat.com/music/synthesized-game-tone-advance-proceed/>

"zapsplat_impact_crash_metal_and_glass_hard_001_32183.mp3" : <https://www.zapsplat.com/music/impact-hard-crash-metal-and-glass-1/>