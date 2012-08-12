package org.you.core.cache;

import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

public class MemCacheManager implements IMemCacheManager {
	private MemcachedClient client;
	private int timeout = 5;

	public MemCacheManager(String serveraddrs) {
		try {
			this.client = new MemcachedClient(AddrUtil
					.getAddresses(serveraddrs));
			System.out.println(serveraddrs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public boolean add(String key, Object value, Date date) {
		Future<Boolean> f = this.client.add(key,
				(int) (date.getTime() / 1000L), value);
		try {
			f.get(this.timeout, TimeUnit.SECONDS);
		} catch (Exception e) {
			f.cancel(false);
			return false;
		}
		return true;
	}

	public boolean set(String key, Object value, Date expiry) {
		Future<Boolean> f = this.client.set(key,
				(int) (expiry.getTime() / 1000L), value);
		try {
			f.get(this.timeout, TimeUnit.SECONDS);
		} catch (Exception e) {
			f.cancel(false);
			return false;
		}
		return true;
	}


	public boolean replace(String key, Object value, Date expiry) {
		Future<Boolean> f = this.client.replace(key,
				(int) (expiry.getTime() / 1000L), value);
		try {
			f.get(this.timeout, TimeUnit.SECONDS);
		} catch (Exception e) {
			f.cancel(false);
			return false;
		}
		return true;
	}


	public boolean delete(String key) {
		Future<Boolean> f = this.client.delete(key);
		try {
			f.get(this.timeout, TimeUnit.SECONDS);
		} catch (Exception e) {
			f.cancel(false);
			return false;
		}
		return true;
	}

	
	public Object get(String key) {
		Future<Object> f = this.client.asyncGet(key);
		Object myObj = null;
		try {
			myObj = f.get(this.timeout, TimeUnit.SECONDS);
			return myObj;
		} catch (Exception e) {
			f.cancel(false);
		}
		return null;
	}

	
	public long incr(String key, int inc) {
		return this.client.incr(key, inc);
	}

	
	public long incr(String key) {
		return this.client.incr(key, 1);
	}


	public long decr(String key) {
		return this.client.decr(key, 1);
	}

	
	public long decr(String key, int inc) {
		return this.client.decr(key, inc);
	}
}
