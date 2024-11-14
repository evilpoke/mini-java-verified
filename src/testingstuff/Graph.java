package testingstuff;

public interface Graph {
    // Liefert den Knoten mit dem Index 'nodeIndex' zurück
    public Node getNode(int nodeIndex);

    // Liefert ein Array der Indices der Zielknoten von Kanten ab dem
    // Knoten mit dem Index 'fromNode' zurück
    public int[] getEdges(int fromNode);
}
