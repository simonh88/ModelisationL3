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
* Pourquoi DFS 1 ne marche pas ? : (L'ordre des visites = parcours en largeur)
Ordre des affichages : Au lieu d'aller au plus profond on sauvegarde sur la pile les sommets visités pour visiter les fils plus tard

* Pourquoi DFS 2 ne marche pas ? : (L'ordre des visites = parcours en largeur come DFS 1)
Ordre des affichages = ordre des visites = la fonction réalise un parcour en largeur


* Pourquoi la pile est trop grande avec DFS 3 ? :
1
2
3
5
1
4
C'est le print de la pile quand on ajoute les enfants, 1 est ajouté 3 fois en tout (1 fois au tout début et deux fois encore là)
* Pourquoi le temps d'éxécution est trop grand avec DFS 4 ? :
i nb tours : 13
Pour ici 6 noeuds, on passe au moins deux fois sur chacun pour vérifier qu'ils n'ont pas de fils pas visités
