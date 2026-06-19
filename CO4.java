import java.util.*;

public class WasteSenseDijkstra {

    static final int V = 6;
    static String[] locations = {
            "CC", "Bin1", "Bin2", "Bin3", "Bin4", "RP"
    };

    static int minDistance(int dist[], boolean visited[]) {
        int min = Integer.MAX_VALUE, minIndex = -1;

        for (int v = 0; v < V; v++) {
            if (!visited[v] && dist[v] < min) {
                min = dist[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    static void dijkstra(int graph[][], int source) {

        int dist[] = new int[V];
        int parent[] = new int[V];
        boolean visited[] = new boolean[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        dist[source] = 0;

        for (int count = 0; count < V - 1; count++) {

            int u = minDistance(dist, visited);
            visited[u] = true;

            for (int v = 0; v < V; v++) {

                if (!visited[v]
                        && graph[u][v] != 0
                        && dist[u] != Integer.MAX_VALUE
                        && dist[u] + graph[u][v] < dist[v]) {

                    dist[v] = dist[u] + graph[u][v];
                    parent[v] = u;
                }
            }
        }

        System.out.println("\nShortest distances from CC:");
        System.out.println("-----------------------------------------");
        System.out.println("Node\tDistance(km)\tPath");

        for (int i = 0; i < V; i++) {
            System.out.print(locations[i] + "\t" + dist[i] + "\t\t");
            printPath(parent, i);
            System.out.println();
        }
    }

    static void printPath(int parent[], int j) {

        if (parent[j] == -1) {
            System.out.print(locations[j]);
            return;
        }

        printPath(parent, parent[j]);
        System.out.print(" -> " + locations[j]);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        /*
           Graph from the example image

                  Bin1
                /  |  \
               4   2   7
             Bin2 Bin3 Bin4
              | \   |   |
              6  1  3   4
              |   \ |   |
              CC----5---RP
                   2
        */

        int graph[][] = {

        //CC Bin1 Bin2 Bin3 Bin4 RP
          {0,  0,   6,   2,   0,  5}, // CC
          {0,  0,   4,   2,   7,  0}, // Bin1
          {6,  4,   0,   1,   0,  0}, // Bin2
          {2,  2,   1,   0,   3,  0}, // Bin3
          {0,  7,   0,   3,   0,  4}, // Bin4
          {5,  0,   0,   0,   4,  0}  // RP
        };

        int choice;

        do {

            System.out.println("\n=== WasteSense Dijkstra System ===");
            System.out.println("1. Dijkstra's Shortest Path");
            System.out.println("2. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    dijkstra(graph, 0); // Source = CC
                    break;

                case 2:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 2);

        sc.close();
    }
}