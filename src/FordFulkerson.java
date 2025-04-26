import java.util.*;
import java.util.stream.Collectors;

/**
 * Calculates maximum water flow through the pipe system
 * - Uses BFS to find paths with available capacity
 * - Gradually fills these paths to find total maximum flow
 */
public class FordFulkerson {
    private boolean[] visited;   // Track visited nodes
    private FlowEdge[] path;     // Remember the path taken
    private int maxFlow;         // Final maximum flow result

    /**
     * Calculate maximum flow
     * @param system The pipe network
     * @param source Starting point (water pump)
     * @param sink End point (drain)
     */
public FordFulkerson(FlowNetwork system, int source, int sink) {
    maxFlow = 0;
    int step = 1;
    
    System.out.println("\nAugmenting Paths Discovery:");
    System.out.println("┌───────┬────────────────────────────┬──────────┐");
    System.out.println("│ Step  │ Path                       │ Flow     │");
    System.out.println("├───────┼────────────────────────────┼──────────┤");
    
    while (findPath(system, source, sink)) {
        int flow = Integer.MAX_VALUE;
        List<Integer> pathNodes = new ArrayList<>();
        
        // Build path and find bottleneck
        for (int node = sink; node != source; node = path[node].otherEnd(node)) {
            pathNodes.add(node);
            flow = Math.min(flow, path[node].spaceLeft(node));
        }
        pathNodes.add(source);
        Collections.reverse(pathNodes);
        
        // Apply flow
        for (int node = sink; node != source; node = path[node].otherEnd(node)) {
            path[node].adjustFlow(node, flow);
        }
        
        maxFlow += flow;
        
        // Print path info
        String pathStr = pathNodes.stream()
            .map(Object::toString)
            .collect(Collectors.joining(" → "));
        System.out.printf("│ %5d │ %-26s │ %8d │\n", step++, pathStr, flow);
    }
    
    System.out.println("└───────┴────────────────────────────┴──────────┘");
    System.out.printf("Total Augmenting Paths Found: %d\n", (step-1));
}
    /**
     * Find an available path using BFS
     * @param system The pipe network
     * @param source Start node
     * @param sink End node
     * @return True if path found
     */
    private boolean findPath(FlowNetwork system, int source, int sink) {
        visited = new boolean[system.nodeCount()];
        path = new FlowEdge[system.nodeCount()];
        Queue<Integer> queue = new LinkedList<>();
        
        visited[source] = true;
        queue.add(source);
        
        while (!queue.isEmpty()) {
            int current = queue.remove();
            
            // Check all connected pipes
            for (FlowEdge pipe : system.connectedPipes(current)) {
                int neighbor = pipe.otherEnd(current);
                
                // If pipe has space and neighbor not visited
                if (pipe.spaceLeft(neighbor) > 0 && !visited[neighbor]) {
                    path[neighbor] = pipe;
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
        
        return visited[sink]; // Did we reach the end?
    }
    
    public int maxFlow() { return maxFlow; }
}