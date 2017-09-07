package com.chatting.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.chatting.common.Utility;
import com.chatting.server.ServerUI;

public class ListenClient extends Thread{
	private BufferedReader reader;
	private PrintWriter writer;
	private ServerUI ui;
	private Socket client;
	private String username;
    
	public ListenClient(ServerUI ui, Socket client) {
		// TODO Auto-generated constructor stub
		this.ui = ui;
        this.client=client;
        this.start();
        
	}

	 public void run() {
		 String msg = "";
		 	try {
				reader = new BufferedReader(new InputStreamReader(
				     client.getInputStream()));
				username = reader.readLine();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        while (true) {
	            try {
	                reader = new BufferedReader(new InputStreamReader(
	                        client.getInputStream()));
	                
	                writer = new PrintWriter(client.getOutputStream(), true);
	                msg = reader.readLine();
	                System.out.println("Recieved message from " + username + ": " + msg);
	                sendMsg(msg, username);
	                
	            } catch (IOException e) {
	                e.printStackTrace();
	                break;
	            }
	            if (msg != null && msg.trim() != "") {
	            	Utility.print(this.ui.textArea, msg);
	            }
	        }
	 }

	private synchronized void sendMsg(String msg, String username) {
		// TODO Auto-generated method stub
	       try {
	            for (int i = 0; i < ServerUI.clients.size(); i++) {
	                Socket client = ServerUI.clients.get(i);
	                writer = new PrintWriter(client.getOutputStream(), true);
	                writer.println(username + ": " + msg);
	            }

	        } catch (Exception e) {
	        	e.printStackTrace();
	        }	
		
	}


	
}
