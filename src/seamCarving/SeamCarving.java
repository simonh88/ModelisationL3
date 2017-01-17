package seamCarving;

import seamCarving.graphe.Edge;
import seamCarving.graphe.Graph;
import seamCarving.graphe.GraphArrayList;

import java.util.Arrays;
import java.util.ArrayList;
import java.io.*;
import java.util.*;

public class SeamCarving {
    public static int[][] readpgm(String fn) {
        try {
            InputStream f = ClassLoader.getSystemClassLoader().getResourceAsStream(fn);
            BufferedReader d = new BufferedReader(new InputStreamReader(f));
            String magic = d.readLine();
            String line = d.readLine();
            while (line.startsWith("#")) {
                line = d.readLine();
            }
            Scanner s = new Scanner(line);
            int width = s.nextInt();
            int height = s.nextInt();
            line = d.readLine();
            s = new Scanner(line);
            int maxVal = s.nextInt();
            int[][] im = new int[height][width];
            s = new Scanner(d);
            int count = 0;
            while (count < height * width) {
                im[count / width][count % width] = s.nextInt();
                count++;
            }
            return im;
        } catch (Throwable t) {
            t.printStackTrace(System.err);
            return null;
        }
    }

    public static void writepgm(int[][] image, String filename) {
        try {
            int height = image.length;
            int width = image[0].length;
            int count = 0;

            OutputStream os = new FileOutputStream(filename);
            PrintStream ps = new PrintStream(os);
            // La première ligne -> magic P2
            ps.println("P2");
            // La deuxième -> largeur et hauteur
            ps.println(width + " " + height);
            // la troisème -> 255 ?
            ps.println("255");

            while (count < height * width) {
                ps.print(image[count / width][count % width] + " ");

                // Apparement, ya un saut de ligne tout les 25 nombres
                if (count % 25 == 0) {
                    ps.print("\n");
                }
                count++;
            }

            ps.close();

        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    public static int[][] interest(int[][] img) {
        int height = img.length;
        int width = img[0].length;
        int[][] res = new int[height][width];
        int interest_value = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // 3 cas à traiter

                // Le pixel a deux voisins
                if ((j - 1) >= 0 && (j + 1) < width) {
                    // Moyenne des voisins
                    int average = (img[i][j - 1] + img[i][j + 1]) / 2;
                    // Valeur absolue de la différence entre la valeur du pixel et la moyenne de ses deux voisins
                    interest_value = Math.abs(img[i][j] - average);
                }

                // Le pixel n'a qu'un voisin à droite
                else if ((j - 1) < 0) {
                    // Valeur absolue de la différence entre la valeur du pixel et celle de son voisin droit
                    interest_value = Math.abs(img[i][j] - img[i][j + 1]);
                }

                // Le pixel n'a qu'un voisin à gauche
                else {
                    // Valeur absolue de la différence entre la valeur du pixel et celle de son voisin gauche
                    interest_value = Math.abs(img[i][j] - img[i][j - 1]);
                }

                res[i][j] = interest_value;
            }
        }

        return res;
    }

