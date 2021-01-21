
import java.io.*;
import java.util.*;


public class Main {
    static int[][] count;

    static boolean isPalindrome(String input){
        String[] strings = input.split("");
        int len =strings.length;
        boolean found = true;
        for(int i =0,j=len -1;j >= 1; ++i, --j) {
            if(!strings[i].equals(strings[j])){
                found= false;
                break;
            }
        }
        return found;
    }

    static int countPalindromes(String input) {
        int len = input.length();
        if (len == 1)
            return 0;
        int count = 0;
//        System.out.println("palindiomecheck0........................................."+input);
        for(int i = 0, j = len - 1; i <= len - 1 && j >= 0; ++i , --j) {

            if (i + 1 != len && input.substring(i, i  + 1).equals(input.substring(j, j  + 1)) && isPalindrome(input.substring(0, i + 1))) {
                count = count + 1;
//                System.out.println("palindiomecheck..."+input.substring(0, i + 1)+ "::"+i+"::"+j +"::"+ count);
            } else {
                if (i + 1 != len && input.substring(i, i  + 1).equals(input.substring(j, j  + 1))) {
                    continue;
                }
                break;
            }
        }
//        System.out.println("palindiomecheckcount...:::::::::::::::::::::::::::::::::::::::"+ count);
//        if (isPalindrome(input) && input.length() > 2)
//            ++count;
//        System.out.println(input+":"+count);
        return count;
    }

    static long palindromes(String s, String subs, int lindex, int rindex) {
        // traverse fro left and right
        int len = s.length();
        long count = 0;
        for (int i = 0; i <= len - 1 ; ++i) {
            int j = len - 1;
            while ( j >= 1 && j > i) {
                while(s.charAt(i) != s.charAt(j)) {
                    --j;
                }
                count += countPalindromes(s.substring(i, j + 1));
                --j;
            }
        }
        return count;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

//        String s = "bhbffggeahhhfeahgagcdadbahdafhdggeddccacbhbgebfcbdfedafehbeeahcgdgcfacabbbhfdeaehfbdfghehdahccgf" ;//s
        String s = scanner.nextLine();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");
        //      count = new int[s.length()][s.length()];
        //  for(int i = 0; i < jjjs.length(); ++i) {
        //        Arrays.fill(count[i],-1);
//
        //}
        //
       // String s = "bhbffggeahhhfeahgagcdadbahdafhdggeddccacbhbgebfcbdfedafehbeeahcgdgcfacabbbhfdeaehfbdfghehdahccgf";
//        System.out.println(countPalindromes("bhbffggeahhhfeahgagcdadbahdafhdggeddccacbhb"));
//        System.out.println(palindromes(s, s, 0, s.length()) % 10e7);
        bufferedWriter.write(String.valueOf(palindromes(s, s, 0, s.length()) ));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}


