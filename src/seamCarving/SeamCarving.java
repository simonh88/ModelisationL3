package seamCarving;

import com.sun.deploy.util.ArrayUtil;
import seamCarving.graphe.Edge;
import seamCarving.graphe.Graph;
import seamCarving.graphe.GraphArrayList;
import seamCarving.graphe.GraphImplicit;
import seamCarving.graphe.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.io.*;
import java.util.*;

public class SeamCarving {

    /**
     * Lecture des ppm p2
     * @param fn
     * @return
     */
    public static int[][] readpgm(String fn) {
        try {
            InputStream f = new FileInputStream(fn);
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

    /**
     * Lecture des ppm P3
     * @param fn filename
     * @return Pixel[][]
     */
    public static PixelRGB[][] readppm(String fn) {
        try {
            InputStream f = new FileInputStream(fn);
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
            PixelRGB[][] im = new PixelRGB[height][width];
            s = new Scanner(d);
            int count = 0;
            while (count < height * width) {
                int r = s.nextInt();
                int g = s.nextInt();
                int b = s.nextInt();
                im[count / width][count % width] = new PixelRGB(r, g, b);
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

    /**
     * Ecriture des ppm p3
     * @param image
     * @param filename
     */
    public static void writeppm(PixelRGB[][] image, String filename) {
        try {
            int height = image.length;
            int width = image[0].length;
            int count = 0;

            OutputStream os = new FileOutputStream(filename);
            PrintStream ps = new PrintStream(os);
            // La première ligne -> magic P2
            ps.println("P3");
            // La deuxième -> largeur et hauteur
            ps.println(width + " " + height);
            // la troisème -> 255 ?
            ps.println("255");

            while (count < height * width) {
                int r = image[count / width][count % width].getR();
                int g = image[count / width][count % width].getG();
                int b = image[count / width][count % width].getB();
                ps.print(r + " " + g + " " + b + " ");
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
        for (Edge e : g.next(u)) {
            if (!visite[e.getTo()]) {
                dfs(g, e.getTo(), visite, alChemin);
            }
        }
        //Ajout du sommet au chemin
        alChemin.add(u);
    }

    //Parcours ordre suffixe dfs et retourne le reverse du tableau
    //du graphe g passé en paramètres
    public static ArrayList<Integer> tritopo(Graph g) {
        int n = g.vertices(); //On récupère le nombre de sommets du graphe
        boolean[] visite = new boolean[n];
        ArrayList<Integer> alChemin = new ArrayList<>(n);
        //On lance dfs sur le premier sommet 0
        dfs(g, g.vertices() - 2, visite, alChemin);
        Collections.reverse(alChemin);
        return alChemin;
    }

    /**
     * Q2 : Parcour dans l'ordre suffixe avec DFS itératif et retourne un tri topo
     *
     * @param g le graphe à parcourir
     * @param s le sommet de départ
     * @return le tri topologique sous forme d'ArrayList
     */
    public static ArrayList<Integer> tritopo_it(Graph g, int s) {
        ArrayList<Integer> suffixe = new ArrayList<>();
        boolean visited[] = new boolean[g.vertices()];
        Iterator<Edge> it;
        int u;

        // (si c’est plus simple, on pourra
        // utiliser deux piles (une pour u et une pour it) plutˆot qu’une pile avec des paires (u, it))
        Stack<Integer> stack_u = new Stack<>();
        Stack<Iterator<Edge>> stack_it = new Stack<>();

        // Au d´epart, on ajoute dans la pile la paire (s, next(s))
        stack_u.push(s);
        stack_it.push(g.next(s).iterator());

        // Tant que la pile n'est pas vide
        while (!stack_u.empty()) {

            //Recuperer (avec peek) la paire (u, it) en sommet de la pile.
            u = stack_u.peek();
            it = stack_it.peek();

            // S’il reste un voisin de u non test´e (c’est `a dire si it.hasNext), alors soit v ce voisin.
            if (it.hasNext()) {
                int v = it.next().getTo();
                // Si v est visite, on revient au d´ebut de la boucle
                if (visited[v]) continue;
                // Sinon, on ajoute v aux sommets visites, et on ajoute la paire (v, next(v)) en tete de la pile
                visited[v] = true;
                stack_u.push(v);
                stack_it.push(g.next(v).iterator());
            }
            // — Sinon, on retire (u, it) de la pile. On a alors fini de visiter u (et on l’ajoute au tableau suffixe
            // si on cherche a calculer l’ordre suffixe)
            else {
                suffixe.add(u);
                stack_u.pop();
                stack_it.pop();
            }
        }

        // On prend l'inverse de l'orde suffixe
        Collections.reverse(suffixe);
        return suffixe;
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
        Arrays.fill(dist, Integer.MAX_VALUE - 100000);
        dist[order.get(0)] = 0;
        //Tableau qui contient les parents pendant le parcours
        Integer[] parents = new Integer[g.vertices()];
        //On rempli le tableau avec -1 (pas de sommet s'appelant -1)
        Arrays.fill(parents, -1);

        Integer point;


        //L'initialisation à 0 est déjà faite avec le new qui met des 0 et le fill qui met des -1

        //On parcours order qui contient l'ordre du tri topo
        for (int i = 0; i < order.size(); i++) {
            point = order.get(i);
            //On recherche toute les arêtes qui mènent au point étudié et on recherche le coût minimal
            //On parcours les arêtes qui mènent à notre point
            for (Edge e : g.prev(point)) {
                int a = dist[e.getFrom()] + e.getCost();
                if (dist[point] >= dist[e.getFrom()] + e.getCost()) {//Si on entre c'est qu'on a trouvé plus petit
                    //On actualise nos tableaux
                    dist[point] = dist[e.getFrom()] + e.getCost();
                    parents[point] = e.getFrom();
                }
            }
        }
        //Arraylist de réponses
        ArrayList<Integer> rep = new ArrayList<>(g.vertices());

        //Initialisation pour retrouver le CCM
        Integer parcours = parents[g.vertices() - 1];
        //On parcours le tableau des parents pour retrouver le CCM
        while (parcours != -1) {
            rep.add(parcours);
            parcours = parents[parcours];
        }
        rep.remove(rep.size() - 1);//On enleve le dernier point factice
        //On retourne le tableau pour avoir le bon sens du chemin
        Collections.reverse(rep);

        return rep;
    }

    /***
     * Supprime un 'colonne' de pixel, ceux présent dans le plus cour chemin calculé
     * @param img l'image source
     * @param res_bellman le plus court chemin
     * @return l'image avec une colonne de pixel en moi
     */
    public static int[][] del_pixel_column(int[][] img, ArrayList<Integer> res_bellman) {

        // La nouvelle image a un pixel de moins en largeur
        int[][] res = new int[img.length][img[0].length - 1];

        // On itère sur les deux tableaux en même temps.
        // Pour itérer sur les lignes, on utilise deux variables différentes
        // Si le pixel courant est dans le chemin le plus court, on incrémente une seule des deux variables
        for (int i = 0; i < img.length; i++) {
            int j_res = 0;
            int j_src = 0;
            while (j_res < res[0].length) {
                if (res_bellman.contains(i * img[0].length + j_src)) {
                    int v = i * img.length + j_src;
                    j_src++;
                }
                res[i][j_res] = img[i][j_src];
                j_res++;
                j_src++;
            }
        }

        return res;
    }

    public static int[][] reduce_width(String filename, int nb_pixel) {
        int[][] image = SeamCarving.readpgm(filename);
        int[][] res = image;

        System.out.println("Avancement : ");
        for (int i = 0; i < nb_pixel; i++) {
            int[][] pix_interest = SeamCarving.interest(res);
            //System.out.println("Taille p : " + pix_interest.length + "Taille p[0] : " + pix_interest[0].length);
            //Graph g2 = SeamCarving.tograph(pix_interest);
            int height = res[0].length;
            int width = res.length;


            Graph g = new GraphImplicit(pix_interest, height, width);
            g.writeFile("res.dot");
            //g2.writeFile("res2.dot");
            //ArrayList<Integer> tritopo2 = SeamCarving.tritopo(g);
            //Graph g = new GraphImplicitEnergieAvant(res);
            ArrayList<Integer> tritopo = SeamCarving.tritopo_it(g, height * width);


            ArrayList<Integer> ppc = SeamCarving.Bellman(g, height * width, height * width + 1, tritopo);
            res = del_pixel_column(res, ppc);
            System.out.printf("%d/%d\n", i + 1, nb_pixel);
        }
        System.out.println("OK");

        return res;
    }


    public static int[][] reduce_width_line(String filename, int nb_pixel) {
        int[][] image = SeamCarving.readpgm(filename);
        int[][] res = image;
        System.out.println("h : "+res[0].length +"w : "+res.length);
        res = reverse_img(res);


        System.out.println("Avancement : ");
        for (int i = 0; i < nb_pixel; i++) {
            //int[][] pix_interest = SeamCarving.interest(res);
            //System.out.println("Taille p : " + pix_interest.length + "Taille p[0] : " + pix_interest[0].length);
            //Graph g2 = SeamCarving.tograph(pix_interest);
            int height = res[0].length;
            System.out.println("AFTER h : "+res[0].length +"w : "+res.length);

            int width = res.length;


            //Graph g = new GraphImplicit(pix_interest, height, width);
            //g.writeFile("res.dot");
            //g2.writeFile("res2.dot");
            //ArrayList<Integer> tritopo2 = SeamCarving.tritopo(g);
            Graph g = new GraphImplicitEnergieAvant(res);
            ArrayList<Integer> tritopo = SeamCarving.tritopo_it(g, height * width);


            ArrayList<Integer> ppc = SeamCarving.Bellman(g, height * width, height * width + 1, tritopo);
            res = del_pixel_column(res, ppc);
            System.out.printf("%d/%d\n", i + 1, nb_pixel);
        }
        System.out.println("OK");
        res = reverse_img(res);
        return res;
    }

    public static int[][] reverse_img(int[][] img) {
        int[][] pivot = new int[img[0].length][];
        for (int row = 0; row < img[0].length; row++)
            pivot[row] = new int[img.length];

        for (int row = 0; row < img.length; row++)
            for (int col = 0; col < img[row].length; col++)
                pivot[col][row] = img[row][col];

        return pivot;
    }
    /**
     * Test lecture écriture ppm
     * @param args
     */
    public static void main(String[] args) {
        PixelRGB[][] img = readppm("res/chateau.ppm");
        writeppm(img, "res/testwriteppm.ppm");
    }

}
