# ModelisationL3
Projet L3 modélisation (Travail sur les images, réduction d'images en supprimant pixels)

# TODO
* DFS itératif
* tritopo avec DFS itératif
* Graphe implicite
* Plusieurs améliorations

# Informations
* Le jar est dans le dossier jar/
* Les sources dans src/
* Les images de test et les images de test réduites sont dans res/

# Répartition du travail
* Guillaume : writepgm, interest, tograph, reduce_width
* Simon : DFS, tritopo, Bellman, del_pix_column


# Usage
java -jar seamcarving.jar <pgm source> <pgm destination> <nombre d'itération>

# Partie 2
* Pourquoi DFS 1 ne marche pas ? :

 (L'ordre des visites = parcours en largeur)
 Ordre des affichages : Au lieu d'aller au plus profond on sauvegarde sur la pile les sommets visités pour visiter les fils plus tard

* Pourquoi DFS 2 ne marche pas ? :

 (L'ordre des visites = parcours en largeur come DFS 1)
 Ordre des affichages = ordre des visites = la fonction réalise un parcour en largeur


* Pourquoi la pile est trop grande avec DFS 3 ? :

    DF3 affiche a chaque fois tous les sommets fils  non visites du sommet actuel
    c'est a dire quasiment dans l'ordre.

    Il depile 0 et push 1, l'affiche, et fais pareil pour 2 et 3.
    alors qu'il n'a pas a afficher 3.

    Le sommet a etre depile est 3 donc
    il push 5 et l'affiche et etc.




* Pourquoi le temps d'éxécution est trop grand avec DFS 4 ? :

    Etant donne que l'on ne met pas visite[s] = true a tous les sommets
    une fois push dans la stack, on risque de les rajouter une nouvelle fois
    si un autre sommet pointe vers lui puisqu'il ne sera toujours pas considere comme visite.


    Exemple d'un graphe à 100 sommets :

    On depile 0, on met visite[0]=true,
    0 est lie de 1 à 99 donc on push de 1 à 99

    on depile 99, on met visite[99]=true,
    mais il est lié de 1 à 98 donc on push de 1 à 98

    ici on a donc des doublons de 1 à 98.

    on depile 98, on met visite[98]=true,
    mais il est lié de 1 à 97 donc on push de 1 à 97

    ici on a donc trois occurences de 1 à 97 dans la pile.

    etc etc

    Ceci montre bien que l'on peut facilement depasser la limite de la pile

