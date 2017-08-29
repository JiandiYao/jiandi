package com.example.datastructure.tree;

public class Tree {
	private Node head;
	private int size;
	public Tree(){
		head = null;
		size = 0;
	}
	public Node getHead() {
		return head;
	}
	public void setHead(Node head) {
		this.head = head;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
}
//class Node{
//	int e;
//	Node child;
//	Node sibling;
//	Node(int e){
//		this.e = e;
//		child = null;
//		sibling = null;
//	}
//	public int getE() {
//		return e;
//	}
//	public void setE(int e) {
//		this.e = e;
//	}
//	public Node getChild() {
//		return child;
//	}
//	public void setChild(Node child) {
//		this.child = child;
//	}
//	public Node getSibling() {
//		return sibling;
//	}
//	public void setSibling(Node sibling) {
//		this.sibling = sibling;
//	}
//	
//}