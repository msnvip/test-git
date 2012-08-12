package org.you.core.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;

public class ConnectionPoolManagerImpl implements ConnectionPoolManager {
	private Map<String, DataSourceInstance> dsMap;
	private DbDescriptorManager dbDescriptorManager;

	public ConnectionPoolManagerImpl() {
		this.dbDescriptorManager = DbDescriptorManagerImpl.getInstance();
		this.dsMap = new ConcurrentHashMap<String, DataSourceInstance>();
	}

	private void initDbInstance(String name) {
		if (this.dsMap.containsKey(name)) {
			return;
		}
		System.out.println("Begin initializing " + name);
		boolean success = false;
		try {
			DbInstanceDescriptor dbInstanceDescriptor = this.dbDescriptorManager
					.getDbInstance(name);
			DataSourceInstance dataSourceInstance = createDataSource(dbInstanceDescriptor);
			this.dsMap.put(name, dataSourceInstance);
			success = true;
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (!success) {
				this.dsMap.remove(name);
			}
		}
		System.out.println("End initializing " + name);
	}

	private DataSourceInstance createDataSource(DbInstanceDescriptor db) {
		return new DataSourceSinglerInstance(db);
	}

	public Connection getWriter(String name) throws SQLException {
		return getWriter(name, "");
	}

	public Connection getWriter(String name, String pattern)
			throws SQLException {
		try {
			DataSource ds = getWriterDataSource(name, pattern);
			return ds.getConnection();
		} catch (SQLException e) {
			System.err.println("ConnectionPoolException: getWriter(" + name
					+ ", " + pattern + "). Error " + e.getErrorCode() + ":"
					+ e.getMessage());

			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
		}
	}

	public DataSource getWriterDataSource(String name, String pattern) {
		DataSourceInstance dsi = getInstance(name);
		DataSource ds = dsi.getWriter(pattern);
		return ds;
	}

	public DataSource getWriterDataSource(String name) {
		DataSourceInstance dsi = getInstance(name);
		DataSource ds = dsi.getWriter("");
		return ds;
	}

	public Connection getReader(String name) throws SQLException {
		return getReader(name, "");
	}

	public Connection getReader(String name, String pattern)
			throws SQLException {
		try {
			DataSource ds = getReaderDataSource(name, pattern);
			return ds.getConnection();
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
		}
	}

	public DataSource getReaderDataSource(String name, String pattern) {
		DataSourceInstance dsi = getInstance(name);
		DataSource ds = dsi.getReader(pattern);
		return ds;
	}

	public DataSource getReaderDataSource(String name) {
		DataSourceInstance dsi = getInstance(name);
		DataSource ds = dsi.getReader("");
		return ds;
	}

	public DataSourceInstance getInstance(String name) {
		initDbInstance(name);
		DataSourceInstance ds = (DataSourceInstance) this.dsMap.get(name);
		return ds;
	}
}