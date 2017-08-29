package com.example.datastructure.list;

public class SinglyLinkedListNode<E> {

	private E e;
	private SinglyLinkedListNode<E> next;
	
	
	public SinglyLinkedListNode(E e){
		this.e = e;
		this.next = null;
	}
	public SinglyLinkedListNode(E e, SinglyLinkedListNode<E> next){
		this.e = e;
		this.next = next;
	}
	
	
	
	public E getE() {
		return e;
	}

	public void setE(E e) {
		this.e = e;
	}

	public void setNext(SinglyLinkedListNode<E> next){
		this.next = next;
	}
	
	public SinglyLinkedListNode<E> getNext(){
		return next;
	}
}
