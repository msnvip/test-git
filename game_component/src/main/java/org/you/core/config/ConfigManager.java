package org.you.core.config;

import java.io.File;
import java.util.Map;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.you.core.util.CollectionUtil;
import org.you.core.util.StringUtils;

public class ConfigManager {
	public static final String CONFIG_PATH_DEFAULT = "/data/config/";
	public static final String CONFIG_FILE_PATH = "configFilePath";
	
	private static Map<Object,Object> configMap = CollectionUtil.newHashMap();
	private static Object configLock = new Object();
	
	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> clazz){
		if(null != configMap.get(clazz)){
			return (T) configMap.get(clazz);
		}
		
		synchronized (configLock) {
			if(null != configMap.get(clazz)){
				return (T) configMap.get(clazz);
			}
			
			String configFilePath = System.getProperty(CONFIG_FILE_PATH);
			if(StringUtils.isEmpty(configFilePath)){
				configFilePath = CONFIG_PATH_DEFAULT;
			}
			
			Root rootAnnotation = clazz.getAnnotation(Root.class);
			String fileName = null;
			try {
				fileName = rootAnnotation.name();
				if(StringUtils.isEmpty(fileName)){
					fileName = clazz.getSimpleName();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Serializer serializer = new Persister();
			File source = new File(configFilePath+fileName+".xml");
			try {
				T dest = serializer.read(clazz, source);
				configMap.put(clazz, dest);
				return dest;
			} catch (Exception e) {
				System.out.println("ConfigManager.get error clazz:"+clazz);
				e.printStackTrace();
			}
		}
		
		return (T) configMap.get(clazz);
	}
	
	public static void main(String[] args){
		ConfigManager.get(TestConfig.class);
	}
}

@Root(name="test")
class TestConfig{
	
}
