import java.util.*;

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
        
        // Keep finding paths until none left
        while (findPath(system, source, sink)) {
            // Find how much we can push through this path
            int flow = Integer.MAX_VALUE;
            for (int node = sink; node != source; node = path[node].otherEnd(node)) {
                flow = Math.min(flow, path[node].spaceLeft(node));
            }
            
            // Adjust all pipes in the path
            for (int node = sink; node != source; node = path[node].otherEnd(node)) {
                path[node].adjustFlow(node, flow);
            }
            
            maxFlow += flow;
            System.out.println("Found path adding " + flow + 
                             ", Total flow now: " + maxFlow);
        }
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