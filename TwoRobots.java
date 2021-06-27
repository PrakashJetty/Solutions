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

public class TwoRobots {


    static class State {
        int d;
        int r1p;
        int r2p;
        int move;
        State prev;

        State(int d, int r1p, int r2p, int move, State prev) {
            this.d = d;
            this.r1p = r1p;
            this.r2p = r2p;
            this.move = move;
            this.prev = prev;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            State state = (State) o;

            if (d != state.d) return false;
            if (r1p != state.r1p) return false;
            return r2p == state.r2p;
        }

        @Override
        public int hashCode() {
            int result = r1p;
            result = 31 * result + r2p;
            return result;
        }
    }



    
    public static int twoRobots(int m, List<List<Integer>> queries) {
        // Write your code here
        int cr1br1p = 0, cr1br2p = 0, cr2br1p = 0, cr2br2p = 0, index = 0, mind = 0;
        SortedMap<State, State> cmap = new TreeMap<>(Comparator.comparingInt(state -> state.d));
        for (List<Integer> query : queries) {
            int lp = query.get(0), rp = query.get(1);
            int cd = Math.abs(lp - rp);

            if (index == 0) {
               
                State state = new State(cd, rp, 0, 0, null);
                // List<State> states = new ArrayList<>();
                // states.add(state);
                cmap.put(state, state);
                cr1br1p = rp;
                cr1br2p = rp;
                mind = cd;
            
            } else {
                SortedMap<State, State> imap = new TreeMap<>(Comparator.comparingInt(state -> state.d));
                for (Map.Entry<State, State> entry : cmap.entrySet()) {
                    int r1d = 0, r2d =0;
                    State r1state = null, r2state = null;
                    State cstate = entry.getKey();
                    if (imap.get(new State(cstate.d, rp, cstate.r2p, 0, null)) == null) {
                        r1d = Math.abs(cstate.r1p - lp) + cd;
                        r1state = new State(cstate.d + r1d , rp, cstate.r2p, 0, null);
                        imap.put(r1state, r1state);
                    }
                    if (imap.get(new State(cstate.d, rp, cstate.r1p, 0, null)) == null) {
                        r2d = (cstate.r2p == 0 ? 0 : Math.abs(cstate.r2p - lp)) + cd;
                        r2state = new State(cstate.d + r2d , cstate.r1p, rp, 1, null);
                       
                        imap.put(r2state, r2state);
                    }
                }
                cmap.clear();
                cmap = imap;
            }

             ++index;       
        }
            


        return cmap.firstKey().d;

    }


    static class Solution {
        public static void main(String[] args) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

            int t = Integer.parseInt(bufferedReader.readLine().trim());



            for (int j = 0; j < t; ++j) {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int m = Integer.parseInt(firstMultipleInput[0]);
                List<List<Integer>> queries = new ArrayList<>();

                int n = Integer.parseInt(firstMultipleInput[1]);

                IntStream.range(0, n).forEach(i -> {
                    try {
                        queries.add(
                            Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                            .map(Integer::parseInt)
                            .collect(toList())
                        );
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });


                int result = TwoRobots.twoRobots(m, queries);
//                bufferedWriter.write(String.valueOf(result));
                //bufferedWriter.newLine();

                System.out.println(result);
            }
            
            bufferedReader.close();
            //bufferedWriter.close();
        }
    }
}
