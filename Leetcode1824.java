class Leetcode1824 {
    public int minSideJumps(int[] obstacles) {
        int n = obstacles.length, lane1 = 0, lane2 = 0, lane3 = 0;
        // int[] dp = new int[3];
        int[][] ways = new int[n][3];
        // Arrays.fill(ways, 0);
        int maxvalue = (int) (5 * Math.pow(10, 5));
        // ways[0][0] = obstacles[0] == 0 ?  maxvalue : 0;
        // ways[0][1] = obstacles[0] == 1 ? maxvalue : 0;
        // ways[0][2] = obstacles[0] == 2 ? maxvalue : 0;
        ways[1][0] = obstacles[1] == 1 ?  maxvalue : 1;
        ways[1][1] = obstacles[1] == 2 ? maxvalue : 0;
        ways[1][2] = obstacles[1] == 3 ? maxvalue : 1;
        for (int i = 2;  i < n; ++i) {
            
            if (obstacles[i] == 1) {
                ways[i][0] = maxvalue;
            } else {
                lane2 = ways[i-1][1] + (obstacles[i] == 2 ? 2 : 1);
                lane3 = ways[i-1][2] + (obstacles[i] == 3 ? 2 : 1);
                ways[i][0] = Math.min(Math.min(ways[i - 1][0], lane2), lane3);
            }
            if (obstacles[i] == 2) {
                ways[i][1] = maxvalue;
            } else {
                lane1= ways[i-1][0] + (obstacles[i] == 1 ? 2 : 1);
                lane3 = ways[i-1][2] + (obstacles[i] == 3 ? 2 : 1);
                ways[i][1] = Math.min(Math.min(ways[i - 1][1], lane1), lane3);
            }
            if (obstacles[i] == 3) {
                ways[i][2] = maxvalue;
            } else {
                lane1 = ways[i-1][0] + (obstacles[i] == 1 ? 2 : 1);
                lane2 = ways[i-1][1] + (obstacles[i] == 2 ? 2 : 1);
                ways[i][2] = Math.min(Math.min(ways[i - 1][2], lane1), lane2);
            }
           // System.out.println(ways[i][0] + "::" + ways[i][1] + "::" + ways[i][2]  + "::" + i);
            // System.out.println( "::::" + i);
        }
             return Math.min(Math.min(ways[n -1][0], ways[n -1][1]), ways[n -1][2]);
    }
}


// 0, 0  2,0 1,1
//      2,0 1,1
//        1,1, 2,1 
//            2, 1 (1)
//             2, 2
//                 0, 2 (2)
//                 0, 3
//                  0, 4 1, 3
                                      
// 1,1 0  - 2,0 2, 1 1
// 2, 1 2,2 1 - 2, 2 1
// 0,2 2      - 0,2 2
// 0, 3 2 , 1, 3 3 0, 3 

// 1, 0  maxValue  1,  1 0   1, 2 
