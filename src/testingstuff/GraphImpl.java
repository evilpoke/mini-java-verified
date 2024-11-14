package testingstuff;


public class GraphImpl implements Graph {
    private Node[] nodes;
    private int[][] edges;

    public Node getNode(int nodeIndex) {
        return nodes[nodeIndex];
    }

    public int[] getEdges(int fromIndex) {
        return edges[fromIndex];
    }

    public GraphImpl(Node[] nodes, int[][] edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public void dot() {
        System.out.println("digraph G {");
        for (int i = 0; i < nodes.length; i++) {
            System.out.println("  " + i + ";");
            int[] edgesFromNode = edges[i];
            for (Integer j : edgesFromNode) {
                System.out.println("  " + i + " -> " + j + ";");
            }
        }
        System.out.println("}");
    }
}
