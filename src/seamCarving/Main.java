package seamCarving;


import seamCarving.graphe.Edge;
import seamCarving.graphe.Graph;
import seamCarving.graphe.GraphArrayList;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    private static void test_question4(){
        GraphArrayList g = new GraphArrayList(6);
        g.addEdge(new Edge(0, 1, 1));
        g.addEdge(new Edge(0, 2, 1));
        g.addEdge(new Edge(0, 3, 1));
        g.addEdge(new Edge(1, 4, 1));
        g.addEdge(new Edge(4, 3, 1));
        g.addEdge(new Edge(3, 5, 1));
        g.addEdge(new Edge(5, 1, 1));

        ArrayList<Integer> res = SeamCarving.tritipo(g);
        for (Integer re : res) {
            System.out.println(re);
        }
    }


    public static void main(String[] argv) {

        System.out.println("\n[*] Test de readpgm");
        int[][] image = SeamCarving.readpgm("ex0.pgm");
        System.out.println("[I] Largeur : " + image[0].length);
        System.out.println("[I] Hauteur : " + image.length);
        System.out.println("\n[*] Test de write pgm, voir res/test.pgm");
        SeamCarving.writepgm(image, "res/test0.pgm");

        System.out.println("\n[*] Test de interest");
        int[][] pix_interest = SeamCarving.interest(image);
        System.out.println("[I] ToString : " + Arrays.deepToString(pix_interest));
        System.out.println("[I] Largeur : " + pix_interest[0].length);
        System.out.println("[I] Hauteur : " + pix_interest.length);

        System.out.println("\n[*] Test de tograph");
        Graph g = SeamCarving.tograph(pix_interest);
        System.out.println("\n[I] voir interest0.dot");
        g.writeFile("res/interest0.dot");

        System.out.println("TEST DE LA QUESTION 4 : ");
        test_question4();
    }
}
