package org.you.core.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.KetamaConnectionFactory;
import net.spy.memcached.MemcachedClient;

import org.apache.log4j.Logger;

public class EasyCacheClient implements IMemCacheManager {

	private List<MemcachedClient> clients;

	private int timeout = 5;
	private Random rand=new Random(System.currentTimeMillis());
	
	private static final Logger logger=Logger.getLogger(EasyCacheClient.class);
	
	public MemcachedClient getClient() {
		return clients.get(rand.nextInt(clients.size()));
	}
	
	/**
	 * not use consistant hash
	 * @param serveraddrs
	 */
	public EasyCacheClient(String serveraddrs) {
		this(serveraddrs,false);
	}
	
	
	public EasyCacheClient(String serveraddrs,boolean useConsistantHash) {
		try {
			clients=new ArrayList<MemcachedClient>();
			for(int i=0;i<5;i++){
				if(useConsistantHash){
					clients.add(new MemcachedClient(new KetamaConnectionFactory(), AddrUtil.getAddresses(serveraddrs)));
				}else{
					clients.add(new MemcachedClient(AddrUtil.getAddresses(serveraddrs)));
				}
			}
			logger.debug("init server:"+serveraddrs);
			
		} catch (Exception e) {
			e.printStackTrace(System.err);
			logger.error("init cache client failed! svrs="+serveraddrs);
		}
	}
	
	public boolean add(String key, Object value, Date date) {
		return futureBoolean(getClient().add(key,(int) (date.getTime() / 1000L), value));
	}

	public boolean set(String key, Object value, Date expiry) {
		return futureBoolean( getClient().set(key, (int) (expiry.getTime() / 1000L), value));
	}

	public boolean replace(String key, Object value, Date expiry) {
		return futureBoolean(getClient().replace(key,(int) (expiry.getTime() / 1000L), value));
	}

	public boolean delete(String key) {
		return futureBoolean(getClient().delete(key));
	}

	public Object get(String key) {
		return futureObject(getClient().asyncGet(key));
	}
	
	public long incr(String key, int inc) {
		return getClient().incr(key, inc);
	}
	public long incr(String key) {
		return getClient().incr(key, 1);
	}

	public long decr(String key) {
		return getClient().decr(key, 1);
	}

	public long decr(String key, int inc) {
		return getClient().decr(key, inc);
	}
	
	protected boolean futureBoolean(Future<Boolean> f){
		boolean success = false;
		try {
			success = f.get(this.timeout, TimeUnit.SECONDS);
			return success;
		} catch (Exception e) {
			f.cancel(false);
		}
		return false;
	}
	
	protected Object futureObject(Future<Object> f){
		Object myObj = null;
		try {
			myObj = f.get(this.timeout, TimeUnit.SECONDS);
			return myObj;
		} catch (Exception e) {
			f.cancel(false);
		}
		return null;
	}
	

}
