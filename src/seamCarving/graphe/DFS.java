package seamCarving.graphe;

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

        System.out.print("Résultat : ");
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
    public static void botched_dfs2(Graph g, int s) {
        Stack<Integer> stack = new Stack<Integer>();
        boolean visited[] = new boolean[g.vertices()];
        stack.push(s);
        System.out.println(s);
        visited[s] = true;
        while (!stack.empty()) {
            int u = stack.pop();
            for (Edge e : g.next(u))
                if (!visited[e.to]) {
                    System.out.println(e.to);
                    visited[e.to] = true;
                    stack.push(e.to);
                }
        }
    }

    public static void botched_dfs3(Graph g, int s) {
        Stack<Integer> stack = new Stack<Integer>();
        boolean visited[] = new boolean[g.vertices()];
        stack.push(s);
        while (!stack.empty()) {
            int u = stack.pop();
            if (!visited[u]) {
                visited[u] = true;
                System.out.println(u);
                for (Edge e : g.next(u))
                    if (!visited[e.to])
                        stack.push(e.to);

            }
        }
    }


    public static void botched_dfs4(Graph g, int s) {
        Stack<Integer> stack = new Stack<Integer>();
        boolean visited[] = new boolean[g.vertices()];
        stack.push(s);
        visited[s] = true;
        System.out.println(s);
        while (!stack.empty()) {
            boolean end = true;
        /* (a) Soit u le sommet en haut de la pile */
        /* (b) Si u a un voisin non visité, alors */
        /*     (c) on le visite et on l'ajoute sur la pile */
        /* Sinon */
        /*     (d) on enlève u de la pile */
	   
	    /* (a) */
            int u = stack.peek();
            for (Edge e : g.next(u))
                if (!visited[e.to]) /* (b) */ {
                    visited[e.to] = true;
                    System.out.println(e.to);
                    stack.push(e.to); /*(c) */
                    end = false;
                    break;
                }
            if (end) /*(d)*/
                stack.pop();
        }
        System.out.println(stack.capacity());
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

    public static void main(String[] args) {
        testGraph();
    }
}
