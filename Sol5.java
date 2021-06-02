package com.company.solution1;

import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.*;
import java.util.stream.Collector;

public class Sol5 {

    static int[] degrees;
    static Map<Integer, List<Integer>> edgeMap = new HashMap<>();
    static SortedMap<Integer, Integer> dMap = new TreeMap<>();

    static void makeDegrees(int[][] edges, int[] degrees) {
        for (int[] edge : edges) {
            ++degrees[edge[0]];
            ++degrees[edge[1]];
            edgeMap.get(edge[0]).add(edge[1]);
            edgeMap.get(edge[1]).add(edge[0]);
        }
    }

    static int countSubPossibles(int n, SortedMap<Integer, Integer> subMap, SortedMap<Integer, Integer> alMap, int k) {
        int count = 0;
        if (k <= 0 && alMap.size() == n)
            return count;
        else {
            for (Map.Entry<Integer, Integer> entry : subMap.entrySet()) {
                if (!alMap.containsKey(entry.getKey()) && entry.getKey() < k ) {
                    long cedges = alMap.values()
                            .stream()
                            .filter(a -> edgeMap.get(entry.getValue()).contains(a)).count();
                    alMap.put(entry.getKey(), entry.getValue());
                    if (cedges > 0 ) {
                        countSubPossibles(n - 1, subMap, alMap, k - entry.getKey() + (int) cedges);
                    } else {
                        countSubPossibles(n - 1, subMap, alMap, k - entry.getKey() );
                    }

                } else {

                }
            }
        }
        return count;
    }


    static int countPossibles(int n, int[] ins, int k) {
        int count = 0;
        for ( int i =2; i < n ; ++i) {
            // get arrays with degrees les than k + i
            SortedMap<Integer, Integer> cmap = dMap.headMap(k + i);
            count += countSubPossibles(i, cmap, new TreeMap<>(), k);
        }
        return count;
    }

    /*
     * Complete the cuttree function below.
     */
    static int cutTree(int n, int k, int[][] edges) {
        /*
         * Write your code here.
         */
        makeDegrees(edges, degrees);
        // sort degrees
        for (int i = 0; i < degrees.length; ++i) {
            dMap.put(degrees[i], i);
        }

        // loop through n for all sizes and calculate

        return 0;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nk = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nk[0].trim());

        int k = Integer.parseInt(nk[1].trim());

        int[][] edges = new int[n-1][2];

        for (int edgesRowItr = 0; edgesRowItr < n-1; edgesRowItr++) {
            String[] edgesRowItems = scanner.nextLine().split(" ");

            for (int edgesColumnItr = 0; edgesColumnItr < 2; edgesColumnItr++) {
                int edgesItem = Integer.parseInt(edgesRowItems[edgesColumnItr].trim());
                edges[edgesRowItr][edgesColumnItr] = edgesItem;
            }
        }
        degrees = new int[n];
        int result = cutTree(n, k, edges);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();
    }
}
