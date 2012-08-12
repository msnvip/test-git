package org.you.core.dao;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "db_conf")
public class DbConfig {
	
	@ElementList(name = "instance", inline = true)
	private List<DbInstanceDescriptor> dbInstances = new ArrayList<DbInstanceDescriptor>();

	public List<DbInstanceDescriptor> getDbInstances() {
		return dbInstances;
	}
	
	public void afterConfig(){
		if(dbInstances != null){
			for(DbInstanceDescriptor db: dbInstances){
				db.classify();
			}
		}
	}
}
