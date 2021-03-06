import java.io.*;
import java.lang.reflect.Array;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

import static java.util.stream.Collectors.*;

class Result1 {

    static int[] minindexes;
    static int[] maxindexes;
    static int minindex = 0, maxindex = 0;


    /*
     * Complete the 'fairCut' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER k
     *  2. INTEGER_ARRAY arr
     */

    public static long fairCut(int k, List<Integer> arr) {
    // Write your code here
        
        int n = arr.size();
        long[][] diffs = new long[n][n + 1];
        Collections.sort(arr);
//        int[] diffs = new int[k];
//        minindexes = new int[k];
       // Arrays.fill(diffs, Integer.MAX_VALUE);
        for (int i = 0; i < arr.size(); ++i) {
            long cdiff = 0;
            int j = 0;
            for( ;j < arr.size(); ++j) {
                long diff = Math.abs(arr.get(i) - arr.get(j));
                cdiff += diff;
                diffs[i][j]= diff;
            }
            diffs[i][j] = cdiff;
        }

        int[] set1 = new int[k];
        int[] set2 = new int[n-k];
        Map<Integer, Integer> nset = new HashMap<>();

        for (int a = 0; a < arr.size(); ++a) {
            nset.put(a, a);
        }
        int looplength = 0;
        int set1index = 0, set2index = 0, index = 0, maxdiff = 0, pdiff = 0, cdiff = 0;
        if ( n - k >= k) {
            set1 =  new int[n-k];
            looplength = n- k;
            set2 = new int[k];
        } else {
            set1 = new int[k];
            looplength = k;
            set2 = new int[n-k];
        }
        Arrays.fill(set1, -1);
        Arrays.fill(set2, -1);


        while(index < looplength) {
            int j = 0 , cmaxdiff = 0;
            if (index == 0) {
                set1[set1index] = index;
                nset.remove(index);
                ++set1index; ++index; 

            } else {
                if (index == 1) {
                    set1[set1index] = n - 1;
                    ++set1index; ++index;
                    nset.remove(n - 1);
                } 
                int minindex = 0;
                long mindiff = Long.MAX_VALUE;

                for(Integer h : nset.keySet()) {
                        long ndiff = 0;
                        for(int l : set1) {
                            if (l != -1)
                                ndiff += diffs[h][l];
                        }
                        long mdiff = Math.abs(diffs[h][n] - ndiff);
                        if (mdiff <= mindiff) {
                            mindiff = mdiff;
                            minindex = h;
                        } 
                }
                if (set1index < looplength) {
                    set1[set1index] = minindex;
                    ++index;++set1index;
                    nset.remove(minindex);
                }  else {
                    break;
                }             
            }

        }

        long sum = 0;
        for (int mini : set1) {
            for (Integer maxi : nset.keySet()) {
                sum += Math.abs(arr.get(mini) - arr.get(maxi));
            }
        }
        return  sum;
    }

}

public class FairCutGreedy {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
       BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        long result = Result1.fairCut(k, arr);
        // System.out.println(result);
        
       bufferedWriter.write(String.valueOf(result));
       bufferedWriter.newLine();

        bufferedReader.close();
       bufferedWriter.close();
    }
}
