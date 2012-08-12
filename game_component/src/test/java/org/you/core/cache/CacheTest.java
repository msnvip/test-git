package org.you.core.cache;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.you.core.config.ConfigUtil;

public class CacheTest {
	private static String CACHE_KEY_TEST = "test_key";
	private Date expired = new Date(2000000L);
	
	@Before
	public void init(){
		ConfigUtil.init();
	}
	
	@Test
	public void add(){
		MemCacheFactory.getMemCacheManager().add(CACHE_KEY_TEST, "this is a test", expired);
	}
	@Test
	public void update(){
		MemCacheFactory.getMemCacheManager().set(CACHE_KEY_TEST, "dddddddddss", expired);
	}
	@Test
	public void get(){
		Object obj = MemCacheFactory.getMemCacheManager().get(CACHE_KEY_TEST);
		System.out.println("geted obj:"+obj);
	}
}
