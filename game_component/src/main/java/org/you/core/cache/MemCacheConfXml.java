package org.you.core.cache;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;
import org.you.core.config.ConfigManager;


@Root(name = "mem_conf")
public class MemCacheConfXml {

	@ElementList(inline = true, entry = "server")
	private List<MemConfItem> list;
	
	private List<MemConfItem> workList;
	
	public static MemCacheConfXml getInstance(){
		return ConfigManager.get(MemCacheConfXml.class);
	}
	
	@Commit
	public void init(){
		workList = createWorkList(list);
		MemCacheFactory.getInstance().reset();
	}
	
	private List<MemConfItem> createWorkList(List<MemConfItem> list){
		List<MemConfItem> workList=new ArrayList<MemConfItem>();
		for(MemConfItem item:list){
			if(item.isWork()){
				workList.add(item);
			}
		}
		return workList;
	}
	
	public String getServers(){
		if(workList.isEmpty()){
			return "";
		}
		String servers = "";
		for (MemConfItem item:workList) {
			servers = servers + item.getServerAddr()+" ";
		}
		servers = servers.substring(0, servers.length() - 1);
		return servers;
	}
}
