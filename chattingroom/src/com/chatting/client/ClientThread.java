package com.chatting.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.chatting.common.Constant;
import com.chatting.common.Utility;

public class ClientThread extends Thread{

    private ClientUI ui;
    private Socket client;
    private BufferedReader reader;
    private PrintWriter writer;
    
    
	public ClientThread(ClientUI clientUI) {
		// TODO Auto-generated constructor stub
		this.ui = clientUI;
        try {
            client = new Socket(Constant.SERVER_ADDRESS, Constant.PORT);
            
            Utility.print(this.ui.taShow, Constant.SUCCESS);
            reader = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));
            writer = new PrintWriter(client.getOutputStream(), true);
            
        } catch (IOException e) {
        	 Utility.print(this.ui.taShow,Constant.FAIL);
            e.printStackTrace();
        }
        this.start();
	}

    public void run() {
        String msg = "";
        while (true) {
            try {
                msg = reader.readLine();
            } catch (IOException e) {
            	 Utility.print(this.ui.taShow,Constant.FAIL);

                break;
            }
            if (msg != null && msg.trim() != "") {
            	 Utility.print(this.ui.taShow,">>" + msg);
            }
        }
    }
	public void sendMsg(String text) {
		// TODO Auto-generated method stub
        try {
            writer.println(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	


}
