package com.example.datastructure.stack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Queue<Item> {

	private int size;
	private Node<Item> first;
	private Node<Item> last;
	
	public Queue(){
		first = null;
		last = null;
		size = 0;
	}

	public void enQueue(Item item){
		Node<Item> node  =new Node<Item>(item);
		last.setNext(node);
		last = node;
		
		if(first == null)
			first = last;
		size++;		
	}
	
	public Item deQueue(){
		if(isEmpty())
			throw new NoSuchElementException("EMpty queue");
		Node<Item> node = first;
		first = first.getNext();
		size--;
		if(isEmpty())
			last=null;
		
		return node.getItem();
	}
	
	public Item peek(){
		if(isEmpty())
			throw new NoSuchElementException("Empty queue");
		return first.getItem();
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		Node<Item> next = first;
		while(next != null){
			sb.append(next.getItem() + " ");
		}
		return sb.toString();
	}
	
	private class QueueIterator<Item> implements Iterator{

		private Node<Item> current;
		public QueueIterator(Queue<Item> queue){
			this.current = queue;
		}
		
		
		@Override
		public boolean hasNext() {
			
			return current != null;
		}

		@Override
		public Item next() {
			// TODO Auto-generated method stub
			Item next = current.getFirst().getItem();
			current = current.getFirst().getNext();
			return current.getFirst().getNext().getItem();
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
	}
	public int getSize() {
		return size;
	}


	public Node<Item> getFirst() {
		return first;
	}


	public Node<Item> getLast() {
		return last;
	}

	public boolean isEmpty(){
		return first == null;
	}
}
