package com.example.RMI;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerOperation extends UnicastRemoteObject implements RMIInterface{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3554029529834477181L;

	protected ServerOperation() throws RemoteException {

        super();

    }
	
	public String sayHello(String name) throws RemoteException {
		return "Hello " + name ;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			Naming.rebind("//localhost/MyServer", new ServerOperation());
//			Registry registry = LocateRegistry.getRegistry();
//			registry.bind(name, obj);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}



}
