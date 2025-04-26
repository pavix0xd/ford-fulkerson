/**
 * Main program to run the pipe network simulation
 * - Reads network from file
 * - Calculates maximum flow
 * - Shows results
 */
import java.io.IOException;
public class NetworkFlow {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide an input file");
            return;
        }
        
        try {
            // 1. Read the pipe system from file
            System.out.println("Reading network from: " + args[0]);
            FlowNetwork system = NetworkParser.readNetwork(args[0]);
            
            System.out.println("\nNetwork has:");
            System.out.println("- " + system.nodeCount() + " junctions");
            System.out.println("- " + system.pipeCount() + " pipes");
            
            // 2. Show all pipes (empty at start)
            System.out.println("\nAll pipes (start empty):");
            for (FlowEdge pipe : system.allPipes()) {
                System.out.println(pipe);
            }
            
            // 3. Calculate maximum flow
            int source = 0; // First node is always water source
            int sink = system.nodeCount() - 1; // Last node is drain
            System.out.println("\nCalculating maximum flow...");
            
            FordFulkerson calculator = new FordFulkerson(system, source, sink);
            
            // 4. Show final results
            System.out.println("\nMaximum possible flow: " + calculator.maxFlow());
            System.out.println("\nFinal pipe status:");
            for (FlowEdge pipe : system.allPipes()) {
                System.out.println(pipe);
            }
            
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}