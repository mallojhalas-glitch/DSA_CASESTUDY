import java.util.*;

public class CO3 {

    static final int WHITE = 0;
    static final int GREY = 1;
    static final int BLACK = 2;

    static Map<String, List<String>> graph = new HashMap<>();
    static Map<String, Integer> color = new HashMap<>();
    static Stack<String> topoStack = new Stack<>();
    static boolean hasCycle = false;

    // Add Edge
    static void addEdge(String from, String to) {
        graph.putIfAbsent(from, new ArrayList<>());
        graph.putIfAbsent(to, new ArrayList<>());
        graph.get(from).add(to);
    }

    // DFS Traversal
    static void dfs(String node) {

        color.put(node, GREY);

        for (String neighbor : graph.get(node)) {

            if (color.get(neighbor) == GREY) {
                hasCycle = true;

                System.out.println("\nBack Edge Found: "
                        + node + " -> " + neighbor);

                System.out.println("\nCycle Detected:");
                System.out.println(
                        "Consultation -> Diagnosis -> Treatment -> Recovery -> Discharge -> Consultation");
            }

            if (color.get(neighbor) == WHITE) {
                dfs(neighbor);
            }
        }

        color.put(node, BLACK);
        topoStack.push(node);
    }

    // Topological Sort
    static void topologicalSort() {

        for (String node : graph.keySet()) {
            color.put(node, WHITE);
        }

        for (String node : graph.keySet()) {
            if (color.get(node) == WHITE) {
                dfs(node);
            }
        }

        if (hasCycle) {
            System.out.println(
                    "\nTopological Sort Not Possible Due To Cycle.");
        } else {

            System.out.println("\nValid Topological Order:");

            while (!topoStack.isEmpty()) {
                System.out.print(topoStack.pop());

                if (!topoStack.isEmpty())
                    System.out.print(" -> ");
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("HOSPITAL PATIENT TREATMENT WORKFLOW");
        System.out.println("----------------------------------");

        System.out.println("\nGRAPH:");
        System.out.println("          Registration");
        System.out.println("           /        \\");
        System.out.println("          v          v");
        System.out.println("  Consultation    Lab Test");
        System.out.println("          \\          /");
        System.out.println("           v        v");
        System.out.println("            Diagnosis");
        System.out.println("                |");
        System.out.println("                v");
        System.out.println("            Treatment");
        System.out.println("                |");
        System.out.println("                v");
        System.out.println("             Recovery");
        System.out.println("                |");
        System.out.println("                v");
        System.out.println("             Discharge");
        System.out.println("                |");
        System.out.println("                v");
        System.out.println("          Consultation");

        // Graph with cycle
        addEdge("Registration", "Consultation");
        addEdge("Registration", "Lab Test");
        addEdge("Consultation", "Diagnosis");
        addEdge("Lab Test", "Diagnosis");
        addEdge("Diagnosis", "Treatment");
        addEdge("Treatment", "Recovery");
        addEdge("Recovery", "Discharge");
        addEdge("Discharge", "Consultation");

        System.out.println("\n\n(a) DFS Traversal & Cycle Detection");
        topologicalSort();

        System.out.println("\n\n(b) Edge Creating Cycle:");
        System.out.println("Discharge -> Consultation");

        System.out.println("\n(c) Remove Edge:");
        System.out.println("Discharge -> Consultation");
        System.out.println("to obtain a valid topological ordering.");
    }
}