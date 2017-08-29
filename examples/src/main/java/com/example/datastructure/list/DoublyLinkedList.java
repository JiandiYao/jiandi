package com.example.datastructure.list;

public class DoublyLinkedList<E> {

	private DoublyLinkedListNode<E> head;
	private DoublyLinkedListNode<E> tail;
	private int size = 0;
	
	public DoublyLinkedList(){
		this.head = null;
		this.tail = null;
		this.size = 0;
	}
	
	public DoublyLinkedList(E e){
		DoublyLinkedListNode<E> node = new DoublyLinkedListNode<E>(e);
		this.head = node;
		this.tail = node;
		this.size = 1;
	}
	
	public void addTail(E e){
		DoublyLinkedListNode<E> node = new DoublyLinkedListNode<E>(e);
		if(this.head == null){
			
			this.head = node;
			this.tail = node;
			this.size = 1;
		}else{
			this.tail.setNext(node);
			node.setPrevious(this.tail);
			this.tail = node;
			size++;
		}
	}
	
	public void addHead(E e){
		DoublyLinkedListNode<E> node = new DoublyLinkedListNode<E>(e);
		if(this.head == null){
			
			this.head = node;
			this.tail = node;
			this.size = 1;
		}else{
			this.head.setPrevious(node);
			node.setNext(this.head);
			this.head = node;
			size++;
		}
	}
	
	public void print(){
		
		DoublyLinkedListNode<E> next = this.head;
		while(next!=null){
			System.out.println(next.getE().toString());
			next = next.getNext();
		}
		
	}
	
	public DoublyLinkedListNode<E> getHead() {
		return head;
	}
	public void setHead(DoublyLinkedListNode<E> head) {
		this.head = head;
	}
	public DoublyLinkedListNode<E> getTail() {
		return tail;
	}
	public void setTail(DoublyLinkedListNode<E> tail) {
		this.tail = tail;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
}
