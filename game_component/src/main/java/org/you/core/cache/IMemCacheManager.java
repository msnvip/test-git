package org.you.core.cache;


import java.util.Date;

/**
 * 缓存处理接口
 * @author Richie Yan
 *
 */
public interface IMemCacheManager {

	public abstract boolean add(String key, Object value, Date date);

	public abstract boolean set(String key, Object value, Date expiry);

	public abstract boolean replace(String key, Object value, Date expiry);

	public abstract boolean delete(String key);

	public abstract Object get(String key);

	public abstract long incr(String key, int inc);

	public abstract long incr(String key);

	public abstract long decr(String key);

	public abstract long decr(String key, int inc);

}