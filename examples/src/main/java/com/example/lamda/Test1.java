package com.example.lamda;

public class Test1 {

	interface MathOper {
		int oper(int a, int b);
	}
	interface Greeting{
		void greet(String message);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MathOper add = (int a, int b) -> a+b;
		MathOper sub = (a, b) ->a -b;
		System.out.println(add.oper(1,2));
		System.out.println(sub.oper(3, 4));
		
		
	}

}
