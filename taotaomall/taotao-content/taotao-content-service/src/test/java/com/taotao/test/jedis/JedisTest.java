package com.taotao.test.jedis;


import java.util.HashSet;
import java.util.Set;

import com.alibaba.dubbo.rpc.cluster.Cluster;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	public static void main(String[] args) {
		testJedisPool();
	}
//单机版测试
	 
	public static void standlone() {
		Jedis cli=new Jedis("47.106.39.238", 6380);
		cli.set("test", "test");
		System.out.println(cli.get("test"));
		cli.close();
	}
	
	 public static void testJedisPool() {
	        //创建jedis连接池
	        JedisPool pool = new JedisPool("47.106.39.238", 6380);
	        //从连接池中获得Jedis对象
	        Jedis jedis = pool.getResource();
	        String string = jedis.get("test");
	        System.out.println(string);
	        //关闭jedis对象
	        jedis.close();
	        pool.close();
	    }
	 //集群版
	 public static void testJedisCluster() {
		 Set<HostAndPort> nodes = new  HashSet<>();
		 nodes.add(new HostAndPort("47.106.39.238", 6380));
		 nodes.add(new HostAndPort("47.106.39.238", 6381));
		 nodes.add(new HostAndPort("47.106.39.238", 6379));
		 
		 JedisCluster jedisCluster = new JedisCluster(nodes);
		 jedisCluster.set("cluster", "cluster-value");
		 System.out.println(jedisCluster.get("cluster"));
		 jedisCluster.close();
	 }
}
