public class MaximumProfitinJobScheduling{
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
}