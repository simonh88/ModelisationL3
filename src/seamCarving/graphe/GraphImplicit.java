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
        int col = (v - 1) % width;
        if (v + width + 1 < N) {//Si on rentre on traite tout sauf la derniere ligne de sommets qui sont rattachés au dernier sommet
            if (v == 0) {//Sommet tout en haut
                for (int i = 0; i < width; i++) {
                    edges.add(new Edge(v, v + width + i, 0));
                }
            } else if (v % width != 0) {//
                edges.add(new Edge(v, v + width - 1, interest[ligne][col]));
                edges.add(new Edge(v, v + width, interest[ligne][col]));
                edges.add(new Edge(v, v + width + 1, interest[ligne][col]));
            } else if ((v - 1) % width == 0) {//Bord gauche
                edges.add(new Edge(v, v + width, interest[ligne][col]));
                edges.add(new Edge(v, v + width + 1, interest[ligne][col]));
            } else {//Bord droit
                edges.add(new Edge(v, v + width, interest[ligne][col]));
                edges.add(new Edge(v, v + width - 1, interest[ligne][col]));
            }
        } else {
            if (v != width * height + 2) {//C'est que c'est dans la derniere ligne mais que c'est pas le sommet tout en bas
                for(int i = 0; i<width; i++){
                    edges.add(new Edge(v, width*height+1, interest[height-1][i]));
                }
            }
        }

        return edges;

    }

    public Iterable<Edge> prev(int v) {
        ArrayList<Edge> edges = new ArrayList();
        
        return edges;
    }


}
