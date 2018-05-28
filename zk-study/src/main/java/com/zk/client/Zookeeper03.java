package com.zk.client;

import java.util.Collections;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class Zookeeper03 implements Watcher{
	
	private static final String LOCK_NODE_NAME = "lockNode";// 锁节点名称
    private String lockNodePath;// 锁节点路径，带序列号的，如：/lockNodeRoot/lockNode0000000010
    private String LOCK_ROOT_NODE_PATH = "/lockNodeRoot"; // 锁根目录
    private Object lockObj = new Object();// 阻塞锁
    private ZooKeeper zk;

    public Zookeeper03(ZooKeeper zk) {
        this.zk = zk;
    }

    /**
     * 获得锁
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void acquire() throws KeeperException, InterruptedException {
        // 创建临时锁节点
        lockNodePath = zk.create(LOCK_ROOT_NODE_PATH + "/" + LOCK_NODE_NAME, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        // 采用“等待-通知”的结构
        synchronized (lockObj) {
            while (true) {
                // 获得根节点路径下所有子节点
                List<String> childLockNodes = zk.getChildren(LOCK_ROOT_NODE_PATH, false);
                int size = childLockNodes.size();
                if (size == 1) {
                    return;// 说明得到了锁
                } else {
                    String thisNode = lockNodePath.substring((LOCK_ROOT_NODE_PATH + "/").length());
                    // 排序
                    Collections.sort(childLockNodes);
                    int index = childLockNodes.indexOf(thisNode);
                    // 为0， 说明在列表中唯一,获得锁
                    if (index == 0) {
                        return;
                    } else {
                        // 获得排名比thisPath前1位的节点
                        String prePath = LOCK_ROOT_NODE_PATH + "/" + childLockNodes.get(index - 1);
                        // 注册监听器：当前一个节点被删除或者创建那个节点的服务器挂掉了,zookeeper都会回调监听器的process方法
                        zk.getData(prePath, this, null);
                    }
                    // 睡一会儿
                    lockObj.wait();
                }
            }
        }
    }

    /**
     * 释放锁
     *
     */
    public void release() throws KeeperException, InterruptedException {
        zk.delete(lockNodePath, -1);
    }

    public void process(WatchedEvent event) {
        synchronized (lockObj) {
            lockObj.notify();// 唤醒睡眠中的线程，重新执行循环中的逻辑
        }
    }
}
