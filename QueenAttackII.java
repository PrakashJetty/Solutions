package com.company;

import java.io.*;
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

class ResultQueenAttackII {

    /*
     * Complete the 'queensAttack' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER k
     *  3. INTEGER r_q
     *  4. INTEGER c_q
     *  5. 2D_INTEGER_ARRAY obstacles
     */

    public static int queensAttack(int n, int k, int r_q, int c_q, List<List<Integer>> obstacles) {
    // Write your code here

        int mid = (int) Math.ceil(n/2);
        int lrcount = r_q - 1;
        int rrcount = n - r_q;
        int uccount = n - c_q;
        int dccount = c_q - 1;
        int ldrcount = Math.min(r_q - 1, c_q - 1);
        int rdrcount = n - Math.max(r_q, c_q);
        int udccount = 0;
        if (r_q <= mid) {
            udccount = r_q - 1;
        } else  {
            udccount = n - c_q;
        }
        int ddccount = 0;
        if (c_q <= mid) {
            ddccount = c_q - 1;
        } else  {
            ddccount = n - r_q;
        }
        for (List<Integer> obstacle: obstacles) {
            int c = obstacle.get(0);
            int r = obstacle.get(1);
            if (r_q == r) {
                if (c < c_q) {
                    dccount = c_q - c - 1;
                } else {
                    uccount  = c - c_q - 1;
                }
                continue;
            }
            if (c_q == c) {
                if (r < r_q) {
                    lrcount = r_q - r - 1;
                } else {
                    rrcount  = r - r_q - 1;
                }
                continue;
            }
            int rdiff = r_q - r;
            int cdiff = c_q - c;
            if (Math.abs(rdiff) == Math.abs(cdiff)) {
                if (rdiff > 0 && cdiff > 0) {
                    ldrcount = Math.min(ldrcount, rdiff - 1);
                    continue;
                }
                if (rdiff < 0 && cdiff < 0) {
                    rdrcount = Math.min(rdrcount, Math.abs(rdiff) - 1);
                    continue;
                }
                if (rdiff < 0) {
                    ddccount =   Math.min(ddccount, Math.abs(cdiff) - 1);
                    continue;
                }
                udccount = Math.min(udccount, Math.abs(cdiff) - 1);
            }


        }
        return lrcount + rrcount + dccount + uccount + ldrcount + rdrcount + udccount + ddccount;

    }

}

public class QueenAttackII {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        String[] secondMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int r_q = Integer.parseInt(secondMultipleInput[0]);

        int c_q = Integer.parseInt(secondMultipleInput[1]);

        List<List<Integer>> obstacles = new ArrayList<>();

        IntStream.range(0, k).forEach(i -> {
            try {
                obstacles.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int result = ResultQueenAttackII.queensAttack(n, k, c_q, r_q , obstacles);
        System.out.println(result);
//        bufferedWriter.write(String.valueOf(result));
//        bufferedWriter.newLine();

        bufferedReader.close();
       // bufferedWriter.close();
    }
}
