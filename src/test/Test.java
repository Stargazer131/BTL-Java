package test;

import java.util.Map;
import java.util.TreeMap;

public class Test{
    public static void main(String[] args) {
        TreeMap<String, Integer> map = new TreeMap<>();

        map.put("1", 2);
        map.replace("1", 4);
        for(Map.Entry<String,Integer>e : map.entrySet())
        {
            System.out.println(e.getKey() + " " + e.getValue());
        }
    }
}
