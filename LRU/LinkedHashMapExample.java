package linkedhashmap;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Map<Integer, String> map = new LinkedHashMap<Integer, String>(2);
		map.put(1, "1");
		map.put(2, "2");
		map.put(3, "3");
		map.put(4, "4");
		map.put(5, "5");
		map.put(6, "6");
		for(int key: map.keySet()){
			System.out.println(map.get(key));
		}
		
	}

}
