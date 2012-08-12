package org.you.core.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

public class DataSourceSinglerInstance implements DataSourceInstance {
	private DbInstanceDescriptor dbInstanceDescriptor;
	private DataSource write_source;
	private List<DataSource> read_sources = new ArrayList<DataSource>();

	public DataSourceSinglerInstance(DbInstanceDescriptor dbInstanceDescriptor) {
		this.dbInstanceDescriptor = dbInstanceDescriptor;
	}

	protected synchronized DataSource buildDbServer(DbServerDescriptor server) {
		BasicDataSource ds = new BasicDataSource();
		if (server.getType().equals("postgresql")) {
			ds.setDriverClassName(CLASSNAME_POSTGRESQL);
			ds.setUrl("jdbc:postgresql://"
					+ server.getHost()
					+ (server.getPort() == 0 ? "" : ":"
							+ String.valueOf(server.getPort())) + "/"
					+ server.getDatabase());
		} else if (server.getType().equals("mysql")) {
			int connectTimeout = server.getConnectTimeout() > 0 ? server
					.getConnectTimeout() : 100;
			int socketTimeout = server.getSocketTimeout();
			ds.setDriverClassName(CLASSNAME_MYSQL);
			StringBuilder url = new StringBuilder("jdbc:mysql://"
					+ server.getHost()
					+ (server.getPort() == 0 ? "" : ":"
							+ String.valueOf(server.getPort())) + "/"
					+ server.getDatabase() + "?user=" + server.getUser()
					+ "&password=" + server.getPassword());
			url.append("&connectTimeout=").append(connectTimeout);
			if (socketTimeout > 0) {
				url.append("&socketTimeout=").append(socketTimeout);
			}
			ds.setUrl(url.toString());
		} else {
			throw new NullPointerException(
					"server type must be either postgresql or mysql");
		}
		ds.setUsername(server.getUser());
		ds.setPassword(server.getPassword());
		ds.setInitialSize(server.getInitPoolSize());

		ds.setMaxActive(server.getMaxPoolSize());
		int maxIdle = server.getMaxIdle();
		if (maxIdle > 0) {
			ds.setMaxIdle(maxIdle);
		} else {
			ds.setMaxIdle(ds.getMaxActive() / 10 + 1);
		}
		ds.setMaxWait(100);
		ds.setTestOnReturn(false);
		ds.setTestWhileIdle(true);
		ds.setMinEvictableIdleTimeMillis(90 * 1000);
		ds.setNumTestsPerEvictionRun(50);
		ds.setTimeBetweenEvictionRunsMillis(1000);
		ds.setValidationQuery("SELECT 1");
		ds.setMinIdle(3);
		//
		return ds;
	}

	private synchronized void buildWriter() {
		if (null != write_source) {
			return;
		}
		write_source = buildDbServer(dbInstanceDescriptor.getWserver());
	}

	public DataSource getWriter(String pattern) {
		buildWriter();
		return write_source;
	}

	public synchronized void buildReaders() {
		if (read_sources != null && read_sources.size() > 0) {
			return;
		}
		for (DbServerDescriptor read_server : dbInstanceDescriptor
				.getRservers()) {
			DataSource read_source = buildDbServer(read_server);
			for (int i = 0; i < read_server.getPriority(); i++) {
				read_sources.add(read_source);
			}
		}
	}

	public DataSource getReader(String pattern) {
		buildReaders();
		return read_sources.get((int) (Math.random() * read_sources.size()));
	}

	public String getTimeStamp() {
		return dbInstanceDescriptor.getTimestamp();
	}

	public void close() {
		closeDataSource(write_source);
		for (DataSource reader : read_sources) {
			closeDataSource(reader);
		}
	}

	private void closeDataSource(DataSource ds) {
		if (ds instanceof BasicDataSource) {
			BasicDataSource new_name = (BasicDataSource) ds;
			try {
				new_name.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		/*
		 * if (ds instanceof ProxoolDataSource) { try {
		 * ProxoolFacade.removeConnectionPool(this.toString() + "_" +
		 * ds.toString()); } catch (ProxoolException e) { e.printStackTrace(); }
		 * }
		 */
	}

	public DataSource getWrite_source() {
		return write_source;
	}

	public void setWrite_source(DataSource write_source) {
		this.write_source = write_source;
	}

	public List<DataSource> getRead_sources() {
		return read_sources;
	}

	public void setRead_sources(List<DataSource> read_sources) {
		this.read_sources = read_sources;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("SinglerInstance: ").append(dbInstanceDescriptor.getName())
				.append(" ");
		if (write_source != null) {
			buf.append("W[")
					.append(((BasicDataSource) write_source).getNumActive())
					.append(",")
					.append(((BasicDataSource) write_source).getNumIdle())
					.append("] ");
		}
		if (read_sources != null) {
			for (DataSource source : read_sources) {
				int i = 0;
				if (source != null) {
					buf.append("R" + i++ + "[")
							.append(((BasicDataSource) source).getNumActive())
							.append(",")
							.append(((BasicDataSource) source).getNumIdle())
							.append("] ");
				}
			}
		}
		return buf.toString();
	}

}