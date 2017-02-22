package seamCarving.graphe;

import java.util.ArrayList;

public class GraphImplicit implements Graph {
    int N = 0;
    int[][] interest;
    int width;
    int height;

    GraphImplicit(int N) {
        this.N = N;
    }

    public GraphImplicit(int[][] interest, int w, int h) {
        this.interest = interest;

        width = w;
        height = h;
        N = width * height + 2;

    }

    public int vertices() {
        return N;
    }

    public Iterable<Edge> next(int v) {
        ArrayList<Edge> edges = new ArrayList();
        int ligne = v / width;
        int col = v % width;
        if (v == width * height) {//Sommet tout en haut
            for (int i = 0; i < width; i++) {
                edges.add(new Edge(v, i, 0));
            }
        } else if (v >= ((height - 1) * width)) {
            //Derniere ligne
            //On ignore le dernier noeud
            if (v != width * height + 1) edges.add(new Edge(v, width * height + 1, interest[ligne][col]));
        } else if (col == 0) {//Bord gauche
            edges.add(new Edge(v, v + width, interest[ligne][col]));
            edges.add(new Edge(v, v + width + 1, interest[ligne][col]));
        } else if (col == width) {
            //Bord droit
            edges.add(new Edge(v, v + width, interest[ligne][col]));
            edges.add(new Edge(v, v + width - 1, interest[ligne][col]));
        } else {
            //Cas ou on est pas sur les bords
            edges.add(new Edge(v, v + width - 1, interest[ligne][col]));
            edges.add(new Edge(v, v + width, interest[ligne][col]));
            edges.add(new Edge(v, v + width + 1, interest[ligne][col]));
        }

        return edges;

    }

    public Iterable<Edge> prev(int v) {
        ArrayList<Edge> edges = new ArrayList();
        int ligne = v / width;
        int col = v % width;
        if (v == width * height) {//Sommet tout en haut
            //Pas de prev
        } else if (v < width) {//Premiere ligne
            //System.out.println("PREMIERE LIGNE " + v);
            edges.add(new Edge(width * height, v, 0));
        } else if (col == 0) {//Bord gauche
            //System.out.println("GAUCHE " + v);
            edges.add(new Edge(v - width, v, interest[(v - width) / width][(v - width) % width]));
            edges.add(new Edge(v - width + 1, v, interest[(v - width + 1) / width][(v - width + 1) % width]));
        } else if (col == width - 1) {//Bord droit
            //System.out.println("DROIT " + v);
            edges.add(new Edge(v - width, v, interest[(v - width) / width][(v - width) % width]));
            edges.add(new Edge(v - width - 1, v, interest[(v - width - 1) / width][(v - width - 1) % width]));
        } else {//Cas ou on est pas sur les bords ou c'est le dernier noeud
            if (v == width * height + 1) {//Dernier noeud
                for (int i = 0; i < width; i++) {
                    edges.add(new Edge(v - width - 1 + i, v, interest[(v - width - 1 + i) / width][(v - width + 1) % width]));
                }
            } else {//Pas bord
                //System.out.println("Milieu : " + v);
                edges.add(new Edge(v - width + 1, v, interest[(v - width + 1) / width][(v - width + 1) % width]));
                edges.add(new Edge(v - width, v, interest[(v - width) / width][(v - width) % width]));
                edges.add(new Edge(v - width - 1, v, interest[(v - width - 1) / width][(v - width - 1) % width]));
            }
        }

        /*System.out.println("Je suis le noeud : " + v);
        for (Edge e : edges) {
            System.out.println(e.toString());
        }*/

        return edges;
    }


}
