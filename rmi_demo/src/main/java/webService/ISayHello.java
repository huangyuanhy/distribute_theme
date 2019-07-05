package webService;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface ISayHello {
	@WebMethod//表明是个SEI方法
	String sayHello(String name) ;
}
