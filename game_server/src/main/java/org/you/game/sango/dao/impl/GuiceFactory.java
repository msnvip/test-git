/**
 * $Id$
 * Copyright(C) 2012-2016 msnvip@msn.cn. All rights reserved.
 */
package org.you.game.sango.dao.impl;

import java.util.Map;

import org.you.core.util.CollectionUtil;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 *
 * @author <a href="mailto:msnvip@msn.cn">msnvip</a>
 * @version 1.0
 * @since 1.0
 */
public class GuiceFactory {
	
	private static Injector injector = Guice.createInjector(new SkillMoudle());
	
	private static Map<Object,Object> objMap = CollectionUtil.newHashMap();
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> clazz){
		Object exist = objMap.get(clazz);
		if(null != exist){
			return (T)exist;
		}else{
			exist = injector.getInstance(clazz);
			objMap.put(clazz, exist);
		}
		return (T) exist;
	}
	
	private static class SkillMoudle extends AbstractModule{
		@Override
		protected void configure() {
		}		
	}
}
