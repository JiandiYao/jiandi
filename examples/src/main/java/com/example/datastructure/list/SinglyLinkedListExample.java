package com.example.datastructure.list;

public class SinglyLinkedListExample {

	public static void main(String[] args) {
		SinglyLinkedList<String> list = new SinglyLinkedList<String>();
		list.addEndNode("I'm a girl");
		list.addEndNode("I'm a boy");
		list.insertNodeHead("I'm a dog");
		
		list.print();
	}

}
