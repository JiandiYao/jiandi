package com.example.datastructure.stack;

import java.util.ArrayList;

public class Heap {

	ArrayList<Integer> list = new ArrayList<Integer>();
	int head;
	int size;
	public Heap(int head){
		this.head = head;
		list.add(head);
	}
	public void insert(int a){
		list.add(a);
		int index = list.size();
		int tmp;
		
		while(list.get(index/2) < a){
			tmp = list.get(index/2);
			list.set(index/2, a);
			list.set(index, tmp);
			index = index/2;
		}
			
	}
	
	public int parentIndex(int i){
		return i/2;
	}
	public int parent(int i){
		return list.get(i/2);
	}
	public boolean hasParent(int index){
		return index>1;
	}
	
	public void bubbleup(int a){
		list.set(size, a);
		int index = size;
		while(hasParent(index)){
		if( list.get(parentIndex(index))< a)
			swap(a, parent(size));
		else
			break;
		index = parentIndex(index);
		}
	}
	
	public boolean hasleftchild(int index){
		return index <= list.size()-1;
	}
	
	public boolean hasrightchild(int index){
		return index <= list.size();
	}
	
	public int leftchild(int index){
		return list.get(index*2);
	}
	
	public int rightchild(int index){
		return list.get(index*2+1);
	}
	
	public int leftindex(int index){
		return index*2;
	}
	public int rightindex(int index){
		return index*2+1;
	}
	public void bubbledown(int a){
		list.set(0, a);
		int index = 1;
		while(hasleftchild(index)){
			
			int smaller = leftindex(index);
			if(hasrightchild(index)){
				if(leftchild(index) > rightchild(index)){
					smaller = rightindex(index);
				}
			}
			
			if(list.get(smaller) > list.get(index))
				swap(smaller, index);
			else
				break;
			
			index = smaller;
		}
	}
}
