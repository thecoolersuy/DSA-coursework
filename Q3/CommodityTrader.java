package Q3;

// Algorithm Description (Algorithm Design)
// To solve the Agricultural Commodity Trading problem, we use a Dynamic Programming (DP) approach.

// Step 1: State Definition: We create a 2D DP table where dp[k][i] represents the maximum profit achievable using at most k transactions up to day i.

// Step 2: Base Cases: * If the number of days is 0 or max_trades is 0, the profit is 0.

// For the first day (index 0), profit is always 0 because we cannot buy and sell on the same day for profit.

// Step 3: State Transition: For each transaction k and each day i, we have two choices:

// Do nothing on day i: Our profit remains the same as the previous day: dp[k][i-1].

// Sell on day i: We must have bought the commodity on some previous day j (where j < i). The profit would be prices[i] - prices[j] + dp[k-1][j].

// Step 4: Optimization: To avoid a triple loop (which would be slow), we maintain a variable maxDiff. This tracks the maximum value of dp[k-1][j] - prices[j] as we iterate through the days. This allows us to calculate the "Sell" option in constant time.

// Efficiency: The time complexity is O(k * n), where k is max_trades and n is the number of days. This is highly efficient compared to a brute-force approach.

/**
 * Question 3: Agricultural Commodity Trading
 * Optimized profit calculation using Dynamic Programming.
 */
public class CommodityTrader {

    public int maxProfit(int maxTrades, int[] prices) {
        int n = prices.length;
        if (n <= 1 || maxTrades == 0) return 0;

        // If maxTrades is large enough, it's like unlimited transactions
        if (maxTrades >= n / 2) {
            int profit = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i - 1]) {
                    profit += prices[i] - prices[i - 1];
                }
            }
            return profit;
        }

        // dp[k][i] = max profit on day i with at most k transactions
        int[][] dp = new int[maxTrades + 1][n];

        for (int k = 1; k <= maxTrades; k++) {
            // maxDiff tracks (profit from previous transactions - cost of buying)
            int maxDiff = -prices[0]; 
            for (int i = 1; i < n; i++) {
                // Choice 1: Don't sell today
                // Choice 2: Sell today (current price + best previous buy opportunity)
                dp[k][i] = Math.max(dp[k][i - 1], prices[i] + maxDiff);
                
                // Update maxDiff for the next day
                maxDiff = Math.max(maxDiff, dp[k - 1][i] - prices[i]);
            }
        }

        return dp[maxTrades][n - 1];
    }

    public static void main(String[] args) {
        CommodityTrader trader = new CommodityTrader();

        // TEST CASE 1: From Assignment (Chitwan produce prices)
        int maxTrades1 = 2;
        int[] prices1 = {2000, 4000, 1000};
        System.out.println("Test Case 1 Output: " + trader.maxProfit(maxTrades1, prices1));

        // TEST CASE 2: Multiple profitable cycles
        int maxTrades2 = 2;
        int[] prices2 = {1000, 2000, 1500, 3000, 500, 4000};
        // Possible trades: (1000 to 3000) and (500 to 4000) -> 2000 + 3500 = 5500
        System.out.println("Test Case 2 Output: " + trader.maxProfit(maxTrades2, prices2));

        // TEST CASE 3: No profit possible
        int maxTrades3 = 1;
        int[] prices3 = {5000, 4000, 3000, 2000};
        System.out.println("Test Case 3 Output: " + trader.maxProfit(maxTrades3, prices3));
    }
}

/* * OUTPUT (Testing & Validation):
 * -----------------------------------------------------------
 * Test Case 1 Output: 2000
 *
 * Test Case 2 Output: 5500
 *
 * Test Case 3 Output: 0
 * -----------------------------------------------------------
 */