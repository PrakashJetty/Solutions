package com.company;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.*;
import java.nio.CharBuffer;
import java.util.*;


public class Mainv2 {
    static Node[] charNodes = new Node[8];



    static class Node {
        private char c;
        private SortedMap<Integer, SortedMap<Integer, Integer>> indexMap;


        public Node(final char c) {
            this.c = c;
        }

        public char getC() {
            return c;
        }

        public void setC(final char c) {
            this.c = c;
        }


        public SortedMap<Integer, SortedMap<Integer, Integer>> getIndexMap() {
            return indexMap;
        }

        public void setIndexMap(final SortedMap<Integer, SortedMap<Integer, Integer>> indexMap) {
            this.indexMap = indexMap;
        }
    }

    static long countPalindromes(Node root,  char[] chars, int l, int r) {
        SortedMap<Integer, SortedMap<Integer, Integer>>   startMap =    root.getIndexMap().tailMap(l);
        Optional<SortedMap<Integer, Integer>> optional = startMap.values().stream().peek(s -> s.tailMap(r)).findFirst();
        if (!startMap.isEmpty()) {

        }


    }

    static  long traverseNCount(char[] chars) {
        int len = chars.length;
        long count = 0;
        for (int i = 0; i <= len - 1 ; ++i) {
            int j = len - 1;
            while ( j >= 1 && j > i) {
                while(chars[i] != chars[j]) {
                    --j;
                }
                count += countPalindromes(charNodes[Math.abs(97 - chars[i])], chars, i , j);
                --j;
            }
        }
        return count;
    }

    static void constructPalIndexRanges(char[] chars) {
        int i = 0, mindex = 0;
        Map<Character, Boolean> visitedMap = new HashMap<>();

        for(char ch: chars) {
           if (visitedMap.containsKey(ch)) {
                if (chars[i - 1] == ch || chars[i - 2] == ch) {
                    visitedMap.clear();
                    if (charNodes[Math.abs(97 - ch)] == null) {
                        Node node = new Node(ch);
                        charNodes[Math.abs(97 - ch)] = node;
                        SortedMap<Integer, SortedMap<Integer, Integer>> indexMap = new TreeMap<>();
                        SortedMap<Integer, Integer> rmap = new TreeMap<>();
                        rmap.put(i, i);
                        indexMap.put(i - 2, rmap);
                        node.setIndexMap(indexMap);
                    } else {
                        Node node =  charNodes[Math.abs(97 - ch)];
                        SortedMap<Integer, SortedMap<Integer, Integer>> indexMap = node.getIndexMap();
                        SortedMap<Integer, Integer> rmap = new TreeMap<>();
                        rmap.put(i, i);
                        indexMap.put(i - 2, rmap);
                    }
                }
           } else {
                visitedMap.put(ch, true);

           }
            ++i;
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
//        FileInputStream fileInputStream = new FileInputStream("c:\\Users\\prakashj\\Documents\\input.txt");
//        byte[] bytes = new byte[fileInputStream.available()];
//        fileInputStream.read(bytes);
//
//
//        String s = new String(bytes);
//        System.out.println(s);
        String s = "bhbffggeahhhfeahgagcdadbahdafhdggeddccacbhbgebfcbdfedafehbeeahcgdgcfacabbbhfdeaehfbdfghehdahccgf";
//        String s= "bhbffggeahhhfeahgagcdadbahdafhdggeddccacbhbgebfcbdfedafehbeeahcgdgcfacabbbhfdeaehfbdfghehdahccgf";
        char[] chars = s.toCharArray();
        constructPalIndexRanges(chars);
        System.out.println(traverseNCount(chars));

//        System.out.println(countPalindromes("bhbffggeahhhfeahgagcdadbahdafhdggeddccacbhb"));
//        System.out.println(palindromes(s, s, 0, s.length()) );
//        bufferedWriter.write(String.valueOf(palindromes(s, s, 0, s.length()) % 10e7));
//        bufferedWriter.newLine();
//
//        bufferedWriter.close();
//
//        scanner.close();
    }
}


