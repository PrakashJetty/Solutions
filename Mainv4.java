package com.company;

import java.io.*;
import java.math.BigInteger;
import java.nio.CharBuffer;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


public class Mainv4 {

    static Node[] charNodes = new Node[8];

    static class Node {
        private char c;
        private SortedMap<Integer, SortedSet<SortedSet<Integer>>> indexLengths;
        private LinkedList<Integer> indexes;

        public Node(final char c) {
            this.c = c;
        }

        public char getC() {
            return c;
        }

        public void setC(final char c) {
            this.c = c;
        }

        public SortedMap<Integer, SortedSet<SortedSet<Integer>>> getIndexLengths() {
            return indexLengths;
        }

        public void setIndexLengths(final SortedMap<Integer, SortedSet<SortedSet<Integer>>> indexLengths) {
            this.indexLengths = indexLengths;
        }

        public LinkedList<Integer> getIndexes() {
            return indexes;
        }

        public void setIndexes(final LinkedList<Integer> indexes) {
            this.indexes = indexes;
        }
    }

    static long countPalindromes() {
        long count = 0;
        long charCount = 0;
//        SortedMap<Integer, Long> sizePerms = new TreeMap<>();
//        sizePerms.put(1, 0l);
//        sizePerms.put(2, 1l);
        for(Node node : charNodes) {
            if (node != null) {
                SortedMap<Integer, SortedSet<SortedSet<Integer>>> map = node.indexLengths;
                for(Map.Entry<Integer, SortedSet<SortedSet<Integer>>> entry: map.entrySet()) {
                    long indexCount = 0;
                   for(SortedSet<Integer> sortedSet :  entry.getValue()) {

                        for(Integer i : sortedSet) {
                            //                       System.out.println( node.getC() +"::"+ i +"::" + entry.getKey() +"::"+ entry.getValue().size());
                            if (entry.getKey() == 1) {
                                indexCount += sortedSet.tailSet(i).size() - 1 + entry.getValue().tailSet(sortedSet).size() - 1 + map.entrySet().stream().filter(e ->
                                        !e.getKey().equals(entry.getKey())
                                ).parallel().map(e -> {
                                    int size = e.getKey();
                                    int tsize = e.getValue().tailSet(sortedSet).size();
                                    return tsize == 0 ? 0 : tsize * size;
                                }).mapToLong(Long::new).sum();
                            } else {
                                final int k = entry.getKey();
                                indexCount += map.entrySet().stream().parallel().map( e -> {
                                    int t = e.getValue().tailSet(sortedSet).size();
                                    int m = e.getKey();
                                    int s = t == 0 ? 0 : t == e.getValue().size() ? t :  t - 1;
                                    if (s == 0)
                                        return 0l;
                                    if (s == 1 && m == k && sortedSet.equals(e.getValue().first()))
                                        return 0l;
                                    if (m == 1 ) {
                                        return (long) (k - 1) * s;
                                    } else {
                                        return (long) s * ((((2 * k * k * m) - (k * (k - 1) *  (m - k + 1)))  /2) - k);
                                    }
                                }).mapToLong(Long::new).sum();
                            }
                            charCount += indexCount;
                            indexCount = 0;
                        }


                   }
                    int k = entry.getKey();
                   if (k != 1)
                       charCount += (k * (k + 1) * (k - 1))/6;

                }

            }
            count += charCount;
            charCount = 0;

        }
        return count;
    }

    static void frameIndexLengths(String s) {
        char prev = 'X';
        int index = 0, prevcount = 0;
        char[] chars = s.toCharArray();
        SortedSet<Integer> current = new TreeSet<>();
        for(char ch: chars) {
            boolean b = index + 1 < chars.length && chars[index + 1] == ch;
            if (charNodes[Math.abs(97 - ch)] == null) {
                if (b) {
                    current.add(index);
                    ++prevcount;
                } else {
                    Node node = new Node(ch);
                    charNodes[Math.abs(97 - ch)] = node;
                    SortedSet<Integer> indexList = new TreeSet<>();
                    indexList.add(index);
                    SortedSet<SortedSet<Integer>> sortedSets = new TreeSet<>((integers, t1) -> integers.last().compareTo(t1.first()));
                    sortedSets.add(indexList);
                    SortedMap<Integer, SortedSet<SortedSet<Integer>>> map = new TreeMap<>();
                    map.put(prevcount + 1, sortedSets);
                    node.setIndexLengths(map);
                    LinkedList<Integer> indexes = new LinkedList<>();
                    indexes.addLast(index);
                    node.setIndexes(indexes);
                }

            } else {
                Node node =  charNodes[Math.abs(97 - ch)];
                SortedMap<Integer, SortedSet<SortedSet<Integer>>> map = node.getIndexLengths();

                if (prev == ch) {
                    if (b) {
                        current.add(index);
                        ++prevcount;
                    } else {
                        SortedSet<SortedSet<Integer>> list = map.get(prevcount + 1);
                        if (list == null) {

                            current.add(index);
                            SortedSet<SortedSet<Integer>> sortedSets = new TreeSet<>((integers, t1) -> integers.last().compareTo(t1.first()));
                            sortedSets.add(current);
                            map.put(prevcount + 1, sortedSets);
                        } else {
                            current.add(index);
                            list.add(current);
                        }
                        prevcount = 0;
                    }
                } else {
                    current = new TreeSet<>();
                    if (b) {
                        current.add(index);
                    }
                    else {
                        SortedSet<SortedSet<Integer>> list = map.get(prevcount + 1);
                        if (list == null) {
                            current.add(index);
                            SortedSet<SortedSet<Integer>> sortedSets = new TreeSet<>((integers, t1) -> integers.last().compareTo(t1.first()));
                            sortedSets.add(current);
                            map.put(1, sortedSets);
                        } else {
                            current.add(index);
                            list.add(current);
                        }
                    }
                }
                node.getIndexes().addLast(index);
            }
            prev = ch;
            ++index;
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
        String s = "bhbffggeahhhfeahgagcdadbahdafhdggeddccacbhbgebfcbdfedafehbeeahcgdgcfacabbbhfdeaehfbdfghehdahccgf";
//        String s = "aa";
//        System.out.println(s);
        frameIndexLengths(s);
//        System.out.println(countPalindromes("bhbffggeahhhfeahgagcdadbahdafhdggeddccacbhb"));
        BigInteger bigInteger = BigInteger.valueOf(countPalindromes());
        BigInteger bigInteger1 = BigInteger.valueOf(1000000000).add(BigInteger.valueOf(7));
        System.out.println( bigInteger.mod(bigInteger1) );
//        bufferedWriter.write(String.valueOf(palindromes(s, s, 0, s.length()) % 10e7));
//        bufferedWriter.newLine();
//
//        bufferedWriter.close();
//
//        scanner.close();
    }
}


