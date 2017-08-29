package com.example.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Menu(){
		initUI();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		JMenuBar menuBar = new JMenuBar();
		ImageIcon icon = new ImageIcon("image/exit.jpg");
		
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
	
		JMenuItem exit = new JMenuItem("Exit", icon);
		JMenuItem newFile = new JMenuItem("New");
		
		exit.setMnemonic(KeyEvent.VK_E);
		exit.setToolTipText("Exit the program");
		exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
		
		file.add(newFile);
		file.add(exit);
		menuBar.add(file);
		
		setJMenuBar(menuBar);
		 setTitle("Simple menu");
	        setSize(300, 200);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
}
