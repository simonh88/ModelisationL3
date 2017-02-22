package seamCarving.graphe;


public class Edge
{
   int from;
   int to;
   int cost;
   public Edge(int x, int y, int cost) {
		this.from = x;
		this.to = y;
		this.cost = cost;
   }

   public Edge(int x, int y, int[][] pixels) {
       this.from = x;
       this.to = y;
       setupEnergieAvant(pixels);
   }

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String toString() {
   	return "to: " + to + " | from: " + from + " | cost: " + cost;
	}

	public void setupEnergieAvant(int[][] pixels) {


   		int height = pixels.length;
   		int width = pixels[0].length;

   		// On veut calculer le cout de l'arete a partir de l'image

        // On connait le sommet de depart et le sommet d'arrive

        // On calcul les (i,j) correspondant à e.from et e.to
        int from_i = from % width;
        int from_j = from / width;
        int to_i = to % width;
        int to_j = to / width;

        /* Les trois cas :
        1 — Le sommet (i, j) au sommet (i + 1, j) avec comme cout |M[i, j + 1] − M[i, j − 1]|
        2 — Le sommet (i, j) au sommet (i + 1, j + 1) avec comme cout |M[i, j + 1] − M[i + 1, j]|
        3 — Le sommet (i, j) au sommet (i + 1, j − 1) avec comme cout |M[i, j − 1] − M[i + 1, j]|

        ---> les i correspondent aux y ...

        */

        // J'ai inversé les i et j
        // 1 + cas des arête menant au dernier sommet
        if ( ( from_j == to_j - 1 && from_i == to_i ) || (to == width * height + 1) ) {
            cost = Math.abs(valEA(from_i + 1, from_j, pixels) - valEA(from_i - 1, from_j, pixels));
        }

        // 2
        else if (from_j == to_j - 1 && from_i == to_i - 1) {
            cost = Math.abs(valEA(from_i+1, from_j, pixels) - valEA(from_i, from_j + 1, pixels));
        }

        // 3
        else if (from_j == to_j - 1 && from_i == to_i + 1) {
            cost = Math.abs(valEA(from_i -1, from_j , pixels) - valEA(from_i , from_j +1, pixels));
        }

        else {
            throw new RuntimeException("Arête invalide " + from + " -> " + to);
        }


	}

	private int valEA(int i, int j, int[][] pixels) {
        int height = pixels.length;
        int width = pixels[0].length;

        // On pose M[i, j] = 0 si le pixel n’existe pas (si
        // on déborde sur la gauche ou la droite)
        if (i < 0 || i >= width ) return 0;
        if (j < 0 || j >= height ) return 0;

        return pixels[j][i];
    }
}
