package org.you.core.cache;

import org.simpleframework.xml.Attribute;

public class MemConfItem {
	
	@Attribute(required=true)
	private String host;
	@Attribute(required=true)
	private int port;
	@Attribute(required=true)
	private int connectionNum=5;
	@Attribute(required=false)
	private boolean work=true;

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public int getConnectionNum() {
		return connectionNum;
	}

	public boolean isWork() {
		return work;
	}
	
	public String getServerAddr(){
		return String.format("%s:%d", host,port);
	}

	@Override
	public String toString() {
		return "MemConfItem [connectionNum=" + connectionNum + ", host=" + host
				+ ", port=" + port + ", work=" + work + "]";
	}

	

}
