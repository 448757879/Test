package com.test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.PreDestroy;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

import org.springframework.util.Assert;

public class MemcachedManager {
	
	//private Logger logger = LoggerFactory.getLogger(MemcachedManager.class);
	private static final String SEPARATOR = "-";
	
	private static MemcachedManager instance;
	private MemcachedClient client;
	private String channel = "51offer" ;
	//private String addresses = "192.168.37.12:11211";
	private String addresses = "10.172.228.81:12000";
	//channel=51offer
	public  static  String offerUrl = "http://www.51offer.com";
	
	
	 MemcachedManager() {
		try {
			client = new MemcachedClient(AddrUtil.getAddresses(addresses));
		} catch (IOException e) {
			e.printStackTrace();
			//logger.error("new MemcachedClient failed!", e);
		}
	}
	 
	 public static void main(String[] args) {
		 MemcachedManager m = new MemcachedManager();
		 
		 m.add("bd1223001", "双德2B");
		 System.out.println(m.get("bd1223001"));
		 //System.out.println(m.get("scode_8fe0cfc6f88533b7cf7b4ecb91bef190"));
	}
	/**
	 * 单例
	 * spy MemcachedClient 是一种nio方式的连接，为避免原则性操作问题，整个应用只允许打开一个连接
	 * @return
	 */
	public static MemcachedManager getInstance() {
		if (null == instance) {
			instance = new MemcachedManager();
		}
		return instance;
	}
	
//	@PostConstruct
//	public void initClient() throws IOException {
//		logger.info("initClient memcached");
//		client = new MemcachedClient(AddrUtil.getAddresses(addresses));
//	}

	@PreDestroy
	public void destroy() {
		client.shutdown();
		//logger.info("destroy memcached");
	}
	
	public Object get(String key)  {
		Assert.notNull(key, "访问memcache的key不可为空");
		String realKey = composeKey(key);
		Object value = client.get(realKey);
		//logger.debug("get value from memcache key:{} value:{}", realKey, value);
		//logger.debug("get value from memcache key:{}", realKey);
		if (value == null) {
			return null;
		}
		return value;

	}
	
	/**
	 * 添加其他类型
	 * @param key1
	 * @param key2
	 * @return
	 */
	public Object get(String key1,String key2)  {
		String realKey = key1 + SEPARATOR + key2;
		Object value = client.get(realKey);
		//logger.debug("get value from memcache key:{} value:{}", realKey, value);
		//logger.debug("get value from memcache key:{}", realKey);
		if (value == null) {
			return null;
		}
		return value;

	}


	public void set(String key, Object value) throws IOException {
		set(key, 0, value);
	}

	public void delete(String key) {
		Assert.notNull(key, "删除memcache的key不可为空");
		String realKey = composeKey(key);
		//logger.debug("begin to delete value from memcache key:{}", realKey);
		client.delete(realKey);
	}

	/**
	 * 
	 * @param key
	 * @param expre 单位 秒
	 * @param value
	 * @throws IOException
	 */
	public void set(String key, int expre, Object value) throws IOException {
		Assert.notNull(key, "设置memcache的key不可为空");
		Assert.isTrue(expre >= 0, "expre time must >= 0");
		String realKey = composeKey(key);
		//logger.debug("begin to set value to memcache key:{} value:{}", realKey, value);
		//logger.debug("begin to set value to memcache key:{}", realKey);
		client.set(realKey, expre, value);
		//logger.debug("success set value to memcache key:{} value:{}", realKey, value);
	}
	
	/**
	 * 一个key只允许包含一条记录
	 * 不等待结果集返回
	 * 内部串行实现
	 * @param key
	 * @param expre 秒
	 * @param value
	 * @throws IOException
	 */
	public void addNoWait(String key, int expre, Object value) throws IOException {
		Assert.notNull(key, "设置memcache的key不可为空");
		Assert.isTrue(expre >= 0, "expre time must >= 0");
		String realKey = composeKey(key);
		//logger.debug("begin to set value to memcache key:{} value:{}", realKey, value);
		//logger.debug("begin to addNoWait value to memcache key:{}", realKey);
		client.add(realKey, expre, value);
		//logger.debug("success set value to memcache key:{} value:{}", realKey, value);
	}
	/**
	 * 一个key只允许包含一条记录
	 * 等待结果集返回
	 * 内部串行实现
	 * @param key
	 * @param expre 秒
	 * @param value
	 * @throws IOException
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public boolean addWait(String key, int expre, Object value) throws IOException, InterruptedException, ExecutionException {
		Assert.notNull(key, "设置memcache的key不可为空");
		Assert.isTrue(expre >= 0, "expre time must >= 0");
		String realKey = composeKey(key);
		//logger.debug("begin to set value to memcache key:{} value:{}", realKey, value);
		//logger.debug("begin to addWait value to memcache key:{}", realKey);
		Future<Boolean> b = client.add(realKey, expre, value);
		return b.get();
	}
	protected String composeKey(String key) {
		String result = this.channel + SEPARATOR + key;
		return result;
	}
	
	 public boolean add(String key, Object value) {
		try {
			set(key, value);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true ;
	  }

	  public boolean add(String key, int secs, Object value)
	  {
		  try {
			set(key , secs ,value  );
		} catch (IOException e) {
			e.printStackTrace();
		}
		  return true ;
	  }
	  
	//mybitas 和shiro用这个方法
		 /* public boolean add(String key, Object value, String groupKey)
		  {
		    String keyStr = toKeyString(key);
		    List group = new ArrayList();
		    Object obj = get(groupKey);
		    if (obj != null) {
		      try {
		        List list = (List)obj;
		        if (list.contains(keyStr)) {
		          return this.mcc.replace(keyStr, value);
		        }
		        group.addAll(list);
		      } catch (Exception e) {
		        log.error("添加group出错");
		      }

		      group.add(keyStr);
		      replace(groupKey, group);
		    } else {
		      group.add(keyStr);
		      add(groupKey, group);
		    }

		    return add(key, value);
		  }*/

		/*  public boolean add(String key, Object value, Date expiry) {
		    Object v = get(key);
		    set(key ,value , expiry );
		  }*/
}
