class Leetcode959 {
    public int regionsBySlashes(String[] grid) {
        
        int n = grid.length();
        Map<Pair<Integer, Integer> , List<Pair<Integer, Integer>>> graph = new HashMap<>();
        
        for (int i = 0; i < n ; ++i) {
            String gridString = grid[i];
            char[] chars = gridString.toCharAarray();
            int index = 1;
            int quad = i <= 7 ? 1 : ((i >= 8 && i <= 15) ?
                                     2 : ((i >= 16 && i <= 23) ? 3 : 4));
            int row = (quad == 1 || quad == 3) ?  ((i <= 3 || i >= 16 && i <= 19) ? 0 : 1) : 
                                     (((i >= 8 && i <= 11) || (i >= 24 && i <= 27) ) ? 2 : 3);
            int col = (quad == 1 || quad == 2) ? ( i % 4 ) : ( 4 + i % 4 );
            
            for (char ch : chars) {
                if (ch == '/') {
                    if (index == 1) {
                        Pair<Integer, Integer> spoint = new Pair<>((row + 1) * 2, col * 2);
                        Pair<Integer, Integer> dpoint = new Pair<>(row  * 2, (col * 2) + 1);
                        List<Pair<Integer, Integer>> sedges = graph.get(spoint);
                        if (sedges == null) {
                            sedges = new ArrayList<>();
                            graph.put(spoint, sedges);
                        }
                        sedges.add(dpoint);
                        List<Pair<Integer, Integer>> dedges = graph.get(dpoint);
                        if (dedges == null) {
                            dedges = new ArrayList<>();
                            graph.put(dpoint, dedges);
                        }
                        dedges.add(spoint);
                        
                        
                    } else {
                        Pair<Integer, Integer> spoint = new Pair<>((row + 1) * 2, (col * 2) + 1);
                        Pair<Integer, Integer> dpoint = new Pair<>(row  * 2, (col + 1) * 2);
                        List<Pair<Integer, Integer>> sedges = graph.get(spoint);
                        if (sedges == null) {
                            sedges = new ArrayList<>();
                            graph.put(spoint, sedges);
                        }
                        sedges.add(dpoint);
                        List<Pair<Integer, Integer>> dedges = graph.get(dpoint);
                        if (dedges == null) {
                            dedges = new ArrayList<>();
                            graph.put(dpoint, dedges);
                        }
                        dedges.add(spoint);
                    }
                }
                if (ch == '\\') {
                    if (index == 1) {
                        Pair<Integer, Integer> spoint = new Pair<>((row) * 2, col * 2);
                        Pair<Integer, Integer> dpoint = new Pair<>( (row + 1)  * 2, (col * 2) + 1);
                        List<Pair<Integer, Integer>> sedges = graph.get(spoint);
                        if (sedges == null) {
                            sedges = new ArrayList<>();
                            graph.put(spoint, sedges);
                        }
                        sedges.add(dpoint);
                        List<Pair<Integer, Integer>> dedges = graph.get(dpoint);
                        if (dedges == null) {
                            dedges = new ArrayList<>();
                            graph.put(dpoint, dedges);
                        }
                        dedges.add(spoint);
                        
                        
                    } else {
                        Pair<Integer, Integer> spoint = new Pair<>((row) * 2, (col * 2) + 1);
                        Pair<Integer, Integer> dpoint = new Pair<>((row + 1) * 2, (col + 1) * 2);
                        List<Pair<Integer, Integer>> sedges = graph.get(spoint);
                        if (sedges == null) {
                            sedges = new ArrayList<>();
                            graph.put(spoint, sedges);
                        }
                        sedges.add(dpoint);
                        List<Pair<Integer, Integer>> dedges = graph.get(dpoint);
                        if (dedges == null) {
                            dedges = new ArrayList<>();
                            graph.put(dpoint, dedges);
                        }
                        dedges.add(spoint);
                    }
                }
                ++index;
            }
        }
        
        for (int = 0; i <= 16 ; ++i) {
            Pair<Integer, Integer> spoint = new Pair<>(0, (i * 2));
             List<Pair<Integer, Integer>> sedges = new ArrayList<>();
             graph.put(spoint, sedges);
            if (graph.containsKey(new Pair<>(0, ((i * 2) + 1))))
                sedges.add(new Pair<>(0, ((i * 2) + 1)));
            if (graph.containsKey(new Pair<>(0, ((i * 2) - 1))))
                sedges.add(new Pair<>(0, ((i * 2) - 1)));
            Pair<Integer, Integer>  rpoint = new Pair<>(8, (i * 2));
             List<Pair<Integer, Integer>> redges = new ArrayList<>();
             graph.put(spoint, redges);
            if (graph.containsKey(new Pair<>(8, ((i * 2) + 1))))
                redges.add(new Pair<>(8, ((i * 2) + 1)));
            if (graph.containsKey(8, ((i * 2) - 1)))
                redges.add(new Pair<>(8, ((i * 2) - 1)));
            
            Pair<Integer, Integer> cpoint = new Pair<>((i * 2), 0);
             List<Pair<Integer, Integer>> cedges = new ArrayList<>();
             graph.put(cpoint, cedges);
            if (graph.containsKey(new Pair<>(((i * 2) + 1), 0)))
                cedges.add(new Pair<>(((i * 2) + 1), 0));
            if (graph.containsKey(new pair<>(((i * 2) - 1), 0)))
                cedges.add(new Pair<>(((i * 2) - 1), 0));
            
            Pair<Integer, Integer> epoint = new Pair<>((i * 2), 16);
             List<Pair<Integer, Integer>> eedges = new ArrayList<>();
             graph.put(epoint, eedges);
            if (graph.containsKey(new Pair<>(((i * 2) + 1), 16)))
                eedges.add(new Pair<>(((i * 2) + 1), 16));
            if (graph.containsKey(new pair<>(((i * 2) - 1), 16)))
                eedges.add(new Pair<>(((i * 2) - 1), 16));
        }
        
        
        
            
       
        
    }
}
