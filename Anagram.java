public class Anagram{
	
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
}