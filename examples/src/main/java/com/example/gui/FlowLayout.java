package com.example.gui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTree;

public class FlowLayout extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FlowLayout(){
		initUI();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel();
		
		JTextArea area = new JTextArea("text area");
		area.setPreferredSize(new Dimension(100, 100));
		
		JButton button = new JButton("button");
		panel.add(button);
		
		JTree tree = new JTree();
		panel.add(tree);
		
		panel.add(area);
		
		add(panel);
		
		pack();
		
		setTitle("FlowLayout example");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
}
