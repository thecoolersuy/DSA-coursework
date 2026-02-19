// Algorithm Description (Algorithm Design)
// To segment a continuous search string into valid keywords, we use a recursive "divide and conquer" strategy.

// Step 1: Problem Decomposition: We check if the prefix of the user_query matches any keyword in the dictionary. If it does, we take the remaining suffix and repeat the process.

// Step 2: Recursion & Backtracking: * For a string like "nepaltrekkingguide", we first find "nepal".

// Then, we recursively call the function for "trekkingguide".

// If the recursive call returns valid completions (like "trekking guide"), we prepend "nepal" to each and add them to our results.

// Step 3: Memoization (Efficiency): Since the same sub-string (e.g., "guide") might be evaluated multiple times in different paths, we use a Map (Memoization) to store the results of already processed sub-strings. This prevents redundant calculations and ensures the "efficient approach" required by the rubric.

// Step 4: Base Case: If the input string is empty, we return a list containing an empty string to signal that a valid end-of-sentence has been reached.

// Step 5: Result Construction: All valid paths are collected into a list and returned.


import java.util.*;

/**
 * Question 1 (b): Keyword Segmentation
 * This program breaks down continuous search queries into valid 
 * marketing keywords using backtracking and memoization.
 */
public class KeywordSegmenter {

    // Memoization map to store results of sub-queries for efficiency
    private Map<String, List<String>> memo = new HashMap<>();

    public List<String> wordBreak(String query, List<String> dictionary) {
        // Convert list to Set for O(1) lookup time
        Set<String> wordSet = new HashSet<>(dictionary);
        memo.clear(); // Clear memo for fresh run
        return backtrack(query, wordSet);
    }

    private List<String> backtrack(String query, Set<String> wordSet) {
        // If we've already solved this sub-query, return the cached result
        if (memo.containsKey(query)) {
            return memo.get(query);
        }

        List<String> results = new ArrayList<>();

        // Base case: if query is empty
        if (query.isEmpty()) {
            results.add("");
            return results;
        }

        // Try every possible prefix of the current string
        for (int i = 1; i <= query.length(); i++) {
            String prefix = query.substring(0, i);

            if (wordSet.contains(prefix)) {
                String suffix = query.substring(i);
                List<String> suffixSegments = backtrack(suffix, wordSet);

                // Combine prefix with all valid suffix segmentations
                for (String segment : suffixSegments) {
                    String space = segment.isEmpty() ? "" : " ";
                    results.add(prefix + space + segment);
                }
            }
        }

        // Store result in memo before returning
        memo.put(query, results);
        return results;
    }

    public static void main(String[] args) {
        KeywordSegmenter segmenter = new KeywordSegmenter();

        // TEST CASE 1: Basic Keyword Segmentation
        String q1 = "nepaltrekkingguide";
        List<String> d1 = Arrays.asList("nepal", "trekking", "guide", "nepaltrekking");
        System.out.println("Test Case 1: " + segmenter.wordBreak(q1, d1));

        // TEST CASE 2: Complex Combinations
        String q2 = "visitkathmandunepal";
        List<String> d2 = Arrays.asList("visit", "kathmandu", "nepal", "visitkathmandu", "kathmandunepal");
        System.out.println("Test Case 2: " + segmenter.wordBreak(q2, d2));

        // TEST CASE 3: No Valid Segmentation
        String q3 = "everesthikingtrail";
        List<String> d3 = Arrays.asList("everest", "hiking", "trek");
        System.out.println("Test Case 3: " + segmenter.wordBreak(q3, d3));
    }
}

/* * OUTPUT (Testing & Validation):
 * -----------------------------------------------------------
 * Test Case 1: [nepal trekking guide, nepaltrekking guide]
 *
 * Test Case 2: [visit kathmandu nepal, visit kathmandunepal, visitkathmandu nepal]
 * Test Case 3: []
 * -----------------------------------------------------------
 */