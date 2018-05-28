package com.zk.client;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class Zookeeper01 {
	
	private final static String connectString = "192.168.37.3:2181,192.168.37.4:2181,192.168.37.5:2181"; 
	
	private final static Integer sessionTimeout = 120;
	
	private static CountDownLatch cdl = new CountDownLatch(1);

	public static void main(String[] args) {
		 
		try {
			ZooKeeper zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
				public void process(WatchedEvent event) {
					if(event.getState() == KeeperState.SyncConnected){
						System.out.println("----------------connection success----------------");
						cdl.countDown();
					}
				}
			});
			cdl.await();
			
			System.out.println("----------------start----------------");
			
			Stat stat = zk.exists("/", false);
			
			if(stat == null){
				System.out.println("222222222");
			}else{
				
				/*cZxid：这是导致创建znode更改的事务ID。
				mZxid：这是最后修改znode更改的事务ID。
				pZxid：这是用于添加或删除子节点的znode更改的事务ID。
				ctime：表示从1970-01-01T00:00:00Z开始以毫秒为单位的znode创建时间。
				mtime：表示从1970-01-01T00:00:00Z开始以毫秒为单位的znode最近修改时间。
				dataVersion：表示对该znode的数据所做的更改次数。
				cversion：这表示对此znode的子节点进行的更改次数。
				aclVersion：表示对此znode的ACL进行更改的次数。
				ephemeralOwner：如果znode是ephemeral类型节点，则这是znode所有者的 session ID。 如果znode不是ephemeral节点，则该字段设置为零。
				dataLength：这是znode数据字段的长度。
				numChildren：这表示znode的子节点的数量。*/
				
				System.out.println("-------------------->cZxid:" + stat.getCzxid());
				System.out.println("-------------------->mZxid:" + stat.getMzxid());
				System.out.println("-------------------->pZxid:" + stat.getPzxid());
				System.out.println("-------------------->ctime:" + stat.getCtime());
				System.out.println("-------------------->mtime:" + stat.getMtime());
				System.out.println("-------------------->dataVersion:");
				System.out.println("-------------------->cversion:" + stat.getCversion());
				System.out.println("-------------------->aclVersion:");
				System.out.println("-------------------->ephemeralOwner:" + stat.getCzxid());
				System.out.println("-------------------->dataLength:" + stat.getDataLength());
				System.out.println("-------------------->numChildren:" + stat.getNumChildren());
			}
			
			deletePath(zk, "/");
			
			
			/*//Stat stat = zk.exists("/0001", false);
			if(stat == null){
				String path = zk.create("/0001", "www.baidu.com".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				System.out.println("----------path-----" + path);
			}else{
				List<String> childPaths = zk.getChildren("/0001", new Watcher() {
					public void process(WatchedEvent event) {
						if(event.getType() == EventType.NodeDataChanged){
							System.out.println("-------");
						}
					}
				});
				
				System.out.println("-------------22222222------------");
				byte[] data = zk.getData("/0001", new Watcher() {
					public void process(WatchedEvent event) {
						if(event.getType() == EventType.NodeDataChanged){
							
						}
					}
				}, stat);
				
				if(data != null && data.length > 0){
					System.out.println(new String(data,"UTF-8"));
				}
				
			}*/
			
			Thread.sleep(Long.MAX_VALUE);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("--------------------------------------------");
		}
	}
	
	
	public static void deletePath(ZooKeeper zk,String path) throws IllegalAccessException, KeeperException, InterruptedException{
		if(zk == null){
			throw new IllegalAccessException("zk 参数错误");
		}
		
		if(path == null || path.equals("")){
			throw new IllegalAccessException("path 参数错误");
		}
		
		if(zk.exists(path, false) != null){
			List<String> paths = zk.getChildren(path, false);
			if(paths != null && paths.size() > 0){
				for(String path_ : paths){
					if(!path_.equals("zookeeper")){
						path_ = "/" + path_;
						deletePath(zk, path_);
					}
				}
			}
			System.out.println("---------------------->" + path);
			zk.delete("/" + path, -1);
		}else{
			throw new RuntimeException("路径错误");
		}
	}
}
