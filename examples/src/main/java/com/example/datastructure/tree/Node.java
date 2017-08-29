package com.example.datastructure.tree;

public class Node<Item> {
	private Node<Item> firstChild;
	private Node<Item> sibling;
	private Item item;
	public Node(Item item){
		this.item = item;
		firstChild = null;
		sibling = null;
	}
	public Node<Item> getFirstChild() {
		return firstChild;
	}
	public void setFirstChild(Node<Item> firstChild) {
		this.firstChild = firstChild;
	}
	public Node<Item> getSibling() {
		return sibling;
	}
	public void setSibling(Node<Item> sibling) {
		this.sibling = sibling;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	
}
