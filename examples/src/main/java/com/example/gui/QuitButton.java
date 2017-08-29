package com.example.gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class QuitButton extends JFrame{

	public QuitButton(){
		initUI();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		Container pane = getContentPane();
		GroupLayout layout = new GroupLayout(pane);
		pane.setLayout(layout);
		
		JButton quitButton = new JButton("Quit");
		quitButton.setToolTipText("the program will quit");
		
//		JButton submit = new JButton("Submit");
		quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }

        });

		
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addComponent(quitButton).addGap(100)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(quitButton).addGap(200)
        );
        
//        layout.setVerticalGroup(layout.createSequentialGroup().addComponent(submit));
//        layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(submit));
        
        pack();

        setTitle("Quit button");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
