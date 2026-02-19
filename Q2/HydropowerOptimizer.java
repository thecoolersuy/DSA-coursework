package Q2;

// Algorithm Description (Algorithm Design)

// To find the maximum power generation from connected plants, we use a Recursive Depth-First Search (DFS) approach.

// Step 1: Global Maximum: We maintain a global variable maxPower initialized to a very small number (Negative Infinity) to track the best path found across the entire tree.
// Step 2: Recursive Post-order Traversal: We visit each node (hydropower plant) starting from the bottom (tributaries).
// Step 3: Calculating Child Contribution: For each plant, we calculate the maximum power it can receive from its left and right sub-trees.Crucial 
// Logic: If a sub-tree has a negative net value (environmental cost outweighing power), we treat its contribution as 0, meaning we choose to exclude that entire branch.
// Step 4: Local Path Evaluation: At each node, we check the "Local Path Sum": Current Node Value + Left Max + Right Max. This represents a sequence that passes through the current node and connects both tributaries. 
// If this sum is higher than our maxPower, we update the global record.
// Step 5: Returning to Parent: To allow the sequence to continue upstream, a node can only pass back the value of its best single branch (either left or right) plus its own value.
// Efficiency: The algorithm visits each node exactly once, resulting in a Time Complexity of $O(N)$, where $N$ is the number of plants.
/**
 * Question 2: Hydropower Plant Cascade Efficiency
 * Finding the Maximum Path Sum in a tree-like river network.
 */

// Definition for a binary tree node (Hydropower Plant)
class PlantNode {
    int val;
    PlantNode left;
    PlantNode right;
    PlantNode(int val) { this.val = val; }
}

public class HydropowerOptimizer {
    private int maxPower = Integer.MIN_VALUE;

    public int getMaxPower(PlantNode root) {
        calculateNodeMax(root);
        return maxPower;
    }

    private int calculateNodeMax(PlantNode node) {
        if (node == null) return 0;

        // Recursive call: only add positive contributions from children
        int leftGain = Math.max(calculateNodeMax(node.left), 0);
        int rightGain = Math.max(calculateNodeMax(node.right), 0);

        // Path sum if this node is the "highest" point in the sequence
        int currentPathSum = node.val + leftGain + rightGain;

        // Update global maximum if current path is better
        maxPower = Math.max(maxPower, currentPathSum);

        // Return the best single-branch contribution to the parent node
        return node.val + Math.max(leftGain, rightGain);
    }

    public static void main(String[] args) {
        HydropowerOptimizer optimizer = new HydropowerOptimizer();

        // TEST CASE 1: Standard Cascade (Example 1)
        // Root: 1, Left: 2, Right: 3
        PlantNode root1 = new PlantNode(1);
        root1.left = new PlantNode(2);
        root1.right = new PlantNode(3);
        System.out.println("Test Case 1 Output: " + optimizer.getMaxPower(root1));

        // TEST CASE 2: High Environmental Costs (Example 2)
        // Root: -10, Left: 9, Right: 20 -> (Right's children: 15, 7)
        optimizer.maxPower = Integer.MIN_VALUE; // Reset for new test
        PlantNode root2 = new PlantNode(-10);
        root2.left = new PlantNode(9);
        root2.right = new PlantNode(20);
        root2.right.left = new PlantNode(15);
        root2.right.right = new PlantNode(7);
        System.out.println("Test Case 2 Output: " + optimizer.getMaxPower(root2));
    }
}

/* * OUTPUT (Testing & Validation):
 * -----------------------------------------------------------
 * Test Case 1 Output: 6
 *
 * Test Case 2 Output: 42

 * The site with -10 cost is correctly ignored.
 * -----------------------------------------------------------
 */