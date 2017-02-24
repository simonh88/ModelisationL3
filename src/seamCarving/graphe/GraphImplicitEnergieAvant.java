package seamCarving.graphe;

import seamCarving.Pixel;
import seamCarving.SeamCarving;

import java.util.ArrayList;

public class GraphImplicitEnergieAvant implements Graph {

    int N = 0;
    Pixel[][] pixels;
    int width;
    int height;

    public GraphImplicitEnergieAvant(Pixel[][] pixels) {
        this.pixels = pixels;

        width = pixels[0].length;
        height = pixels.length;
        N = width * height + 2;

    }

    public int vertices() {
        return N;
    }

    public Iterable<Edge> next(int v) {
        ArrayList<Edge> edges = new ArrayList();
        int ligne = v / width;
        int col = v % width;
        if (v == width * height) {

            //Sommet tout en haut
            for (int i = 0; i < width; i++) {
                edges.add(new Edge(v, i, 0));
            }

        } else if (v >= ((height - 1) * width)) {
            //Derniere ligne
            //On ignore le dernier noeud
            if (v != width * height + 1) {
                edges.add(new Edge(v, width * height + 1, pixels));

            }

        } else if (col == 0) {

            //Bord gauche
            edges.add(new Edge(v, v + width, pixels));
            edges.add(new Edge(v, v + width + 1, pixels));

        } else if (col == width-1) {
            //Bord droit
            edges.add(new Edge(v, v + width, pixels));
            edges.add(new Edge(v, v + width - 1, pixels));

        } else {
            //Cas ou on est pas sur les bords
            edges.add(new Edge(v, v + width - 1, pixels));
            edges.add(new Edge(v, v + width, pixels));
            edges.add(new Edge(v, v + width + 1, pixels));
        }

        return edges;

    }

    public Iterable<Edge> prev(int v) {
        ArrayList<Edge> edges = new ArrayList();

        int ligne = v / width;
        int col = v % width;

        if (v == width * height) {

            //Sommet tout en haut
            //Pas de prev

        } else if (v < width) {//Premiere ligne

            edges.add(new Edge(width * height, v, 0));

        } else if (col == 0) {//Bord gauche

            edges.add(new Edge(v - width, v, pixels));
            edges.add(new Edge(v - width + 1, v, pixels));
            
        } else if (col == width - 1) {//Bord droit
            
            edges.add(new Edge(v - width, v, pixels));
            edges.add(new Edge(v - width - 1, v, pixels));
            
        } else {

            //Cas ou on est pas sur les bords ou c'est le dernier noeud
            if (v == width * height + 1) {//Dernier noeud
                for (int i = 0; i < width; i++) {
                    edges.add(new Edge(v - width - 1 + i, v, pixels));
                }
            }

            else  {

                //Pas bord
                //System.out.println("Milieu : " + v);
                edges.add(new Edge(v - width + 1, v, pixels));
                edges.add(new Edge(v - width, v, pixels));
                edges.add(new Edge(v - width - 1, v, pixels));
            }
        }
        
        return edges;
    }

}
