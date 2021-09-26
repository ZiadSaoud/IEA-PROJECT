package SmartAgent;

public class Edge {
	
	private tile Dest;
	private int weight;
	
	public Edge(tile d, int weight) {
		Dest=d;
		this.weight=weight;	
	}
	public tile getDest() {
		return Dest;
	}
	public void setDest(tile dest) {
		Dest = dest;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
}
