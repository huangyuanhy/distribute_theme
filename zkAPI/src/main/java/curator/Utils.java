package curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Utils {
	private static String group="hadoop1:2181,"
			+ "hadoop1:2181,"
			+ "hadoop1:2181,";
	
	public static CuratorFramework getConnection() {
		//normal
		CuratorFramework connection = CuratorFrameworkFactory.newClient(group, 5000, 5000, new ExponentialBackoffRetry(1000, 3));
		
		connection.start();
		
		CuratorFrameworkState state = connection.getState();
		
		return connection;
	}
	
	//fluent风格
	public static CuratorFramework getConnectionFluent() {
		CuratorFramework connection = CuratorFrameworkFactory.builder().connectString(group)
				.sessionTimeoutMs(10000).retryPolicy(new ExponentialBackoffRetry(1000, 3, 100))
				.build();
		connection.start();
		return connection;
	}
}









