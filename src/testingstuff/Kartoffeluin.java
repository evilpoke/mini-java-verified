package testingstuff;

public class Kartoffeluin extends Thread {
    private Graph graph;
    private int startNode;
    private java.util.Random random = new java.util.Random();
    private int potato;

    public Kartoffeluin(Graph graph, int startNode, int potato) {
        this.graph = graph;
        this.startNode = startNode;
        this.potato = potato;
    }

    private void traverse(int nodeIndex) throws InterruptedException {
        Node node = graph.getNode(nodeIndex);

        System.out.println("Kartoffeluin " + Thread.currentThread().getId() + " betritt Knoten "
                + nodeIndex + " mit Kartoffel der Temperatur " + potato + "°C.");

        potato--;

        if (potato == 0) {
            System.out.println(
                    "Kartoffeluin " + Thread.currentThread().getId() + " isst die abgekühlte Kartoffel.");
        }

        synchronized (node) {
            boolean wait = false;
            if (potato == 0) {
                node.waiting++;
                wait = true;
            } else if (node.waiting > 0) {
                if (node.potato == 0) {
                    System.out.println(
                            "Kartoffeluin " + Thread.currentThread().getId() + " gibt die Kartoffel ab.");
                    wait = true;
                    node.potato = potato;
                    node.notify();
                } else {
                    System.out.println(
                            "Kartoffeluin " + Thread.currentThread().getId() + " tauscht die Kartoffel.");
                    int tmp = potato;
                    potato = node.potato;
                    node.potato = tmp;
                    node.notify();
                }
            }
            if (wait) {
                System.out.println("Kartoffeluin " + Thread.currentThread().getId() + " wartet.");
                while (node.potato == 0)
                    node.wait();
                node.waiting--;
                System.out.println(
                        "Kartoffeluin " + Thread.currentThread().getId() + " übernimmt die Kartoffel.");
                potato = node.potato;
                node.potato = 0;
            }
        }
        int[] edges = graph.getEdges(nodeIndex);
        if (edges.length == 0) {
            System.out
                    .println("Kein Ausweg, Kartoffeluin " + Thread.currentThread().getId() + " stirbt :/");
            return;
        }
        int edge = random.nextInt(edges.length);
        traverse(edges[edge]);
    }

    @Override
    public void run() {
        try {
            traverse(startNode);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
