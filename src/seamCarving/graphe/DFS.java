package seamCarving.graphe;

import seamCarving.SeamCarving;

import java.util.ArrayList;
import java.util.Stack;

class DFS {

    /**
     *
     * @param g le graph
     * @param s le sommet de départ
     */
    public static void botched_dfs1(Graph g, int s) {
        ArrayList<Integer> res = new ArrayList<>();
        Stack<Integer> stack = new Stack<Integer>();
        boolean visited[] = new boolean[g.vertices()];

        // On push le premier sommet
        stack.push(s);
        System.out.printf("On push/visite %d\n", s);
        // ON dit qu'on l'a visité
        visited[s] = true;

        // Tant que la pile n'est pas vide
        while (!stack.empty()) {

            // On prend le sommet de la pile
            int u = stack.pop();

            //System.out.println(u);
            res.add(u);

            // Pour chaque arrête issues de ce sommet
            for (Edge e : g.next(u))
                // Si on n'a pas visité le sommet où pointe cette arrête

                // En gros : pour chaque sommet fils, on le visite et le le push pour visiter ses fils plus tard
                if (!visited[e.to]) {
                    // On visite ce sommet et on le push
                    visited[e.to] = true;
                    stack.push(e.to);
                    System.out.printf("On push/visite %d\n", e.to);
                }
        }

        System.out.print("\nRésultat : ");
        for (Integer i : res) {
            System.out.printf("%d ", i);
        }
        System.out.println("");
    }

    /**
     * La principale différence avec DFS 1 : le println n'est plus placé avant l'itération sur les arrêtes
     * -> il est placé au niveau du push/visite (du coup c'est une fonction qui semble faire un parcour en largeur)
     * @param g le graph
     * @param s le sommet de départ
     */
    public static void botched_dfs2(Graph g, int s) {
        // Pour stocker le résultat
        ArrayList<Integer> res = new ArrayList<>();
        Stack<Integer> stack = new Stack<Integer>();
        boolean visited[] = new boolean[g.vertices()];

        System.out.printf("On push/visite %d\n", s);
        stack.push(s);
        //System.out.println(s);
        res.add(s);
        visited[s] = true;

        // Tant que la stack n'est pas vide
        while (!stack.empty()) {

            // On pop un sommet de la stack
            int u = stack.pop();

            // Pour chaque arrête issue de ce sommet
            for (Edge e : g.next(u))

                // Si nous n'avons pas visité le sommet pointé par cette arrête
                if (!visited[e.to]) {
                    // Alors on le visite et on le push (pour visiter ses fils plus tard ?)
                    //System.out.println(e.to);
                    res.add(e.to);
                    System.out.printf("On push/visite %d\n", e.to);
                    visited[e.to] = true;
                    stack.push(e.to);
                }
        }

        System.out.print("\nRésultat : ");
        for (Integer i : res) {
            System.out.printf("%d ", i);
        }
        System.out.println("");
    }


    /**
     *
     * @param g le graph
     * @param s le sommet de départ
     */
    public static void botched_dfs3(Graph g, int s) {
        // Pour stocker le résultat
        ArrayList<Integer> res = new ArrayList<>();

        Stack<Integer> stack = new Stack<Integer>();
        boolean visited[] = new boolean[g.vertices()];

        // On push le sommet de départ
        stack.push(s);

        // Tant que la pile n'est pas vide
        while (!stack.empty()) {

            // On pop un sommet de la pile
            int u = stack.pop();

            // Si il n'a pas été visité
            if (!visited[u]) {
                // Alors on le visite

                visited[u] = true;
                //System.out.println(u);
                res.add(u);

                // Et on ajoute tous ces fils à la pile
                for (Edge e : g.next(u))
                    if (!visited[e.to]) {
                        System.out.println(e.to);
                        //Il y a deux fois 1 qui est affiché
                        stack.push(e.to);
                    }

            }
        }

        System.out.print("\nRésultat : ");
        for (Integer i : res) {
            System.out.printf("%d ", i);
        }
        System.out.println("");
    }


    /**
     *
     * @param g le graphe
     * @param s le sommet de départ
     */
    public static void botched_dfs4(Graph g, int s) {
        // Pour stocker le résultat
        ArrayList<Integer> res = new ArrayList<>();

        Stack<Integer> stack = new Stack<Integer>();
        boolean visited[] = new boolean[g.vertices()];

        // On push et on visite le premier sommet
        stack.push(s);
        visited[s] = true;
        //System.out.println(s);
        res.add(s);
        int nbTours = 0;
        // Tant que la stack n'est pas vide
        while (!stack.empty()) {
            boolean end = true;
                /* (a) Soit u le sommet en haut de la pile */
                /* (b) Si u a un voisin non visité, alors */
                /* (c)   on le visite et on l'ajoute sur la pile */
                /* Sinon */
                /*     (d) on enlève u de la pile */

                /* (a) */
            int u = stack.peek();
            for (Edge e : g.next(u)) {
                nbTours++;
                if (!visited[e.to]) /* (b) */ {
                    visited[e.to] = true;
                    //System.out.println(e.to);
                    res.add(e.to);
                    stack.push(e.to); /*(c) */
                    end = false;
                    break;
                }
            }
            if (end) /*(d)*/
                System.out.println("Pop de la fin : "+stack.pop());
        }
        System.out.println("i nb tours : "+nbTours);
        // Affichage capacité et résultat
        System.out.println("Capacité : " + stack.capacity());
        System.out.print("\nRésultat : ");
        for (Integer i : res) {
            System.out.printf("%d ", i);
        }
        System.out.println("");
    }


    public static void testGraph() {
        int n = 5;
        int i, j;
        GraphArrayList g = new GraphArrayList(6);
        g.addEdge(new Edge(0, 1, 1));
        g.addEdge(new Edge(0, 2, 1));
        g.addEdge(new Edge(0, 3, 1));
        g.addEdge(new Edge(1, 4, 1));
        g.addEdge(new Edge(4, 3, 1));
        g.addEdge(new Edge(3, 5, 1));
        g.addEdge(new Edge(5, 1, 1));
        g.writeFile("testGraph.dot");
        System.out.println("\n--- DFS1 ---\n");
        botched_dfs1(g, 0);
        System.out.println("\n--- DFS2 ---\n");
        botched_dfs2(g, 0);
        System.out.println("\n--- DFS3 ---\n");
        botched_dfs3(g, 0);
        System.out.println("\n--- DFS4 ---\n");
        botched_dfs4(g, 0);
    }

    public static void testTriTopo() {
        int n = 5;
        int i, j;
        GraphArrayList g = new GraphArrayList(6);
        g.addEdge(new Edge(0, 1, 1));
        g.addEdge(new Edge(0, 2, 1));
        g.addEdge(new Edge(0, 3, 1));
        g.addEdge(new Edge(1, 4, 1));
        g.addEdge(new Edge(4, 3, 1));
        g.addEdge(new Edge(3, 5, 1));
        g.addEdge(new Edge(5, 1, 1));
        ArrayList<Integer> tritopo_it = SeamCarving.tritopo_it(g, 0);

        System.out.print("Tri topo itératif : ");
        for (int t : tritopo_it) {
            System.out.print(t + " ");
        }
        System.out.println("");

        ArrayList<Integer> tritopo = SeamCarving.tritopo(g);

        System.out.print("Tri topo de base  : ");
        for (int t : tritopo_it) {
            System.out.print(t + " ");
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        testTriTopo();
    }
}
