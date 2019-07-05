package webService;

import javax.xml.ws.Endpoint;

public class BootStrap {
	//1. 启动后，打开http://localhost:8888/hello?wsdl   可看到wsdl文档
	//2. 在此根路径下，调出命令行窗口，输入
	//wsimport -keep http://localhost:8888/hello?wsdl  将生成的客户端代码保存在本地
	//3. 将相关的代码导入client_demo包中 ，
	//(其实生产的固定代码不可包括包名不可做任何更改，否则报错，3 4 两部只是把代码补充完成，不能运行)
	//4. 在此包中可进行客户端调用 CallServiceTest
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8888/hello", new SayHelloImpl());
		System.out.println("publish success");
	}

}
