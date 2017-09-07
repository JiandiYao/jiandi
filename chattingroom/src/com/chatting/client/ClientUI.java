package com.chatting.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.chatting.common.Constant;

public class ClientUI extends JFrame{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2739722289255062649L;
	public JButton btStart;
    public JButton btSend;
    public JTextField tfSend;
    public JTextField tfIP;
    public JTextField tfPost;
    
    public JTextArea taShow;
    public ClientThread server;
    
    private String username;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientUI client = new ClientUI();
	}
	
	public ClientUI(){
		 
		super("Client");
		btStart = new JButton("Start Connection");
	    btSend = new JButton("Send Messages");
	    tfSend = new JTextField(10);
	    tfIP = new JTextField(10);
	    tfPost = new JTextField(5);
	    taShow = new JTextArea();

	    btStart.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				server = new ClientThread(ClientUI.this);
				server.sendMsg(username);
			}
	    	
	    });
	   
	    btSend.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				server.sendMsg(tfSend.getText());
				tfSend.setText("");
			}
	    	
	    });
	   
	    this.addWindowListener(new WindowAdapter(){
	    	public void windowClosing(WindowEvent e){
	    		int a = JOptionPane.showConfirmDialog(null, Constant.CLOSE_DIALOG, 
	    				Constant.CLOSE_REMINDER,
                        JOptionPane.YES_NO_OPTION);
	    		if(a == 1)
	    			System.exit(0);
	    	}
	    });
	    
	    username = JOptionPane.showInputDialog("Input your username");
	    System.out.println("Client username is " + username);
	    
	    JPanel top = new JPanel(new FlowLayout());
	    top.add(tfSend);
	    top.add(btSend);
	    top.add(btStart);
	    this.add(top, BorderLayout.SOUTH);
	    final JScrollPane sp = new JScrollPane();
	    sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    sp.setViewportView(this.taShow);
	    this.taShow.setEditable(false);
	    this.add(sp, BorderLayout.CENTER);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(500, 400);
	    this.setLocation(600, 200);
	    this.setVisible(true);
	}

	
    
	public String getUsername() {
		return username;
	}
}
