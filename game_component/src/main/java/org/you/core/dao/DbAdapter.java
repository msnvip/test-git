package org.you.core.dao;


import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public final class DbAdapter
{
  private static DbAdapter _instance = new DbAdapter();
  private final ConnectionPoolManager _manager;
  public static final String DB_FISH = "fishpool";

  public static DbAdapter getInstance()
  {
    return _instance;
  }

  public DbAdapter()
  {
    this._manager = new ConnectionPoolManagerImpl();
  }

  public static final String PATTERN_USER_SCORE(int id)
  {
    return "";
  }

  public static final String PATTERN_RELATION(int id) {
    return _PATTERN_HELPER("relation_", id, 100);
  }

  public static final String PATTERN_GOSSIP(int id) {
    return _PATTERN_HELPER("gossip_", id, 100);
  }

  public static final String PATTERN_BLOG(int id) {
    return _PATTERN_HELPER("blog_", id, 100);
  }

  public static final String PATTERN_MESSAGE(int id) {
    return _PATTERN_HELPER("message_", id, 100);
  }

  public static final String PATTERN_ALBUM(int id) {
    return _PATTERN_HELPER("album_", id, 100);
  }

  public static final String PATTERN_GROUP(int id) {
    return _PATTERN_HELPER("group_", id, 100);
  }

  public static final String PATTERN_CLASS(int id) {
    return _PATTERN_HELPER("class_", id, 100);
  }

  private static final String _PATTERN_HELPER(String prefix, int id, int cluster) {
    StringBuffer buff = new StringBuffer();
    buff.append(prefix);
    buff.append(id % cluster);
    return buff.toString();
  }

  public Connection getReadConnection(String name, String pattern)
    throws SQLException
  {
    return this._manager.getReader(name, pattern);
  }

  public Connection getWriteConnection(String name, String pattern) throws SQLException {
    return this._manager.getWriter(name, pattern);
  }

  public Connection getReadConnection(String name) throws SQLException {
    return this._manager.getReader(name);
  }

  public Connection getWriteConnection(String name) throws SQLException {
    return this._manager.getWriter(name);
  }

  public DataSource getReadDataSource(String name, String pattern) {
    return this._manager.getReaderDataSource(name, pattern);
  }
  public DataSource getWriteDataSource(String name, String pattern) {
    return this._manager.getWriterDataSource(name, pattern);
  }

  public DataSource getReadDataSource(String name) {
    return this._manager.getReaderDataSource(name);
  }
  public DataSource getWriteDataSource(String name) {
    return this._manager.getWriterDataSource(name);
  }
}