package com.mouchina.base.redis;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

@Component
public class RedisServer {
	
	private String hosts;
	private String reqeuirePass;
	private int maxTotal = 20;
	private int maxIdle = 10;
	private long maxWaitMillis = 1000 * 50;
	private static ShardedJedisPool pool = null;

	/**
	 * 构建redis连接池
	 *
	 * @param ip
	 * @param port
	 * @return JedisPool
	 */
	public synchronized ShardedJedisPool getPool() {
		if (pool == null) {
			createPool();
		}

		return pool;
	}

	public void init() {
		createPool();
	}

	/**
	 * 返还到连接池
	 *
	 * @param pool
	 * @param redis
	 */

	public void returnResource(ShardedJedisPool pool, ShardedJedis sjedis) {
		if (pool != null) {
			boolean broken = false;
			for (Jedis jedis : sjedis.getAllShards()) {
				if (jedis.getClient().isBroken()) {
					broken = true;
					break;
				}
			}

			if (broken) {
				pool.returnBrokenResource(sjedis);
			} else {
				pool.returnResource(sjedis);
			}
		}
	}

	private void createPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		//设置最大连接数
		config.setMaxTotal(maxTotal);
		// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
		// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
		// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
		config.setMaxIdle(maxIdle);
		// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		config.setMaxWaitMillis(maxWaitMillis);
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		config.setTestOnBorrow(true);

		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();

		for (String host : hosts.split(",")) {
			String[] _host = host.split(":");
			JedisShardInfo si = new JedisShardInfo(_host[0], _host[1]);
			si.setPassword(reqeuirePass);
			shards.add(si);
		}

		pool = new ShardedJedisPool(config, shards);
	}

	public String getHosts() {
		return hosts;
	}

	public void setHosts(String hosts) {
		this.hosts = hosts;
	}

	public String getReqeuirePass() {
		return reqeuirePass;
	}

	public void setReqeuirePass(String reqeuirePass) {
		this.reqeuirePass = reqeuirePass;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	
	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public long getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(long maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}
}
