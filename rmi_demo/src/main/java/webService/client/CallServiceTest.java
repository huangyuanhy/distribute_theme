package webService.client;

public class CallServiceTest {

	public static void main(String[] args) {
		SayHelloImplService service = new SayHelloImplService();
		SayHelloImpl proxy = service.getSayHelloImplPort();
		System.out.println(proxy.sayHello("huangyuan"));

	}

}
