import java.util.*;

/**
 * Represents a complete pipe network system (like water pipes)
 * - Keeps track of all junctions (nodes) and connecting pipes
 * - Manages the connections between all components
 */
public class FlowNetwork {
    private final int nodeCount;       // Total number of junctions
    private int pipeCount;            // Total number of pipes
    private List<FlowEdge>[] pipes;    // Pipes connected to each junction

    /**
     * Create a new empty pipe network
     * @param nodeCount Number of junctions in the system (must be ≥ 1)
     */
    @SuppressWarnings("unchecked")
    public FlowNetwork(int nodeCount) {
        if (nodeCount < 1) {
            throw new IllegalArgumentException("Network must have at least 1 node");
        }
        
        this.nodeCount = nodeCount;
        this.pipeCount = 0;
        this.pipes = new ArrayList[nodeCount];
        
        // Initialize empty pipe lists for each junction
        for (int i = 0; i < nodeCount; i++) {
            pipes[i] = new ArrayList<FlowEdge>();
        }
    }

    /**
     * Add a new pipe connection between two junctions
     * @param pipe The pipe to add to the network
     * @throws IllegalArgumentException if pipe is null
     */
    public void addPipe(FlowEdge pipe) {
        if (pipe == null) {
            throw new IllegalArgumentException("Pipe cannot be null");
        }
        
        // Add reference to pipe at both connecting junctions
        pipes[pipe.from()].add(pipe);
        pipes[pipe.to()].add(pipe);
        pipeCount++;
    }

    /**
     * Get all pipes connected to a specific junction
     * @param node The junction index (0 ≤ node < nodeCount)
     * @return List of connected pipes (never null)
     */
    public Iterable<FlowEdge> connectedPipes(int node) {
        if (node < 0 || node >= nodeCount) {
            throw new IllegalArgumentException("Invalid node index");
        }
        return pipes[node];
    }

    /**
     * Get all unique pipes in the entire network
     * @return Collection of all pipes without duplicates
     */
    public Iterable<FlowEdge> allPipes() {
        List<FlowEdge> uniquePipes = new ArrayList<>(pipeCount);
        // Only add pipes when we see them from their 'from' junction
        for (int i = 0; i < nodeCount; i++) {
            for (FlowEdge pipe : pipes[i]) {
                if (pipe.from() == i) {  // This ensures no duplicates
                    uniquePipes.add(pipe);
                }
            }
        }
        return uniquePipes;
    }

    /**
     * @return Total number of junctions in the network
     */
    public int nodeCount() {
        return nodeCount;
    }

    /**
     * @return Total number of pipes in the network
     */
    public int pipeCount() {
        return pipeCount;
    }
}