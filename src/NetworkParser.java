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
        Scanner scanner = new Scanner(new File(filename));
        
        // First line = number of junctions
        int nodes = scanner.nextInt();
        FlowNetwork system = new FlowNetwork(nodes);
        
        // Each following line = one pipe
        while (scanner.hasNextInt()) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            int capacity = scanner.nextInt();
            system.addPipe(new FlowEdge(from, to, capacity));
        }
        
        scanner.close();
        return system;
    }
}