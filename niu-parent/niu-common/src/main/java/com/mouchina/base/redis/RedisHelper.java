package com.mouchina.base.redis;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Component
public class RedisHelper {
	
	
	public enum RedisKeyPrefix{
		SEND_PHONE_IDENFITY_CODE_24_HOUR_LIMIT,//24小时的限制次数
		SEND_PHONE_IDENFITY_CODE_1_HOUR_LIMIT,//1小时的限制次数
		SEND_PHONE_IDENFITY_CODE_5_MINUTES_LIMIT,//5分钟的限制
		PHONE_IDENFITY_CODE_KEY_PREFIX,//短信验证码前缀
		USER_LOGIN_TOKEN_KEY_PREFIX,//用户登录获取的签名key前缀
		GLOBAL_UNIQUE_USERID_KEY,//用户ID  key前缀
		GLOBAL_UNIQUE_INVITE_CODE_KEY,//邀请码增长
		RESQUEST_ACCESS_TOKEN_KEY_PREFIX,//调用接口时的token
		GLOBAL_UNIQUE_ORDER_NO_KEY,//全局唯一订单序列
		GLOBAL_UNIQUE_PAY_NO_KEY,//全局唯一订单序列
	}
	
	
	
	
	private static Logger logger = LogManager.getLogger("RedisHelper");
	@Resource
	private RedisServer redisServer;

	/**
	 * 获取数据
	 *
	 * @param key
	 * @return
	 */
	public String get(String key) {
		String value = null;

		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;

		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			// 释放redis对象
			// pool.returnBrokenResource( jedis );
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}

		return value;
	}

	public void set(String key, String value) {
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;

		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			value = jedis.set(key, value);
		} catch (Exception e) {
			// 释放redis对象
			// pool.returnBrokenResource( jedis );
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}
	}
	
	/**
	 * 向redis插入一个key,并且设置过期时间
	 * @param key			redis key
	 * @param seconds		过期时间
	 * @param value			存储值
	 */
	public void setex(String key, Integer seconds,String value) {
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;

		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			value = jedis.setex(key, seconds, value);
		} catch (Exception e) {
			// 释放redis对象
			// pool.returnBrokenResource( jedis );
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}
	}

	public void remove(String key) {
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;

		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			jedis.del(key);
		} catch (Exception e) {
			// 释放redis对象
			// pool.returnBrokenResource( jedis );
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}
	}

	public void lPush(String key, String value) {
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;

		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			jedis.lpush(key, value);
		} catch (Exception e) {
			// 释放redis对象
			// pool.returnBrokenResource( jedis );
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}
	}

	public void lPushs(String key, String[] value) {
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;

		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			jedis.lpush(key, value);
		} catch (Exception e) {
			// 释放redis对象
			// pool.returnBrokenResource( jedis );
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}
	}

	public void rPop(String key) {
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;

		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			jedis.rpop(key);
		} catch (Exception e) {
			// 释放redis对象
			// pool.returnBrokenResource( jedis );
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}
	}

	public Long llen(String key) {
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;
		Long len = null;

		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			len = jedis.llen(key);
		} catch (Exception e) {
			// 释放redis对象
			// pool.returnBrokenResource( jedis );
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}

		return len;
	}

	public List<String> lrange(String key, int start, int top) {
		List<String> list = null;
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;

		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			list = jedis.lrange(key, start, top);
		} catch (Exception e) {
			// 释放redis对象
			// pool.returnBrokenResource( jedis );
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}

		return list;
	}

	public void lrem(String key, int count, String value) {
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;
		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			jedis.lrem(key, count, value);
		} catch (Exception e) {
			// 释放redis对象
			// pool.returnBrokenResource( jedis );
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}
	}

	public static void main(String[] args) {
		RedisHelper helper = new RedisHelper();
		helper.set("test", "aa");

		String test = helper.get("test");

		System.out.println("test=" + test);

		helper.lPush("list", "111");
		helper.lPush("list", "222");
		helper.lPush("list", "333");

		List<String> list = helper.lrange("list", 0, 10);

		for (Object object : list) {
			System.out.println("list  obj=" + object);
		}

		System.out.println("list  llen =" + helper.llen("list"));
		helper.rPop("list");
		helper.lPush("list", "444");
		helper.lPush("list", "555");
		helper.lPush("list", "6666");
		System.out.println("list  llen =" + helper.llen("list"));
		list = helper.lrange("list", 0, 10);

		for (Object object : list) {
			System.out.println("list  obj=" + object);
		}
	}

	public Long getIncr(String key, long integer) {
		Long value = null;

		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;

		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			value = jedis.incrBy(key, integer);
		} catch (Exception e) {
			// 释放redis对象
			// pool.returnBrokenResource( jedis );
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}

		return value;
	}

	public Long getIncr(String key) {
		Long value = null;

		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;

		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			value = jedis.incr(key);
		} catch (Exception e) {
			// 释放redis对象
			// pool.returnBrokenResource( jedis );
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}

		return value;
	}

	/*-------------------------------4.0-----------------------------------*/
	/*-------------------------------author: liuxin-----------------------------------*/

	public void hset(String hkey, String key, String value) {
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;

		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			jedis.hset(hkey, key, value);
		} catch (Exception e) {
			// 释放redis对象
			// pool.returnBrokenResource( jedis );
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}
	}

	public String hget(String hkey, String key) {
		String value = "";
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;

		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			value = jedis.hget(hkey, key);
		} catch (Exception e) {
			// 释放redis对象
			// pool.returnBrokenResource( jedis );
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}
		return value;
	}

	public void hdel(String hkey, String key) {
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;

		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			jedis.hdel(hkey, key);
		} catch (Exception e) {
			// 释放redis对象
			// pool.returnBrokenResource( jedis );
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}
	}

	/**
	 * 
	 * @param hkey
	 * @return 返回所有的 hash 键值对
	 */
	public Map<String, String> hgetAll(String hkey) {
		Map<String, String> map = new LinkedHashMap<>();
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;

		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			map = jedis.hgetAll(hkey);
		} catch (Exception e) {
			// 释放redis对象
			// pool.returnBrokenResource( jedis );
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}
		return map;
	}

	/**
	 * HASH 结构, 返回所有hkey 的 value
	 * 
	 * @param hkey
	 * @return
	 */
	public List<String> hvals(String hkey) {
		List<String> hvals = new ArrayList<>();
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;
		
		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			hvals = jedis.hvals(hkey);
		} catch (Exception e) {
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}
		return hvals;
	}
	
	/**
	 * HASH 结构, 返回所有hkey 的 key
	 * 
	 * @param hkey
	 * @return
	 */
	public Set<String> hkeys(String hkey) {
		Set<String> keys = null;
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;
		
		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			keys = jedis.hkeys(hkey);
		} catch (Exception e) {
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}
		return keys;
	}
	
	/**
	 * getset
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	public String getSet(String key, String val) {
		String result = null;
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;
		
		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			result = jedis.getSet(key, val);
		} catch (Exception e) {
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}
		return result;
	}
	
	/**
	 * EXPIREAT
	 * 
	 * @param key
	 * @param timestamp
	 * @return
	 */
	public void expire(String key, int seconds) {
		ShardedJedisPool pool = null;
		ShardedJedis jedis = null;
		
		try {
			pool = redisServer.getPool();
			jedis = pool.getResource();
			jedis.expire(key, seconds);
		} catch (Exception e) {
			logger.error("获取连接池中的jedis连接出错!");
			e.printStackTrace();
		} finally {
			// 返还到连接池
			redisServer.returnResource(pool, jedis);
		}
	}

}
