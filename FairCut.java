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
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result1 {

    static int[] minindexes;
    static int[] maxindexes;
    static int minindex = 0, maxindex = 0;

//    public static void insertIfMin(int diff, int index, int[] diffs,  int[] minindexes, int[] maxindexes) {
//        boolean min = false;
//        int i = 0;
//        for(i < diffs.length; ++i) {
//            if (diff < diffs[i]) {
//                min = true;
//                if (diffs[i] == Integer.MAX_VALUE) {
//                    diffs[i] = diff;
//                    break;
//                } else {
//                    int temp = diffs[i];
//                    diffs[i] = diff;
//                    diff = temp;
//                }
//
//            }
//        }
//        if (min) {
//            minindexes[minindex] = index;
//            ++minindex;
//        } else {
//            if (i == diffs.length) {
//
//            }
//            maxindexes[maxindex] = index;
//            ++maxindex;
//        }
//
//    }

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
        maxindexes = new int[n-k];
//        int[] diffs = new int[k];
//        minindexes = new int[k];
       // Arrays.fill(diffs, Integer.MAX_VALUE);
        SortedMap<Long, List<Integer>> diffMap = new TreeMap<>();
        List<Integer> maxList = new ArrayList<>();
        for (int i = 0; i < arr.size(); ++i) {
            long diff = 0;
            for(int j = 0; j < arr.size(); ++j) {
                diff += Math.abs(arr.get(i) - arr.get(j));
            }
            if (i == 0) {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                diffMap.put(diff, list);
            } else {
                long lastDiff = diffMap.lastKey();
                if (diffMap.values().stream().mapToInt(v -> v.size()).sum() == k) {
                    if (diff <= lastDiff) {
                        List<Integer> indexList = diffMap.get(lastDiff);
                        maxList.add(indexList.remove(0));
                        diffMap.remove(lastDiff);
                        indexList = diffMap.get(diff);
                        if (indexList != null) {
                            indexList.add(i);
                            diffMap.put(diff, indexList);
                        } else {
                            List<Integer> list = new ArrayList<>();
                            list.add(i);
                            diffMap.put(diff, list);
                        }
                    } else {
                        maxList.add(i);
                    }
//                    diffMap.put(diff, indexList);
                }  else {
                    List<Integer> indexList = diffMap.get(diff);
                    if (indexList != null) {
                        indexList.add(i);
                        diffMap.put(diff, indexList);
                    } else {
                        List<Integer> list = new ArrayList<>();
                        list.add(i);
                        diffMap.put(diff, list);
                    }
                }
            }


           // insertIfMin(diff, i, diffs, minindexes, maxindexes);
        }
        long sum = 0;
        Collection<List<Integer>> values = diffMap.values();
        for (List<Integer> ivalues : values) {
            for(Integer value : ivalues) {
                for (Integer mvalue : maxList) {
                    sum += Math.abs(arr.get(value) - arr.get(mvalue));
                }
            }
        }
        return  sum;
    }

}

public class FairCut {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        long result = Result1.fairCut(k, arr);
        System.out.println(result);
        
//        bufferedWriter.write(String.valueOf(result));
//        bufferedWriter.newLine();

        bufferedReader.close();
//        bufferedWriter.close();
    }
}
