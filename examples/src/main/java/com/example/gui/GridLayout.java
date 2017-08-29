package com.example.gui;

import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GridLayout extends JFrame{

	public GridLayout(){
		initUI();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		panel.setLayout(new GridLayout(5,4,5,5));
		String[] buttons = {
				"Cls", "Bck", "Close", "7", "8", "9", "/"
		};
	}
}
