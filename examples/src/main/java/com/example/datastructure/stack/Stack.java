package com.example.datastructure.stack;

public class Stack {

	private int size;
	private int[] array;
	private int top;
	public Stack(int size){
		this.size = size;
		array = new int[size];
		top = -1;
	}
	
	public void push(int a){
		array[++top] = a;
	}
	public int pop(){
			return array[top--];
		
	}
	public int peek(){
		return array[top];
	}
}
