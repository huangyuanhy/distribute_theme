/**
 * 
 */
package rmi;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;

/**
 * @author pc
 *启动服务
 */
public class HelloServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ISayHello hello=new SayHelloImpl();
			LocateRegistry.createRegistry(8888);
			Naming.bind("rmi://localhost:8888/sayHello", hello);
			
			System.out.println("server start sucess");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
