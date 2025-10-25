package main;

import model.*;
import algorithms.*;
import utils.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Optimization of City Transportation Network");

            // Файлы в src/test/resources/
            String inputFile = "src/test/resources/ass_3_input.json";
            String outputFile = "src/test/resources/ass_3_output.json";

            List<Graph> graphs = JsonIO.readGraphs(inputFile);
            System.out.println("Total graphs loaded: " + graphs.size());

            List<Map<String, Object>> allResults = new ArrayList<>();

            for (Graph graph : graphs) {
                System.out.println("\n=== Processing Graph " + graph.getId() + " ===");
                System.out.println("Vertices: " + graph.getVertexCount() + ", Edges: " + graph.getEdgeCount());

                Map<String, Object> prim = PrimAlgorithm.findMST(graph);
                Map<String, Object> kruskal = KruskalAlgorithm.findMST(graph);

                System.out.println("\n--- Prim's Algorithm ---");
                System.out.println("MST Edges: " + prim.get("mstEdges"));
                System.out.println("Total Cost: " + prim.get("totalCost"));
                System.out.println("Operations: " + prim.get("operationsCount"));
                System.out.println("Execution Time: " + String.format("%.2f", prim.get("executionTimeMs")) + " ms");

                System.out.println("\n--- Kruskal's Algorithm ---");
                System.out.println("MST Edges: " + kruskal.get("mstEdges"));
                System.out.println("Total Cost: " + kruskal.get("totalCost"));
                System.out.println("Operations: " + kruskal.get("operationsCount"));
                System.out.println("Execution Time: " + String.format("%.2f", kruskal.get("executionTimeMs")) + " ms");

                System.out.println("\n--- Comparison ---");
                boolean costsEqual = prim.get("totalCost").equals(kruskal.get("totalCost"));
                System.out.println("Total costs are " + (costsEqual ? "EQUAL" : "DIFFERENT"));
                System.out.println("Prim's operations: " + prim.get("operationsCount"));
                System.out.println("Kruskal's operations: " + kruskal.get("operationsCount"));

                Map<String, Object> result = new HashMap<>();
                result.put("graphId", graph.getId());
                result.put("vertices", graph.getVertexCount());
                result.put("edges", graph.getEdgeCount());
                result.put("prim", prim);
                result.put("kruskal", kruskal);

                allResults.add(result);
            }

            JsonIO.writeResults(allResults, outputFile);
            System.out.println("\nCompleted! Processed " + graphs.size() + " graphs");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}