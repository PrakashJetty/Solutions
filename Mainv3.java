package com.company;

import java.io.*;
import java.math.BigInteger;
import java.nio.CharBuffer;
import java.time.Instant;
import java.util.*;


public class Mainv3 {

    static Node[] charNodes = new Node[8];

    static class Node {
        private char c;
        private LinkedList<Integer> indexLengths;

        public Node(final char c) {
            this.c = c;
        }

        public char getC() {
            return c;
        }

        public void setC(final char c) {
            this.c = c;
        }

        public LinkedList<Integer> getIndexLengths() {
            return indexLengths;
        }

        public void setIndexLengths(final LinkedList<Integer> indexLengths) {
            this.indexLengths = indexLengths;
        }
    }

    static boolean isPalindrome(String input){
        String[] strings = input.split("");
        int len = strings.length;
        boolean found = true;
        for(int i =0,j=len -1;j >= 1; ++i, --j) {
            if(!strings[i].equals(strings[j])){
                found= false;
                break;
            }
        }
        return found;
    }

    static BigInteger binomial(final int N, final int K) {
        BigInteger ret = BigInteger.ONE;
        for (int k = 0; k < K; k++) {
            ret = ret.multiply(BigInteger.valueOf(N-k))
                    .divide(BigInteger.valueOf(k+1));
        }
        return ret;
    }

    static long countPalindromes() {
       long count = 0;
        long icount = 0;
       for(Node node : charNodes) {
           if (node != null) {
               LinkedList<Integer> integers = node.indexLengths;
               System.out.println(integers);
               for (int i = 0; i < integers.size(); ++i) {
                   int ilen = integers.get(i);
                   if (ilen > 1) {
                       if (ilen == 2) {
                           icount += 2;
                       } else {
                           icount += ((ilen) * (ilen + 1)) / 2;
                       }
                   } else {
                       if (integers.size() > 1) {
                           icount += 1;
                       }
                   }
                   for (int j = i + 1; j < integers.size(); ++j) {
                       icount += binomial(i, integers.get(j)).longValue();
                   }
               }
               count += icount;

           }
           icount = 0;
       }
        return count;
    }

    static void frameIndexLengths(String s) {
        char prev = 'X';
        for(char ch: s.toCharArray()) {
            if (charNodes[Math.abs(97 - ch)] == null) {
                Node node = new Node(ch);
                charNodes[Math.abs(97 - ch)] = node;
                LinkedList<Integer> indexList = new LinkedList<>();
                indexList.add(1);
                node.setIndexLengths(indexList);

            } else {
                Node node =  charNodes[Math.abs(97 - ch)];
                LinkedList<Integer> indexMap = node.getIndexLengths();
                if (prev == ch)
                    indexMap.addLast(indexMap.getLast() + 1);
                else
                    indexMap.add(1);
            }
            prev = ch;
        }
    }


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

//        String s = "bhbffggeahhhfeahgagcdadbahdafhdggeddccacbhbgebfcbdfedafehbeeahcgdgcfacabbbhfdeaehfbdfghehdahccgf" ;//s
//        String s = scanner.nextLine();
//        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");
        //      count = new int[s.length()][s.length()];
        //  for(int i = 0; i < jjjs.length(); ++i) {
        //        Arrays.fill(count[i],-1);
//
        //}
        //
        FileInputStream fileInputStream = new FileInputStream("c:\\Users\\prakashj\\Documents\\input.txt");
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);


        String s = new String(bytes);
//        String s = "ababa";
//        System.out.println(s);
        frameIndexLengths(s);
//        System.out.println(countPalindromes("bhbffggeahhhfeahgagcdadbahdafhdggeddccacbhb"));
        System.out.println(countPalindromes() );
//        bufferedWriter.write(String.valueOf(palindromes(s, s, 0, s.length()) % 10e7));
//        bufferedWriter.newLine();
//
//        bufferedWriter.close();
//
//        scanner.close();
    }
}


