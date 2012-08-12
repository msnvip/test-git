package org.you.core.cache;

import org.apache.log4j.Logger;
import org.you.core.util.StringUtils;

public enum MemCacheFactory {
	INSTANCE;
	
	private IMemCacheManager client;
	
	private static final Logger logger=Logger.getLogger(MemCacheFactory.class);
	
	private MemCacheFactory(){
	}
	
	public static MemCacheFactory getInstance() {
		return INSTANCE;
	}
	
	public static IMemCacheManager getMemCacheManager() {		
		if(INSTANCE.client==null){
			INSTANCE.client = INSTANCE.createClient(MemCacheConfXml.getInstance().getServers());
		}
		
		return INSTANCE.client;
	}

	public void reset(){
		client=null;
	}
	
	protected IMemCacheManager createClient(String servers) {
		//PlatformEnum platform=ApplicationProperties.getInstance().getPlatformType();
		IMemCacheManager client=null;
		if(!StringUtils.isEmpty(servers)){
			//client = new EasyCacheClient(servers,platform.isUseConsistantHash());
			client = new EasyCacheClient(servers,true);
		}else{
			logger.error("server addr is empty!");
		}
		return client;
	}/**/
	
}

