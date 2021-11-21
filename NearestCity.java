public class NearestCity{
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
    
}