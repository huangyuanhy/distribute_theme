package webService;

import javax.jws.WebService;

@WebService
public class SayHelloImpl implements ISayHello{

	
	public String sayHello(String name) {
		 System.out.println("call say hello");
		return "Hello :"+name;
	}

}
