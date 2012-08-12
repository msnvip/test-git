package org.you.core.dao;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "instance")
public class DbInstanceDescriptor
{
	@Attribute
	private String name;
	@Attribute
	private String timestamp;

	@Attribute
	private java.lang.String type;
	private DbServerDescriptor wserver;

	@ElementList(name = "server", inline = true)
	private List<DbServerDescriptor> allServers = new ArrayList<DbServerDescriptor>();

	private List<DbServerDescriptor> rservers = new ArrayList<DbServerDescriptor>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DbServerDescriptor getWserver() {
		return wserver;
	}

	public void setWserver(DbServerDescriptor wserver) {
		this.wserver = wserver;
	}

	public List<DbServerDescriptor> getRservers() {
		return rservers;
	}

	public void setRservers(List<DbServerDescriptor> rservers) {
		this.rservers = rservers;
	}

	public void addReadServer(DbServerDescriptor reader_server) {

		this.rservers.add(reader_server);
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DB INSTANCE: ").append(name).append("\n");
		if (wserver != null) {
			buffer.append("[write server]\n");
			buffer.append(wserver.toString()).append("\n");
		}
		buffer.append("[read server]\n");
		for (DbServerDescriptor readServer : rservers) {
			buffer.append(readServer.toString()).append("\n");
		}
		return buffer.toString();
	}

	public void classify() {
		for (DbServerDescriptor db : allServers) {
			String flag = db.getWrflag();
			if (flag.contains("r")) {
				db.setCanRead(true);
				for (int i = 0; i < db.getPriority(); i++) {
					this.addReadServer(db);
				}
			}
			if (flag.contains("w")) {
				db.setCanWrite(true);
				this.wserver = db;
			}
		}
	}
}