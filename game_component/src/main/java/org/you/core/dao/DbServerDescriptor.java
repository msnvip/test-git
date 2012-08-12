package org.you.core.dao;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.you.core.util.StringUtils;

@Root(name = "server")
public class DbServerDescriptor
{
	@Attribute
	private String type;
	@Attribute
	private String database;
	@Attribute
	private String host;

	@Attribute(required = false)
	private int port;
	@Attribute
	private String charset;
	@Attribute
	private String user;
	@Attribute
	private String password;
	@Attribute(name = "priority", required = false)
	private String priority;

	@Attribute
	private String wrflag;

	@Attribute(required = false)
	private int initPoolSize = 3;

	@Attribute(required = false)
	private int maxPoolSize = 300;
	
	@Attribute(required = false)
	private int maxIdle = -1;

	/**
	 * Timeout for socket connect (in milliseconds), with 0 being no timeout.
	 * Only works on JDK-1.4 or newer. Defaults to '0'.
	 * <p>
	 * used at: java.net.Socket.connect(SocketAddress, int)
	 */
	@Attribute(required = false)
	private int connectTimeout = -1;

	/**
	 * Timeout on network socket operations (0, the default means no timeout).
	 * used at: java.net.Socket.setSoTimeout(int)
	 */
	@Attribute(required = false)
	private int socketTimeout = -1;

	private boolean canWrite = false;
	private boolean canRead = false;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isCanWrite() {
		return canWrite;
	}

	public void setCanWrite(boolean canWrite) {
		this.canWrite = canWrite;
	}

	public boolean isCanRead() {
		return canRead;
	}

	public void setCanRead(boolean canRead) {
		this.canRead = canRead;
	}

	public int getPriority() {
		if (StringUtils.isEmpty(priority)) {
			return 1;
		}
		int p = 1;
		try {
			p = Integer.valueOf(priority);
		} catch (NumberFormatException e) {
			p = 1;
		}
		return p;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("type:").append(this.type).append("  ");
		buffer.append("host:").append(this.host).append("  ");
		buffer.append("port:").append(this.port).append("  ");
		buffer.append("database:").append(this.database).append("  ");
		buffer.append("priority:").append(this.priority).append("  ");
		buffer.append("canRead:").append(this.canRead).append("  ");
		buffer.append("canWrite:").append(this.canWrite).append("  ");
		return buffer.toString();
	}

	public String getWrflag() {
		return wrflag;
	}

	public void setWrflag(String wrflag) {
		this.wrflag = wrflag;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getInitPoolSize() {
		return initPoolSize;
	}

	public void setInitPoolSize(int initPoolSize) {
		this.initPoolSize = initPoolSize;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
 
}
