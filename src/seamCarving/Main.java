package seamCarving;


import seamCarving.graphe.Edge;
import seamCarving.graphe.Graph;
import seamCarving.graphe.GraphArrayList;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    static GraphArrayList g;
    static ArrayList<Integer> topo;

    //TEST OK
    private static void test_question4(){
        g = new GraphArrayList(6);
        g.addEdge(new Edge(0, 1, 1));
        g.addEdge(new Edge(0, 2, 1));
        g.addEdge(new Edge(0, 3, 1));
        g.addEdge(new Edge(1, 4, 1));
        g.addEdge(new Edge(4, 3, 1));
        g.addEdge(new Edge(3, 5, 1));

        topo = SeamCarving.tritopo(g);
        for (Integer re : topo) {
            System.out.println(re);
        }
    }

    //Test de la question 5 à partir du résultat de la Q4
    private static void test_question5(){
        System.out.println("Test de la question 5 : ");
        ArrayList<Integer> ccm = SeamCarving.Bellman(g, 0, 0, topo);
        for(Integer i : ccm){
            System.out.println(i);
        }
    }


    public static void main(String[] argv) {

        int[][] res = SeamCarving.reduce_width("ex1.pgm", 30);
        SeamCarving.writepgm(res, "res/testreduce.pgm");
    }
}
