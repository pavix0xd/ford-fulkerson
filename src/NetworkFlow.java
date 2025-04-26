/**
 * Main program to run the pipe network simulation
 * - Reads network from file
 * - Calculates maximum flow
 * - Shows results
 */
import java.io.IOException;
public class NetworkFlow {
    public static void main(String[] args) {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║     Network Flow Calculator    ║");
        System.out.println("╚════════════════════════════════╝");
        
        if (args.length == 0) {
            System.out.println("\n Error: Please provide an input file");
            System.out.println("Usage: java NetworkFlow <filename>");
            return;
        }
        
        try {
            FlowNetwork system = NetworkParser.readNetwork(args[0]);
            
            System.out.println("\n╔════════════════════════════════╗");
            System.out.println("║        Network Summary        ║");
            System.out.println("╚════════════════════════════════╝");
            System.out.printf(" %-20s: %d\n", "Total Junctions", system.nodeCount());
            System.out.printf(" %-20s: %d\n", "Total Pipes", system.pipeCount());
            
            System.out.println("\n Initial Pipe Status:");
            printPipeStatus(system);
            
            int source = 0;
            int sink = system.nodeCount() - 1;
            System.out.println("\n Calculating maximum flow...");
            System.out.println("• Source: Node " + source);
            System.out.println("• Sink: Node " + sink);
            
            FordFulkerson calculator = new FordFulkerson(system, source, sink);
            
            System.out.println("\n╔════════════════════════════════╗");
            System.out.println("║         Final Results         ║");
            System.out.println("╚════════════════════════════════╝");
            System.out.printf(" Maximum Flow: %d\n", calculator.maxFlow());
            
            System.out.println("\n Final Pipe Status:");
            printPipeStatus(system);
            
        } catch (IOException e) {
            System.out.println("\n Error reading file: " + e.getMessage());
        }
    }
    
    private static void printPipeStatus(FlowNetwork system) {
        System.out.println("┌──────┬──────┬──────────┬────────────┐");
        System.out.println("│ From │ To   │ Capacity │ Flow       │");
        System.out.println("├──────┼──────┼──────────┼────────────┤");
        for (FlowEdge pipe : system.allPipes()) {
            System.out.printf("│ %4d │ %4d │ %8d │ %4d/%-5d │\n",
                pipe.from(), pipe.to(), 
                pipe.capacity(), pipe.flow(), pipe.capacity());
        }
        System.out.println("└──────┴──────┴──────────┴────────────┘");
    }
}