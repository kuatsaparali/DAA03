package utils;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class JsonIO {

    public static List<Graph> readGraphs(String filename) {
        List<Graph> graphs = new ArrayList<>();

        try {
            FileReader reader = new FileReader(filename);
            JSONObject json = new JSONObject(new JSONTokener(reader));

            JSONArray graphsArray = json.getJSONArray("graphs");
            System.out.println("Found " + graphsArray.length() + " graphs in JSON");

            for (int i = 0; i < graphsArray.length(); i++) {
                JSONObject graphObj = graphsArray.getJSONObject(i);
                int id = graphObj.getInt("id");

                JSONArray nodesArray = graphObj.getJSONArray("nodes");
                List<String> nodes = new ArrayList<>();
                for (int j = 0; j < nodesArray.length(); j++) {
                    nodes.add(nodesArray.getString(j));
                }

                JSONArray edgesArray = graphObj.getJSONArray("edges");
                List<Edge> edges = new ArrayList<>();
                for (int j = 0; j < edgesArray.length(); j++) {
                    JSONObject edgeObj = edgesArray.getJSONObject(j);
                    String from = edgeObj.getString("from");
                    String to = edgeObj.getString("to");
                    int weight = edgeObj.getInt("weight");
                    edges.add(new Edge(from, to, weight));
                }

                graphs.add(new Graph(id, nodes, edges));
                System.out.println("Graph " + id + ": " + nodes.size() + " nodes, " + edges.size() + " edges");
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
            e.printStackTrace();
        }

        return graphs;
    }

    public static void writeResults(List<Map<String, Object>> allResults, String filename) {
        try {
            JSONObject output = new JSONObject();
            JSONArray resultsArray = new JSONArray();

            for (Map<String, Object> result : allResults) {
                JSONObject resultObj = new JSONObject();
                resultObj.put("graph_id", result.get("graphId"));

                JSONObject inputStats = new JSONObject();
                inputStats.put("vertices", result.get("vertices"));
                inputStats.put("edges", result.get("edges"));
                resultObj.put("input_stats", inputStats);

                @SuppressWarnings("unchecked")
                Map<String, Object> prim = (Map<String, Object>) result.get("prim");
                resultObj.put("prim", createAlgorithmResult(prim));

                @SuppressWarnings("unchecked")
                Map<String, Object> kruskal = (Map<String, Object>) result.get("kruskal");
                resultObj.put("kruskal", createAlgorithmResult(kruskal));

                resultsArray.put(resultObj);
            }

            output.put("results", resultsArray);

            FileWriter writer = new FileWriter(filename);
            writer.write(output.toString(2));
            writer.close();

            System.out.println("Results written to: " + filename);

        } catch (Exception e) {
            System.out.println("Error writing JSON file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static JSONObject createAlgorithmResult(Map<String, Object> algorithmResult) {
        JSONObject result = new JSONObject();

        JSONArray mstEdges = new JSONArray();
        @SuppressWarnings("unchecked")
        List<Edge> edges = (List<Edge>) algorithmResult.get("mstEdges");

        for (Edge edge : edges) {
            JSONObject edgeObj = new JSONObject();
            edgeObj.put("from", edge.from);
            edgeObj.put("to", edge.to);
            edgeObj.put("weight", edge.weight);
            mstEdges.put(edgeObj);
        }
        result.put("mst_edges", mstEdges);

        result.put("total_cost", algorithmResult.get("totalCost"));
        result.put("operations_count", algorithmResult.get("operationsCount"));
        result.put("execution_time_ms", algorithmResult.get("executionTimeMs"));

        return result;
    }
}