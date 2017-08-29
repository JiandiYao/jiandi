package com.example.gui;
//package com.practice.example;
//

//import java.awt.*;
//import java.awt.event.*;
//
//import javax.swing.*;
//
//public class JTextFieldDemo2 extends JFrame implements ActionListener {
//	JTextField jtfInput;
//
//	JTextArea jtAreaOutput;
//
//	String newline = "\n";
//
//	public JTextFieldDemo2() {
//		createGui();
//	}
//
//	public void createGui() {
//		jtfInput = new JTextField(20);
//		jtfInput.addActionListener(this);
//
//		jtAreaOutput = new JTextArea(5, 20);
//		jtAreaOutput.setEditable(false);
//		JScrollPane scrollPane = new JScrollPane(jtAreaOutput,
//				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		
//		GridBagLayout gridBag = new GridBagLayout();
//		Container contentPane = getContentPane();
//		contentPane.setLayout(gridBag);
//		
//		GridBagConstraints gridBagConstraintsx1 = new GridBagConstraints();
//        gridBagConstraintsx1.gridwidth = GridBagConstraints.REMAINDER;
//        gridBagConstraintsx1.fill = GridBagConstraints.HORIZONTAL;
//        contentPane.add(jtfInput, gridBagConstraintsx1);
//        
//        GridBagConstraints gridBagConstraintsx2 = new GridBagConstraints();
//        gridBagConstraintsx2.weightx = 1.0;
//        gridBagConstraintsx2.weighty = 1.0;
//        contentPane.add(scrollPane, gridBagConstraintsx2);
//
//	}
//
//	public void actionPerformed(ActionEvent evt) {
//		String text = jtfInput.getText();
//		jtAreaOutput.append(text + newline);
//		jtfInput.selectAll();
//	}
//	
//	   public static void main(String[] args) {
//		   JTextFieldDemo2 jtfTfDemo = new JTextFieldDemo2();		
//	        jtfTfDemo.pack();
//	        jtfTfDemo.addWindowListener(new WindowAdapter() {
//	            public void windowClosing(WindowEvent e) {
//	                System.exit(0);
//	            }
//	        });
//	        jtfTfDemo.setVisible(true);
//	    }
//}

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class JTextFieldDemo2 extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField jtfInput;
	JTextArea jtAreaOutput;
	String newline= "\n";
	
	
	public JTextFieldDemo2(){
		initUI();
	}


	private void initUI() {
		// TODO Auto-generated method stub
		jtfInput = new JTextField();
		jtAreaOutput = new JTextArea(20, 20);
		
		jtfInput.addActionListener(this);
		jtAreaOutput.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(jtAreaOutput, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		GridBagLayout gridBag = new GridBagLayout();
		Container contentPane = getContentPane();
		
		contentPane.setLayout(gridBag);
		
		GridBagConstraints gridConsl = new GridBagConstraints();
		gridConsl.gridwidth = GridBagConstraints.REMAINDER;
		gridConsl.fill = GridBagConstraints.HORIZONTAL;
		
		contentPane.add(jtfInput, gridConsl);
		
		GridBagConstraints gridConsl2 = new GridBagConstraints();
		gridConsl2.weightx = 2.0;
		gridConsl2.weighty = 5.0;
		
		contentPane.add(scrollPane, gridConsl2);
	}
	
	public void actionPerformed(ActionEvent event){
		String text = jtfInput.getText();
		jtAreaOutput.append(text+newline);
		jtfInput.selectAll();
	}
	
	public static void main(String[] args){
		JTextFieldDemo2 textField = new JTextFieldDemo2();
		textField.pack();
		textField.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		textField.setVisible(true);
	}
}
