package com.example.gui;

import javax.swing.JButton;
import javax.swing.JFrame;

public class AbsoluteLayout extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AbsoluteLayout(){
		initUI();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		setLayout(null);
		JButton ok = new JButton("OK");
		ok.setBounds(50, 50, 80, 25);
		
		JButton close = new JButton("Close");
		close.setBounds(150, 50, 80, 25);
		
		add(ok);
		add(close);
		
		setTitle("Absolute positioning");
		setSize(300, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
}
