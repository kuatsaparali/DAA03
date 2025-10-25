Analytical Report: Optimization of City Transportation Network (Minimum Spanning Tree)

1. Introduction

Project Objective

Implementation and comparison of Prim's and Kruskal's algorithms for finding the Minimum Spanning Tree (MST) in a graph representing a city transportation network.

Problem Statement

The city administration plans to construct roads between districts such that:

Each district is reachable from any other district
The total construction cost is minimized
2. Methodology

2.1. Data Model

The graph is represented as:

Vertices: City districts (A, B, C, D, E)
Edges: Potential roads between districts
Edge weight: Road construction cost
2.2. Implemented Algorithms

Prim's Algorithm

Type: Greedy algorithm
Strategy: Builds tree from arbitrary vertex, at each step adds minimum edge connecting tree to new vertex
Complexity: O(E log V) with priority queue
Memory: O(V²)
Kruskal's Algorithm

Type: Greedy algorithm
Strategy: Sorts all edges by weight and sequentially adds edges that don't create cycles
Complexity: O(E log E) for sorting + O(E α(V)) for union-find
Memory: O(E)
2.3. Project Structure

text
DAA3/
├── src/main/java/
│   ├── main/Main.java
│   ├── algorithms/
│   │   ├── PrimAlgorithm.java
│   │   └── KruskalAlgorithm.java
│   ├── model/
│   │   ├── Graph.java
│   │   └── Edge.java
│   └── utils/JsonIO.java
├── src/test/resources/
│   ├── ass_3_input.json
│   └── ass_3_output.json
└── pom.xml
3. Execution Results

3.1. Graph 1 (5 vertices, 7 edges)

Input Data:

Vertices: A, B, C, D, E
Edges:

A-B(4), A-C(3), B-C(2), B-D(5), C-D(7), C-E(8), D-E(6)
Results:

Prim's Algorithm:

MST Edges: A-C(3), B-C(2), B-D(5), D-E(6)
Total Cost: 16
Operations: 56
Execution Time: 1.06 ms
Kruskal's Algorithm:

MST Edges: B-C(2), A-C(3), B-D(5), D-E(6)
Total Cost: 16
Operations: 61
Execution Time: 0.26 ms
3.2. Graph 2 (4 vertices, 5 edges)

Input Data:

Vertices: A, B, C, D
Edges:

A-B(1), A-C(4), B-C(2), C-D(3), B-D(5)
Results:

Prim's Algorithm:

MST Edges: A-B(1), B-C(2), C-D(3)
Total Cost: 6
Operations: 45
Execution Time: 0.03 ms
Kruskal's Algorithm:

MST Edges: A-B(1), B-C(2), C-D(3)
Total Cost: 6
Operations: 24
Execution Time: 0.03 ms
4. Algorithm Comparison Analysis

4.1. Operational Efficiency

Graph	Prim's Algorithm	Kruskal's Algorithm	Difference
Graph 1	56 operations	61 operations	+5 operations for Kruskal
Graph 2	45 operations	24 operations	+21 operations for Prim
Observation: Operational efficiency depends on graph structure. For dense graphs, Prim is more efficient; for sparse graphs, Kruskal performs better.

4.2. Execution Time Performance

Graph	Prim's Algorithm	Kruskal's Algorithm
Graph 1	1.06 ms	0.26 ms
Graph 2	0.03 ms	0.03 ms
Observation: Kruskal's algorithm showed better execution time for larger graphs.

4.3. Result Correctness

Both algorithms found MSTs with identical total costs for both graphs, confirming implementation correctness.

5. Technical Challenges and Solutions

5.1. JSON File Reading Issues

Problem: File not found due to incorrect path
Solution: Using full path src/test/resources/ass_3_input.json
5.2. JSON Data Parsing

Problem: Initially only one graph was read from two available
Solution: Using org.json library for proper JSON structure parsing
5.3. Data Structure Optimization

Problem: Complexity with wrapper classes
Solution: Simplified structure to basic Graph and Edge classes using Map for results
6. Conclusions and Recommendations

6.1. Comparative Advantages

Prim's Algorithm is preferable when:

Graph is dense (E ≈ V²)
Maximum performance on dense graphs is required
Working with adjacency matrix
Kruskal's Algorithm is preferable when:

Graph is sparse (E ≪ V²)
Implementation simplicity is important
Working with edge lists
Stable performance is required
6.2. Urban Planning Recommendations

For dense urban areas (many districts, many possible roads) - Prim's algorithm is recommended
For sparse structures (districts far apart, few roads) - Kruskal's algorithm is more efficient
For large cities - consider memory requirements: Prim requires O(V²), Kruskal requires O(E)
6.3. Overall Conclusion

Both algorithms successfully solve the MST problem and show identical result correctness. The choice between them should be based on:

Transportation network density
Performance requirements
Available computational resources
Implementation and maintenance simplicity
Kruskal's algorithm demonstrated more stable execution times, while Prim's algorithm can be more operationally efficient for certain graph types. For typical city transportation networks with mixed density, Kruskal's algorithm offers a good balance of performance and implementation simplicity.
