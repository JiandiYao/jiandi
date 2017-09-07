package com.chatting.server;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.chatting.common.Constant;

public class ServerUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5728634140949428099L;


	
	public JButton startButton;
	public JButton sendButton;
	public JTextField sendText;
	public JTextArea textArea;
	public Server server;
	public static List<Socket> clients;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerUI serverUI = new ServerUI();
	}
	
	public ServerUI(){
		super("Server");
		startButton = new JButton("Start Server");
		sendButton = new JButton("Send Messages");
		sendText = new JTextField(10);
		textArea = new JTextArea();
		
		startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				server = new Server(ServerUI.this); 
			}
			
		});
		
		sendButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				server.sendMsg(sendText.getText());
				sendText.setText("");
			}
			
		});
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				int a = JOptionPane.showConfirmDialog(null, Constant.CLOSE_DIALOG, 
						Constant.CLOSE_REMINDER, JOptionPane.YES_NO_OPTION);
				if(a == 1){
					server.closeServer();
					System.exit(0);
				}
			}
		});
		JPanel top = new JPanel(new FlowLayout());
		top.add(sendText);
		top.add(sendButton);
		top.add(startButton);
		this.add(top, BorderLayout.SOUTH);
		final JScrollPane sp = new JScrollPane();
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setViewportView(this.textArea);
		this.textArea.setEditable(false);
		this.add(sp, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 400);
		this.setLocation(100, 200);
		this.setVisible(true);
	}

}
