package linkedhashmap;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {

	
	class Node {
		Node next;
		Node previous;
		int val;
		int key;
		public Node(int key, int val){
			this.key = key;
			this.val = val;
		}
	}
	private static final int MAX_CAP = 3;
	Map<Integer, Node> map;
	Node head;
	Node tail;
	int capacity;
	
	public LRUCache(){
		map = new HashMap<Integer, Node>();
		head = null;
		tail = null;
		capacity = 0;
	}
	public static void main(String[] args){
		 
		LRUCache cache = new LRUCache();
		cache.put(1, 1);
		cache.put(2, 2);
		cache.put(3, 3);
		cache.put(4, 4);
		cache.put(5, 5);
		cache.put(6, 6);
		cache.put(7, 7);
		
		System.out.println(cache);
	}
	
	public void put(int key, int val){
		
		
		if(map.containsKey(key)){
			map.get(key).val = val;
		}else{
			Node n = new Node(key, val);
			map.put(key, n);
			
			if(head == null){
				head = n;
				tail = n;
			}
			
			Node tmp = head;
			head = n;
			head.next = tmp;
			tmp.previous = head;
			
			
			
			if(capacity >= MAX_CAP){
				 tmp = tail;
				tail = tail.previous;
				tail.next = null;
				map.remove(tmp.key);
			}else{
				capacity ++;
				
			}
		}
	}
	
	
	public Integer get(int key){
		if(map.containsKey(key)){
			return map.get(key).val;
		}
		return null;
	}
	
	public void delete(int key){
		if(map.containsKey(key)){
			Node n = map.get(key);
			n.previous.next = n.next;
			n.next.previous = n.previous;
			map.remove(key);
		}
	}
	
	@Override
	public String toString(){
		Node n = head;
		StringBuilder sb = new StringBuilder();
		sb.append("key-value pairs: ");
		while(n != null){
			sb.append(" ["+n.key +","+ n.val+"] ");
			n = n.next;
		}
		return sb.toString();
	}
}
