package com.example.datastructure.list;

public class DoublyLinkedListExample {

	public static void main(String[] args) {
		DoublyLinkedList<String> list = new DoublyLinkedList<String>();
		list.addHead("I'm a girl");
		list.addTail("I'm a boy");
		list.addHead("I'm a bay");
		list.print();
	}

}