    public static Graph tograph(int[][] itr) {
        int height = itr.length;
        int width = itr[0].length;

        // Le constructeur prend le nombre de sommet en paramètre
        // On ajoute deux pour le sommet de départ et d'arrivé
        GraphArrayList g = new GraphArrayList((height * width) + 2);

        // Pour chaque sommet, on créé une arête entre lui et ses trois 'descendants' si ils éxistent
        // Le sommet représentant la case [i][j] à pour valeur i * largeur + largeur
        // -> Pour éviter que deux sommets aient la même valeur
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                // L'arête reliant un sommet à celui juste en dessous
                if ((i + 1) < height) {
                    g.addEdge(new Edge(
                            (i * width + j),
                            ((i + 1) * width + j),
                            itr[i][j]
                    ));
                }

                // L'arête reliant un sommet à celui en bas à gauche
                if ((i + 1) < height && (j - 1) >= 0) {
                    g.addEdge(new Edge(
                            (i * width + j),
                            ((i + 1) * width + (j - 1)),
                            itr[i][j]
                    ));
                }

                // L'arête reliant un sommet à celui en bas à droite
                if ((i + 1) < height && (j + 1) < width) {
                    g.addEdge(new Edge(
                            (i * width + j),
                            ((i + 1) * width + (j + 1)),
                            itr[i][j]
                    ));
                }
            }
        }

        // On relie les sommets du haut à un sommet qui servira de point de départ
        // Etant donné que les sommets ont pour valeur i * largeur + j,
        // les sommets du haut ont des valeurs allant de 0 à largeur - 1

        // Le sommet de départ à pour valeur hauteur * largeur (en attendant de trouver mieux...)
        // Le poid de ces arête est 0
        int start = width * height;
        for (int i = 0; i < width; i++) {
            g.addEdge(new Edge(
                    start,
                    i,
                    0
            ));
        }

        // On relie les sommets du bas à un sommet qui servira de point d'arrivé
        // Le sommet de départ à pour valeur hauteur * largeur + 1
        int end = (width * height) + 1;
        for (int i = 0; i < width; i++) {
            g.addEdge(new Edge(
                    (height - 1) * width + i,
                    end,
                    itr[height - 1][i]
            ));
        }

        return g;
    }


    //DFS utilisé par le tritipo
    //Ajoute le sommet à l'al une fois que tout ses "fils" sont visités
    static void dfs(Graph g, int u, boolean visite[], ArrayList<Integer> alChemin) {
        visite[u] = true;
        System.out.println("Je visite " + u);
        for (Edge e : g.next(u))
            if (!visite[e.getTo()])
                dfs(g, e.getTo(), visite, alChemin);
        //Ajout du sommet au chemin
        alChemin.add(u);
    }

    //Parcours ordre suffixe dfs et retourne le reverse du tableau
    //du graphe g passé en paramètres
    public static ArrayList<Integer> tritopo(Graph g) {
        int n = g.vertices(); //On récupère le nombre de sommets du graphe
        boolean[] visite = new boolean[n * n + 2];
        ArrayList<Integer> alChemin = new ArrayList<>(n);
        //On lance dfs sur le premier sommet 0
        dfs(g, 0, visite, alChemin);
        Collections.reverse(alChemin);
        return alChemin;
    }

    /**
     * @param g     le graph à étudier
     * @param s     Le sommet d'ou part l'algo bellman
     * @param t     Le sommet d'arrivée
     * @param order Un tri topologique du graphe
     * @return Le chemin du coût minimal ( la liste des sommets à parcourir )
     */
    public static ArrayList<Integer> Bellman(Graph g, int s, int t, ArrayList<Integer> order) {
        //Tableau contenant les distances de s au noeud
        Integer[] dist = new Integer[g.vertices()];
        Arrays.fill(dist, Integer.MAX_VALUE);
        //Tableau qui contient les parents pendant le parcours
        Integer[] parents = new Integer[g.vertices()];
        //On rempli le tableau avec -1 (pas de sommet s'appelant -1)
        Arrays.fill(parents, -1);

        Integer point;

        //L'initialisation à 0 est déjà faite avec le new qui met des 0 et le fill qui met des -1

        //On parcours order qui contient l'ordre du tri topo
        for(int i = 1; i<order.size(); i++){
            point = order.get(i);
            //On recherche toute les arêtes qui mènent au point étudié et on recherche le coût minimal
            for(int j = 0; j<i; j++){
                //On recherche si une arête arrive sur le point qui nous intéresse
                for (Edge e : g.next(order.get(j))){
                    if(e.getTo() == point){//Arête qui mène au point qui nous intéresse
                        if(dist[point] > dist[e.getFrom()]+e.getCost()){//Si on entre c'est qu'on a trouvé plus petit
                            //On actualise nos tableaux
                            dist[point] = dist[e.getFrom()]+e.getCost();
                            parents[point] = e.getFrom();
                        }
                    }
                }
            }

        }
        //Arraylist de réponses
        ArrayList<Integer> rep = new ArrayList<>(g.vertices());

        //On ajoute le point d'arriver en premier
        rep.add(order.get(order.size()-1));

        //Initialisation pour retrouver le CCM
        Integer parcours = parents[g.vertices()-1];
        //On parcours le tableau des parents pour retrouver le CCM
        while(parcours != -1){
            rep.add(parcours);
            parcours = parents[parcours];
        }
        //On retourne le tableau pour avoir le bon sens du chemin
        Collections.reverse(rep);

        return rep;
    }

}
