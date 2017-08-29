package com.example.datastructure.stack;

public class Node<Item> {

	Item item;
	Node<Item> next;
	
	public Node(Item item){
		this.item = item;
		next = null;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Node<Item> getNext() {
		return next;
	}

	public void setNext(Node<Item> next) {
		this.next = next;
	}
	
	
}
