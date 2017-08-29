package com.example.gui;

import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

public class PracticeMultipleInput extends JFrame implements ActionListener {

	JPanel panel;
	
	JTextField input1;
	JTextField input2;
	
	JTextArea output;
	
	String newline = "\n";
	
	public PracticeMultipleInput(){
		createUI();
	}
	
	
	private void createUI() {
		// TODO Auto-generated method stub
	
		input1 = new JTextField(20);
		input2 = new JTextField(20);
		
		input1.addActionListener(this);
		input2.addActionListener(this);
		
		output = new JTextArea(100, 100);
		
		output.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		GridBagLayout gridBag = new GridBagLayout();
		
		
		
		//get the popup dialog
		panel = new JPanel();
		panel.setLayout(gridBag);
		
		
		panel.add(input1);
		panel.add(input2);
		
		
		JButton submit = new JButton("Submit");
		
		submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(panel, "Are you sure to submit the request?", 
						"Submit", JOptionPane.QUESTION_MESSAGE);
			}
			
		});
		
		panel.add(submit);
		add(panel);
		setTitle("message");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String text1 = input1.getText();
		String text2 = input2.getText();
		
		output.append(text1+newline+text2+newline);
		
	}

	public static void main(String[] args){
		PracticeMultipleInput p = new PracticeMultipleInput();
		p.pack();
		p.addWindowListener(new WindowListener(){

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		p.setVisible(true);
	}
}
