package com.example.datastructure.list;

public class DoublyLinkedListNode<E> {

	private E e;
	private DoublyLinkedListNode<E> next;
	private DoublyLinkedListNode<E> previous;

	public DoublyLinkedListNode(E e){
		this.e = e;
		this.next = null;
		this.previous = null;
	}
	
	public DoublyLinkedListNode(E e, DoublyLinkedListNode<E> next, DoublyLinkedListNode<E> previous){
		this.e = e;
		this.next = next;
		this.previous = previous;
	}
	
	
	public E getE() {
		return e;
	}
	public void setE(E e) {
		this.e = e;
	}
	public DoublyLinkedListNode<E> getNext() {
		return next;
	}
	public void setNext(DoublyLinkedListNode<E> next) {
		this.next = next;
	}
	public DoublyLinkedListNode<E> getPrevious() {
		return previous;
	}
	public void setPrevious(DoublyLinkedListNode<E> previous) {
		this.previous = previous;
	}
	
	
}
