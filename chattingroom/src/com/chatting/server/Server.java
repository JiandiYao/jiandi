package com.chatting.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.chatting.common.Constant;
import com.chatting.common.Utility;

public class Server extends Thread{

	private ServerUI ui;
	private ServerSocket ss;
	private BufferedReader reader;
	private PrintWriter writer;
	
	
	public Server(ServerUI serverUI) {
		// TODO Auto-generated constructor stub
		this.ui = serverUI;
		this.start();
	}

	
	public void run(){
		try{
			ss = new ServerSocket(Constant.PORT);
			ServerUI.clients = new ArrayList<Socket>();
			Utility.print(this.ui.textArea, Constant.SERVER_STARTING_MSG);
			
			while(true){
				Utility.print(this.ui.textArea, Constant.WAITING_FOR_CLIENTS);
				Socket client = ss.accept();
				ServerUI.clients.add(client);
				Utility.print(this.ui.textArea, "Client connected: " + client.toString());
				new ListenClient(ui, client);
			}
		}catch(IOException e){
			Utility.print(this.ui.textArea, Constant.FAIL);
		}
	}



	public synchronized void sendMsg(String text) {
		// TODO Auto-generated method stub
		try {
			for(int i = 0; i < ServerUI.clients.size(); i ++){
				Socket client = ServerUI.clients.get(i);
				
				writer = new PrintWriter(client.getOutputStream(), true);
				writer.print(text);
			
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void closeServer() {
		// TODO Auto-generated method stub
		try {
			ss.close();
			reader.close();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
