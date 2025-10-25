package algorithms;

import model.*;
import java.util.*;

public class PrimAlgorithm {
    private static int operations;

    public static Map<String, Object> findMST(Graph graph) {
        long startTime = System.nanoTime();
        operations = 0;

        List<Edge> mstEdges = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));

        if (!graph.getNodes().isEmpty()) {
            String startNode = graph.getNodes().get(0);
            visited.add(startNode);
            operations++;

            for (Edge edge : graph.getEdges()) {
                operations++;
                if (edge.from.equals(startNode) || edge.to.equals(startNode)) {
                    pq.offer(edge);
                    operations++;
                }
            }
        }

        while (!pq.isEmpty() && visited.size() < graph.getVertexCount()) {
            Edge edge = pq.poll();
            operations++;

            String nextNode = null;
            if (visited.contains(edge.from) && !visited.contains(edge.to)) {
                nextNode = edge.to;
            } else if (visited.contains(edge.to) && !visited.contains(edge.from)) {
                nextNode = edge.from;
            }

            if (nextNode != null) {
                mstEdges.add(edge);
                visited.add(nextNode);
                operations += 2;

                for (Edge e : graph.getEdges()) {
                    operations++;
                    if ((e.from.equals(nextNode) && !visited.contains(e.to)) ||
                            (e.to.equals(nextNode) && !visited.contains(e.from))) {
                        pq.offer(e);
                        operations++;
                    }
                }
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
}