package zkClient;

import java.util.List;

import org.I0Itec.zkclient.*;

/**
 * @author pc
 *在使用ZooKeeper的Java客户端时，
 *经常需要处理几个问题：重复注册watcher、session失效重连、异常处理。

       要解决上述的几个问题，可以自己解决，也可以采用第三方的java客户端来完成。
       这里就介绍一种常用的客户端zkclient，目前已经运用到了很多项目中，知名的有Dubbo、Kafka、Helix。
 */
public class zkClientAPI {
	private static ZkClient instance;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		instance=Utils.getInstance();

	}
	//创建临时/持久化节点,可以递归创建 底层还是调用原生的JavaAPI
	public static void demo() {
		instance.createPersistent("/zkclient/zkclient01");
		System.out.println("success");
		instance.deleteRecursive("/zkclient");
		List<String> children = instance.getChildren("/node");
		
		//watcher
		//instance.subscribeDataChanges("", new IZkDataListener(){});
		
	}

}
