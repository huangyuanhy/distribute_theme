package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SayHelloImpl extends UnicastRemoteObject implements ISayHello{

	protected SayHelloImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public String sayHello(String name) throws RemoteException {
		 
		return "Hello :"+name;
	}

}
