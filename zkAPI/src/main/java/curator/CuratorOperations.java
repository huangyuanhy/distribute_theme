package curator;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
/**
 * Curator 是Netflix开源的zk客户端，对各种应用场景进行封装，
 * curator-framewok 提供了fluent风格api
 * curator-replice 提供了实现封装
 * curator 提供多种连接重试策略 衰减重试，指定最大重试次数，仅重试一次，一直重试到规定时间
 * @author pc
 *
 */
public class CuratorOperations {
	private static CuratorFramework instance=null;
	public static void main(String[] args) throws InterruptedException {
		 instance = Utils.getConnection();
		System.out.println("连接成功"+instance.getState());
		//aysncreate();
		//System.out.println(createNode());
		transactionOperation();
		TimeUnit.MINUTES.sleep(1000);
		instance.close();
	}
	
	/**
	 * 创建节点
	 * @throws InterruptedException 
	 */
	public static String createNode() throws InterruptedException{
		String result=null;
		
		String path="/test/test1";
		//只有持久化节点才能创建子节点
		try {
			 result=instance.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(
					path,"123".getBytes());
			 System.out.println("创建成功。。。。。");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static void delete() {
		try {
			//默认情况下 version =-1
			
			//可删除所有节点
			instance.delete().deletingChildrenIfNeeded().forPath("/test");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getData() throws Exception {
		Stat stat = new Stat();
		byte[] data = instance.getData().storingStatIn(stat).forPath("/test");
		return new String(data)+"stat："+stat;
	}
	
	public static void update() {
		try {
			instance.setData().forPath("test", "123".getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//支持异步操作
	public static void aysncreate() throws InterruptedException {
		CountDownLatch countDownLatch=new CountDownLatch(1);
		try {
			instance.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).
			inBackground(new BackgroundCallback() {
				//异步操作的回调地址
				/**
				 * 响应码(#getResultCode())

					响应码	意义
					0	OK，即调用成功
					-4	ConnectionLoss，即客户端与服务端断开连接
					-110	NodeExists，即节点已经存在
					-112	SessionExpired，即会话过期
				 */
				@Override
				public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
					System.out.println("创建完成，开始回调");
					System.out.println(Thread.currentThread().getName()+"resultCode"+event.getResultCode());
					countDownLatch.countDown();
				}
			}).forPath("/test3");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		countDownLatch.await();
		System.out.println("回调结束，主进程结束");
		
	}
	
	//支持事务操作
	public static void transactionOperation() {
		try {
			Collection<CuratorTransactionResult> res = instance.inTransaction().create().forPath("/trans","11".getBytes()).
			and().setData().forPath("/trans","222".getBytes()).and().commit();
			
			for (CuratorTransactionResult curatorTransactionResult : res) {
				System.out.println(curatorTransactionResult.getForPath()+"->"
						+curatorTransactionResult.getType());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//事件监听
	public static void nodeWatch() throws Exception {
		//节点事件监听，会缓存改变后的结果
		NodeCache nodeCache = new NodeCache(instance, "/curator",false);
		try {
			nodeCache.start(true);//初始化相关参数
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		nodeCache.getListenable().addListener(()->System.out.println("节点数据发生变化，新数据为"+
				new String(nodeCache.getCurrentData().getData())));
		//当数据发生变化时候，会触发上面的监听器
		instance.setData().forPath("/curator","fifff".getBytes());
		System.in.read();
	}
}















