package JavaAPI;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class CreateSession {
	private static CountDownLatch countDownLatch=new CountDownLatch(1);
	private static String group="hadoop1:2181,"
			+ "hadoop1:2181,"
			+ "hadoop1:2181,";
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		ZooKeeper zooKeeper=new ZooKeeper(group, 6000, new Watcher() {

			@Override
			public void process(WatchedEvent watchedEvent) {
				if (watchedEvent.getState()==Event.KeeperState.SyncConnected) {
					countDownLatch.countDown();
					System.out.println(watchedEvent.getState());
				}
			}
		} );
		countDownLatch.await();
		System.out.println(zooKeeper.getState());
		
		
		String result = zooKeeper.create("/test", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println(result);//返回创建的节点路径
	}
}






















