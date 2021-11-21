public class EncodeHours{
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
}