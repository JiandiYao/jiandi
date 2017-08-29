package com.example.gui;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class HelloWorld extends JFrame{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//	public static void main(String[] args){
//		 EventQueue.invokeLater(new Runnable() {
//	            @Override
//	            public void run() {
////	                HelloWorld e = new HelloWorld();
////	                e.setVisible(true);
////	            	Menu ex = new Menu();
////	            	AbsoluteLayout ex = new AbsoluteLayout();
//	            	FlowLayout ex = new FlowLayout();
//	                ex.setVisible(true);
//	            }
//	        });
//		
//	}
	public HelloWorld(){
		JLabel jlbHelloWorld = new JLabel("Hello World");

	
//	    add(jlbHelloWorld);
//	    this.setSize(500,500);

//        setVisible(false);
        
        JButton jButton = new JButton("Submit");
        jButton.add(jlbHelloWorld);
        add(jButton);
        this.setSize(100, 100);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
