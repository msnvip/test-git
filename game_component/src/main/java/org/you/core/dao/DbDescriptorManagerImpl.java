package org.you.core.dao;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.you.core.config.ConfigManager;

public class DbDescriptorManagerImpl implements DbDescriptorManager{
	private static DbDescriptorManagerImpl instance = new DbDescriptorManagerImpl();
	private Map<String, DbInstanceDescriptor> dbInstanceDescriptorMap = new HashMap<String, DbInstanceDescriptor>();
	private String db_conf_path = "db_conf.xml";
	private static final Logger logger = Logger
			.getLogger(DbDescriptorManagerImpl.class);

	public static DbDescriptorManagerImpl getInstance() {
		if (instance == null)
			synchronized (DbDescriptorManagerImpl.class) {
				if (instance == null)
					instance = new DbDescriptorManagerImpl();
			}
		return instance;
	}

	private DbDescriptorManagerImpl() {
		try {
			initDbDescriptorMap(db_conf_path);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

	// @SuppressWarnings("unchecked")
	private void initDbDescriptorMap(String db_conf_path) throws Exception {
		
		DbConfig config = ConfigManager.get(DbConfig.class);
		if (config == null) {
			throw new IllegalStateException(
					"db config null, failed to read config file db_conf.xml from remote");
		}
		config.afterConfig();
		for (DbInstanceDescriptor instance : config.getDbInstances()) {
			dbInstanceDescriptorMap.put(instance.getName(), instance);
		}
	}

	public static Document loadXMLFile(URL fileURL) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(fileURL);
		return document;
	}

	public DbInstanceDescriptor getDbInstance(String name) {
		return dbInstanceDescriptorMap.get(name);
	}

	/**
	 * 加载指定的资源。
	 * 
	 * @param sourceName
	 * @return 资源的URL
	 * @throws RuntimeException
	 *             如果找不到资源抛出此异常
	 */
	public static URL getResource(String sourceName) {
		assert sourceName != null && !sourceName.equals("");
		URL url = ClassLoader.getSystemResource(sourceName);
		if (url == null) {
			url = Thread.currentThread().getContextClassLoader().getResource(
					sourceName);
		}
		if (url == null) {
			String errMsg = "couldn't find resource: " + sourceName;
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		return url;
	}

	public static void main(String[] args) {
		DbDescriptorManagerImpl impl = DbDescriptorManagerImpl.getInstance();
		Set<String> keySet = impl.dbInstanceDescriptorMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			DbInstanceDescriptor des = (DbInstanceDescriptor) impl.dbInstanceDescriptorMap
					.get(key);
			System.out.println(des);
		}

	}
}