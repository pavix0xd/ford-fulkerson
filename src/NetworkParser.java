import java.io.*;
import java.util.*;

/**
 * Reads pipe network information from text files
 * - Converts text data into our pipe system objects
 */
public class NetworkParser {
    /**
     * Read network from file
     * @param filename File containing network data
     * @return Ready-to-use pipe system
     * @throws IOException If file can't be read
     */
    public static FlowNetwork readNetwork(String filename) throws IOException {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║   Reading Network Input File   ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.println("Loading file: " + filename);
        
        Scanner scanner = new Scanner(new File(filename));
        int nodes = scanner.nextInt();
        System.out.println("• Found " + nodes + " junctions (nodes)");
        
        FlowNetwork system = new FlowNetwork(nodes);
        int edgeCount = 0;
        
        System.out.println("\n Pipe Connections:");
        System.out.println("┌──────┬──────┬──────────┐");
        System.out.println("│ From │ To   │ Capacity │");
        System.out.println("├──────┼──────┼──────────┤");
        
        while (scanner.hasNextInt()) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            int capacity = scanner.nextInt();
            system.addPipe(new FlowEdge(from, to, capacity));
            System.out.printf("│ %4d │ %4d │ %8d │\n", from, to, capacity);
            edgeCount++;
        }
        
        System.out.println("└──────┴──────┴──────────┘");
        System.out.println("• Total pipes loaded: " + edgeCount);
        scanner.close();
        
        return system;
    }
}