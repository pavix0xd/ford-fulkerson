# Network Flow Algorithm Implementation

This Java project implements a maximum flow algorithm (Ford-Fulkerson) for analyzing network flow problems, like water pipe systems or transportation networks.

## Features

- Flow network representation with nodes and capacity-limited edges
- File parser for network configurations
- Ford-Fulkerson algorithm implementation with BFS
- Detailed flow calculation with step-by-step output

## How to Use

 **Compile the project**:
   ```bash
   javac src/*.java -d src/

   java -cp src NetworkFlow path/to/input_file.txt
```

Example input file format:

4       # Number of nodes (0-3)
0 1 6   # Edge from 0→1 with capacity 6
0 2 4   # Edge from 0→2 with capacity 4
1 2 2   # Edge from 1→2 with capacity 2
1 3 3   # Edge from 1→3 with capacity 3
2 3 5   # Edge from 2→3 with capacity 5


network-flow/
├── src/
│   ├── FlowEdge.java       # Pipe/edge implementation
│   ├── FlowNetwork.java    # Network system
│   ├── FordFulkerson.java  # Max flow algorithm
│   ├── NetworkParser.java  # File reader
│   ├── NetworkFlow.java    # Main program
│   └── test_input.txt     # Sample input
├── .gitignore
└── README.md

## Requirements

Java JDK 8 or later
