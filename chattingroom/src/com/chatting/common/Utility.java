package com.chatting.common;


import javax.swing.JTextArea;

public class Utility {

	public static void print(JTextArea ta, String msg){
		if(msg != null){
			ta.setText(ta.getText() + msg + "\n");
			System.out.println(msg);
		}
	}
}
