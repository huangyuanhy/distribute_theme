package zkClient;

import org.I0Itec.zkclient.ZkClient;

public class Utils {
	private static String group="hadoop1:2181,"
			+ "hadoop1:2181,"
			+ "hadoop1:2181,";
	
	public static ZkClient getInstance() {
		return new ZkClient(group, 10000);
	}
}
