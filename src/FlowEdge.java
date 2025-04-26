/**
 * Represents one pipe in our network system
 * - Has a start point (from) and end point (to)
 * - Has a maximum capacity (like pipe width)
 * - Tracks current water flow through it
 */
public class FlowEdge {
    private final int from;      // Starting point
    private final int to;       // Ending point
    private final int maxFlow;   // Maximum allowed flow
    private int currentFlow;     // Current actual flow

    /**
     * Create a new pipe
     * @param from Start node
     * @param to End node
     * @param maxFlow Pipe capacity (maximum flow possible)
     */
    public FlowEdge(int from, int to, int maxFlow) {
        this.from = from;
        this.to = to;
        this.maxFlow = maxFlow;
        this.currentFlow = 0; // Starts empty
    }

    // Basic getters
    public int from() { return from; }
    public int to() { return to; }
    public int capacity() { return maxFlow; }
    public int flow() { return currentFlow; }

    /**
     * Find the other end of the pipe
     * @param oneEnd Either start or end point
     * @return The opposite end of the pipe
     */
    public int otherEnd(int oneEnd) {
        if (oneEnd == from) return to;
        else if (oneEnd == to) return from;
        else throw new IllegalArgumentException("Not a valid pipe end");
    }

    /**
     * Check remaining space in the pipe toward a direction
     * @param towardNode Which direction we're checking
     * @return Available space for more flow
     */
    public int spaceLeft(int towardNode) {
        if (towardNode == from) return currentFlow; // Can reduce flow
        else if (towardNode == to) return maxFlow - currentFlow; // Can increase flow
        else throw new IllegalArgumentException("Invalid direction");
    }

    /**
     * Adjust the flow in the pipe
     * @param towardNode Which direction to push flow
     * @param amount How much to adjust
     */
    public void adjustFlow(int towardNode, int amount) {
        if (towardNode == from) currentFlow -= amount; // Reduce flow
        else if (towardNode == to) currentFlow += amount; // Increase flow
        else throw new IllegalArgumentException("Invalid direction");
    }

    // Show pipe status
    @Override
    public String toString() {
        return from + "->" + to + " " + currentFlow + "/" + maxFlow;
    }
}