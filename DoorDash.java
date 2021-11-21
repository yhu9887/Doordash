package DoorDash;

import Amazon.Amazon;

import javax.swing.event.TreeExpansionEvent;
import java.util.*;

public class DoorDash {
    // =============== Computing Team Dash Quality ==============
    static class  Node{
        int speed;
        int pro;
        public Node(int speed, int pro){
            this.speed = speed;
            this.pro = pro;
        }
        public String toString(){return this.speed+","+this.pro;}
    }
    public static int maxTeamQuality(int[] speed, int[] professionalism, int maxDashers){
        int res = 0, sum = 0;
        int len = speed.length;
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.pro));
        for (int j=1;j<=maxDashers;j++){
            sum = 0;
            pq.clear();
            for (int i=0;i<len;i++){
                Node n = new Node(speed[i], professionalism[i]);;
                pq.offer(n);
                sum += n.speed;
                if (pq.size() == j){
                    res = Math.max(res, sum*pq.peek().pro);
                }else if (pq.size() > j){
                    Node tmp = pq.poll();
                    sum -= tmp.speed;
                    res = Math.max(res, sum*pq.peek().pro);
                }
            }
        }
        return res;
    }

    // =============== Knight Move ==============
    private static Map<String, Integer> memo = new HashMap<>();
    private static int dfs(int x, int y) {
        String key = x + "," + y;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        if (x + y == 0) {
            return 0;
        } else if (x + y == 2) {
            return 2;
        } else {
            Integer ret = Math.min(dfs(Math.abs(x - 1), Math.abs(y - 2)),
                    dfs(Math.abs(x - 2), Math.abs(y - 1))) + 1;
            memo.put(key, ret);
            return ret;
        }
    }
    public static int minKnightMoves(int x, int y) {
        return dfs(Math.abs(x), Math.abs(y));
    }

    // =============== Website pagination ==============
    public static String[] fetchItemsToDisplay(String[][] items, int sortPara, int sortOrd, int itemsPerPage, int pageNum){
        if (sortOrd == 0){
            Arrays.sort(items, new Comparator<String[]>() {
                @Override
                public int compare(String[] o1, String[] o2) {
                    if (sortPara == 0) return o1[0].compareTo(o2[0]);
                    return Integer.valueOf(o1[sortPara])-Integer.valueOf(o2[sortPara]);
                }
            });
        }else{
            Arrays.sort(items, new Comparator<String[]>() {
                @Override
                public int compare(String[] o1, String[] o2) {
                    if (sortPara == 0) return o2[0].compareTo(o1[0]);
                    return Integer.valueOf(o2[sortPara])-Integer.valueOf(o1[sortPara]);
                }
            });
        }
        int ide = pageNum*itemsPerPage;
        List<String> ls = new ArrayList<>();
        int size = Math.min(ide + itemsPerPage, items.length);
        for (;ide < size;ide++){
            ls.add(items[ide][0]);
        }
        String[] res = new String[ls.size()];
        for (int i=0;i<ls.size();i++){
            res[i] = ls.get(i);
        }
        return res;
    }

    // =============== Intelligent substring ==============
    public static int getSpecialSubstring(String s, int k, String charValue){
        Set<Character> set = new HashSet<>();
        int ide = 0, len = s.length();
        for (char c: charValue.toCharArray()){
            if (c == '0'){
                set.add((char)('a'+ide));
            }
            ide++;
        }
        int left = 0, right = 0, cnt = 0, res = 0;
        while (right < len){
            if (set.contains(s.charAt(right))){
                cnt++;
                if (cnt > k){
                    while (left < right){
                        if (set.contains(s.charAt(left++))){
                            cnt--;
                        }
                    }
                }
            }
            res = Math.max(res, right-left+1);
            right++;
        }
        return res;
    }

    // Sqrt(x, precision) !! Sort (0<=num<=200 No Duplicate) !! Connect 4 Design !!
    // Word Break 输出最长的组合
    // The Longest Consecutive Subarray and Sequence
    // LC54 LC59 LC33 LC46 LC47 LC67 LC121 LC269 LC42 LC1152 LC239 LC973 LC1779
    // LC1472!! LC986 LC429 LC62 LC15 LC759 LC1229 LC399 LC1359!! LC1235!! LC124
    // Find Overlapped Intervals
    // The Size of The Largest Contiguous Block (LC200 LC695 变形) !!
    // BST Path of Two Nodes
    // All Distinct Paths to Reach Destinations within K Steps !!
    // Resolve Library Dependency （LC210 变种) !!
    // Valid Delivery

    /*
     Given a list of time blocks where a particular person is already booked/busy,
     a start and end time to search between, a minimum duration to search for,
     find all the blocks of time that a person is free for a potential meeting that will last the aforementioned duration.
     Given: start_time, end_time, duration, meetings_list -> suggested_meeting_times
     */
    public static List<int[]> minAvailableDuration(int[][] slots, int startTime, int endTime, int duration){
        Arrays.sort(slots, Comparator.comparing(a->a[0]));
        List<int[]> res = new ArrayList<>();
        for (int i=0;i<slots.length;i++){
            if (slots[i][1] <= startTime) continue;
            if (slots[i][0] >= startTime && slots[i][0] <= endTime){
                if (i == 0 && slots[i][0] - startTime >= duration){
                    res.add(new int[]{startTime, slots[i][0]});
                }else if (slots[i][0] - Math.max(startTime, slots[i-1][1]) >= duration){
                    res.add(new int[]{Math.max(startTime, slots[i-1][1]), slots[i][0]});
                }
            }else if (slots[i][0] > endTime){
                if (i == 0){
                    if (endTime - startTime >= duration) res.add(new int[]{startTime, endTime});
                }else if (endTime - slots[i-1][1] >= duration){
                    res.add(new int[]{slots[i-1][1], endTime});
                }
            }
        }
        if (endTime - slots[slots.length-1][1] >= duration) res.add(new int[]{slots[slots.length-1][1], endTime});
        return res;
    }

    // Generate All Possible Delivery Options
    static List<List<String>> delivery = new ArrayList<>();
    public static List<List<String>> allDelivery(int n){
        helper(n, new ArrayList<>(), new int[n]);
        return delivery;
    }
    private static void helper(int n, List<String> ls, int[] arr){
        if (ls.size() == n*2){
            List<String> tmp = new ArrayList<>(ls);
            delivery.add(tmp);
            return;
        }
        for (int i=0;i<n;i++){
            if (arr[i] == 0){
                ls.add("P"+i);
                arr[i] = 1;
                helper(n, ls, arr);
                arr[i] = 0;
                ls.remove(ls.size()-1);
            }else if (arr[i] == 1){
                ls.add("D"+i);
                arr[i] = 2;
                helper(n, ls, arr);
                arr[i] = 1;
                ls.remove(ls.size()-1);
            }
        }
    }

    /*
    At DoorDash, menus are updated daily even hourly to keep them up-to-date.
    Each menu can be regarded as a tree. When the merchant sends us the latest menu,
    can we calculate how many nodes have changed/added/deleted?
    class Node {
        String key;
        int value;
        List<Node> children;
    }
    Assume there are no duplicate nodes with the same key.
    Output: Return the number of changed nodes in the tree.
    */
    static class TreeNode{
        int key;
        int value;
        List<TreeNode> children;
    }
    public int findDiffNodes(TreeNode root1, TreeNode root2) {
         if (root1 == null && root2 == null) return 0;
         if ((root1 != null && root2 == null) || (root1 == null && root2 != null) || (root1.key != root2.key)) {
             return countNodes(root1) + countNodes(root2);
         }
         int diffCount = 0;
         if (root1.value != root2.value) diffCount += 2;
         List<TreeNode> children1 = root1.children;
         List<TreeNode> children2 = root2.children;
         Map<Integer, TreeNode> childrenMap1 = new HashMap<>();
         for (TreeNode child1 : children1) {
             childrenMap1.put(child1.key, child1);
         }
         Map<Integer, TreeNode> childrenMap2 = new HashMap<>();
         for (TreeNode child2 : children2) {
             childrenMap2.put(child2.key, child2);
         }

         for (int key1 : childrenMap1.keySet()) {
             TreeNode child1 = childrenMap1.get(key1);
             if (!childrenMap2.containsKey(key1)) {
                 diffCount += countNodes(child1);
             } else {
                 TreeNode child2 = childrenMap2.get(key1);
                 diffCount += findDiffNodes(child1, child2);
                 childrenMap2.remove(key1);
             }
         }
         for (TreeNode child2 : childrenMap2.values()) {
             diffCount += countNodes(child2);
         }
         return diffCount;
    }

    private int countNodes(TreeNode node) {
        if (node == null) return 0;
        int count = 1;
        for (TreeNode child : node.children) {
             count += countNodes(child);
        }
        return count;
    }

    // Similar Words and K-anagram
    public static List<String> getAnagram(String name, String[] words, int K){
        List<String> res = new ArrayList<>();
        if (K == 0){ // For Similar
            for (String w: words){
                if (isSimilar(name, w)){
                    res.add(w);
                }
            }
        }else if (K < 0){
            K = -K;
            for (String w: words){
                if (canReplace(name, w, K)){
                    res.add(w);
                }
            }
        }else{
            for (String w: words){
                if (minKSwap(name, w) <= K){
                    res.add(w);
                }
            }
        }
        return res;
    }
    private static boolean isSimilar(String s1, String s2){
        int cnt = 0;
        Integer[] pos1 = new Integer[4];
        for (int i=0;i<s1.length();i++){
            char c1 = s1.charAt(i), c2 = s2.charAt(i);
            if (c1 != c2){
                if (cnt >= 4) return false;
                pos1[cnt++] = i;
            }
        }
        if (cnt % 2 != 0) return false;
        if (cnt == 0) return true;
        if (cnt == 2) return s1.charAt(pos1[0]) == s2.charAt(pos1[1]) && s2.charAt(pos1[0]) == s1.charAt(pos1[1]);
        char c11 = s1.charAt(pos1[0]), c12 = s1.charAt(pos1[1]), c13 = s1.charAt(pos1[2]), c14 = s1.charAt(pos1[3]);
        char c21 = s2.charAt(pos1[0]), c22 = s2.charAt(pos1[1]), c23 = s2.charAt(pos1[2]), c24 = s2.charAt(pos1[3]);
        return  c11 == c22 && c12 == c21 && c13 == c24 && c14 == c23 ||
                c12 == c23 && c13 == c22 && c11 == c24 && c14 == c21;
    }
    private static boolean canReplace(String s1, String s2, int k){
        int cnt = 0;
        for (int i=0;i<s1.length();i++){
            char c1 = s1.charAt(i), c2 = s2.charAt(i);
            if (c1 != c2){
                cnt++;
                if (cnt > k) return false;
            }
        }
        return true;
    }
    public static int minKSwap(String A, String B) {
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(A);
        visited.add(A);
        int level = 0;
        while (!queue.isEmpty()) {
            int sz = queue.size();
            for (int i = 0; i < sz; i++) {
                String curNode = queue.poll();
                if (curNode.equals(B)) {
                    return level;
                }
                for (String neighbour : getNeighbours(curNode, B)) {
                    if (!visited.contains(neighbour)) {
                        queue.offer(neighbour);
                        visited.add(neighbour);
                    }
                }
            }
            level++;
        }
        return -1;
    }
    private static List<String> getNeighbours(String S, String B) {
        List<String> result = new ArrayList<>();
        char[] arr = S.toCharArray();
        int i = 0;
        for (; i < arr.length; i++) {
            if (arr[i] != B.charAt(i)) {
                break;
            }
        }
        for (int j = i + 1; j < arr.length; j++) {
            if (arr[j] == B.charAt(i)) {
                swap(arr, i, j);
                result.add(new String(arr));
                swap(arr, i, j);
            }
        }
        return result;
    }
    private static void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // Encoding Hours (24小时制的 如果是ampm的转换一下就行)
    public static List<String> encodeHours(String start, String end){
        String[] arr1 = start.split("\\s+"), arr2 = end.split("\\s+");
        Map<String,Integer> map = new HashMap<>();
        map.put("Mon",1);map.put("Tue",2);
        map.put("Wed",3);map.put("Thu",4);
        map.put("Fri",5);map.put("Sat",6);
        map.put("Sun",7);
        List<String> res = new ArrayList<>();
        if (arr1.length == 2){
            String day1 = arr1[0], day2 = arr2[0];
            int cnt = map.get(day2) - map.get(day1), num = map.get(day1);
            String[] time1 = arr1[1].split(":"), time2 = arr2[1].split(":");
            int sh1 = Integer.parseInt(time1[0]), eh1 = Integer.parseInt(time2[0]),
                    sm1 = Integer.parseInt(time1[1]), em1 = Integer.parseInt(time2[1]);
            if (cnt == 0) {
                while (sh1 <= eh1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(map.get(day1));
                    if (sh1 < 10) sb.append(0);
                    sb.append(sh1);
                    if (sm1 < 10) sb.append(0);
                    sb.append(sm1);
                    res.add(sb.toString());
                    sm1 += 5;
                    if (sh1 == eh1 && sm1 > em1) break;
                    if (sm1 >= 60){
                        sm1 %= 60;
                        sh1++;
                    }
                }
            }else{
                int et = 23;
                while (sh1 <= et) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(num);
                    if (sh1 < 10) sb.append(0);
                    sb.append(sh1);
                    if (sm1 < 10) sb.append(0);
                    sb.append(sm1);
                    res.add(sb.toString());
                    sm1 += 5;
                    if (cnt == 0 && sh1 == et && sm1 > em1) break;
                    if (sm1 >= 60){
                        sm1 %= 60;
                        sh1++;
                        if (sh1 >= 24){
                            num++;cnt--;
                            if (cnt == 0) et = eh1;
                            sh1 = 0;
                        }
                    }
                }
            }

        }
        return res;
    }

    // Max Profit between Start Time and End Time (LC1235 变种) !!
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit, int start, int end) {
        int n = startTime.length;
        int[][] jobs = new int[n][3];
        for (int i = 0; i < n; i++) {
            if (startTime[i] >= start && endTime[i] <= end){
                jobs[i] = new int[] {startTime[i], endTime[i], profit[i]};
            }
        }
        Arrays.sort(jobs, (a, b)->a[1] - b[1]);
        TreeMap<Integer, Integer> dp = new TreeMap<>();
        dp.put(0, 0);
        for (int[] job : jobs) {
            int cur = dp.floorEntry(job[0]).getValue() + job[2];
            if (cur > dp.lastEntry().getValue())
                dp.put(job[1], cur);
        }
        return dp.lastEntry().getValue();
    }

    // Free Time
    static class Interval {
        public int start;
        public int end;
        public Interval() {}
        public Interval(int _start, int _end) {
            start = _start;
            end = _end;
        }
    }
    public static List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        List<Interval> res = new ArrayList<>();
        List<Interval> allInts = mergeAll(schedule);
        for (int i=0;i<allInts.size()-1;i++){
            res.add(new Interval(allInts.get(i).end,allInts.get(i+1).start));
        }
        return res;
    }
    private static List<Interval> mergeAll(List<List<Interval>> ls){
        List<Interval> res = new ArrayList<>();
        for (int i=0;i<ls.size();i++){
            res = merge(res, ls.get(i));
        }
        return res;
    }
    private static List<Interval> merge(List<Interval> l1, List<Interval> l2){
        int ptr1 = 0, ptr2 = 0, len1 = l1.size(), len2 = l2.size();
        List<Interval> res = new ArrayList<>();
        if (l1.size() == 0) return l2;
        Interval in = (l1.get(0).start < l2.get(0).start)? l1.get(ptr1++):l2.get(ptr2++);
        while (ptr1 < len1 || ptr2 < len2){
            if (ptr2 == len2 || ptr1 < len1 && l1.get(ptr1).start < l2.get(ptr2).start){
                int start1 = l1.get(ptr1).start, end1 = l1.get(ptr1).end;
                if (in.end < start1){
                    res.add(in);
                    in = l1.get(ptr1);
                }else{
                    in.end = Math.max(in.end, end1);
                }
                ptr1++;
            }else{
                int start2 = l2.get(ptr2).start, end2 = l2.get(ptr2).end;
                if (in.end < start2){
                    res.add(in);
                    in = l2.get(ptr2);
                }else{
                    in.end = Math.max(in.end, end2);
                }
                ptr2++;
            }
        }
        res.add(in);
        return res;
    }

    // Binary Tree ->
    // Find Max Path Sum from Leaf to Leaf ->
    // Some Leaves Have Tag, Find Max Path Sum from Tagged Leaves ->
    // Tagged Node May Not Be Leaf, Find Max Path Sum from Tagged Leaves
    static class Tree{
        int val;
        Tree left;
        Tree right;
        boolean tagged;
        public Tree(int val, Tree left, Tree right, boolean tagged){
            this.val = val;
            this.left = left;
            this.right = right;
            this.tagged = tagged;
        }
        public Tree(){}
    }
    static int maxSubSum = Integer.MIN_VALUE;
    public static int pathSumLtoL(Tree root){
        dfs(root);
        return maxSubSum;
    }
    private static int dfs(Tree root){
        if (root == null) return 0;
        int l = dfs(root.left), r = dfs(root.right);
        maxSubSum = Math.max(maxSubSum, l+r+root.val);
        return Math.max(l,r)+root.val;
    }
    private static int[] dfsTagged(Tree root){
        if (root == null) return new int[]{0,-1};
        int[] l = dfsTagged(root.left), r = dfsTagged(root.right);
        int ret = 0, flag = (l[1] == 1 || r[1] == 1)? 1 : -1;
        if (root.tagged){
            ret += root.val;
            flag = 1;
        }
        if (l[1] == 1 && r[1] == 1) {
            maxSubSum = Math.max(maxSubSum, l[0]+r[0]+root.val);
            ret += Math.max(l[0],r[0]) + root.val;
        }else if (l[1] == 1){
            maxSubSum = Math.max(maxSubSum, l[0]+root.val);
            ret += l[0]+root.val;
        }else if (r[1] == 1){
            maxSubSum = Math.max(maxSubSum, r[0]+root.val);
            ret += r[0]+root.val;
        }
        return new int[]{ret, flag};
    }

    // Nearest Neighbour City
    static class City implements Comparable<City>{
        int coord;
        String name;
        public City(int c, String city) {
            this.coord = c;
            this.name = city;
        }

        @Override
        public int compareTo(City c) {
            return Integer.compare(this.coord, c.coord);
        }
    }
    public static List<String> closestCity(List<String> c, List<Integer> x, List<Integer> y, List<String> queries) {
        // Initialize maps
        Map<String, int[]> cityMap = new HashMap<>();
        Map<Integer, List<City>> xMap = new HashMap<>();
        Map<Integer, List<City>> yMap = new HashMap<>();
        int N = c.size();
        for(int i=0; i<N; i++) {
            int xpos = x.get(i), ypos = y.get(i);
            String name = c.get(i);
            xMap.putIfAbsent(xpos, new ArrayList<>());
            xMap.get(xpos).add(new City(ypos, name));
            yMap.putIfAbsent(y.get(i), new ArrayList<>());
            yMap.get(ypos).add(new City(xpos, name));
            cityMap.put(c.get(i), new int[] {xpos, ypos});
        }
        for(int xKey : xMap.keySet()) {
            Collections.sort(xMap.get(xKey));
        }
        for(int yKey : yMap.keySet()) {
            Collections.sort(yMap.get(yKey));
        }

        // Do queries
        List<String> result = new ArrayList<>();
        for(String q : queries) {
            int[] location = cityMap.get(q);
            List<City> xLs = xMap.get(location[0]);
            List<City> yLs = yMap.get(location[1]);
            City closestX = getDistOnAxis(xLs, location[1], q);
            City closestY = getDistOnAxis(yLs, location[0], q);
            result.add(getResult(closestX, closestY, location));
        }
        return result;
    }
    private static String getResult(City x, City y, int[] location) {
        int dis1 = Math.abs(location[0] - x.coord);
        int dis2 = Math.abs(location[1] - y.coord);
        if(dis1 < dis2) {
            return x.name;
        } else if(dis1 == dis2) {
            return y.name.compareTo(y.name) < 0 ? x.name : y.name;
        } else {
            return y.name;
        }
    }
    private static City getDistOnAxis(List<City> list, int location, String city) {
        int index = Collections.binarySearch(list, new City(location, city));
        City leftCity = null, rightCity = null;
        int leftDist = Integer.MAX_VALUE, rightDist = Integer.MAX_VALUE;
        if(index > 0) {
            leftCity = list.get(index - 1);
            leftDist = Math.abs(leftCity.coord - location);
        }
        if(index < list.size() - 1) {
            rightCity = list.get(index + 1);
            rightDist = Math.abs(rightCity.coord - location);
        }
        if(leftDist < rightDist) {
            return leftCity;
        } else if(leftDist == rightDist) {
            return leftCity.name.compareTo(rightCity.name) < 0 ? leftCity : rightCity;
        } else {
            return rightCity;
        }
    }
    
    // Shortest Path
    public static boolean[] shortestPath(int cityNum, int[] fromCitys, int[] toCitys, int[] weights) {
        int start = 1, end = cityNum, roads = fromCitys.length;
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int i = 0; i < roads; i++) {
            int roadId = i + 1;
            int from = fromCitys[i];
            int to = toCitys[i];
            int weight = weights[i];
            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(new int[] {to, weight, roadId});
            graph.computeIfAbsent(to, k -> new ArrayList<>()).add(new int[] {from, weight, roadId});
        }

        // [city ID, start->current city weight sum]
        Queue<int[]> q = new PriorityQueue<>(Comparator.comparing(a -> a[1]));
        Set<Integer> visited = new HashSet<>();
        visited.add(start);
        q.offer(new int[] {start, 0});
        int L = 0; // shortest Path Weigh
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            if (cur[0] == end) {
                L = cur[1];
                break;
            }
            for (int[] nei : graph.get(cur[0])) {
                if (!visited.contains(nei[0])) {
                    visited.add(nei[0]);
                    q.offer(new int[] {nei[0], cur[1] + nei[1]});
                }
            }
        }
        boolean[] res = new boolean[roads];
        backtrack(start, end, graph, L, res, new HashSet<>());
        return res;
    }

    private static void backtrack(
            int city, int end, Map<Integer, List<int[]>> graph, int L, boolean[] res, Set<Integer> set) {
        if (L < 0) return;
        if (city == end && L == 0) {
            for (int rid : set) res[rid - 1] = true;
            return;
        }
        for (int[] to : graph.get(city)) {
            if (!set.contains(to[2])) {
                set.add(to[2]);
                backtrack(to[0], end, graph, L - to[1], res, set);
                set.remove(to[2]);
            }
        }
    }

    // Patching Array
    public static int minPatches(int[] nums, int n) {
        int patches = 0, i = 0;
        long miss = 1;
        while (miss <= n) {
            if (i < nums.length && nums[i] <= miss) // miss is covered
                miss += nums[i++];
            else { // patch miss to the array
                miss += miss;
                patches++; // increase the answer
            }
        }
        return patches;
    }

    // Valid Sudoku
    public boolean isValidSudoku(char[][] board) {
        int N = 9;
        int[][] rows = new int[N][N];
        int[][] cols = new int[N][N];
        int[][] boxes = new int[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (board[r][c] == '.') continue;
                int pos = board[r][c] - '1';
                if (rows[r][pos] == 1) return false;
                rows[r][pos] = 1;
                if (cols[c][pos] == 1) return false;
                cols[c][pos] = 1;
                int idx = (r / 3) * 3 + c / 3;
                if (boxes[idx][pos] == 1) return false;
                boxes[idx][pos] = 1;
            }
        }
        return true;
    }

    // Solve Sudoku
    public void solveSudoku(char[][] board) {
        if(board == null || board.length == 0) return;
        solve(board);
    }

    public boolean solve(char[][] board){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] == '.'){
                    for(char c = '1'; c <= '9'; c++){//trial. Try 1 through 9
                        board[i][j] = c;
                        if(isValidSudoku(board)){
                            if(solve(board))
                                return true; //If it's the solution return true
                            else
                                board[i][j] = '.'; //Otherwise go back
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // Walls and Gates
    private static final int EMPTY = Integer.MAX_VALUE;
    private static final int GATE = 0;
    private static final List<int[]> DIRECTIONS = Arrays.asList(
            new int[] { 1,  0},
            new int[] {-1,  0},
            new int[] { 0,  1},
            new int[] { 0, -1}
    );

    public void wallsAndGates(int[][] rooms) {
        int m = rooms.length;
        if (m == 0) return;
        int n = rooms[0].length;
        Queue<int[]> q = new LinkedList<>();
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if (rooms[row][col] == GATE) {
                    q.add(new int[] { row, col });
                }
            }
        }
        while (!q.isEmpty()) {
            int[] point = q.poll();
            int row = point[0];
            int col = point[1];
            for (int[] direction : DIRECTIONS) {
                int r = row + direction[0];
                int c = col + direction[1];
                if (r < 0 || c < 0 || r >= m || c >= n || rooms[r][c] != EMPTY) {
                    continue;
                }
                rooms[r][c] = rooms[row][col] + 1;
                q.add(new int[] { r, c });
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // ===============Computing Team Dash Quality==============
//        int[] speed = new int[]{1,1,15,15,1};
//        int[] speed = new int[]{4,3,15,5,6};
//        int[] speed = new int[]{12,112,100,13,55};
//        int[] speed = new int[]{11,10,7};
//        int[] pro = new int[]{6,4,8};
//        int[] pro = new int[]{7,6,1,2,8};
//        int[] pro = new int[]{1,1,10,10,1};
//        int[] pro = new int[]{31,4,100,55,50};
//        int maxDashers = 2;
//        System.out.println(DoorDash.maxTeamQuality(speed, pro, maxDashers));

        // =============== Website pagination ==============
//        String[][] items = new String[][]{{"item1", "10", "15"},{"item2","3","4"},{"item3","17","8"}};
////        String[][] items = new String[][]{{"p1", "1", "2"},{"p2","2","1"}};
//        int sortPara = 1, sortOrd = 0, itemsPerPage = 2, pageNum = 1;
////        int sortPara = 0, sortOrd = 0, itemsPerPage = 1, pageNum = 0;
//        for (String str: DoorDash.fetchItemsToDisplay(items, sortPara, sortOrd, itemsPerPage, pageNum)){
//            System.out.print(str + " ");
//        }

        // =============== Intelligent substring ==============
//        String s = "giraffe";
//        String s = "abcde";
//        int k = 1;
//        String charValue = "01111001111111111011111111";
//        String charValue = "10101111111111111111111111";
//        System.out.println(DoorDash.getSpecialSubstring(s, k, charValue));
//        Consumer<List<String>> item = (item) -> {
//            for (int i=0;i<item.size();i++){
//                if (item.get(i).length() == 0){
//                    item.remove(i);
//                }
//            }
//        };
//        List<String> names = new ArrayList<>(Arrays.asList("r","m", "", "","t"));
//        item.accept(names);
//        System.out.println(names);

        // MinAvailableDuration
//        int[][] input = {{3, 20}, {-2, 0}, {0, 2}, {16, 17}, {19, 23}, {30, 40}, {27, 33}};
//        int[][] input = {{-3, 20}, {5, 10}, {8, 26}, {16, 30}, {39, 43}};
//        for (int[] i : DoorDash.minAvailableDuration(input, -6, 45, 2))
//            System.out.println(Arrays.toString(i));

        // Generate All Possible Delivery Options
//        int n = 5;
//        List<List<String>> res = DoorDash.allDelivery(n);
//        System.out.println(res.size());
////        for (List<String> ls: res){
////            for (String s: ls){
////                System.out.print(s+" ");
////            }
////            System.out.println();
////        }

        // Similar Words and K-anagram
//        String name = "abcdefg";
//        String[] words = new String[]{"bacdefg","acbedfg", "acbdefg","defgabc","gfcdeba"};
//        List<String> ls = DoorDash.getAnagram(name,words,0);
//        List<String> ls = DoorDash.getAnagram(name,words,2);
//        for (String s: ls){
//            System.out.print(s+" ");
//        }

        // Encoding Hours
//        String start = "Mon 23:30", end = "Mon 23:40";
//        List<String> res = DoorDash.encodeHours(start,end);
//        for (String s: res){
//            System.out.println(s);
//        }


    }

}
