import java.util.*;

/**
 * Task 6 - Question 2: Safest Path Algorithm
 * Adapting Dijkstra's using negative log transformation.
 */
public class EmergencyLogistics {
    static class Edge {
        String to;
        double probability;
        Edge(String to, double prob) { this.to = to; this.probability = prob; }
    }

    public void findSafestPath(Map<String, List<Edge>> graph, String source) {
        Map<String, Double> dist = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        // Priority Queue to minimize the sum of -log(p)
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.weight));

        for (String node : graph.keySet()) dist.put(node, Double.POSITIVE_INFINITY);
        
        dist.put(source, 0.0);
        pq.add(new Node(source, 0.0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            for (Edge edge : graph.getOrDefault(current.name, new ArrayList<>())) {
                // RELAX Operation modified for transformed weights
                double weight = -Math.log(edge.probability); 
                if (dist.get(current.name) + weight < dist.get(edge.to)) {
                    dist.put(edge.to, dist.get(current.name) + weight);
                    parent.put(edge.to, current.name);
                    pq.add(new Node(edge.to, dist.get(edge.to)));
                }
            }
        }
        
        // Output results (Example: PH)
        double maxProb = Math.exp(-dist.get("PH"));
        System.out.println("Maximum Safety Probability to Patan Hospital: " + maxProb);
    }

    static class Node {
        String name; double weight;
        Node(String name, double weight) { this.name = name; this.weight = weight; }
    }
}
/* OUTPUT COMMENT: 
 * If KTM->JA is 0.9 and JA->PH is 0.95, 
 * the sum of weights is -log(0.9) + -log(0.95) = 0.105 + 0.051 = 0.156.
 * The algorithm finds path with minimum 0.156, which is exp(-0.156) = 0.855 probability.
 */


