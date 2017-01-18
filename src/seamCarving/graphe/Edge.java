package seamCarving.graphe;

public class Edge
{
   int from;
   int to;
   int cost;
   public Edge(int x, int y, int cost)
	 {
		this.from = x;
		this.to = y;
		this.cost = cost;
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
}
