package testingstuff;

public class ProgrammKatoffel {



        public static void main(String[] args) throws InterruptedException {
            int nodeCount = 9;
            Node[] nodes = new Node[nodeCount];

            int[][] edges = new int[nodeCount][];
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = new Node();
            }

            edges[0] = new int[3];
            edges[0][0] = 1;
            edges[0][1] = 3;
            edges[0][2] = 6;
            edges[1] = new int[1];
            edges[1][0] = 2; // X
            edges[2] = new int[1];
            edges[2][0] = 7; // X
            edges[3] = new int[1];
            edges[3][0] = 2;
            edges[4] = new int[2];
            edges[4][0] = 3;
            edges[4][1] = 6;
            edges[5] = new int[1];
            edges[5][0] = 4;
            edges[6] = new int[2];
            edges[6][0] = 5;
            edges[6][1] = 7;
            edges[7] = new int[1];
            edges[7][0] = 8; // X
            edges[8] = new int[1];
            edges[8][0] = 5;

            GraphImpl graph = new GraphImpl(nodes, edges);

            Kartoffeluin k1 = new Kartoffeluin(graph, 0, 10);
            k1.start();
            Kartoffeluin k2 = new Kartoffeluin(graph, 8, 3);
            k2.start();
            Kartoffeluin k3 = new Kartoffeluin(graph, 2, 5);
            k3.start();

           k1.join();
//            k2.join();
 //           k3.join();


    }

}
