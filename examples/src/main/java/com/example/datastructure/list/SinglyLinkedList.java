package com.example.datastructure.list;

public class SinglyLinkedList<E> {
	
	private SinglyLinkedListNode<E> head;
	private SinglyLinkedListNode<E> end;
	private int size;
	
	public SinglyLinkedList(){
		this.head = null;
		this.end = null;
		this.size = 0;
	}
	
	
	
	public void addEndNode(E e){
		SinglyLinkedListNode<E> node = new SinglyLinkedListNode<E>(e);
		if(head == null){
			
			this.head = node;
			this.end = node;
			this.size = 1;
		}else{
			this.getEnd().setNext(node);
			this.end = node;
			this.size++;
		}
			
	}
	
	public void insertNodeHead(E e){
		SinglyLinkedListNode<E> node = new SinglyLinkedListNode<E>(e, head);
		this.head = node;
		size++;
	}
	
	
	public void print(){
		
		SinglyLinkedListNode<E> next = this.head;
		while(next!=null){
			System.out.println(next.getE().toString());
			next = next.getNext();
		}
		
	}
	public SinglyLinkedListNode<E> getHead() {
		return head;
	}
	public void setHead(SinglyLinkedListNode<E> head) {
		this.head = head;
	}
	public SinglyLinkedListNode<E> getEnd() {
		return end;
	}
	public void setEnd(SinglyLinkedListNode<E> end) {
		this.end = end;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
}
