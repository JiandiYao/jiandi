package com.example.RMI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientOperation {

	static RMIInterface lookup;
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		// TODO Auto-generated method stub
		lookup = (RMIInterface) Naming.lookup("//localhost/MyServer");
		System.out.println(lookup.sayHello("Jiandi"));
	}

}
