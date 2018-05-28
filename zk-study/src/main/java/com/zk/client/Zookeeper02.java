package com.zk.client;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class Zookeeper02 {
	
	private final static String connectString = "192.168.37.3:2181,192.168.37.4:2181,192.168.37.5:2181"; 
	
	private final static Integer sessionTimeout = 120;
	
	private static CountDownLatch cdl = new CountDownLatch(1);

	public static void main(String[] args) {
		 
		try {
			ZooKeeper zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
				public void process(WatchedEvent event) {
					if(event.getState() == KeeperState.SyncConnected){
						System.out.println("111111");
						cdl.countDown();
					}
				}
			});
			cdl.await();
			
			System.out.println("------------------开始---------------");
			
			
			Stat stat = zk.setData("/0001", "hi china".getBytes(), -1);
			
			System.out.println(stat.getVersion());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
