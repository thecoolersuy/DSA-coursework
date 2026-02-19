package Q1A;
// 1. Algorithm Description (Algorithm Design)
// To find the maximum number of customer homes (points) that lie on a single straight line, we use a slope-based approach. 
// This ensures we identify every possible linear alignment on the 2D grid efficiently.

// Step 1: Initialization: Create a variable maxPoints to keep track of the highest number of collinear points found. 
// If the input list has 2 or fewer points, return the length of the list immediately.
// Step 2: Outer Iteration: Iterate through each point $P_i$ in the customer_locations array. 
// This point will act as a "reference" or "anchor."
// Step 3: Slope Mapping: For each anchor point $P_i$, initialize a Hash Map to store the frequency of slopes encountered with all other points $P_j$.
// Step 4: Slope Calculation: Calculate the vertical change ($dy = y_j - y_i$) and horizontal change ($dx = x_j - x_i$).
// Step 5: Normalization (GCD): To avoid precision issues with floating-point numbers (e.g., $0.3333$), calculate the Greatest Common Divisor (GCD) of $dy$ and $dx$.
// Divide both by the GCD to get a "reduced fraction" (e.g., $2/6$ becomes $1/3$).
// Step 6: Update Map: Use the reduced fraction (e.g., a string like "1/3") as a key in the Map. Increment its count for every point $P_j$ that shares this slope with $P_i$.
// Step 7: Global Update: For each anchor point, find the highest count in its Map and update the global maxPoints.
// Step 8: Final Result: Return the maxPoints value.


import java.util.HashMap;
import java.util.Map;

/**
 * Question 1 (a): Ideal Repeater Placement
 * This program identifies the maximum number of points on a 2D grid 
 * that lie on the same straight line to optimize signal coverage.
 */
public class RepeaterPlacement {

    public int maxPointsOnLine(int[][] points) {
        int n = points.length;
        if (n <= 2) return n;

        int maxPointsAcrossAllLines = 0;

        for (int i = 0; i < n; i++) {
            // Map stores slope as a String "dy/dx" to avoid precision errors
            Map<String, Integer> slopeMap = new HashMap<>();
            int localMax = 0;

            for (int j = i + 1; j < n; j++) {
                int dy = points[j][1] - points[i][1];
                int dx = points[j][0] - points[i][0];

                // Reduce slope by GCD to normalize it (e.g., 2/4 becomes 1/2)
                int commonDivisor = gcd(dy, dx);
                String normalizedSlope = (dy / commonDivisor) + "/" + (dx / commonDivisor);

                // Update the count for this specific line
                slopeMap.put(normalizedSlope, slopeMap.getOrDefault(normalizedSlope, 0) + 1);
                localMax = Math.max(localMax, slopeMap.get(normalizedSlope));
            }
            
            // localMax + 1 because we need to include the "anchor" point itself
            maxPointsAcrossAllLines = Math.max(maxPointsAcrossAllLines, localMax + 1);
        }

        return maxPointsAcrossAllLines;
    }

    // Helper method to find the Greatest Common Divisor
    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    public static void main(String[] args) {
        RepeaterPlacement optimizer = new RepeaterPlacement();

        // TEST CASE 1: Standard diagonal line
        int[][] locations1 = {{1, 1}, {2, 2}, {3, 3}};
        System.out.println("Test Case 1 Output: " + optimizer.maxPointsOnLine(locations1));

        // TEST CASE 2: Complex arrangement (Example from PDF)
        int[][] locations2 = {{1, 1}, {3, 2}, {5, 3}, {4, 1}, {2, 3}, {1, 4}};
        System.out.println("Test Case 2 Output: " + optimizer.maxPointsOnLine(locations2));

        // TEST CASE 3: Vertical and horizontal lines
        int[][] locations3 = {{1, 1}, {1, 2}, {1, 3}, {2, 1}, {3, 1}};
        System.out.println("Test Case 3 Output: " + optimizer.maxPointsOnLine(locations3));
    }
}

/* * OUTPUT (Testing & Validation):
 * -----------------------------------------------------------
 * Test Case 1 Output: 3
 * Test Case 2 Output: 4
 * Test Case 3 Output: 3
 * -----------------------------------------------------------
 */