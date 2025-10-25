package algorithms;

import model.*;
import java.util.*;

public class KruskalAlgorithm {
    private static int operations;
    private static Map<String, String> parent;
    private static Map<String, Integer> rank;

    public static Map<String, Object> findMST(Graph graph) {
        long startTime = System.nanoTime();
        operations = 0;

        List<Edge> mstEdges = new ArrayList<>();
        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());

        sortedEdges.sort(Comparator.comparingInt(e -> e.weight));
        operations += sortedEdges.size();

        parent = new HashMap<>();
        rank = new HashMap<>();
        for (String node : graph.getNodes()) {
            parent.put(node, node);
            rank.put(node, 0);
            operations++;
        }

        for (Edge edge : sortedEdges) {
            operations++;
            String root1 = find(edge.from);
            String root2 = find(edge.to);

            if (!root1.equals(root2)) {
                mstEdges.add(edge);
                union(root1, root2);
                operations++;
            }

            if (mstEdges.size() == graph.getVertexCount() - 1) {
                break;
            }
        }

        long endTime = System.nanoTime();
        int totalCost = mstEdges.stream().mapToInt(e -> e.weight).sum();
        double executionTimeMs = (endTime - startTime) / 1_000_000.0;

        Map<String, Object> result = new HashMap<>();
        result.put("mstEdges", mstEdges);
        result.put("totalCost", totalCost);
        result.put("operationsCount", operations);
        result.put("executionTimeMs", executionTimeMs);

        return result;
    }

    private static String find(String node) {
        operations++;
        if (!parent.get(node).equals(node)) {
            parent.put(node, find(parent.get(node)));
            operations++;
        }
        return parent.get(node);
    }

    private static void union(String x, String y) {
        operations++;
        String rootX = find(x);
        String rootY = find(y);

        if (rank.get(rootX) < rank.get(rootY)) {
            parent.put(rootX, rootY);
        } else if (rank.get(rootX) > rank.get(rootY)) {
            parent.put(rootY, rootX);
        } else {
            parent.put(rootY, rootX);
            rank.put(rootX, rank.get(rootX) + 1);
        }
        operations += 3;
    }
}